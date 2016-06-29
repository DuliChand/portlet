package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.app.util.ServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ProfilePortlet
 */
public class ProfilePortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(ProfilePortlet.class);

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
		String customerData = null;
		String subscriptionChannel = null;
		try {
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
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
			String customerId = RestUtil.getCustomerId(request);
			String profileURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_CUSTOMER")).toString()
					.replace("{deviceId}", deviceId);

			JSONObject customerJson = JSONFactoryUtil.createJSONObject();
			JSONObject customerWrapper = JSONFactoryUtil.createJSONObject();
			JSONArray customers = JSONFactoryUtil.createJSONArray();
			JSONObject cust = JSONFactoryUtil.createJSONObject();
			cust.put("customerId", customerId);
			customers.put(cust);
			customerWrapper.put("customers", customers);
			customerJson.put("customerWrapper", customerWrapper);

			customerData = RestUtil.getResponseFromPostURL(profileURL,
					customerJson.toString());

			JSONObject detailJson = JSONFactoryUtil.createJSONObject();

			String cartString = RestUtil.getCartbyDeviceId(res, deviceId);

			JSONObject cartJson = JSONFactoryUtil.createJSONObject(cartString);
			detailJson.put("CartData", cartJson);
			subscriptionChannel = ServiceUtil.getSubscriptionByDeviceId(
					deviceId, res);
			
			
			

		} catch (Exception e) {
			logger.error("Unable to get customer info", e);
		}

		request.setAttribute("customerProfileData", customerData);
		request.setAttribute("subscriptionData", subscriptionChannel);
		super.doView(request, response);

	}
}
