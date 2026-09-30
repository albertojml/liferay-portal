/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.rule.ExportImportScopeClassTestRule;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.site.resource.v1_0.NavigationMenuResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class NavigationMenuBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final ExportImportScopeClassTestRule
		exportImportScopeClassTestRule = new ExportImportScopeClassTestRule(
			Scope.SITE);

	@Override
	protected String addEntry(long groupId, Date modifiedDate, long userId)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				null, userId, groupId, RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		siteNavigationMenu.setModifiedDate(modifiedDate);

		siteNavigationMenu =
			_siteNavigationMenuLocalService.updateSiteNavigationMenu(
				siteNavigationMenu);

		return siteNavigationMenu.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(String externalReferenceCode, long groupId)
		throws Exception {

		_siteNavigationMenuLocalService.deleteSiteNavigationMenu(
			_getSiteNavigationMenu(externalReferenceCode, groupId));
	}

	@Override
	protected long getCreatorUserId(String externalReferenceCode, long groupId)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = _getSiteNavigationMenu(
			externalReferenceCode, groupId);

		return siteNavigationMenu.getUserId();
	}

	@Override
	protected Object getEntryValue(String externalReferenceCode, long groupId)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = _getSiteNavigationMenu(
			externalReferenceCode, groupId);

		return siteNavigationMenu.getName();
	}

	@Override
	protected ExportImportScopeClassTestRule
		getExportImportScopeClassTestRule() {

		return exportImportScopeClassTestRule;
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return getExportImportVulcanBatchEngineTaskItemDelegate(
			NavigationMenuResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_siteNavigationMenuLocalService.getSiteNavigationMenus(groupId),
			SiteNavigationMenu::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(String externalReferenceCode, long groupId)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = _getSiteNavigationMenu(
			externalReferenceCode, groupId);

		return siteNavigationMenu.getSiteNavigationMenuId();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return false;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(String externalReferenceCode, long groupId)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu = _getSiteNavigationMenu(
			externalReferenceCode, groupId);

		_siteNavigationMenuLocalService.updateSiteNavigationMenu(
			siteNavigationMenu.getUserId(),
			siteNavigationMenu.getSiteNavigationMenuId(),
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				groupId, siteNavigationMenu.getUserId()));
	}

	private SiteNavigationMenu _getSiteNavigationMenu(
			String externalReferenceCode, long groupId)
		throws Exception {

		return _siteNavigationMenuLocalService.
			fetchSiteNavigationMenuByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}