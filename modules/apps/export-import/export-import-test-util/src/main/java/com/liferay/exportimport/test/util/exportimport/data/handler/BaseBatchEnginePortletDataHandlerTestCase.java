/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test.util.exportimport.data.handler;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.test.util.LazyReferencingTestUtil;
import com.liferay.exportimport.test.util.lar.BasePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate.Scope;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.SystemEvent;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Alberto Javier Moreno Lage
 */
public abstract class BaseBatchEnginePortletDataHandlerTestCase
	extends BasePortletDataHandlerTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_targetCompany = CompanyTestUtil.addCompany();

		_targetUser = UserTestUtil.addCompanyAdminUser(_targetCompany);

		Group companyGroup = _groupLocalService.getCompanyGroup(
			TestPropsValues.getCompanyId());

		_companyLayout = LayoutTestUtil.addTypePortletLayout(
			companyGroup.getGroupId());

		_companyTargetLayout = LayoutTestUtil.addTypePortletLayout(
			_targetCompany.getGroupId());

		_depotEntry = _addDepotEntry();
		_targetDepotEntry = _addDepotEntry();

		_depotLayout = LayoutTestUtil.addTypePortletLayout(
			_depotEntry.getGroupId());
		_depotTargetLayout = LayoutTestUtil.addTypePortletLayout(
			_targetDepotEntry.getGroupId());

		_group = GroupTestUtil.addGroup();
		_targetGroup = GroupTestUtil.addGroup();

		_siteLayout = LayoutTestUtil.addTypePortletLayout(_group.getGroupId());
		_siteTargetLayout = LayoutTestUtil.addTypePortletLayout(
			_targetGroup.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {

		// Every other layout cascades with its group, but the company group
		// outlives the class

		_layoutLocalService.deleteLayout(_companyLayout);

		_depotEntryLocalService.deleteDepotEntry(_depotEntry);
		_depotEntryLocalService.deleteDepotEntry(_targetDepotEntry);

		_groupLocalService.deleteGroup(_group);
		_groupLocalService.deleteGroup(_targetGroup);

		// The company outlives its asynchronous provisioning, which seeds
		// system data masks that only the data mask batch engine unit may
		// delete

		BatchEngineUnitThreadLocal.setFileName(
			"com.liferay.headless.data.mask.impl_0.0.0");

		try {
			_companyLocalService.deleteCompany(_targetCompany);
		}
		finally {
			BatchEngineUnitThreadLocal.setFileName(StringPool.BLANK);
		}
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		if (_getScope() == Scope.COMPANY) {
			setUpTargetCompany(_targetCompany, _targetUser);
		}
	}

	@Test
	public void testExportImportComments() throws Exception {
		if (!supportsComments()) {
			return;
		}

		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		String body = RandomTestUtil.randomString();

		_addComment(groupId, externalReferenceCode, body);

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.COMMENTS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		List<String> comments = getComments(
			_getTargetGroupId(scope), externalReferenceCode);

		Assert.assertTrue(
			comments.toString(),
			ListUtil.exists(comments, comment -> comment.contains(body)));
	}

	@Test
	public void testExportImportData() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode1 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());
		String externalReferenceCode2 = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		_exportImport(scope, Collections.emptyMap(), null, null);

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode1));
		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode2));
	}

	@Test
	public void testExportImportDeletions() throws Exception {
		long groupId = _getGroupId(_getScope());

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		long primaryKey = getPrimaryKey(groupId, externalReferenceCode);

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		deleteEntry(groupId, externalReferenceCode);

		long classNameId = _classNameLocalService.getClassNameId(
			exportImportDescriptor.getModelClassName());

		SystemEvent systemEvent = _systemEventLocalService.fetchSystemEvent(
			groupId, classNameId, primaryKey, SystemEventConstants.TYPE_DELETE);

		if (systemEvent == null) {

			// Entities without a group, such as company scoped entities,
			// record their deletion system events under group 0

			systemEvent = _systemEventLocalService.fetchSystemEvent(
				0, classNameId, primaryKey, SystemEventConstants.TYPE_DELETE);
		}

		Assert.assertNotNull(systemEvent);
		Assert.assertEquals(
			externalReferenceCode, systemEvent.getClassExternalReferenceCode());
	}

	@Test
	public void testExportImportDescriptor() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		Assert.assertNotNull(exportImportDescriptor.getKey());
		Assert.assertNotNull(exportImportDescriptor.getLabelLanguageKey());
		Assert.assertNotNull(exportImportDescriptor.getModelClassName());
		Assert.assertEquals(portletId, exportImportDescriptor.getPortletId());
		Assert.assertNotNull(exportImportDescriptor.getScope());
	}

	@Test
	public void testExportImportDescriptorNestedFields() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		Assert.assertEquals(
			getExpectedNestedFields(),
			exportImportDescriptor.getNestedFields());
	}

	@Test
	public void testExportImportFilterByDateRange() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		long time = System.currentTimeMillis();

		Date startDate = new Date(time - (3 * Time.DAY));
		Date endDate = new Date(time - Time.DAY);

		String beforeExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(),
			new Date(time - (4 * Time.DAY)));
		String withinExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(),
			new Date(time - (2 * Time.DAY)));
		String afterExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date(time));

		_exportImport(scope, Collections.emptyMap(), startDate, endDate);

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(withinExternalReferenceCode));
		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(beforeExternalReferenceCode));
		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(afterExternalReferenceCode));
	}

	@Test
	public void testExportImportFromLastPublishDate() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());
		String otherExternalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		ChangesetCollection changesetCollection =
			_changesetCollectionLocalService.addChangesetCollection(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(), StringPool.BLANK);

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		_changesetEntryLocalService.addChangesetEntry(
			TestPropsValues.getUserId(),
			changesetCollection.getChangesetCollectionId(),
			externalReferenceCode,
			_classNameLocalService.getClassNameId(
				exportImportDescriptor.getModelClassName()),
			0);

		_exportImport(
			scope,
			HashMapBuilder.put(
				ExportImportDateUtil.RANGE,
				new String[] {ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE}
			).put(
				"changesetCollectionId",
				new String[] {
					String.valueOf(
						changesetCollection.getChangesetCollectionId())
				}
			).build(),
			null, null);

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode));
		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(otherExternalReferenceCode));
	}

	@Test
	public void testExportImportKeepCreatorData() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		User creatorUser = _addCreatorUser(scope);

		String externalReferenceCode = addEntry(
			groupId, creatorUser.getUserId(), new Date());

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID}
			).build(),
			null, null);

		Assert.assertEquals(
			_getTargetCreatorUserId(scope),
			getCreatorUserId(_getTargetGroupId(scope), externalReferenceCode));
	}

	@Test
	public void testExportImportPermissions() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		Role role = _addRole(scope);

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		_resourcePermissionLocalService.setResourcePermissions(
			TestPropsValues.getCompanyId(),
			exportImportDescriptor.getModelClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(getPrimaryKey(groupId, externalReferenceCode)),
			role.getRoleId(), new String[] {getResourceActionId()});

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.PERMISSIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		Assert.assertTrue(
			_resourcePermissionLocalService.hasResourcePermission(
				_getTargetCompanyId(scope), getTargetModelClassName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(
					getPrimaryKey(
						_getTargetGroupId(scope), externalReferenceCode)),
				_getTargetRoleId(scope), getResourceActionId()));
	}

	@Test
	public void testExportImportReplicatesDeletions() throws Exception {
		Scope scope = _getScope();

		long groupId = _getGroupId(scope);

		String externalReferenceCode = addEntry(
			groupId, TestPropsValues.getUserId(), new Date());

		_exportImport(scope, Collections.emptyMap(), null, null);

		deleteEntry(groupId, externalReferenceCode);

		_exportImport(
			scope,
			HashMapBuilder.put(
				PortletDataHandlerKeys.DELETIONS,
				new String[] {Boolean.TRUE.toString()}
			).build(),
			null, null);

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertFalse(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode));
	}

	@Test
	public void testExportImportUpsertsExistingEntries() throws Exception {
		Scope scope = _getScope();

		String externalReferenceCode = addEntry(
			_getGroupId(scope), TestPropsValues.getUserId(), new Date());

		_exportImport(scope, Collections.emptyMap(), null, null);
		_exportImport(scope, Collections.emptyMap(), null, null);

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertEquals(
			externalReferenceCodes.toString(), 1,
			Collections.frequency(
				externalReferenceCodes, externalReferenceCode));
	}

	@Test
	public void testImportResolvesLazyReferences() throws Exception {
		Scope scope = _getScope();

		String externalReferenceCode = addEntryWithMissingReference(
			_getGroupId(scope), TestPropsValues.getUserId());

		if (externalReferenceCode == null) {
			return;
		}

		LazyReferencingTestUtil.executeWithLazyReferencingSafeCloseable(
			() -> _exportImport(scope, Collections.emptyMap(), null, null));

		List<String> externalReferenceCodes = getExternalReferenceCodes(
			_getTargetGroupId(scope));

		Assert.assertTrue(
			externalReferenceCodes.toString(),
			externalReferenceCodes.contains(externalReferenceCode));
	}

	@Override
	@Test
	public void testPrepareManifestSummary() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			super.testPrepareManifestSummary();
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	protected abstract String addEntry(
			long groupId, long userId, Date dateModified)
		throws Exception;

	protected String addEntryWithMissingReference(long groupId, long userId)
		throws Exception {

		return null;
	}

	protected abstract void deleteEntry(
			long groupId, String externalReferenceCode)
		throws Exception;

	protected List<String> getComments(
			long groupId, String externalReferenceCode)
		throws Exception {

		return TransformUtil.transform(
			_commentManager.getComments(
				getTargetModelClassName(),
				getPrimaryKey(groupId, externalReferenceCode),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			comment -> {
				if (comment.isRoot()) {
					return null;
				}

				return comment.getBody();
			});
	}

	protected abstract long getCreatorUserId(
			long groupId, String externalReferenceCode)
		throws Exception;

	@Override
	protected DataLevel getDataLevel() {
		Scope scope = _getScope();

		if (scope == Scope.COMPANY) {
			return DataLevel.PORTAL;
		}

		if (scope == Scope.DEPOT) {
			return DataLevel.DEPOT;
		}

		if (scope == Scope.SITE) {
			return DataLevel.SITE;
		}

		return DataLevel.PORTLET_INSTANCE;
	}

	protected List<String> getExpectedNestedFields() {
		return null;
	}

	protected abstract ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate();

	protected abstract List<String> getExternalReferenceCodes(long groupId)
		throws Exception;

	protected abstract long getPrimaryKey(
			long groupId, String externalReferenceCode)
		throws Exception;

	protected String getResourceActionId() {
		return ActionKeys.VIEW;
	}

	protected String getTargetModelClassName() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		return exportImportDescriptor.getModelClassName();
	}

	protected String getTargetPortletId() {
		return portletId;
	}

	protected void setUpTargetCompany(Company company, User user)
		throws Exception {
	}

	protected abstract boolean supportsComments();

	private static DepotEntry _addDepotEntry() throws Exception {
		return _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());
	}

	private void _addComment(
			long groupId, String externalReferenceCode, String body)
		throws Exception {

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		_commentManager.addComment(
			TestPropsValues.getUserId(), groupId,
			exportImportDescriptor.getModelClassName(),
			getPrimaryKey(groupId, externalReferenceCode), body,
			className -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_PUBLISH);

				return serviceContext;
			});
	}

	private User _addCreatorUser(Scope scope) throws Exception {
		_creatorUser = UserTestUtil.addUser();

		if (scope != Scope.COMPANY) {
			return _creatorUser;
		}

		_creatorUser.setExternalReferenceCode(RandomTestUtil.randomString());

		_creatorUser = _userLocalService.updateUser(_creatorUser);

		User targetCreatorUser = UserTestUtil.addUser(_targetCompany);

		targetCreatorUser.setExternalReferenceCode(
			_creatorUser.getExternalReferenceCode());

		_targetCreatorUser = _userLocalService.updateUser(targetCreatorUser);

		return _creatorUser;
	}

	private Role _addRole(Scope scope) throws Exception {
		_role = RoleTestUtil.addRole(RoleConstants.TYPE_REGULAR);

		if (scope == Scope.COMPANY) {
			_targetRole = _roleLocalService.addRole(
				null, _targetUser.getUserId(), null, 0, _role.getName(), null,
				null, RoleConstants.TYPE_REGULAR, null, null);
		}

		return _role;
	}

	private void _exportImport(
			Scope scope, Map<String, String[]> parameterMap, Date startDate,
			Date endDate)
		throws Exception {

		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		parameterMap = HashMapBuilder.put(
			ExportImportDateUtil.RANGE,
			new String[] {ExportImportDateUtil.RANGE_ALL}
		).put(
			PortletDataHandlerControl.getNamespacedName(
				portletId, exportImportDescriptor.getKey()),
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				portletId,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).putAll(
			parameterMap
		).build();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			User user = TestPropsValues.getUser();

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			Layout layout = _getLayout(scope);

			Map<String, Serializable> settingsMap =
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildExportPortletSettingsMap(
						user, layout.getPlid(), layout.getGroupId(), portletId,
						parameterMap, StringPool.BLANK);

			if ((endDate != null) && (startDate != null)) {
				settingsMap.put("endDate", endDate);
				settingsMap.put("startDate", startDate);
			}

			ExportImportConfiguration exportImportConfiguration =
				ExportImportConfigurationLocalServiceUtil.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.
							TYPE_PUBLISH_PORTLET_LOCAL,
						settingsMap);

			File larFile = ExportImportLocalServiceUtil.exportPortletInfoAsFile(
				exportImportConfiguration);

			try {
				User targetUser = _getTargetUser(scope);

				PermissionThreadLocal.setPermissionChecker(
					PermissionCheckerFactoryUtil.create(targetUser));

				Layout targetLayout = _getTargetLayout(scope);

				exportImportConfiguration =
					ExportImportConfigurationLocalServiceUtil.
						updateExportImportConfiguration(
							targetUser.getUserId(),
							exportImportConfiguration.
								getExportImportConfigurationId(),
							StringPool.BLANK, StringPool.BLANK,
							ExportImportConfigurationSettingsMapFactoryUtil.
								buildImportPortletSettingsMap(
									targetUser, targetLayout.getPlid(),
									targetLayout.getGroupId(),
									getTargetPortletId(), parameterMap),
							new ServiceContext());

				exportImportConfiguration.setGroupId(targetLayout.getGroupId());

				exportImportConfiguration =
					ExportImportConfigurationLocalServiceUtil.
						updateExportImportConfiguration(
							exportImportConfiguration);

				ExportImportLocalServiceUtil.importPortletDataDeletions(
					exportImportConfiguration, larFile);

				ExportImportLocalServiceUtil.importPortletInfo(
					exportImportConfiguration, larFile);
			}
			finally {
				FileUtil.delete(larFile);
			}
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
	}

	private ExportImportDescriptor<?> _getExportImportDescriptor() {
		ExportImportVulcanBatchEngineTaskItemDelegate<?>
			exportImportVulcanBatchEngineTaskItemDelegate =
				getExportImportVulcanBatchEngineTaskItemDelegate();

		ExportImportDescriptor<?> exportImportDescriptor =
			exportImportVulcanBatchEngineTaskItemDelegate.
				getExportImportDescriptor();

		Assert.assertNotNull(exportImportDescriptor);

		return exportImportDescriptor;
	}

	private long _getGroupId(Scope scope) {
		Layout layout = _getLayout(scope);

		return layout.getGroupId();
	}

	private Layout _getLayout(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _companyLayout;
		}

		if (scope == Scope.DEPOT) {
			return _depotLayout;
		}

		return _siteLayout;
	}

	private Scope _getScope() {
		ExportImportDescriptor<?> exportImportDescriptor =
			_getExportImportDescriptor();

		return exportImportDescriptor.getScope();
	}

	private long _getTargetCompanyId(Scope scope) {
		Layout targetLayout = _getTargetLayout(scope);

		return targetLayout.getCompanyId();
	}

	private long _getTargetCreatorUserId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _targetCreatorUser.getUserId();
		}

		return _creatorUser.getUserId();
	}

	private long _getTargetGroupId(Scope scope) {
		Layout targetLayout = _getTargetLayout(scope);

		return targetLayout.getGroupId();
	}

	private Layout _getTargetLayout(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _companyTargetLayout;
		}

		if (scope == Scope.DEPOT) {
			return _depotTargetLayout;
		}

		return _siteTargetLayout;
	}

	private long _getTargetRoleId(Scope scope) {
		if (scope == Scope.COMPANY) {
			return _targetRole.getRoleId();
		}

		return _role.getRoleId();
	}

	private User _getTargetUser(Scope scope) throws Exception {
		if (scope == Scope.COMPANY) {
			return _targetUser;
		}

		return TestPropsValues.getUser();
	}

	private static Layout _companyLayout;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static Layout _companyTargetLayout;
	private static DepotEntry _depotEntry;

	@Inject
	private static DepotEntryLocalService _depotEntryLocalService;

	private static Layout _depotLayout;
	private static Layout _depotTargetLayout;
	private static Group _group;

	@Inject
	private static GroupLocalService _groupLocalService;

	@Inject
	private static LayoutLocalService _layoutLocalService;

	private static Layout _siteLayout;
	private static Layout _siteTargetLayout;
	private static Company _targetCompany;
	private static DepotEntry _targetDepotEntry;
	private static Group _targetGroup;
	private static User _targetUser;

	@Inject
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Inject
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CommentManager _commentManager;

	@DeleteAfterTestRun
	private User _creatorUser;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@DeleteAfterTestRun
	private Role _role;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private SystemEventLocalService _systemEventLocalService;

	private User _targetCreatorUser;
	private Role _targetRole;

	@Inject
	private UserLocalService _userLocalService;

}