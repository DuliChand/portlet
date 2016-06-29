package com.app.portlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.app.util.ServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Credits
 */
public class Credits extends MVCPortlet {
	private Logger logger = Logger.getLogger(Credits.class);

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

		String creditsData = "";
		String deviceId = null;
		BigDecimal rewards = null;
		BigDecimal credits = null;
		JSONObject vouchersJson = null;

		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());
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
			
			String creditsURL = new StringBuilder(res.getString("API"))
					.append(res.getString("STORED_CREDITS")).toString()
					.replace("{customerId}", customerId)
					.replace("{deviceId}", deviceId);

			creditsData = RestUtil.getResponsefromURL(creditsURL);
			
			rewards = ServiceUtil.getRewardsForCustomer(res,
					deviceId);
			credits = ServiceUtil.getCustomerCreditSum(res,
					customerId, deviceId);
			
			String voucherList = ServiceUtil.getCustomerAllVouchers(res,
					deviceId);
			vouchersJson = JSONFactoryUtil
					.createJSONObject(voucherList);

		} catch (Exception e) {
			logger.error("unable to get credit data for customerId-"
					+ customerId + "---exception----", e);
		}
		request.setAttribute("customerCreditsData", creditsData);
		request.setAttribute("customerRewards", rewards);
		request.setAttribute("customerCredits", credits);
		request.setAttribute("customerVouchers", vouchersJson);
		super.doView(request, response);

	}

}
