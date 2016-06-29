package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.LogEvent;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class PreviewPortlet
 */
public class PreviewPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(PreviewPortlet.class);

	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param renderRequest
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 */

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		renderRequest.setAttribute("categoryName", "0");

		String dataString = "";
		String featuredDataString = "";
		String promotionContent1 = "Not Available";
		String promotionContent2 = "Not Available";
		String promotionContent3 = "Not Available";
		String promotionalBottom = "Not Available";
		String marketingBar = "Not Available";

		String promotionMediumContent1 = "Not Available";
		String promotionMediumContent2 = "Not Available";

		String articleName1 = "promotional-banner-1";
		String articleName2 = "promotional-banner-2";
		String articleName3 = "promotional-banner-3";
		String bottomBanner = "promo-home-page-bottom";
		String marketingBarBanner = "marketing-bar";

		String articleMediumName1 = "promotional-banner-medium-1";
		String articleMediumName2 = "promotional-banner-medium-2";

		try {
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
			String categoryName = res.getString("CATEGORY_NAME");
			String featuredCategoryName = res
					.getString("FEATURED_CATEGORY_NAME");

			String requestURL = new StringBuilder(res.getString("API"))
					.append(res.getString("CATEGORY_EVENTS_PREVIEW"))
					.toString().replace("{categoryName}", categoryName);
			
			dataString = RestUtil.getResponsefromURL(requestURL);

			String featuredURL = new StringBuilder(res.getString("API"))
					.append(res.getString("CATEGORY_EVENTS_PREVIEW"))
					.toString().replace("{categoryName}", featuredCategoryName);
			
			featuredDataString = RestUtil.getResponsefromURL(featuredURL);

			renderRequest.setAttribute("homePageData", dataString);
			renderRequest.setAttribute("featuredData", featuredDataString);

			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);

			/*----------------------Promotional Home Bottom content---------------*/
			JournalArticle journalbottomBanner = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							bottomBanner, 0);
			String journalbottomBannerArticleId = journalbottomBanner
					.getArticleId();
			Double journalbottomVersion = journalbottomBanner.getVersion();
			JournalArticleDisplay journalbottomBannerDisplay = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							journalbottomBannerArticleId, journalbottomVersion,
							"", "", "", themeDisplay, 0, "");
			if (journalbottomBannerDisplay.getContent() != null) {
				promotionalBottom = journalbottomBannerDisplay.getContent();
			}

			/*----------------------Marketing Bar content---------------*/
			JournalArticle journalMarketingBar = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							marketingBarBanner, 0);
			String journalMarketingBarArticleId = journalMarketingBar
					.getArticleId();
			Double journalMarketingBarVersion = journalMarketingBar
					.getVersion();
			JournalArticleDisplay journalMarketingBarDisplay = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(),
							journalMarketingBarArticleId,
							journalMarketingBarVersion, "", "", "",
							themeDisplay, 0, "");

			if (journalMarketingBarDisplay.getContent() != null) {
				marketingBar = journalMarketingBarDisplay.getContent();
			}
			/*----------------------Promotional Banner - 1 content---------------*/
			JournalArticle journalArticle1 = JournalArticleLocalServiceUtil
					.getLatestArticleByUrlTitle(themeDisplay.getScopeGroupId(),
							articleName1, 0);
			String articleId1 = journalArticle1.getArticleId();
			Double version1 = journalArticle1.getVersion();
			JournalArticleDisplay articleDisplay1 = JournalContentUtil
					.getDisplay(themeDisplay.getScopeGroupId(), articleId1,
							version1, "", "", "", themeDisplay, 0, "");

			if (articleDisplay1.getContent() != null) {
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

			if (articleDisplay2.getContent() != null) {
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

			if (articleDisplay3.getContent() != null) {
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

		} catch (Exception ex) {
			logger.error("Exception while executing HomePortlet doView()", ex);
		}
		renderRequest.setAttribute("promotionalContent1", promotionContent1);

		renderRequest.setAttribute("promotionalContent2", promotionContent2);

		renderRequest.setAttribute("promotionalContent3", promotionContent3);

		renderRequest.setAttribute("promotionalMediumContent1",
				promotionMediumContent1);

		renderRequest.setAttribute("promotionalMediumContent2",
				promotionMediumContent2);

		renderRequest.setAttribute("promotionalBottom", promotionalBottom);

		renderRequest.setAttribute("marketingBar", marketingBar);

		super.doView(renderRequest, renderResponse);

	}

}