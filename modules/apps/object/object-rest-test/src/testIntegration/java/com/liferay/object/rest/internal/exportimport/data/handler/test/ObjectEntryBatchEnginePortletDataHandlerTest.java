/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.test.util.exportimport.data.handler.BaseBatchEnginePortletDataHandlerTestCase;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.definition.setting.builder.ObjectDefinitionSettingBuilder;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Alberto Javier Moreno Lage
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-43996"))
@RunWith(Arquillian.class)
public class ObjectEntryBatchEnginePortletDataHandlerTest
	extends BaseBatchEnginePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		List<ObjectDefinitionSetting> objectDefinitionSettings =
			Collections.emptyList();

		String scope = _objectDefinitionScope;

		if (StringUtil.equals(scope, ObjectDefinitionConstants.SCOPE_DEPOT)) {
			objectDefinitionSettings = Collections.singletonList(
				new ObjectDefinitionSettingBuilder(
				).name(
					ObjectDefinitionSettingConstants.NAME_ACCEPT_ALL_GROUPS
				).value(
					StringPool.TRUE
				).build());
		}

		_objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				null, TestPropsValues.getUserId(), 0, null, true, true, false,
				false, true, false, false, false, false, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				ObjectDefinitionTestUtil.getRandomName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectDefinitionSettings, Collections.emptyList(),
				Collections.emptyList(), new ServiceContext());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				TestPropsValues.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("able")
			).name(
				"able"
			).objectDefinitionId(
				_objectDefinition.getObjectDefinitionId()
			).required(
				false
			).build());

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_objectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		_objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		if (_targetObjectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_targetObjectDefinition);

			_targetObjectDefinition = null;
		}
	}

	@Rule(order = Integer.MIN_VALUE)
	public final TestRule objectDefinitionScopesTestRule =
		(statement, description) -> new Statement() {

			@Override
			public void evaluate() throws Throwable {
				for (String objectDefinitionScope :
						new String[] {
							ObjectDefinitionConstants.SCOPE_COMPANY,
							ObjectDefinitionConstants.SCOPE_DEPOT,
							ObjectDefinitionConstants.SCOPE_SITE
						}) {

					_objectDefinitionScope = objectDefinitionScope;

					try {
						statement.evaluate();
					}
					catch (Throwable throwable) {
						throw new AssertionError(
							StringBundler.concat(
								"Scope \"", objectDefinitionScope, "\": ",
								throwable.getMessage()),
							throwable);
					}
				}
			}

		};

	@Override
	protected String addEntry(long groupId, long userId, Date dateModified)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		long objectEntryGroupId = _getObjectEntryGroupId(groupId);

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			objectEntryGroupId, userId,
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			null,
			Collections.singletonMap("able", RandomTestUtil.randomString()),
			ServiceContextTestUtil.getServiceContext(
				objectDefinition.getCompanyId(), objectEntryGroupId, userId));

		objectEntry.setModifiedDate(dateModified);

		objectEntry = _objectEntryLocalService.updateObjectEntry(objectEntry);

		return objectEntry.getExternalReferenceCode();
	}

	@Override
	protected void addStagedModels() throws Exception {
	}

	@Override
	protected void deleteEntry(long groupId, String externalReferenceCode)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(
			_getObjectEntry(groupId, externalReferenceCode));
	}

	@Override
	protected long getCreatorUserId(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		return objectEntry.getUserId();
	}

	@Override
	protected List<String> getExpectedNestedFields() {
		return Collections.singletonList("rootModelHierarchy");
	}

	@Override
	protected ExportImportVulcanBatchEngineTaskItemDelegate<?>
		getExportImportVulcanBatchEngineTaskItemDelegate() {

		try {
			Bundle bundle = FrameworkUtil.getBundle(getClass());

			BundleContext bundleContext = bundle.getBundleContext();

			Collection<ServiceReference<ObjectEntryResource>>
				serviceReferences = bundleContext.getServiceReferences(
					ObjectEntryResource.class,
					StringBundler.concat(
						"(&(batch.engine.task.item.delegate.name=",
						_objectDefinition.getName(), ")(companyId=",
						_objectDefinition.getCompanyId(), "))"));

			Iterator<ServiceReference<ObjectEntryResource>> iterator =
				serviceReferences.iterator();

			return (ExportImportVulcanBatchEngineTaskItemDelegate<?>)
				bundleContext.getService(iterator.next());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected List<String> getExternalReferenceCodes(long groupId)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		return TransformUtil.transform(
			_objectEntryLocalService.getObjectEntries(
				_getObjectEntryGroupId(groupId),
				objectDefinition.getObjectDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS),
			ObjectEntry::getExternalReferenceCode);
	}

	@Override
	protected String getPortletId() {
		return _objectDefinition.getPortletId();
	}

	@Override
	protected long getPrimaryKey(long groupId, String externalReferenceCode)
		throws Exception {

		ObjectEntry objectEntry = _getObjectEntry(
			groupId, externalReferenceCode);

		return objectEntry.getObjectEntryId();
	}

	@Override
	protected String getTargetModelClassName() {
		if (_targetObjectDefinition == null) {
			return super.getTargetModelClassName();
		}

		return _targetObjectDefinition.getClassName();
	}

	@Override
	protected String getTargetPortletId() {
		if (_targetObjectDefinition == null) {
			return super.getTargetPortletId();
		}

		return _targetObjectDefinition.getPortletId();
	}

	@Override
	protected void setUpTargetCompany(Company company, User user)
		throws Exception {

		_targetObjectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				_objectDefinition.getExternalReferenceCode(), user.getUserId(),
				0, null, true, true, false, false, true, false, false, false,
				false, StringUtil.toLowerCase(RandomTestUtil.randomString()),
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				_objectDefinition.getShortName(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, ObjectDefinitionConstants.SCOPE_COMPANY,
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList(), new ServiceContext());

		ObjectField objectField = ObjectFieldUtil.addCustomObjectField(
			new TextObjectFieldBuilder(
			).userId(
				user.getUserId()
			).labelMap(
				LocalizedMapUtil.getLocalizedMap("able")
			).name(
				"able"
			).objectDefinitionId(
				_targetObjectDefinition.getObjectDefinitionId()
			).required(
				false
			).build());

		_objectDefinitionLocalService.updateTitleObjectFieldId(
			_targetObjectDefinition.getObjectDefinitionId(),
			objectField.getObjectFieldId());

		_targetObjectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user.getUserId(),
				_targetObjectDefinition.getObjectDefinitionId());
	}

	@Override
	protected boolean supportsComments() {
		return true;
	}

	private ObjectDefinition _getObjectDefinition(long groupId)
		throws Exception {

		if (_targetObjectDefinition == null) {
			return _objectDefinition;
		}

		Group group = _groupLocalService.getGroup(groupId);

		if (group.getCompanyId() == _targetObjectDefinition.getCompanyId()) {
			return _targetObjectDefinition;
		}

		return _objectDefinition;
	}

	private ObjectEntry _getObjectEntry(
			long groupId, String externalReferenceCode)
		throws Exception {

		ObjectDefinition objectDefinition = _getObjectDefinition(groupId);

		return _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, _getObjectEntryGroupId(groupId),
			objectDefinition.getObjectDefinitionId());
	}

	private long _getObjectEntryGroupId(long groupId) {
		if (StringUtil.equals(
				_objectDefinitionScope,
				ObjectDefinitionConstants.SCOPE_COMPANY)) {

			return 0;
		}

		return groupId;
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private String _objectDefinitionScope;

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

	private ObjectDefinition _targetObjectDefinition;

}