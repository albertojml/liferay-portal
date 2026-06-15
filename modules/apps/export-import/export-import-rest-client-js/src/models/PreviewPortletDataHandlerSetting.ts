/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {PreviewPortletDataHandlerControl} from './PreviewPortletDataHandlerControl';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class PreviewPortletDataHandlerSetting extends PreviewPortletDataHandlerControl {
			"defaultState"?: boolean;
			"previewPortletDataHandlerControls"?: Array<PreviewPortletDataHandlerControl>;

		static "discriminator": string | undefined = undefined;

	static "attributeTypeMap": Array<{
		baseName: string;
		name: string;
		type: string;
	}> = [
		{
			baseName: "defaultState",
			name: "defaultState",
			type: "boolean",
		},
		{
			baseName: "previewPortletDataHandlerControls",
			name: "previewPortletDataHandlerControls",
			type: "Array<PreviewPortletDataHandlerControl>",
		},
		];

		static getAttributeTypeMap() {
				return super.getAttributeTypeMap().concat(PreviewPortletDataHandlerSetting.attributeTypeMap);
		}
	}
