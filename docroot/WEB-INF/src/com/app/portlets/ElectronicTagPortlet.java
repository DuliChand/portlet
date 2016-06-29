package com.app.portlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.app.util.RestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * Portlet implementation class ElectronicTagPortlet
 */
public class ElectronicTagPortlet extends MVCPortlet {

	@SuppressWarnings("unchecked")
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		String param = renderRequest.getParameter("actionParam");
		String year = renderRequest.getParameter("articleYear");
		String month = renderRequest.getParameter("articleMonth");

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
					.getAttribute(WebKeys.THEME_DISPLAY);
			GetArticle article = new GetArticle();
			List<JournalArticle> journalArticles = article.getArticleByTags(
					themeDisplay.getScopeGroupId(), "electronic");
			Collections.sort(journalArticles,
					new ArticleDisplayDateComparator());
			String accrData = RestUtil
					.getAccordionDataFromArticles(journalArticles);
			String accrYear = RestUtil
					.getAccordionYearFromArticles(journalArticles);
			if (param == null) {
				JournalArticle lastArticle = journalArticles.get(0);
				journalArticles.removeAll(journalArticles);
				journalArticles.add(lastArticle);
				renderRequest.setAttribute("journalArticles", journalArticles);
			}
			
			renderRequest.setAttribute("accordData", accrData);
			renderRequest.setAttribute("accordYr", accrYear);
			renderRequest.setAttribute("artYear", year);
			renderRequest.setAttribute("artMonth", month);
		} catch (Exception e) {
			e.getMessage();
		}
		super.doView(renderRequest, renderResponse);
	}

	@SuppressWarnings("unchecked")
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String selectYear = actionRequest.getParameter("articleYear");
		String selectMonth = actionRequest.getParameter("articleMonth");
		try {
			GetArticle article = new GetArticle();
			List<JournalArticle> journalArticles = article
					.getArticleByYearMonthTags(selectYear, selectMonth,
							themeDisplay.getScopeGroupId(), "electronic");
			Collections.sort(journalArticles,
					new ArticleDisplayDateComparator());

			actionRequest.setAttribute("journalArticles", journalArticles);
		} catch (Exception e) {
			e.getMessage();
		}
		actionResponse.setRenderParameter("articleYear", selectYear);
		actionResponse.setRenderParameter("articleMonth", selectMonth);
		actionResponse.setRenderParameter("actionParam", "action");
	}

}
