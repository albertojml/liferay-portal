/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */


/**
 * @author Petteri Karttunen
 * @generated
 */

	export class Type {
			"code"?: number;
			"label"?: string;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "code",
			name: "code",
			type: "number",
		},
		{
			baseName: "label",
			name: "label",
			type: "string",
		},
		];

		static getAttributeTypeMap() {
				return Type.attributeTypeMap;
		}
	}
