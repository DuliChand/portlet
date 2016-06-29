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
 * AfterSalesPortlet class represents the portlet class responsible for
 * generating navigation bar in page.
 * 
 * @author webshop-team
 */
public class AfterSales extends MVCPortlet {

	private Logger logger = Logger.getLogger(AfterSales.class);

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


		String jspPage = "AfterSalesView.jsp";

		String orderId = request.getParameter("orderId");
		String paymentStatus = request.getParameter("paymentStatus");

		request.setAttribute("orderId", orderId);
		request.setAttribute("paymentStatus", paymentStatus);

		PortletSession portletSession = request.getPortletSession();

		portletSession.setAttribute("orderId", orderId,
				PortletSession.APPLICATION_SCOPE);
		portletSession.setAttribute("paymentStatus", paymentStatus,
				PortletSession.APPLICATION_SCOPE);

		getPortletContext().getRequestDispatcher(
				response.encodeURL("/html/aftersales/" + jspPage)).include(request, response);
	}
}
