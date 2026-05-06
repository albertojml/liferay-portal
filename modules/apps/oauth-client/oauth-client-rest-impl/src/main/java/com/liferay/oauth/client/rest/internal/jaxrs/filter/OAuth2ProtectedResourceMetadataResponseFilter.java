/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.rest.internal.jaxrs.filter;

import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Amends 401 responses on the Liferay OAuth2 Application so that the
 * {@code WWW-Authenticate: Bearer …} challenge advertises the matching RFC
 * 9728 Protected Resource Metadata document via the {@code resource_metadata}
 * parameter.
 *
 * Per the LPD-88766 plan, the mapping from a CXF request to a specific
 * Protected Resource record is intentionally minimal in this first cut: the
 * filter advertises the first enabled record for the current company. A
 * scope-aware or application-aware mapping is a follow-up refinement.
 *
 * @author Alberto Moreno
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(osgi.jaxrs.name=Liferay.OAuth2.Application)",
		"osgi.jaxrs.extension=true",
		"osgi.jaxrs.name=OAuth2ProtectedResourceMetadataResponseFilter"
	},
	service = ContainerResponseFilter.class
)
@Provider
public class OAuth2ProtectedResourceMetadataResponseFilter
	implements ContainerResponseFilter {

	@Override
	public void filter(
		ContainerRequestContext containerRequestContext,
		ContainerResponseContext containerResponseContext) {

		if (containerResponseContext.getStatus() !=
				Response.Status.UNAUTHORIZED.getStatusCode()) {

			return;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-88766")) {
			return;
		}

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			_oAuthClientPRLocalMetadataLocalService.
				fetchOAuthClientPRLocalMetadata(companyId, true, null);

		if (oAuthClientPRLocalMetadata == null) {
			return;
		}

		String localWellKnownURI =
			oAuthClientPRLocalMetadata.getLocalWellKnownURI();

		if (Validator.isNull(localWellKnownURI)) {
			return;
		}

		MultivaluedMap<String, Object> headers =
			containerResponseContext.getHeaders();

		List<Object> wwwAuthenticateHeaders = headers.get(_WWW_AUTHENTICATE);

		String resourceMetadataParameter =
			"resource_metadata=\"" + localWellKnownURI + "\"";

		if (ListUtil.isEmpty(wwwAuthenticateHeaders)) {
			headers.add(
				_WWW_AUTHENTICATE, "Bearer " + resourceMetadataParameter);

			return;
		}

		for (int i = 0; i < wwwAuthenticateHeaders.size(); i++) {
			String headerValue = String.valueOf(wwwAuthenticateHeaders.get(i));

			if (headerValue.contains("resource_metadata=")) {
				continue;
			}

			String separator = headerValue.endsWith(",") ? " " : ", ";

			wwwAuthenticateHeaders.set(
				i, headerValue + separator + resourceMetadataParameter);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Amended WWW-Authenticate header with " +
						resourceMetadataParameter);
			}
		}
	}

	private static final String _WWW_AUTHENTICATE = "WWW-Authenticate";

	private static final Log _log = LogFactoryUtil.getLog(
		OAuth2ProtectedResourceMetadataResponseFilter.class);

	@Reference
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}