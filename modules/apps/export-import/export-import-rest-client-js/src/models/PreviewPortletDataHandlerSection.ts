/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {PreviewPortletDataHandler} from './PreviewPortletDataHandler';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class PreviewPortletDataHandlerSection {
			"additionCount"?: number;
			"deletionCount"?: number;
			"label"?: string;
			"name"?: string;
			"previewPortletDataHandlers"?: Array<PreviewPortletDataHandler>;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "additionCount",
			name: "additionCount",
			type: "number",
		},
		{
			baseName: "deletionCount",
			name: "deletionCount",
			type: "number",
		},
		{
			baseName: "label",
			name: "label",
			type: "string",
		},
		{
			baseName: "name",
			name: "name",
			type: "string",
		},
		{
			baseName: "previewPortletDataHandlers",
			name: "previewPortletDataHandlers",
			type: "Array<PreviewPortletDataHandler>",
		},
		];

		static getAttributeTypeMap() {
				return PreviewPortletDataHandlerSection.attributeTypeMap;
		}
	}
