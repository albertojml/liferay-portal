/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.mcp.server.rest.dto.v1_0.Tool;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSet;
import com.liferay.mcp.server.rest.dto.v1_0.ToolSummary;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.http.VulcanRequestForwarder;
import com.liferay.portal.vulcan.jackson.databind.ObjectMapperProviderUtil;
import com.liferay.portal.vulcan.pagination.Page;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jaxrs.runtime.JaxrsServiceRuntime;
import org.osgi.service.jaxrs.runtime.dto.ApplicationDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceDTO;
import org.osgi.service.jaxrs.runtime.dto.ResourceMethodInfoDTO;
import org.osgi.service.jaxrs.runtime.dto.RuntimeDTO;

/**
 * @author Alejandro Tardín
 */
public class ToolSetUtil {

	public static Tool getTool(
		HttpServletRequest httpServletRequest,
		Map<String, Set<String>> restrictFieldNamesMap, String toolName,
		String toolSetName) {

		return OpenAPIUtil.getTool(
			!Objects.equals(toolSetName, _TOOL_SET_NAME),
			_getOpenAPIJSONObject(
				_getOpenAPIBrief(toolSetName), httpServletRequest),
			_getRestrictFieldNames(
				restrictFieldNamesMap, toolName, toolSetName),
			toolName);
	}

	public static String getToolKey(String toolSetName, String toolName) {
		return toolSetName + StringPool.SPACE + toolName;
	}

	public static Page<ToolSet> getToolSetsPage() {
		Map<String, OpenAPIBrief> openAPIBriefs = _getOpenAPIBriefs();

		return Page.of(
			TransformUtil.transform(
				openAPIBriefs.entrySet(),
				entry -> new ToolSet() {
					{
						setDescription(
							() -> {
								OpenAPIBrief openAPIBrief = entry.getValue();

								return openAPIBrief._description;
							});

						setName(entry::getKey);
					}
				}));
	}

	public static Page<ToolSummary> getToolSummariesPage(
		HttpServletRequest httpServletRequest, String toolSetName) {

		return Page.of(
			OpenAPIUtil.getToolSummaries(
				_getOpenAPIJSONObject(
					_getOpenAPIBrief(toolSetName), httpServletRequest)));
	}

	public static Response invokeTool(
			List<String> dataMaskExternalReferenceCodes,
			HttpServletRequest httpServletRequest, Object inputObject,
			String mcpServerProfileExternalReferenceCode,
			Map<String, Set<String>> restrictFieldNamesMap, String toolName,
			String toolSetName)
		throws Exception {

		JSONObject inputJSONObject = null;

		if (inputObject instanceof JSONObject) {
			inputJSONObject = (JSONObject)inputObject;
		}
		else if (inputObject instanceof Map) {
			inputJSONObject = JSONFactoryUtil.createJSONObject(
				(Map<String, ?>)inputObject);
		}
		else {
			inputJSONObject = JSONFactoryUtil.createJSONObject();
		}

		if (Objects.equals(toolSetName, _TOOL_SET_NAME)) {
			if (Objects.equals(toolName, "getToolSetToolSetNameTool")) {
				return _getResponse(
					getTool(
						httpServletRequest, restrictFieldNamesMap,
						inputJSONObject.getString("toolName"),
						inputJSONObject.getString("toolSetName")));
			}

			if (Objects.equals(
					toolName, "getToolSetToolSetNameToolSummariesPage")) {

				return _getResponse(
					getToolSummariesPage(
						httpServletRequest,
						inputJSONObject.getString("toolSetName")));
			}

			if (Objects.equals(toolName, "getToolSetsPage")) {
				return _getResponse(getToolSetsPage());
			}

			if (Objects.equals(toolName, "postToolSetToolSetNameToolInvoke")) {
				return invokeTool(
					dataMaskExternalReferenceCodes, httpServletRequest,
					inputJSONObject.opt("body"),
					mcpServerProfileExternalReferenceCode,
					restrictFieldNamesMap,
					inputJSONObject.getString("toolName"),
					inputJSONObject.getString("toolSetName"));
			}
		}

		OpenAPIBrief openAPIBrief = _getOpenAPIBrief(toolSetName);

		Set<String> restrictFieldNames = _getRestrictFieldNames(
			restrictFieldNamesMap, toolName, toolSetName);

		if ((restrictFieldNames != null) && !restrictFieldNames.isEmpty()) {
			_routeAuditMessage(
				"RESTRICT_FIELDS", httpServletRequest,
				mcpServerProfileExternalReferenceCode,
				"Restricted fields were excluded from the tool response.",
				restrictFieldNames, toolName, toolSetName);
		}

		Set<String> restrictedQueryFieldNames =
			OpenAPIUtil.getRestrictedQueryFieldNames(
				inputJSONObject, restrictFieldNames);

		if (!restrictedQueryFieldNames.isEmpty()) {
			_routeAuditMessage(
				"BLOCK_RESTRICT_FIELDS", httpServletRequest,
				mcpServerProfileExternalReferenceCode,
				"The tool invocation was blocked because it queries " +
					"restricted fields.",
				restrictedQueryFieldNames, toolName, toolSetName);

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to invoke the \"", toolName,
					"\" tool because the query references the restricted ",
					"fields \"",
					StringUtil.merge(
						restrictedQueryFieldNames, StringPool.COMMA_AND_SPACE),
					"\". Remove them from the query and try again."));
		}

		if (OpenAPIUtil.isRestrictedSearch(
				inputJSONObject, restrictFieldNames)) {

			_routeAuditMessage(
				"BLOCK_RESTRICT_FIELDS", httpServletRequest,
				mcpServerProfileExternalReferenceCode,
				"The tool invocation was blocked because a keyword search " +
					"can match restricted fields.",
				restrictFieldNames, toolName, toolSetName);

			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to invoke the \"", toolName,
					"\" tool with a keyword search because the search can ",
					"match the restricted fields \"",
					StringUtil.merge(
						restrictFieldNames, StringPool.COMMA_AND_SPACE),
					"\". Remove the search and try again."));
		}

		VulcanRequestForwarder vulcanRequestForwarder =
			_vulcanRequestForwarderSnapshot.get();

		VulcanRequestForwarder.Response response =
			vulcanRequestForwarder.forward(
				httpServletRequest,
				OpenAPIUtil.getRequest(
					openAPIBrief._basePath,
					HashMapBuilder.put(
						"X-Liferay-Data-Masks",
						() -> StringUtil.merge(
							dataMaskExternalReferenceCodes, StringPool.COMMA)
					).build(),
					inputJSONObject,
					_getOpenAPIJSONObject(openAPIBrief, httpServletRequest),
					restrictFieldNames, toolName,
					_getUser(httpServletRequest)));

		String content = response.getContent();

		return Response.status(
			response.getStatusCode()
		).entity(
			Validator.isNull(content) ? null : _getContent(content)
		).type(
			ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static String _getContent(String content) {
		if (Validator.isNull(content) || (content.charAt(0) != '{') ||
			!content.contains("\"actions\"")) {

			return content;
		}

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(content);

			if (!jsonObject.has("actions")) {
				return content;
			}

			jsonObject.remove("actions");

			return jsonObject.toString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return content;
		}
	}

	private static String _getDescription(Object service) {
		if (service == null) {
			return null;
		}

		Class<?> serviceClass = service.getClass();

		OpenAPIDefinition openAPIDefinition = serviceClass.getAnnotation(
			OpenAPIDefinition.class);

		if (openAPIDefinition == null) {
			return null;
		}

		Info info = openAPIDefinition.info();

		String description = info.description();

		if (description == null) {
			return null;
		}

		return description;
	}

	private static OpenAPIBrief _getOpenAPIBrief(String toolSetName) {
		Map<String, OpenAPIBrief> openAPIBriefs = _getOpenAPIBriefs();

		OpenAPIBrief openAPIBrief = openAPIBriefs.get(toolSetName);

		if (openAPIBrief == null) {
			throw new IllegalArgumentException(
				"No tool-set was found with name \"" + toolSetName + "\"");
		}

		return openAPIBrief;
	}

	private static Map<String, OpenAPIBrief> _getOpenAPIBriefs() {
		Map<String, OpenAPIBrief> openAPIBriefs = new TreeMap<>();

		JaxrsServiceRuntime jaxrsServiceRuntime =
			_jaxrsServiceRuntimeSnapshot.get();

		RuntimeDTO runtimeDTO = jaxrsServiceRuntime.getRuntimeDTO();

		Map<String, String> toolSetDescriptions = _getToolSetDescriptions();

		for (ApplicationDTO applicationDTO : runtimeDTO.applicationDTOs) {
			String base = applicationDTO.base;

			if (Validator.isNull(base)) {
				continue;
			}

			if (!base.startsWith(StringPool.SLASH)) {
				base = StringPool.SLASH + base;
			}

			String openAPIPath = _getOpenAPIPath(applicationDTO);

			if (openAPIPath == null) {
				continue;
			}

			String basePath = base + _getVersionPath(openAPIPath);

			openAPIBriefs.put(
				StringUtil.replace(
					basePath.substring(1), CharPool.SLASH, CharPool.DASH),
				new OpenAPIBrief(
					base, toolSetDescriptions.get(basePath), openAPIPath));
		}

		return openAPIBriefs;
	}

	private static JSONObject _getOpenAPIJSONObject(
		OpenAPIBrief openAPIBrief, HttpServletRequest httpServletRequest) {

		return _openAPIJSONObjectCache.computeIfAbsent(
			openAPIBrief._basePath + openAPIBrief._openAPIPath,
			path -> {
				try {
					VulcanRequestForwarder vulcanRequestForwarder =
						_vulcanRequestForwarderSnapshot.get();

					VulcanRequestForwarder.Response response =
						vulcanRequestForwarder.forward(
							httpServletRequest,
							new VulcanRequestForwarder.Request() {

								@Override
								public String getMethod() {
									return "GET";
								}

								@Override
								public String getPath() {
									return path;
								}

								@Override
								public User getUser() {
									return _getUser(httpServletRequest);
								}

							});

					if (response.getStatusCode() >= 300) {
						throw new RuntimeException(
							StringBundler.concat(
								"HTTP ", response.getStatusCode(), " for ",
								path, ": ", response.getContent()));
					}

					return JSONFactoryUtil.createJSONObject(
						response.getContent());
				}
				catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			});
	}

	private static String _getOpenAPIPath(ApplicationDTO applicationDTO) {
		for (ResourceDTO resourceDTO : applicationDTO.resourceDTOs) {
			String openAPIPath = _getOpenAPIPath(resourceDTO.resourceMethods);

			if (openAPIPath != null) {
				return openAPIPath;
			}
		}

		return _getOpenAPIPath(applicationDTO.resourceMethods);
	}

	private static String _getOpenAPIPath(
		ResourceMethodInfoDTO[] resourceMethodInfoDTOs) {

		if (resourceMethodInfoDTOs == null) {
			return null;
		}

		for (ResourceMethodInfoDTO resourceMethodInfoDTO :
				resourceMethodInfoDTOs) {

			String path = resourceMethodInfoDTO.path;

			if ((path != null) && path.contains("/openapi")) {
				return StringUtil.replace(path, "{type:json|yaml}", "json");
			}
		}

		return null;
	}

	private static Response _getResponse(Object value) throws Exception {
		ObjectMapper objectMapper = ObjectMapperProviderUtil.getObjectMapper();

		return Response.ok(
			objectMapper.writeValueAsString(value), ContentTypes.TEXT_PLAIN_UTF8
		).build();
	}

	private static Set<String> _getRestrictFieldNames(
		Map<String, Set<String>> restrictFieldNamesMap, String toolName,
		String toolSetName) {

		if (restrictFieldNamesMap == null) {
			return null;
		}

		return restrictFieldNamesMap.get(getToolKey(toolSetName, toolName));
	}

	private static Map<String, String> _getToolSetDescriptions() {
		Map<String, String> toolSetDescriptions = new HashMap<>();

		Bundle bundle = FrameworkUtil.getBundle(ToolSetUtil.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference<?>[] serviceReferences;

		try {
			serviceReferences = bundleContext.getAllServiceReferences(
				null, "(openapi.resource=true)");
		}
		catch (InvalidSyntaxException invalidSyntaxException) {
			if (_log.isWarnEnabled()) {
				_log.warn(invalidSyntaxException);
			}

			return toolSetDescriptions;
		}

		if (serviceReferences == null) {
			return toolSetDescriptions;
		}

		for (ServiceReference<?> serviceReference : serviceReferences) {
			String path = GetterUtil.getString(
				serviceReference.getProperty("openapi.resource.path"));

			if (Validator.isNull(path)) {
				continue;
			}

			String version = GetterUtil.getString(
				serviceReference.getProperty("api.version"));

			if (Validator.isNotNull(version)) {
				path = path + StringPool.SLASH + version;
			}

			Object service = bundleContext.getService(serviceReference);

			try {
				toolSetDescriptions.putIfAbsent(path, _getDescription(service));
			}
			finally {
				bundleContext.ungetService(serviceReference);
			}
		}

		return toolSetDescriptions;
	}

	private static User _getUser(HttpServletRequest httpServletRequest) {
		return UserLocalServiceUtil.fetchUser(
			GetterUtil.getLong(
				httpServletRequest.getAttribute(WebKeys.USER_ID)));
	}

	private static String _getVersionPath(String openAPIPath) {
		int index = openAPIPath.lastIndexOf("/openapi");

		if (index <= 0) {
			return StringPool.BLANK;
		}

		return openAPIPath.substring(0, index);
	}

	private static void _routeAuditMessage(
		String eventType, HttpServletRequest httpServletRequest,
		String mcpServerProfileExternalReferenceCode, String message,
		Set<String> restrictFieldNames, String toolName, String toolSetName) {

		AuditRouter auditRouter = AuditRouterUtil.getAuditRouter();

		if (auditRouter == null) {
			return;
		}

		try {
			User user = _getUser(httpServletRequest);

			auditRouter.route(
				new AuditMessage(
					0, (user != null) ? user.getCompanyId() : 0,
					(user != null) ? user.getUserId() : 0,
					(user != null) ? user.getFullName() : null, null,
					JSONUtil.put(
						"mcpServerProfileExternalReferenceCode",
						mcpServerProfileExternalReferenceCode
					).put(
						"restrictFieldNames",
						StringUtil.merge(restrictFieldNames, StringPool.COMMA)
					).put(
						"toolName", toolName
					).put(
						"toolSetName", toolSetName
					),
					"L_MCP_SERVER_PROFILE_RESTRICT_FIELD", null, eventType,
					message));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to route audit message", exception);
			}
		}
	}

	private static final String _TOOL_SET_NAME = "mcp-server-v1.0";

	private static final Log _log = LogFactoryUtil.getLog(ToolSetUtil.class);

	private static final Snapshot<JaxrsServiceRuntime>
		_jaxrsServiceRuntimeSnapshot = new Snapshot<>(
			ToolSetUtil.class, JaxrsServiceRuntime.class);
	private static final Map<String, JSONObject> _openAPIJSONObjectCache =
		new ConcurrentHashMap<>();
	private static final Snapshot<VulcanRequestForwarder>
		_vulcanRequestForwarderSnapshot = new Snapshot<>(
			ToolSetUtil.class, VulcanRequestForwarder.class);

	private static class OpenAPIBrief {

		private OpenAPIBrief(
			String basePath, String description, String openAPIPath) {

			_basePath = basePath;
			_description = description;
			_openAPIPath = openAPIPath;
		}

		private final String _basePath;
		private final String _description;
		private final String _openAPIPath;

	}

}