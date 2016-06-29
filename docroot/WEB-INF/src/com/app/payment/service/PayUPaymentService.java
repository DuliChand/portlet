package com.app.payment.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.app.dto.OrderDTO;
import com.app.util.HttpConnection;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;

public class PayUPaymentService {

	protected static final Logger LOGGER = Logger.getLogger(PayUPaymentService.class);
	private static final String SURL = PropsUtil.get("payU_surl");
	private static final String FURL = PropsUtil.get("payU_furl");
	private static final String MERCHANT_KEY = PropsUtil.get("payU_merchant_key");
	private static final String SALT = PropsUtil.get("payU_salt");
	private static final String PAYU_VERIFY_PAYMENT_URL = PropsUtil.get("payU_verify_payment_url");
	private static String ACTION = PropsUtil.get("payU_action1");
	private static final String BASE_URL = PropsUtil.get("payU_base_url");
	private static final String DROP_CATEGORY = PropsUtil.get("payU_drop_category");

	// AMEX parameters
	private final String VPC_USER = PropsUtil.get("vpc_User");
	private final String VPC_MERCHANT = PropsUtil.get("vpc_Merchant");
	private final String VPC_ACCESSCODE = PropsUtil.get("vpc_AccessCode");
	private final String SECURESECRET = PropsUtil.get("securesecret");

	public OrderDTO getOrderDTO(String orderId, String bankCode, String deviceId, String pg, boolean isAmex)
			throws HttpResponseException, IOException, JSONException {
		String orderData = RestUtil.getOrderDetails(orderId, deviceId);
		OrderDTO order = getOrderJson(orderData, orderId, bankCode, deviceId, pg, isAmex);
		return order;
	}

	private OrderDTO getOrderJson(String orderRespone, String orderId, String bankCode, String deviceId, String pg,
			boolean isAmex) throws JSONException {

		OrderDTO order = null;
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(orderRespone);

			JSONObject dtoObj = obj.getJSONObject("domainResponse").getJSONObject("entitiesResponse")
					.getJSONObject("baseDTO");
			String amount = dtoObj.getString("totalAmountPaid");
			String fullname = dtoObj.getJSONObject("billingAddress").getString("fullName");
			String[] name = fullname.split(" ");
			String firstname = name[0];
			String lastname = name[1];
			String email = dtoObj.getJSONObject("billingAddress").getString("email");
			String phone = dtoObj.getJSONObject("billingAddress").getString("mobileNo");
			String productinfo = "product";
			String udf2 = deviceId; // ---------udf2 used for order
									// deviceId--------------------------
			String udf4 = "userdefined4"; // ---------udf4 used to check whether
											// order is valid for COD or
											// not---------

			Map<String, String> optionalParams = new HashMap<String, String>();
			optionalParams.put("lastname", lastname);
			optionalParams.put("email", email);
			optionalParams.put("phone", phone);
			Map<String, String> params = new HashMap<String, String>();
			params.put("key", MERCHANT_KEY);
			params.put("firstname", firstname);
			params.put("productinfo", productinfo);
			params.put("amount", amount);
			params.put("surl", SURL);
			params.put("furl", FURL);
			params.put("txnid", orderId);
			params.put("udf2", udf2);
			params.put("udf4", udf4);
			params.put("pg", pg);
			params.put("bankCode", bankCode);

			String checkSum = generateChecksumforRequest(params, Long.parseLong(orderId));

			order = buildOrderDTO(params, optionalParams, checkSum, isAmex);
		} catch (Exception ex) {
			LOGGER.error(" In PayUPaymentService Unable to getOrderJson() ", ex);
		}
		return order;

	}

	private OrderDTO buildOrderDTO(Map<String, String> requestParams, Map<String, String> optionalParams,
			String checkSum, boolean isAmex) {
		OrderDTO order = new OrderDTO();
		try {
			order.setKey(MERCHANT_KEY);
			order.setAmount(requestParams.get("amount"));
			order.setFirstname(requestParams.get("firstname"));
			order.setLastName(optionalParams.get("lastname"));
			order.setEmail(optionalParams.get("email"));
			order.setPhone(optionalParams.get("phone"));
			order.setProductinfo(requestParams.get("productinfo"));
			order.setUdf2(requestParams.get("udf2"));
			order.setUdf4(requestParams.get("udf4"));
			order.setSurl(SURL);
			order.setFurl(FURL);
			order.setTxnid(requestParams.get("txnid"));
			order.setDrop_category(DROP_CATEGORY);
			order.setHash(checkSum);
			order.setUrl(ACTION);
			order.setPg(requestParams.get("pg"));
			order.setBankCode(requestParams.get("bankCode"));

			if (isAmex) {
				order.setVpc_User(VPC_USER);
				order.setVpc_Merchant(VPC_MERCHANT);
				order.setVpc_AccessCode(VPC_ACCESSCODE);
				order.setSecuresecret(SECURESECRET);
				order.setEnforceMethod("AMEX");
			}
		} catch (Exception ex) {
			LOGGER.error(" In PayUPaymentService Unable to buildOrderDTO() ", ex);
		}
		return order;
	}

	public String generateChecksumforRequest(Map<String, String> requestParams, long orderId) {
		String hashString = "";
		String hash = null;

		/* Do no change this sequence */
		String hashSequenceRequest = "key|txnid|amount|productinfo|firstname||udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";

		if (StringUtils.isBlank(requestParams.get("key"))
				|| StringUtils.isBlank(requestParams.get("txnid"))
				|| StringUtils.isBlank(requestParams.get("amount"))
				|| StringUtils.isBlank(requestParams.get("firstname"))
				// || StringUtils.isBlank(requestParams.get("email"))
				// || StringUtils.isBlank(requestParams.get("phone"))
				|| StringUtils.isBlank(requestParams.get("productinfo"))
				|| StringUtils.isBlank(requestParams.get("surl")) || StringUtils.isBlank(requestParams.get("furl"))
				|| StringUtils.isBlank(requestParams.get("udf2")) || StringUtils.isBlank(requestParams.get("udf4"))) {
			LOGGER.error("insufficient parameters to generate checksum :: orderId : " + orderId);
		} else {
			String[] hashVarSeq = hashSequenceRequest.split("\\|");
			for (String part : hashVarSeq) {
				hashString = (StringUtils.isBlank(requestParams.get(part))) ? hashString.concat("") : hashString
						.concat(requestParams.get(part));
				hashString = hashString.concat("|");
			}
			hashString = hashString.concat(SALT);
			LOGGER.info("hash string: "+hashString);
			hash = hashCal("SHA-512", hashString);
			LOGGER.info("hash value: "+hash);
		}
		ACTION = BASE_URL.concat("/_payment");
		return hash;

	}

	public String hashCal(String type, String str) {
		byte[] hashseq = str.getBytes();
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i = 0; i < messageDigest.length; i++) {
				String hex = Integer.toHexString(0xFF & messageDigest[i]);
				if (hex.length() == 1)
					hexString.append("0");
				hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException nsae) {
			LOGGER.error("Failed to generate hash", nsae);
		}
		return hexString.toString();
	}

	public boolean verifyChecksum(Map<String, String> params, long orderId) throws Exception {
		String newHash = "";
		// String salt = null;// _faysc.getPayuSalt();

		final String hashSequenceResponse = "status||||||udf5|udf4|udf3|udf2|udf1||firstname|productinfo|amount|txnid|key";
		String hashString = "";

		if (StringUtils.isBlank(params.get("key")) || StringUtils.isBlank(params.get("txnid"))
				|| StringUtils.isBlank(params.get("amount")) || StringUtils.isBlank(params.get("firstname"))
				// || StringUtils.isBlank(params.get("email"))
				|| StringUtils.isBlank(params.get("status")) || StringUtils.isBlank(params.get("productinfo"))
				|| StringUtils.isBlank(params.get("hash"))) {
			LOGGER.error("insufficient parameters to generate checksum::verification of hash failed for oder : "
					+ orderId);
			return false;
		} else {
			String[] hashVarSeq = hashSequenceResponse.split("\\|");
			hashString = SALT;
			for (String part : hashVarSeq) {
				hashString = hashString.concat("|");
				hashString = (StringUtils.isBlank(params.get(part))) ? hashString.concat("") : hashString.concat(params
						.get(part));
			}
			newHash = hashCal("SHA-512", hashString);
		}
		return newHash.equals(params.get("hash"));
	}

	public String updateSuccessfulOrder(Map<String, String> params, String orderId) throws ClientProtocolException,
			IOException, JSONException {
		return RestUtil.updatePaymentStatus(orderId, params);
	}

	public void updateFailedOrder(Map<String, String> params, String orderId, boolean isOrderEligibleForCOD)
			throws JSONException, ClientProtocolException, IOException {

		if (isOrderEligibleForCOD) {
			// @Todo
		} else {
			RestUtil.updatePaymentStatus(orderId, params);
		}
	}

	public String processResponseFrmPayU(Map<String, String> params, String orderId, boolean isOrderEligibleForCOD)
			throws Exception {

		String status = "";
		String deviceId = params.get("udf2");

		try {
			if (!verifyChecksum(params, Long.parseLong(orderId))) {
				LOGGER.error("Failed to verify PayU checksum for order : " + orderId);

				params.put("error", "verify checksum failed");
				params.put("error_Message", "Failed to verify PayU checksum");
				params.put("status", PaymentConstants.FAILED);

				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.CHECKSUM_VERIFY_FAILED;
				return status;
			}
			BigDecimal amntReceivd = new BigDecimal(params.get("amount"));
			BigDecimal amntSent = getSentOrderAmount(orderId, deviceId);

			if (amntReceivd.compareTo(amntSent) != 0) {
				LOGGER.error("PayU Amount mismatch :: sent amount =" + amntSent + ", received amount =" + amntReceivd
						+ " for order : " + orderId);

				params.put("error", PaymentConstants.AMOUNT_MISMATCHED);
				params.put("error_Message", "sent amount =" + amntSent + ", received amount =" + amntReceivd);
				params.put("status", PaymentConstants.FAILED);

				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.AMOUNT_MISMATCHED;
				return status;
			}

			if (PaymentConstants.SUCCESS.equalsIgnoreCase(params.get("status"))) { //

				String resp = verifyPayuPayment(orderId);
				if (resp.equalsIgnoreCase(PaymentConstants.SUCCESS)) {
					params.put("status", PaymentConstants.SUCCESS);
					status = PaymentConstants.SUCCESS;
				} else {
					params.put("status", PaymentConstants.PAYU_VERIFICATION_FAILED);
					params.put("error_Message",
							"Transaction status is Failed while verifying PayU transaction using PayU-API");
					status = PaymentConstants.FAILED;
				}
				updateSuccessfulOrder(params, orderId);
				return status;
			} else {
				LOGGER.error("PayU payment failed for order : " + orderId + " : order cancelled");

				params.put("status", PaymentConstants.FAILED);
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.FAILED;
				return status;
			}

		} catch (Exception e) {
			LOGGER.error("Got Exception in processResponseFrmPayU() for[" + orderId + "]", e);
			params.put("error_Message", "Exception occur while updating");
			params.put("status", PaymentConstants.FAILED);
			updateFailedOrder(params, orderId, isOrderEligibleForCOD);
			status = PaymentConstants.FAILED;
			return status;
		}

	}

	private BigDecimal getSentOrderAmount(String orderId, String deviceId) throws HttpResponseException, IOException,
			JSONException {
		String orderData = RestUtil.getOrderDetails(orderId, deviceId);
		JSONObject obj = JSONFactoryUtil.createJSONObject(orderData);

		JSONObject dtoObj = obj.getJSONObject("domainResponse").getJSONObject("entitiesResponse")
				.getJSONObject("baseDTO");
		String amount = dtoObj.getString("totalAmountPaid");
		return new BigDecimal(amount);
	}

	public String updateCODorderPayment(Map<String, String> params, String orderId) throws JSONException,
			ClientProtocolException, IOException {
		return updateSuccessfulOrder(params, orderId);
	}

	private String verifyPayuPayment(String orderId) throws IOException, ClientProtocolException, InterruptedException {

		CloseableHttpResponse response = null;
		String responseString = null;
		String status = null;
		ArrayList<NameValuePair> postParameters;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(PAYU_VERIFY_PAYMENT_URL);
			postParameters = new ArrayList<NameValuePair>();
			String key = MERCHANT_KEY;
			String salt = SALT;
			String command = "verify_payment";
			String hashString = key + "|" + command + "|" + orderId + "|" + salt;
			String hash = hashCal("SHA-512", hashString);
			postParameters.add(new BasicNameValuePair("key", key));
			postParameters.add(new BasicNameValuePair("hash", hash));
			postParameters.add(new BasicNameValuePair("var1", orderId));
			postParameters.add(new BasicNameValuePair("command", command));
			request.setEntity(new UrlEncodedFormEntity(postParameters));
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);

			JSONObject obj = JSONFactoryUtil.createJSONObject(responseString);

			String resp = obj.getString("status");
			JSONObject transactionDetails = null;
			if (resp.equals("1")) {
				transactionDetails = obj.getJSONObject("transaction_details").getJSONObject(orderId);
				if (transactionDetails != null) {
					String payStatus = transactionDetails.getString("status");
					status = payStatus;
				} else {
					transactionDetails = obj.getJSONObject("transaction_details");
					String payStatus = transactionDetails.getString("status");
					status = payStatus;
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Failed to execute verifyPayuPayment() for order:" + orderId);
			LOGGER.error("Exception while executing verifyPayuPayment()", ex);
			status= PaymentConstants.FAILED;
		} finally {
			response.close();
		}

		return status;
	}

}
