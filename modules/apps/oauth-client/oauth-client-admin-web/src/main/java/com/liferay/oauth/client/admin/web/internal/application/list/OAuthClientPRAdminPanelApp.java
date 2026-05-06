/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.oauth.client.constants.OAuthClientAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Moreno
 */
@Component(
	property = {
		"panel.app.order:Integer=310",
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_SECURITY
	},
	service = PanelApp.class
)
public class OAuthClientPRAdminPanelApp extends BasePanelApp {

	@Override
	public String getIcon() {
		return "shield-check";
	}

	@Override
	public Portlet getPortlet() {
		return _portlet;
	}

	@Override
	public String getPortletId() {
		return OAuthClientAdminPortletKeys.OAUTH_CLIENT_PR_ADMIN;
	}

	@Override
	public boolean isShow(
			PermissionChecker permissionChecker, Group group)
		throws PortalException {

		try {
			if (!FeatureFlagManagerUtil.isEnabled(
					permissionChecker.getCompanyId(), "LPD-99999")) {

				return false;
			}
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isDebugEnabled()) {
				_log.debug(illegalStateException);
			}

			return false;
		}

		return super.isShow(permissionChecker, group);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OAuthClientPRAdminPanelApp.class);

	@Reference(
		target = "(jakarta.portlet.name=" + OAuthClientAdminPortletKeys.OAUTH_CLIENT_PR_ADMIN + ")"
	)
	private Portlet _portlet;

}
