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
 * Portlet implementation class InviteFriendPortlet
 */
public class InviteFriendPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(InviteFriendPortlet.class);

	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {

		String inviteContent = "No Image";

		String inviteName = "invite-friend";
		ThemeDisplay themeDisplay = (ThemeDisplay) request
				.getAttribute(WebKeys.THEME_DISPLAY);
		/*---------------------Invite Friend content---------------*/
		try {

			JournalArticle journalArticle1 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							inviteName, 0);
			String articleId1 = journalArticle1.getArticleId();
			Double version1 = journalArticle1.getVersion();
			JournalArticleDisplay articleDisplay1 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId1,
							version1, "", "", "", themeDisplay, 0, "");
			if (!(articleDisplay1.equals(null) || articleDisplay1.equals(""))) {
				inviteContent = articleDisplay1.getContent();
			}
		} catch (Exception e) {
			logger.error("Exception while executing InviteFriend", e);
		}

		request.setAttribute("inviteContentData", inviteContent);
		super.doView(request, response);
	}

}
