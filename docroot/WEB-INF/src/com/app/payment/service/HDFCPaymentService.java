package com.app.payment.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.app.dto.OrderDTO;
import com.app.util.PaymentConstants;
import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.util.PortalUtil;

public class HDFCPaymentService {

	protected static final Logger logger = Logger
			.getLogger(HDFCPaymentService.class);

	private static final String HDFC_URL = PropsUtil.get("hdfc_url");
	private static final String HDFC_RESPONSE_URL = PropsUtil
			.get("hdfc_reponse_url");
	private static final String HDFC_REDIRECT_URL = PropsUtil
			.get("hdfc_redirect_url");
	private static final String HDFC_MERCHANT_ID = PropsUtil
			.get("hdfc_merchantId");
	private static final String HDFC_PASSWORD = PropsUtil.get("hdfc_password");
	private static final String HDFC_ACTION = PropsUtil.get("hdfc_action");
	private static final String HDFC_CURRENCY_CODE = PropsUtil
			.get("hdfc_currency_code");
	private static final String HDFC_LANG_ID = PropsUtil.get("hdfc_lang_id");
	private static final String HDFC_RESPONSE_IP_ONE = PropsUtil
			.get("hdfc_response_ip_one");
	private static final String HDFC_RESPONSE_IP_TWO = PropsUtil
			.get("hdfc_response_ip_two");
	private static final String HDFC_RESPONSE_IP_THREE = PropsUtil
			.get("hdfc_response_ip_three");

	public void processHDFCPaymentRequest(ActionRequest actionRequest,
			ActionResponse actionResponse, String deviceId, OrderDTO order,
			String paymentChannel, boolean b) {
		String action = HDFC_ACTION;
		String amt = order.getAmount();
		String currencycode = HDFC_CURRENCY_CODE;
		String trackid = order.getOrderId(); // ORDER-ID
		String langid = HDFC_LANG_ID;
		String responseURL = getHDFCResponseUrl(actionRequest);
		String errorURL = getHDFCResponseUrl(actionRequest);
		String udf1 = "false"; // order eligible for COD
		String udf2 = order.getEmail(); // login email name
		String udf3 = "HDFC"; // order account Id
		String udf4 = deviceId; // deviceId
		String udf5 = getRequestHash(trackid, amt, currencycode, action);
		
		String tranRequest = "id=" + HDFC_MERCHANT_ID + "&password="
				+ HDFC_PASSWORD + "&action=" + action + "&langid=" + langid
				+ "&currencycode=" + currencycode + "&amt=" + amt
				+ "&responseURL=" + responseURL + "&errorURL=" + errorURL
				+ "&trackid=" + trackid + "&udf1=" + udf1 + "&udf2=" + udf2
				+ "&udf3=" + udf3 + "&udf4=" + udf4 + "&udf5=" + udf5;
		createHDFCConnection(actionRequest, actionResponse, tranRequest,
				trackid, amt);
	}

	public String getRequestHash(String trackId, String amt,
			String currencycode, String action) {

		final String hashString = HDFC_MERCHANT_ID.trim() + trackId.trim()
				+ amt.trim() + currencycode.trim() + action.trim();

		return getSHA256(hashString);
	}

	public String getSHA256(String str) {
		StringBuffer strhash = new StringBuffer();
		try {
			String message = str;
			MessageDigest messagedigest = MessageDigest.getInstance("SHA-256");
			messagedigest.update(message.getBytes());
			byte digest[] = messagedigest.digest();
			strhash = new StringBuffer(digest.length * 2);
			int length = digest.length;
			for (int n = 0; n < length; n++) {
				int number = digest[n];
				if (number < 0) {
					number = number + 256;
				}
				String str1 = "";
				if (Integer.toString(number, 16).length() == 1) {
					str1 = "0" + String.valueOf(Integer.toString(number, 16));
				} else {
					str1 = String.valueOf(Integer.toString(number, 16));
				}
				strhash.append(str1);
			}
		} catch (Exception e) {
			logger.error("Error Generating Checksum Hash for HDFC gateway", e);
		}
		return strhash.toString();
	}

	private void createHDFCConnection(ActionRequest actionRequest,
			ActionResponse actionResponse, String tranRequest, String trackid,
			String amt) {
		String paymentId = null;
		String paymentPage = null;
		String TranResponse = null;
		try {
			URL url = new URL(HDFC_URL);
			Object obj;
			obj = (HttpsURLConnection) url.openConnection(); // create a SSL
																// connection
																// object
																// server-to-server
			((URLConnection) obj).setDoInput(true);
			((URLConnection) obj).setDoOutput(true);
			((URLConnection) obj).setUseCaches(false);
			((URLConnection) obj).setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			if (tranRequest.length() > 0) {
				// Here the HTTPS request URL is created
				DataOutputStream dataoutputstream = new DataOutputStream(
						((URLConnection) obj).getOutputStream());
				dataoutputstream.writeBytes(tranRequest); // here the request is
															// sent to payment
															// gateway
				dataoutputstream.flush();
				dataoutputstream.close(); // connection closed

				BufferedReader bufferedreader = new BufferedReader(
						new InputStreamReader(
								((URLConnection) obj).getInputStream()));
				TranResponse = bufferedreader.readLine(); // Payment Gateway
															// response is read
				String ErrorCheck = null;
				try {
					ErrorCheck = getTextBetweenTags(TranResponse, "!", "!-");// This
																				// line
																				// will
																				// find
																				// Error
																				// Keyword
																				// in
																				// TranResponse
				} catch (Exception e) {
					logger.error(
							"Error Message received in first request hit at HDFC",
							e);
					ErrorCheck = "";
				}

				if (!ErrorCheck.equals("ERROR")) {
					// This block will check for Error in TranResponce
					// If Payment Gateway response has Payment ID & Pay page URL
					int index = TranResponse.indexOf(":");
					int size = TranResponse.length();
					// Merchant MUST map (update) the Payment ID received with
					// the merchant Track Id in his database at this place.
					paymentId = TranResponse.substring(0, index);
					paymentPage = TranResponse.substring(index + 1, size);
					// here redirecting the customer browser from ME site to
					// Payment Gateway Page with the Payment ID
					actionResponse
							.sendRedirect(actionResponse.encodeURL(paymentPage
									+ "?PaymentID=" + paymentId));
				} else {
					// here redirecting the error page
					logger.error("HDFCPaymentService redirecting to ErrorURL");
					actionResponse
							.sendRedirect(getHDFCResponseUrl(actionRequest)
									+ "?ResError=" + TranResponse
									+ "&ResTrackId=" + trackid + "&ResAmount="
									+ amt);
				}
			}
		} catch (Exception localException) {
			// here redirecting the error page
			try {
				actionResponse.sendRedirect(getHDFCResponseUrl(actionRequest)
						+ "?ResError=" + localException.getMessage()
						+ TranResponse + "&ResTrackId=" + trackid
						+ "&ResAmount=" + amt);
			} catch (IOException e) {
				logger.error("Error in redirecting to HDFC Page", e);
			}
		}
	}

	public String getHDFCResponseUrl(ActionRequest actionRequest) {

		String serverName = actionRequest.getServerName();
		int serverPort = actionRequest.getServerPort();
		StringBuilder sb = new StringBuilder("http://").append(serverName)
				.append(":").append(serverPort).append(HDFC_RESPONSE_URL);
		String baseUrl = sb.toString();
		return baseUrl;
	}

	public String getHDFCRedirectUrl(RenderRequest renderRequest) {

		String serverName = renderRequest.getServerName();
		int serverPort = renderRequest.getServerPort();
		StringBuilder sb = new StringBuilder("http://").append(serverName)
				.append(":").append(serverPort).append(HDFC_REDIRECT_URL);
		String baseUrl = sb.toString();
		return baseUrl;
	}

	// Method for fetching if response is having any Error
	public String getTextBetweenTags(String InputText, String Tag1, String Tag2) {
		String Result = "";
		if (InputText.contains(Tag1)) {
			int index1 = InputText.indexOf(Tag1);
			int index2 = InputText.indexOf(Tag2);
			index1 = index1 + Tag1.length();
			Result = InputText.substring(index1, index2);
		}
		return Result;
	}

	public String processResponseFrmHDFC(RenderRequest request,
			Map<String, String> params, OrderDTO order,
			boolean isOrderEligibleForCOD) throws JSONException,
			ClientProtocolException, IOException {
		HttpServletRequest req = PortalUtil.getHttpServletRequest(request);
		boolean udf1 = false;
		String orderId = params.get("trackid");
		BigDecimal amntReceived = BigDecimal.ZERO;
		BigDecimal amntSent = new BigDecimal(order.getAmount());
		if (params.get("amt") != null) {
			amntReceived = new BigDecimal(params.get("amt").replace(",", ""));
		}
		String resPaymentId, resErrorText, resResult, resTranId, resAuth, resAmount, resErrorNo, resTrackID, resRef, resudf5;
		String strResponseIPAdd = req.getHeader("X-Forwarded-For");
		if (strResponseIPAdd == null || strResponseIPAdd.length() == 0
				|| "unknown".equalsIgnoreCase(strResponseIPAdd)) {
			strResponseIPAdd = req.getHeader("Proxy-Client-IP");
		}
		if (strResponseIPAdd == null || strResponseIPAdd.length() == 0
				|| "unknown".equalsIgnoreCase(strResponseIPAdd)) {
			strResponseIPAdd = req.getHeader("WL-Proxy-Client-IP");
		}
		if (strResponseIPAdd == null || strResponseIPAdd.length() == 0
				|| "unknown".equalsIgnoreCase(strResponseIPAdd)) {
			strResponseIPAdd = req.getHeader("HTTP_CLIENT_IP");
		}
		if (strResponseIPAdd == null || strResponseIPAdd.length() == 0
				|| "unknown".equalsIgnoreCase(strResponseIPAdd)) {
			strResponseIPAdd = req.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (strResponseIPAdd == null || strResponseIPAdd.length() == 0
				|| "unknown".equalsIgnoreCase(strResponseIPAdd)) {
			strResponseIPAdd = req.getRemoteAddr();
		}

		if (params.get("ResError") != null) {
			final String resError = params.get("ResError");
			String ErrorCheck = getTextBetweenTags(resError, "!", "!-");
			if (ErrorCheck.equals("ERROR")) {
				logger.error("Error Message in first response from HDFC for Order:"
						+ orderId + " :Error: " + resError);
				updateFailedOrder(params, orderId, udf1);
				return PaymentConstants.FAILED;
			}

		} else if (!strResponseIPAdd.equals(HDFC_RESPONSE_IP_ONE)
				&& !strResponseIPAdd.equals(HDFC_RESPONSE_IP_TWO)
				&& !strResponseIPAdd.equals(HDFC_RESPONSE_IP_THREE)) {
			logger.error("Failure due to HDFC response IP mismatch for Order:"
					+ orderId + " Received IP:" + strResponseIPAdd + " Port:"
					+ req.getRemotePort());
			updateFailedOrder(params, orderId, udf1);
			return PaymentConstants.FAILED;
		} else if (amntSent.compareTo(amntReceived) != 0) {
			logger.error("Sent and Received amount mismatch for Order:"
					+ orderId);
			updateFailedOrder(params, orderId, udf1);
			return PaymentConstants.FAILED;
		} else {
			resErrorText = params.get("ErrorText"); // Error Text/message
			resPaymentId = params.get("paymentid"); // Payment Id
			resTrackID = params.get("trackid"); // Merchant Track ID or order ID
			resErrorNo = params.get("Error"); // Error Number
			resResult = params.get("result"); // Transaction Result
			resTranId = params.get("tranid"); // Transaction ID
			resAuth = params.get("auth"); // Auth Code
			resRef = params.get("ref"); // Reference Number also called Seq
										// Number
			resAmount = params.get("amt"); // Transaction Amount
			resudf5 = params.get("udf5"); // UDF5
			// resudf1 = params.get("udf1"); // order eligible for COD

			if (resErrorNo == null) {
				String strHashTraportalID = HDFC_MERCHANT_ID;
				String strhashstring = "";
				strhashstring = strHashTraportalID.trim();

				if (resTrackID != null && !resTrackID.trim().equals(""))
					strhashstring = strhashstring + resTrackID.trim();
				if (resAmount != null && !resAmount.trim().equals(""))
					strhashstring = strhashstring + resAmount.trim();
				if (resResult != null && !resResult.trim().equals(""))
					strhashstring = strhashstring + resResult.trim();
				if (resPaymentId != null && !resPaymentId.trim().equals(""))
					strhashstring = strhashstring + resPaymentId.trim();
				if (resRef != null && !resRef.trim().equals(""))
					strhashstring = strhashstring + resRef.trim();
				if (resAuth != null && !resAuth.trim().equals(""))
					strhashstring = strhashstring + resAuth.trim();
				if (resTranId != null && !resTranId.trim().equals(""))
					strhashstring = strhashstring + resTranId.trim();

				String hashvalue = getSHA256(strhashstring);

				if (hashvalue.equals(resudf5)) {
					if (resResult.equals("CAPTURED")) {
						try {
							return updateSuccessfulOrder(params, orderId);
						} catch (Exception e) {
							logger.error(
									"Exception in Updating successful response from HDFC for order:"
											+ orderId, e);
							return PaymentConstants.FAILED;
						}
					} else {
						logger.error("HDFC Payment failed due to unsuccessful result status for order:"
								+ orderId);
						updateFailedOrder(params, orderId, udf1);
						return PaymentConstants.FAILED;
					}
				} else {
					logger.error("Response Hash Mismatch for HDFC payment for order:"
							+ orderId);
					updateFailedOrder(params, orderId, isOrderEligibleForCOD);
					return PaymentConstants.FAILED;
				}
			} else {
				logger.error("HDFC Payment failed for order:" + orderId
						+ " due to error message in response :" + resErrorText);
				updateFailedOrder(params, orderId, isOrderEligibleForCOD);
				return PaymentConstants.FAILED;
			}
		}
		return null;

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
}
