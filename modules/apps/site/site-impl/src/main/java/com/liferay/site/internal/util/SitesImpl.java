/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.util.comparator.BackgroundTaskCreateDateComparator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.sites.kernel.util.Sites;

import jakarta.portlet.PortletPreferences;

import java.io.File;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
@Component(service = Sites.class)
public class SitesImpl implements Sites {

	@Override
	public void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		Locale siteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();

		LayoutTypePortlet targetLayoutType =
			(LayoutTypePortlet)targetLayout.getLayoutType();

		List<String> targetLayoutPortletIds = targetLayoutType.getPortletIds();

		Layout layoutPrototypeLayout = layoutPrototype.getLayout();

		byte[] iconBytes = null;

		if (layoutPrototypeLayout.isIconImage()) {
			Image image = _imageLocalService.getImage(
				layoutPrototypeLayout.getIconImageId());

			iconBytes = image.getTextObj();
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		long[] originalAssetCategoryIds = serviceContext.getAssetCategoryIds();
		String[] originalAssetTagNames = serviceContext.getAssetTagNames();
		Serializable originalPortletLayoutPageTemplateEntryERC =
			serviceContext.getAttribute("portletLayoutPageTemplateEntryERC");
		Serializable originalPortletLayoutPageTemplateEntryLinkEnabled =
			serviceContext.getAttribute(
				"portletLayoutPageTemplateEntryLinkEnabled");
		Serializable originalPortletLayoutPageTemplateEntryScopeERC =
			serviceContext.getAttribute(
				"portletLayoutPageTemplateEntryScopeERC");

		try {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				Layout.class.getName(), layoutPrototypeLayout.getPlid());

			serviceContext.setAssetCategoryIds(assetEntry.getCategoryIds());
			serviceContext.setAssetTagNames(assetEntry.getTagNames());

			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					getFirstLayoutPageTemplateEntry(
						layoutPrototype.getLayoutPrototypeId());

			serviceContext.setAttribute(
				"portletLayoutPageTemplateEntryERC",
				layoutPageTemplateEntry.getExternalReferenceCode());

			serviceContext.setAttribute(
				"portletLayoutPageTemplateEntryLinkEnabled", linkEnabled);
			serviceContext.setAttribute(
				"portletLayoutPageTemplateEntryScopeERC",
				ScopeUtil.getItemScopeExternalReferenceCode(
					layoutPageTemplateEntry.getGroupId(),
					targetLayout.getGroupId()));

			Locale targetSiteDefaultLocale = _portal.getSiteDefaultLocale(
				targetLayout.getGroupId());

			LocaleThreadLocal.setSiteDefaultLocale(targetSiteDefaultLocale);

			targetLayout = _layoutLocalService.updateLayout(
				targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
				targetLayout.getLayoutId(), targetLayout.getParentLayoutId(),
				targetLayout.getNameMap(), targetLayout.getTitleMap(),
				targetLayout.getDescriptionMap(), targetLayout.getKeywordsMap(),
				targetLayout.getRobotsMap(), layoutPrototypeLayout.getType(),
				targetLayout.isHidden(), targetLayout.getFriendlyURLMap(),
				layoutPrototypeLayout.isIconImage(), iconBytes, null, null,
				null,
				layoutPrototypeLayout.getMasterLayoutPageTemplateEntryERC(),
				serviceContext);
		}
		finally {
			serviceContext.setAssetCategoryIds(originalAssetCategoryIds);
			serviceContext.setAssetTagNames(originalAssetTagNames);

			if (originalPortletLayoutPageTemplateEntryERC == null) {
				serviceContext.removeAttribute(
					"portletLayoutPageTemplateEntryERC");
			}
			else {
				serviceContext.setAttribute(
					"portletLayoutPageTemplateEntryERC",
					originalPortletLayoutPageTemplateEntryERC);
			}

			if (originalPortletLayoutPageTemplateEntryLinkEnabled == null) {
				serviceContext.removeAttribute(
					"portletLayoutPageTemplateEntryLinkEnabled");
			}
			else {
				serviceContext.setAttribute(
					"portletLayoutPageTemplateEntryLinkEnabled",
					originalPortletLayoutPageTemplateEntryLinkEnabled);
			}

			if (originalPortletLayoutPageTemplateEntryScopeERC == null) {
				serviceContext.removeAttribute(
					"portletLayoutPageTemplateEntryScopeERC");
			}
			else {
				serviceContext.setAttribute(
					"portletLayoutPageTemplateEntryScopeERC",
					originalPortletLayoutPageTemplateEntryScopeERC);
			}

			LocaleThreadLocal.setSiteDefaultLocale(siteDefaultLocale);
		}

		targetLayout = _layoutLocalService.updateTypeSettings(
			targetLayout, layoutPrototypeLayout.getTypeSettings());

		copyExpandoBridgeAttributes(layoutPrototypeLayout, targetLayout);

		copyPortletPermissions(targetLayout, layoutPrototypeLayout);

		copyPortletSetups(layoutPrototypeLayout, targetLayout);

		_layoutLocalService.updateLookAndFeel(
			targetLayout.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId(), layoutPrototypeLayout.getThemeId(),
			layoutPrototypeLayout.getColorSchemeId(),
			layoutPrototypeLayout.getCss());

		deleteUnreferencedPortlets(
			targetLayoutPortletIds, targetLayout, layoutPrototypeLayout);

		UnicodeProperties prototypeTypeSettingsUnicodeProperties =
			layoutPrototypeLayout.getTypeSettingsProperties();

		if (prototypeTypeSettingsUnicodeProperties.containsKey(
				MERGE_FAIL_COUNT)) {

			prototypeTypeSettingsUnicodeProperties.remove(MERGE_FAIL_COUNT);

			_layoutLocalService.updateLayout(layoutPrototypeLayout);
		}
	}

	@Override
	public void copyPortletPermissions(Layout targetLayout, Layout sourceLayout)
		throws Exception {

		List<Role> roles = _roleLocalService.getGroupRelatedRoles(
			targetLayout.getGroupId());
		Group targetGroup = targetLayout.getGroup();

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = sourceLayoutTypePortlet.getPortletIds();

		for (String sourcePortletId : sourcePortletIds) {
			String resourceName = PortletIdCodec.decodePortletName(
				sourcePortletId);

			String sourceResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				sourceLayout.getPlid(), sourcePortletId);

			String targetResourcePrimKey = PortletPermissionUtil.getPrimaryKey(
				targetLayout.getPlid(), sourcePortletId);

			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(resourceName);

			for (Role role : roles) {
				String roleName = role.getName();

				if (roleName.equals(RoleConstants.ADMINISTRATOR) ||
					(!targetGroup.isLayoutSetPrototype() &&
					 targetLayout.isPrivateLayout() &&
					 roleName.equals(RoleConstants.GUEST))) {

					continue;
				}

				List<String> actions =
					_resourcePermissionLocalService.
						getAvailableResourcePermissionActionIds(
							targetLayout.getCompanyId(), resourceName,
							ResourceConstants.SCOPE_INDIVIDUAL,
							sourceResourcePrimKey, role.getRoleId(), actionIds);

				_resourcePermissionLocalService.setResourcePermissions(
					targetLayout.getCompanyId(), resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, targetResourcePrimKey,
					role.getRoleId(), actions.toArray(new String[0]));
			}
		}
	}

	@Override
	public void copyPortletSetups(Layout sourceLayout, Layout targetLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutTypePortlet =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> sourcePortletIds = ListUtil.toList(
			sourceLayoutTypePortlet.getAllPortlets(),
			Portlet.PORTLET_ID_ACCESSOR);

		for (String sourcePortletId : sourcePortletIds) {
			PortletPreferences sourcePortletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					sourceLayout, sourcePortletId, null);

			PortletPreferencesImpl sourcePortletPreferencesImpl =
				(PortletPreferencesImpl)sourcePortletPreferences;

			PortletPreferences targetPortletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					targetLayout, sourcePortletId, null);

			PortletPreferencesImpl targetPortletPreferencesImpl =
				(PortletPreferencesImpl)targetPortletPreferences;

			_portletPreferencesLocalService.updatePreferences(
				targetPortletPreferencesImpl.getOwnerId(),
				targetPortletPreferencesImpl.getOwnerType(),
				targetPortletPreferencesImpl.getPlid(), sourcePortletId,
				sourcePortletPreferences);

			if ((sourcePortletPreferencesImpl.getOwnerId() !=
					PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
				(sourcePortletPreferencesImpl.getOwnerType() !=
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) {

				sourcePortletPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						sourceLayout, sourcePortletId);

				targetPortletPreferences =
					PortletPreferencesFactoryUtil.getLayoutPortletSetup(
						targetLayout, sourcePortletId);

				targetPortletPreferencesImpl =
					(PortletPreferencesImpl)targetPortletPreferences;

				_portletPreferencesLocalService.updatePreferences(
					targetPortletPreferencesImpl.getOwnerId(),
					targetPortletPreferencesImpl.getOwnerType(),
					targetPortletPreferencesImpl.getPlid(), sourcePortletId,
					sourcePortletPreferences);
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			_updateLayoutScopes(
				serviceContext.getUserId(), sourceLayout, targetLayout,
				sourcePortletPreferences, targetPortletPreferences,
				sourcePortletId, serviceContext.getLanguageId());
		}
	}

	@Override
	public void executeLayoutSetPrototypeSync(
			long layoutSetPrototypeId, long userId)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
				layoutSetPrototypeId);

		if (layoutSetPrototype == null) {
			return;
		}

		for (LayoutSet layoutSet :
				_layoutSetLocalService.getLayoutSetsByLayoutSetPrototypeUuid(
					layoutSetPrototype.getUuid())) {

			try {
				mergeLayoutSetPrototypeLayouts(layoutSet.getGroup(), layoutSet);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to start site template sync for layout set " +
						layoutSet.getLayoutSetId(),
					exception);
			}
		}

		// TODO LPD-82108 AC9: hook a BackgroundTaskCompleteListener on the
		// per-site LAYOUT_SET_PROTOTYPE_MERGE_BACKGROUND_TASK_EXECUTOR tasks
		// queued above to aggregate results and notify userId with one of:
		// "Sync of [name] Site Template Finished Successfully."
		// "Sync of [name] Site Template Finished with Errors."
		// "Sync of [name] Site Template Failed and the process did not Finish."

	}

	/**
	 * Returns <code>true</code> if the linked site template can be merged into
	 * the layout set. This method checks the current number of merge fail
	 * attempts stored for the linked site template and, if greater than the
	 * merge fail threshold, will return <code>false</code>.
	 *
	 * @param  group the site template's group, which is about to be merged into
	 *         the layout set
	 * @param  layoutSet the site in which the site template is attempting to
	 *         merge into
	 * @return <code>true</code> if the linked site template can be merged into
	 *         the layout set; <code>false</code> otherwise
	 */
	@Override
	public boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException {

		if (!layoutSet.isLayoutSetPrototypeLinkActive() ||
			group.isLayoutPrototype() || group.isLayoutSetPrototype()) {

			return false;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		LayoutSet layoutSetPrototypeLayoutSet =
			layoutSetPrototype.getLayoutSet();

		UnicodeProperties layoutSetPrototypeLayoutSetSettingsUnicodeProperties =
			layoutSetPrototypeLayoutSet.getSettingsProperties();

		int mergeFailCount = GetterUtil.getInteger(
			layoutSetPrototypeLayoutSetSettingsUnicodeProperties.getProperty(
				MERGE_FAIL_COUNT));

		if (mergeFailCount >
				PropsValues.LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Merge not performed because the fail threshold was ",
						"reached for layoutSetPrototypeId ",
						layoutSetPrototype.getLayoutSetPrototypeId(),
						" and layoutId ",
						layoutSetPrototypeLayoutSet.getLayoutSetId(),
						". Update the count in the database to try again."));
			}

			return false;
		}

		return true;
	}

	@Override
	public void mergeLayoutSetPrototypeLayouts(Group group, LayoutSet layoutSet)
		throws Exception {

		if (MergeLayoutPrototypesThreadLocal.isSkipMerge()) {
			return;
		}

		MergeLayoutPrototypesThreadLocal.setSkipMerge(true);

		layoutSet = _layoutSetLocalService.fetchLayoutSet(
			layoutSet.getLayoutSetId());

		if (!isLayoutSetMergeable(group, layoutSet)) {
			return;
		}

		LayoutSetPrototype layoutSetPrototype =
			_layoutSetPrototypeLocalService.
				getLayoutSetPrototypeByUuidAndCompanyId(
					layoutSet.getLayoutSetPrototypeUuid(),
					layoutSet.getCompanyId());

		mergeLayoutSetPrototypeLayoutsInBackground(
			layoutSetPrototype, layoutSet);
	}

	@Override
	public void removeMergeFailFriendlyURLLayouts(LayoutSet layoutSet)
		throws PortalException {

		UnicodeProperties settingsUnicodeProperties =
			layoutSet.getSettingsProperties();

		if (settingsUnicodeProperties.containsKey(
				MERGE_FAIL_FRIENDLY_URL_LAYOUTS)) {

			settingsUnicodeProperties.remove(MERGE_FAIL_FRIENDLY_URL_LAYOUTS);

			_layoutSetLocalService.updateLayoutSet(layoutSet);
		}
	}

	@Override
	public void updateLayoutSetPrototypesLinks(
			Group group, long publicLayoutSetPrototypeId,
			long privateLayoutSetPrototypeId,
			boolean publicLayoutSetPrototypeLinkEnabled,
			boolean privateLayoutSetPrototypeLinkEnabled)
		throws Exception {

		updateLayoutSetPrototypeLink(
			group.getGroupId(), true, privateLayoutSetPrototypeId,
			privateLayoutSetPrototypeLinkEnabled);
		updateLayoutSetPrototypeLink(
			group.getGroupId(), false, publicLayoutSetPrototypeId,
			publicLayoutSetPrototypeLinkEnabled);
	}

	protected void deleteUnreferencedPortlets(
			List<String> targetLayoutPortletIds, Layout targetLayout,
			Layout sourceLayout)
		throws Exception {

		LayoutTypePortlet sourceLayoutType =
			(LayoutTypePortlet)sourceLayout.getLayoutType();

		List<String> unreferencedPortletIds = new ArrayList<>(
			targetLayoutPortletIds);

		unreferencedPortletIds.removeAll(sourceLayoutType.getPortletIds());

		_portletLocalService.deletePortlets(
			targetLayout.getCompanyId(),
			unreferencedPortletIds.toArray(new String[0]),
			targetLayout.getPlid());
	}

	protected File exportLayoutSetPrototype(
		User user, LayoutSetPrototype layoutSetPrototype,
		Map<String, String[]> parameterMap, String cacheFileName) {

		File cacheFile = null;

		if (cacheFileName != null) {
			cacheFile = new File(cacheFileName);

			if (cacheFile.exists()) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Using cached layout set prototype LAR file " +
							cacheFile.getAbsolutePath());
				}

				return cacheFile;
			}
		}

		long layoutSetPrototypeGroupId = 0;

		try {
			layoutSetPrototypeGroupId = layoutSetPrototype.getGroupId();
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to get groupId for layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				portalException);

			return null;
		}

		List<Layout> layoutSetPrototypeLayouts = _layoutLocalService.getLayouts(
			layoutSetPrototypeGroupId, true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototypeGroupId, true,
					_exportImportHelper.getLayoutIds(layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add draft export-import configuration",
				portalException);

			return null;
		}

		File file = null;

		try {
			file = _exportImportLocalService.exportLayoutsAsFile(
				exportImportConfiguration);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to export layout set prototype " +
					layoutSetPrototype.getLayoutSetPrototypeId(),
				portalException);

			return null;
		}

		if (cacheFile == null) {
			return file;
		}

		try {
			FileUtil.copyFile(file, cacheFile);

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Copied ", file.getAbsolutePath(), " to ",
						cacheFile.getAbsolutePath()));
			}
		}
		catch (Exception exception) {
			_log.error(
				StringBundler.concat(
					"Unable to copy file ", file.getAbsolutePath(), " to ",
					cacheFile.getAbsolutePath()),
				exception);
		}

		return cacheFile;
	}

	protected Map<String, String[]> getLayoutSetPrototypesParameters() {
		return LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR}
		).put(
			PortletDataHandlerKeys.DELETE_LAYOUTS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.FAVICON,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.IGNORE_LAST_PUBLISH_DATE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_LINK_ENABLED,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			new String[] {
				PortletDataHandlerKeys.
					LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE
			}
		).put(
			PortletDataHandlerKeys.LOGO, new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.THEME_REFERENCE,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.UPDATE_LAST_PUBLISH_DATE,
			new String[] {Boolean.FALSE.toString()}
		).put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID}
		).build();
	}

	protected void importLayoutSetPrototype(
			LayoutSetPrototype layoutSetPrototype, long groupId,
			boolean privateLayout, Map<String, String[]> parameterMap)
		throws PortalException {

		User user = _userLocalService.getGuestUser(
			layoutSetPrototype.getCompanyId());

		parameterMap.put(
			"layoutSetPrototypeId",
			new String[] {
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId())
			});

		File file = exportLayoutSetPrototype(
			user, layoutSetPrototype, parameterMap, null);

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			groupId, privateLayout);

		if (file == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping import of layout set prototype ",
						layoutSetPrototype.getUuid(), " (mvccVersion ",
						layoutSetPrototype.getMvccVersion(), ") to layout set ",
						layoutSet.getLayoutSetId(), " (mvccVersion ",
						layoutSet.getMvccVersion(), ")"));
			}

			return;
		}

		removeMergeFailFriendlyURLLayouts(layoutSet);

		Map<String, Serializable> importLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildImportLayoutSettingsMap(
					user.getUserId(), groupId, privateLayout, null,
					parameterMap, user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.addExportImportConfiguration(
				user.getUserId(), groupId, StringPool.BLANK, StringPool.BLANK,
				ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
				importLayoutSettingsMap, WorkflowConstants.STATUS_DRAFT,
				new ServiceContext());

		_exportImportLocalService.importLayoutSetPrototypeInBackground(
			user.getUserId(), exportImportConfiguration, file);
	}

	protected boolean isLayoutSetPrototypeMergeBackgroundTaskExists(
			LayoutSetPrototype layoutSetPrototype, LayoutSet layoutSet)
		throws PortalException {

		List<BackgroundTask> incompleteBackgroundTasks =
			_backgroundTaskManager.getBackgroundTasks(
				layoutSet.getGroupId(),
				BackgroundTaskExecutorNames.
					LAYOUT_SET_PROTOTYPE_MERGE_BACKGROUND_TASK_EXECUTOR,
				false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				BackgroundTaskCreateDateComparator.getInstance(false));

		for (BackgroundTask incompleteBackgroundTask :
				incompleteBackgroundTasks) {

			long exportImportConfigurationId = MapUtil.getLong(
				incompleteBackgroundTask.getTaskContextMap(),
				"exportImportConfigurationId");

			ExportImportConfiguration exportImportConfiguration =
				_exportImportConfigurationLocalService.
					fetchExportImportConfiguration(exportImportConfigurationId);

			if (exportImportConfiguration != null) {
				Map<String, Serializable> settingsMap =
					exportImportConfiguration.getSettingsMap();

				Map<String, String[]> parameterMap =
					(Map<String, String[]>)settingsMap.get("parameterMap");

				long layoutSetId = MapUtil.getLong(parameterMap, "layoutSetId");

				if ((layoutSetId == layoutSet.getLayoutSetId()) &&
					(incompleteBackgroundTask.getStatus() !=
						BackgroundTaskConstants.STATUS_IN_PROGRESS)) {

					return true;
				}
			}
		}

		return false;
	}

	protected void mergeLayoutSetPrototypeLayoutsInBackground(
			LayoutSetPrototype layoutSetPrototype, LayoutSet layoutSet)
		throws PortalException {

		if (ExportImportThreadLocal.isExportInProcess() ||
			ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			return;
		}

		if (isLayoutSetPrototypeMergeBackgroundTaskExists(
				layoutSetPrototype, layoutSet)) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Layout set prototype merge is in progress for layout " +
						"set " + layoutSet.getLayoutSetId());
			}

			return;
		}

		Map<String, String[]> parameterMap = getLayoutSetPrototypesParameters();

		parameterMap.put(
			PortletDataHandlerKeys.LAYOUT_SET_PRIVATE_LAYOUT,
			new String[] {String.valueOf(layoutSet.isPrivateLayout())});
		parameterMap.put(
			"layoutSetId",
			new String[] {String.valueOf(layoutSet.getLayoutSetId())});
		parameterMap.put(
			"layoutSetPrototypeId",
			new String[] {
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId())
			});

		User user = _userLocalService.getDefaultUser(layoutSet.getCompanyId());

		List<Layout> layoutSetPrototypeLayouts = _layoutLocalService.getLayouts(
			layoutSetPrototype.getGroupId(), true);

		Map<String, Serializable> exportLayoutSettingsMap =
			ExportImportConfigurationSettingsMapFactoryUtil.
				buildExportLayoutSettingsMap(
					user, layoutSetPrototype.getGroupId(), true,
					_exportImportHelper.getLayoutIds(layoutSetPrototypeLayouts),
					parameterMap);

		ExportImportConfiguration exportImportConfiguration = null;

		try {
			exportImportConfiguration =
				_exportImportConfigurationLocalService.
					addDraftExportImportConfiguration(
						user.getUserId(),
						ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
						exportLayoutSettingsMap);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to add draft export-import configuration",
				portalException);

			return;
		}

		_exportImportLocalService.mergeLayoutSetPrototypeInBackground(
			user.getUserId(), layoutSet.getGroupId(),
			exportImportConfiguration);
	}

	protected void updateLayoutSetPrototypeLink(
			long groupId, boolean privateLayout, long layoutSetPrototypeId,
			boolean layoutSetPrototypeLinkEnabled)
		throws Exception {

		String layoutSetPrototypeUuid = null;

		if (layoutSetPrototypeId > 0) {
			LayoutSetPrototype layoutSetPrototype =
				_layoutSetPrototypeLocalService.fetchLayoutSetPrototype(
					layoutSetPrototypeId);

			if (layoutSetPrototype != null) {
				layoutSetPrototypeUuid = layoutSetPrototype.getUuid();

				// Merge without enabling the link

				if (!layoutSetPrototypeLinkEnabled &&
					(layoutSetPrototypeId > 0)) {

					boolean mergeLayoutPrototypesThreadLocalInProgress =
						MergeLayoutPrototypesThreadLocal.isInProgress();

					try {
						MergeLayoutPrototypesThreadLocal.setInProgress(true);

						importLayoutSetPrototype(
							layoutSetPrototype, groupId, privateLayout,
							getLayoutSetPrototypesParameters());
					}
					finally {
						MergeLayoutPrototypesThreadLocal.setInProgress(
							mergeLayoutPrototypesThreadLocalInProgress);
					}
				}
			}
		}

		_layoutSetService.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);

		_layoutLocalService.updatePriorities(groupId, privateLayout);
	}

	private void _updateLayoutScopes(
			long userId, Layout sourceLayout, Layout targetLayout,
			PortletPreferences sourcePortletPreferences,
			PortletPreferences targetPortletPreferences, String sourcePortletId,
			String languageId)
		throws Exception {

		String scopeType = GetterUtil.getString(
			sourcePortletPreferences.getValue("lfrScopeType", null));

		if (Validator.isNull(scopeType) || !scopeType.equals("layout")) {
			return;
		}

		Layout targetScopeLayout =
			_layoutLocalService.getLayoutByUuidAndGroupId(
				targetLayout.getUuid(), targetLayout.getGroupId(),
				targetLayout.isPrivateLayout());

		if (!targetScopeLayout.hasScopeGroup()) {
			_groupLocalService.addGroup(
				StringPool.BLANK, userId,
				GroupConstants.DEFAULT_PARENT_GROUP_ID, Layout.class.getName(),
				targetLayout.getPlid(), GroupConstants.DEFAULT_LIVE_GROUP_ID,
				targetLayout.getNameMap(), null, 0, null, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
				false, true, null);
		}

		String newPortletTitle = _portal.getNewPortletTitle(
			_portal.getPortletTitle(
				PortletIdCodec.decodePortletName(sourcePortletId), languageId),
			String.valueOf(sourceLayout.getLayoutId()),
			targetLayout.getName(languageId));

		targetPortletPreferences.setValue(
			"groupId", String.valueOf(targetLayout.getGroupId()));
		targetPortletPreferences.setValue("lfrScopeType", "layout");
		targetPortletPreferences.setValue(
			"lfrScopeLayoutUuid", targetLayout.getUuid());
		targetPortletPreferences.setValue(
			"portletSetupTitle_" + languageId, newPortletTitle);
		targetPortletPreferences.setValue(
			"portletSetupUseCustomTitle", Boolean.TRUE.toString());

		targetPortletPreferences.store();
	}

	private static final Log _log = LogFactoryUtil.getLog(SitesImpl.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private LayoutSetService _layoutSetService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}