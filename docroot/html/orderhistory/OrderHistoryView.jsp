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
<div id="backbone-portlet-my-account-nav"></div>

<div id="backbone-portlet-order-history">
	<div class="product-loader"><span></span></div>
</div>
<div id="backbone-portlet-latest-order"></div>
<div id="customerdata" style="display:none">${customerOrderData}</div>
<script>
window.onload = function(){
	//console.log("order history portlet initialized...");
	var data = ${customerOrderData};
	var lastOrderData = ${lastOrderData};
	
	var myAccountNavView = new Webshop.Views.MyAccountNavView();
    myAccountNavView.render();

    var latestOrderView = new Webshop.Views.LatestOrderView();
    latestOrderView.render(lastOrderData);

    var orderHistoryView = new Webshop.Views.OrderHistoryView();
    orderHistoryView.render(data);
};
</script>