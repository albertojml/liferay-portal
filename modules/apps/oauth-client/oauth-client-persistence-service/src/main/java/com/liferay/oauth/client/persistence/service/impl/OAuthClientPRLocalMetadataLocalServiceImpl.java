/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.impl;

import com.liferay.oauth.client.persistence.exception.DuplicateOAuthClientPRLocalMetadataException;
import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataLocalWellKnownURIException;
import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataMetadataJSONException;
import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataResourceException;
import com.liferay.oauth.client.persistence.internal.jose.OAuthClientPRSignedMetadataSigner;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.base.OAuthClientPRLocalMetadataLocalServiceBaseImpl;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Moreno
 */
@Component(
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata",
	service = AopService.class
)
public class OAuthClientPRLocalMetadataLocalServiceImpl
	extends OAuthClientPRLocalMetadataLocalServiceBaseImpl {

	public OAuthClientPRLocalMetadata addOAuthClientPRLocalMetadata(
			String externalReferenceCode, long userId, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		String localWellKnownURI = _generateLocalWellKnownURI(resource);

		_validate(
			null, user.getCompanyId(), resource, jwksURI, localWellKnownURI);

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.create(
				counterLocalService.increment());

		oAuthClientPRLocalMetadata.setExternalReferenceCode(
			externalReferenceCode);
		oAuthClientPRLocalMetadata.setCompanyId(user.getCompanyId());
		oAuthClientPRLocalMetadata.setUserId(user.getUserId());
		oAuthClientPRLocalMetadata.setUserName(user.getFullName());

		oAuthClientPRLocalMetadata.setAuthorizationServers(
			_join(authorizationServers));
		oAuthClientPRLocalMetadata.setBearerMethodsSupported(
			_join(bearerMethodsSupported));
		oAuthClientPRLocalMetadata.setJwksURI(jwksURI);
		oAuthClientPRLocalMetadata.setLocalWellKnownEnabled(
			localWellKnownEnabled);
		oAuthClientPRLocalMetadata.setLocalWellKnownURI(localWellKnownURI);
		oAuthClientPRLocalMetadata.setMetadataJSON(
			_generateProtectedResourceMetadataJSON(
				resource, authorizationServers, bearerMethodsSupported,
				jwksURI, resourceDocumentation, scopesSupported,
				signedMetadataEnabled, signedMetadataKeyAlias));
		oAuthClientPRLocalMetadata.setResource(resource);
		oAuthClientPRLocalMetadata.setResourceDocumentation(
			resourceDocumentation);
		oAuthClientPRLocalMetadata.setScopesSupported(_join(scopesSupported));
		oAuthClientPRLocalMetadata.setSignedMetadataEnabled(
			signedMetadataEnabled);
		oAuthClientPRLocalMetadata.setSignedMetadataKeyAlias(
			signedMetadataKeyAlias);

		oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.update(
				oAuthClientPRLocalMetadata);

		_resourceLocalService.addResources(
			oAuthClientPRLocalMetadata.getCompanyId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			oAuthClientPRLocalMetadata.getUserId(),
			OAuthClientPRLocalMetadata.class.getName(),
			oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId(), false,
			false, false);

		return oAuthClientPRLocalMetadata;
	}

	@Override
	public OAuthClientPRLocalMetadata deleteOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.findByPrimaryKey(
				oAuthClientPRLocalMetadataId);

		return deleteOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadata);
	}

	@Override
	public OAuthClientPRLocalMetadata deleteOAuthClientPRLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.findByC_LWKURI(
				companyId, localWellKnownURI);

		return deleteOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadata);
	}

	@Override
	public OAuthClientPRLocalMetadata deleteOAuthClientPRLocalMetadata(
			OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata)
		throws PortalException {

		oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.remove(
				oAuthClientPRLocalMetadata);

		_resourceLocalService.deleteResource(
			oAuthClientPRLocalMetadata.getCompanyId(),
			OAuthClientPRLocalMetadata.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			oAuthClientPRLocalMetadata.getOAuthClientPRLocalMetadataId());

		return oAuthClientPRLocalMetadata;
	}

	@Override
	public OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
		long companyId, boolean localWellKnownEnabled,
		OrderByComparator<OAuthClientPRLocalMetadata> orderByComparator) {

		return oAuthClientPRLocalMetadataPersistence.fetchByC_L_First(
			companyId, localWellKnownEnabled, orderByComparator);
	}

	@Override
	public OAuthClientPRLocalMetadata fetchOAuthClientPRLocalMetadata(
		long companyId, String resource) {

		return oAuthClientPRLocalMetadataPersistence.fetchByC_R(
			companyId, resource);
	}

	@Override
	public OAuthClientPRLocalMetadata
		fetchOAuthClientPRLocalMetadataByLocalWellKnownURI(
			long companyId, String localWellKnownURI) {

		return oAuthClientPRLocalMetadataPersistence.fetchByC_LWKURI(
			companyId, localWellKnownURI);
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

	@Override
	public OAuthClientPRLocalMetadata getOAuthClientPRLocalMetadata(
			long companyId, String localWellKnownURI)
		throws PortalException {

		return oAuthClientPRLocalMetadataPersistence.findByC_LWKURI(
			companyId, localWellKnownURI);
	}

	@Override
	public int getOAuthClientPRLocalMetadatasCount(long companyId) {
		return oAuthClientPRLocalMetadataPersistence.countByCompanyId(
			companyId);
	}

	@Override
	public List<OAuthClientPRLocalMetadata> getUserOAuthClientPRLocalMetadata(
		long userId) {

		return oAuthClientPRLocalMetadataPersistence.findByUserId(userId);
	}

	@Override
	public List<OAuthClientPRLocalMetadata> getUserOAuthClientPRLocalMetadata(
		long userId, int start, int end) {

		return oAuthClientPRLocalMetadataPersistence.findByUserId(
			userId, start, end);
	}

	public OAuthClientPRLocalMetadata updateOAuthClientPRLocalMetadata(
			long oAuthClientPRLocalMetadataId, String resource,
			String[] authorizationServers, String[] bearerMethodsSupported,
			String jwksURI, boolean localWellKnownEnabled,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataLocalService.
				getOAuthClientPRLocalMetadata(oAuthClientPRLocalMetadataId);

		String localWellKnownURI = _generateLocalWellKnownURI(resource);

		_validate(
			oAuthClientPRLocalMetadata,
			oAuthClientPRLocalMetadata.getCompanyId(), resource, jwksURI,
			localWellKnownURI);

		oAuthClientPRLocalMetadata.setAuthorizationServers(
			_join(authorizationServers));
		oAuthClientPRLocalMetadata.setBearerMethodsSupported(
			_join(bearerMethodsSupported));
		oAuthClientPRLocalMetadata.setJwksURI(jwksURI);
		oAuthClientPRLocalMetadata.setLocalWellKnownEnabled(
			localWellKnownEnabled);
		oAuthClientPRLocalMetadata.setLocalWellKnownURI(localWellKnownURI);
		oAuthClientPRLocalMetadata.setMetadataJSON(
			_generateProtectedResourceMetadataJSON(
				resource, authorizationServers, bearerMethodsSupported,
				jwksURI, resourceDocumentation, scopesSupported,
				signedMetadataEnabled, signedMetadataKeyAlias));
		oAuthClientPRLocalMetadata.setResource(resource);
		oAuthClientPRLocalMetadata.setResourceDocumentation(
			resourceDocumentation);
		oAuthClientPRLocalMetadata.setScopesSupported(_join(scopesSupported));
		oAuthClientPRLocalMetadata.setSignedMetadataEnabled(
			signedMetadataEnabled);
		oAuthClientPRLocalMetadata.setSignedMetadataKeyAlias(
			signedMetadataKeyAlias);

		return oAuthClientPRLocalMetadataPersistence.update(
			oAuthClientPRLocalMetadata);
	}

	private String _generateLocalWellKnownURI(String resource)
		throws PortalException {

		try {
			URI resourceURI = URI.create(resource);

			return StringBundler.concat(
				resourceURI.getScheme(), "://", resourceURI.getAuthority(),
				"/o/.well-known/oauth-protected-resource",
				_normalizeResourcePath(resourceURI.getPath()));
		}
		catch (Exception exception) {
			throw new OAuthClientPRLocalMetadataLocalWellKnownURIException(
				exception);
		}
	}

	private String _generateProtectedResourceMetadataJSON(
			String resource, String[] authorizationServers,
			String[] bearerMethodsSupported, String jwksURI,
			String resourceDocumentation, String[] scopesSupported,
			boolean signedMetadataEnabled, String signedMetadataKeyAlias)
		throws PortalException {

		try {
			JSONObject jsonObject = JSONUtil.put("resource", resource);

			if ((authorizationServers != null) &&
				(authorizationServers.length > 0)) {

				JSONArray jsonArray = _jsonFactory.createJSONArray();

				for (String authorizationServer : authorizationServers) {
					jsonArray.put(authorizationServer);
				}

				jsonObject.put("authorization_servers", jsonArray);
			}

			if ((bearerMethodsSupported != null) &&
				(bearerMethodsSupported.length > 0)) {

				JSONArray jsonArray = _jsonFactory.createJSONArray();

				for (String bearerMethod : bearerMethodsSupported) {
					jsonArray.put(bearerMethod);
				}

				jsonObject.put("bearer_methods_supported", jsonArray);
			}

			if (Validator.isNotNull(jwksURI)) {
				jsonObject.put("jwks_uri", jwksURI);
			}

			if (Validator.isNotNull(resourceDocumentation)) {
				jsonObject.put("resource_documentation", resourceDocumentation);
			}

			if ((scopesSupported != null) && (scopesSupported.length > 0)) {
				JSONArray jsonArray = _jsonFactory.createJSONArray();

				for (String scope : scopesSupported) {
					jsonArray.put(scope);
				}

				jsonObject.put("scopes_supported", jsonArray);
			}

			if (signedMetadataEnabled) {
				jsonObject.put(
					"signed_metadata",
					_oAuthClientPRSignedMetadataSigner.sign(
						jsonObject, signedMetadataKeyAlias));
			}

			return jsonObject.toString();
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			throw new OAuthClientPRLocalMetadataMetadataJSONException(
				exception.getMessage(), exception);
		}
	}

	private String _join(String[] values) {
		if (ArrayUtil.isEmpty(values)) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			if (i > 0) {
				sb.append(',');
			}

			sb.append(values[i]);
		}

		return sb.toString();
	}

	private String _normalizeResourcePath(String path) {
		if ((path == null) || path.isEmpty() || path.equals("/")) {
			return "";
		}

		if (!path.startsWith("/")) {
			return "/" + path;
		}

		return path;
	}

	private void _validate(
			OAuthClientPRLocalMetadata oldOAuthClientPRLocalMetadata,
			long companyId, String resource, String jwksURI,
			String localWellKnownURI)
		throws PortalException {

		if (FeatureFlagManagerUtil.isEnabled(companyId, "LPD-99999")) {
			if (Validator.isNull(resource)) {
				throw new OAuthClientPRLocalMetadataResourceException();
			}

			_validateURL(resource);
			_validateURL(jwksURI);
		}

		if (oldOAuthClientPRLocalMetadata == null) {
			OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
				oAuthClientPRLocalMetadataPersistence.fetchByC_LWKURI(
					companyId, localWellKnownURI);

			if (oAuthClientPRLocalMetadata != null) {
				throw new DuplicateOAuthClientPRLocalMetadataException();
			}
		}

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
			oAuthClientPRLocalMetadataPersistence.fetchByC_R(
				companyId, resource);

		if ((oAuthClientPRLocalMetadata != null) &&
			!Objects.equals(
				oldOAuthClientPRLocalMetadata, oAuthClientPRLocalMetadata)) {

			throw new DuplicateOAuthClientPRLocalMetadataException();
		}
	}

	private void _validateURL(String urlString) throws PortalException {
		if (Validator.isNull(urlString)) {
			return;
		}

		try {
			URL url = new URL(urlString);

			if (!Http.HTTPS.equalsIgnoreCase(url.getProtocol())) {
				throw new OAuthClientPRLocalMetadataLocalWellKnownURIException(
					urlString);
			}
		}
		catch (MalformedURLException malformedURLException) {
			throw new OAuthClientPRLocalMetadataLocalWellKnownURIException(
				urlString, malformedURLException);
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OAuthClientPRSignedMetadataSigner
		_oAuthClientPRSignedMetadataSigner;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
