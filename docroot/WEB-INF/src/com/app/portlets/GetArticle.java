package com.app.portlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;

public class GetArticle {

 
 public List<JournalArticle> getArticleByYearMonthTags(String year,
   String month, long groupId, String tagName) throws PortalException,
   SystemException {

  Integer selectYear = Integer.parseInt(year);
  Integer selectMonth = Integer.parseInt(month);
  selectMonth = selectMonth-1;
  AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
  long[] anyTagIds = AssetTagLocalServiceUtil.getTagIds(groupId,
    new String[] { "alllocation", tagName });
 
  assetEntryQuery.setAnyTagIds(anyTagIds);
  List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil
    .getEntries(assetEntryQuery);
  List<JournalArticle> journalArticleList = new ArrayList<JournalArticle>();
  for (AssetEntry ae : assetEntryList) {
   JournalArticleResource journalArticleResourceObj = JournalArticleResourceLocalServiceUtil
     .getJournalArticleResource(ae.getClassPK());
   JournalArticle journalArticleObj = JournalArticleLocalServiceUtil
     .getArticle(groupId,
       journalArticleResourceObj.getArticleId());
   String modifiedContent = journalArticleObj.getContent();
   modifiedContent = modifiedContent.replace("<![CDATA[", "");
   modifiedContent = modifiedContent.replace("]]>", "");
   journalArticleObj.setContent(modifiedContent);
   Date dt = journalArticleObj.getDisplayDate();
   Calendar c = Calendar.getInstance();
   c.setTime(dt);
   int articleYear = c.get(Calendar.YEAR);
   int articleMonth = c.get(Calendar.MONTH);
   if (selectYear == 0 && selectMonth == -1) {
    journalArticleList.add(journalArticleObj);
   } else if (selectYear == articleYear && selectMonth == -1) {
    journalArticleList.add(journalArticleObj);
   } else if (selectYear == articleYear && selectMonth ==articleMonth) {
    journalArticleList.add(journalArticleObj);
   } else if (selectYear == 0 && selectMonth == articleMonth) {
    journalArticleList.add(journalArticleObj);
   }

  }
  
  return journalArticleList;
 }

 public List<JournalArticle> getArticleByTags(long groupId, String tagName)
   throws PortalException, SystemException {
  AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
  long[] anyTagIds = AssetTagLocalServiceUtil.getTagIds(groupId,
    new String[] { "alllocation", tagName });
  
  assetEntryQuery.setAnyTagIds(anyTagIds);
  List<AssetEntry> assetEntryList = AssetEntryLocalServiceUtil
    .getEntries(assetEntryQuery);
  List<JournalArticle> journalArticleList = new ArrayList<JournalArticle>();
  for (AssetEntry ae : assetEntryList) {
   JournalArticleResource journalArticleResourceObj = JournalArticleResourceLocalServiceUtil
     .getJournalArticleResource(ae.getClassPK());
   JournalArticle journalArticleObj = JournalArticleLocalServiceUtil
     .getArticle(groupId,
       journalArticleResourceObj.getArticleId());
   String modifiedContent = journalArticleObj.getContent();
   modifiedContent = modifiedContent.replace("<![CDATA[", "");
   modifiedContent = modifiedContent.replace("]]>", "");
   journalArticleObj.setContent(modifiedContent);
   
   journalArticleList.add(journalArticleObj);

  }
  return journalArticleList;
 }

 
}