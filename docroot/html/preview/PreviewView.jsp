<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<portlet:defineObjects />

<div id="backbone-portlet-preview" data-category-name="<%= renderRequest.getAttribute("categoryName") %>">
	
	<div id="heroBanner"></div>
	<div id="freshArrival"></div>
	<%-- <div id="marketingBanner">
		<liferay-ui:journal-article articleId="12247" groupId="10231" />
	</div> --%>
	<div id="weRecommend"></div>
	<div id="featuredCategories"></div>
	<div id="marketingLinks">
		${promotionalBottom}
	</div>
	<div id="endingSoon"></div>
	<div id="marketingBar">
		${marketingBar}
	</div>
	<div id="promotionalContent1" style="display:none">${promotionalContent1}</div>
	<div id="promotionalContent2" style="display:none">${promotionalContent2}</div>
</div>
<script>
window.onload = function() {
	console.log("preview portlet initialized...");
	var data = ${homePageData},
	featureShop = ${featuredData};
	setTimeout(function(){
		var previewView = new Webshop.Views.PreviewView();
		previewView.render(data , featureShop);
	    
	},1000);
};	
</script>    