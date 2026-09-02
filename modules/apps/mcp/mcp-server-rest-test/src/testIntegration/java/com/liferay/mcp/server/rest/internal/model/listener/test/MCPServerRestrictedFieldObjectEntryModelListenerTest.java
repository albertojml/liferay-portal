/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.mcp.server.rest.test.util.MCPServerTestUtil;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalInstances;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Javier Moreno Lage
 */
@FeatureFlags(featureFlags = @FeatureFlag("LPD-63311"))
@RunWith(Arquillian.class)
public class MCPServerRestrictedFieldObjectEntryModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		MCPServerTestUtil.processBatchEngineUnits();
	}

	@Test
	public void testOnAfterCreate() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry creatorExternalReferenceCodeObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.externalReferenceCode",
				mcpServerProfileToolObjectEntry);
		ObjectEntry creatorIdObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id", mcpServerProfileToolObjectEntry);

		// Fields outside of the restricted subtree

		ObjectEntry creatorNameObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creatorName", mcpServerProfileToolObjectEntry);
		ObjectEntry descriptionObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"description", mcpServerProfileToolObjectEntry);

		// Fields of another profile tool

		ObjectEntry otherMCPServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPageExport", "mcp-server-profiles");

		ObjectEntry otherCreatorIdObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id", otherMCPServerProfileToolObjectEntry);

		MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
			"creator", mcpServerProfileToolObjectEntry);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				creatorExternalReferenceCodeObjectEntry.getObjectEntryId()));
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				creatorIdObjectEntry.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				creatorNameObjectEntry.getObjectEntryId()));
		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				descriptionObjectEntry.getObjectEntryId()));
		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				otherCreatorIdObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testOnAfterCreateWhenDescendantsAreNested() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry actionsDeleteHrefObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.delete.href", mcpServerProfileToolObjectEntry);

		ObjectEntry actionsDeleteMethodObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.delete.method", mcpServerProfileToolObjectEntry);

		ObjectEntry actionsGetMethodObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.get.method", mcpServerProfileToolObjectEntry);

		// Fields outside of the restricted subtree

		ObjectEntry actionsCountObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actionsCount", mcpServerProfileToolObjectEntry);

		// Intermediate node

		ObjectEntry actionsDeleteObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.delete", mcpServerProfileToolObjectEntry);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsDeleteHrefObjectEntry.getObjectEntryId()));
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsDeleteMethodObjectEntry.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsCountObjectEntry.getObjectEntryId()));
		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsGetMethodObjectEntry.getObjectEntryId()));

		// Root node

		MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
			"actions", mcpServerProfileToolObjectEntry);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsDeleteObjectEntry.getObjectEntryId()));
		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsGetMethodObjectEntry.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				actionsCountObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testOnAfterUpdate() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry creatorIdObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id", mcpServerProfileToolObjectEntry);

		ObjectEntry descriptionObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"description", mcpServerProfileToolObjectEntry);

		_objectEntryLocalService.updateObjectEntry(
			TestPropsValues.getUserId(),
			descriptionObjectEntry.getObjectEntryId(), 0,
			HashMapBuilder.<String, Serializable>putAll(
				descriptionObjectEntry.getValues()
			).put(
				"fieldName", "creator"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				creatorIdObjectEntry.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				descriptionObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testOnBeforeCreate() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		// Several fields at a time

		AssertUtils.assertFailure(
			ModelListenerException.class,
			"jakarta.validation.ValidationException: Unable to restrict more " +
				"than one field at a time",
			() -> MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id,creator.externalReferenceCode",
				mcpServerProfileToolObjectEntry));

		// One field at a time

		Assert.assertNotNull(
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.externalReferenceCode",
				mcpServerProfileToolObjectEntry));
		Assert.assertNotNull(
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id", mcpServerProfileToolObjectEntry));
	}

	@Test
	public void testOnBeforeCreateWhenAncestorIsRestricted() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
			"actions", mcpServerProfileToolObjectEntry);

		// Every level below the restricted ancestor

		AssertUtils.assertFailure(
			ModelListenerException.class,
			"jakarta.validation.ValidationException: Unable to restrict " +
				"field \"actions.get\" because restricted field \"actions\" " +
					"already hides it",
			() -> MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.get", mcpServerProfileToolObjectEntry));

		AssertUtils.assertFailure(
			ModelListenerException.class,
			"jakarta.validation.ValidationException: Unable to restrict " +
				"field \"actions.get.method\" because restricted field " +
					"\"actions\" already hides it",
			() -> MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actions.get.method", mcpServerProfileToolObjectEntry));

		// Fields outside of the restricted subtree

		Assert.assertNotNull(
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"actionsCount", mcpServerProfileToolObjectEntry));
		Assert.assertNotNull(
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"description", mcpServerProfileToolObjectEntry));
	}

	@Test
	public void testOnBeforeRemove() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry mcpServerRestrictedFieldObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"description", mcpServerProfileToolObjectEntry);

		AssertUtils.assertFailure(
			ModelListenerException.class,
			"jakarta.validation.ValidationException: Unable to remove a " +
				"restricted field without a delete reason",
			() -> _objectEntryLocalService.deleteObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId()));

		Assert.assertNotNull(
			_objectEntryLocalService.fetchObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId()));

		MCPServerTestUtil.deleteMCPServerRestrictedFieldObjectEntry(
			RandomTestUtil.randomString(), mcpServerRestrictedFieldObjectEntry);

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testOnBeforeRemoveWhenCompanyInDeletionProcess()
		throws Exception {

		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry mcpServerRestrictedFieldObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"description", mcpServerProfileToolObjectEntry);

		try (SafeCloseable safeCloseable =
				PortalInstances.setCompanyInDeletionProcessWithSafeCloseable(
					TestPropsValues.getCompanyId())) {

			_objectEntryLocalService.deleteObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId());
		}

		Assert.assertNull(
			_objectEntryLocalService.fetchObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId()));
	}

	@Test
	public void testOnBeforeUpdate() throws Exception {
		ObjectEntry mcpServerProfileObjectEntry =
			MCPServerTestUtil.addMCPServerProfileObjectEntry(
				RandomTestUtil.randomString(), RandomTestUtil.randomString());

		ObjectEntry mcpServerProfileToolObjectEntry =
			MCPServerTestUtil.addMCPServerProfileToolObjectEntry(
				mcpServerProfileObjectEntry.getExternalReferenceCode(),
				"getMCPServerProfilesPage", "mcp-server-profiles");

		ObjectEntry mcpServerRestrictedFieldObjectEntry =
			MCPServerTestUtil.addMCPServerRestrictedFieldObjectEntry(
				"creator.id", mcpServerProfileToolObjectEntry);

		AssertUtils.assertFailure(
			ModelListenerException.class,
			"jakarta.validation.ValidationException: Unable to restrict more " +
				"than one field at a time",
			() -> _objectEntryLocalService.updateObjectEntry(
				TestPropsValues.getUserId(),
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId(), 0,
				HashMapBuilder.<String, Serializable>putAll(
					mcpServerRestrictedFieldObjectEntry.getValues()
				).put(
					"fieldName", "creator.id,creator.externalReferenceCode"
				).build(),
				ServiceContextTestUtil.getServiceContext()));
	}

	@Inject
	private ObjectEntryLocalService _objectEntryLocalService;

}