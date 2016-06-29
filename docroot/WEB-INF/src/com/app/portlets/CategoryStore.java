package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CategoryStore
 */
public class CategoryStore extends MVCPortlet {

	private Logger logger = Logger.getLogger(CategoryStore.class);

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
		String requestJson = null;
		String requestURL = null;
		String newURL = null;
		String groupCategory = request.getParameter("groupCategory");
		String tag = request.getParameter("tag");

		try {

			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
			JSONObject productfilters = JSONFactoryUtil.createJSONObject();
			JSONObject product = JSONFactoryUtil.createJSONObject();
			requestURL = new StringBuilder(res.getString("API")).append(
					res.getString("STORE_CATEGORY")).toString();
			newURL = requestURL.replace("{groupCategory}", groupCategory)
					.replace("{tag}", tag);
			productfilters.put("limit", 99);
			productfilters.put("skip", 0);
			product.put("productfilters", productfilters);
			requestJson = product.toString();

		} catch (Exception e) {
			logger.error("Exception while executing CategoryStore doView()", e);
		}

		String storeData = RestUtil.getResponseFromPostURL(newURL, requestJson);
		request.setAttribute("storeData", storeData);
		request.setAttribute("groupCategory", groupCategory);
		request.setAttribute("tag", tag);
		super.doView(request, response);

	}

}
