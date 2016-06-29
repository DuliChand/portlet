package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class PreviewNavigationPortlet
 */
public class PreviewNavigationPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(PreviewNavigationPortlet.class);

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		String navData = "";
		try {
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());

			String requestURL = new StringBuilder(res.getString("API")).append(
					res.getString("NAV")).toString();
			
			navData = RestUtil.getResponsefromURL(requestURL);

		} catch (Exception e) {
			logger.error(
					"Exception while executing Preview Navigation portlet doView()",
					e);
		}
		renderRequest.setAttribute("navigationData", navData);

		super.doView(renderRequest, renderResponse);

	}
}