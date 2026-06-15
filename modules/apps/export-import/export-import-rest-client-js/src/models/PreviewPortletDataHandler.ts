/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {PreviewPortletDataHandlerControl} from './PreviewPortletDataHandlerControl';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class PreviewPortletDataHandler {
			"additionCount"?: number;
			"deletionCount"?: number;
			"description"?: string;
			"label"?: string;
			"name"?: string;
			"previewPortletDataHandlerControls"?: Array<PreviewPortletDataHandlerControl>;
			"tag"?: string;

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
			baseName: "description",
			name: "description",
			type: "string",
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
			baseName: "previewPortletDataHandlerControls",
			name: "previewPortletDataHandlerControls",
			type: "Array<PreviewPortletDataHandlerControl>",
		},
		{
			baseName: "tag",
			name: "tag",
			type: "string",
		},
		];

		static getAttributeTypeMap() {
				return PreviewPortletDataHandler.attributeTypeMap;
		}
	}
