/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.jose;

import com.liferay.oauth.client.persistence.exception.OAuthClientPRLocalMetadataMetadataJSONException;
import com.liferay.oauth.client.persistence.internal.configuration.OAuthClientPRSignedMetadataConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.io.FileInputStream;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;

import java.util.Iterator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Produces RFC 9728 §2.1 {@code signed_metadata} compact-serialized JWTs from
 * the unsigned metadata payload. The signing key is looked up by alias in a
 * keystore configured at the company scope via
 * {@link OAuthClientPRSignedMetadataConfiguration}.
 *
 * @author Alberto Moreno
 */
@Component(service = OAuthClientPRSignedMetadataSigner.class)
public class OAuthClientPRSignedMetadataSigner {

	public String sign(JSONObject jsonObject, String keyAlias)
		throws PortalException {

		if (Validator.isNull(keyAlias)) {
			throw new OAuthClientPRLocalMetadataMetadataJSONException(
				"Signed metadata is enabled but no key alias is configured");
		}

		try {
			OAuthClientPRSignedMetadataConfiguration
				oAuthClientPRSignedMetadataConfiguration =
					_configurationProvider.getCompanyConfiguration(
						OAuthClientPRSignedMetadataConfiguration.class,
						CompanyThreadLocal.getCompanyId());

			PrivateKey privateKey = _loadPrivateKey(
				oAuthClientPRSignedMetadataConfiguration, keyAlias);

			JWSAlgorithm jwsAlgorithm = JWSAlgorithm.parse(
				oAuthClientPRSignedMetadataConfiguration.algorithm());

			JWSSigner jwsSigner = new RSASSASigner(privateKey);

			JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

			Iterator<String> iterator = jsonObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				builder.claim(key, jsonObject.get(key));
			}

			SignedJWT signedJWT = new SignedJWT(
				new JWSHeader.Builder(
					jwsAlgorithm
				).keyID(
					keyAlias
				).type(
					JOSEObjectType.JWT
				).build(),
				builder.build());

			signedJWT.sign(jwsSigner);

			return signedJWT.serialize();
		}
		catch (PortalException portalException) {
			throw portalException;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new OAuthClientPRLocalMetadataMetadataJSONException(
				"Unable to sign protected resource metadata", exception);
		}
	}

	private PrivateKey _loadPrivateKey(
			OAuthClientPRSignedMetadataConfiguration
				oAuthClientPRSignedMetadataConfiguration,
			String keyAlias)
		throws Exception {

		String keyStorePath =
			oAuthClientPRSignedMetadataConfiguration.keyStorePath();

		if (Validator.isNull(keyStorePath) ||
			!Files.exists(Paths.get(keyStorePath))) {

			throw new OAuthClientPRLocalMetadataMetadataJSONException(
				"OAuth client protected resource signed metadata keystore is " +
					"not configured or does not exist");
		}

		KeyStore keyStore = KeyStore.getInstance(
			oAuthClientPRSignedMetadataConfiguration.keyStoreType());

		char[] keyStorePassword = _toCharArray(
			oAuthClientPRSignedMetadataConfiguration.keyStorePassword());

		try (InputStream inputStream = new FileInputStream(keyStorePath)) {
			keyStore.load(inputStream, keyStorePassword);
		}

		String keyPasswordString =
			oAuthClientPRSignedMetadataConfiguration.keyPassword();

		char[] keyPassword =
			Validator.isNull(keyPasswordString) ? keyStorePassword :
				_toCharArray(keyPasswordString);

		Key key = keyStore.getKey(keyAlias, keyPassword);

		if (!(key instanceof PrivateKey)) {
			throw new OAuthClientPRLocalMetadataMetadataJSONException(
				"No private key found for alias " + keyAlias);
		}

		return (PrivateKey)key;
	}

	private char[] _toCharArray(String value) {
		if (value == null) {
			return new char[0];
		}

		return value.toCharArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientPRSignedMetadataSigner.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

}