/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.model.listener;

import com.liferay.mcp.server.rest.internal.constants.MCPServerConstants;
import com.liferay.mcp.server.rest.internal.servlet.MCPServerServlet;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;

import jakarta.servlet.Servlet;

import jakarta.validation.ValidationException;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class MCPServerRestrictedFieldObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return MCPServerConstants.
			EXTERNAL_REFERENCE_CODE_MCP_SERVER_RESTRICTED_FIELD;
	}

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_deleteCoveredMCPServerRestrictedFieldObjectEntries(objectEntry);

		_invalidateServlet(objectEntry);
	}

	@Override
	public void onAfterRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_invalidateServlet(objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_isFieldNameModified(originalObjectEntry, objectEntry)) {
			_deleteCoveredMCPServerRestrictedFieldObjectEntries(objectEntry);
		}

		_invalidateServlet(objectEntry);
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_validateFieldName(objectEntry);
	}

	@Override
	public void onBeforeRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		if (PortalInstances.isCurrentCompanyInDeletionProcess()) {
			return;
		}

		if (Validator.isNull(
				MapUtil.getString(objectEntry.getValues(), "deleteReason"))) {

			throw new ModelListenerException(
				new ValidationException(
					"Unable to remove a restricted field without a delete " +
						"reason"));
		}
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		if (_isFieldNameModified(originalObjectEntry, objectEntry)) {
			_validateFieldName(objectEntry);
		}
	}

	private void _deleteCoveredMCPServerRestrictedFieldObjectEntries(
		ObjectEntry mcpServerRestrictedFieldObjectEntry) {

		ObjectEntry mcpServerProfileToolObjectEntry =
			_fetchMCPServerProfileToolObjectEntry(
				mcpServerRestrictedFieldObjectEntry);

		if (mcpServerProfileToolObjectEntry == null) {
			return;
		}

		String fieldName = MapUtil.getString(
			mcpServerRestrictedFieldObjectEntry.getValues(), "fieldName");

		for (ObjectEntry objectEntry :
				_getMCPServerRestrictedFieldObjectEntries(
					mcpServerProfileToolObjectEntry)) {

			String coveredFieldName = MapUtil.getString(
				objectEntry.getValues(), "fieldName");

			if (coveredFieldName.startsWith(fieldName + StringPool.PERIOD)) {
				_deleteMCPServerRestrictedFieldObjectEntry(
					fieldName, objectEntry);
			}
		}
	}

	private void _deleteMCPServerRestrictedFieldObjectEntry(
		String coveringFieldName,
		ObjectEntry mcpServerRestrictedFieldObjectEntry) {

		try {
			Map<String, Serializable> values =
				HashMapBuilder.<String, Serializable>putAll(
					mcpServerRestrictedFieldObjectEntry.getValues()
				).put(
					"deleteReason",
					"Restricted field \"" + coveringFieldName + "\" was added."
				).build();

			_objectEntryLocalService.updateObjectEntry(
				mcpServerRestrictedFieldObjectEntry.getUserId(),
				mcpServerRestrictedFieldObjectEntry.getObjectEntryId(),
				mcpServerRestrictedFieldObjectEntry.getObjectEntryFolderId(),
				values, new ServiceContext());

			mcpServerRestrictedFieldObjectEntry.setValues(values);

			_objectEntryLocalService.deleteObjectEntry(
				mcpServerRestrictedFieldObjectEntry);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to delete object entry ",
						mcpServerRestrictedFieldObjectEntry.
							getExternalReferenceCode(),
						" covered by restricted field \"", coveringFieldName,
						"\""),
					portalException);
			}
		}
	}

	private ObjectEntry _fetchMCPServerProfileToolObjectEntry(
		ObjectEntry mcpServerRestrictedFieldObjectEntry) {

		Map<String, Serializable> values =
			mcpServerRestrictedFieldObjectEntry.getValues();

		long mcpServerProfileToolObjectEntryId = MapUtil.getLong(
			values,
			"r_mcpServerToolToRestrictedFields_l_mcpServerProfileToolId");

		if (mcpServerProfileToolObjectEntryId != 0) {
			return _objectEntryLocalService.fetchObjectEntry(
				mcpServerProfileToolObjectEntryId);
		}

		return _fetchObjectEntry(
			mcpServerRestrictedFieldObjectEntry.getCompanyId(),
			MapUtil.getString(
				values,
				"r_mcpServerToolToRestrictedFields_l_mcpServerProfileToolERC"),
			MCPServerConstants.EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE_TOOL);
	}

	private ObjectEntry _fetchObjectEntry(
		long companyId, String externalReferenceCode,
		String objectDefinitionExternalReferenceCode) {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);

		if (objectDefinition == null) {
			return null;
		}

		return _objectEntryLocalService.fetchObjectEntry(
			externalReferenceCode, 0, objectDefinition.getObjectDefinitionId());
	}

	private List<ObjectEntry> _getMCPServerRestrictedFieldObjectEntries(
		ObjectEntry mcpServerProfileToolObjectEntry) {

		try {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					mcpServerProfileToolObjectEntry.getObjectDefinitionId(),
					"mcpServerToolToRestrictedFields");

			return _objectEntryLocalService.getOneToManyObjectEntries(
				0, objectRelationship.getObjectRelationshipId(), null, false,
				mcpServerProfileToolObjectEntry.getObjectEntryId(), true, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private void _invalidateServlet(
		ObjectEntry mcpServerRestrictedFieldObjectEntry) {

		ObjectEntry mcpServerProfileToolObjectEntry =
			_fetchMCPServerProfileToolObjectEntry(
				mcpServerRestrictedFieldObjectEntry);

		if (mcpServerProfileToolObjectEntry == null) {
			return;
		}

		long companyId = mcpServerRestrictedFieldObjectEntry.getCompanyId();

		ObjectEntry mcpServerProfileObjectEntry = _fetchObjectEntry(
			companyId,
			MapUtil.getString(
				mcpServerProfileToolObjectEntry.getValues(),
				"r_mcpServerProfileToTools_l_mcpServerProfileERC"),
			MCPServerConstants.EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE);

		if (mcpServerProfileObjectEntry == null) {
			return;
		}

		MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

		mcpServerServlet.invalidate(
			companyId,
			MapUtil.getString(mcpServerProfileObjectEntry.getValues(), "name"));
	}

	private boolean _isFieldNameModified(
		ObjectEntry originalObjectEntry, ObjectEntry objectEntry) {

		return !Objects.equals(
			MapUtil.getString(originalObjectEntry.getValues(), "fieldName"),
			MapUtil.getString(objectEntry.getValues(), "fieldName"));
	}

	private void _validateFieldName(
			ObjectEntry mcpServerRestrictedFieldObjectEntry)
		throws ModelListenerException {

		String fieldName = MapUtil.getString(
			mcpServerRestrictedFieldObjectEntry.getValues(), "fieldName");

		if (fieldName.contains(StringPool.COMMA)) {
			throw new ModelListenerException(
				new ValidationException(
					"Unable to restrict more than one field at a time"));
		}

		ObjectEntry mcpServerProfileToolObjectEntry =
			_fetchMCPServerProfileToolObjectEntry(
				mcpServerRestrictedFieldObjectEntry);

		if (mcpServerProfileToolObjectEntry == null) {
			return;
		}

		for (ObjectEntry objectEntry :
				_getMCPServerRestrictedFieldObjectEntries(
					mcpServerProfileToolObjectEntry)) {

			if (objectEntry.getObjectEntryId() ==
					mcpServerRestrictedFieldObjectEntry.getObjectEntryId()) {

				continue;
			}

			String coveringFieldName = MapUtil.getString(
				objectEntry.getValues(), "fieldName");

			if (fieldName.startsWith(coveringFieldName + StringPool.PERIOD)) {
				throw new ModelListenerException(
					new ValidationException(
						StringBundler.concat(
							"Unable to restrict field \"", fieldName,
							"\" because restricted field \"", coveringFieldName,
							"\" already covers it")));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MCPServerRestrictedFieldObjectEntryModelListener.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference(
		target = "(osgi.http.whiteboard.servlet.name=com.liferay.mcp.server.rest.internal.servlet.MCPServerServlet)"
	)
	private Servlet _servlet;

}