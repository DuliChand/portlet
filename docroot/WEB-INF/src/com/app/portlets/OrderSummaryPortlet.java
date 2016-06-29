package com.app.portlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.app.dto.CustomerVoucherDTO;
import com.app.util.RestUtil;
import com.app.util.ServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class OrderSummaryPortlet
 */
public class OrderSummaryPortlet extends MVCPortlet {

	private static Logger logger = Logger.getLogger(OrderSummaryPortlet.class);

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());

		String cartDetails = null;
		JSONObject detailJson = JSONFactoryUtil.createJSONObject();
		try {
			String deviceId = null;

			String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
			Cookie[] cookies = renderRequest.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(fnyTokenName)) {
						deviceId = cookies[i].getValue();
						break;
					}
				}
			}
			String cartString = RestUtil.getCartbyDeviceId(res, deviceId);

			JSONObject cartJson = JSONFactoryUtil.createJSONObject(cartString);
			detailJson.put("CartData", cartJson);
			BigDecimal rewards = ServiceUtil.getRewardsForCustomer(res, deviceId);
			detailJson.put("RewardsData", rewards.toString());
			String customerId = RestUtil.getCustomerId(renderRequest);

			if (customerId != null && customerId != "") {

				BigDecimal credits = ServiceUtil.getCustomerCreditSum(res, customerId, deviceId);
				detailJson.put("CreditsData", credits.toString());
			}
			String voucherList = ServiceUtil.getCustomerAllVouchers(res, deviceId);
			JSONObject vouchersJson = JSONFactoryUtil.createJSONObject(voucherList);
			detailJson.put("VouchersData", vouchersJson);

			String checkoutUrl = new StringBuilder(PropsUtil.get("checkout_url")).append(deviceId).toString();

			renderRequest.setAttribute("checkoutUrl", checkoutUrl);
		} catch (Exception e) {
			logger.error("Unable to execute OrderSummaryPortlet doView()", e);

		}
		cartDetails = detailJson.toString();
		renderRequest.setAttribute("cartDetails", cartDetails);

		super.doView(renderRequest, renderResponse);

	}

	public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
		String deviceId = null;

		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(fnyTokenName)) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}
		JSONObject obj = JSONFactoryUtil.createJSONObject();

		String customerId = ServiceUtil.getCustomerId(request);
		String responseString = "";

		String cartString = RestUtil.getCartbyDeviceId(res, deviceId);
		JSONObject cartJson = null;
		try {
			JSONObject cartStringJson = JSONFactoryUtil.createJSONObject(cartString);
			cartJson = cartStringJson.getJSONObject("cart");
		} catch (Exception ex) {
			logger.error("OrdersummaryPortlet unable to get cartJson", ex);
		}
		if (request.getParameter("action").equals("updatePromoCoupon")) {
			try {
				String couponVal = request.getParameter("coupon");
				String url = "APPLY_VOUCHER";
				String promotionType = "voucher";
				String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

				JSONObject blk = JSONFactoryUtil.createJSONObject();
				obj.put("cart", blk);
				String requestJson = obj.toString();

				if (couponVal != null && couponVal != "") {
					if (cartString.contains(couponVal)) {
						JSONObject erJson = JSONFactoryUtil.createJSONObject();
						erJson.put("errorMessage", "Voucher already used");
						erJson.put("responseCode", "FAILURE");
						obj.put("domainResponse", erJson);
						responseString = obj.toString();
					} else {
						responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
					}
				} else {
					JSONObject erJson = JSONFactoryUtil.createJSONObject();
					erJson.put("errorMessage", "Enter Valid Promo Code");
					erJson.put("responseCode", "FAILURE");
					obj.put("domainResponse", erJson);
					responseString = obj.toString();
				}

			} catch (Exception e) {
				logger.error("Unable to complete process PromoCode", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();

			}

			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("applyPersonalVoucher")) {
			try {
				String voucherMsg = null;
				String couponVal = request.getParameter("coupon");
				Double couponAmount = cartJson.getDouble("grandOrderValue");
				CustomerVoucherDTO custVoucher = new CustomerVoucherDTO();
				custVoucher.setVoucherCode(couponVal);
				String url = "APPLY_VOUCHER";
				String promotionType = "PersonalVoucher";
				String couponListString = ServiceUtil.getCustomerAllVouchers(res, deviceId);

				boolean cmp = false;
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(couponListString);
				List<String> nameNode = rootNode.findValuesAsText("voucherCode");

				if (nameNode != null) {
					for (String nm : nameNode) {
						if (nm.equals(couponVal)) {
							cmp = true;
							break;
						}
					}
				}
				JSONObject voucherListJson = JSONFactoryUtil.createJSONObject(couponListString);

				Object dtoObj = voucherListJson.getJSONObject("domainResponse").getJSONArray("entitiesResponse");
				if (dtoObj != null) {

					JSONArray jsAr = voucherListJson.getJSONObject("domainResponse").getJSONArray("entitiesResponse");

					for (int jr = 0; jr < jsAr.length(); jr++) {
						JSONObject jb = jsAr.getJSONObject(jr);
						JSONObject dto = jb.getJSONObject("baseDTO");
						Double amnt = dto.getDouble("minimumRequiredAmount");
						String vouch = dto.getString("voucherCode");
						if (couponAmount < amnt && couponVal.equals(vouch)) {
							voucherMsg = "Invalid Voucher code or Insufficient cart balance";
							cmp = false;
							break;
						}
					}

				} else {
					JSONObject jobj = voucherListJson.getJSONObject("domainResponse").getJSONObject("entitiesResponse")
							.getJSONObject("baseDTO");

					Double amnt = jobj.getDouble("minimumRequiredAmount");
					String vouch = jobj.getString("voucherCode");
					if (couponAmount < amnt && couponVal.equals(vouch)) {

						cmp = false;
					}
				}

				if (cmp) {
					if (cartString.contains(couponVal)) {
						JSONObject erJson = JSONFactoryUtil.createJSONObject();
						erJson.put("errorMessage", "Voucher already used");
						erJson.put("responseCode", "FAILURE");
						obj.put("domainResponse", erJson);
						responseString = obj.toString();
					} else {
						String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

						JSONObject blk = JSONFactoryUtil.createJSONObject();
						obj.put("cart", blk);
						String requestJson = obj.toString();
						responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
					}
				} else {
					JSONObject erJson = JSONFactoryUtil.createJSONObject();
					if (voucherMsg != null) {
						erJson.put("errorMessage", voucherMsg);
					} else {
						erJson.put("errorMessage", "Unable to Proceed");
					}
					erJson.put("responseCode", "FAILURE");
					obj.put("domainResponse", erJson);
					responseString = obj.toString();
				}
			} catch (Exception e) {
				logger.error("Unable to complete process apply Personal Voucher", e);

				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("applyRewardPoint")) {
			try {
				BigDecimal usedRewards = null;
				BigDecimal grandOrderValue = null;
				if (cartJson != null) {
					usedRewards = new BigDecimal(cartJson.getString("rewardAmount"));
					grandOrderValue = new BigDecimal(cartJson.getString("grandOrderValue"));
				}
				BigDecimal couponVal = new BigDecimal(request.getParameter("coupon"));
				BigDecimal rewardsSum = usedRewards.add(couponVal);

				String url = "APPLY_VOUCHER";
				String promotionType = "Reward";
				BigDecimal totalRewards = ServiceUtil.getRewardsForCustomer(res, deviceId);

				int amountComp = grandOrderValue.compareTo(couponVal);
				if (amountComp == 1 || amountComp == 0) {
					int pointStat = totalRewards.compareTo(rewardsSum);
					if (pointStat == 1 || pointStat == 0) {
						String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal.toString(),
								request);

						JSONObject blk = JSONFactoryUtil.createJSONObject();
						obj.put("cart", blk);
						String requestJson = obj.toString();
						responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
					} else {
						JSONObject erJson = JSONFactoryUtil.createJSONObject();
						erJson.put("errorMessage", "Insufficient Reward points");
						erJson.put("responseCode", "FAILURE");
						obj.put("domainResponse", erJson);
						responseString = obj.toString();

					}
				} else {
					JSONObject erJson = JSONFactoryUtil.createJSONObject();
					erJson.put("errorMessage", "Insufficient Reward points");
					erJson.put("responseCode", "FAILURE");
					obj.put("domainResponse", erJson);
					responseString = obj.toString();

				}
			} catch (Exception e) {
				logger.error("Unable to complete process apply Reward points", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();

			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("applyRewardCredits")) {
			try {
				BigDecimal usedCredits = null;
				BigDecimal grandOrderValue = null;
				if (cartJson != null) {
					usedCredits = new BigDecimal(cartJson.getString("storedCreditAmount"));
					grandOrderValue = new BigDecimal(cartJson.getString("grandOrderValue"));
				}
				BigDecimal couponVal = new BigDecimal(request.getParameter("coupon"));

				BigDecimal creditsSum = usedCredits.add(couponVal);

				String url = "APPLY_VOUCHER";
				String promotionType = "StoredCredit";
				BigDecimal totalCredits = ServiceUtil.getCustomerCreditSum(res, customerId, deviceId);

				int amountComp = grandOrderValue.compareTo(couponVal);
				if (amountComp == 1 || amountComp == 0) {
					int creditStat = totalCredits.compareTo(creditsSum);
					if (creditStat == 1 || creditStat == 0) {
						String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal.toString(),
								request);

						JSONObject blk = JSONFactoryUtil.createJSONObject();
						obj.put("cart", blk);
						String requestJson = obj.toString();
						responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
					} else {
						JSONObject erJson = JSONFactoryUtil.createJSONObject();
						erJson.put("errorMessage", "Insufficient Credits");
						erJson.put("responseCode", "FAILURE");
						obj.put("domainResponse", erJson);
						responseString = obj.toString();
					}
				} else {
					JSONObject erJson = JSONFactoryUtil.createJSONObject();
					erJson.put("errorMessage", "Insufficient Credits");
					erJson.put("responseCode", "FAILURE");
					obj.put("domainResponse", erJson);
					responseString = obj.toString();
				}
			} catch (Exception e) {
				logger.error("Unable to complete process apply Credits points", e);

				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

			/*-----------------------------------------Remove Credits, Vouchers, points---------------------------------------*/

		} else if (request.getParameter("action").equals("removeRewardCredits")) {
			try {
				String couponVal = request.getParameter("coupon");
				String url = "REMOVE_VOUCHER";
				String promotionType = "StoredCredit";
				String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

				JSONObject blk = JSONFactoryUtil.createJSONObject();
				obj.put("cart", blk);
				String requestJson = obj.toString();
				responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
			} catch (Exception e) {
				logger.error("Unable to complete remove Credits", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("removeRewardPoint")) {
			try {
				String couponVal = request.getParameter("coupon");
				String url = "REMOVE_VOUCHER";
				String promotionType = "Reward";
				String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

				JSONObject blk = JSONFactoryUtil.createJSONObject();
				obj.put("cart", blk);
				String requestJson = obj.toString();
				responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
			} catch (Exception e) {
				logger.error("Unable to complete remove Reward Points", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("removePersonalVoucher")) {
			try {
				String couponVal = request.getParameter("coupon");
				String url = "REMOVE_VOUCHER";
				String promotionType = "PersonalVoucher";

				String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

				JSONObject blk = JSONFactoryUtil.createJSONObject();
				obj.put("cart", blk);
				String requestJson = obj.toString();
				responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
			} catch (Exception e) {
				logger.error("Unable to complete remove PersonalVoucher", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("removePromoCoupon")) {
			try {
				String couponVal = request.getParameter("coupon");
				String url = "REMOVE_VOUCHER";
				String promotionType = "voucher";

				String voucherURL = ServiceUtil.getVoucherURL(res, url, promotionType, couponVal, request);

				JSONObject blk = JSONFactoryUtil.createJSONObject();
				obj.put("cart", blk);
				String requestJson = obj.toString();
				responseString = RestUtil.getResponseFromPostURL(voucherURL, requestJson);
			} catch (Exception e) {
				logger.error("Unable to complete remove Promo Coupon", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}
			response.getWriter().write(responseString);

		} else if (request.getParameter("action").equals("getOrderSummaryDetails")) {
			JSONObject detailJson = JSONFactoryUtil.createJSONObject();
			try {
				String cartStr = RestUtil.getCartbyDeviceId(res, deviceId);
				JSONObject cartJ = JSONFactoryUtil.createJSONObject(cartStr);
				detailJson.put("CartData", cartJ);
				BigDecimal rewards = ServiceUtil.getRewardsForCustomer(res, deviceId);
				detailJson.put("RewardsData", rewards.toString());
				if (customerId != null && customerId != "") {
					BigDecimal credits = ServiceUtil.getCustomerCreditSum(res, customerId, deviceId);
					detailJson.put("CreditsData", credits.toString());
				}
				String voucherList = ServiceUtil.getCustomerAllVouchers(res, deviceId);
				JSONObject vouchersJson = JSONFactoryUtil.createJSONObject(voucherList);
				detailJson.put("VouchersData", vouchersJson);
				responseString = detailJson.toString();

			} catch (Exception e) {
				logger.error("Unable to get Order Summary Details", e);
				JSONObject erJson = JSONFactoryUtil.createJSONObject();
				erJson.put("errorMessage", "Unable to process");
				erJson.put("responseCode", "FAILURE");
				obj.put("domainResponse", erJson);
				responseString = obj.toString();
			}

			response.getWriter().write(responseString);
		}

	}
}
