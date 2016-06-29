package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ProductLisitng
 */
public class ProductLisitng extends MVCPortlet {

	private Logger logger = Logger.getLogger(ProductLisitng.class);

	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 */

	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		/*
		 * String str = request.getParameter("id"); String type =
		 * request.getParameter("type");
		 * 
		 * String[] strArr = str.split("_"); String id =
		 * strArr[strArr.length-1]; request.setAttribute("id", id);
		 * request.setAttribute("type", type);
		 */

		String isFavouriteEvents = "{}";
		String id = request.getParameter("id");
		String type = request.getParameter("type");

		request.setAttribute("id", id);
		request.setAttribute("type", type);

		PortletSession portletSession = request.getPortletSession();

		portletSession.setAttribute("id", id, PortletSession.APPLICATION_SCOPE);
		portletSession.setAttribute("type", type, PortletSession.APPLICATION_SCOPE);
		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
		String requestJson = "";
		String requestURL = "";
		String deviceId = "";
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
		JSONObject productfilters = JSONFactoryUtil.createJSONObject();
		JSONObject product = JSONFactoryUtil.createJSONObject();

		if (type.equals("event")) {
			requestURL = new StringBuilder(res.getString("API")).append(res.getString("EVENT_PRODUCTS")).toString();

			productfilters.put("eventId", id);
			productfilters.put("limit", 50);
			productfilters.put("from", 0);
			productfilters.put("sortBy", "asc");
			//product.put("productfilters", productfilters);
			requestJson = productfilters.toString();

		} else if (type.equals("catalog")) {
			requestURL = new StringBuilder(res.getString("API")).append(res.getString("EVENT_PRODUCTS")).toString();
			productfilters.put("menuCatId", id);
			productfilters.put("limit", 50);
			productfilters.put("from", 0);
			productfilters.put("sortBy", "asc");
			//product.put("productfilters", productfilters);
			requestJson = productfilters.toString();

		}

		String customerId = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("COOKIE_FNY_CUSTOMER_ID")) {
					customerId = cookies[i].getValue();
					break;
				}
			}
		}
		System.out.println("productData request url" +requestURL +" jsdon===" +  requestJson);
		String productData = RestUtil.getResponseFromPostURL(requestURL, requestJson);
		if (customerId != null) {
			String favouriteEventsURL = new StringBuilder(res.getString("API")).append(
					res.getString("IS_FAVOURITE_EVENT")).toString();
			String favUrl = favouriteEventsURL.replace("{customerId}", customerId);

			String isFavourite = RestUtil.getResponsefromURL(favUrl);

			if (isFavourite != null) {
				isFavouriteEvents = isFavourite;
			}
		}
		request.setAttribute("isFavouriteEvents", isFavouriteEvents);
		request.setAttribute("productData", productData);

		super.doView(request, response);

	}

	public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {
		logger.info("inside srve resourxce");
		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
		String prodListingResponse = null;
		String requestJSON = request.getParameter("jsonData");
		String type = request.getParameter("listingType");
		try {
			if (type.equals("event")) {

				String requestURL = new StringBuilder(res.getString("API")).append(res.getString("EVENT_PRODUCTS"))
						.toString();
				prodListingResponse = RestUtil.getResponseFromPostURL(requestURL, requestJSON);
			}
			if (type.equals("category")) {

				String requestURL = new StringBuilder(res.getString("API")).append(res.getString("CATEGORY_PRODUCTS"))
						.toString();
				prodListingResponse = RestUtil.getResponseFromPostURL(requestURL, requestJSON);
			}
		} catch (Exception e) {
			logger.error("Exception while executing ProductListing serveResource", e);
		}

		response.getWriter().write(prodListingResponse);

	}
}
