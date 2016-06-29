package com.app.portlets;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.app.util.ServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class OrderHistoryPortlet
 */
public class OrderHistoryPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(OrderHistoryPortlet.class);

	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 */

	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());
		String deviceId = null;
		String orderData = null;
		String lastOrderData = null;
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
		String customerId = RestUtil.getCustomerId(request);
		try {
			String orderURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_RECENT_ORDERS")).toString()
					.replace("{deviceId}", deviceId);

			orderData = RestUtil.getResponsefromURL(orderURL);

			String lastOrderURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_LAST_ORDER")).toString()
					.replace("{customerId}", customerId)
					.replace("{deviceId}", deviceId);

			lastOrderData = RestUtil.getResponsefromURL(lastOrderURL);
			

		} catch (Exception e) {
			logger.error("Failed to get order history data", e);
		}
		request.setAttribute("lastOrderData", lastOrderData);
		request.setAttribute("customerOrderData", orderData);
		
		super.doView(request, response);

	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());
		String deviceId = null;
		String orderDetail = null;
		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
		String orderNumber = request.getParameter("orderNumber");
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(fnyTokenName)) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}

		if (request.getParameter("action").equals("getOrderByNumber")) {
			try {
				
				orderDetail = ServiceUtil.getOrderByNumber(deviceId, res,
						orderNumber);
			} catch (Exception e) {
				logger.error(
						"Exception while executing OrderHistory getOrderByNumber",
						e);
			}
		}
		response.getWriter().write(orderDetail);
	}
}
