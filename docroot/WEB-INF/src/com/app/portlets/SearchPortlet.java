package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class SearchPortlet
 */
public class SearchPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(SearchPortlet.class);

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

		String id = request.getParameter("id");

		request.setAttribute("id", id);

		super.doView(request, response);
	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {
		
		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());
		String responseString = null;
		String size = request.getParameter("size");
		String from = request.getParameter("from");
		String searchJSON = request.getParameter("searchJSON");
		
		
			try {
				String searchURL = new StringBuilder(res.getString("API"))
						.append(res.getString("SEARCH_RESULT")).toString();
				String searchProductURL = searchURL.replace("{size}", size)
						.replace("{from}", from);
				responseString = RestUtil.getResponseFromPostURL(
						searchProductURL, searchJSON);
			} catch (Exception e) {
				logger.error("Exception while searchProduct in SearchPortlet",
						e);
			}
		
		response.getWriter().write(responseString);
	}

}
