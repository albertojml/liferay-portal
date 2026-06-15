/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {RequestPortletDataHandler} from './RequestPortletDataHandler';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class ImportProcessRequest {
			"comments"?: boolean;
			"dataStrategy"?: 'MIRROR' | 'MIRROR_OVERWRITE' | 'COPY_AS_NEW';
			"deletions"?: boolean;
			"logo"?: boolean;
			"name"?: string;
			"permissions"?: boolean;
			"ratings"?: boolean;
			"requestPortletDataHandlers"?: Array<RequestPortletDataHandler>;
			"sitePagesSettings"?: boolean;
			"siteTemplateSettings"?: boolean;
			"themeSettings"?: boolean;
			"userIdStrategy"?: 'CURRENT_USER_ID' | 'ALWAYS_CURRENT_USER_ID';

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "comments",
			name: "comments",
			type: "boolean",
		},
		{
			baseName: "dataStrategy",
			name: "dataStrategy",
			type: "'MIRROR' | 'MIRROR_OVERWRITE' | 'COPY_AS_NEW'",
		},
		{
			baseName: "deletions",
			name: "deletions",
			type: "boolean",
		},
		{
			baseName: "logo",
			name: "logo",
			type: "boolean",
		},
		{
			baseName: "name",
			name: "name",
			type: "string",
		},
		{
			baseName: "permissions",
			name: "permissions",
			type: "boolean",
		},
		{
			baseName: "ratings",
			name: "ratings",
			type: "boolean",
		},
		{
			baseName: "requestPortletDataHandlers",
			name: "requestPortletDataHandlers",
			type: "Array<RequestPortletDataHandler>",
		},
		{
			baseName: "sitePagesSettings",
			name: "sitePagesSettings",
			type: "boolean",
		},
		{
			baseName: "siteTemplateSettings",
			name: "siteTemplateSettings",
			type: "boolean",
		},
		{
			baseName: "themeSettings",
			name: "themeSettings",
			type: "boolean",
		},
		{
			baseName: "userIdStrategy",
			name: "userIdStrategy",
			type: "'CURRENT_USER_ID' | 'ALWAYS_CURRENT_USER_ID'",
		},
		];

		static getAttributeTypeMap() {
				return ImportProcessRequest.attributeTypeMap;
		}
	}
