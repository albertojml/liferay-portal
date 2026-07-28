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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;

import jakarta.servlet.Servlet;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class MCPServerProfileToolObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return MCPServerConstants.
			EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE_TOOL;
	}

	@Override
	public void onAfterCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_invalidateServlet(objectEntry);
	}

	@Override
	public void onAfterUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_invalidateServlet(objectEntry);
	}

	@Override
	public void onBeforeRemove(ObjectEntry objectEntry)
		throws ModelListenerException {

		_invalidateServlet(objectEntry);

		_deleteMCPServerProfileRestrictFieldObjectEntries(objectEntry);
	}

	private void _deleteMCPServerProfileRestrictFieldObjectEntries(
		ObjectEntry objectEntry) {

		for (ObjectEntry mcpServerProfileRestrictFieldObjectEntry :
				_getMCPServerProfileRestrictFieldObjectEntries(objectEntry)) {

			try {
				Map<String, Serializable> newValues =
					HashMapBuilder.<String, Serializable>putAll(
						mcpServerProfileRestrictFieldObjectEntry.getValues()
					).put(
						"deleteReason", "MCP server profile tool was deleted."
					).build();

				_objectEntryLocalService.updateObjectEntry(
					mcpServerProfileRestrictFieldObjectEntry.getUserId(),
					mcpServerProfileRestrictFieldObjectEntry.getObjectEntryId(),
					mcpServerProfileRestrictFieldObjectEntry.
						getObjectEntryFolderId(),
					newValues, new ServiceContext());

				mcpServerProfileRestrictFieldObjectEntry.setValues(newValues);

				_objectEntryLocalService.deleteObjectEntry(
					mcpServerProfileRestrictFieldObjectEntry);
			}
			catch (PortalException portalException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to delete object entry ",
							mcpServerProfileRestrictFieldObjectEntry.
								getObjectEntryId(),
							" for profile tool ",
							objectEntry.getExternalReferenceCode()),
						portalException);
				}
			}
		}
	}

	private List<ObjectEntry> _getMCPServerProfileRestrictFieldObjectEntries(
		ObjectEntry objectEntry) {

		try {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectEntry.getObjectDefinitionId(),
					"mcpServerToolToRestrictFields");

			return _objectEntryLocalService.getOneToManyObjectEntries(
				0, objectRelationship.getObjectRelationshipId(), null, false,
				objectEntry.getObjectEntryId(), true, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private void _invalidateServlet(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					MCPServerConstants.
						EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE,
					objectEntry.getCompanyId());

		if (objectDefinition == null) {
			return;
		}

		ObjectEntry mcpServerProfileObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(
				MapUtil.getString(
					objectEntry.getValues(),
					"r_mcpServerProfileToTools_l_mcpServerProfileERC"),
				0, objectDefinition.getObjectDefinitionId());

		if (mcpServerProfileObjectEntry == null) {
			return;
		}

		MCPServerServlet mcpServerServlet = (MCPServerServlet)_servlet;

		mcpServerServlet.invalidate(
			objectEntry.getCompanyId(),
			MapUtil.getString(mcpServerProfileObjectEntry.getValues(), "name"));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MCPServerProfileToolObjectEntryModelListener.class);

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