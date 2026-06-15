/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {Creator} from './Creator';
			import {Status} from './Status';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class ImportProcess {
			"creator"?: Creator;
			"dateCreated"?: Date;
			"dateModified"?: Date;
			"id"?: number;
			"name"?: string;
			"status"?: Status;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
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
			baseName: "id",
			name: "id",
			type: "number",
		},
		{
			baseName: "name",
			name: "name",
			type: "string",
		},
		{
			baseName: "status",
			name: "status",
			type: "Status",
		},
		];

		static getAttributeTypeMap() {
				return ImportProcess.attributeTypeMap;
		}
	}
