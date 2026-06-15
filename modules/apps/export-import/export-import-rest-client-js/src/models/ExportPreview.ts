/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

			import {PreviewPortletDataHandlerSection} from './PreviewPortletDataHandlerSection';

/**
 * @author Petteri Karttunen
 * @generated
 */

	export class ExportPreview {
			"additionCount"?: number;
			"deletionCount"?: number;
			"previewPortletDataHandlerSections"?: Array<PreviewPortletDataHandlerSection>;

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
			baseName: "previewPortletDataHandlerSections",
			name: "previewPortletDataHandlerSections",
			type: "Array<PreviewPortletDataHandlerSection>",
		},
		];

		static getAttributeTypeMap() {
				return ExportPreview.attributeTypeMap;
		}
	}
