/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet;

import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves OAuth 2.0 Protected Resource Metadata (RFC 9728) from
 * {@code /.well-known/oauth-protected-resource[/{resource path}]}.
 *
 * @author Alberto Moreno
 */
@Component(
	property = {
		"osgi.http.whiteboard.context.path=/",
		"osgi.http.whiteboard.servlet.pattern=/.well-known/oauth-protected-resource",
		"osgi.http.whiteboard.servlet.pattern=/.well-known/oauth-protected-resource/*",
		"servlet.init.httpMethods=GET"
	},
	service = Servlet.class
)
public class OAuth2WellKnownProtectedResourceServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		if (!FeatureFlagManagerUtil.isEnabled(
				CompanyThreadLocal.getCompanyId(), "LPD-XXXXX")) {

			return;
		}

		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

		long companyId = GetterUtil.getLong(
			httpServletRequest.getAttribute(WebKeys.COMPANY_ID));

		String resourceSuffix = _getResourceSuffix(httpServletRequest);

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata = null;

		if (resourceSuffix == null) {
			oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataLocalService.
					fetchOAuthClientPRLocalMetadata(companyId, true, null);
		}
		else {
			String localWellKnownURI = _buildLocalWellKnownURI(
				httpServletRequest, resourceSuffix);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Looking up protected resource metadata for " +
						localWellKnownURI);
			}

			oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataLocalService.
					fetchOAuthClientPRLocalMetadataByLocalWellKnownURI(
						companyId, localWellKnownURI);
		}

		if ((oAuthClientPRLocalMetadata == null) ||
			!oAuthClientPRLocalMetadata.isLocalWellKnownEnabled()) {

			return;
		}

		// RFC 9728 §3.3: the returned `resource` must exactly match the URL
		// the client used to retrieve the metadata. If the persisted record's
		// localWellKnownURI does not match the request's URL, the spec
		// requires us to reject the request rather than return a mismatched
		// document.

		if (!_isWellKnownURIMatch(
				httpServletRequest, oAuthClientPRLocalMetadata)) {

			return;
		}

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse, oAuthClientPRLocalMetadata.getMetadataJSON());
	}

	private String _buildLocalWellKnownURI(
		HttpServletRequest httpServletRequest, String resourceSuffix) {

		StringBuilder sb = new StringBuilder();

		sb.append(httpServletRequest.getScheme());
		sb.append("://");
		sb.append(httpServletRequest.getServerName());
		sb.append("/o/.well-known/oauth-protected-resource");

		if (!resourceSuffix.isEmpty() && !resourceSuffix.startsWith("/")) {
			sb.append('/');
		}

		sb.append(resourceSuffix);

		return sb.toString();
	}

	private String _getResourceSuffix(HttpServletRequest httpServletRequest) {
		String requestURI = StringUtil.trimTrailing(
			httpServletRequest.getRequestURI(), StringPool.SLASH.charAt(0));

		String path =
			httpServletRequest.getContextPath() +
				"/.well-known/oauth-protected-resource";

		if (requestURI.length() <= path.length()) {
			return null;
		}

		String suffix = requestURI.substring(path.length());

		if (Validator.isNull(suffix)) {
			return null;
		}

		return URLDecoder.decode(suffix, StandardCharsets.UTF_8);
	}

	private boolean _isWellKnownURIMatch(
		HttpServletRequest httpServletRequest,
		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata) {

		StringBuilder sb = new StringBuilder();

		sb.append(httpServletRequest.getScheme());
		sb.append("://");
		sb.append(httpServletRequest.getServerName());
		sb.append(
			StringUtil.trimTrailing(
				httpServletRequest.getRequestURI(),
				StringPool.SLASH.charAt(0)));

		String requestedURL = sb.toString();
		String persistedURL = oAuthClientPRLocalMetadata.getLocalWellKnownURI();

		// Tolerate the case where the entry has no resource path suffix and
		// the request hit the bare /.well-known/oauth-protected-resource.

		return requestedURL.equals(persistedURL) ||
			   requestedURL.equals(
				   persistedURL + StringPool.SLASH);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2WellKnownProtectedResourceServlet.class);

	@Reference
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}
