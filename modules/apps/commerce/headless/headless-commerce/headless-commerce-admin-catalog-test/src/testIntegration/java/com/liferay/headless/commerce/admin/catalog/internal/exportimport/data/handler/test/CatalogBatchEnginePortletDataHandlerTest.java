/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.exportimport.test.rule.ExportImportScopeClassTestRule;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.CatalogResource;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@RunWith(Arquillian.class)
public class CatalogBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	public static final ExportImportScopeClassTestRule
		exportImportScopeClassTestRule = new ExportImportScopeClassTestRule(
			Scope.COMPANY);

	@Override
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		long companyId = _getCompanyId(groupId);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				companyId);

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.getOrAddEmptyCommerceCatalog(
				RandomTestUtil.randomString(), companyId, userId,
				commerceCurrency.getCode());

		return commerceCatalog.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, Date modifiedDate, long userId)
		throws Exception {

		long companyId = _getCompanyId(groupId);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.fetchPrimaryCommerceCurrency(
				companyId);

		CommerceCatalog commerceCatalog =
			_commerceCatalogLocalService.addCommerceCatalog(
				null, RandomTestUtil.randomString(), commerceCurrency.getCode(),
				LocaleUtil.toLanguageId(LocaleUtil.getDefault()),
				ServiceContextTestUtil.getServiceContext(
					companyId, groupId, userId));

		commerceCatalog.setModifiedDate(modifiedDate);

		commerceCatalog = _commerceCatalogLocalService.updateCommerceCatalog(
			commerceCatalog);

		return commerceCatalog.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(String externalReferenceCode, long groupId)
		throws Exception {

		_commerceCatalogLocalService.deleteCommerceCatalog(
			_getCommerceCatalog(externalReferenceCode, groupId));
	}

	@Override
	protected long getCreatorUserId(String externalReferenceCode, long groupId)
		throws Exception {

		CommerceCatalog commerceCatalog = _getCommerceCatalog(
			externalReferenceCode, groupId);

		return commerceCatalog.getUserId();
	}

	@Override
	protected Object getEntryValue(String externalReferenceCode, long groupId)
		throws Exception {

		CommerceCatalog commerceCatalog = _getCommerceCatalog(
			externalReferenceCode, groupId);

		return commerceCatalog.getName();
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
			CatalogResource.class);
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		return TransformUtil.transform(
			_commerceCatalogLocalService.getCommerceCatalogs(
				_getCompanyId(groupId)),
			CommerceCatalog::getExternalReferenceCode);
	}

	@Override
	protected long getPrimaryKey(String externalReferenceCode, long groupId)
		throws Exception {

		CommerceCatalog commerceCatalog = _getCommerceCatalog(
			externalReferenceCode, groupId);

		return commerceCatalog.getCommerceCatalogId();
	}

	@Override
	protected int getStatus(String externalReferenceCode, long groupId)
		throws Exception {

		CommerceCatalog commerceCatalog = _getCommerceCatalog(
			externalReferenceCode, groupId);

		return commerceCatalog.getStatus();
	}

	@Override
	protected boolean supportsComments() {
		return false;
	}

	@Override
	protected boolean supportsEmptyEntries() {
		return true;
	}

	@Override
	protected boolean supportsPermissions() {
		return true;
	}

	@Override
	protected void updateEntry(String externalReferenceCode, long groupId)
		throws Exception {

		CommerceCatalog commerceCatalog = _getCommerceCatalog(
			externalReferenceCode, groupId);

		_commerceCatalogLocalService.updateCommerceCatalog(
			commerceCatalog.getCommerceCatalogId(),
			commerceCatalog.getAccountEntryId(), RandomTestUtil.randomString(),
			commerceCatalog.getCommerceCurrencyCode(),
			commerceCatalog.getCatalogDefaultLanguageId());
	}

	private CommerceCatalog _getCommerceCatalog(
			String externalReferenceCode, long groupId)
		throws Exception {

		return _commerceCatalogLocalService.
			fetchCommerceCatalogByExternalReferenceCode(
				externalReferenceCode, _getCompanyId(groupId));
	}

	private long _getCompanyId(long groupId) throws Exception {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	@Inject
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

}