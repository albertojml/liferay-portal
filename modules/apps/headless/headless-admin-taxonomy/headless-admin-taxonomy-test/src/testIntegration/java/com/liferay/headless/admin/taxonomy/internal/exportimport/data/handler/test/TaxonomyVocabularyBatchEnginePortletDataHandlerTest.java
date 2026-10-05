/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.test.rule.ExportImportScopeClassTestRule;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
public class TaxonomyVocabularyBatchEnginePortletDataHandlerTest
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
	protected String addEmptyEntry(long groupId, long userId) throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getOrAddEmptyVocabulary(
				RandomTestUtil.randomString(), userId, groupId);

		return assetVocabulary.getExternalReferenceCode();
	}

	@Override
	protected String addEntry(long groupId, Date modifiedDate, long userId)
		throws Exception {

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				userId, groupId, RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId, userId));

		assetVocabulary.setModifiedDate(modifiedDate);

		assetVocabulary = _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);

		return assetVocabulary.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(String externalReferenceCode, long groupId)
		throws Exception {

		_assetVocabularyLocalService.deleteVocabulary(
			_fetchAssetVocabulary(externalReferenceCode, groupId));
	}

	@Override
	protected Object fetchEntry(String externalReferenceCode, long groupId)
		throws Exception {

		return _fetchAssetVocabulary(externalReferenceCode, groupId);
	}

	@Override
	protected long getCreatorUserId(String externalReferenceCode, long groupId)
		throws Exception {

		AssetVocabulary assetVocabulary = _fetchAssetVocabulary(
			externalReferenceCode, groupId);

		return assetVocabulary.getUserId();
	}

	@Override
	protected Object getEntryValue(String externalReferenceCode, long groupId)
		throws Exception {

		AssetVocabulary assetVocabulary = _fetchAssetVocabulary(
			externalReferenceCode, groupId);

		return assetVocabulary.getTitle(LocaleUtil.getSiteDefault());
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
			_taxonomyVocabularyResource;
	}

	@Override
	protected long getPrimaryKey(String externalReferenceCode, long groupId)
		throws Exception {

		AssetVocabulary assetVocabulary = _fetchAssetVocabulary(
			externalReferenceCode, groupId);

		return assetVocabulary.getVocabularyId();
	}

	@Override
	protected int getStatus(String externalReferenceCode, long groupId)
		throws Exception {

		AssetVocabulary assetVocabulary = _fetchAssetVocabulary(
			externalReferenceCode, groupId);

		return assetVocabulary.getStatus();
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

		AssetVocabulary assetVocabulary = _fetchAssetVocabulary(
			externalReferenceCode, groupId);

		_assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getExternalReferenceCode(),
			assetVocabulary.getVocabularyId(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), RandomTestUtil.randomString()
			).build(),
			assetVocabulary.getDescriptionMap(), assetVocabulary.getSettings());
	}

	private AssetVocabulary _fetchAssetVocabulary(
			String externalReferenceCode, long groupId)
		throws Exception {

		return _assetVocabularyLocalService.
			fetchAssetVocabularyByExternalReferenceCode(
				externalReferenceCode, groupId);
	}

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject(
		filter = "export.import.vulcan.batch.engine.task.item.delegate=true"
	)
	private TaxonomyVocabularyResource _taxonomyVocabularyResource;

}