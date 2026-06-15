/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {ExportPreview} from '../models/ExportPreview';

/**
 * @author Petteri Karttunen
 * @generated
 */

export class ExportPreviewAPI {
	protected _basePath: string;
	protected _defaultHeaders: any = {};

	constructor(basePath?: string) {
		if (basePath) {
			this._basePath = basePath;
		}
	}

	set defaultHeaders(defaultHeaders: any) {
		this._defaultHeaders = defaultHeaders;
	}

		/**
		 * 
				 * @param assetLibraryExternalReferenceCode
				 * @param endDate
				 * @param last
				 * @param range
				 * @param startDate
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryExportPreview(
						assetLibraryExternalReferenceCode: string,
						endDate?: Date,
						last?: number,
						range?: 'all' | 'dateRange' | 'last',
						startDate?: Date,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportPreview;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/asset-libraries/{assetLibraryExternalReferenceCode}/export-preview"
						.replace("{assetLibraryExternalReferenceCode}",encodeURIComponent(assetLibraryExternalReferenceCode))
																				;

			const queryParameters: any = {};

						if (assetLibraryExternalReferenceCode === null || assetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter assetLibraryExternalReferenceCode was null or undefined when calling getAssetLibraryExportPreview.");
						}

						if (endDate !== undefined) {
							queryParameters["endDate"] = ObjectSerializer.serialize(endDate, "Date");
						}

						if (last !== undefined) {
							queryParameters["last"] = ObjectSerializer.serialize(last, "number");
						}

						if (range !== undefined) {
							queryParameters["range"] = ObjectSerializer.serialize(range, "'all' | 'dateRange' | 'last'");
						}

						if (startDate !== undefined) {
							queryParameters["startDate"] = ObjectSerializer.serialize(startDate, "Date");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportPreview"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param endDate
				 * @param last
				 * @param range
				 * @param startDate
		 * @param headers Optional custom request headers
		 */
		public async getExportPreview(
						endDate?: Date,
						last?: number,
						range?: 'all' | 'dateRange' | 'last',
						startDate?: Date,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportPreview;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/export-preview"
																;

			const queryParameters: any = {};

						if (endDate !== undefined) {
							queryParameters["endDate"] = ObjectSerializer.serialize(endDate, "Date");
						}

						if (last !== undefined) {
							queryParameters["last"] = ObjectSerializer.serialize(last, "number");
						}

						if (range !== undefined) {
							queryParameters["range"] = ObjectSerializer.serialize(range, "'all' | 'dateRange' | 'last'");
						}

						if (startDate !== undefined) {
							queryParameters["startDate"] = ObjectSerializer.serialize(startDate, "Date");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportPreview"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

		/**
		 * 
				 * @param siteExternalReferenceCode
				 * @param endDate
				 * @param last
				 * @param range
				 * @param startDate
		 * @param headers Optional custom request headers
		 */
		public async getSiteExportPreview(
						siteExternalReferenceCode: string,
						endDate?: Date,
						last?: number,
						range?: 'all' | 'dateRange' | 'last',
						startDate?: Date,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportPreview;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/sites/{siteExternalReferenceCode}/export-preview"
						.replace("{siteExternalReferenceCode}",encodeURIComponent(siteExternalReferenceCode))
																				;

			const queryParameters: any = {};

						if (siteExternalReferenceCode === null || siteExternalReferenceCode === undefined) {
							throw new Error("Required parameter siteExternalReferenceCode was null or undefined when calling getSiteExportPreview.");
						}

						if (endDate !== undefined) {
							queryParameters["endDate"] = ObjectSerializer.serialize(endDate, "Date");
						}

						if (last !== undefined) {
							queryParameters["last"] = ObjectSerializer.serialize(last, "number");
						}

						if (range !== undefined) {
							queryParameters["range"] = ObjectSerializer.serialize(range, "'all' | 'dateRange' | 'last'");
						}

						if (startDate !== undefined) {
							queryParameters["startDate"] = ObjectSerializer.serialize(startDate, "Date");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
					,headers || {}
					),
				method: "GET",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportPreview"), response};
					}
					else {
						return {body: await response.text() as any, response};
					}
			}
			else {
				throw new Error("HTTP Error " + response.status + ": " + response.statusText + ". " + await response.text());
			}
		}

}