<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "oauth-clients");
%>

<%@ include file="/oauth_client_admin_navigation.jspf" %>

<c:choose>
	<c:when test='<%= navigation.equals("oauth-clients") %>'>
		<liferay-util:include page="/admin/view_oauth_client_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= navigation.contains("oauth-client-as-local-metadata") %>'>
		<liferay-util:include page="/admin/view_oauth_client_as_local_metadata.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>