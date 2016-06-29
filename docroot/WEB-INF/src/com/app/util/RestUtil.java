package com.app.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.RenderRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.app.dto.OrderDTO;
import com.app.dto.OrderPaymentWrapper;
import com.app.dto.OrderWrapper;
import com.app.dto.PaymentChannelDTO;
import com.app.dto.PaymentChannelWrapper;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;

final public class RestUtil {

	private static final String CONTENT_TYPE = "content-type";
	private static final String CONTENT_TYPE_VALUE = "application/json";
	private static final String WEBSHOP_URL = PropsUtil.get("webshop_url");
	private static final String PAYMENT_URL = PropsUtil.get("webshop_payment_url");
	private static final String REWARDS_URL = PropsUtil.get("rewards_url");
	private static final String PINCODE_URL = PropsUtil.get("webshop_pincode_url");
	private static final String CART_URL = PropsUtil.get("webshop_cart_url");
	
	private static final String NEW_CART_URL = PropsUtil.get("new_cart_url");
	private static Logger logger = Logger.getLogger(RestUtil.class);

	private RestUtil() {

	}

	public static String getOrderDetails(String orderId, String deviceId) throws HttpResponseException, IOException {
		CloseableHttpResponse response = null;
		String responseString = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpGet request = new HttpGet(new StringBuilder(WEBSHOP_URL).append("getInboundOrderHeader").append(
					"&deviceId=").append(deviceId).append("&orderId=").append(orderId).toString());
			//logger.info("get orderURL " + request.getURI());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (Exception ex) {
			logger.error("Exception while executing getOrderDetails() in RestUtil", ex);
		} finally {
			response.close();
		}
		return responseString;
	}

	public static String updatePaymentStatus(String orderId, Map<String, String> params)
			throws ClientProtocolException, IOException, JSONException {
		CloseableHttpResponse response = null;
		String responseString = null;
		String deviceId = null;
		String operationName = "processPGInboundOrder";
		String pgType = params.get("pgType");
		if (pgType == null) {
			String payType = params.get("udf3");
			if (payType.equalsIgnoreCase("HDFC")) {
				deviceId = params.get("udf4");
			}
		} else {
			if (pgType.equalsIgnoreCase("payU")) {
				deviceId = params.get("udf2");
			} else if (pgType.equalsIgnoreCase("MWALL")) {
				deviceId = params.get("deviceId");
			}
		}
		String responseMessage = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(new StringBuilder(WEBSHOP_URL).append(operationName).append("&deviceId=")
					.append(deviceId).append("&inboundOrderId=").append(orderId).toString());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			String paymentJson = getPaymentJson(params);
			request.setEntity(new StringEntity(paymentJson));
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
			ObjectMapper mapper = Mapper.getObjectMapper();
			OrderPaymentWrapper paymentResponse = mapper.readValue(mapper.readTree(responseString).path(
					"orderPaymentWrapper"), OrderPaymentWrapper.class);
			responseMessage = paymentResponse.getResponseCode();

		} catch (Exception ex) {
			logger.error("Failed to execute RestUtil updatePaymentStatus() for order:" + orderId + " and type" + pgType);
			logger.error("Exception while executing updatePaymentStatus()", ex);
		} finally {
			response.close();
		}

		return responseMessage;
	}

	private static String getPaymentJson(Map<String, String> params) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		JSONObject orderPayment = JSONFactoryUtil.createJSONObject();
		JSONObject values = JSONFactoryUtil.createJSONObject();
		JSONObject paymentObj = JSONFactoryUtil.createJSONObject();
		String paymentChannel = null;
		String pgType = params.get("pgType");
		if (pgType == null) {
			String payType = params.get("udf3");
			if (payType.equalsIgnoreCase("HDFC")) {
				String amountPaid = params.get("amt").replace(",", "");
				paymentObj.put("paymentChannelCode", "HDFCCC");
				values.put("paymentChannel", paymentObj);
				values.put("mihId", params.get("paymentid"));

				if (params.get("result").equals("CAPTURED")) {
					values.put("bankRefNo", params.get("ref"));
					values.put("paymentStatus", PaymentConstants.SUCCESS);
					values.put("bankCode", params.get("auth"));
				} else {
					values.put("paymentStatus", PaymentConstants.FAILED);
					values.put("paymentErrorMsg", params.get("errortext"));
					values.put("paymentErrorCode", params.get("Error"));
				}
				values.put("paymentUnMappedStatus", params.get("result"));
				values.put("pgBankType", "HDFC");
				values.put("bankTransactionId", params.get("tranid"));
				values.put("paidAmount", Double.parseDouble(amountPaid));
			}
		} else {
			if (pgType.equalsIgnoreCase("payU")) {
				String paymentSource = params.get("payment_source").toUpperCase();
				String paymentMode = params.get("mode").toUpperCase();
				if (paymentMode.equalsIgnoreCase("CC") || paymentMode.equalsIgnoreCase("DC")) {
					paymentChannel = paymentSource.concat("CC");
				} else if (paymentMode.equalsIgnoreCase("NB")) {
					paymentChannel = paymentSource.concat("NB");
				} else if (paymentMode.equalsIgnoreCase("WALLET")) {
					paymentChannel = paymentSource.concat("WALL");
				}
				try {
					Date date = formatter.parse(params.get("addedon"));
					values.put("paymentRequestDate", formatter2.format(date));
					values.put("paymentResponseDate", formatter2.format(date));
				} catch (ParseException pe) {
					values.put("paymentRequestDate", params.get("addedon"));
					values.put("paymentResponseDate", params.get("addedon"));
				}
				paymentObj.put("paymentChannelCode", paymentChannel);
				values.put("paymentChannel", paymentObj);
				values.put("bankTransactionId", params.get("txnid"));
				values.put("paidAmount", Double.parseDouble(params.get("amount")));
				values.put("mihId", params.get("mihpayid"));
				values.put("bankRefNo", params.get("bank_ref_num"));
				values.put("paymentErrorCode", params.get("error"));
				values.put("paymentErrorMsg", params.get("error_Message"));
				values.put("paymentStatus", params.get("status"));
				values.put("pgBankType", params.get("PG_TYPE"));
				values.put("paymentUnMappedStatus", params.get("unmappedstatus"));
				values.put("bankCode", params.get("mode"));
				orderPayment.put("orderPayment", values);

			} else if (pgType.equalsIgnoreCase("MWALL")) {

				String statusCode = params.get("statuscode");
				if ("0".equals(statusCode)) {
					values.put("paymentStatus", PaymentConstants.SUCCESS);
				} else {
					values.put("paymentStatus", PaymentConstants.FAILED);
					values.put("paymentErrorCode", params.get("statuscode"));
					values.put("paymentErrorMsg", params.get("statusmessage"));
				}
				values.put("paidAmount", Double.parseDouble(params.get("amount")));
				paymentObj.put("paymentChannelCode", pgType.toUpperCase());
				values.put("paymentChannel", paymentObj);
				orderPayment.put("orderPayment", values);

			} else if (pgType.equalsIgnoreCase("PAYTM")) {

				String statusCode = params.get("RESPCODE");
				if ("01".equals(statusCode)) {
					values.put("paymentStatus", PaymentConstants.SUCCESS);
					values.put("paymentErrorCode", statusCode);
					values.put("paymentErrorMsg", params.get("RESPMSG"));
				} else {
					values.put("paymentStatus", PaymentConstants.FAILED);
					values.put("paymentErrorCode", statusCode);
					values.put("paymentErrorMsg", params.get("RESPMSG"));
				}
				values.put("paidAmount", Double.parseDouble(params.get("TXNAMOUNT")));
				values.put("mihId", params.get("TXNID"));
				values.put("bankTransactionId", params.get("BANKTXNID"));
				paymentObj.put("paymentChannelCode", pgType.toUpperCase());
				values.put("paymentChannel", paymentObj);
				orderPayment.put("orderPayment", values);

			}
		}
		orderPayment.put("orderPayment", values);

		return orderPayment.toString();
	}

	public static OrderWrapper createOrder(String deviceId) throws IOException {
		CloseableHttpResponse orderResponse = null;
		String responseString = null;
		deviceId = deviceId.trim();
		OrderWrapper orderWrapper = null;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			String operationType = "createInboundOrderNew";
			HttpPost orderRequest = new HttpPost(new StringBuilder(WEBSHOP_URL).append(operationType).append(
					"&deviceId=" + deviceId).toString());

			orderRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			orderResponse = httpclient.execute(orderRequest);
			responseString = new BasicResponseHandler().handleResponse(orderResponse);

			ObjectMapper mapper = Mapper.getObjectMapper();
			orderWrapper = mapper.readValue(mapper.readTree(responseString).path("orderWrapper"), OrderWrapper.class);

		} catch (Exception ex) {
			logger.error("Exception while executing createOrder()", ex);
		} finally {
			orderResponse.close();
		}

		return orderWrapper;
	}

	public static PaymentChannelWrapper getPaymentChannels() throws HttpResponseException, IOException {
		CloseableHttpResponse paymentResponse = null;
		String responseString = null;
		PaymentChannelWrapper paymentChannelWrapper = null;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			String operationType = "getPaymentChannels";
			HttpGet paymentRequest = new HttpGet(new StringBuilder(PAYMENT_URL).append(operationType).toString());
			paymentRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			paymentResponse = httpclient.execute(paymentRequest);
			responseString = new BasicResponseHandler().handleResponse(paymentResponse);
			ObjectMapper mapper = Mapper.getObjectMapper();
			paymentChannelWrapper = mapper.readValue(mapper.readTree(responseString).path("paymentChannelWrapper"),
					PaymentChannelWrapper.class);

		} catch (Exception ex) {
			logger.error("Exception while executing getPaymentChannels()", ex);
		} finally {
			paymentResponse.close();
		}

		return paymentChannelWrapper;
	}

	public static String getRewardsPoint(String email, String productName, String price, String quantity)
			throws ClientProtocolException, IOException {

		CloseableHttpResponse rewardsResponse = null;
		String responseString = null;
		String campaignCode = "12JUN21W9619";
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			String url = new StringBuilder(REWARDS_URL).append("email=" + email)
					.append("&CampaignCode=" + campaignCode).append("&ProductName=WKurta").append("&Price=" + price)
					.append("&Quantity=" + quantity).append("&orderamount=" + price).append("&discount=0.00")
					.toString();
			HttpGet rewardsRequest = new HttpGet(url);
			rewardsRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			rewardsResponse = httpclient.execute(rewardsRequest);
			responseString = new BasicResponseHandler().handleResponse(rewardsResponse);

		} catch (Exception ex) {
			logger.error("Exception while executing getRewardsPoint()", ex);
		} finally {
			rewardsResponse.close();
		}

		return responseString;
	}

	public static String getCustomerPhonebydeviceId(String deviceId) throws HttpResponseException, IOException,
			JSONException {
		CloseableHttpResponse addressResponse = null;
		String customerPhoneNumber = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			String operationType = "getOrderAddress";
			HttpGet addressRequest = new HttpGet(new StringBuilder(CART_URL).append(operationType).append(
					"&deviceId=" + deviceId).toString());

			addressRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			addressResponse = httpclient.execute(addressRequest);
			String responseString = new BasicResponseHandler().handleResponse(addressResponse);
			JSONObject values = JSONFactoryUtil.createJSONObject(responseString).getJSONObject("orderAddressWrapper")
					.getJSONArray("orderAddresses").getJSONObject(0);

			customerPhoneNumber = values.getString("mobileNo");
		} catch (Exception ex) {
			logger.error("Exception while executing getCustomerPhoneNumber()", ex);
		} finally {
			addressResponse.close();
		}

		return customerPhoneNumber;
	}

	public static String getResponsefromURL(String requestURL) throws IOException {

		CloseableHttpResponse response = null;
		String responseString = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpGet request = new HttpGet(requestURL);
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (Exception ex) {
			logger.error("Exception while executing getResponseFromURL()", ex);
		} finally {
			response.close();
		}
		return responseString;
	}

	public static String getCustomerId(RenderRequest request) {
		String customerId = "";
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("COOKIE_FNY_CUSTOMER_ID")) {
						customerId = cookies[i].getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception while getting CustomerId", e);
		}
		return customerId;
	}

	public static String getResponseFromPostURL(String requestURL, String requestJson) throws IOException {

		CloseableHttpResponse response = null;
		String responseString = null;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(requestURL);
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			request.setEntity(new StringEntity(requestJson));

			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);

		} catch (Exception ex) {
			logger.error("Exception while executing getResponseFromPostURL()", ex);
		} finally {
			response.close();
		}
		return responseString;
	}

	public static String getPostRequestResponse(String requestURL) throws IOException {
		CloseableHttpResponse response = null;
		String responseString = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(requestURL);
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (Exception ex) {
			logger.error("Exception while executing getPostRequestResponse()", ex);
		} finally {
			response.close();
		}
		return responseString;
	}

	public static void updateGatewayConnectionFailed(String orderId, String deviceId, String paymentChannel)
			throws IOException {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(new StringBuilder(WEBSHOP_URL).append("processPGInboundOrder").append(
					"&orderId=").append(orderId).append("&deviceId=").append(deviceId).toString());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);

			JSONObject orderPayment = JSONFactoryUtil.createJSONObject();
			JSONObject values = JSONFactoryUtil.createJSONObject();
			JSONObject paymentObj = JSONFactoryUtil.createJSONObject();
			paymentObj.put("paymentChannelCode", paymentChannel);
			values.put("paymentChannel", paymentObj);
			values.put("paymentStatus", PaymentConstants.FAILED);
			values.put("paymentErrorCode", "SystemFailure");
			values.put("paymentErrorMsg", "System Failed before Gateway connection");
			orderPayment.put("orderPayment", values);

			request.setEntity(new StringEntity(orderPayment.toString()));
			response = httpclient.execute(request);
			String responseString = new BasicResponseHandler().handleResponse(response);

		} catch (Exception ex) {
			logger.error("Exception while executing updateGatewayConnectionFailed()", ex);
		} finally {
			response.close();
		}

	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> extractParamsFromResponse(RenderRequest request) {

		HttpServletRequest servletRequest = PortalUtil.getHttpServletRequest(request);
		HttpServletRequest req = PortalUtil.getOriginalServletRequest(servletRequest);

		Enumeration paramNames = req.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			String paramValue = req.getParameter(paramName);
			params.put(paramName, paramValue);
		}
		return params;

	}

	public static String getRewardsCount(OrderDTO order) {

		String rewardsCount = "0";

		try {
			String rewardsPoint = RestUtil.getRewardsPoint(order.getEmail(), order.getProductinfo(), order
					.getGrandPrice(), order.getQuantity());
			if (rewardsPoint != null) {
				rewardsCount = rewardsPoint.split("<html>")[0];
			}
			return rewardsCount;
		} catch (Exception e) {
			e.printStackTrace();
			return rewardsCount;
		}

	}

	public static OrderDTO getOrderForRewards(String orderData, String orderId, String deviceId) {

		OrderDTO orderDTO = new OrderDTO();
		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(orderData);

			JSONObject dtoObj = obj.getJSONObject("domainResponse").getJSONObject("entitiesResponse").getJSONObject(
					"baseDTO");
			String amount = dtoObj.getString("totalAmountPaid");

			String email = dtoObj.getJSONObject("customer").getString("loginId");
			String phone = dtoObj.getJSONObject("billingAddress").getString("mobileNo");
			String productInfo = "testProduct";

			String grandPrice = dtoObj.getString("grandOrderValue");

			String totalBasePrice = dtoObj.getString("totalBasePrice");

			String quantity = dtoObj.getString("totalOrderItems");

			orderDTO.setEmail(email);
			orderDTO.setAmount(amount);
			orderDTO.setPhone(phone);
			orderDTO.setOrderId(orderId);
			orderDTO.setProductinfo(productInfo);
			orderDTO.setQuantity(quantity);
			orderDTO.setTotalBasePrice(totalBasePrice);
			orderDTO.setGrandPrice(grandPrice);
		} catch (Exception e) {
			logger.error("Exception while executing getOrderForRewards in RestUtil", e);
		}
		return orderDTO;

	}

	public static OrderPaymentWrapper updateCODInboundOrder(String orderId, Map<String, String> params)
			throws IOException {
		CloseableHttpResponse response = null;
		String responseString = null;
		String deviceId = params.get("deviceId");
		String operationName = "processCODInboundOrder";
		String pgType = params.get("pgType");
		OrderPaymentWrapper paymentResponse = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(new StringBuilder(WEBSHOP_URL).append(operationName).append("&deviceId=")
					.append(deviceId).append("&inboundOrderId=").append(orderId).toString());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			String paymentJson = getCODPaymentJson(params);
			request.setEntity(new StringEntity(paymentJson));
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);

			ObjectMapper mapper = Mapper.getObjectMapper();
			paymentResponse = mapper.readValue(mapper.readTree(responseString).path("orderPaymentWrapper"),
					OrderPaymentWrapper.class);

		} catch (Exception ex) {
			logger.error("Failed to execute RestUtil updateCODInboundOrder() for order:" + orderId + " and type"
					+ pgType);
			logger.error("Exception while executing updateCODInboundOrder()", ex);
		} finally {
			response.close();
		}

		return paymentResponse;
	}

	private static String getCODPaymentJson(Map<String, String> params) {

		String paymentJson = "";
		try {
			JSONObject orderPayment = JSONFactoryUtil.createJSONObject();
			JSONObject values = JSONFactoryUtil.createJSONObject();
			JSONObject paymentObj = JSONFactoryUtil.createJSONObject();
			paymentObj.put("paymentChannelCode", "COD");
			values.put("paymentChannel", paymentObj);
			values.put("paymentStatus", PaymentConstants.SUCCESS);
			orderPayment.put("orderPayment", values);
			paymentJson = orderPayment.toString();
		} catch (Exception e) {
			logger.error("Exception while executing RestUtil getCODPaymentJson()", e);
		}
		return paymentJson;
	}

	public static String getCartbyDeviceId(ResourceBundle res, String deviceId) {
		String responseString = null;
		try {
			String requestJson = null;
			JSONObject cartbody = JSONFactoryUtil.createJSONObject();
			String cartURL = new StringBuilder(res.getString("API")).append(res.getString("GET_CART")).toString();

			cartbody.put("deviceId", deviceId);
			cartbody.put("pullInventory", false);
			requestJson = cartbody.toString();
			responseString = RestUtil.getResponseFromPostURL(cartURL, requestJson);

		} catch (Exception e) {
			logger.error("Exception while executing getCartbyDeviceId in RestUtil", e);
		}
		return responseString;

	}

	public static String getAggregatedInboundOrder(String orderId, String deviceId) throws IOException {
		CloseableHttpResponse response = null;
		String responseString = null;
		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpGet request = new HttpGet(new StringBuilder(WEBSHOP_URL).append("getAggregatedInboundOrder").append(
					"&deviceId=").append(deviceId).append("&orderNumber=").append(orderId).toString());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (Exception ex) {
			logger.error("Exception while executing getAggregatedInboundOrder() in RestUtil", ex);
		} finally {
			response.close();
		}
		return responseString;
	}

	public static String getOrderShippingPincode(String deviceId) {
		String pincode = null;

		CloseableHttpResponse addressResponse = null;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			String operationType = "getOrderAddress";
			HttpGet addressRequest = new HttpGet(new StringBuilder(CART_URL).append(operationType).append(
					"&deviceId=" + deviceId).toString());

			addressRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			addressResponse = httpclient.execute(addressRequest);
			String responseString = new BasicResponseHandler().handleResponse(addressResponse);

			pincode = JSONFactoryUtil.createJSONObject(responseString).getJSONObject("orderAddressWrapper")
					.getJSONArray("orderAddresses").getJSONObject(1) // 1 for
																		// shipping,
																		// 0 for
																		// billing
					.getJSONObject("pinCodeMaster").getString("pinCode");

		} catch (Exception ex) {
			logger.error("Exception while executing getPincode()", ex);
		} finally {
			try {
				addressResponse.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return pincode;
	}

	public static String getPaymentOptionbyPincode(String pincode) {

		String responseString = null;
		String serviceavailabilty = null;
		try {
			String pincodeURL = new StringBuilder(PINCODE_URL).append(pincode).toString();

			responseString = RestUtil.getResponsefromURL(pincodeURL);

			JSONObject obj = JSONFactoryUtil.createJSONObject(responseString);

			JSONObject dtoObj = obj.getJSONObject("pinCodeWrapper").getJSONObject("pinCodes");
			serviceavailabilty = dtoObj.getString("serviceability");

		} catch (Exception ex) {
			logger.error("Exception while executing getPaymentOptionbyPincode() in RestUtil", ex);
		}
		return serviceavailabilty;
	}

	public static String getBillingPincode(String lastOrderAddress) {

		String pincode = null;

		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(lastOrderAddress);

			pincode = obj.getJSONObject("orderAddressWrapper").getJSONArray("orderAddresses").getJSONObject(0)
					.getJSONObject("pinCodeMaster").getString("pinCode");
		} catch (Exception e) {
			logger.error("Exception while executing RestUtil getBillingPincode", e);
			logger.error("Exception while executing getBillingPincode" + lastOrderAddress);
		}
		return pincode;
	}

	public static String getShippingPincode(String lastOrderAddress) {

		String pincode = null;

		try {
			JSONObject obj = JSONFactoryUtil.createJSONObject(lastOrderAddress);

			pincode = obj.getJSONObject("orderAddressWrapper").getJSONArray("orderAddresses").getJSONObject(1)
					.getJSONObject("pinCodeMaster").getString("pinCode");
		} catch (Exception e) {
			logger.error("Exception while executing RestUtil getBillingPincode", e);
			logger.error("Exception while executing getBillingPincode" + lastOrderAddress);
		}
		return pincode;
	}

	public static String getAccordionDataFromArticles(List<JournalArticle> journalArticles) {
		String accordData = null;
		try {
			HashSet<Integer> hs = new HashSet<Integer>();
			HashSet<Integer> yrSet = new HashSet<Integer>();
			for (JournalArticle art : journalArticles) {
				Date date = art.getDisplayDate();
				Calendar cal = new GregorianCalendar();
				cal.setTime(date);
				Integer yr = cal.get(Calendar.YEAR);
				Integer mon = cal.get(Calendar.MONTH) + 1;
				String yrStr = yr.toString() + mon.toString();
				hs.add(Integer.parseInt(yrStr));
				yrSet.add(yr);
			}

			accordData = hs.toString();

		} catch (Exception ex) {
			logger.error("Exception while executing RestUtil getAccordionDataFromArticles()", ex);
		}
		return accordData;
	}

	public static String getAccordionYearFromArticles(List<JournalArticle> journalArticles) {
		String accrYear = null;
		try {
			HashSet<Integer> yrSet = new HashSet<Integer>();
			for (JournalArticle art : journalArticles) {
				Date date = art.getDisplayDate();
				Calendar cal = new GregorianCalendar();
				cal.setTime(date);
				Integer yr = cal.get(Calendar.YEAR);
				yrSet.add(yr);
			}
			TreeSet<Integer> st = new TreeSet<Integer>(yrSet);
			st = (TreeSet<Integer>) st.descendingSet();
			accrYear = st.toString();
		} catch (Exception ex) {
			logger.error("Exception while executing RestUtil getAccordionYearFromArticles()", ex);
		}
		return accrYear;
	}

	public static OrderDTO getOrderDTORewardsCredit(String deviceId, String orderId) {
		OrderDTO orderDTO = new OrderDTO();
		try {

			String orderData = getOrderDetails(orderId, deviceId);
			JSONObject obj = JSONFactoryUtil.createJSONObject(orderData);
			//logger.info("orderDTO rewards response " + obj);
			JSONObject dtoObj = obj.getJSONObject("domainResponse").getJSONObject("entitiesResponse").getJSONObject(
					"baseDTO");

			String amount = dtoObj.getString("totalAmountPaid");
			String creditApply = dtoObj.getString("totalStoredCredit");
			String basePrice = dtoObj.getString("totalBasePrice");
			String rewardsApply = dtoObj.getString("totalRewardAmount");
			orderDTO.setAmount(amount);
			orderDTO.setTotalBasePrice(basePrice);
			orderDTO.setCredits(creditApply);
			orderDTO.setRewards(rewardsApply);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception while executing getOrderDTO()", e);
		}
		return orderDTO;
	}

	public static OrderPaymentWrapper updateStoreCreditOrder(String orderId, Map<String, String> params)
			throws IOException {
		CloseableHttpResponse response = null;
		String responseString = null;
		String deviceId = params.get("deviceId");
		String operationName = "processPGInboundOrder";
		String pgType = params.get("pgType");
		OrderPaymentWrapper paymentResponse = null;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			HttpPost request = new HttpPost(new StringBuilder(WEBSHOP_URL).append(operationName).append("&deviceId=")
					.append(deviceId).append("&inboundOrderId=").append(orderId).toString());
			request.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			String storePaymentJson = getStoreCreditPaymentJson(params);
			request.setEntity(new StringEntity(storePaymentJson));
			response = httpclient.execute(request);
			responseString = new BasicResponseHandler().handleResponse(response);
			ObjectMapper mapper = Mapper.getObjectMapper();
			paymentResponse = mapper.readValue(mapper.readTree(responseString).path("orderPaymentWrapper"),
					OrderPaymentWrapper.class);
		} catch (Exception ex) {
			logger.error("Failed to execute RestUtil updateStoreCreditOrder() for order:" + orderId + " and type"
					+ pgType);
			logger.error("Exception while executing updateStoreCreditOrder()", ex);
		} finally {
			response.close();
		}

		return paymentResponse;
	}

	private static String getStoreCreditPaymentJson(Map<String, String> params) {

		String storePaymentJson = "";
		try {
			JSONObject orderPayment = JSONFactoryUtil.createJSONObject();
			JSONObject values = JSONFactoryUtil.createJSONObject();
			JSONObject paymentObj = JSONFactoryUtil.createJSONObject();
			paymentObj.put("paymentChannelCode", "SC");
			values.put("paymentChannel", paymentObj);
			values.put("paymentStatus", PaymentConstants.SUCCESS);
			orderPayment.put("orderPayment", values);
			storePaymentJson = orderPayment.toString();
		} catch (Exception e) {
			logger.error("Exception while executing RestUtil getStoreCreditPaymentJson()", e);
		}
		return storePaymentJson;
	}

	public static String[] getApplyPromoCode(String deviceId) {

		String[] applyVouchers = null;
		try {
			String requestJson = null;
			JSONObject cartbody = JSONFactoryUtil.createJSONObject();
			cartbody.put("deviceId", deviceId);
			cartbody.put("pullInventory", false);
			requestJson = cartbody.toString();
			//logger.info("apply Promo-- " + NEW_CART_URL + " json:-" + requestJson);
			String cartData = RestUtil.getResponseFromPostURL(NEW_CART_URL, requestJson);
			//logger.info("apply Promo response--" + cartData);
			List<String> vouchersLst = new ArrayList<String>();

			JSONObject obj = JSONFactoryUtil.createJSONObject(cartData);

			Object dtoObj = obj.getJSONObject("cart").getJSONArray("vouchers");
			//logger.info("voucher json " + dtoObj);
			if (dtoObj != null) {
				JSONArray jsAr = obj.getJSONObject("cart").getJSONArray("vouchers");
				for (int jr = 0; jr < jsAr.length(); jr++) {
					JSONObject jb = jsAr.getJSONObject(jr);
					vouchersLst.add(jb.getString("code"));
				}
			} else {
				JSONObject promotion = obj.getJSONObject("cart").getJSONObject("vouchers");
				if (promotion != null) {
					vouchersLst.add(promotion.getString("code"));
				}
			}
			applyVouchers = vouchersLst.toArray(new String[vouchersLst.size()]);
		} catch (Exception ex) {
			logger.error("Exception while executing getApplyCustomerVoucher", ex);
		}
		return applyVouchers;
	}

	public static String getPromoCouponVal(ThemeDisplay themeDisplay) {

		String couponVal = null;
		try {
			JournalArticle journalBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(themeDisplay
					.getScopeGroupId(), "restrict-coupon-content", 0);
			String journalBannerArticleId = journalBanner.getArticleId();
			Double journalVersion = JournalArticleLocalServiceUtil.getLatestVersion(themeDisplay.getScopeGroupId(),
					journalBannerArticleId);
			JournalArticleDisplay journalBannerDisplay = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					journalBannerArticleId, journalVersion, "", "", "", themeDisplay, 0, "");
			if (journalBannerDisplay.getContent() != null) {
				couponVal = journalBannerDisplay.getContent();
			}

		} catch (NoSuchArticleException ne) {
			logger.error("No Article exist with name-> restrict-coupon-content");
		} catch (Exception e) {
			logger.error("Exception while getting PromoCoupon Value", e);
		}
		return couponVal;
	}

	public static String[] getProductEventIds(String deviceId) {

		List<String> eventIds = new ArrayList<String>();
		String[] eventArray = null;
		try {
			String requestJson = null;
			JSONObject cartbody = JSONFactoryUtil.createJSONObject();
			cartbody.put("deviceId", deviceId);
			cartbody.put("pullInventory", false);
			requestJson = cartbody.toString();
			logger.info("event url " + NEW_CART_URL + " json:-" + requestJson);
			String cartData = RestUtil.getResponseFromPostURL(NEW_CART_URL, requestJson);
			logger.info("event result--" + cartData);
			JSONObject obj = JSONFactoryUtil.createJSONObject(cartData);
			Object dtoObj = obj.getJSONObject("cart").getJSONArray("products");
			logger.info("product array json" + dtoObj);
			if (dtoObj != null) {
				JSONArray jsAr = obj.getJSONObject("cart").getJSONArray("products");
				for (int jr = 0; jr < jsAr.length(); jr++) {
					JSONObject jb = jsAr.getJSONObject(jr);
					eventIds.add(jb.getJSONObject("sellerSku").getJSONObject("event").getString("eventId"));
					logger.info("eventIDS-" + jb.getJSONObject("sellerSku").getJSONObject("event").getString("eventId"));
				}
			} else {
				JSONObject promotion = obj.getJSONObject("cart").getJSONObject("products");
				Iterator<String> keys = promotion.keys();
				while (keys.hasNext()) {
					String keyVal = (String) keys.next();
					logger.info("key " + keyVal);
					logger.info("key json " + promotion.getJSONObject(keyVal));
					logger.info("seller sku " + promotion.getJSONObject(keyVal).getJSONObject("sellerSku"));
					eventIds.add(promotion.getJSONObject(keyVal).getJSONObject("sellerSku").getJSONObject("event")
							.getString("eventId"));
					logger.info("eventId-"
							+ promotion.getJSONObject(keyVal).getJSONObject("sellerSku").getJSONObject("event")
									.getString("eventId"));
				}
			}
			eventArray = eventIds.toArray(new String[eventIds.size()]);
		} catch (Exception ex) {
			logger.error("Exception while executing getProductEventId", ex);
		}

		return eventArray;
	}

	public static String getRestrictEventVal(ThemeDisplay themeDisplay) {
		String eventVal = null;
		try {
			JournalArticle journalBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(themeDisplay
					.getScopeGroupId(), "restrict-event-content", 0);
			String journalBannerArticleId = journalBanner.getArticleId();
			Double journalVersion = JournalArticleLocalServiceUtil.getLatestVersion(themeDisplay.getScopeGroupId(),
					journalBannerArticleId);
			JournalArticleDisplay journalBannerDisplay = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					journalBannerArticleId, journalVersion, "", "", "", themeDisplay, 0, "");
			if (journalBannerDisplay.getContent() != null) {
				eventVal = journalBannerDisplay.getContent();
			}

		} catch (NoSuchArticleException ne) {
			logger.error("No Article exist with name -> restrict-event-content");
		} catch (Exception e) {
			logger.error("Exception while getting Restrict Event Value", e);
		}
		return eventVal;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<PaymentChannelDTO> getValidPaymentChannels(List<PaymentChannelDTO> paymentChannels,
			String deviceId, String promoVal, String eventVal) {
		List<PaymentChannelDTO> newPaymentChannels = new ArrayList<PaymentChannelDTO>();
		HashMap<String, String> vchm = new HashMap<String, String>();
		HashMap<String, String> evhm = new HashMap<String, String>();
		try {
			String[] applyPromoCode = RestUtil.getApplyPromoCode(deviceId);
			String[] eventIds = RestUtil.getProductEventIds(deviceId);
			if (promoVal != null && applyPromoCode != null) {
				String[] voucherPr = promoVal.split("\\$");
				for (int j = 0; j < applyPromoCode.length; j++) {
					if (voucherPr[1].equals("1")) {
						String[] vcRule = voucherPr[0].split("\\|");
						for (int sr = 0; sr < vcRule.length; sr++) {
							String[] rul = vcRule[sr].split("=");
							String rul1 = rul[0].trim();
							String rul2 = rul[1].trim();
							vchm.put(rul1, rul2);
						}
						String channelStr = vchm.get(applyPromoCode[j]);
						if (channelStr != null) {
							String[] payChn = channelStr.split(",");
							for (int i = 0; i < payChn.length; i++) {
								for (PaymentChannelDTO paymentChannel : paymentChannels) {
									String ruleChn = payChn[i].trim();
									String channel = paymentChannel.getPaymentChannelCode();
									if (channel.equalsIgnoreCase(ruleChn)) {
										newPaymentChannels.add(paymentChannel);
									}
								}
							}
						}
					}
				}
			}
			if (eventVal != null) {
				String[] evPr = eventVal.split("\\$");
				if (evPr[1].equals("1")) {
					String[] evRule = evPr[0].split("\\|");
					for (int er = 0; er < evRule.length; er++) {
						String[] rul = evRule[er].split("=");
						String rul1 = rul[0].trim();
						String rul2 = rul[1].trim();
						evhm.put(rul1, rul2);
					}
					List<String> chn = new ArrayList<String>();
					for (int ir = 0; ir < eventIds.length; ir++) {
						String evt = evhm.get(eventIds[ir]);
						if (evt != null) {
							chn.add(evt);
							break;
						}
					}
					if (chn.size() > 0) {
						String channelStr = chn.get(0);
						String[] payChn = channelStr.split(",");
						for (int i = 0; i < payChn.length; i++) {
							for (PaymentChannelDTO paymentChannel : paymentChannels) {
								String ruleChn = payChn[i].trim();
								String channel = paymentChannel.getPaymentChannelCode();
								if (channel.equalsIgnoreCase(ruleChn)) {
									newPaymentChannels.add(paymentChannel);
								}
							}
						}
					}
				}
			}
			Set set = new HashSet();
			List newList = new ArrayList<PaymentChannelDTO>();
			for (Iterator iter = newPaymentChannels.iterator(); iter.hasNext();) {
				Object element = iter.next();
				if (set.add(element)) {
					newList.add(element);
				}
			}
			newPaymentChannels.clear();
			newPaymentChannels.addAll(newList);
		} catch (Exception e) {
			logger.error("Exception while getting valid payment cahnnels", e);
		}

		return newPaymentChannels;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getPaymentMode(List<PaymentChannelDTO> paymentChannels) {
		List<String> lst = new ArrayList<String>();
		Set chSet = new HashSet();
		for (PaymentChannelDTO channel : paymentChannels) {
			String payCd = channel.getPaymentChannelCode();
			if (payCd.equalsIgnoreCase("PAYUCC") || payCd.equalsIgnoreCase("HDFCCC")) {
				chSet.add("CC");
			}
			if (payCd.equalsIgnoreCase("PAYUNB")) {
				chSet.add("NB");
			}
			if (payCd.equalsIgnoreCase("COD")) {
				chSet.add("COD");
			}
			if (payCd.equalsIgnoreCase("PAYTM") || payCd.equalsIgnoreCase("MWALL")
					|| payCd.equalsIgnoreCase("PAYUWALL")) {
				chSet.add("WALL");
			}
			if (payCd.equalsIgnoreCase("PAYTM")){
				chSet.add("PAYTM");
			}

		}
		lst.addAll(chSet);
		return lst;
	}

	public static String getPayuOfferKey(ThemeDisplay themeDisplay) {

		String offerKey = null;
		try {
			JournalArticle journalBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(themeDisplay
					.getScopeGroupId(), "payu-offer-key", 0);
			String journalBannerArticleId = journalBanner.getArticleId();
			Double journalVersion = JournalArticleLocalServiceUtil.getLatestVersion(themeDisplay.getScopeGroupId(),
					journalBannerArticleId);
			JournalArticleDisplay journalBannerDisplay = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					journalBannerArticleId, journalVersion, "", "", "", themeDisplay, 0, "");
			if (journalBannerDisplay.getContent() != null) {
				offerKey = journalBannerDisplay.getContent();
			}

		} catch (NoSuchArticleException ne) {
			logger.error("No Article exist with name-> payu-offer-key");
		} catch (Exception e) {
			logger.error("Exception while getting getPayuOfferKey Value", e);
		}
		return offerKey;
	}

	public static List<String> getDefaultMode() {
		List<String> mdlst = new ArrayList<String>();
		mdlst.add("CC");
		mdlst.add("NB");
		mdlst.add("WALL");
		mdlst.add("PAYTM");
		mdlst.add("COD");		

		return mdlst;
	}

	public static boolean blockInventory(String deviceId, String orderId) throws IOException {

		CloseableHttpResponse blockInventoryResponse = null;
		boolean blockInventory = true;

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();

			HttpGet blockInventoryRequest = new HttpGet(new StringBuilder(WEBSHOP_URL).append("blockInventory").append(
					"&deviceId=").append(deviceId).append("&inboundOrderId=").append(orderId).toString());
			blockInventoryRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			blockInventoryResponse = httpclient.execute(blockInventoryRequest);
			String responseString = new BasicResponseHandler().handleResponse(blockInventoryResponse);

			String respCode = JSONFactoryUtil.createJSONObject(responseString).getJSONObject("orderWrapper").getString("responseCode");
			//logger.info("blockInventory response--" + respCode);

			if (respCode.equals("SUCCESS")) {
				blockInventory = false;
			}
		} catch (Exception ex) {
			logger.error("Exception while executing blockInventory()", ex);
		} finally {
			blockInventoryResponse.close();
		}

		return blockInventory;
	}

	public static void unblockInventory(String deviceId, String orderId) throws IOException {
		
		CloseableHttpResponse unblockInventoryResponse = null;		

		try {
			CloseableHttpClient httpclient = HttpConnection.getHttpClient();
			
			HttpGet unblockInventoryRequest = new HttpGet(new StringBuilder(WEBSHOP_URL).append("unblockInventory").append(
					"&deviceId=").append(deviceId).append("&inboundOrderId=").append(orderId).toString());
			//logger.info("unblock url ------------"+unblockInventoryRequest.getURI());
			unblockInventoryRequest.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE);
			unblockInventoryResponse = httpclient.execute(unblockInventoryRequest);
			String responseString = new BasicResponseHandler().handleResponse(unblockInventoryResponse);

			String respCode = JSONFactoryUtil.createJSONObject(responseString).getJSONObject("orderWrapper").getString("responseCode");
			//logger.info("unblockInventory response--" + respCode);

		} catch (Exception ex) {
			logger.error("Exception while executing unblockInventory()", ex);
		} finally {
			unblockInventoryResponse.close();
		}

	}
}
