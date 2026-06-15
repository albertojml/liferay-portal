/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {PreviewPortletDataHandlerControl} from './PreviewPortletDataHandlerControl';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class PreviewPortletDataHandlerChoice extends PreviewPortletDataHandlerControl {
			"choices"?: Array<object>;
			"defaultChoice"?: string;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "choices",
			name: "choices",
			type: "Array<object>",
		},
		{
			baseName: "defaultChoice",
			name: "defaultChoice",
			type: "string",
		},
		];

		static getAttributeTypeMap() {
				return super.getAttributeTypeMap().concat(PreviewPortletDataHandlerChoice.attributeTypeMap);
		}
	}
