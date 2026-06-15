/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
 * @author Petteri Karttunen
 * @generated
 */

	export class RequestPortletDataHandlerControl {
			"name"?: string;
			"requestPortletDataHandlerControls"?: Array<RequestPortletDataHandlerControl>;
			"values"?: Array<string>;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "name",
			name: "name",
			type: "string",
		},
		{
			baseName: "requestPortletDataHandlerControls",
			name: "requestPortletDataHandlerControls",
			type: "Array<RequestPortletDataHandlerControl>",
		},
		{
			baseName: "values",
			name: "values",
			type: "Array<string>",
		},
		];

		static getAttributeTypeMap() {
				return RequestPortletDataHandlerControl.attributeTypeMap;
		}
	}
