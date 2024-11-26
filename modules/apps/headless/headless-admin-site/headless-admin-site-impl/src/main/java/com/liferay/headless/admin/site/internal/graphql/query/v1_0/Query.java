/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.graphql.query.v1_0;

import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.dto.v1_0.FragmentComposition;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.dto.v1_0.PageExperience;
import com.liferay.headless.admin.site.dto.v1_0.PageRule;
import com.liferay.headless.admin.site.dto.v1_0.PageRuleAction;
import com.liferay.headless.admin.site.dto.v1_0.PageRuleCondition;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateFolderResource;
import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.FragmentCompositionResource;
import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;
import com.liferay.headless.admin.site.resource.v1_0.MasterPageResource;
import com.liferay.headless.admin.site.resource.v1_0.PageElementResource;
import com.liferay.headless.admin.site.resource.v1_0.PageExperienceResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleActionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleConditionResource;
import com.liferay.headless.admin.site.resource.v1_0.PageRuleResource;
import com.liferay.headless.admin.site.resource.v1_0.PageSpecificationResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateResource;
import com.liferay.headless.admin.site.resource.v1_0.PageTemplateSetResource;
import com.liferay.headless.admin.site.resource.v1_0.SitePageResource;
import com.liferay.headless.admin.site.resource.v1_0.UtilityPageResource;
import com.liferay.headless.admin.site.resource.v1_0.WidgetPageWidgetInstanceResource;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.aggregation.Facet;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.ComponentServiceObjects;

/**
 * @author Rubén Pulido
 * @generated
 */
@Generated("")
public class Query {

	public static void setDisplayPageTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<DisplayPageTemplateResource>
			displayPageTemplateResourceComponentServiceObjects) {

		_displayPageTemplateResourceComponentServiceObjects =
			displayPageTemplateResourceComponentServiceObjects;
	}

	public static void
		setDisplayPageTemplateFolderResourceComponentServiceObjects(
			ComponentServiceObjects<DisplayPageTemplateFolderResource>
				displayPageTemplateFolderResourceComponentServiceObjects) {

		_displayPageTemplateFolderResourceComponentServiceObjects =
			displayPageTemplateFolderResourceComponentServiceObjects;
	}

	public static void setFragmentCompositionResourceComponentServiceObjects(
		ComponentServiceObjects<FragmentCompositionResource>
			fragmentCompositionResourceComponentServiceObjects) {

		_fragmentCompositionResourceComponentServiceObjects =
			fragmentCompositionResourceComponentServiceObjects;
	}

	public static void setFriendlyUrlHistoryResourceComponentServiceObjects(
		ComponentServiceObjects<FriendlyUrlHistoryResource>
			friendlyUrlHistoryResourceComponentServiceObjects) {

		_friendlyUrlHistoryResourceComponentServiceObjects =
			friendlyUrlHistoryResourceComponentServiceObjects;
	}

	public static void setMasterPageResourceComponentServiceObjects(
		ComponentServiceObjects<MasterPageResource>
			masterPageResourceComponentServiceObjects) {

		_masterPageResourceComponentServiceObjects =
			masterPageResourceComponentServiceObjects;
	}

	public static void setPageElementResourceComponentServiceObjects(
		ComponentServiceObjects<PageElementResource>
			pageElementResourceComponentServiceObjects) {

		_pageElementResourceComponentServiceObjects =
			pageElementResourceComponentServiceObjects;
	}

	public static void setPageExperienceResourceComponentServiceObjects(
		ComponentServiceObjects<PageExperienceResource>
			pageExperienceResourceComponentServiceObjects) {

		_pageExperienceResourceComponentServiceObjects =
			pageExperienceResourceComponentServiceObjects;
	}

	public static void setPageRuleResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleResource>
			pageRuleResourceComponentServiceObjects) {

		_pageRuleResourceComponentServiceObjects =
			pageRuleResourceComponentServiceObjects;
	}

	public static void setPageRuleActionResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleActionResource>
			pageRuleActionResourceComponentServiceObjects) {

		_pageRuleActionResourceComponentServiceObjects =
			pageRuleActionResourceComponentServiceObjects;
	}

	public static void setPageRuleConditionResourceComponentServiceObjects(
		ComponentServiceObjects<PageRuleConditionResource>
			pageRuleConditionResourceComponentServiceObjects) {

		_pageRuleConditionResourceComponentServiceObjects =
			pageRuleConditionResourceComponentServiceObjects;
	}

	public static void setPageSpecificationResourceComponentServiceObjects(
		ComponentServiceObjects<PageSpecificationResource>
			pageSpecificationResourceComponentServiceObjects) {

		_pageSpecificationResourceComponentServiceObjects =
			pageSpecificationResourceComponentServiceObjects;
	}

	public static void setPageTemplateResourceComponentServiceObjects(
		ComponentServiceObjects<PageTemplateResource>
			pageTemplateResourceComponentServiceObjects) {

		_pageTemplateResourceComponentServiceObjects =
			pageTemplateResourceComponentServiceObjects;
	}

	public static void setPageTemplateSetResourceComponentServiceObjects(
		ComponentServiceObjects<PageTemplateSetResource>
			pageTemplateSetResourceComponentServiceObjects) {

		_pageTemplateSetResourceComponentServiceObjects =
			pageTemplateSetResourceComponentServiceObjects;
	}

	public static void setSitePageResourceComponentServiceObjects(
		ComponentServiceObjects<SitePageResource>
			sitePageResourceComponentServiceObjects) {

		_sitePageResourceComponentServiceObjects =
			sitePageResourceComponentServiceObjects;
	}

	public static void setUtilityPageResourceComponentServiceObjects(
		ComponentServiceObjects<UtilityPageResource>
			utilityPageResourceComponentServiceObjects) {

		_utilityPageResourceComponentServiceObjects =
			utilityPageResourceComponentServiceObjects;
	}

	public static void
		setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			ComponentServiceObjects<WidgetPageWidgetInstanceResource>
				widgetPageWidgetInstanceResourceComponentServiceObjects) {

		_widgetPageWidgetInstanceResourceComponentServiceObjects =
			widgetPageWidgetInstanceResourceComponentServiceObjects;
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplates(displayPageTemplateFolderExternalReferenceCode: ___, flatten: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the display page templates within a display page template folder of a site page."
	)
	public DisplayPageTemplatePage
			byExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplates(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
					String displayPageTemplateFolderExternalReferenceCode,
				@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage(
						siteExternalReferenceCode,
						displayPageTemplateFolderExternalReferenceCode,
						flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplates(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the display page templates of the site"
	)
	public DisplayPageTemplatePage byExternalReferenceCodeDisplayPageTemplates(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getBySiteExternalReferenceCodeDisplayPageTemplatesPage(
						siteExternalReferenceCode, search,
						_aggregationBiFunction.apply(
							displayPageTemplateResource, aggregations),
						_filterBiFunction.apply(
							displayPageTemplateResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							displayPageTemplateResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplatePermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplatePage
			byExternalReferenceCodeDisplayPageTemplatePermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplate(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){contentTypeReference, creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, displayPageTemplateSettings, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, key, markedAsDefault, name, pageSpecifications, parentFolder, thumbnail, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific display page template of a site."
	)
	public DisplayPageTemplate byExternalReferenceCodeDisplayPageTemplate(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("displayPageTemplateExternalReferenceCode") String
				displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource ->
				displayPageTemplateResource.
					getBySiteExternalReferenceCodeDisplayPageTemplate(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodeDisplayPageTemplatePermissions(displayPageTemplateExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplatePage
			siteExternalReferenceCodeDisplayPageTemplatePermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateExternalReferenceCode") String
					displayPageTemplateExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateResource -> new DisplayPageTemplatePage(
				displayPageTemplateResource.
					getSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplateFolders(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the display page template folders of the site."
	)
	public DisplayPageTemplateFolderPage
			byExternalReferenceCodeDisplayPageTemplateFolders(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("search") String search,
				@GraphQLName("aggregation") List<String> aggregations,
				@GraphQLName("filter") String filterString,
				@GraphQLName("pageSize") int pageSize,
				@GraphQLName("page") int page,
				@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				new DisplayPageTemplateFolderPage(
					displayPageTemplateFolderResource.
						getBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage(
							siteExternalReferenceCode, search,
							_aggregationBiFunction.apply(
								displayPageTemplateFolderResource,
								aggregations),
							_filterBiFunction.apply(
								displayPageTemplateFolderResource,
								filterString),
							Pagination.of(page, pageSize),
							_sortsBiFunction.apply(
								displayPageTemplateFolderResource,
								sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplateFolderPermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplateFolderPage
			byExternalReferenceCodeDisplayPageTemplateFolderPermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				new DisplayPageTemplateFolderPage(
					displayPageTemplateFolderResource.
						getBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage(
							siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplateFolder(displayPageTemplateFolderExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, description, externalReferenceCode, key, name, parentDisplayPageTemplateFolderExternalReferenceCode, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific display page template folder of a site."
	)
	public DisplayPageTemplateFolder
			byExternalReferenceCodeDisplayPageTemplateFolder(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
					String displayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				displayPageTemplateFolderResource.
					getBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						siteExternalReferenceCode,
						displayPageTemplateFolderExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodeDisplayPageTemplateFolderPermissions(displayPageTemplateFolderExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public DisplayPageTemplateFolderPage
			siteExternalReferenceCodeDisplayPageTemplateFolderPermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateFolderExternalReferenceCode")
					String displayPageTemplateFolderExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects,
			this::_populateResourceContext,
			displayPageTemplateFolderResource ->
				new DisplayPageTemplateFolderPage(
					displayPageTemplateFolderResource.
						getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage(
							siteExternalReferenceCode,
							displayPageTemplateFolderExternalReferenceCode,
							roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeFragmentCompositions(filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the fragment compositions of the site."
	)
	public FragmentCompositionPage byExternalReferenceCodeFragmentCompositions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects,
			this::_populateResourceContext,
			fragmentCompositionResource -> new FragmentCompositionPage(
				fragmentCompositionResource.
					getBySiteExternalReferenceCodeFragmentCompositionsPage(
						siteExternalReferenceCode, search,
						_filterBiFunction.apply(
							fragmentCompositionResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							fragmentCompositionResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeFragmentComposition(fragmentCompositionExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, description, externalReferenceCode, fragmentSetExternalReferenceCode, key, name, pageElement, thumbnail}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific fragment composition of a site."
	)
	public FragmentComposition byExternalReferenceCodeFragmentComposition(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("fragmentCompositionExternalReferenceCode") String
				fragmentCompositionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects,
			this::_populateResourceContext,
			fragmentCompositionResource ->
				fragmentCompositionResource.
					getBySiteExternalReferenceCodeFragmentComposition(
						siteExternalReferenceCode,
						fragmentCompositionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a display page template."
	)
	public FriendlyUrlHistory
			byExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateExternalReferenceCode") String
					displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.
					getBySiteExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePageFriendlyUrlHistory(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a page."
	)
	public FriendlyUrlHistory byExternalReferenceCodeSitePageFriendlyUrlHistory(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.
					getBySiteExternalReferenceCodeSitePageFriendlyUrlHistory(
						siteExternalReferenceCode,
						sitePageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeUtilityPageFriendlyUrlHistory(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){friendlyUrlPath_i18n}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves the history of previously used URLs for a utility page."
	)
	public FriendlyUrlHistory
			byExternalReferenceCodeUtilityPageFriendlyUrlHistory(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("utilityPageExternalReferenceCode") String
					utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects,
			this::_populateResourceContext,
			friendlyUrlHistoryResource ->
				friendlyUrlHistoryResource.
					getBySiteExternalReferenceCodeUtilityPageFriendlyUrlHistory(
						siteExternalReferenceCode,
						utilityPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeMasterPages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the master pages of the site.")
	public MasterPagePage byExternalReferenceCodeMasterPages(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> new MasterPagePage(
				masterPageResource.
					getBySiteExternalReferenceCodeMasterPagesPage(
						siteExternalReferenceCode, search,
						_aggregationBiFunction.apply(
							masterPageResource, aggregations),
						_filterBiFunction.apply(
							masterPageResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							masterPageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeMasterPagePermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public MasterPagePage byExternalReferenceCodeMasterPagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> new MasterPagePage(
				masterPageResource.
					getBySiteExternalReferenceCodeMasterPagePermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeMasterPage(masterPageExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, key, keywordItemExternalReferences, keywords, markedAsDefault, name, pageSpecifications, taxonomyCategories, taxonomyCategoryItemExternalReferences, thumbnail, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific master page of a site.")
	public MasterPage byExternalReferenceCodeMasterPage(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("masterPageExternalReferenceCode") String
				masterPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource ->
				masterPageResource.getBySiteExternalReferenceCodeMasterPage(
					siteExternalReferenceCode,
					masterPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodeMasterPagePermissions(masterPageExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public MasterPagePage siteExternalReferenceCodeMasterPagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("masterPageExternalReferenceCode") String
				masterPageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_masterPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			masterPageResource -> new MasterPagePage(
				masterPageResource.
					getSiteSiteExternalReferenceCodeMasterPagePermissionsPage(
						siteExternalReferenceCode,
						masterPageExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageElement(pageElementExternalReferenceCode: ___, siteExternalReferenceCode: ___){definition, externalReferenceCode, pageElements, parentExternalReferenceCode, position, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page element within an experience of a specific page specification of a site page within a site."
	)
	public PageElement byExternalReferenceCodePageElement(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageElementExternalReferenceCode") String
				pageElementExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource ->
				pageElementResource.getBySiteExternalReferenceCodePageElement(
					siteExternalReferenceCode,
					pageElementExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageElementPageElements(flatten: ___, pageElementExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the descendant page elements of a page element within an experience in a page specification of a site page."
	)
	public PageElementPage byExternalReferenceCodePageElementPageElements(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageElementExternalReferenceCode") String
				pageElementExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource -> new PageElementPage(
				pageElementResource.
					getBySiteExternalReferenceCodePageElementPageElementsPage(
						siteExternalReferenceCode,
						pageElementExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageExperiencePageElements(flatten: ___, pageExperienceExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page elements within an experience in a page specification of a site page."
	)
	public PageElementPage byExternalReferenceCodePageExperiencePageElements(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageElementResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageElementResource -> new PageElementPage(
				pageElementResource.
					getBySiteExternalReferenceCodePageExperiencePageElementsPage(
						siteExternalReferenceCode,
						pageExperienceExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageExperience(pageExperienceExternalReferenceCode: ___, siteExternalReferenceCode: ___){externalReferenceCode, key, name_i18n, pageElements, pageRules, priority, segmentExternalReferenceCode, sitePageExternalReferenceCode}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves an experience of a specific page specification of a site page within a site."
	)
	public PageExperience byExternalReferenceCodePageExperience(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageExperienceResource ->
				pageExperienceResource.
					getBySiteExternalReferenceCodePageExperience(
						siteExternalReferenceCode,
						pageExperienceExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageSpecificationPageExperiences(pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the experiences of a page specification."
	)
	public PageExperiencePage
			byExternalReferenceCodePageSpecificationPageExperiences(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("pageSpecificationExternalReferenceCode") String
					pageSpecificationExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageExperienceResource -> new PageExperiencePage(
				pageExperienceResource.
					getBySiteExternalReferenceCodePageSpecificationPageExperiencesPage(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageExperiencePageRules(flatten: ___, pageExperienceExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rules within an experience in a page specification of a site page."
	)
	public PageRulePage byExternalReferenceCodePageExperiencePageRules(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageExperienceExternalReferenceCode") String
				pageExperienceExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleResource -> new PageRulePage(
				pageRuleResource.
					getBySiteExternalReferenceCodePageExperiencePageRulesPage(
						siteExternalReferenceCode,
						pageExperienceExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageRule(pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){conditionType, externalReferenceCode, name, pageRuleActions, pageRuleConditions}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves page rule within an experience of a specific page specification of a site page within a site."
	)
	public PageRule byExternalReferenceCodePageRule(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleExternalReferenceCode") String
				pageRuleExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleResource ->
				pageRuleResource.getBySiteExternalReferenceCodePageRule(
					siteExternalReferenceCode, pageRuleExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageRuleAction(pageRuleActionExternalReferenceCode: ___, siteExternalReferenceCode: ___){action, externalReferenceCode, itemId, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page rule action within a page rule of an experience of a specific page specification of a site page within a site."
	)
	public PageRuleAction byExternalReferenceCodePageRuleAction(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleActionExternalReferenceCode") String
				pageRuleActionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleActionResource ->
				pageRuleActionResource.
					getBySiteExternalReferenceCodePageRuleAction(
						siteExternalReferenceCode,
						pageRuleActionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageRulePageRuleActions(flatten: ___, pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rule action actions within an experience in a page specification of a site page."
	)
	public PageRuleActionPage byExternalReferenceCodePageRulePageRuleActions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleExternalReferenceCode") String
				pageRuleExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleActionResource -> new PageRuleActionPage(
				pageRuleActionResource.
					getBySiteExternalReferenceCodePageRulePageRuleActionsPage(
						siteExternalReferenceCode,
						pageRuleExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageRuleCondition(pageRuleConditionExternalReferenceCode: ___, siteExternalReferenceCode: ___){condition, externalReferenceCode, type, value}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page rule condition within a page rule of an experience of a specific page specification of a site page within a site."
	)
	public PageRuleCondition byExternalReferenceCodePageRuleCondition(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageRuleConditionExternalReferenceCode") String
				pageRuleConditionExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleConditionResource ->
				pageRuleConditionResource.
					getBySiteExternalReferenceCodePageRuleCondition(
						siteExternalReferenceCode,
						pageRuleConditionExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageRulePageRuleConditions(flatten: ___, pageRuleExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page rule condition conditions within an experience in a page specification of a site page."
	)
	public PageRuleConditionPage
			byExternalReferenceCodePageRulePageRuleConditions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("pageRuleExternalReferenceCode") String
					pageRuleExternalReferenceCode,
				@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageRuleConditionResource -> new PageRuleConditionPage(
				pageRuleConditionResource.
					getBySiteExternalReferenceCodePageRulePageRuleConditionsPage(
						siteExternalReferenceCode,
						pageRuleExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeDisplayPageTemplatePageSpecifications(displayPageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a display page template."
	)
	public PageSpecificationPage
			byExternalReferenceCodeDisplayPageTemplatePageSpecifications(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("displayPageTemplateExternalReferenceCode") String
					displayPageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getBySiteExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage(
						siteExternalReferenceCode,
						displayPageTemplateExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeMasterPagePageSpecifications(masterPageExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a master page."
	)
	public PageSpecificationPage
			byExternalReferenceCodeMasterPagePageSpecifications(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("masterPageExternalReferenceCode") String
					masterPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getBySiteExternalReferenceCodeMasterPagePageSpecificationsPage(
						siteExternalReferenceCode,
						masterPageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageSpecification(pageSpecificationExternalReferenceCode: ___, siteExternalReferenceCode: ___){externalReferenceCode, settings, status, type}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a page specification of a site page."
	)
	public PageSpecification byExternalReferenceCodePageSpecification(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageSpecificationExternalReferenceCode") String
				pageSpecificationExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource ->
				pageSpecificationResource.
					getBySiteExternalReferenceCodePageSpecification(
						siteExternalReferenceCode,
						pageSpecificationExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplatePageSpecifications(pageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a page template."
	)
	public PageSpecificationPage
			byExternalReferenceCodePageTemplatePageSpecifications(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("pageTemplateExternalReferenceCode") String
					pageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getBySiteExternalReferenceCodePageTemplatePageSpecificationsPage(
						siteExternalReferenceCode,
						pageTemplateExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePagePageSpecifications(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a site page."
	)
	public PageSpecificationPage
			byExternalReferenceCodeSitePagePageSpecifications(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("sitePageExternalReferenceCode") String
					sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getBySiteExternalReferenceCodeSitePagePageSpecificationsPage(
						siteExternalReferenceCode,
						sitePageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeUtilityPagePageSpecifications(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page specifications of a utility page."
	)
	public PageSpecificationPage
			byExternalReferenceCodeUtilityPagePageSpecifications(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("utilityPageExternalReferenceCode") String
					utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageSpecificationResource -> new PageSpecificationPage(
				pageSpecificationResource.
					getBySiteExternalReferenceCodeUtilityPagePageSpecificationsPage(
						siteExternalReferenceCode,
						utilityPageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplateSetPageTemplates(flatten: ___, pageTemplateSetExternalReferenceCode: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the page templates within a page template set of a site page."
	)
	public PageTemplatePage byExternalReferenceCodePageTemplateSetPageTemplates(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateSetExternalReferenceCode") String
				pageTemplateSetExternalReferenceCode,
			@GraphQLName("flatten") Boolean flatten)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.
					getBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage(
						siteExternalReferenceCode,
						pageTemplateSetExternalReferenceCode, flatten)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplates(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the page templates of the site")
	public PageTemplatePage byExternalReferenceCodePageTemplates(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.
					getBySiteExternalReferenceCodePageTemplatesPage(
						siteExternalReferenceCode, search,
						_aggregationBiFunction.apply(
							pageTemplateResource, aggregations),
						_filterBiFunction.apply(
							pageTemplateResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							pageTemplateResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplatePermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplatePage byExternalReferenceCodePageTemplatePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.
					getBySiteExternalReferenceCodePageTemplatePermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplate(pageTemplateExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, key, keywordItemExternalReferences, keywords, name, pageSpecifications, pageTemplateSet, pageTemplateSettings, taxonomyCategories, taxonomyCategoryItemExternalReferences, type, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific page template of a site.")
	public PageTemplate byExternalReferenceCodePageTemplate(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateExternalReferenceCode") String
				pageTemplateExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource ->
				pageTemplateResource.getBySiteExternalReferenceCodePageTemplate(
					siteExternalReferenceCode,
					pageTemplateExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodePageTemplatePermissions(pageTemplateExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplatePage siteExternalReferenceCodePageTemplatePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateExternalReferenceCode") String
				pageTemplateExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateResource -> new PageTemplatePage(
				pageTemplateResource.
					getSiteSiteExternalReferenceCodePageTemplatePermissionsPage(
						siteExternalReferenceCode,
						pageTemplateExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplateSets(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the page template sets of the site")
	public PageTemplateSetPage byExternalReferenceCodePageTemplateSets(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource -> new PageTemplateSetPage(
				pageTemplateSetResource.
					getBySiteExternalReferenceCodePageTemplateSetsPage(
						siteExternalReferenceCode, search,
						_aggregationBiFunction.apply(
							pageTemplateSetResource, aggregations),
						_filterBiFunction.apply(
							pageTemplateSetResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							pageTemplateSetResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplateSetPermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplateSetPage
			byExternalReferenceCodePageTemplateSetPermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource -> new PageTemplateSetPage(
				pageTemplateSetResource.
					getBySiteExternalReferenceCodePageTemplateSetPermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodePageTemplateSet(pageTemplateSetExternalReferenceCode: ___, siteExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, description, externalReferenceCode, key, name, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a specific page template set of a site."
	)
	public PageTemplateSet byExternalReferenceCodePageTemplateSet(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("pageTemplateSetExternalReferenceCode") String
				pageTemplateSetExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource ->
				pageTemplateSetResource.
					getBySiteExternalReferenceCodePageTemplateSet(
						siteExternalReferenceCode,
						pageTemplateSetExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodePageTemplateSetPermissions(pageTemplateSetExternalReferenceCode: ___, roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public PageTemplateSetPage
			siteExternalReferenceCodePageTemplateSetPermissions(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("pageTemplateSetExternalReferenceCode") String
					pageTemplateSetExternalReferenceCode,
				@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects,
			this::_populateResourceContext,
			pageTemplateSetResource -> new PageTemplateSetPage(
				pageTemplateSetResource.
					getSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage(
						siteExternalReferenceCode,
						pageTemplateSetExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the public pages of the site")
	public SitePagePage byExternalReferenceCodeSitePages(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> new SitePagePage(
				sitePageResource.getBySiteExternalReferenceCodeSitePagesPage(
					siteExternalReferenceCode, search,
					_aggregationBiFunction.apply(
						sitePageResource, aggregations),
					_filterBiFunction.apply(sitePageResource, filterString),
					Pagination.of(page, pageSize),
					_sortsBiFunction.apply(sitePageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePagePermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SitePagePage byExternalReferenceCodeSitePagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> new SitePagePage(
				sitePageResource.
					getBySiteExternalReferenceCodeSitePagePermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePage(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){availableLanguages, creator, creatorExternalReferenceCode, customFields, dateCreated, dateModified, datePublished, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, keywordItemExternalReferences, keywords, name_i18n, pageSettings, pageSpecifications, parentSitePageExternalReferenceCode, siteExternalReferenceCode, taxonomyCategories, taxonomyCategoryItemExternalReferences, type, uuid, viewableBy}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific public page of a site.")
	public SitePage byExternalReferenceCodeSitePage(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource ->
				sitePageResource.getBySiteExternalReferenceCodeSitePage(
					siteExternalReferenceCode, sitePageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodeSitePagePermissions(roleNames: ___, siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public SitePagePage siteExternalReferenceCodeSitePagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("sitePageExternalReferenceCode") String
				sitePageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_sitePageResourceComponentServiceObjects,
			this::_populateResourceContext,
			sitePageResource -> new SitePagePage(
				sitePageResource.
					getSiteSiteExternalReferenceCodeSitePagePermissionsPage(
						siteExternalReferenceCode,
						sitePageExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeUtilityPages(aggregation: ___, filter: ___, page: ___, pageSize: ___, search: ___, siteExternalReferenceCode: ___, sorts: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves the utility pages of the site.")
	public UtilityPagePage byExternalReferenceCodeUtilityPages(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("search") String search,
			@GraphQLName("aggregation") List<String> aggregations,
			@GraphQLName("filter") String filterString,
			@GraphQLName("pageSize") int pageSize,
			@GraphQLName("page") int page,
			@GraphQLName("sort") String sortsString)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> new UtilityPagePage(
				utilityPageResource.
					getBySiteExternalReferenceCodeUtilityPagesPage(
						siteExternalReferenceCode, search,
						_aggregationBiFunction.apply(
							utilityPageResource, aggregations),
						_filterBiFunction.apply(
							utilityPageResource, filterString),
						Pagination.of(page, pageSize),
						_sortsBiFunction.apply(
							utilityPageResource, sortsString))));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeUtilityPagePermissions(roleNames: ___, siteExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UtilityPagePage byExternalReferenceCodeUtilityPagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> new UtilityPagePage(
				utilityPageResource.
					getBySiteExternalReferenceCodeUtilityPagePermissionsPage(
						siteExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeUtilityPage(siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){creator, creatorExternalReferenceCode, dateCreated, dateModified, datePublished, externalReferenceCode, friendlyUrlHistory, friendlyUrlPath_i18n, markedAsDefault, name, pageSpecifications, thumbnail, type, utilityPageSettings, uuid}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(description = "Retrieves a specific utility page of a site.")
	public UtilityPage byExternalReferenceCodeUtilityPage(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource ->
				utilityPageResource.getBySiteExternalReferenceCodeUtilityPage(
					siteExternalReferenceCode,
					utilityPageExternalReferenceCode));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {siteExternalReferenceCodeUtilityPagePermissions(roleNames: ___, siteExternalReferenceCode: ___, utilityPageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField
	public UtilityPagePage siteExternalReferenceCodeUtilityPagePermissions(
			@GraphQLName("siteExternalReferenceCode") String
				siteExternalReferenceCode,
			@GraphQLName("utilityPageExternalReferenceCode") String
				utilityPageExternalReferenceCode,
			@GraphQLName("roleNames") String roleNames)
		throws Exception {

		return _applyComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects,
			this::_populateResourceContext,
			utilityPageResource -> new UtilityPagePage(
				utilityPageResource.
					getSiteSiteExternalReferenceCodeUtilityPagePermissionsPage(
						siteExternalReferenceCode,
						utilityPageExternalReferenceCode, roleNames)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeSitePageWidgetInstances(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___){items {__}, page, pageSize, totalCount}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves all the widget instances of a widget page."
	)
	public WidgetPageWidgetInstancePage
			byExternalReferenceCodeSitePageWidgetInstances(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("sitePageExternalReferenceCode") String
					sitePageExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			widgetPageWidgetInstanceResource ->
				new WidgetPageWidgetInstancePage(
					widgetPageWidgetInstanceResource.
						getBySiteExternalReferenceCodeSitePageWidgetInstancesPage(
							siteExternalReferenceCode,
							sitePageExternalReferenceCode)));
	}

	/**
	 * Invoke this method with the command line:
	 *
	 * curl -H 'Content-Type: text/plain; charset=utf-8' -X 'POST' 'http://localhost:8080/o/graphql' -d $'{"query": "query {byExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(siteExternalReferenceCode: ___, sitePageExternalReferenceCode: ___, widgetInstanceExternalReferenceCode: ___){widgetName, parentSectionId, widgetInstanceId, widgetLookAndFeelConfig, widgetPermissions, parentWidgetInstanceExternalReferenceCode, position, widgetConfig, externalReferenceCode}}"}' -u 'test@liferay.com:test'
	 */
	@GraphQLField(
		description = "Retrieves a widget instance of a widget page or widget page template within a site."
	)
	public WidgetPageWidgetInstance
			byExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				@GraphQLName("siteExternalReferenceCode") String
					siteExternalReferenceCode,
				@GraphQLName("sitePageExternalReferenceCode") String
					sitePageExternalReferenceCode,
				@GraphQLName("widgetInstanceExternalReferenceCode") String
					widgetInstanceExternalReferenceCode)
		throws Exception {

		return _applyComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects,
			this::_populateResourceContext,
			widgetPageWidgetInstanceResource ->
				widgetPageWidgetInstanceResource.
					getBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
						siteExternalReferenceCode,
						sitePageExternalReferenceCode,
						widgetInstanceExternalReferenceCode));
	}

	@GraphQLName("DisplayPageTemplatePage")
	public class DisplayPageTemplatePage {

		public DisplayPageTemplatePage(Page displayPageTemplatePage) {
			actions = displayPageTemplatePage.getActions();

			facets = displayPageTemplatePage.getFacets();

			items = displayPageTemplatePage.getItems();
			lastPage = displayPageTemplatePage.getLastPage();
			page = displayPageTemplatePage.getPage();
			pageSize = displayPageTemplatePage.getPageSize();
			totalCount = displayPageTemplatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<DisplayPageTemplate> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("DisplayPageTemplateFolderPage")
	public class DisplayPageTemplateFolderPage {

		public DisplayPageTemplateFolderPage(
			Page displayPageTemplateFolderPage) {

			actions = displayPageTemplateFolderPage.getActions();

			facets = displayPageTemplateFolderPage.getFacets();

			items = displayPageTemplateFolderPage.getItems();
			lastPage = displayPageTemplateFolderPage.getLastPage();
			page = displayPageTemplateFolderPage.getPage();
			pageSize = displayPageTemplateFolderPage.getPageSize();
			totalCount = displayPageTemplateFolderPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<DisplayPageTemplateFolder> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FragmentCompositionPage")
	public class FragmentCompositionPage {

		public FragmentCompositionPage(Page fragmentCompositionPage) {
			actions = fragmentCompositionPage.getActions();

			facets = fragmentCompositionPage.getFacets();

			items = fragmentCompositionPage.getItems();
			lastPage = fragmentCompositionPage.getLastPage();
			page = fragmentCompositionPage.getPage();
			pageSize = fragmentCompositionPage.getPageSize();
			totalCount = fragmentCompositionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<FragmentComposition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("FriendlyUrlHistoryPage")
	public class FriendlyUrlHistoryPage {

		public FriendlyUrlHistoryPage(Page friendlyUrlHistoryPage) {
			actions = friendlyUrlHistoryPage.getActions();

			facets = friendlyUrlHistoryPage.getFacets();

			items = friendlyUrlHistoryPage.getItems();
			lastPage = friendlyUrlHistoryPage.getLastPage();
			page = friendlyUrlHistoryPage.getPage();
			pageSize = friendlyUrlHistoryPage.getPageSize();
			totalCount = friendlyUrlHistoryPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<FriendlyUrlHistory> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("MasterPagePage")
	public class MasterPagePage {

		public MasterPagePage(Page masterPagePage) {
			actions = masterPagePage.getActions();

			facets = masterPagePage.getFacets();

			items = masterPagePage.getItems();
			lastPage = masterPagePage.getLastPage();
			page = masterPagePage.getPage();
			pageSize = masterPagePage.getPageSize();
			totalCount = masterPagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<MasterPage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageElementPage")
	public class PageElementPage {

		public PageElementPage(Page pageElementPage) {
			actions = pageElementPage.getActions();

			facets = pageElementPage.getFacets();

			items = pageElementPage.getItems();
			lastPage = pageElementPage.getLastPage();
			page = pageElementPage.getPage();
			pageSize = pageElementPage.getPageSize();
			totalCount = pageElementPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageElement> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageExperiencePage")
	public class PageExperiencePage {

		public PageExperiencePage(Page pageExperiencePage) {
			actions = pageExperiencePage.getActions();

			facets = pageExperiencePage.getFacets();

			items = pageExperiencePage.getItems();
			lastPage = pageExperiencePage.getLastPage();
			page = pageExperiencePage.getPage();
			pageSize = pageExperiencePage.getPageSize();
			totalCount = pageExperiencePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageExperience> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRulePage")
	public class PageRulePage {

		public PageRulePage(Page pageRulePage) {
			actions = pageRulePage.getActions();

			facets = pageRulePage.getFacets();

			items = pageRulePage.getItems();
			lastPage = pageRulePage.getLastPage();
			page = pageRulePage.getPage();
			pageSize = pageRulePage.getPageSize();
			totalCount = pageRulePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRule> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRuleActionPage")
	public class PageRuleActionPage {

		public PageRuleActionPage(Page pageRuleActionPage) {
			actions = pageRuleActionPage.getActions();

			facets = pageRuleActionPage.getFacets();

			items = pageRuleActionPage.getItems();
			lastPage = pageRuleActionPage.getLastPage();
			page = pageRuleActionPage.getPage();
			pageSize = pageRuleActionPage.getPageSize();
			totalCount = pageRuleActionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRuleAction> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageRuleConditionPage")
	public class PageRuleConditionPage {

		public PageRuleConditionPage(Page pageRuleConditionPage) {
			actions = pageRuleConditionPage.getActions();

			facets = pageRuleConditionPage.getFacets();

			items = pageRuleConditionPage.getItems();
			lastPage = pageRuleConditionPage.getLastPage();
			page = pageRuleConditionPage.getPage();
			pageSize = pageRuleConditionPage.getPageSize();
			totalCount = pageRuleConditionPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageRuleCondition> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageSpecificationPage")
	public class PageSpecificationPage {

		public PageSpecificationPage(Page pageSpecificationPage) {
			actions = pageSpecificationPage.getActions();

			facets = pageSpecificationPage.getFacets();

			items = pageSpecificationPage.getItems();
			lastPage = pageSpecificationPage.getLastPage();
			page = pageSpecificationPage.getPage();
			pageSize = pageSpecificationPage.getPageSize();
			totalCount = pageSpecificationPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageSpecification> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageTemplatePage")
	public class PageTemplatePage {

		public PageTemplatePage(Page pageTemplatePage) {
			actions = pageTemplatePage.getActions();

			facets = pageTemplatePage.getFacets();

			items = pageTemplatePage.getItems();
			lastPage = pageTemplatePage.getLastPage();
			page = pageTemplatePage.getPage();
			pageSize = pageTemplatePage.getPageSize();
			totalCount = pageTemplatePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageTemplate> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("PageTemplateSetPage")
	public class PageTemplateSetPage {

		public PageTemplateSetPage(Page pageTemplateSetPage) {
			actions = pageTemplateSetPage.getActions();

			facets = pageTemplateSetPage.getFacets();

			items = pageTemplateSetPage.getItems();
			lastPage = pageTemplateSetPage.getLastPage();
			page = pageTemplateSetPage.getPage();
			pageSize = pageTemplateSetPage.getPageSize();
			totalCount = pageTemplateSetPage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<PageTemplateSet> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("SitePagePage")
	public class SitePagePage {

		public SitePagePage(Page sitePagePage) {
			actions = sitePagePage.getActions();

			facets = sitePagePage.getFacets();

			items = sitePagePage.getItems();
			lastPage = sitePagePage.getLastPage();
			page = sitePagePage.getPage();
			pageSize = sitePagePage.getPageSize();
			totalCount = sitePagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<SitePage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("UtilityPagePage")
	public class UtilityPagePage {

		public UtilityPagePage(Page utilityPagePage) {
			actions = utilityPagePage.getActions();

			facets = utilityPagePage.getFacets();

			items = utilityPagePage.getItems();
			lastPage = utilityPagePage.getLastPage();
			page = utilityPagePage.getPage();
			pageSize = utilityPagePage.getPageSize();
			totalCount = utilityPagePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<UtilityPage> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	@GraphQLName("WidgetPageWidgetInstancePage")
	public class WidgetPageWidgetInstancePage {

		public WidgetPageWidgetInstancePage(Page widgetPageWidgetInstancePage) {
			actions = widgetPageWidgetInstancePage.getActions();

			facets = widgetPageWidgetInstancePage.getFacets();

			items = widgetPageWidgetInstancePage.getItems();
			lastPage = widgetPageWidgetInstancePage.getLastPage();
			page = widgetPageWidgetInstancePage.getPage();
			pageSize = widgetPageWidgetInstancePage.getPageSize();
			totalCount = widgetPageWidgetInstancePage.getTotalCount();
		}

		@GraphQLField
		protected Map<String, Map<String, String>> actions;

		@GraphQLField
		protected List<Facet> facets;

		@GraphQLField
		protected java.util.Collection<WidgetPageWidgetInstance> items;

		@GraphQLField
		protected long lastPage;

		@GraphQLField
		protected long page;

		@GraphQLField
		protected long pageSize;

		@GraphQLField
		protected long totalCount;

	}

	private <T, R, E1 extends Throwable, E2 extends Throwable> R
			_applyComponentServiceObjects(
				ComponentServiceObjects<T> componentServiceObjects,
				UnsafeConsumer<T, E1> unsafeConsumer,
				UnsafeFunction<T, R, E2> unsafeFunction)
		throws E1, E2 {

		T resource = componentServiceObjects.getService();

		try {
			unsafeConsumer.accept(resource);

			return unsafeFunction.apply(resource);
		}
		finally {
			componentServiceObjects.ungetService(resource);
		}
	}

	private void _populateResourceContext(
			DisplayPageTemplateResource displayPageTemplateResource)
		throws Exception {

		displayPageTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		displayPageTemplateResource.setContextCompany(_company);
		displayPageTemplateResource.setContextHttpServletRequest(
			_httpServletRequest);
		displayPageTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		displayPageTemplateResource.setContextUriInfo(_uriInfo);
		displayPageTemplateResource.setContextUser(_user);
		displayPageTemplateResource.setGroupLocalService(_groupLocalService);
		displayPageTemplateResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			DisplayPageTemplateFolderResource displayPageTemplateFolderResource)
		throws Exception {

		displayPageTemplateFolderResource.setContextAcceptLanguage(
			_acceptLanguage);
		displayPageTemplateFolderResource.setContextCompany(_company);
		displayPageTemplateFolderResource.setContextHttpServletRequest(
			_httpServletRequest);
		displayPageTemplateFolderResource.setContextHttpServletResponse(
			_httpServletResponse);
		displayPageTemplateFolderResource.setContextUriInfo(_uriInfo);
		displayPageTemplateFolderResource.setContextUser(_user);
		displayPageTemplateFolderResource.setGroupLocalService(
			_groupLocalService);
		displayPageTemplateFolderResource.setRoleLocalService(
			_roleLocalService);
	}

	private void _populateResourceContext(
			FragmentCompositionResource fragmentCompositionResource)
		throws Exception {

		fragmentCompositionResource.setContextAcceptLanguage(_acceptLanguage);
		fragmentCompositionResource.setContextCompany(_company);
		fragmentCompositionResource.setContextHttpServletRequest(
			_httpServletRequest);
		fragmentCompositionResource.setContextHttpServletResponse(
			_httpServletResponse);
		fragmentCompositionResource.setContextUriInfo(_uriInfo);
		fragmentCompositionResource.setContextUser(_user);
		fragmentCompositionResource.setGroupLocalService(_groupLocalService);
		fragmentCompositionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			FriendlyUrlHistoryResource friendlyUrlHistoryResource)
		throws Exception {

		friendlyUrlHistoryResource.setContextAcceptLanguage(_acceptLanguage);
		friendlyUrlHistoryResource.setContextCompany(_company);
		friendlyUrlHistoryResource.setContextHttpServletRequest(
			_httpServletRequest);
		friendlyUrlHistoryResource.setContextHttpServletResponse(
			_httpServletResponse);
		friendlyUrlHistoryResource.setContextUriInfo(_uriInfo);
		friendlyUrlHistoryResource.setContextUser(_user);
		friendlyUrlHistoryResource.setGroupLocalService(_groupLocalService);
		friendlyUrlHistoryResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(MasterPageResource masterPageResource)
		throws Exception {

		masterPageResource.setContextAcceptLanguage(_acceptLanguage);
		masterPageResource.setContextCompany(_company);
		masterPageResource.setContextHttpServletRequest(_httpServletRequest);
		masterPageResource.setContextHttpServletResponse(_httpServletResponse);
		masterPageResource.setContextUriInfo(_uriInfo);
		masterPageResource.setContextUser(_user);
		masterPageResource.setGroupLocalService(_groupLocalService);
		masterPageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageElementResource pageElementResource)
		throws Exception {

		pageElementResource.setContextAcceptLanguage(_acceptLanguage);
		pageElementResource.setContextCompany(_company);
		pageElementResource.setContextHttpServletRequest(_httpServletRequest);
		pageElementResource.setContextHttpServletResponse(_httpServletResponse);
		pageElementResource.setContextUriInfo(_uriInfo);
		pageElementResource.setContextUser(_user);
		pageElementResource.setGroupLocalService(_groupLocalService);
		pageElementResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageExperienceResource pageExperienceResource)
		throws Exception {

		pageExperienceResource.setContextAcceptLanguage(_acceptLanguage);
		pageExperienceResource.setContextCompany(_company);
		pageExperienceResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageExperienceResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageExperienceResource.setContextUriInfo(_uriInfo);
		pageExperienceResource.setContextUser(_user);
		pageExperienceResource.setGroupLocalService(_groupLocalService);
		pageExperienceResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(PageRuleResource pageRuleResource)
		throws Exception {

		pageRuleResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleResource.setContextCompany(_company);
		pageRuleResource.setContextHttpServletRequest(_httpServletRequest);
		pageRuleResource.setContextHttpServletResponse(_httpServletResponse);
		pageRuleResource.setContextUriInfo(_uriInfo);
		pageRuleResource.setContextUser(_user);
		pageRuleResource.setGroupLocalService(_groupLocalService);
		pageRuleResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageRuleActionResource pageRuleActionResource)
		throws Exception {

		pageRuleActionResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleActionResource.setContextCompany(_company);
		pageRuleActionResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageRuleActionResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageRuleActionResource.setContextUriInfo(_uriInfo);
		pageRuleActionResource.setContextUser(_user);
		pageRuleActionResource.setGroupLocalService(_groupLocalService);
		pageRuleActionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageRuleConditionResource pageRuleConditionResource)
		throws Exception {

		pageRuleConditionResource.setContextAcceptLanguage(_acceptLanguage);
		pageRuleConditionResource.setContextCompany(_company);
		pageRuleConditionResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageRuleConditionResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageRuleConditionResource.setContextUriInfo(_uriInfo);
		pageRuleConditionResource.setContextUser(_user);
		pageRuleConditionResource.setGroupLocalService(_groupLocalService);
		pageRuleConditionResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageSpecificationResource pageSpecificationResource)
		throws Exception {

		pageSpecificationResource.setContextAcceptLanguage(_acceptLanguage);
		pageSpecificationResource.setContextCompany(_company);
		pageSpecificationResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageSpecificationResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageSpecificationResource.setContextUriInfo(_uriInfo);
		pageSpecificationResource.setContextUser(_user);
		pageSpecificationResource.setGroupLocalService(_groupLocalService);
		pageSpecificationResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageTemplateResource pageTemplateResource)
		throws Exception {

		pageTemplateResource.setContextAcceptLanguage(_acceptLanguage);
		pageTemplateResource.setContextCompany(_company);
		pageTemplateResource.setContextHttpServletRequest(_httpServletRequest);
		pageTemplateResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageTemplateResource.setContextUriInfo(_uriInfo);
		pageTemplateResource.setContextUser(_user);
		pageTemplateResource.setGroupLocalService(_groupLocalService);
		pageTemplateResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			PageTemplateSetResource pageTemplateSetResource)
		throws Exception {

		pageTemplateSetResource.setContextAcceptLanguage(_acceptLanguage);
		pageTemplateSetResource.setContextCompany(_company);
		pageTemplateSetResource.setContextHttpServletRequest(
			_httpServletRequest);
		pageTemplateSetResource.setContextHttpServletResponse(
			_httpServletResponse);
		pageTemplateSetResource.setContextUriInfo(_uriInfo);
		pageTemplateSetResource.setContextUser(_user);
		pageTemplateSetResource.setGroupLocalService(_groupLocalService);
		pageTemplateSetResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(SitePageResource sitePageResource)
		throws Exception {

		sitePageResource.setContextAcceptLanguage(_acceptLanguage);
		sitePageResource.setContextCompany(_company);
		sitePageResource.setContextHttpServletRequest(_httpServletRequest);
		sitePageResource.setContextHttpServletResponse(_httpServletResponse);
		sitePageResource.setContextUriInfo(_uriInfo);
		sitePageResource.setContextUser(_user);
		sitePageResource.setGroupLocalService(_groupLocalService);
		sitePageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			UtilityPageResource utilityPageResource)
		throws Exception {

		utilityPageResource.setContextAcceptLanguage(_acceptLanguage);
		utilityPageResource.setContextCompany(_company);
		utilityPageResource.setContextHttpServletRequest(_httpServletRequest);
		utilityPageResource.setContextHttpServletResponse(_httpServletResponse);
		utilityPageResource.setContextUriInfo(_uriInfo);
		utilityPageResource.setContextUser(_user);
		utilityPageResource.setGroupLocalService(_groupLocalService);
		utilityPageResource.setRoleLocalService(_roleLocalService);
	}

	private void _populateResourceContext(
			WidgetPageWidgetInstanceResource widgetPageWidgetInstanceResource)
		throws Exception {

		widgetPageWidgetInstanceResource.setContextAcceptLanguage(
			_acceptLanguage);
		widgetPageWidgetInstanceResource.setContextCompany(_company);
		widgetPageWidgetInstanceResource.setContextHttpServletRequest(
			_httpServletRequest);
		widgetPageWidgetInstanceResource.setContextHttpServletResponse(
			_httpServletResponse);
		widgetPageWidgetInstanceResource.setContextUriInfo(_uriInfo);
		widgetPageWidgetInstanceResource.setContextUser(_user);
		widgetPageWidgetInstanceResource.setGroupLocalService(
			_groupLocalService);
		widgetPageWidgetInstanceResource.setRoleLocalService(_roleLocalService);
	}

	private static ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;
	private static ComponentServiceObjects<DisplayPageTemplateFolderResource>
		_displayPageTemplateFolderResourceComponentServiceObjects;
	private static ComponentServiceObjects<FragmentCompositionResource>
		_fragmentCompositionResourceComponentServiceObjects;
	private static ComponentServiceObjects<FriendlyUrlHistoryResource>
		_friendlyUrlHistoryResourceComponentServiceObjects;
	private static ComponentServiceObjects<MasterPageResource>
		_masterPageResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageElementResource>
		_pageElementResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageExperienceResource>
		_pageExperienceResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleResource>
		_pageRuleResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleActionResource>
		_pageRuleActionResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageRuleConditionResource>
		_pageRuleConditionResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageSpecificationResource>
		_pageSpecificationResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageTemplateResource>
		_pageTemplateResourceComponentServiceObjects;
	private static ComponentServiceObjects<PageTemplateSetResource>
		_pageTemplateSetResourceComponentServiceObjects;
	private static ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;
	private static ComponentServiceObjects<UtilityPageResource>
		_utilityPageResourceComponentServiceObjects;
	private static ComponentServiceObjects<WidgetPageWidgetInstanceResource>
		_widgetPageWidgetInstanceResourceComponentServiceObjects;

	private AcceptLanguage _acceptLanguage;
	private BiFunction<Object, List<String>, Aggregation>
		_aggregationBiFunction;
	private com.liferay.portal.kernel.model.Company _company;
	private BiFunction<Object, String, Filter> _filterBiFunction;
	private GroupLocalService _groupLocalService;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private RoleLocalService _roleLocalService;
	private BiFunction<Object, String, Sort[]> _sortsBiFunction;
	private UriInfo _uriInfo;
	private com.liferay.portal.kernel.model.User _user;

}