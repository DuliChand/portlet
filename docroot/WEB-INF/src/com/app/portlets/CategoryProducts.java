package com.app.portlets;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class CategoryProducts
 */
public class CategoryProducts extends MVCPortlet {
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

	public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {

		PortletSession portletSession = request.getPortletSession();

		String catData = "";
		try {

			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
			String source = PortalUtil.getOriginalServletRequest(httpRequest).getParameter("src");
			String id = PortalUtil.getOriginalServletRequest(httpRequest).getParameter("id");

			ResourceBundle res = getPortletConfig().getResourceBundle(Locale.getDefault());
			String sourcenullvalue = "value" + source;

			/*----------------------Fetch Category_Events when src is null---------------*/

			if (sourcenullvalue.equals("valuenull")) {
				try {

					String uristring = themeDisplay.getLayout().getFriendlyURL();
					String categoryName = uristring.substring(1);
					/*
					 * String categoryId = res.getString(categoryName);
					 * categoryName = categoryId;
					 */

					String requestURL = new StringBuilder(res.getString("API"))
							.append(res.getString("CATEGORY_EVENTS")).toString()
							.replace("{categoryName}", categoryName);

					catData = RestUtil.getResponsefromURL(requestURL);
					getCategoryEvent(request, response);
					request.setAttribute("categoryName", categoryName);

					request.setAttribute("categoryData", catData);
					request.setAttribute("pageType", "groupcategory");
					portletSession.setAttribute("categoryName", categoryName, PortletSession.APPLICATION_SCOPE);
				} catch (Exception e) {
					logger.error("Exception while executing CategoryProduct portlet doView() when src is null ", e);

				}
			}

			/*----------------------Fetch Category_Events when src is mainNav---------------*/
			else if (source.equalsIgnoreCase("mainNav")) {
				try {

					String uristring = themeDisplay.getLayout().getFriendlyURL();
					String categoryName = uristring.substring(1);
					/*
					 * String categoryId = res.getString(categoryName);
					 * categoryName = categoryId;
					 */

					String requestURL = new StringBuilder(res.getString("API"))
							.append(res.getString("CATEGORY_EVENTS")).toString()
							.replace("{categoryName}", categoryName);
					catData = RestUtil.getResponsefromURL(requestURL);
					getCategoryEvent(request, response);
					request.setAttribute("categoryName", categoryName);
					request.setAttribute("categoryData", catData);
					request.setAttribute("pageType", "groupcategory");
					portletSession.setAttribute("categoryName", categoryName, PortletSession.APPLICATION_SCOPE);
				} catch (Exception e) {
					logger.error("Exception while executing CategoryProduct portlet doView() and when src is mainNav",
							e);
				}

			} else if (source.equals("event") || source.equals("category")) {
				String isFavouriteEvents = "{}";

				String type = source;
				try {
					request.setAttribute("sourceid", id);
					request.setAttribute("sourcetype", type);

					/* portletSession = request.getPortletSession(); */

					portletSession.setAttribute("id", id, PortletSession.APPLICATION_SCOPE);
					portletSession.setAttribute("type", type, PortletSession.APPLICATION_SCOPE);
					res = getPortletConfig().getResourceBundle(Locale.getDefault());
					String requestJson = "";
					String requestURL = "";
					String deviceId = "";
					String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							if (cookies[i].getName().equals(fnyTokenName)) {
								deviceId = cookies[i].getValue();
								break;
							}
						}
					}
					JSONObject productfilters = JSONFactoryUtil.createJSONObject();
					JSONObject product = JSONFactoryUtil.createJSONObject();

					if (type.equals("event")) {
						requestURL = new StringBuilder(res.getString("API")).append(res.getString("EVENT_PRODUCTS"))
								.toString();

						productfilters.put("eventId", id);
						productfilters.put("limit", 99);
						productfilters.put("skip", 0);
						product.put("productfilters", productfilters);
						requestJson = product.toString();

					} else if (type.equals("category")) {
						requestURL = new StringBuilder(res.getString("API")).append(res.getString("CATEGORY_PRODUCTS"))
								.toString();
						productfilters.put("categoryId", id);
						productfilters.put("limit", 99);
						productfilters.put("skip", 0);
						product.put("productfilters", productfilters);
						requestJson = product.toString();

					}

					String productData = RestUtil.getResponseFromPostURL(requestURL, requestJson);
					String favouriteEventsURL = new StringBuilder(res.getString("API")).append(
							res.getString("IS_FAVOURITE_EVENT")).toString();
					String favUrl = favouriteEventsURL.replace("{deviceId}", deviceId).replace("{eventId}", id);

					String isFavourite = RestUtil.getResponsefromURL(favUrl);

					if (isFavourite != null) {
						isFavouriteEvents = isFavourite;
					}

					request.setAttribute("isFavouriteEvents", isFavouriteEvents);

					request.setAttribute("productData", productData);

					request.setAttribute("pageType", "categoryproductlisting");
				} catch (Exception e) {
					logger.error(
							"Exception while executing CategoryProduct portlet doView() and when src are event or category",
							e);

				}
			} else if (source.equals("productdetails")) {

				String isFavouriteProduct = "{}";
				try {

					String productPagePromoContent = null;
					String productPagePromoName = "product-page-promo";
					httpRequest = PortalUtil.getHttpServletRequest(request);
					String vendorProductId = PortalUtil.getOriginalServletRequest(httpRequest).getParameter(
							"vendorProductId");

					/*
					 * String vendorProductId = request
					 * .getParameter("vendorProductId");
					 */String eventId = PortalUtil.getOriginalServletRequest(httpRequest).getParameter("eventId");

					/* String eventId = request.getParameter("eventId"); */
					String deviceId = null;
					res = getPortletConfig().getResourceBundle(Locale.getDefault());
					String fnyTokenName = PropsUtil.get("FNY_TOKEN_NAME");
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (int i = 0; i < cookies.length; i++) {
							if (cookies[i].getName().equals(fnyTokenName)) {
								deviceId = cookies[i].getValue();
								break;
							}
						}
					}

					String requestURL = new StringBuilder(res.getString("API")).append(res.getString("GET_PRODUCT"))
							.toString().replace("{vendorProductId}", vendorProductId).replace("{eventId}", eventId);

					String productDetailData = RestUtil.getResponsefromURL(requestURL);

					String productName = JSONFactoryUtil.createJSONObject(productDetailData)
							.getJSONObject("vendorProductWrapper").getJSONObject("vendorProduct")
							.getJSONObject("product").getString("name");
					String amount = JSONFactoryUtil.createJSONObject(productDetailData)
							.getJSONObject("vendorProductWrapper").getJSONObject("liveinventory")
							.getString("salePriceWithVAT");
					String erpskuId = JSONFactoryUtil.createJSONObject(productDetailData)
							.getJSONObject("vendorProductWrapper").getJSONObject("liveinventory").getString("erpSku");
					String thumbnail = JSONFactoryUtil.createJSONObject(productDetailData)
							.getJSONObject("vendorProductWrapper").getJSONObject("vendorProduct")
							.getJSONArray("vendorProductInventories").getJSONObject(0).getJSONArray("imageView")
							.getJSONObject(0).getString("thumbURL");

					String reqURL = new StringBuilder(res.getString("API")).append(
							res.getString("ADD_PRODUCT_BROWSING_HISTORY")).toString();

					String browseURL = reqURL.replace("{deviceId}", deviceId);

					JSONObject productfilters = JSONFactoryUtil.createJSONObject();
					JSONObject product = JSONFactoryUtil.createJSONObject();
					JSONArray productArray = JSONFactoryUtil.createJSONArray();
					product.put("productName", productName);
					product.put("salePrice", amount);
					product.put("thumbnailUrl", thumbnail);
					product.put("vendorProductId", vendorProductId);
					product.put("eventId", eventId);

					productArray.put(product);

					productfilters.put("product", productArray);
					String productJson = productfilters.toString();
					String browseData = RestUtil.getResponseFromPostURL(browseURL, productJson);

					String browseHistoryURL = new StringBuilder(res.getString("API"))
							.append(res.getString("GET_PRODUCT_BROWSING_HISTORY")).toString()
							.replace("{deviceId}", deviceId);

					String browseHistoryData = RestUtil.getResponsefromURL(browseHistoryURL);
					String favouriteProduct = new StringBuilder(res.getString("API")).append(
							res.getString("IS_FAVOURITE_PRODUCT")).toString();

					String favUrl = favouriteProduct.replace("{deviceId}", deviceId).replace("{vendorProductId}",
							vendorProductId);

					String isFavourite = RestUtil.getResponsefromURL(favUrl);

					if (isFavourite != null) {
						isFavouriteProduct = isFavourite;
					}
					try {
						/*----------------------Product Page Promo Block content---------------*/
						JournalArticle productPageBanner = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(), productPagePromoName, 0);
						String productPageArticleId = productPageBanner.getArticleId();
						Double productPageVersion = productPageBanner.getVersion();
						JournalArticleDisplay productPageBannerDisplay = JournalContentUtil.getDisplay(
								themeDisplay.getScopeGroupId(), productPageArticleId, productPageVersion, "", "", "",
								themeDisplay, 0, "");
						if (productPageBannerDisplay.getContent() != null) {
							productPagePromoContent = productPageBannerDisplay.getContent();
						}
					} catch (Exception ne) {
						logger.error("No Article exist with name-" + productPagePromoName);
					}

					request.setAttribute("isFavouriteProduct", isFavouriteProduct);
					request.setAttribute("browseHistoryData", browseHistoryData);
					request.setAttribute("productDetailData", productDetailData);
					request.setAttribute("salesprice", amount);
					request.setAttribute("vendorProductId", vendorProductId);
					request.setAttribute("eventId", eventId);
					request.setAttribute("erpskuno", erpskuId);
					request.setAttribute("productPagePromoContent", productPagePromoContent);
					request.setAttribute("pageType", "productdetails");

				} catch (Exception e) {
					logger.error("Exception while executing ProductDetail doView() when src is productdetails", e);
				}

			}

			super.doView(request, response);
		} catch (Exception e) {

			logger.error("Exception in CategoryProduct portlet doView()", e);
		}

	}

	public void getCategoryEvent(RenderRequest request, RenderResponse response) throws IOException, PortletException {
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
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

			/*----------------------Promotional Banner - 1 content---------------*/
			JournalArticle journalArticle1 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleName1, 0);
			String articleId1 = journalArticle1.getArticleId();
			Double version1 = journalArticle1.getVersion();
			JournalArticleDisplay articleDisplay1 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleId1, version1, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent1.equals(null) || promotionContent1.equals(""))) {
				promotionContent1 = articleDisplay1.getContent();
			}

			/*----------------------Promotional Banner - 2 content---------------*/
			JournalArticle journalArticle2 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleName2, 0);
			String articleId2 = journalArticle2.getArticleId();
			Double version2 = journalArticle2.getVersion();
			JournalArticleDisplay articleDisplay2 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleId2, version2, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent2.equals(null) || promotionContent2.equals(""))) {
				promotionContent2 = articleDisplay2.getContent();
			}

			/*----------------------Promotional Banner - 3 content---------------*/
			JournalArticle journalArticle3 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleName3, 0);
			String articleId3 = journalArticle3.getArticleId();
			Double version3 = journalArticle3.getVersion();
			JournalArticleDisplay articleDisplay3 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleId3, version3, "", "", "", themeDisplay, 0, "");
			if (!(promotionContent3.equals(null) || promotionContent3.equals(""))) {
				promotionContent3 = articleDisplay3.getContent();
			}

			/*----------------------Medium Promotional Banner - 1 content---------------*/
			JournalArticle journalMediumArticle1 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleMediumName1, 0);
			String articleMediumId1 = journalMediumArticle1.getArticleId();
			Double versionMedium1 = journalMediumArticle1.getVersion();
			JournalArticleDisplay articleMediumDisplay1 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleMediumId1, versionMedium1, "", "", "", themeDisplay, 0, "");

			if (articleMediumDisplay1.getContent() != null) {
				promotionMediumContent1 = articleMediumDisplay1.getContent();
			}
			/*----------------------Medium Promotional Banner - 2 content---------------*/
			JournalArticle journalMediumArticle2 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleMediumName2, 0);
			String articleMediumId2 = journalMediumArticle2.getArticleId();
			Double versionMedium2 = journalMediumArticle2.getVersion();
			JournalArticleDisplay articleMediumDisplay2 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleMediumId2, versionMedium2, "", "", "", themeDisplay, 0, "");

			if (articleMediumDisplay2.getContent() != null) {
				promotionMediumContent2 = articleMediumDisplay2.getContent();
			}

			/*----------------------Small Promotional Banner - 1 content---------------*/
			JournalArticle journalSmallArticle1 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleSmallName1, 0);
			String articleSmallId1 = journalSmallArticle1.getArticleId();
			Double versionSmall1 = journalSmallArticle1.getVersion();
			JournalArticleDisplay articleSmallDisplay1 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleSmallId1, versionSmall1, "", "", "", themeDisplay, 0, "");

			if (articleSmallDisplay1.getContent() != null) {
				promotionSmallContent1 = articleSmallDisplay1.getContent();
			}

			/*----------------------Small Promotional Banner - 2 content---------------*/
			JournalArticle journalSmallArticle2 = JournalArticleLocalServiceUtil.getLatestArticleByUrlTitle(
					themeDisplay.getScopeGroupId(), articleSmallName2, 0);
			String articleSmallId2 = journalSmallArticle2.getArticleId();
			Double versionSmall2 = journalSmallArticle2.getVersion();
			JournalArticleDisplay articleSmallDisplay2 = JournalContentUtil.getDisplay(themeDisplay.getScopeGroupId(),
					articleSmallId2, versionSmall2, "", "", "", themeDisplay, 0, "");

			if (articleSmallDisplay2.getContent() != null) {
				promotionSmallContent2 = articleSmallDisplay2.getContent();
			}

		} catch (Exception e) {
			logger.error("Exception while executing CategoryProduct portlet getCategoryEvent()", e);
		}

		request.setAttribute("promotionalContent1", promotionContent1);

		request.setAttribute("promotionalContent2", promotionContent2);

		request.setAttribute("promotionalContent3", promotionContent3);

		request.setAttribute("promotionalMediumContent1", promotionMediumContent1);

		request.setAttribute("promotionalMediumContent2", promotionMediumContent2);

		request.setAttribute("promotionalSmallContent1", promotionSmallContent1);

		request.setAttribute("promotionalSmallContent2", promotionSmallContent2);

	}

}
