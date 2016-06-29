package com.app.payment.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;

import org.apache.http.client.HttpResponseException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.app.dto.OrderDTO;
import com.app.dto.OrderWrapper;
import com.app.dto.PaymentChannelDTO;
import com.app.dto.PaymentChannelWrapper;
import com.app.dto.PgBankMappingDTO;
import com.app.payment.service.HDFCPaymentService;
import com.app.payment.service.MWalletPaymentService;
import com.app.payment.service.PayTMPaymentService;
import com.app.payment.service.PayUPaymentService;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class PaymentIntegrationPortlet
 */
@Controller(value = "PaymentController")
@RequestMapping("VIEW")
public class PaymentController extends MVCPortlet {

	private Logger logger = Logger.getLogger(PaymentController.class);

	PayUPaymentService payUPaymentService;

	MWalletPaymentService mWalletPaymentService;

	HDFCPaymentService hdfcPaymentService;

	PayTMPaymentService paytmPaymentService;

	@RenderMapping
	public String doView(RenderRequest request, RenderResponse response, Model model) throws IOException {

		String page = "CreditDebitPaymentView";
		String errMsg = "";
		boolean isFreeOrder = false;
		try {
			String deviceId = null;
			String payGateway = "";
			String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
			String payServerURL = PropsUtil.get("PAY_SERVER_URL");
			request.setAttribute("payServerUrl", payServerURL);
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(fnyTokenName)) {
						deviceId = cookies[i].getValue();
						break;
					}
				}
			}

			PaymentChannelWrapper paymentChannelWrapper = RestUtil.getPaymentChannels();
			String orderId = null;
			String pincode = RestUtil.getOrderShippingPincode(deviceId);
			OrderWrapper orderWrapper = RestUtil.createOrder(deviceId);
			//logger.info("orderWraper-" + orderWrapper);
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			String promoVal = RestUtil.getPromoCouponVal(themeDisplay);
			String eventVal = RestUtil.getRestrictEventVal(themeDisplay);
			List<PaymentChannelDTO> paymentChannels = paymentChannelWrapper.getPaymentChannels();

			/*----------------for rule validation-----------------*/
			List<PaymentChannelDTO> newPaymentChannels = RestUtil.getValidPaymentChannels(paymentChannels, deviceId,
					promoVal, eventVal);
			List<String> mode = RestUtil.getDefaultMode();
			if (!(newPaymentChannels.isEmpty()) && newPaymentChannels != null) {
				mode = RestUtil.getPaymentMode(newPaymentChannels);
				paymentChannelWrapper.setPaymentChannels(newPaymentChannels);
			}
			/*-----------------------Split Prior bank and other bank lists-----------*/

			List<PaymentChannelDTO> payChnList = paymentChannelWrapper.getPaymentChannels();
			
			List<PgBankMappingDTO> pgBankList = null;
			for (PaymentChannelDTO payDTO : payChnList) {
				if (payDTO.getPaymentChannelCode().equalsIgnoreCase("PAYUNB")) {
					pgBankList = payDTO.getPgBankMappings();
				}
				if (payDTO.getPaymentChannelCode().equalsIgnoreCase("PAYUCC")) {
					request.setAttribute("creditCardGateway", payDTO.getPaymentChannelCode());
					request.setAttribute("cardGatewayName", PaymentConstants.PAYU);
				}
				if (payDTO.getPaymentChannelCode().equalsIgnoreCase("HDFCCC")) {
					request.setAttribute("creditCardGateway", payDTO.getPaymentChannelCode());
					request.setAttribute("cardGatewayName", PaymentConstants.HDFC);
				}
			}
			if (pgBankList != null) {
				List<PgBankMappingDTO> priorBank = pgBankList.subList(0, 6);
				request.setAttribute("priorBank", priorBank);
				List<PgBankMappingDTO> otherBank = pgBankList.subList(7, pgBankList.size());
				request.setAttribute("otherBank", otherBank);
			}
			/*------------------------------------------------------------*/
			String customerPhoneNumber = RestUtil.getCustomerPhonebydeviceId(deviceId);
			//logger.info("phone number- " + customerPhoneNumber);
			if (orderWrapper != null) {
				String orderMsg = orderWrapper.getMessage();
				//logger.info("order message " + orderMsg);
				//logger.info("order response " + orderWrapper.getResponseCode());
				if (orderWrapper.getResponseCode().equalsIgnoreCase(PaymentConstants.SUCCESS)) {

					if (orderMsg.contains(",")) {
						String ordStr = orderMsg.split(",")[0];
						orderId = ordStr.split(":")[1];
						/*
						 * String isFreeStr = orderMsg.split(",")[1]; String
						 * isFreeVal = isFreeStr.split(":")[1]; isFreeOrder =
						 * new Boolean(isFreeVal);
						 */
					} else {
						orderId = orderMsg.split(":")[1];
					}
					//logger.info("orderId " + orderId);
				} else if (orderWrapper.getResponseCode().equalsIgnoreCase(PaymentConstants.SYSTEM_DROP)) {
					orderId = orderMsg.split(":")[1];
					//logger.info("order MSG " + orderWrapper.getErrorMessage());
					String errorMsg = orderWrapper.getErrorMessage();
					if (errorMsg != null) {
						if (errorMsg.equalsIgnoreCase("System Error")) {
							errMsg = "OOPS!!! Something went wrong.";
						} else {
							errMsg = errorMsg;
						}
					} else {
						errMsg = "OOPS!!! Something went wrong.";
					}
					//logger.info("orderId " + orderId);
					page = "redirectToCart";
				}
			}
			try {
				//logger.info("deviceid- " + deviceId + " orderId " + orderId);
				OrderDTO order = RestUtil.getOrderDTORewardsCredit(deviceId, orderId);
				//logger.info("get orderDTO " + order.getAmount());
				request.setAttribute("codPayVal", order.getAmount());
				BigDecimal amount = new BigDecimal(order.getAmount());
				BigDecimal credit = new BigDecimal(order.getCredits());
				BigDecimal one = new BigDecimal(1);
				int res = amount.compareTo(one);

				if (res == -1) {
					String servername = request.getServerName();
					int serverPort = request.getServerPort();
					StringBuilder sb = new StringBuilder("http://").append(servername).append(":").append(serverPort);
					String baseUrl = sb.toString();
					OrderDTO orderDTO = getCODdetails(orderId, deviceId, baseUrl);
					if (credit.compareTo(BigDecimal.ZERO) > 0) {
						page = "StoreCreditRedirect";
						orderDTO.setCredits(credit.toString());
						orderDTO.setDeviceId(deviceId);
						request.setAttribute("orderBean", orderDTO);
					}
				}
				String payOption = "2";
				if (pincode != null) {
					for (int i = 0; i < 3; i++) {
						String serVal = RestUtil.getPaymentOptionbyPincode(pincode);
						if (serVal != null || serVal != "") {
							if (serVal.equals("2") || serVal.equals("1")) {
								payOption = serVal;
								break;
							}
						}
					}
				} else {
					logger.error("Exception while getting pincode for orderId-" + orderId);
				}
				request.setAttribute("payOption", payOption);
			} catch (Exception ex) {
				logger.error("Exception in Payment Controller-", ex);
			}

			if (orderId == null || orderId.equals("")) {
				page = "createOrderFailure";
			} else {

				if (isFreeOrder) {
					String servername = request.getServerName();
					int serverPort = request.getServerPort();
					StringBuilder sb = new StringBuilder("http://").append(servername).append(":").append(serverPort);
					String baseUrl = sb.toString();
					OrderDTO orderDTO = getCODdetails(orderId, deviceId, baseUrl);
					orderDTO.setDeviceId(deviceId);
					request.setAttribute("orderBean", orderDTO);
					page = "DirectToConfirmation";
				} else {
					request.setAttribute("orderId", orderId);
					request.setAttribute("customerPhoneNumber", customerPhoneNumber);

					request.setAttribute("paymentChannelWrapper", paymentChannelWrapper);
					request.setAttribute("payGateway", payGateway);
					request.setAttribute("mode", mode);
					request.setAttribute("errorMessage", errMsg);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while executing PaymentController doView()" + e);
		}
		logger.info("page name-" + page);
		return page;
	}

	@RenderMapping(params = "action=payUForm")
	public String testRenderMethod(RenderRequest request, RenderResponse response, Model model) {

		OrderDTO order = (OrderDTO) model.asMap().get("orderBean");
		request.setAttribute("orderBean", order);
		return "CheckoutPayUPayment";
	}

	@RenderMapping(params = "action=mWalletForm")
	public String mWalletRenderMethod(RenderRequest request, RenderResponse response, Model model) {

		OrderDTO order = (OrderDTO) model.asMap().get("orderBean");
		request.setAttribute("orderBean", order);
		return "CheckoutMWalletPayment";
	}

	@RenderMapping(params = "action=CODForm")
	public String codRenderMethod(RenderRequest request, RenderResponse response, Model model) {

		OrderDTO order = (OrderDTO) model.asMap().get("orderBean");
		request.setAttribute("orderBean", order);
		return "CODRedirect";
	}

	@RenderMapping(params = "action=payTMForm")
	public String payTMRenderMethod(RenderRequest request, RenderResponse response, Model model) {

		OrderDTO order = (OrderDTO) model.asMap().get("orderBean");
		request.setAttribute("orderBean", order);
		return "CheckoutPaytmPayment";
	}

	@RenderMapping(params = "action=createOrderFailure")
	public String createOrderFailure(RenderRequest request, RenderResponse response, Model model) {
		try {

			PaymentChannelWrapper paymentChannelWrapper = RestUtil.getPaymentChannels();
			String customerPhoneNumber = RestUtil.getCustomerPhonebydeviceId(request.getParameter("deviceId"));
			request.setAttribute("orderId", request.getParameter("orderId"));
			request.setAttribute("customerPhoneNumber", customerPhoneNumber);
			request.setAttribute("paymentChannelWrapper", paymentChannelWrapper);
		} catch (Exception e) {
			logger.error("Exception while executing createOrdeFailure in PaymentController", e);
		}
		return "PaymentView";
	}

	@ActionMapping(params = "action=doPayment")
	public void processPayment(ActionRequest actionRequest, ActionResponse actionResponse, Model model)
			throws IOException {

		OrderDTO order = null;
		String orderId = actionRequest.getParameter("orderId");
		String paymentMethod = actionRequest.getParameter("paymentMethod");
		String bankCode = actionRequest.getParameter("bankCode");
		String paymentChannel = actionRequest.getParameter("paymentChannel");
		String pg = actionRequest.getParameter("pg");
		String deviceId = null;
		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
		Cookie[] cookies = actionRequest.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(fnyTokenName)) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}

		String servername = actionRequest.getServerName();
		int serverPort = actionRequest.getServerPort();
		StringBuilder sb = new StringBuilder("http://").append(servername).append(":").append(serverPort);
		String baseUrl = sb.toString();
		boolean blockInventory = RestUtil.blockInventory(deviceId, orderId);
		logger.info("Block Inventory - " + blockInventory);		
		if (!blockInventory) {
			try {

				if (paymentMethod.equals(PaymentConstants.PAYU)) {
					ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
					order = payUPaymentService.getOrderDTO(orderId, bankCode, deviceId, pg, false);
					order.setSurl(baseUrl + order.getSurl());
					order.setFurl(baseUrl + order.getFurl());
					String offerKey = RestUtil.getPayuOfferKey(themeDisplay);
					order.setOffer_key(offerKey);
					model.addAttribute("orderBean", order);
					actionResponse.setRenderParameter("action", "payUForm");

				} else if (paymentMethod.equals(PaymentConstants.PAYUMONEY)) {
					order = payUPaymentService.getOrderDTO(orderId, bankCode, deviceId, pg, false);
					order.setSurl(baseUrl + order.getSurl());
					order.setFurl(baseUrl + order.getFurl());
					model.addAttribute("orderBean", order);
					actionResponse.setRenderParameter("action", "payUForm");

				} else if (paymentMethod.equals(PaymentConstants.AMEX)) {
					order = payUPaymentService.getOrderDTO(orderId, bankCode, deviceId, pg, true);
					order.setSurl(baseUrl + order.getSurl());
					order.setFurl(baseUrl + order.getFurl());
					model.addAttribute("orderBean", order);
					actionResponse.setRenderParameter("action", "payUForm");

				} else if (paymentMethod.equals(PaymentConstants.MWALLET)) {
					order = mWalletPaymentService.getOrderDTO(orderId, deviceId);
					StringBuilder mWalletSb = new StringBuilder(baseUrl + order.getRedirecturl() + "&deviceId="
							+ deviceId);
					order.setRedirecturl(mWalletSb.toString());
					OrderDTO orderWithChecksum = mWalletPaymentService.getMwalletOrderWithChecksum(order);
					model.addAttribute("orderBean", orderWithChecksum);
					actionResponse.setRenderParameter("action", "mWalletForm");

				} else if (paymentMethod.equals(PaymentConstants.COD)) {
					order = getCODdetails(orderId, deviceId, baseUrl);
					order.setDeviceId(deviceId);
					order.setOrderId(orderId);
					model.addAttribute("orderBean", order);

					actionResponse.setRenderParameter("action", "CODForm");

				} else if (paymentMethod.equals(PaymentConstants.HDFC)) {
					order = mWalletPaymentService.getOrderDTO(orderId, deviceId);

					hdfcPaymentService.processHDFCPaymentRequest(actionRequest, actionResponse, deviceId, order,
							paymentChannel, false);

				} else if (paymentMethod.equals(PaymentConstants.PAYTM)) {
					String customerId = null;
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							if (cookies[i].getName().equals("COOKIE_FNY_CUSTOMER_ID")) {
								customerId = cookies[i].getValue();
								break;
							}
						}
					}

					order = paytmPaymentService.getOrderDTO(orderId, deviceId, customerId, baseUrl);
					model.addAttribute("orderBean", order);
					actionResponse.setRenderParameter("action", "payTMForm");
				}

			} catch (Exception e) {
				logger.error("Exception while executing orderId" + orderId + "] processPayment()", e);

				actionResponse.setRenderParameter("orderId", orderId);
				actionResponse.setRenderParameter("deviceId", deviceId);
				actionResponse.setRenderParameter("action", "createOrderFailure");

			}
		} else {
			actionResponse.sendRedirect("/cart");
		}

	}

	@ResourceMapping(value = "validateCart")
	public void validateCartOnPayment(ResourceRequest request, ResourceResponse response) throws IOException {
		/*
		 * <portlet:resourceURL var="validateCartURL" id="validateCart">
		 * </portlet:resourceURL>
		 */
		String deviceId = null;
		String paymentChannel = request.getParameter("paymentChannel");
		String cartDetails = null;
		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("FNY_TOKEN")) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject();
			JSONObject blk = JSONFactoryUtil.createJSONObject();
			obj.put("cart", blk);
			String requestJson = obj.toString();

			String requestURL = new StringBuilder(res.getString("API")).append(
					res.getString("VALIDATE_CART_ON_PAYMENT")).toString();
			String newUrl = requestURL.replace("{paymentChannel}", paymentChannel).replace("{deviceId}", deviceId);
			RestUtil.getResponseFromPostURL(newUrl, requestJson);

			String getCartUrl = new StringBuilder(res.getString("API")).append(res.getString("GET_CART")).toString();
			String cartURL = getCartUrl.replace("{deviceId}", deviceId);

			cartDetails = RestUtil.getResponsefromURL(cartURL);

		} catch (Exception e) {

			logger.error("Exception while executing validateCartOnPayment in PaymentController", e);
		}
		response.getWriter().write(cartDetails);
	}

	private OrderDTO getCODdetails(String orderId, String deviceId, String baseUrl) throws HttpResponseException,
			JSONException, IOException {

		OrderDTO order = mWalletPaymentService.getOrderDTO(orderId, deviceId);
		String redirectURL = baseUrl + "/confirmation";
		order.setUrl(redirectURL);
		order.setMessage("success");
		return order;
	}

	public PayUPaymentService getPayUPaymentService() {
		return payUPaymentService;
	}

	public void setPayUPaymentService(PayUPaymentService payUPaymentService) {
		this.payUPaymentService = payUPaymentService;
	}

	public MWalletPaymentService getmWalletPaymentService() {
		return mWalletPaymentService;
	}

	public void setmWalletPaymentService(MWalletPaymentService mWalletPaymentService) {
		this.mWalletPaymentService = mWalletPaymentService;
	}

	public HDFCPaymentService getHdfcPaymentService() {
		return hdfcPaymentService;
	}

	public void setHdfcPaymentService(HDFCPaymentService hdfcPaymentService) {
		this.hdfcPaymentService = hdfcPaymentService;
	}

	public PayTMPaymentService getPaytmPaymentService() {
		return paytmPaymentService;
	}

	public void setPaytmPaymentService(PayTMPaymentService paytmPaymentService) {
		this.paytmPaymentService = paytmPaymentService;
	}

}
