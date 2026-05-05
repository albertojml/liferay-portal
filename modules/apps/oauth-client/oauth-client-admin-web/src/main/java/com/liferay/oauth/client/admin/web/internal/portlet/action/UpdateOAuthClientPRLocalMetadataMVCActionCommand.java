/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.portlet.action;

import com.liferay.oauth.client.constants.OAuthClientAdminPortletKeys;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Moreno
 */
@Component(
	property = {
		"jakarta.portlet.name=" + OAuthClientAdminPortletKeys.OAUTH_CLIENT_ADMIN,
		"mvc.command.name=/oauth_client_admin/update_oauth_client_pr_local_metadata"
	},
	service = MVCActionCommand.class
)
public class UpdateOAuthClientPRLocalMetadataMVCActionCommand
	implements MVCActionCommand {

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			if (!FeatureFlagManagerUtil.isEnabled(
					themeDisplay.getCompanyId(), "LPD-99999")) {

				return true;
			}

			long oAuthClientPRLocalMetadataId = ParamUtil.getLong(
				actionRequest, "oAuthClientPRLocalMetadataId");

			String resource = ParamUtil.getString(actionRequest, "resource");
			String authorizationServers = ParamUtil.getString(
				actionRequest, "authorizationServers");
			String bearerMethodsSupported = ParamUtil.getString(
				actionRequest, "bearerMethodsSupported");
			String jwksURI = ParamUtil.getString(actionRequest, "jwksURI");
			boolean localWellKnownEnabled = ParamUtil.getBoolean(
				actionRequest, "localWellKnownEnabled");
			String resourceDocumentation = ParamUtil.getString(
				actionRequest, "resourceDocumentation");
			String scopesSupported = ParamUtil.getString(
				actionRequest, "scopesSupported");
			boolean signedMetadataEnabled = ParamUtil.getBoolean(
				actionRequest, "signedMetadataEnabled");
			String signedMetadataKeyAlias = ParamUtil.getString(
				actionRequest, "signedMetadataKeyAlias");

			OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata =
				_oAuthClientPRLocalMetadataService.
					fetchOAuthClientPRLocalMetadata(
						oAuthClientPRLocalMetadataId);

			if (oAuthClientPRLocalMetadata == null) {
				_oAuthClientPRLocalMetadataService.
					addOAuthClientPRLocalMetadata(
						null, resource,
						StringUtil.split(
							authorizationServers, StringPool.COMMA),
						StringUtil.split(
							bearerMethodsSupported, StringPool.COMMA),
						jwksURI, localWellKnownEnabled, resourceDocumentation,
						StringUtil.split(scopesSupported, StringPool.COMMA),
						signedMetadataEnabled, signedMetadataKeyAlias);
			}
			else {
				_oAuthClientPRLocalMetadataService.
					updateOAuthClientPRLocalMetadata(
						oAuthClientPRLocalMetadata.
							getOAuthClientPRLocalMetadataId(),
						resource,
						StringUtil.split(
							authorizationServers, StringPool.COMMA),
						StringUtil.split(
							bearerMethodsSupported, StringPool.COMMA),
						jwksURI, localWellKnownEnabled, resourceDocumentation,
						StringUtil.split(scopesSupported, StringPool.COMMA),
						signedMetadataEnabled, signedMetadataKeyAlias);
			}

			return true;
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			Class<?> clazz = portalException.getClass();

			SessionErrors.add(actionRequest, clazz.getName(), portalException);

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateOAuthClientPRLocalMetadataMVCActionCommand.class);

	@Reference
	private OAuthClientPRLocalMetadataService
		_oAuthClientPRLocalMetadataService;

}
