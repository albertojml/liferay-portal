<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata = (OAuthClientPRLocalMetadata)request.getAttribute(OAuthClientPRLocalMetadata.class.getName());

String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle((oAuthClientPRLocalMetadata == null) ? LanguageUtil.get(request, "new-oauth-client-pr-local-metadata") : LanguageUtil.get(request, "edit-oauth-client-pr-local-metadata"));
%>

<portlet:actionURL name="/oauth_client_admin/update_oauth_client_pr_local_metadata" var="updateOAuthClientPRLocalMetadataURL">
	<portlet:param name="mvcRenderCommandName" value="/oauth_client_admin/update_oauth_client_pr_local_metadata" />
</portlet:actionURL>

<aui:form action="<%= updateOAuthClientPRLocalMetadataURL %>" id="oauth-client-pr-fm" method="post" name="oauth-client-pr-fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="oAuthClientPRLocalMetadataId" type="hidden" value="<%= (oAuthClientPRLocalMetadata != null) ? oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId() : StringPool.BLANK %>" />

	<aui:model-context bean="<%= oAuthClientPRLocalMetadata %>" model="<%= OAuthClientPRLocalMetadata.class %>" />

	<clay:container-fluid
		cssClass="container-view"
	>
		<div class="sheet">
			<aui:fieldset>
				<liferay-ui:error exception="<%= DuplicateOAuthClientPRLocalMetadataException.class %>" message="oauth-client-pr-local-metadata-duplicate-protected-resource" />

				<liferay-ui:error exception="<%= OAuthClientPRLocalMetadataResourceException.class %>" message="oauth-client-pr-local-metadata-resource-cannot-be-empty" />

				<liferay-ui:error exception="<%= OAuthClientPRLocalMetadataLocalWellKnownURIException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientPRLocalMetadataLocalWellKnownURIException)errorException).getMessage()) %>" key="oauth-client-pr-local-metadata-invalid-https-uri-x" />
				</liferay-ui:error>

				<liferay-ui:error exception="<%= OAuthClientPRLocalMetadataMetadataJSONException.class %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(((OAuthClientPRLocalMetadataMetadataJSONException)errorException).getMessage()) %>" key="oauth-client-pr-local-metadata-invalid-metadata-json-x" />
				</liferay-ui:error>

				<aui:fieldset label="general">
					<aui:input helpMessage="oauth-client-pr-local-metadata-resource-help" label="oauth-client-pr-local-metadata-resource" name="resource" required="<%= true %>" type="text" />

					<aui:input helpMessage="oauth-client-pr-local-metadata-authorization-servers-help" label="oauth-client-pr-local-metadata-authorization-servers" name="authorizationServers" type="text" />

					<aui:input helpMessage="oauth-client-pr-local-metadata-scopes-supported-help" label="oauth-client-pr-local-metadata-scopes-supported" name="scopesSupported" type="text" />

					<aui:input helpMessage="oauth-client-pr-local-metadata-bearer-methods-supported-help" label="oauth-client-pr-local-metadata-bearer-methods-supported" name="bearerMethodsSupported" type="text" value="header" />

					<aui:input label="oauth-client-pr-local-metadata-jwks-uri" name="jwksURI" type="text" />

					<aui:input label="oauth-client-pr-local-metadata-resource-documentation" name="resourceDocumentation" type="text" />
				</aui:fieldset>

				<aui:fieldset label="oauth-client-pr-local-oauth-protected-resource">
					<aui:input checked="<%= (oAuthClientPRLocalMetadata != null) ? oAuthClientPRLocalMetadata.getLocalWellKnownEnabled() : false %>" label="enable" name="localWellKnownEnabled" type="checkbox" />

					<aui:input helpMessage="oauth-client-pr-local-well-known-uri-help" label="oauth-client-pr-local-well-known-uri" name="localWellKnownURI" readonly="true" type="text" value="<%= (oAuthClientPRLocalMetadata != null) ? oAuthClientPRLocalMetadata.getLocalWellKnownURI() : \"\" %>" />
				</aui:fieldset>

				<aui:fieldset label="oauth-client-pr-local-metadata-signed-metadata-section">
					<aui:input checked="<%= (oAuthClientPRLocalMetadata != null) ? oAuthClientPRLocalMetadata.getSignedMetadataEnabled() : false %>" helpMessage="oauth-client-pr-local-metadata-signed-metadata-help" label="oauth-client-pr-local-metadata-signed-metadata" name="signedMetadataEnabled" type="checkbox" />

					<aui:input helpMessage="oauth-client-pr-local-metadata-signed-metadata-key-alias-help" label="oauth-client-pr-local-metadata-signed-metadata-key-alias" name="signedMetadataKeyAlias" type="text" />
				</aui:fieldset>

				<aui:fieldset label="oauth-client-pr-local-metadata-json-preview-section">
					<aui:input helpMessage="oauth-client-pr-local-metadata-json-help" label="oauth-client-pr-local-metadata-json" name="metadataJSON" readonly="true" style="min-height: 400px;" type="textarea" value="<%= (oAuthClientPRLocalMetadata != null) ? oAuthClientPRLocalMetadata.getMetadataJSON() : \"{}\" %>" />
				</aui:fieldset>

				<aui:button-row>
					<aui:button type="submit" />
					<aui:button href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</div>
	</clay:container-fluid>
</aui:form>
