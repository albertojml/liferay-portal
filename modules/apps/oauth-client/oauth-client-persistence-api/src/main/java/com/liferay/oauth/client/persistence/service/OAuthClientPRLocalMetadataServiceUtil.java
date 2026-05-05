/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service;

import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * Provides the remote service utility for OAuthClientPRLocalMetadata. This utility wraps
 * <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientPRLocalMetadataServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientPRLocalMetadataService
 * @generated
 */
public class OAuthClientPRLocalMetadataServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.oauth.client.persistence.service.impl.OAuthClientPRLocalMetadataServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static OAuthClientPRLocalMetadata addOAuthClientPRLocalMetadata(
			String externalReferenceCode, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		return getService().addOAuthClientPRLocalMetadata(
			externalReferenceCode, resource, authorizationServers,
			bearerMethodsSupported, jwksURI, localWellKnownEnabled,
			resourceDocumentation, scopesSupported, signedMetadataEnabled,
			signedMetadataKeyAlias);
	}

	public static OAuthClientPRLocalMetadata deleteOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId)
		throws PortalException {

		return getService().deleteOAuthClientPRLocalMetadata(
			oAuthClientPRLocalMetadataId);
	}

	public static OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId)
		throws PortalException {

		return getService().fetchOAuthClientPRLocalMetadata(
			oAuthClientPRLocalMetadataId);
	}

	public static OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
			long companyId, String resource)
		throws PortalException {

		return getService().fetchOAuthClientPRLocalMetadata(
			companyId, resource);
	}

	public static List<OAuthClientPRLocalMetadata>
		getCompanyOAuthClientPRLocalMetadata(long companyId) {

		return getService().getCompanyOAuthClientPRLocalMetadata(companyId);
	}

	public static List<OAuthClientPRLocalMetadata>
		getCompanyOAuthClientPRLocalMetadata(
			long companyId, int start, int end) {

		return getService().getCompanyOAuthClientPRLocalMetadata(
			companyId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static OAuthClientPRLocalMetadata updateOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		return getService().updateOAuthClientPRLocalMetadata(
			oAuthClientPRLocalMetadataId, resource, authorizationServers,
			bearerMethodsSupported, jwksURI, localWellKnownEnabled,
			resourceDocumentation, scopesSupported, signedMetadataEnabled,
			signedMetadataKeyAlias);
	}

	public static OAuthClientPRLocalMetadataService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<OAuthClientPRLocalMetadataService>
		_serviceSnapshot = new Snapshot<>(
			OAuthClientPRLocalMetadataServiceUtil.class,
			OAuthClientPRLocalMetadataService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-427225059