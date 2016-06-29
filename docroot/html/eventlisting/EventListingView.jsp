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

<portlet:defineObjects />
<div id="backbone-portlet-event-list" data-section-type="<%= renderRequest.getAttribute("sectionType") %>" data-category-name="<%= renderRequest.getAttribute("categoryName") %>"></div>
<div class="product-loader"><span></span></div>
<div id="promotionalContent1" style="display:none">${promotionalContent1}</div>
<div id="promotionalContent2" style="display:none">${promotionalContent2}</div>
<script>
window.onload = function(){
	//console.log("event listing portlet initialized...");
	var data = ${categoryEventData};

	var eventListingView = new Webshop.Views.EventListingView();
	eventListingView.render(data);
};
</script>