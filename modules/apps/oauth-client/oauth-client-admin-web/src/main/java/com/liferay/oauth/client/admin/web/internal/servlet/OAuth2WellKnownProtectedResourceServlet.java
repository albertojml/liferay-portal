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

import java.net.URI;
import java.net.URLDecoder;

import java.nio.charset.StandardCharsets;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Serves OAuth 2.0 Protected Resource Metadata (RFC 9728) from
 * {@code /.well-known/oauth-protected-resource[/{resource path}]}.
 *
 * <p>Lookup is keyed on the path component of the persisted {@code resource}
 * URL rather than the full URL, so the response is independent of the
 * request's scheme, host or port. Spec compliance with RFC 9728 §3.3 is
 * guaranteed by the persisted {@code resource} field that the metadata
 * document echoes back: clients validate the response by comparing the
 * returned {@code resource} value against the URL they expected.
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
				CompanyThreadLocal.getCompanyId(), "LPD-99999")) {

			return;
		}

		httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);

		long companyId = GetterUtil.getLong(
			httpServletRequest.getAttribute(WebKeys.COMPANY_ID));

		String resourceSuffix = _getResourceSuffix(httpServletRequest);

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata;

		if (resourceSuffix == null) {
			oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataLocalService.
					fetchOAuthClientPRLocalMetadata(companyId, true, null);
		}
		else {
			oAuthClientPRLocalMetadata = _findByResourcePath(
				companyId, resourceSuffix);
		}

		if ((oAuthClientPRLocalMetadata == null) ||
			!oAuthClientPRLocalMetadata.isLocalWellKnownEnabled()) {

			return;
		}

		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(
			httpServletResponse, oAuthClientPRLocalMetadata.getMetadataJSON());
	}

	private OAuthClientPRLocalMetadata _findByResourcePath(
		long companyId, String resourceSuffix) {

		String requestedPath = _normalizePath(resourceSuffix);

		List<OAuthClientPRLocalMetadata> oAuthClientPRLocalMetadatas =
			_oAuthClientPRLocalMetadataLocalService.
				getCompanyOAuthClientPRLocalMetadata(companyId);

		for (OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata :
				oAuthClientPRLocalMetadatas) {

			String resource = oAuthClientPRLocalMetadata.getResource();

			if (Validator.isNull(resource)) {
				continue;
			}

			try {
				URI resourceURI = URI.create(resource);

				String resourcePath = _normalizePath(resourceURI.getPath());

				if (requestedPath.equals(resourcePath)) {
					return oAuthClientPRLocalMetadata;
				}
			}
			catch (IllegalArgumentException illegalArgumentException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skipping unparseable resource: " + resource,
						illegalArgumentException);
				}
			}
		}

		return null;
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

	private String _normalizePath(String path) {
		if (path == null) {
			return StringPool.SLASH;
		}

		String trimmed = StringUtil.trimTrailing(
			path, StringPool.SLASH.charAt(0));

		if (trimmed.isEmpty()) {
			return StringPool.SLASH;
		}

		if (!trimmed.startsWith(StringPool.SLASH)) {
			return StringPool.SLASH + trimmed;
		}

		return trimmed;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2WellKnownProtectedResourceServlet.class);

	@Reference
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}
