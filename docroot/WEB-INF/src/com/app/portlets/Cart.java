package com.app.portlets;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class Cart
 */
public class Cart extends MVCPortlet {

	private Logger logger = Logger.getLogger(Cart.class);

	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param renderRequest
	 * @param response
	 * @throws IOException
	 * @throws PortletException
	 */

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");

		String latestOfferContent = null;
		String topHeaderContent = null;
		String infoLinkContent = null;
		String topBrandsContent = null;
		String copyRightContent = null;
		String companyLogoContent = null;
		String cartPagePromoContent = null;
		String freeProductPromoContent = "NA";
		String cartData = null;
		String cartdata = null;
		String deviceId = null;
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		try {
			String productDetailData = "{}";
			String latestOfferPromoName = "top-header-latest-offer";
			String topHeaderName = "top-header-bar";
			String infoLinkName = "info-link-block";
			String topBrandsName = "top-brands-block";
			String copyRightName = "copyright-block";
			String companyLogoName = "company-logo-block";
			String cartPagePromoName = "cart-page-promo";
			String freeProductPromoName = "free-product-promo";

			String pageName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
			renderRequest.setAttribute("pageName", pageName);

			try {
				/*----------------------Latest Offer Block content---------------*/

				JournalArticle latestOfferBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), latestOfferPromoName, 0);
				String latestOfferArticleId = latestOfferBanner.getArticleId();
				Double latestOfferVersion = latestOfferBanner.getVersion();
				JournalArticleDisplay latestOfferBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), latestOfferArticleId, latestOfferVersion, "", "", "", themeDisplay, 0, "");
				if (latestOfferBannerDisplay.getContent() != null) {
					latestOfferContent = latestOfferBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + latestOfferPromoName);
			}
			try {
				/*----------------------Top Header content---------------*/
				JournalArticle topHeaderNameBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), topHeaderName, 0);
				String topHeaderNameArticleId = topHeaderNameBanner.getArticleId();
				Double topHeaderNameVersion = topHeaderNameBanner.getVersion();
				JournalArticleDisplay topHeadeBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), topHeaderNameArticleId, topHeaderNameVersion, "", "", "", themeDisplay, 0,
						"");
				if (topHeadeBannerDisplay.getContent() != null) {
					topHeaderContent = topHeadeBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + topHeaderName);
			}

			try {
				/*----------------------Information Block content---------------*/

				JournalArticle infoLinkNameBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), infoLinkName, 0);
				String infoLinkNameArticleId = infoLinkNameBanner.getArticleId();
				Double infoLinkNameVersion = infoLinkNameBanner.getVersion();
				JournalArticleDisplay infoLinkNameBannerDisplay = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(), infoLinkNameArticleId, infoLinkNameVersion, "", "",
								"", themeDisplay, 0, "");
				if (infoLinkNameBannerDisplay.getContent() != null) {
					infoLinkContent = infoLinkNameBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + infoLinkName);
			}

			try {
				/*----------------------Top Brands Block content---------------*/

				JournalArticle topBrandsNameBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), topBrandsName, 0);
				String topBrandsNameArticleId = topBrandsNameBanner.getArticleId();
				Double topBrandsNameVersion = topBrandsNameBanner.getVersion();
				JournalArticleDisplay topBrandsNameBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), topBrandsNameArticleId, topBrandsNameVersion, "", "", "", themeDisplay, 0,
						"");
				if (topBrandsNameBannerDisplay.getContent() != null) {
					topBrandsContent = topBrandsNameBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + topBrandsName);
			}

			try {
				/*----------------------Top Brands Block content---------------*/

				JournalArticle copyRightNameBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), copyRightName, 0);
				String copyRightNameArticleId = copyRightNameBanner.getArticleId();
				Double copyRightNameVersion = copyRightNameBanner.getVersion();
				JournalArticleDisplay copyRightNameBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), copyRightNameArticleId, copyRightNameVersion, "", "", "", themeDisplay, 0,
						"");
				if (copyRightNameBannerDisplay.getContent() != null) {
					copyRightContent = copyRightNameBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + copyRightName);
			}

			try {
				/*----------------------Company Logo Block content---------------*/

				JournalArticle companyLogoBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), companyLogoName, 0);
				String companyLogoArticleId = companyLogoBanner.getArticleId();
				Double companyLogoVersion = companyLogoBanner.getVersion();
				JournalArticleDisplay companyLogoBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), companyLogoArticleId, companyLogoVersion, "", "", "", themeDisplay, 0, "");
				if (companyLogoBannerDisplay.getContent() != null) {
					companyLogoContent = companyLogoBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + companyLogoName);
			}

			try {
				/*----------------------Cart Page Promo Block content---------------*/

				JournalArticle cartPageBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(themeDisplay
						.getScopeGroupId(), cartPagePromoName, 0);
				String cartPageArticleId = cartPageBanner.getArticleId();
				Double cartPageVersion = cartPageBanner.getVersion();
				JournalArticleDisplay cartPageBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
						.getScopeGroupId(), cartPageArticleId, cartPageVersion, "", "", "", themeDisplay, 0, "");
				if (cartPageBannerDisplay.getContent() != null) {
					cartPagePromoContent = cartPageBannerDisplay.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-" + cartPagePromoName);
			}
			try {
				/*----------------------Free Product content---------------*/

				JournalArticle freeProductOfferBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
						themeDisplay.getScopeGroupId(), freeProductPromoName, 0);
				String freeProductOfferArticleId = freeProductOfferBanner.getArticleId();
				Double freeProductOfferVersion = freeProductOfferBanner.getVersion();
				Long expireTime = freeProductOfferBanner.getExpirationDate().getTime();
				Long displayTime = freeProductOfferBanner.getDisplayDate().getTime();
				Long currentTime = new Date().getTime();
				if (currentTime <= expireTime && currentTime >= displayTime) {
					JournalArticleDisplay freeProductBannerDisplay = JournalContentUtil.getDisplay(themeDisplay
							.getScopeGroupId(), freeProductOfferArticleId, freeProductOfferVersion, "", "", "",
							themeDisplay, 0, "");
					if (freeProductBannerDisplay.getContent() != null) {
						freeProductPromoContent = freeProductBannerDisplay.getContent();
					}
				}

			} catch (Exception ne) {
				logger.error("No Article exist with name-" + freeProductPromoName);
			}
			ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());

			Cookie[] cookies = renderRequest.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals(fnyTokenName)) {
						deviceId = cookies[i].getValue();
						break;
					}
				}
			}
			String requestJson = null;
			JSONObject cartbody = JSONFactoryUtil.createJSONObject();
			String cartURL = new StringBuilder(res.getString("API")).append(res.getString("GET_CART")).toString();

			cartbody.put("deviceId", deviceId);
			cartbody.put("pullInventory", false);
			requestJson = cartbody.toString();
			cartData = RestUtil.getResponseFromPostURL(cartURL, requestJson);

			String curUrl = themeDisplay.getURLPortal() + themeDisplay.getURLCurrent();

			String mainSrcName = getmainURLSrc(curUrl);
			renderRequest.setAttribute("mainSrcName", mainSrcName);
			String[] idVal = getEventIdandProductIdbyURL(curUrl);
			/*
			 * if (idVal != null) { String eventId = idVal[idVal.length - 2];
			 * String tempvendorProductId = idVal[idVal.length - 1]; String
			 * vendorProductId = tempvendorProductId.substring(0,6); String
			 * requestURL = new StringBuilder(res.getString("API"))
			 * .append(res.getString("GET_PRODUCT")).toString()
			 * .replace("{vendorProductId}", vendorProductId)
			 * .replace("{eventId}", eventId); productDetailData = RestUtil
			 * .getResponsefromURL(requestURL);
			 * renderRequest.setAttribute("srcPageName", "product"); } else{
			 * renderRequest.setAttribute("srcPageName", "productlisting"); }
			 */
			renderRequest.setAttribute("productDetailData", productDetailData);
		} catch (Exception ex) {
			logger.info("Exception in cart portlet", ex);
		}
		
		renderRequest.setAttribute("latestOfferContent", latestOfferContent);

		renderRequest.setAttribute("topHeaderContent", topHeaderContent);

		renderRequest.setAttribute("infoLinkContent", infoLinkContent);

		renderRequest.setAttribute("topBrandsContent", topBrandsContent);

		renderRequest.setAttribute("copyRightContent", copyRightContent);

		renderRequest.setAttribute("companyLogoContent", companyLogoContent);

		renderRequest.setAttribute("fnyTokenName", fnyTokenName);

		renderRequest.setAttribute("cartdata", cartData);

		renderRequest.setAttribute("cartPagePromoContent", cartPagePromoContent);

		renderRequest.setAttribute("freeProductPromoContent", freeProductPromoContent);

		super.doView(renderRequest, renderResponse);
	}

	public String getmainURLSrc(String currentURL) {

		String mainUrlSrc = "default";
		String[] tokens = currentURL.split("/");
		if (tokens.length == 5) {
			mainUrlSrc = "status";
		}
		return mainUrlSrc;
	}

	public void serveResource(ResourceRequest request, ResourceResponse response) throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
		String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
		String deviceId = null;
		String cartData = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(fnyTokenName)) {
					deviceId = cookies[i].getValue();
					break;
				}
			}
		}

		try {
			String requestJson = null;
			JSONObject cartbody = JSONFactoryUtil.createJSONObject();
			String cartURL = new StringBuilder(res.getString("API")).append(res.getString("GET_CART")).toString();

			cartbody.put("deviceId", deviceId);
			cartbody.put("pullInventory", false);
			requestJson = cartbody.toString();
			cartData = RestUtil.getResponseFromPostURL(cartURL, requestJson);

		} catch (Exception ex) {
			logger.error("Exception while getCart in Cart portlet", ex);
		}

		response.getWriter().write(cartData);
	}

	public String[] getEventIdandProductIdbyURL(String currentUrl) {
		String[] idValues = null;
		try {
			String catname = null;
			String lastprtnumber = null, temprdtnumber = null;
			String[] temprdtdata = null;
			String tempdata = null;
			String[] tokens = currentUrl.split("/");
			if (tokens.length == 5) {
				catname = tokens[3];
				tempdata = tokens[4];
				if (catname.equalsIgnoreCase("women") || catname.equalsIgnoreCase("men")
						|| catname.equalsIgnoreCase("home_and_living") || catname.equalsIgnoreCase("kids")
						|| catname.equalsIgnoreCase("lounge") || catname.equalsIgnoreCase("accessories")
						|| catname.equalsIgnoreCase("shops")) {
					idValues = null;
				} else {
					try {
						temprdtdata = tempdata.split("_");
						temprdtnumber = temprdtdata[temprdtdata.length - 1];
						lastprtnumber = temprdtnumber.substring(0, 6);
						if (!(lastprtnumber.equals(null))) {
							idValues = temprdtdata;
						}
					} catch (NumberFormatException ne) {
						logger.error("NumberFormatException while getEventIdandProductIdbyURL in Cart portlet", ne);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception while getEventIdandProductIdbyURL in Cart portlet", e);
		}
		return idValues;
	}
}
