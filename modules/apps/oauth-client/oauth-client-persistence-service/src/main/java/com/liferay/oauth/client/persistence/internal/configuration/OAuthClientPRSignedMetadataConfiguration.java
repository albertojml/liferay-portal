/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * Configuration for the keystore that holds the private signing keys used to
 * produce the {@code signed_metadata} claim of an RFC 9728 Protected Resource
 * Metadata document. Each persisted Protected Resource record references a
 * keystore entry by its alias.
 *
 * @author Alberto Moreno
 */
@ExtendedObjectClassDefinition(
	category = "oauth-client",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.oauth.client.persistence.internal.configuration.OAuthClientPRSignedMetadataConfiguration",
	localization = "content/Language",
	name = "oauth-client-pr-signed-metadata-configuration-name"
)
public interface OAuthClientPRSignedMetadataConfiguration {

	@Meta.AD(
		deflt = "RS256",
		description = "oauth-client-pr-signed-metadata-algorithm-help",
		name = "oauth-client-pr-signed-metadata-algorithm",
		optionValues = {"RS256", "RS384", "RS512", "PS256", "PS384", "PS512"},
		required = false
	)
	public String algorithm();

	@Meta.AD(
		deflt = "",
		description = "oauth-client-pr-signed-metadata-key-password-help",
		name = "oauth-client-pr-signed-metadata-key-password", required = false,
		type = Meta.Type.Password
	)
	public String keyPassword();

	@Meta.AD(
		deflt = "",
		description = "oauth-client-pr-signed-metadata-keystore-password-help",
		name = "oauth-client-pr-signed-metadata-keystore-password",
		required = false, type = Meta.Type.Password
	)
	public String keyStorePassword();

	@Meta.AD(
		deflt = "",
		description = "oauth-client-pr-signed-metadata-keystore-path-help",
		name = "oauth-client-pr-signed-metadata-keystore-path", required = false
	)
	public String keyStorePath();

	@Meta.AD(
		deflt = "PKCS12",
		description = "oauth-client-pr-signed-metadata-keystore-type-help",
		name = "oauth-client-pr-signed-metadata-keystore-type",
		optionValues = {"PKCS12", "JKS"}, required = false
	)
	public String keyStoreType();

}