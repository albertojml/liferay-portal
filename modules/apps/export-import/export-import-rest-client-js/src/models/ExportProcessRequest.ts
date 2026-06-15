/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {RequestPortletDataHandler} from './RequestPortletDataHandler';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class ExportProcessRequest {
			"comments"?: boolean;
			"deletions"?: boolean;
			"endDate"?: Date;
			"last"?: number;
			"logo"?: boolean;
			"name"?: string;
			"permissions"?: boolean;
			"range"?: 'all' | 'dateRange' | 'last';
			"ratings"?: boolean;
			"requestPortletDataHandlers"?: Array<RequestPortletDataHandler>;
			"sitePagesSettings"?: boolean;
			"siteTemplateSettings"?: boolean;
			"startDate"?: Date;
			"themeSettings"?: boolean;

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
			baseName: "deletions",
			name: "deletions",
			type: "boolean",
		},
		{
			baseName: "endDate",
			name: "endDate",
			type: "Date",
		},
		{
			baseName: "last",
			name: "last",
			type: "number",
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
			baseName: "range",
			name: "range",
			type: "'all' | 'dateRange' | 'last'",
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
			baseName: "startDate",
			name: "startDate",
			type: "Date",
		},
		{
			baseName: "themeSettings",
			name: "themeSettings",
			type: "boolean",
		},
		];

		static getAttributeTypeMap() {
				return ExportProcessRequest.attributeTypeMap;
		}
	}
