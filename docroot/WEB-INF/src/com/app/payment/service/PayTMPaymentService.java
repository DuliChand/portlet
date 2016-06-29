package com.app.payment.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.log4j.Logger;

import com.app.dto.OrderDTO;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;

public class PayTMPaymentService {

	private static final Logger logger = Logger
			.getLogger(PayTMPaymentService.class);
	private static final String PAYTM_WEBSITE = PropsUtil.get("paytm_website");
	private static final String PAYTM_MID = PropsUtil.get("paytm_mid");
	private static final String PAYTM_MERCHANT_KEY = PropsUtil
			.get("paytm_merchant_key");
	private static final String PAYTM_INDUSTRY_TYPE = PropsUtil
			.get("paytm_industry_type_id");
	private static final String PAYTM_CHANNEL_ID = PropsUtil
			.get("paytm_channel_id");
	private static final String PAYTM_URL = PropsUtil.get("paytm_url");
	private static final String PAYTM_CALL_URL = PropsUtil
			.get("paytm_callback_url");

	public OrderDTO getOrderDTO(String orderId, String deviceId,
			String customerId, String baseUrl) throws HttpResponseException,
			IOException, JSONException {

		String orderData = RestUtil.getOrderDetails(orderId, deviceId);

		OrderDTO order = getOrder(orderData, orderId, customerId, baseUrl,
				deviceId);

		return order;

	}

	private OrderDTO getOrder(String orderRespone, String orderId,
			String customerId, String baseUrl, String deviceId)
			throws JSONException {

		OrderDTO order = new OrderDTO();

		try {
			com.paytm.merchant.CheckSumServiceHelper checkSumServiceHelper = com.paytm.merchant.CheckSumServiceHelper
					.getCheckSumServiceHelper();
			StringBuilder payTmSb = new StringBuilder(baseUrl + PAYTM_CALL_URL
					+ "&deviceId=" + deviceId);
			String payTmResponseURL = payTmSb.toString();
			
			JSONObject obj = JSONFactoryUtil.createJSONObject(orderRespone);

			JSONObject dtoObj = obj.getJSONObject("domainResponse")
					.getJSONObject("entitiesResponse").getJSONObject("baseDTO");
			String amount = dtoObj.getString("totalAmountPaid");
			String email = dtoObj.getJSONObject("billingAddress").getString(
					"email");
			String phone = dtoObj.getJSONObject("billingAddress").getString(
					"mobileNo");
			TreeMap<String, String> parameters = new TreeMap<String, String>();
			String merchantKey = PAYTM_MERCHANT_KEY; // Key provided by Paytm
			parameters.put("MID", PAYTM_MID); // Merchant ID (MID)
												// provided by Paytm
			parameters.put("ORDER_ID", orderId); // Merchant’s order id
			parameters.put("CUST_ID", customerId); // Customer ID registered
													// with
													// merchant
			parameters.put("TXN_AMOUNT", amount);
			parameters.put("CHANNEL_ID", PAYTM_CHANNEL_ID);
			parameters.put("INDUSTRY_TYPE_ID", PAYTM_INDUSTRY_TYPE); // Provided
																		// by
																		// Paytm
			parameters.put("WEBSITE", PAYTM_WEBSITE); // Provided by Paytm
			parameters.put("EMAIL", email);
			parameters.put("MSISDN", phone);
			parameters.put("CALLBACK_URL", payTmResponseURL);
			
			String checkSum = checkSumServiceHelper.genrateCheckSum(
					merchantKey, parameters);

			order.setAmount(amount);
			order.setMid(PAYTM_MID);
			order.setOrderId(orderId);
			order.setCustomerId(customerId);
			order.setChannelId(PAYTM_CHANNEL_ID);
			order.setIndustryType(PAYTM_INDUSTRY_TYPE);
			order.setWebsite(PAYTM_WEBSITE);
			order.setChecksum(checkSum);
			order.setPhone(phone);
			order.setEmail(email);
			order.setUrl(PAYTM_URL + orderId);
			order.setRedirecturl(payTmResponseURL);
		} catch (Exception ex) {
			logger.error(" In PayTMPaymentService Unable to getOrderJson() ",
					ex);
		}
		return order;

	}

	public String processResponseFrmPaytm(Map<String, String> params,
			String orderId, String deviceId, boolean isOrderEligibleForCOD)
			throws JSONException, ClientProtocolException, IOException {

		String status = "";
		BigDecimal amntReceivd = BigDecimal.ZERO;
		try {
			if ((!params.get("TXNAMOUNT").equalsIgnoreCase("null"))
					&& params.get("TXNAMOUNT") != null) {
				amntReceivd = new BigDecimal(params.get("TXNAMOUNT"));
			} else {
				params.put("RESPCODE","A00");
				params.put("RESPMSG","Amount is null");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.ZERO_AMOUNT;
				return status;

			}

			BigDecimal amntSent = getSentOrderAmount(orderId, deviceId);

			if (amntReceivd.compareTo(amntSent) != 0) {
				logger.error("PayTM Amount mismatch :: sent amount ="
						+ amntSent + ", received amount =" + amntReceivd
						+ " for order : " + orderId);
				params.put("RESPCODE","A1");
				params.put("RESPMSG","sent amount ="
						+ amntSent + ", received amount =" + amntReceivd);
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.AMOUNT_MISMATCHED;
				return status;

			}

			boolean isValidChecksum = verifyCheckSum(params);
			if ("01".equalsIgnoreCase(params.get("RESPCODE"))) {
				if (isValidChecksum) {
					updateSuccessfulOrder(params, orderId);
					status = PaymentConstants.SUCCESS;
					return status;

				} else {
					logger.error("Failed to verify PayTM checksum for order : "
							+ orderId);
					params.put("RESPCODE","C1");
					params.put("RESPMSG","Failed to verify PayTM checksum");
					updateFailedOrder(params, orderId, isOrderEligibleForCOD);
					status = PaymentConstants.FAILED;
					return status;

				}
			} else {
				logger.error("Paytm payment failed for order : " + orderId
						+ " : order cancelled");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.FAILED;
				return status;

			}
		} catch (Exception e) {
			logger.error("Paytm PaymentException for order : " + orderId, e);
			params.put("RESPCODE","E1");
			params.put("RESPMSG","Exception while updating order");
			updateFailedOrder(params, orderId, isOrderEligibleForCOD);
			status = PaymentConstants.FAILED;
			return status;

		}

	}

	private boolean verifyCheckSum(Map<String, String> params) {

		boolean isValidChecksum = false;
		try {
			com.paytm.merchant.CheckSumServiceHelper checkSumServiceHelper = com.paytm.merchant.CheckSumServiceHelper
					.getCheckSumServiceHelper();

				TreeMap<String, String> parameters = new TreeMap<String, String>();
			String paytmChecksum = params.get("CHECKSUMHASH"); // Sent by Paytm
																// pg

			String merchantKey = PAYTM_MERCHANT_KEY; // Key provided by Paytm
			parameters.put("MID", PAYTM_MID); // Merchant ID (MID) sent by Paytm
			parameters.put("ORDERID", params.get("ORDERID")); // Merchant’
																//  order id
			parameters.put("TXNAMOUNT", params.get("TXNAMOUNT"));
			parameters.put("CURRENCY", params.get("CURRENCY"));
			parameters.put("TXNID", params.get("TXNID")); // Transaction id sent
															// by Paytm p

				parameters.put("BANKTXNID", params.get("BANKTXNID")); // Bank TXN id
																	// sent by
																	// Paytm p

				parameters.put("STATUS", params.get("STATUS")); // sent by Paytm pg
			parameters.put("RESPCODE", params.get("RESPCODE")); // sent by Paytm
																// pg
			parameters.put("RESPMSG", params.get("RESPMSG")); // sent by Payt
																//  pg
			parameters.put("TXNDATE", params.get("TXNDATE")); // sent by Payt
																//  pg
			parameters.put("GATEWAYNAME", params.get("GATEWAYNAME")); // sent b
																		//  Payt
																		//  pg
			parameters.put("BANKNAME", params.get("BANKNAME")); // sent by Payt
																//  pg
			parameters.put("PAYMENTMODE", params.get("PAYMENTMODE")); // sent b
																		//  Payt
																		//  p

				isValidChecksum = checkSumServiceHelper.verifycheckSum(merchantKey,
					parameters, paytmChecksum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Paytm verifyChecksum failed for order : "
					+ params.get("ORDERID") + " : order cancelled");
		}

		return isValidChecksum;
	}

	private BigDecimal getSentOrderAmount(String orderId, String deviceId)
			throws HttpResponseException, IOException, JSONException {
		String orderData = RestUtil.getOrderDetails(orderId, deviceId);
		JSONObject obj = JSONFactoryUtil.createJSONObject(orderData);

		JSONObject dtoObj = obj.getJSONObject("domainResponse")
				.getJSONObject("entitiesResponse").getJSONObject("baseDTO");
		String amount = dtoObj.getString("totalAmountPaid");
		return new BigDecimal(amount);
	}

	public String updateSuccessfulOrder(Map<String, String> params,
			String orderId) throws ClientProtocolException, IOException,
			JSONException 
		{
		return RestUtil.updatePaymentStatus(orderId, params);
	}

	public void updateFailedOrder(Map<String, String> params, String orderId,
			boolean isOrderEligibleForCOD) throws JSONException,
			ClientProtocolException, IOException {

		if (isOrderEligibleForCOD) {
			// @Todo
		} else {
			RestUtil.updatePaymentStatus(orderId, params);
		}
	}
}
