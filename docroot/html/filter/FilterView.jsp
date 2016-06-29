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

<main role="filter-main" data-context-path="${renderRequest.contextPath}" id="backbone-portlet-filter" data-type="<%= renderRequest.getAttribute("type") %>" data-id="<%= renderRequest.getAttribute("id") %>"></main>
<script>
	$(window).load(function() {	
		require(["jquery","underscore","backbone","handlebars",
		     "${renderRequest.contextPath}/js/filter-app.js",
		     "${renderRequest.contextPath}/js/filter-router.js"
		], function($, _, Backbone, Handlebars, app, AppRouter) {
		    app.router = new AppRouter();
		    // All navigation that is relative should be passed through the navigate
		    // method, to be processed by the router. If the link has a `data-bypass`
		    // attribute, bypass the delegation completely.
		});
	});
</script>
