/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.exportimport.rest.dto.v1_0.RequestPortletDataHandler;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.Validator;

import jakarta.ws.rs.BadRequestException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alberto Moreno
 */
public class RequestPortletDataHandlerValidatorUtil {

	public static void validate(
		ExportImportHelper exportImportHelper,
		PortletDataHandlerProvider portletDataHandlerProvider, long companyId,
		long groupId, RequestPortletDataHandler[] requestPortletDataHandlers) {

		if (requestPortletDataHandlers == null) {
			return;
		}

		Set<String> availableNames = _getAvailableNames(
			companyId, exportImportHelper, groupId, portletDataHandlerProvider);

		for (RequestPortletDataHandler requestPortletDataHandler :
				requestPortletDataHandlers) {

			String name = requestPortletDataHandler.getName();

			if (Validator.isBlank(name) || availableNames.contains(name)) {
				continue;
			}

			throw new BadRequestException(
				StringBundler.concat(
					"The portlet data handler \"", name,
					"\" is not available for the export-import scope. Valid ",
					"handler names are returned by the export preview in ",
					"previewPortletDataHandlerSections[].",
					"previewPortletDataHandlers[].name."));
		}
	}

	private static Set<String> _getAvailableNames(
		long companyId, ExportImportHelper exportImportHelper, long groupId,
		PortletDataHandlerProvider portletDataHandlerProvider) {

		Set<String> availableNames = new HashSet<>();

		List<Portlet> portlets = exportImportHelper.getExportablePortlets(
			companyId, false, groupId);

		for (Portlet portlet : portlets) {
			PortletDataHandler portletDataHandler =
				portletDataHandlerProvider.provide(portlet);

			if ((portletDataHandler == null) ||
				!portletDataHandler.isEnabled(companyId)) {

				continue;
			}

			availableNames.add(
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					portlet.getPortletId());
		}

		return availableNames;
	}

}