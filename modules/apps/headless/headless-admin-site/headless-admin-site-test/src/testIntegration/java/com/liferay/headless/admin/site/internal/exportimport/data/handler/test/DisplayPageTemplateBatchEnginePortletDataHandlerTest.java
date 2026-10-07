/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.exportimport.test.rule.ExportImportScopeClassTestRule;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class DisplayPageTemplateBatchEnginePortletDataHandlerTest
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

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				null, userId, groupId,
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				null, _portal.getClassNameId(AssetCategory.class.getName()),
				null, RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_APPROVED,
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		layoutPageTemplateEntry.setModifiedDate(modifiedDate);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry);

		return layoutPageTemplateEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(String externalReferenceCode, long groupId)
		throws Exception {

		_layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
			_fetchLayoutPageTemplateEntry(externalReferenceCode, groupId));
	}

	@Override
	protected Object fetchEntry(String externalReferenceCode, long groupId)
		throws Exception {

		return _fetchLayoutPageTemplateEntry(externalReferenceCode, groupId);
	}

	@Override
	protected long getCreatorUserId(String externalReferenceCode, long groupId)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_fetchLayoutPageTemplateEntry(externalReferenceCode, groupId);

		return layoutPageTemplateEntry.getUserId();
	}

	@Override
	protected Object getEntryValue(String externalReferenceCode, long groupId)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_fetchLayoutPageTemplateEntry(externalReferenceCode, groupId);

		return layoutPageTemplateEntry.getName();
	}

	@Override
	protected ExportImportScopeClassTestRule
		getExportImportScopeClassTestRule() {

		return exportImportScopeClassTestRule;
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		return (ExportImportVulcanBatchEngineTaskItemDelegate<?>)
			_displayPageTemplateResource;
	}

	@Override
	protected long getPrimaryKey(String externalReferenceCode, long groupId)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_fetchLayoutPageTemplateEntry(externalReferenceCode, groupId);

		return layoutPageTemplateEntry.getLayoutPageTemplateEntryId();
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

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_fetchLayoutPageTemplateEntry(externalReferenceCode, groupId);

		_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			RandomTestUtil.randomString());
	}

	private LayoutPageTemplateEntry _fetchLayoutPageTemplateEntry(
			String externalReferenceCode, long groupId)
		throws Exception {

		return _layoutPageTemplateEntryLocalService.
			fetchLayoutPageTemplateEntryByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject(
		filter = "export.import.vulcan.batch.engine.task.item.delegate=true"
	)
	private DisplayPageTemplateResource _displayPageTemplateResource;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}