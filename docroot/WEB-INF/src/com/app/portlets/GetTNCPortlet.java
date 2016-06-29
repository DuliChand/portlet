package com.app.portlets;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class GetTNCPortlet
 */
public class GetTNCPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(GetTNCPortlet.class);

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		String content = "<h3>No Content Available<h3>";

		try {

			String articleName = renderRequest.getParameter("articleName");
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			JournalArticle journalArticle = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleName, 0);
			String articleId = journalArticle.getArticleId();
			Double version = journalArticle.getVersion();
			JournalArticleDisplay articleDisplay = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId,
							version, "", "", "", themeDisplay, 0, "");
			if (!(content.equals(null) || content.equals(""))) {
				content = articleDisplay.getContent();
			}
		} catch (Exception ex) {
			logger.error("Exception while executing GetTNC doView()", ex);
		}
		renderRequest.setAttribute("jspContent", content);
		super.doView(renderRequest, renderResponse);
	}
}
