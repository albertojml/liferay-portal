/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.oauth.client.persistence.constants.OAuthClientPersistenceActionKeys;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.base.OAuthClientPRLocalMetadataServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Moreno
 */
@Component(
	property = {
		"json.web.service.context.name=oauthclient",
		"json.web.service.context.path=OAuthClientPRLocalMetadata"
	},
	service = AopService.class
)
public class OAuthClientPRLocalMetadataServiceImpl
	extends OAuthClientPRLocalMetadataServiceBaseImpl {

	public OAuthClientPRLocalMetadata addOAuthClientPRLocalMetadata(
			String externalReferenceCode, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_oAuthClientPRLocalMetadataModelResourcePermission,
			getPermissionChecker(), GroupConstants.DEFAULT_LIVE_GROUP_ID, 0,
			OAuthClientPersistenceActionKeys.
				ACTION_ADD_OAUTH_CLIENT_PR_LOCAL_METADATA);

		return oAuthClientPRLocalMetadataLocalService.
			addOAuthClientPRLocalMetadata(
				externalReferenceCode, getUserId(), resource,
				authorizationServers, bearerMethodsSupported, jwksURI,
				localWellKnownEnabled, resourceDocumentation, scopesSupported,
				signedMetadataEnabled, signedMetadataKeyAlias);
	}

	@Override
	public OAuthClientPRLocalMetadata deleteOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataLocalService.
				getOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadataId);

		_oAuthClientPRLocalMetadataModelResourcePermission.check(
			getPermissionChecker(), oAuthClientPRLocalMetadata,
			ActionKeys.DELETE);

		return oAuthClientPRLocalMetadataLocalService.
			deleteOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadata);
	}

	@Override
	public OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.fetchByPrimaryKey(
				oAuthClientPRLocalMetadataId);

		if (oAuthClientPRLocalMetadata != null) {
			_oAuthClientPRLocalMetadataModelResourcePermission.check(
				getPermissionChecker(), oAuthClientPRLocalMetadata,
				ActionKeys.VIEW);
		}

		return oAuthClientPRLocalMetadata;
	}

	@Override
	public OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
			long companyId, String resource)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.fetchByC_R(
				companyId, resource);

		if (oAuthClientPRLocalMetadata != null) {
			_oAuthClientPRLocalMetadataModelResourcePermission.check(
				getPermissionChecker(), oAuthClientPRLocalMetadata,
				ActionKeys.VIEW);
		}

		return oAuthClientPRLocalMetadata;
	}

	@Override
	public List<OAuthClientPRLocalMetadata>
		getCompanyOAuthClientPRLocalMetadata(long companyId) {

		return oAuthClientPRLocalMetadataPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<OAuthClientPRLocalMetadata>
		getCompanyOAuthClientPRLocalMetadata(
			long companyId, int start, int end) {

		return oAuthClientPRLocalMetadataPersistence.findByCompanyId(
			companyId, start, end);
	}

	public OAuthClientPRLocalMetadata updateOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		_oAuthClientPRLocalMetadataModelResourcePermission.check(
			getPermissionChecker(),
			oAuthClientPRLocalMetadataPersistence.findByPrimaryKey(
				oAuthClientPRLocalMetadataId),
			ActionKeys.UPDATE);

		return oAuthClientPRLocalMetadataLocalService.
			updateOAuthClientPRLocalMetadata(
				oAuthClientPRLocalMetadataId, resource, authorizationServers,
				bearerMethodsSupported, jwksURI, localWellKnownEnabled,
				resourceDocumentation, scopesSupported, signedMetadataEnabled,
				signedMetadataKeyAlias);
	}

	@Reference(
		target = "(model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata)"
	)
	private ModelResourcePermission<OAuthClientPRLocalMetadata>
		_oAuthClientPRLocalMetadataModelResourcePermission;

}