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
					
<div data-context-path="${renderRequest.contextPath}" id="backbone-portlet-product" class="productDetailPage" data-page-value="productDetailPage" data-title="<%= renderRequest.getAttribute("title") %>"  data-product-id="<%= renderRequest.getAttribute("vendorProductId") %>"></div>
 <!-- breadcrumb starts here --> <div class="breadcrumb-container"></div>
 <div class="clearfix"></div>
<div id="productDetail"></div>
<div id="productPromoData">${productPagePromoContent}</div>
<div id="recentlyBrowsedProducts"></div>
<div id="productPriceData" style="display:none" data-product-detail ="">${salesprice}</div>
<div id="erpskuid" style="display:none" data-product-detail ="">${erpskuno}</div>

<script>
	window.onload = function() {
			
		var data = ${productDetailData};
		isFavProduct = ${isFavouriteProduct};
		//browseHistoryData = ${browseHistoryData} ;
		//document.getElementById("productPriceData").innerHTML = data.vendorProductWrapper.liveinventory.salePriceWithVAT;
		
		var productView = new Webshop.Views.ProductView();
		productView.render(data , isFavProduct);
		
		var breadcrumbView = new Webshop.Views.BreadCrumbView();
	    breadcrumbView.render(data);
		
		var recentlyBrowsedView = new Webshop.Views.RecentlyBrowsedView();
	    recentlyBrowsedView.render(browseHistoryData); 
	   
	    $(document).bind("contextmenu",function(e){
			if(e.target.nodeName == 'IMG'){
			return false;
			}
			});
	   
	};
	
</script>