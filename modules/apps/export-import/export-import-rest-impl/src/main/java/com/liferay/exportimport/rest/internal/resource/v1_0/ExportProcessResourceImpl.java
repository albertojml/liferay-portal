/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.resource.v1_0;

import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.rest.dto.v1_0.ExportProcess;
import com.liferay.exportimport.rest.dto.v1_0.ExportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.Status;
import com.liferay.exportimport.rest.internal.util.DateRangeUtil;
import com.liferay.exportimport.rest.internal.util.ParameterMapUtil;
import com.liferay.exportimport.rest.internal.util.PermissionUtil;
import com.liferay.exportimport.rest.resource.v1_0.ExportProcessResource;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchBackgroundTaskException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.staging.StagingGroupHelper;

import jakarta.ws.rs.NotFoundException;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Daniel Raposo
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/export-process.properties",
	scope = ServiceScope.PROTOTYPE, service = ExportProcessResource.class
)
public class ExportProcessResourceImpl extends BaseExportProcessResourceImpl {

	@Override
	public Page<ExportProcess> getAssetLibraryExportProcessesPage(
			Long assetLibraryId, Long creatorId, String search, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_getBackgroundTasks(
					creatorId, assetLibraryId, pagination, search, sorts,
					status),
				this::_toExportProcess),
			pagination,
			_backgroundTaskLocalService.dynamicQueryCount(
				_getDynamicQuery(creatorId, assetLibraryId, search, status)));
	}

	@Override
	public ExportProcess getExportProcess(Long exportProcessId)
		throws Exception {

		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.getBackgroundTask(exportProcessId);

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), backgroundTask.getGroupId());

		if (!StringUtil.equals(
				backgroundTask.getTaskExecutorClassName(),
				BackgroundTaskExecutorNames.
					LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR)) {

			throw new NoSuchBackgroundTaskException();
		}

		return _toExportProcess(backgroundTask);
	}

	@Override
	public Page<ExportProcess> getExportProcessesPage(
			Long creatorId, String search, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		long groupId = _getCompanyGroupId();

		return Page.of(
			transform(
				_getBackgroundTasks(
					creatorId, groupId, pagination, search, sorts, status),
				this::_toExportProcess),
			pagination,
			_backgroundTaskLocalService.dynamicQueryCount(
				_getDynamicQuery(creatorId, groupId, search, status)));
	}

	@Override
	public Page<ExportProcess> getSiteExportProcessesPage(
			Long siteId, Long creatorId, String search, Integer status,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_getBackgroundTasks(
					creatorId, siteId, pagination, search, sorts, status),
				this::_toExportProcess),
			pagination,
			_backgroundTaskLocalService.dynamicQueryCount(
				_getDynamicQuery(creatorId, siteId, search, status)));
	}

	@Override
	public ExportProcess postAssetLibraryExportProcess(
			String assetLibraryExternalReferenceCode,
			ExportProcessRequest exportProcessRequest)
		throws Exception {

		Group group = groupLocalService.fetchGroupByExternalReferenceCode(
			assetLibraryExternalReferenceCode, contextCompany.getCompanyId());

		if ((group == null) || !group.isDepot()) {
			throw new NotFoundException();
		}

		return _postExportProcess(group, exportProcessRequest);
	}

	@Override
	public ExportProcess postExportProcess(
			ExportProcessRequest exportProcessRequest)
		throws Exception {

		Group group = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		if (group == null) {
			throw new NotFoundException();
		}

		return _postExportProcess(group, exportProcessRequest);
	}

	@Override
	public ExportProcess postSiteExportProcess(
			String siteExternalReferenceCode,
			ExportProcessRequest exportProcessRequest)
		throws Exception {

		Group group = groupLocalService.fetchGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		if ((group == null) || !group.isSite()) {
			throw new NotFoundException();
		}

		return _postExportProcess(group, exportProcessRequest);
	}

	private List<BackgroundTask> _getBackgroundTasks(
			Long creatorId, long groupId, Pagination pagination, String search,
			Sort[] sorts, Integer status)
		throws Exception {

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), groupId);

		DynamicQuery dynamicQuery = _getDynamicQuery(
			creatorId, groupId, search, status);

		_setSorts(dynamicQuery, sorts);

		return _backgroundTaskLocalService.dynamicQuery(
			dynamicQuery, pagination.getStartPosition(),
			pagination.getEndPosition());
	}

	private long _getCompanyGroupId() {
		Group group = _stagingGroupHelper.fetchCompanyGroup(
			contextCompany.getCompanyId());

		if (group == null) {
			return 0L;
		}

		return group.getGroupId();
	}

	private DynamicQuery _getDynamicQuery(
		Long creatorId, long groupId, String search, Integer status) {

		DynamicQuery dynamicQuery = _backgroundTaskLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"companyId", contextCompany.getCompanyId()));
		dynamicQuery.add(RestrictionsFactoryUtil.eq("groupId", groupId));

		if (!Validator.isBlank(search)) {
			dynamicQuery.add(RestrictionsFactoryUtil.ilike("name", search));
		}

		if (status != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("status", status));
		}

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"taskExecutorClassName",
				BackgroundTaskExecutorNames.
					LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR));

		if (creatorId != null) {
			dynamicQuery.add(RestrictionsFactoryUtil.eq("userId", creatorId));
		}

		return dynamicQuery;
	}

	private ExportProcess _postExportProcess(
			Group group, ExportProcessRequest exportProcessRequest)
		throws Exception {

		long groupId = group.getGroupId();

		PermissionUtil.checkExportPermission(
			contextCompany.getCompanyId(), groupId);

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest);

		long[] layoutIds = GetterUtil.getLongValues(
			parameterMap.get("layoutIds"));
		boolean privateLayout = MapUtil.getBoolean(
			parameterMap, "privateLayout");

		if (ArrayUtil.isEmpty(layoutIds) &&
			MapUtil.getBoolean(
				parameterMap,
				"PORTLET_DATA_" + LayoutAdminPortletKeys.LAYOUT_SET_LAYOUTS)) {

			layoutIds = _exportImportHelper.getAllLayoutIds(
				groupId, privateLayout);
		}

		Map<String, Serializable> settingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					contextUser.getUserId(), groupId, privateLayout, layoutIds,
					parameterMap, contextAcceptLanguage.getPreferredLocale(),
					contextUser.getTimeZone());

		_putDateRange(exportProcessRequest, settingsMap);

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					contextUser.getUserId(), exportProcessRequest.getName(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					settingsMap);

		long backgroundTaskId =
			_exportImportLocalService.exportLayoutsAsFileInBackground(
				contextUser.getUserId(), exportImportConfiguration);

		return _toExportProcess(
			_backgroundTaskLocalService.getBackgroundTask(backgroundTaskId));
	}

	private void _putDateRange(
		ExportProcessRequest exportProcessRequest,
		Map<String, Serializable> settingsMap) {

		DateRange dateRange = DateRangeUtil.toDateRange(
			exportProcessRequest.getEndDate(), exportProcessRequest.getLast(),
			exportProcessRequest.getRangeAsString(),
			exportProcessRequest.getStartDate());

		if (dateRange.getStartDate() == null) {
			return;
		}

		settingsMap.put("endDate", dateRange.getEndDate());
		settingsMap.put("startDate", dateRange.getStartDate());
	}

	private void _setSorts(DynamicQuery dynamicQuery, Sort[] sorts) {
		if (sorts == null) {
			dynamicQuery.addOrder(OrderFactoryUtil.desc("modifiedDate"));

			return;
		}

		for (Sort sort : sorts) {
			String fieldName = sort.getFieldName();

			fieldName = StringUtil.removeSubstring(fieldName, "_sortable");

			if (fieldName.equals("creator")) {
				fieldName = "userName";
			}
			else if (fieldName.equals("dateCreated")) {
				fieldName = "createDate";
			}
			else if (fieldName.equals("id")) {
				fieldName = "backgroundTaskId";
			}
			else if (fieldName.equals("dateModified")) {
				fieldName = "modifiedDate";
			}

			if (sort.isReverse()) {
				dynamicQuery.addOrder(OrderFactoryUtil.desc(fieldName));
			}
			else {
				dynamicQuery.addOrder(OrderFactoryUtil.asc(fieldName));
			}
		}
	}

	private ExportProcess _toExportProcess(BackgroundTask backgroundTask) {
		return new ExportProcess() {
			{
				setCreator(
					() -> CreatorUtil.toCreator(
						null, _portal,
						_userLocalService.fetchUser(
							backgroundTask.getUserId())));
				setDateCreated(backgroundTask::getCreateDate);
				setDateModified(backgroundTask::getModifiedDate);
				setId(backgroundTask::getBackgroundTaskId);
				setName(backgroundTask::getName);
				setStatus(() -> _toStatus(backgroundTask.getStatus()));
			}
		};
	}

	private Status _toStatus(int status) {
		return new Status() {
			{
				setCode(() -> status);
				setLabel(
					() -> _language.get(
						contextAcceptLanguage.getPreferredLocale(),
						BackgroundTaskConstants.getStatusLabel(status)));
			}
		};
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private UserLocalService _userLocalService;

}