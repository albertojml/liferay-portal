/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {ExportProcess} from '../models/ExportProcess';
		import {ExportProcessRequest} from '../models/ExportProcessRequest';

/**
 * @author Petteri Karttunen
 * @generated
 */

export class ExportProcessAPI {
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
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postAssetLibraryExportProcessWithContentType(
						assetLibraryExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/asset-libraries/{assetLibraryExternalReferenceCode}/export-processes"
						.replace("{assetLibraryExternalReferenceCode}",encodeURIComponent(assetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryExternalReferenceCode === null || assetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter assetLibraryExternalReferenceCode was null or undefined when calling postAssetLibraryExportProcess.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportProcess"), response};
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
					 *  - Default method for JSON body
							 * @param assetLibraryExternalReferenceCode
						 * @param exportProcessRequest
					 */
					public async postAssetLibraryExportProcess(
									assetLibraryExternalReferenceCode: string,
							exportProcessRequest?: ExportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ExportProcess;
						response: Response;
					}> {
						return this.postAssetLibraryExportProcessWithContentType(
										assetLibraryExternalReferenceCode,
							{
								parameters: {
										exportProcessRequest: exportProcessRequest
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postExportProcessWithContentType(
					requestBody:
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/export-processes"
;

			const queryParameters: any = {};

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportProcess"), response};
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
					 *  - Default method for JSON body
						 * @param exportProcessRequest
					 */
					public async postExportProcess(
							exportProcessRequest?: ExportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ExportProcess;
						response: Response;
					}> {
						return this.postExportProcessWithContentType(
							{
								parameters: {
										exportProcessRequest: exportProcessRequest
								},
								type: "application/json"
							},
							headers
						);
					}
		/**
		 * 
				 * @param siteExternalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postSiteExportProcessWithContentType(
						siteExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										exportProcessRequest?: ExportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ExportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.exportProcessRequest, "ExportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/sites/{siteExternalReferenceCode}/export-processes"
						.replace("{siteExternalReferenceCode}",encodeURIComponent(siteExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteExternalReferenceCode === null || siteExternalReferenceCode === undefined) {
							throw new Error("Required parameter siteExternalReferenceCode was null or undefined when calling postSiteExportProcess.");
						}

			const queryString = Object.keys(queryParameters).length ?
				"?" + new URLSearchParams(queryParameters).toString() :
					"";

			const response = await fetch(path + queryString, {
					body: body,
				headers:
					Object.assign({}, this._defaultHeaders
						,{
								Accept: "application/json"
						}
								,{"Content-Type": requestBody.type}
					,headers || {}
					),
				method: "POST",
			});

			if (response.ok) {
				const contentType = response.headers.get("content-type") || "";

					if (contentType.includes("application/json")) {
						return {body: ObjectSerializer.deserialize(await response.json(), "ExportProcess"), response};
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
					 *  - Default method for JSON body
							 * @param siteExternalReferenceCode
						 * @param exportProcessRequest
					 */
					public async postSiteExportProcess(
									siteExternalReferenceCode: string,
							exportProcessRequest?: ExportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ExportProcess;
						response: Response;
					}> {
						return this.postSiteExportProcessWithContentType(
										siteExternalReferenceCode,
							{
								parameters: {
										exportProcessRequest: exportProcessRequest
								},
								type: "application/json"
							},
							headers
						);
					}
}