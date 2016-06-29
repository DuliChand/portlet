package com.app.portlets;

import java.io.IOException;
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
import com.app.util.ServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
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
 * Portlet implementation class ProductDetailPortlet
 */
public class ProductDetailPortlet extends MVCPortlet {

	private Logger logger = Logger.getLogger(ProductDetailPortlet.class);

	/**
	 * Render method for the VIEW portlet mode. This is where all the main
	 * business functionality of the portlet lies.
	 * 
	 * @param request
	 * @param response'
	 * @throws IOException
	 * @throws PortletException
	 */

	public void doView(RenderRequest request, RenderResponse response)
			throws IOException, PortletException {

		String isFavouriteProduct = "{}";
		try {
			String productPagePromoContent = null;
			String productPagePromoName = "product-page-promo";
			String vendorProductId = request.getParameter("vendorProductId");
			String title = request.getParameter("title");
			System.out.println("--title--"+title+"---venpdid--"+vendorProductId);
			String deviceId = null;
			ResourceBundle res = getPortletConfig().getResourceBundle(
					Locale.getDefault());
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

			

			String requestURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_PRODUCT")).toString()
					.replace("{vendorProductId}", vendorProductId)
					.replace("{title}", title);
			System.out.println("---product requestURL---"+requestURL);
			String productDetailData = RestUtil.getResponsefromURL(requestURL);
			//System.out.println("---product json---"+productDetailData);

			JSONObject productJson = JSONFactoryUtil
					.createJSONObject(productDetailData).getJSONObject("product");
			String productName = productJson.getString("title"); 
						
			String amount = productJson.getJSONArray("productVariants").getJSONObject(0)
			.getJSONArray("sellerSKUs").getJSONObject(0).getString("mrp");
			
			String thumbnail = productJson.getJSONArray("images").getJSONObject(0)
					.getString("thumbUrl");

			/*String reqURL = new StringBuilder(res.getString("API")).append(
					res.getString("ADD_PRODUCT_BROWSING_HISTORY")).toString();

			String browseURL = reqURL.replace("{deviceId}", deviceId);

			JSONObject productfilters = JSONFactoryUtil.createJSONObject();
			JSONObject product = JSONFactoryUtil.createJSONObject();
			JSONArray productArray = JSONFactoryUtil.createJSONArray();
			product.put("productName", productName);
			product.put("salePrice", amount);
			product.put("thumbnailUrl", thumbnail);
			product.put("vendorProductId", vendorProductId);
			product.put("title", title);

			productArray.put(product);

			productfilters.put("product", productArray);
			String prodJson = productfilters.toString();
			String browseData = RestUtil.getResponseFromPostURL(browseURL,
					prodJson);*/

			/*String browseHistoryURL = new StringBuilder(res.getString("API"))
					.append(res.getString("GET_PRODUCT_BROWSING_HISTORY"))
					.toString().replace("{deviceId}", deviceId);

			String browseHistoryData = RestUtil
					.getResponsefromURL(browseHistoryURL);*/
			
			String customerId = null;
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equals(
								"COOKIE_FNY_CUSTOMER_ID")) {
							customerId = cookies[i].getValue();
							break;
						}
					}
				}
			if(customerId != null){	
				String favouriteProduct = new StringBuilder(res.getString("API"))
						.append(res.getString("IS_FAVOURITE_PRODUCT")).toString();

				String favUrl = favouriteProduct.replace("{customerId}", customerId);

				String isFavourite = RestUtil.getResponsefromURL(favUrl);

				if (isFavourite != null) {
					isFavouriteProduct = isFavourite;
				}
			}
			
			try {
				/*----------------------Product Page Promo Block content---------------*/
				ThemeDisplay themeDisplay = (ThemeDisplay) request
						.getAttribute(WebKeys.THEME_DISPLAY);
				JournalArticle productPageBanner = JournalArticleLocalServiceUtil
						.getLatestArticleByUrlTitle(
								themeDisplay.getScopeGroupId(),
								productPagePromoName, 0);
				String productPageArticleId = productPageBanner.getArticleId();
				Double productPageVersion = productPageBanner.getVersion();
				JournalArticleDisplay productPageBannerDisplay = JournalContentUtil
						.getDisplay(themeDisplay.getScopeGroupId(),
								productPageArticleId, productPageVersion, "",
								"", "", themeDisplay, 0, "");
				if (productPageBannerDisplay.getContent() != null) {
					productPagePromoContent = productPageBannerDisplay
							.getContent();
				}
			} catch (Exception ne) {
				logger.error("No Article exist with name-"
						+ productPagePromoName);
			}
			//System.out.println("---product json get---"+productDetailData);

			request.setAttribute("isFavouriteProduct", isFavouriteProduct);
			
			//request.setAttribute("browseHistoryData", browseHistoryData);
			request.setAttribute("productDetailData", productDetailData);
			request.setAttribute("salesprice", amount);
			request.setAttribute("vendorProductId", vendorProductId);
			request.setAttribute("title", title);
			request.setAttribute("productPagePromoContent",
					productPagePromoContent);

		} catch (Exception e) {
			logger.error("Exception while executing ProductDetail doView()", e);
		}
		
		super.doView(request, response);

	}

	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws IOException, PortletException {

		ResourceBundle res = getPortletConfig().getResourceBundle(
				Locale.getDefault());
		String pincode = ServiceUtil.getPincode(request);
		String deviceId = null;

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
		String addProductDetail = "";
		String requestURL = "";
		if (request.getParameter("action").equals("addProduct")) {
			try {
				String productJson = request.getParameter("productJson");

				if (pincode != null && pincode != "") {
					requestURL = new StringBuilder(res.getString("API"))
							.append(res.getString("ADD_PRODUCT"))
							.append("&pincode=").append(pincode).toString()
							.replace("{deviceId}", deviceId);
				} else {
					requestURL = new StringBuilder(res.getString("API"))
							.append(res.getString("ADD_PRODUCT")).toString()
							.replace("{deviceId}", deviceId);
				}
				addProductDetail = RestUtil.getResponseFromPostURL(requestURL,
						productJson);

			} catch (Exception e) {
			}
		}
		response.getWriter().write(addProductDetail);
	}
}