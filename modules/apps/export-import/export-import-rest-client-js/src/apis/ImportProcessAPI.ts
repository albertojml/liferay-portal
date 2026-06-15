/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectSerializer} from '../utils/SerDes';

		import {ImportProcess} from '../models/ImportProcess';
		import {ImportProcessRequest} from '../models/ImportProcessRequest';
		import {PageImportProcess} from '../models/PageImportProcess';

/**
 * @author Petteri Karttunen
 * @generated
 */

export class ImportProcessAPI {
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
				 * @param assetLibraryId
				 * @param creatorId
				 * @param fields
				 * @param nestedFields
				 * @param page
				 * @param pageSize
				 * @param restrictFields
				 * @param search
				 * @param sort
				 * @param status
		 * @param headers Optional custom request headers
		 */
		public async getAssetLibraryImportProcessesPage(
						assetLibraryId: number,
						creatorId?: number,
						fields?: string,
						nestedFields?: string,
						page?: number,
						pageSize?: number,
						restrictFields?: string,
						search?: string,
						sort?: string,
						status?: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageImportProcess;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/asset-libraries/{assetLibraryId}/import-processes"
						.replace("{assetLibraryId}",encodeURIComponent(assetLibraryId))
																																								;

			const queryParameters: any = {};

						if (assetLibraryId === null || assetLibraryId === undefined) {
							throw new Error("Required parameter assetLibraryId was null or undefined when calling getAssetLibraryImportProcessesPage.");
						}

						if (creatorId !== undefined) {
							queryParameters["creatorId"] = ObjectSerializer.serialize(creatorId, "number");
						}

						if (fields !== undefined) {
							queryParameters["fields"] = ObjectSerializer.serialize(fields, "string");
						}

						if (nestedFields !== undefined) {
							queryParameters["nestedFields"] = ObjectSerializer.serialize(nestedFields, "string");
						}

						if (page !== undefined) {
							queryParameters["page"] = ObjectSerializer.serialize(page, "number");
						}

						if (pageSize !== undefined) {
							queryParameters["pageSize"] = ObjectSerializer.serialize(pageSize, "number");
						}

						if (restrictFields !== undefined) {
							queryParameters["restrictFields"] = ObjectSerializer.serialize(restrictFields, "string");
						}

						if (search !== undefined) {
							queryParameters["search"] = ObjectSerializer.serialize(search, "string");
						}

						if (sort !== undefined) {
							queryParameters["sort"] = ObjectSerializer.serialize(sort, "string");
						}

						if (status !== undefined) {
							queryParameters["status"] = ObjectSerializer.serialize(status, "number");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageImportProcess"), response};
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
		 * Retrieves the import process entry with the specified ID.
				 * @param importProcessId
				 * @param fields
				 * @param nestedFields
				 * @param restrictFields
		 * @param headers Optional custom request headers
		 */
		public async getImportProcess(
						importProcessId: number,
						fields?: string,
						nestedFields?: string,
						restrictFields?: string,
			headers?: {[name: string]: string},
		): Promise<{
				body: ImportProcess;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/import-processes/{importProcessId}"
						.replace("{importProcessId}",encodeURIComponent(importProcessId))
																;

			const queryParameters: any = {};

						if (importProcessId === null || importProcessId === undefined) {
							throw new Error("Required parameter importProcessId was null or undefined when calling getImportProcess.");
						}

						if (fields !== undefined) {
							queryParameters["fields"] = ObjectSerializer.serialize(fields, "string");
						}

						if (nestedFields !== undefined) {
							queryParameters["nestedFields"] = ObjectSerializer.serialize(nestedFields, "string");
						}

						if (restrictFields !== undefined) {
							queryParameters["restrictFields"] = ObjectSerializer.serialize(restrictFields, "string");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ImportProcess"), response};
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
				 * @param creatorId
				 * @param fields
				 * @param nestedFields
				 * @param page
				 * @param pageSize
				 * @param restrictFields
				 * @param search
				 * @param sort
				 * @param status
		 * @param headers Optional custom request headers
		 */
		public async getImportProcessesPage(
						creatorId?: number,
						fields?: string,
						nestedFields?: string,
						page?: number,
						pageSize?: number,
						restrictFields?: string,
						search?: string,
						sort?: string,
						status?: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageImportProcess;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/import-processes"
																																				;

			const queryParameters: any = {};

						if (creatorId !== undefined) {
							queryParameters["creatorId"] = ObjectSerializer.serialize(creatorId, "number");
						}

						if (fields !== undefined) {
							queryParameters["fields"] = ObjectSerializer.serialize(fields, "string");
						}

						if (nestedFields !== undefined) {
							queryParameters["nestedFields"] = ObjectSerializer.serialize(nestedFields, "string");
						}

						if (page !== undefined) {
							queryParameters["page"] = ObjectSerializer.serialize(page, "number");
						}

						if (pageSize !== undefined) {
							queryParameters["pageSize"] = ObjectSerializer.serialize(pageSize, "number");
						}

						if (restrictFields !== undefined) {
							queryParameters["restrictFields"] = ObjectSerializer.serialize(restrictFields, "string");
						}

						if (search !== undefined) {
							queryParameters["search"] = ObjectSerializer.serialize(search, "string");
						}

						if (sort !== undefined) {
							queryParameters["sort"] = ObjectSerializer.serialize(sort, "string");
						}

						if (status !== undefined) {
							queryParameters["status"] = ObjectSerializer.serialize(status, "number");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageImportProcess"), response};
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
				 * @param siteId
				 * @param creatorId
				 * @param fields
				 * @param nestedFields
				 * @param page
				 * @param pageSize
				 * @param restrictFields
				 * @param search
				 * @param sort
				 * @param status
		 * @param headers Optional custom request headers
		 */
		public async getSiteImportProcessesPage(
						siteId: number,
						creatorId?: number,
						fields?: string,
						nestedFields?: string,
						page?: number,
						pageSize?: number,
						restrictFields?: string,
						search?: string,
						sort?: string,
						status?: number,
			headers?: {[name: string]: string},
		): Promise<{
				body: PageImportProcess;
			response: Response;
		}> {

			const path = this._basePath + "/export-import/v1.0/sites/{siteId}/import-processes"
						.replace("{siteId}",encodeURIComponent(siteId))
																																								;

			const queryParameters: any = {};

						if (siteId === null || siteId === undefined) {
							throw new Error("Required parameter siteId was null or undefined when calling getSiteImportProcessesPage.");
						}

						if (creatorId !== undefined) {
							queryParameters["creatorId"] = ObjectSerializer.serialize(creatorId, "number");
						}

						if (fields !== undefined) {
							queryParameters["fields"] = ObjectSerializer.serialize(fields, "string");
						}

						if (nestedFields !== undefined) {
							queryParameters["nestedFields"] = ObjectSerializer.serialize(nestedFields, "string");
						}

						if (page !== undefined) {
							queryParameters["page"] = ObjectSerializer.serialize(page, "number");
						}

						if (pageSize !== undefined) {
							queryParameters["pageSize"] = ObjectSerializer.serialize(pageSize, "number");
						}

						if (restrictFields !== undefined) {
							queryParameters["restrictFields"] = ObjectSerializer.serialize(restrictFields, "string");
						}

						if (search !== undefined) {
							queryParameters["search"] = ObjectSerializer.serialize(search, "string");
						}

						if (sort !== undefined) {
							queryParameters["sort"] = ObjectSerializer.serialize(sort, "string");
						}

						if (status !== undefined) {
							queryParameters["status"] = ObjectSerializer.serialize(status, "number");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "PageImportProcess"), response};
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
				 * @param assetLibraryExternalReferenceCode
		 		* @param requestBody Request body that can be one of multiple content types
		 * @param headers Optional custom request headers
		 */
		public async postAssetLibraryImportProcessWithContentType(
						assetLibraryExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ImportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/asset-libraries/{assetLibraryExternalReferenceCode}/import-processes"
						.replace("{assetLibraryExternalReferenceCode}",encodeURIComponent(assetLibraryExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (assetLibraryExternalReferenceCode === null || assetLibraryExternalReferenceCode === undefined) {
							throw new Error("Required parameter assetLibraryExternalReferenceCode was null or undefined when calling postAssetLibraryImportProcess.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ImportProcess"), response};
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
						 * @param importProcessRequest
					 */
					public async postAssetLibraryImportProcess(
									assetLibraryExternalReferenceCode: string,
							importProcessRequest?: ImportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ImportProcess;
						response: Response;
					}> {
						return this.postAssetLibraryImportProcessWithContentType(
										assetLibraryExternalReferenceCode,
							{
								parameters: {
										importProcessRequest: importProcessRequest
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
		public async postImportProcessWithContentType(
					requestBody:
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ImportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/import-processes"
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ImportProcess"), response};
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
						 * @param importProcessRequest
					 */
					public async postImportProcess(
							importProcessRequest?: ImportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ImportProcess;
						response: Response;
					}> {
						return this.postImportProcessWithContentType(
							{
								parameters: {
										importProcessRequest: importProcessRequest
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
		public async postSiteImportProcessWithContentType(
						siteExternalReferenceCode: string,
					requestBody:
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/json"
							}
								|
							{
								parameters: {
										importProcessRequest?: ImportProcessRequest
								},
								type: "application/xml"
							}
								,
			headers?: {[name: string]: string},
		): Promise<{
				body: ImportProcess;
			response: Response;
		}> {
				let body;
						if (requestBody.type === "application/json") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}
						if (requestBody.type === "application/xml") {
								body = JSON.stringify(ObjectSerializer.serialize(requestBody.parameters.importProcessRequest, "ImportProcessRequest"));
						}

			const path = this._basePath + "/export-import/v1.0/sites/{siteExternalReferenceCode}/import-processes"
						.replace("{siteExternalReferenceCode}",encodeURIComponent(siteExternalReferenceCode))
				;

			const queryParameters: any = {};

						if (siteExternalReferenceCode === null || siteExternalReferenceCode === undefined) {
							throw new Error("Required parameter siteExternalReferenceCode was null or undefined when calling postSiteImportProcess.");
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
						return {body: ObjectSerializer.deserialize(await response.json(), "ImportProcess"), response};
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
						 * @param importProcessRequest
					 */
					public async postSiteImportProcess(
									siteExternalReferenceCode: string,
							importProcessRequest?: ImportProcessRequest,
						headers?: {[name: string]: string}
					): Promise<{
							body: ImportProcess;
						response: Response;
					}> {
						return this.postSiteImportProcessWithContentType(
										siteExternalReferenceCode,
							{
								parameters: {
										importProcessRequest: importProcessRequest
								},
								type: "application/json"
							},
							headers
						);
					}
}