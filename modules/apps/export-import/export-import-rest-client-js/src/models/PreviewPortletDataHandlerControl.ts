/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
 * @author Petteri Karttunen
 * @generated
 */

	export class PreviewPortletDataHandlerControl {
			"disabled"?: boolean;
			"label"?: string;
			"name"?: string;
			"type"?: 'Boolean' | 'Choice' | 'Setting';

		static "discriminator": string | undefined = "type";

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "disabled",
			name: "disabled",
			type: "boolean",
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
			baseName: "type",
			name: "type",
			type: "'Boolean' | 'Choice' | 'Setting'",
		},
		];

		static getAttributeTypeMap() {
				return PreviewPortletDataHandlerControl.attributeTypeMap;
		}
	}
