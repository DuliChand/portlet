package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Category
 */
public class Category extends MVCPortlet {

	private Logger logger = Logger.getLogger(Category.class);

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

		String categoryName = request.getParameter("categoryName");

		String catData = "";
	
		PortletSession portletSession = request.getPortletSession();

		String promotionContent1 = "No Image";
		String promotionContent2 = "No Image";
		String promotionContent3 = "No Image";
		
		String promotionMediumContent1 = "Not Available";
		String promotionMediumContent2 = "Not Available";
		
		String promotionSmallContent1 = "Not Available";
		String promotionSmallContent2 = "Not Available";

		String articleName1 = "promotional-banner-1";
		String articleName2 = "promotional-banner-2";
		String articleName3 = "promotional-banner-3";
		
		String articleMediumName1 = "promotional-banner-medium-1";
		String articleMediumName2 = "promotional-banner-medium-2";
		
		String articleSmallName1 = "promotional-banner-small-1";
		String articleSmallName2 = "promotional-banner-small-2";
		
		
		
		try {

			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
			//String categoryId = res.getString(categoryName);
			String categoryId = categoryName;
			String requestURL = new StringBuilder(res.getString("API"))
					.append(res.getString("CATEGORY_EVENTS")).toString()
					.replace("{categoryName}", categoryId);
			
			catData = RestUtil.getResponsefromURL(requestURL);

			ThemeDisplay themeDisplay = (ThemeDisplay) request
					.getAttribute(WebKeys.THEME_DISPLAY);

			/*----------------------Promotional Banner - 1 content---------------*/
			JournalArticle journalArticle1 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleName1, 0);
			String articleId1 = journalArticle1.getArticleId();
			Double version1 = journalArticle1.getVersion();
			JournalArticleDisplay articleDisplay1 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId1,
							version1, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent1.equals(null) || promotionContent1
					.equals(""))) {
				promotionContent1 = articleDisplay1.getContent();
			}

			/*----------------------Promotional Banner - 2 content---------------*/
			JournalArticle journalArticle2 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleName2, 0);
			String articleId2 = journalArticle2.getArticleId();
			Double version2 = journalArticle2.getVersion();
			JournalArticleDisplay articleDisplay2 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId2,
							version2, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent2.equals(null) || promotionContent2
					.equals(""))) {
				promotionContent2 = articleDisplay2.getContent();
			}

			/*----------------------Promotional Banner - 3 content---------------*/
			JournalArticle journalArticle3 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleName3, 0);
			String articleId3 = journalArticle3.getArticleId();
			Double version3 = journalArticle3.getVersion();
			JournalArticleDisplay articleDisplay3 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId3,
							version3, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent3.equals(null) || promotionContent3
					.equals(""))) {
				promotionContent3 = articleDisplay3.getContent();
			}
			
			
			/*----------------------Medium Promotional Banner - 1 content---------------*/
			JournalArticle journalMediumArticle1 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleMediumName1, 0);
			String articleMediumId1 = journalMediumArticle1.getArticleId();
			Double versionMedium1 = journalMediumArticle1.getVersion();
			JournalArticleDisplay articleMediumDisplay1 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							articleMediumId1, versionMedium1, "", "", "",
							themeDisplay, 0, "");

			if (articleMediumDisplay1.getContent() != null) {
				promotionMediumContent1 = articleMediumDisplay1.getContent();
			}
			/*----------------------Medium Promotional Banner - 2 content---------------*/
			JournalArticle journalMediumArticle2 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleMediumName2, 0);
			String articleMediumId2 = journalMediumArticle2.getArticleId();
			Double versionMedium2 = journalMediumArticle2.getVersion();
			JournalArticleDisplay articleMediumDisplay2 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							articleMediumId2, versionMedium2, "", "", "",
							themeDisplay, 0, "");

			if (articleMediumDisplay2.getContent() != null) {
				promotionMediumContent2 = articleMediumDisplay2.getContent();
			}
			
			/*----------------------Small Promotional Banner - 1 content---------------*/
			JournalArticle journalSmallArticle1 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleSmallName1, 0);
			String articleSmallId1 = journalSmallArticle1.getArticleId();
			Double versionSmall1 = journalSmallArticle1.getVersion();
			JournalArticleDisplay articleSmallDisplay1 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							articleSmallId1, versionSmall1, "", "", "",
							themeDisplay, 0, "");

			if (articleSmallDisplay1.getContent() != null) {
				promotionSmallContent1 = articleSmallDisplay1.getContent();
			}
			
			/*----------------------Small Promotional Banner - 2 content---------------*/
			JournalArticle journalSmallArticle2 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleSmallName2, 0);
			String articleSmallId2 = journalSmallArticle2.getArticleId();
			Double versionSmall2 = journalSmallArticle2.getVersion();
			JournalArticleDisplay articleSmallDisplay2 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							articleSmallId2, versionSmall2, "", "", "",
							themeDisplay, 0, "");

			if (articleSmallDisplay2.getContent() != null) {
				promotionSmallContent2 = articleSmallDisplay2.getContent();
			}

		} catch (Exception e) {
			logger.error("Exception while executing Category Portlet doView()",
					e);
		}

		request.setAttribute("categoryData", catData);

		request.setAttribute("categoryName", categoryName);

		request.setAttribute("promotionalContent1", promotionContent1);

		request.setAttribute("promotionalContent2", promotionContent2);

		request.setAttribute("promotionalContent3", promotionContent3);
		
		request.setAttribute("promotionalMediumContent1",
				promotionMediumContent1);

		request.setAttribute("promotionalMediumContent2",
				promotionMediumContent2);

		request.setAttribute("promotionalSmallContent1",
				promotionSmallContent1);

		request.setAttribute("promotionalSmallContent2",
				promotionSmallContent2);

		
		portletSession.setAttribute("categoryName", categoryName,
				PortletSession.APPLICATION_SCOPE);

		super.doView(request, response);

	}
}