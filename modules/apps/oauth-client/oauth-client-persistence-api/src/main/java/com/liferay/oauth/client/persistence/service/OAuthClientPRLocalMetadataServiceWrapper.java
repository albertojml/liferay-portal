/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link OAuthClientPRLocalMetadataService}.
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientPRLocalMetadataService
 * @generated
 */
public class OAuthClientPRLocalMetadataServiceWrapper
	implements OAuthClientPRLocalMetadataService,
			   ServiceWrapper<OAuthClientPRLocalMetadataService> {

	public OAuthClientPRLocalMetadataServiceWrapper() {
		this(null);
	}

	public OAuthClientPRLocalMetadataServiceWrapper(
		OAuthClientPRLocalMetadataService oAuthClientPRLocalMetadataService) {

		_oAuthClientPRLocalMetadataService = oAuthClientPRLocalMetadataService;
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			addOAuthClientPRLocalMetadata(
				String externalReferenceCode, String resource,
				String[] authorizationServers, String[] bearerMethodsSupported,
				String jwksURI, boolean localWellKnownEnabled,
				String resourceDocumentation, String[] scopesSupported,
				boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientPRLocalMetadataService.addOAuthClientPRLocalMetadata(
			externalReferenceCode, resource, authorizationServers,
			bearerMethodsSupported, jwksURI, localWellKnownEnabled,
			resourceDocumentation, scopesSupported, signedMetadataEnabled,
			signedMetadataKeyAlias);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			deleteOAuthClientPRLocalMetadata(long oAuthClientPRLocalMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientPRLocalMetadataService.
			deleteOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadataId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			fetchOAuthClientPRLocalMetadata(long oAuthClientPRLocalMetadataId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientPRLocalMetadataService.
			fetchOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadataId);
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			fetchOAuthClientPRLocalMetadata(long companyId, String resource)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientPRLocalMetadataService.
			fetchOAuthClientPRLocalMetadata(companyId, resource);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata>
			getCompanyOAuthClientPRLocalMetadata(long companyId) {

		return _oAuthClientPRLocalMetadataService.
			getCompanyOAuthClientPRLocalMetadata(companyId);
	}

	@Override
	public java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata>
			getCompanyOAuthClientPRLocalMetadata(
				long companyId, int start, int end) {

		return _oAuthClientPRLocalMetadataService.
			getCompanyOAuthClientPRLocalMetadata(companyId, start, end);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _oAuthClientPRLocalMetadataService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
			updateOAuthClientPRLocalMetadata(
				long oAuthClientPRLocalMetadataId, String resource,
				String[] authorizationServers, String[] bearerMethodsSupported,
				String jwksURI, boolean localWellKnownEnabled,
				String resourceDocumentation, String[] scopesSupported,
				boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _oAuthClientPRLocalMetadataService.
			updateOAuthClientPRLocalMetadata(
				oAuthClientPRLocalMetadataId, resource, authorizationServers,
				bearerMethodsSupported, jwksURI, localWellKnownEnabled,
				resourceDocumentation, scopesSupported, signedMetadataEnabled,
				signedMetadataKeyAlias);
	}

	@Override
	public OAuthClientPRLocalMetadataService getWrappedService() {
		return _oAuthClientPRLocalMetadataService;
	}

	@Override
	public void setWrappedService(
		OAuthClientPRLocalMetadataService oAuthClientPRLocalMetadataService) {

		_oAuthClientPRLocalMetadataService = oAuthClientPRLocalMetadataService;
	}

	private OAuthClientPRLocalMetadataService
		_oAuthClientPRLocalMetadataService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-264076420