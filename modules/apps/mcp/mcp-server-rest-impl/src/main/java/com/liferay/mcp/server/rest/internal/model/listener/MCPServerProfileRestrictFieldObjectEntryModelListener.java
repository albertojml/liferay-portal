/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.model.listener;

import com.liferay.mcp.server.rest.internal.constants.MCPServerConstants;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalInstances;

import jakarta.validation.ValidationException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class MCPServerProfileRestrictFieldObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return MCPServerConstants.
			EXTERNAL_REFERENCE_CODE_MCP_SERVER_PROFILE_RESTRICT_FIELD;
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
					"Unable to remove a profile restrict field without a " +
						"delete reason"));
		}
	}

}