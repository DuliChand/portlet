<%
/**
 * Copyright (coffee) 2000-2012 Liferay, Inc. All rights reserved.
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
<form id="prdtListPaging" method="post" action="<portlet:resourceURL/>">
</form>
<div id="backbone-portlet-product-listing" data-context-path="${renderRequest.contextPath}" data-type="<%= renderRequest.getAttribute("type") %>" data-id="<%= renderRequest.getAttribute("id") %>">
	 <!-- breadcrumb starts here --> 
	<div class="breadcrumb-container"></div>
 	<div class="clearfix"></div>
 	<!-- breadcrumb ends here -->
	<div id="eventInfo"></div>
	<div class="prod-lstng-area">
	  <div id="filters"></div>
	  <div class="prod-area-right">
		<div class="sort-by"><div class="col-xs-12"></div></div>
		<div class="row products" id="productListing"></div>
		<div class="product-loader"><span></span></div>
		<div class="quickviewpopup clearfix" id="quickview1"></div>
	 </div>
	</div>
</div>

<script>
window.onload = function(){	
	
	var currentUrldata = window.location.href;
	var prevousUrlData = $.cookie('pageUrlData') || "";
	var stringUrlfirst = currentUrldata.split('/').pop(-1),
	procductlistUrl = currentUrldata.split('/').pop(-1),
	stringUrlsecond = prevousUrlData.split('/')[5];
	if(stringUrlfirst === stringUrlsecond || stringUrlfirst === procductlistUrl){
		$('#filterSatus').attr('data-status',true);
	}

 var data = ${productData};
 var eventFavData =${isFavouriteEvents}; 
 var productListView = new Webshop.Views.ProductListView();
    productListView.render(data);
    
 var breadcrumbView = new Webshop.Views.BreadCrumbView();
    breadcrumbView.render(data); 
};
</script>
