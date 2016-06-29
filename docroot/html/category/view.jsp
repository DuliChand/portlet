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

<div id="backbone-portlet-home" data-category-name="<%= renderRequest.getAttribute("categoryName") %>">
	<div id="heroBanner"></div>
	<div id="freshArrival"></div>
	<div id="weRecommend"></div>
	<div id="endingSoon"></div>
	
</div>
	<div id="promotionalContent1" style="display:none">${promotionalContent1}</div>
	<div id="promotionalContent2" style="display:none">${promotionalContent2}</div>
	<div id="promotionalContent3" style="display:none">${promotionalContent3}</div>
	
	<div id="promotionalMediumContent1" style="display:none">${promotionalMediumContent1}</div>
	<div id="promotionalMediumContent2" style="display:none">${promotionalMediumContent2}</div>
	
	<div id="promotionalSmallContent1" style="display:none">${promotionalSmallContent1}</div>
	<div id="promotionalSmallContent2" style="display:none">${promotionalSmallContent2}</div>
	
<script>
window.onload = function(){
	//console.log("category portlet initialized...");
	var data = ${categoryData}; 
	var homeView = new Webshop.Views.HomeView();
    homeView.render(data);
};
</script>