package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Billing
 */
public class Billing extends MVCPortlet {

	private Logger logger = Logger.getLogger(Billing.class);

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
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
			String pincode = "";
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(
							"COOKIE_PINCODE_DELIVERABLE")) {
						pincode = cookies[i].getValue();
						break;
					}
				}
			}
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
			String requestURL = new StringBuilder(res.getString("API")).append(
					res.getString("GET_LAST_ORDER_ADDRESS")).toString();
			String newUrl = requestURL.replace("{deviceId}", deviceId);
			String lastOrderAddress = RestUtil.getResponsefromURL(newUrl);
			String billingPincode = RestUtil
					.getBillingPincode(lastOrderAddress);
			String shippingPincode = RestUtil
					.getShippingPincode(lastOrderAddress);
			String pinCodeURL = new StringBuilder(res.getString("API")).append(
					res.getString("SERVICEABILITY")).toString();
			String pinUrl = pinCodeURL.replace("{pinCode}", pincode);
			String pinCodeData = RestUtil.getResponsefromURL(pinUrl);
			String billingCityURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_CITY_STATE")).toString();
			String billCityURL = billingCityURL.replace("{pinCode}",
					billingPincode);

			String billingCityData = RestUtil.getResponsefromURL(billCityURL);

			String shippingCityURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_CITY_STATE")).toString();
			String shipCityURL = shippingCityURL.replace("{pinCode}",
					shippingPincode);

			String shippingCityData = RestUtil.getResponsefromURL(shipCityURL);

			renderRequest.setAttribute("lastOrderAddress", lastOrderAddress);
			renderRequest.setAttribute("pinCodeData", pinCodeData);
			renderRequest.setAttribute("billingCityData", billingCityData);
			renderRequest.setAttribute("shippingCityData", shippingCityData);
		} catch (Exception ex) {
			logger.error("Execption occured at billing view", ex);
		}
		super.doView(renderRequest, renderResponse);

	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());

		String deviceId = null;
		String requestJSON = request.getParameter("jsonData");

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("FNY_TOKEN")) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}
		String requestURL = new StringBuilder(res.getString("API")).append(
				res.getString("CHECKOUT")).toString();
		String newUrl = requestURL.replace("{deviceId}", deviceId);

		String checkOutResponse = RestUtil.getResponseFromPostURL(newUrl,
				requestJSON);

		response.getWriter().write(checkOutResponse);

	}
}
