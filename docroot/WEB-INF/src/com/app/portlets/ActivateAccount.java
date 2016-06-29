package com.app.portlets;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * ActivateAccountPortlet class represents the portlet class responsible for
 * generating navigation bar in page.
 * 
 * @author webshop-team
 */
public class ActivateAccount extends MVCPortlet {

	private Logger logger = Logger.getLogger(ActivateAccount.class);

	
	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 */

	@RenderMode(name = "VIEW")
	public void showEventInfo(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {
		
		String jspPage = "activateAccountView.jsp";

		String activationCode = request.getParameter("activationCode");

		String customerId = request.getParameter("customerId");

		request.setAttribute("activationCode", activationCode);

		request.setAttribute("customerId", customerId);

		PortletSession portletSession = request.getPortletSession();

		portletSession.setAttribute("activationCode", activationCode,
				PortletSession.APPLICATION_SCOPE);

		portletSession.setAttribute("customerId", customerId,
				PortletSession.APPLICATION_SCOPE);

		getPortletContext().getRequestDispatcher(
				response.encodeURL("/html/activateaccount/" + jspPage)).include(request, response);
	}
}