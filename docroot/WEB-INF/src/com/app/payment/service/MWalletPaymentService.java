package com.app.payment.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

public class MWalletPaymentService {

	protected static final Logger logger = Logger
			.getLogger(MWalletPaymentService.class);

	private static final String MWALLET_MERCHANT_NAME = PropsUtil
			.get("merchantName");
	private static final String MWALLET_REDIRECT_URL = PropsUtil
			.get("redirecturl");
	private static final String MWALLET_MERCHANT_ID = PropsUtil
			.get("mWallet_mid");
	private static final String MWALLET_SECRETKEY = PropsUtil
			.get("mWallet_secretKey");

	private static final String MWALLET_URL = PropsUtil.get("mWallet_url");
	private static final String CHECK_API_URL = PropsUtil.get("checkAPIURL");

	public OrderDTO getOrderDTO(String orderId, String deviceId)
			throws HttpResponseException, IOException, JSONException {

		String orderData = RestUtil.getOrderDetails(orderId, deviceId);

		OrderDTO order = getOrderJson(orderData, orderId);

		return order;

	}

	private OrderDTO getOrderJson(String orderRespone, String orderId)
			throws JSONException {
		JSONObject obj = JSONFactoryUtil.createJSONObject(orderRespone);
		JSONObject dtoObj = obj.getJSONObject("domainResponse")
				.getJSONObject("entitiesResponse").getJSONObject("baseDTO");

		OrderDTO orderDTO = new OrderDTO();
		try {
			String amount = dtoObj.getString("totalAmountPaid");

			String email = dtoObj.getJSONObject("customer")
					.getString("loginId");
			String phone = dtoObj.getJSONObject("billingAddress").getString(
					"mobileNo");
			String productInfo = "testProduct";
			String grandPrice = dtoObj.getString("grandOrderValue");

			String quantity = dtoObj.getString("totalOrderItems");

			orderDTO.setMerchantName(MWALLET_MERCHANT_NAME);
			orderDTO.setmWalletUrl(MWALLET_URL);
			orderDTO.setMid(MWALLET_MERCHANT_ID);
			orderDTO.setRedirecturl(MWALLET_REDIRECT_URL);
			orderDTO.setEmail(email);
			orderDTO.setAmount(amount);
			orderDTO.setPhone(phone);
			orderDTO.setOrderId(orderId);
			orderDTO.setProductinfo(productInfo);
			orderDTO.setQuantity(quantity);
			orderDTO.setGrandPrice(grandPrice);
		} catch (Exception e) {
			logger.error("----Inside Get Order Json----", e);
		}
		return orderDTO;

	}

	public String processResponseFrmMWallet(Map<String, String> params,
			String orderId, String deviceId, boolean isOrderEligibleForCOD)
			throws JSONException, ClientProtocolException, IOException,
			InvalidKeyException, NoSuchAlgorithmException {

		String status = "";
		BigDecimal amntReceivd = BigDecimal.ZERO;
		
		try {
			if ((!params.get("amount").equalsIgnoreCase("null"))
					&& params.get("amount") != null) {
				amntReceivd = new BigDecimal(params.get("amount"));
			} else {
				logger.error("MWallet - Response Amount recieved ZERO for order : "
						+ orderId);
				params.put("statuscode","A00");
				params.put("statusmessage","Response Amount recieved ZERO");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.ZERO_AMOUNT;
				return status;

			}

			BigDecimal amntSent = getSentOrderAmount(orderId, deviceId);

			if (amntReceivd.compareTo(amntSent) != 0) {
				logger.error("MWallet - Response Amount mismatch for order : "
						+ orderId);
				params.put("statuscode","A1");
				params.put("statusmessage","Amount Mistmatch");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.AMOUNT_MISMATCHED;
				return status;

			}
			final String responseChecksumString = "'"
					+ params.get("statuscode") + "''" + params.get("orderid")
					+ "''" + params.get("amount") + "''"
					+ params.get("statusmessage") + "''" + params.get("mid")
					+ "'";
			final String responseChecksum = getmWalletChecksum(responseChecksumString);
			if (!responseChecksum.equals(params.get("checksum"))) {
				logger.error("MWallet - Response checksum mismatch for order : "
						+ orderId);
				params.put("statuscode","C1");
				params.put("statusmessage","Response checksum mismatch");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.FAILED;
				return status;

			}
			if ("0".equalsIgnoreCase(params.get("statuscode"))) {

				if (isStatusRechecked(params, orderId)) {
					updateSuccessfulOrder(params, orderId);
					status = PaymentConstants.SUCCESS;
					return status;

				} else {
					params.put("statuscode","S1");
					params.put("statusmessage","Status Rechecked Failed");
					updateFailedOrder(params, orderId, isOrderEligibleForCOD);
					status = PaymentConstants.FAILED;
					return status;

				}
			} else {
				logger.error("mwallet payment failed for order : " + orderId
						+ " : order cancelled");
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				status = PaymentConstants.FAILED;
				return status;

			}
		} catch (Exception e) {
			logger.error("Mwallet PaymentException for order : " + orderId, e);
			params.put("statuscode","E1");
			params.put("statusmessage","Exception while updating order");
			updateFailedOrder(params, orderId, isOrderEligibleForCOD);
			status = PaymentConstants.FAILED;
			return status;

		}
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
			JSONException {
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

	private boolean isStatusRechecked(Map<String, String> params, String orderId)
			throws InvalidKeyException, NoSuchAlgorithmException {
		final String mId = MWALLET_MERCHANT_ID;
		final String requestCheckSumString = "'" + mId + "''" + orderId + "'";
		final String calculatedRequestChecksum = getmWalletChecksum(requestCheckSumString);
		final String checkAPIURL = CHECK_API_URL + "?mid=" + mId + "&orderid="
				+ orderId + "&checksum=" + calculatedRequestChecksum;
		StringBuilder responseXML = new StringBuilder();

		try {
			URL url = new URL(checkAPIURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				responseXML.append(line);
			}
			br.close();

			Pattern statusCodePattern = Pattern
					.compile("<statuscode>(.*?)</statuscode>");
			Pattern amountPattern = Pattern.compile("<amount>(.*?)</amount>");
			Pattern checksumPattern = Pattern
					.compile("<checksum>(.*?)</checksum>");
			Pattern orderIdPattern = Pattern
					.compile("<orderid>(.*?)</orderid>");
			Pattern refIdPattern = Pattern.compile("<refid>(.*?)</refid>");
			Pattern statusMessagePattern = Pattern
					.compile("<statusmessage>(.*?)</statusmessage>");
			Pattern orderTypePattern = Pattern
					.compile("<ordertype>(.*?)</ordertype>");
			Matcher statusCodeMatcher = statusCodePattern.matcher(responseXML);
			Matcher amountPatternMatcher = amountPattern.matcher(responseXML);
			Matcher checksumPatternMatcher = checksumPattern
					.matcher(responseXML);
			Matcher orderIdPatternMatcher = orderIdPattern.matcher(responseXML);
			Matcher refIdPatternMatcher = refIdPattern.matcher(responseXML);
			Matcher statusMessagePatternMatcher = statusMessagePattern
					.matcher(responseXML);
			Matcher orderTypePatternMatcher = orderTypePattern
					.matcher(responseXML);
			String statusCode = "";
			String amount = "";
			String checksum = "";
			String resOrderId = "";
			String refId = "";
			String statusMessage = "";
			String orderType = "";
			while (statusCodeMatcher.find()) {
				statusCode = statusCodeMatcher.group(1);
			}
			while (amountPatternMatcher.find()) {
				amount = amountPatternMatcher.group(1);
			}
			while (checksumPatternMatcher.find()) {
				checksum = checksumPatternMatcher.group(1);
			}
			while (orderIdPatternMatcher.find()) {
				resOrderId = orderIdPatternMatcher.group(1);
			}
			while (refIdPatternMatcher.find()) {
				refId = refIdPatternMatcher.group(1);
			}
			while (statusMessagePatternMatcher.find()) {
				statusMessage = statusMessagePatternMatcher.group(1);
			}
			while (orderTypePatternMatcher.find()) {
				orderType = orderTypePatternMatcher.group(1);
			}

			BigDecimal amountRecParams = new BigDecimal(params.get("amount"));
			BigDecimal amountRecXML = new BigDecimal(amount);
			final String responseCheckSumString = "'" + statusCode + "''"
					+ resOrderId + "''" + refId + "''" + amount + "''"
					+ statusMessage + "''" + orderType + "'";
			final String calculatedResponseChecksum = getmWalletChecksum(responseCheckSumString);
			if (statusCode.equalsIgnoreCase("0")
					&& amountRecXML.compareTo(amountRecParams) == 0
					&& checksum.equals(calculatedResponseChecksum)) {
				return true;
			} else {
				return false;
			}

		} catch (MalformedURLException e) {
			logger.error("MWallet Check API URL error" + e);
			return false;
		} catch (IOException e) {
			logger.error("MWallet Check API Response error" + e);
			return false;
		} catch (Exception e) {
			logger.error("MWallet Check API Response XML error" + e);
			return false;
		}
	}

	public OrderDTO getMwalletOrderWithChecksum(OrderDTO order)
			throws InvalidKeyException, NoSuchAlgorithmException {
		// Merchant ID given by MobiKwik
		String merchantId = order.getMid();
		String orderId = order.getOrderId();
		String check_sum_string = "'" + order.getPhone() + "''"
				+ order.getEmail() + "''" + order.getAmount() + "''" + orderId
				+ "''" + order.getRedirecturl() + "''" + merchantId + "'";
		String checksum = getmWalletChecksum(check_sum_string);
		order.setmWalletUrl(MWALLET_URL);
		order.setmWalletChecksum(checksum);
		return order;
	}

	public String getmWalletChecksum(String checksumString)
			throws NoSuchAlgorithmException, InvalidKeyException {
		// Merchant Secret Key given by MobiKwik
		String secretKey = MWALLET_SECRETKEY;
		SecretKeySpec secret = new SecretKeySpec(secretKey.getBytes(),
				"HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(secret);
		byte[] digest = mac.doFinal(checksumString.getBytes());
		String checksum = "";
		for (byte b : digest) {
			checksum = checksum + String.format("%02x", b);
		}
		return checksum;

	}

}