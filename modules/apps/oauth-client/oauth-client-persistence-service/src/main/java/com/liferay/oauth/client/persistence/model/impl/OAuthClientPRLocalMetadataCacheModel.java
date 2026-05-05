/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.model.impl;

import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuthClientPRLocalMetadata in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuthClientPRLocalMetadataCacheModel
	implements CacheModel<OAuthClientPRLocalMetadata>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OAuthClientPRLocalMetadataCacheModel)) {
			return false;
		}

		OAuthClientPRLocalMetadataCacheModel
			oAuthClientPRLocalMetadataCacheModel =
				(OAuthClientPRLocalMetadataCacheModel)object;

		if ((oAuthClientPRLocalMetadataId ==
				oAuthClientPRLocalMetadataCacheModel.
					oAuthClientPRLocalMetadataId) &&
			(mvccVersion == oAuthClientPRLocalMetadataCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, oAuthClientPRLocalMetadataId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", oAuthClientPRLocalMetadataId=");
		sb.append(oAuthClientPRLocalMetadataId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", authorizationServers=");
		sb.append(authorizationServers);
		sb.append(", bearerMethodsSupported=");
		sb.append(bearerMethodsSupported);
		sb.append(", jwksURI=");
		sb.append(jwksURI);
		sb.append(", localWellKnownEnabled=");
		sb.append(localWellKnownEnabled);
		sb.append(", localWellKnownURI=");
		sb.append(localWellKnownURI);
		sb.append(", metadataJSON=");
		sb.append(metadataJSON);
		sb.append(", resource=");
		sb.append(resource);
		sb.append(", resourceDocumentation=");
		sb.append(resourceDocumentation);
		sb.append(", scopesSupported=");
		sb.append(scopesSupported);
		sb.append(", signedMetadataEnabled=");
		sb.append(signedMetadataEnabled);
		sb.append(", signedMetadataKeyAlias=");
		sb.append(signedMetadataKeyAlias);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuthClientPRLocalMetadata toEntityModel() {
		OAuthClientPRLocalMetadataImpl oAuthClientPRLocalMetadataImpl =
			new OAuthClientPRLocalMetadataImpl();

		oAuthClientPRLocalMetadataImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			oAuthClientPRLocalMetadataImpl.setUuid("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			oAuthClientPRLocalMetadataImpl.setExternalReferenceCode("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		oAuthClientPRLocalMetadataImpl.setOAuthClientPRLocalMetadataId(
			oAuthClientPRLocalMetadataId);
		oAuthClientPRLocalMetadataImpl.setCompanyId(companyId);
		oAuthClientPRLocalMetadataImpl.setUserId(userId);

		if (userName == null) {
			oAuthClientPRLocalMetadataImpl.setUserName("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuthClientPRLocalMetadataImpl.setCreateDate(null);
		}
		else {
			oAuthClientPRLocalMetadataImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			oAuthClientPRLocalMetadataImpl.setModifiedDate(null);
		}
		else {
			oAuthClientPRLocalMetadataImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (authorizationServers == null) {
			oAuthClientPRLocalMetadataImpl.setAuthorizationServers("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setAuthorizationServers(
				authorizationServers);
		}

		if (bearerMethodsSupported == null) {
			oAuthClientPRLocalMetadataImpl.setBearerMethodsSupported("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setBearerMethodsSupported(
				bearerMethodsSupported);
		}

		if (jwksURI == null) {
			oAuthClientPRLocalMetadataImpl.setJwksURI("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setJwksURI(jwksURI);
		}

		oAuthClientPRLocalMetadataImpl.setLocalWellKnownEnabled(
			localWellKnownEnabled);

		if (localWellKnownURI == null) {
			oAuthClientPRLocalMetadataImpl.setLocalWellKnownURI("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setLocalWellKnownURI(
				localWellKnownURI);
		}

		if (metadataJSON == null) {
			oAuthClientPRLocalMetadataImpl.setMetadataJSON("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setMetadataJSON(metadataJSON);
		}

		if (resource == null) {
			oAuthClientPRLocalMetadataImpl.setResource("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setResource(resource);
		}

		if (resourceDocumentation == null) {
			oAuthClientPRLocalMetadataImpl.setResourceDocumentation("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setResourceDocumentation(
				resourceDocumentation);
		}

		if (scopesSupported == null) {
			oAuthClientPRLocalMetadataImpl.setScopesSupported("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setScopesSupported(scopesSupported);
		}

		oAuthClientPRLocalMetadataImpl.setSignedMetadataEnabled(
			signedMetadataEnabled);

		if (signedMetadataKeyAlias == null) {
			oAuthClientPRLocalMetadataImpl.setSignedMetadataKeyAlias("");
		}
		else {
			oAuthClientPRLocalMetadataImpl.setSignedMetadataKeyAlias(
				signedMetadataKeyAlias);
		}

		oAuthClientPRLocalMetadataImpl.resetOriginalValues();

		return oAuthClientPRLocalMetadataImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		oAuthClientPRLocalMetadataId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		authorizationServers = objectInput.readUTF();
		bearerMethodsSupported = objectInput.readUTF();
		jwksURI = objectInput.readUTF();

		localWellKnownEnabled = objectInput.readBoolean();
		localWellKnownURI = objectInput.readUTF();
		metadataJSON = objectInput.readUTF();
		resource = objectInput.readUTF();
		resourceDocumentation = objectInput.readUTF();
		scopesSupported = objectInput.readUTF();

		signedMetadataEnabled = objectInput.readBoolean();
		signedMetadataKeyAlias = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(oAuthClientPRLocalMetadataId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (authorizationServers == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(authorizationServers);
		}

		if (bearerMethodsSupported == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(bearerMethodsSupported);
		}

		if (jwksURI == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jwksURI);
		}

		objectOutput.writeBoolean(localWellKnownEnabled);

		if (localWellKnownURI == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(localWellKnownURI);
		}

		if (metadataJSON == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(metadataJSON);
		}

		if (resource == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(resource);
		}

		if (resourceDocumentation == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(resourceDocumentation);
		}

		if (scopesSupported == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopesSupported);
		}

		objectOutput.writeBoolean(signedMetadataEnabled);

		if (signedMetadataKeyAlias == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(signedMetadataKeyAlias);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long oAuthClientPRLocalMetadataId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String authorizationServers;
	public String bearerMethodsSupported;
	public String jwksURI;
	public boolean localWellKnownEnabled;
	public String localWellKnownURI;
	public String metadataJSON;
	public String resource;
	public String resourceDocumentation;
	public String scopesSupported;
	public boolean signedMetadataEnabled;
	public String signedMetadataKeyAlias;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1459316590