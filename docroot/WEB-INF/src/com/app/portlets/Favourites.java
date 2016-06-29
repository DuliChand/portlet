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
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Favourites
 */
public class Favourites extends MVCPortlet {

	private Logger logger = Logger.getLogger(Favourites.class);

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

		String customerId = null;
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equals(
								"COOKIE_FNY_CUSTOMER_ID")) {
							customerId = cookies[i].getValue();
							break;
						}
					}
				}
			
		
		System.out.print("customerId ========="+ customerId);			
		
		String favEventURL = new StringBuilder(res.getString("API"))
				.append(res.getString("GET_FAVOURITE_EVENT")).toString()
				.replace("{customerId}", customerId);

		String favEventData = RestUtil.getResponsefromURL(favEventURL);

		String favProdURL = new StringBuilder(res.getString("API"))
				.append(res.getString("GET_FAVOURITE_PRODUCT")).toString()
				.replace("{customerId}", customerId);

		String favProdData = RestUtil.getResponsefromURL(favProdURL);

		request.setAttribute("favouriteEventsData", favEventData);
		request.setAttribute("favouriteProductData", favProdData);
		super.doView(request, response);

	}
}
