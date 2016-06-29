package com.app.util;

import java.math.BigDecimal;
import java.util.ResourceBundle;

import javax.portlet.ResourceRequest;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.app.dto.DomainResponse;
import com.liferay.portal.kernel.util.PropsUtil;

public class ServiceUtil {

	private static Logger logger = Logger.getLogger(RestUtil.class);

	private ServiceUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String getVoucherURL(ResourceBundle res, String url,
			String promotionType, String promotionVal, ResourceRequest request) {
		String voucherURL = "";

		try {
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
			voucherURL = new StringBuilder(res.getString("API"))
					.append(res.getString(url)).toString()
					.replace("{promotionType}", promotionType)
					.replace("{promotionValue}", promotionVal)
					.replace("{deviceId}", deviceId);

		} catch (Exception e) {
			logger.error("Error while executing ServiceUtil getVoucherURL()", e);
		}
		return voucherURL;
	}

	public static String getCustomerId(ResourceRequest request) {
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
			logger.error("Exception while getting CustomerId in ServiceUtil", e);
		}
		return customerId;
	}

	public static String getCustomerAllVouchers(ResourceBundle res,
			String deviceId) {
		// TODO Auto-generated method stub----------GET_PERSONALIZE_VOUCHER
		String responseString = null;
		try {
			String allVoucherURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_PERSONALIZE_VOUCHER"))
					.toString().replace("{deviceId}", deviceId);
			responseString = RestUtil.getResponsefromURL(allVoucherURL);

		} catch (Exception e) {
			logger.error(
					"Exception while executing getCustomerAllVouchers in ServiceUtil",
					e);
		}
		return responseString;

	}

	public static BigDecimal getRewardsForCustomer(ResourceBundle res,
			String deviceId) {
		// TODO Auto-generated method stub---SERVICE_URL_REWARD_POINTS
		BigDecimal point = new BigDecimal(0);
		try {
			String rewardsURL = new StringBuilder(res.getString("API"))
					.append(res.getString("SERVICE_URL_REWARD_POINTS"))
					.toString().replace("{deviceId}", deviceId);
			String responseString = RestUtil.getResponsefromURL(rewardsURL);
			if (responseString != null && responseString != "") {
				point = new BigDecimal(responseString);
			}
		} catch (Exception e) {
			logger.error(
					"Exception while executing getRewardsForCustomer in ServiceUtil",
					e);
		}
		return point;
	}

	public static BigDecimal getCustomerCreditSum(ResourceBundle res,
			String customerId, String deviceId) {
		// TODO Auto-generated method stub--------SERVICE_URL_GET_CREDIT_BALANCE

		BigDecimal credits = new BigDecimal(0);;
		try {
			String creditsURL = new StringBuilder(res.getString("API"))
					.append(res.getString("SERVICE_URL_GET_CREDIT_BALANCE"))
					.toString().replace("{customerId}", customerId)
					.replace("{deviceId}", deviceId);
			String responseString = RestUtil.getResponsefromURL(creditsURL);
			ObjectMapper mapper = Mapper.getObjectMapper();
			DomainResponse domainResponse = mapper.readValue(
					mapper.readTree(responseString).path("domainResponse"),
					DomainResponse.class);
			String responseMessage = domainResponse.getMessage();
			if (responseMessage != null && responseMessage != "") {
				credits = new BigDecimal(responseMessage);
			}
		} catch (Exception e) {
			logger.error(
					"Exception while executing getCustomerCreditSum in ServiceUtil",
					e);
		}
		return credits;
	}

	public static String getPincode(ResourceRequest request) {
		String pincode = "";
		try {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(
							"COOKIE_PINCODE_DELIVERABLE")) {
						pincode = cookies[i].getValue();
						break;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception while getting pincode in ServiceUtil", e);
		}
		return pincode;
	}

	public static String getSubscriptionByDeviceId(String deviceId,
			ResourceBundle res) {
		String responseString = null;
		try {
			String creditsURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_SUBSCRIPTION_CHANNEL"))
					.toString().replace("{deviceId}", deviceId);
			responseString = RestUtil.getResponsefromURL(creditsURL);
		} catch (Exception e) {
			logger.error(
					"Exception while getSubscriptionByDeviceId in ServiceUtil",
					e);
		}
		return responseString;
	}

	public static String getOrderByNumber(String deviceId, ResourceBundle res, String orderNumber) {
		String responseString = null;
		try {
			String ordersURL = new StringBuilder(res.getString("API"))
					.append(res.getString("ORDER_NUMBER_LINE")).toString();
			String orderNumberURL = ordersURL.replace("{deviceId}", deviceId).replace("{orderNumber}", orderNumber);
			responseString = RestUtil.getResponsefromURL(orderNumberURL);
		} catch (Exception e) {
			logger.error(
					"Exception while getOrderByNumber in ServiceUtil",
					e);
		}
		return responseString;
	}

}