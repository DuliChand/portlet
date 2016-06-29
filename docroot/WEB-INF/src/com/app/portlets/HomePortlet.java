package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class HomePortlet
 */
public class HomePortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(HomePortlet.class);

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
		String promotionContent1 = "";
		String promotionContent2 = "";
		String promotionContent3 = "";
		String promotionalBottom = "";
		String marketingBar = "";

		String promotionMediumContent1 = "";
		String promotionMediumContent2 = "";

		String promotionSmallContent1 = "";
		String promotionSmallContent2 = "";
		String staticHeroBannerContent = "";

		String articleName1 = "promotional-banner-1";
		String articleName2 = "promotional-banner-2";
		String articleName3 = "promotional-banner-3";
		String bottomBanner = "promo-home-page-bottom";
		String marketingBarBanner = "marketing-bar";

		String articleMediumName1 = "promotional-banner-medium-1";
		String articleMediumName2 = "promotional-banner-medium-2";

		String articleSmallName1 = "promotional-banner-small-1";
		String articleSmallName2 = "promotional-banner-small-2";
		String staticHeroBannerName = "static-hero-banner";

		try {
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
			String categoryName = res.getString("CATEGORY_NAME");
			String featuredCategoryName = res
					.getString("FEATURED_CATEGORY_NAME");

			String requestURL = new StringBuilder(res.getString("API"))
					.append(res.getString("CATEGORY_EVENTS")).toString()
					.replace("{categoryName}", categoryName);

			dataString = RestUtil.getResponsefromURL(requestURL);

			String featuredURL = new StringBuilder(res.getString("API"))
					.append(res.getString("CATEGORY_EVENTS")).toString()
					.replace("{categoryName}", featuredCategoryName);

			featuredDataString = RestUtil.getResponsefromURL(featuredURL);

			renderRequest.setAttribute("homePageData", dataString);
			renderRequest.setAttribute("featuredData", featuredDataString);

			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);

			/*----------------------Promotional Home Bottom content---------------*/
			try {
				JournalArticle journalbottomBanner = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(), bottomBanner, 0);
				String journalbottomBannerArticleId = journalbottomBanner
						.getArticleId();
				Double journalbottomVersion = journalbottomBanner.getVersion();
				JournalArticleDisplay journalbottomBannerDisplay = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(),
								journalbottomBannerArticleId,
								journalbottomVersion, "", "", "", themeDisplay,
								0, "");
				if (journalbottomBannerDisplay.getContent() != null) {
					promotionalBottom = journalbottomBannerDisplay.getContent();
				}

			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + bottomBanner);
			}

			/*----------------------Marketing Bar content---------------*/
			try {
				JournalArticle journalMarketingBar = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
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
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + marketingBarBanner);
			}
			/*----------------------Promotional Banner - 1 content---------------*/
			try {
				JournalArticle journalArticle1 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(), articleName1, 0);
				String articleId1 = journalArticle1.getArticleId();
				Double version1 = journalArticle1.getVersion();
				JournalArticleDisplay articleDisplay1 = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(), articleId1,
								version1, "", "", "", themeDisplay, 0, "");

				if (articleDisplay1.getContent() != null) {
					promotionContent1 = articleDisplay1.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleName1);
			}

			/*----------------------Promotional Banner - 2 content---------------*/
			try {
				JournalArticle journalArticle2 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(), articleName2, 0);
				String articleId2 = journalArticle2.getArticleId();
				Double version2 = journalArticle2.getVersion();
				JournalArticleDisplay articleDisplay2 = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(), articleId2,
								version2, "", "", "", themeDisplay, 0, "");

				if (articleDisplay2.getContent() != null) {
					promotionContent2 = articleDisplay2.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleName2);
			}
			/*----------------------Promotional Banner - 3 content---------------*/
			try {
				JournalArticle journalArticle3 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(), articleName3, 0);
				String articleId3 = journalArticle3.getArticleId();
				Double version3 = journalArticle3.getVersion();
				JournalArticleDisplay articleDisplay3 = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(), articleId3,
								version3, "", "", "", themeDisplay, 0, "");

				if (articleDisplay3.getContent() != null) {
					promotionContent3 = articleDisplay3.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleName3);
			}
			/*----------------------Medium Promotional Banner - 1 content---------------*/
			try {
				JournalArticle journalMediumArticle1 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
								articleMediumName1, 0);
				String articleMediumId1 = journalMediumArticle1.getArticleId();
				Double versionMedium1 = journalMediumArticle1.getVersion();
				JournalArticleDisplay articleMediumDisplay1 = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(),
								articleMediumId1, versionMedium1, "", "", "",
								themeDisplay, 0, "");

				if (articleMediumDisplay1.getContent() != null) {
					promotionMediumContent1 = articleMediumDisplay1
							.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleMediumName1);
			}
			/*----------------------Medium Promotional Banner - 2 content---------------*/
			try {
				JournalArticle journalMediumArticle2 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
								articleMediumName2, 0);
				String articleMediumId2 = journalMediumArticle2.getArticleId();
				Double versionMedium2 = journalMediumArticle2.getVersion();
				JournalArticleDisplay articleMediumDisplay2 = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(),
								articleMediumId2, versionMedium2, "", "", "",
								themeDisplay, 0, "");

				if (articleMediumDisplay2.getContent() != null) {
					promotionMediumContent2 = articleMediumDisplay2
							.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleMediumName2);
			}

			/*----------------------Small Promotional Banner - 1 content---------------*/
			try {
				JournalArticle journalSmallArticle1 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
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
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleSmallName1);
			}
			/*----------------------Small Promotional Banner - 2 content---------------*/
			try {
				JournalArticle journalSmallArticle2 = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
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
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + articleSmallName2);
			}
			
			/*----------------------Static Hero Banner content---------------*/
			try {
				JournalArticle journalStaticHeroBanner = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
								staticHeroBannerName, 0);
				String staticHeroBannerId = journalStaticHeroBanner.getArticleId();
				Double versionstaticHeroBanner = journalStaticHeroBanner.getVersion();
				JournalArticleDisplay staticHeroBannerDisplay = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(),
								staticHeroBannerId, versionstaticHeroBanner, "", "", "",
								themeDisplay, 0, "");

				if (staticHeroBannerDisplay.getContent() != null) {
					staticHeroBannerContent = staticHeroBannerDisplay.getContent();
				}
			} catch (NoSuchArticleException ne) {
				logger.error("No Article exist with name-" + staticHeroBannerName);
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

		renderRequest.setAttribute("promotionalSmallContent1",
				promotionSmallContent1);

		renderRequest.setAttribute("promotionalSmallContent2",
				promotionSmallContent2);

		renderRequest.setAttribute("promotionalBottom", promotionalBottom);

		renderRequest.setAttribute("marketingBar", marketingBar);
				
		renderRequest.setAttribute("staticHeroBannerContent", staticHeroBannerContent);

		super.doView(renderRequest, renderResponse);

	}

}