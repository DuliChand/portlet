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
<main role="rpp-main" data-context-path="${renderRequest.contextPath}" id="backbone-portlet-restore-password"></main>
<script>
	$(window).load(function() {
		require([
		     "${renderRequest.contextPath}/js/rpp-app.js",
		     "${renderRequest.contextPath}/js/rpp-router.js"
		], function(app, AppRouter) {
		    app.router = new AppRouter();
		});
	});
</script>