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
 * Portlet implementation class PrintTagPortlet
 */
public class PrintTagPortlet extends MVCPortlet {

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
   List<JournalArticle> journaldigitalArticles = article.getArticleByTags(
     themeDisplay.getScopeGroupId(), "digital");
   List<JournalArticle> journalArticles = article.getArticleByTags(
     themeDisplay.getScopeGroupId(), "print");
  // List<JournalArticle> journalelectronicArticles = article.getArticleByTags(
		//			themeDisplay.getScopeGroupId(), "electronic");

   Collections.sort(journalArticles,
     new ArticleDisplayDateComparator());
   Collections.sort(journaldigitalArticles,
     new ArticleDisplayDateComparator());
   // Collections.sort(journalelectronicArticles,
     //new ArticleDisplayDateComparator());
   String accrData = RestUtil
     .getAccordionDataFromArticles(journalArticles);
   String accrYear = RestUtil
     .getAccordionYearFromArticles(journalArticles);
   if (param == null) {
    JournalArticle lastArticle = journalArticles.get(0);
    JournalArticle lastdigitalArticle = journaldigitalArticles.get(0);
   // JournalArticle lastelectronicArticle = journalelectronicArticles.get(0);
    journalArticles.removeAll(journalArticles);
    journaldigitalArticles.removeAll(journaldigitalArticles);
    //journalelectronicArticles.removeAll(journalelectronicArticles);
    journalArticles.add(lastArticle);
    /*journalArticles.add(lastdigitalArticle);*/
    journaldigitalArticles.add(lastdigitalArticle);
    //journalelectronicArticles.add(lastelectronicArticle);
    renderRequest.setAttribute("journalArticles", journalArticles);
    renderRequest.setAttribute("journaldigitalArticles", journaldigitalArticles);
   // renderRequest.setAttribute("journalelectronicArticles", journalelectronicArticles);
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
       themeDisplay.getScopeGroupId(), "print");
     List<JournalArticle> journaldigitalArticles = article
     .getArticleByYearMonthTags(selectYear, selectMonth,
       themeDisplay.getScopeGroupId(), "digital");
     // List<JournalArticle> journalelectronicArticles = article.getArticleByTags(
       //   themeDisplay.getScopeGroupId(), "electronic");


    /* journalArticles1*/
   Collections.sort(journalArticles,
     new ArticleDisplayDateComparator());
   Collections.sort(journaldigitalArticles,
     new ArticleDisplayDateComparator());
  // Collections.sort(journalelectronicArticles,
    // new ArticleDisplayDateComparator());

   actionRequest.setAttribute("journalArticles", journalArticles);
   actionRequest.setAttribute("journaldigitalArticles", journaldigitalArticles);
   //actionRequest.setAttribute("journalelectronicArticles", journalelectronicArticles);
  } catch (Exception e) {
   e.getMessage();
  }
  actionResponse.setRenderParameter("articleYear", selectYear);
  actionResponse.setRenderParameter("articleMonth", selectMonth);
  actionResponse.setRenderParameter("actionParam", "action");
 }

}