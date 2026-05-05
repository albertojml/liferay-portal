/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.service.http;

import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>OAuthClientPRLocalMetadataServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthClientPRLocalMetadataServiceHttp {

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				addOAuthClientPRLocalMetadata(
					HttpPrincipal httpPrincipal, String externalReferenceCode,
					String resource, String[] authorizationServers,
					String[] bearerMethodsSupported, String jwksURI,
					boolean localWellKnownEnabled, String resourceDocumentation,
					String[] scopesSupported, boolean signedMetadataEnabled,
					String signedMetadataKeyAlias)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"addOAuthClientPRLocalMetadata",
				_addOAuthClientPRLocalMetadataParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, externalReferenceCode, resource,
				authorizationServers, bearerMethodsSupported, jwksURI,
				localWellKnownEnabled, resourceDocumentation, scopesSupported,
				signedMetadataEnabled, signedMetadataKeyAlias);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientPRLocalMetadata)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				deleteOAuthClientPRLocalMetadata(
					HttpPrincipal httpPrincipal,
					long oAuthClientPRLocalMetadataId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"deleteOAuthClientPRLocalMetadata",
				_deleteOAuthClientPRLocalMetadataParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthClientPRLocalMetadataId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientPRLocalMetadata)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				fetchOAuthClientPRLocalMetadata(
					HttpPrincipal httpPrincipal,
					long oAuthClientPRLocalMetadataId)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"fetchOAuthClientPRLocalMetadata",
				_fetchOAuthClientPRLocalMetadataParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthClientPRLocalMetadataId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientPRLocalMetadata)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				fetchOAuthClientPRLocalMetadata(
					HttpPrincipal httpPrincipal, long companyId,
					String resource)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"fetchOAuthClientPRLocalMetadata",
				_fetchOAuthClientPRLocalMetadataParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, resource);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientPRLocalMetadata)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata>
			getCompanyOAuthClientPRLocalMetadata(
				HttpPrincipal httpPrincipal, long companyId) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"getCompanyOAuthClientPRLocalMetadata",
				_getCompanyOAuthClientPRLocalMetadataParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientPRLocalMetadata>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static java.util.List
		<com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata>
			getCompanyOAuthClientPRLocalMetadata(
				HttpPrincipal httpPrincipal, long companyId, int start,
				int end) {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"getCompanyOAuthClientPRLocalMetadata",
				_getCompanyOAuthClientPRLocalMetadataParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, companyId, start, end);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (java.util.List
				<com.liferay.oauth.client.persistence.model.
					OAuthClientPRLocalMetadata>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	public static
		com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata
				updateOAuthClientPRLocalMetadata(
					HttpPrincipal httpPrincipal,
					long oAuthClientPRLocalMetadataId, String resource,
					String[] authorizationServers,
					String[] bearerMethodsSupported, String jwksURI,
					boolean localWellKnownEnabled, String resourceDocumentation,
					String[] scopesSupported, boolean signedMetadataEnabled,
					String signedMetadataKeyAlias)
			throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				OAuthClientPRLocalMetadataServiceUtil.class,
				"updateOAuthClientPRLocalMetadata",
				_updateOAuthClientPRLocalMetadataParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, oAuthClientPRLocalMetadataId, resource,
				authorizationServers, bearerMethodsSupported, jwksURI,
				localWellKnownEnabled, resourceDocumentation, scopesSupported,
				signedMetadataEnabled, signedMetadataKeyAlias);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception exception) {
				if (exception instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						exception;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					exception);
			}

			return (com.liferay.oauth.client.persistence.model.
				OAuthClientPRLocalMetadata)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException
					systemException) {

			_log.error(systemException, systemException);

			throw systemException;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		OAuthClientPRLocalMetadataServiceHttp.class);

	private static final Class<?>[]
		_addOAuthClientPRLocalMetadataParameterTypes0 = new Class[] {
			String.class, String.class, String[].class, String[].class,
			String.class, boolean.class, String.class, String[].class,
			boolean.class, String.class
		};
	private static final Class<?>[]
		_deleteOAuthClientPRLocalMetadataParameterTypes1 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchOAuthClientPRLocalMetadataParameterTypes2 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_fetchOAuthClientPRLocalMetadataParameterTypes3 = new Class[] {
			long.class, String.class
		};
	private static final Class<?>[]
		_getCompanyOAuthClientPRLocalMetadataParameterTypes4 = new Class[] {
			long.class
		};
	private static final Class<?>[]
		_getCompanyOAuthClientPRLocalMetadataParameterTypes5 = new Class[] {
			long.class, int.class, int.class
		};
	private static final Class<?>[]
		_updateOAuthClientPRLocalMetadataParameterTypes6 = new Class[] {
			long.class, String.class, String[].class, String[].class,
			String.class, boolean.class, String.class, String[].class,
			boolean.class, String.class
		};

}
// LIFERAY-SERVICE-BUILDER-HASH:306842863