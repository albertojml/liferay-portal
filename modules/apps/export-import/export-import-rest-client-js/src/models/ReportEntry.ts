/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {Creator} from './Creator';
			import {Origin} from './Origin';
			import {Status} from './Status';
			import {Type} from './Type';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class ReportEntry {
			"classExternalReferenceCode"?: string;
			"classNameId"?: number;
			"classPK"?: number;
			"configurationId"?: number;
			"creator"?: Creator;
			"dateCreated"?: Date;
			"dateModified"?: Date;
			"errorMessage"?: string;
			"errorStacktrace"?: string;
			"id"?: number;
			"modelName"?: string;
			"origin"?: Origin;
			"scope"?: any;
			"status"?: Status;
			"type"?: Type;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "classExternalReferenceCode",
			name: "classExternalReferenceCode",
			type: "string",
		},
		{
			baseName: "classNameId",
			name: "classNameId",
			type: "number",
		},
		{
			baseName: "classPK",
			name: "classPK",
			type: "number",
		},
		{
			baseName: "configurationId",
			name: "configurationId",
			type: "number",
		},
		{
			baseName: "creator",
			name: "creator",
			type: "Creator",
		},
		{
			baseName: "dateCreated",
			name: "dateCreated",
			type: "Date",
		},
		{
			baseName: "dateModified",
			name: "dateModified",
			type: "Date",
		},
		{
			baseName: "errorMessage",
			name: "errorMessage",
			type: "string",
		},
		{
			baseName: "errorStacktrace",
			name: "errorStacktrace",
			type: "string",
		},
		{
			baseName: "id",
			name: "id",
			type: "number",
		},
		{
			baseName: "modelName",
			name: "modelName",
			type: "string",
		},
		{
			baseName: "origin",
			name: "origin",
			type: "Origin",
		},
		{
			baseName: "scope",
			name: "scope",
			type: "any",
		},
		{
			baseName: "status",
			name: "status",
			type: "Status",
		},
		{
			baseName: "type",
			name: "type",
			type: "Type",
		},
		];

		static getAttributeTypeMap() {
				return ReportEntry.attributeTypeMap;
		}
	}
