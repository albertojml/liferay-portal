/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.graphql.servlet.v1_0;

import com.liferay.headless.admin.site.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.admin.site.internal.graphql.query.v1_0.Query;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateFolderResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.DisplayPageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.FragmentCompositionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.FriendlyUrlHistoryResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.MasterPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageElementResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageExperienceResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleActionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleConditionResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageRuleResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageSpecificationResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.PageTemplateSetResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.SitePageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.UtilityPageResourceImpl;
import com.liferay.headless.admin.site.internal.resource.v1_0.WidgetPageWidgetInstanceResourceImpl;
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
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Rubén Pulido
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Mutation.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Mutation.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Mutation.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Mutation.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Mutation.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Mutation.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Mutation.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Mutation.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Mutation.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Mutation.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Mutation.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Mutation.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Mutation.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Mutation.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Mutation.setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects);

		Query.setDisplayPageTemplateResourceComponentServiceObjects(
			_displayPageTemplateResourceComponentServiceObjects);
		Query.setDisplayPageTemplateFolderResourceComponentServiceObjects(
			_displayPageTemplateFolderResourceComponentServiceObjects);
		Query.setFragmentCompositionResourceComponentServiceObjects(
			_fragmentCompositionResourceComponentServiceObjects);
		Query.setFriendlyUrlHistoryResourceComponentServiceObjects(
			_friendlyUrlHistoryResourceComponentServiceObjects);
		Query.setMasterPageResourceComponentServiceObjects(
			_masterPageResourceComponentServiceObjects);
		Query.setPageElementResourceComponentServiceObjects(
			_pageElementResourceComponentServiceObjects);
		Query.setPageExperienceResourceComponentServiceObjects(
			_pageExperienceResourceComponentServiceObjects);
		Query.setPageRuleResourceComponentServiceObjects(
			_pageRuleResourceComponentServiceObjects);
		Query.setPageRuleActionResourceComponentServiceObjects(
			_pageRuleActionResourceComponentServiceObjects);
		Query.setPageRuleConditionResourceComponentServiceObjects(
			_pageRuleConditionResourceComponentServiceObjects);
		Query.setPageSpecificationResourceComponentServiceObjects(
			_pageSpecificationResourceComponentServiceObjects);
		Query.setPageTemplateResourceComponentServiceObjects(
			_pageTemplateResourceComponentServiceObjects);
		Query.setPageTemplateSetResourceComponentServiceObjects(
			_pageTemplateSetResourceComponentServiceObjects);
		Query.setSitePageResourceComponentServiceObjects(
			_sitePageResourceComponentServiceObjects);
		Query.setUtilityPageResourceComponentServiceObjects(
			_utilityPageResourceComponentServiceObjects);
		Query.setWidgetPageWidgetInstanceResourceComponentServiceObjects(
			_widgetPageWidgetInstanceResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Admin.Site";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-admin-site-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodObjectValuePair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodObjectValuePairs.get(
				"mutation#" + methodName);
		}

		return _resourceMethodObjectValuePairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodObjectValuePairs =
			new HashMap<String, ObjectValuePair<Class<?>, String>>() {
				{
					put(
						"mutation#createBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate"));
					put(
						"mutation#createBySiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#updateBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"deleteBySiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#patchBySiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"patchBySiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#updateBySiteExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putBySiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"mutation#createBySiteExternalReferenceCodeDisplayPageTemplatePageSpecification",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodeDisplayPageTemplatePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"postBySiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"deleteBySiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#patchBySiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"patchBySiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateBySiteExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putBySiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"putSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"postBySiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"deleteBySiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#patchBySiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"patchBySiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#updateBySiteExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"putBySiteExternalReferenceCodeFragmentComposition"));
					put(
						"mutation#createBySiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postBySiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putBySiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"deleteBySiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#patchBySiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"patchBySiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putBySiteExternalReferenceCodeMasterPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeMasterPagePageSpecification",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"postBySiteExternalReferenceCodeMasterPagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeMasterPagePermissionsPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageElement"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"patchBySiteExternalReferenceCodePageElement"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"putBySiteExternalReferenceCodePageElement"));
					put(
						"mutation#createBySiteExternalReferenceCodePageElementFragmentComposition",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postBySiteExternalReferenceCodePageElementFragmentComposition"));
					put(
						"mutation#createBySiteExternalReferenceCodePageExperiencePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"postBySiteExternalReferenceCodePageExperiencePageElement"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageExperience"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"patchBySiteExternalReferenceCodePageExperience"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"putBySiteExternalReferenceCodePageExperience"));
					put(
						"mutation#createBySiteExternalReferenceCodePageSpecificationPageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"postBySiteExternalReferenceCodePageSpecificationPageExperience"));
					put(
						"mutation#createBySiteExternalReferenceCodePageExperiencePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"postBySiteExternalReferenceCodePageExperiencePageRule"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageRule"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"patchBySiteExternalReferenceCodePageRule"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"putBySiteExternalReferenceCodePageRule"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"patchBySiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"putBySiteExternalReferenceCodePageRuleAction"));
					put(
						"mutation#createBySiteExternalReferenceCodePageRulePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"postBySiteExternalReferenceCodePageRulePageRuleAction"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"patchBySiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"putBySiteExternalReferenceCodePageRuleCondition"));
					put(
						"mutation#createBySiteExternalReferenceCodePageRulePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"postBySiteExternalReferenceCodePageRulePageRuleCondition"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"patchBySiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"putBySiteExternalReferenceCodePageSpecification"));
					put(
						"mutation#createBySiteExternalReferenceCodePageSpecificationPublish",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"postBySiteExternalReferenceCodePageSpecificationPublish"));
					put(
						"mutation#createBySiteExternalReferenceCodePageTemplateSetPageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodePageTemplateSetPageTemplate"));
					put(
						"mutation#createBySiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putBySiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"patchBySiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putBySiteExternalReferenceCodePageTemplate"));
					put(
						"mutation#createBySiteExternalReferenceCodePageTemplatePageSpecification",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"postBySiteExternalReferenceCodePageTemplatePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplatePermissionsPage",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"postBySiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putBySiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"deleteBySiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#patchBySiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"patchBySiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateBySiteExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putBySiteExternalReferenceCodePageTemplateSet"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"putSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postBySiteExternalReferenceCodeSitePage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putBySiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"deleteBySiteExternalReferenceCodeSitePage"));
					put(
						"mutation#patchBySiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"patchBySiteExternalReferenceCodeSitePage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putBySiteExternalReferenceCodeSitePage"));
					put(
						"mutation#createBySiteExternalReferenceCodeSitePagePageSpecification",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"postBySiteExternalReferenceCodeSitePagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeSitePagePermissionsPage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postBySiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putBySiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"deleteBySiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#patchBySiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"patchBySiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#updateBySiteExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putBySiteExternalReferenceCodeUtilityPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeUtilityPagePageSpecification",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"postBySiteExternalReferenceCodeUtilityPagePageSpecification"));
					put(
						"mutation#updateSiteSiteExternalReferenceCodeUtilityPagePermissionsPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"putSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"mutation#createBySiteExternalReferenceCodeSitePageWidgetInstance",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"postBySiteExternalReferenceCodeSitePageWidgetInstance"));
					put(
						"mutation#deleteBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"deleteBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
					put(
						"mutation#patchBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"patchBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
					put(
						"mutation#updateBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"putBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));

					put(
						"query#byExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplates",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplatesPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplate",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplate"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplatePermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplateFolders",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplateFolder",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplateFolder"));
					put(
						"query#siteExternalReferenceCodeDisplayPageTemplateFolderPermissions",
						new ObjectValuePair<>(
							DisplayPageTemplateFolderResourceImpl.class,
							"getSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage"));
					put(
						"query#byExternalReferenceCodeFragmentCompositions",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getBySiteExternalReferenceCodeFragmentCompositionsPage"));
					put(
						"query#byExternalReferenceCodeFragmentComposition",
						new ObjectValuePair<>(
							FragmentCompositionResourceImpl.class,
							"getBySiteExternalReferenceCodeFragmentComposition"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplateFriendlyUrlHistory"));
					put(
						"query#byExternalReferenceCodeSitePageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePageFriendlyUrlHistory"));
					put(
						"query#byExternalReferenceCodeUtilityPageFriendlyUrlHistory",
						new ObjectValuePair<>(
							FriendlyUrlHistoryResourceImpl.class,
							"getBySiteExternalReferenceCodeUtilityPageFriendlyUrlHistory"));
					put(
						"query#byExternalReferenceCodeMasterPages",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getBySiteExternalReferenceCodeMasterPagesPage"));
					put(
						"query#byExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getBySiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#byExternalReferenceCodeMasterPage",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getBySiteExternalReferenceCodeMasterPage"));
					put(
						"query#siteExternalReferenceCodeMasterPagePermissions",
						new ObjectValuePair<>(
							MasterPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeMasterPagePermissionsPage"));
					put(
						"query#byExternalReferenceCodePageElement",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getBySiteExternalReferenceCodePageElement"));
					put(
						"query#byExternalReferenceCodePageElementPageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getBySiteExternalReferenceCodePageElementPageElementsPage"));
					put(
						"query#byExternalReferenceCodePageExperiencePageElements",
						new ObjectValuePair<>(
							PageElementResourceImpl.class,
							"getBySiteExternalReferenceCodePageExperiencePageElementsPage"));
					put(
						"query#byExternalReferenceCodePageExperience",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getBySiteExternalReferenceCodePageExperience"));
					put(
						"query#byExternalReferenceCodePageSpecificationPageExperiences",
						new ObjectValuePair<>(
							PageExperienceResourceImpl.class,
							"getBySiteExternalReferenceCodePageSpecificationPageExperiencesPage"));
					put(
						"query#byExternalReferenceCodePageExperiencePageRules",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getBySiteExternalReferenceCodePageExperiencePageRulesPage"));
					put(
						"query#byExternalReferenceCodePageRule",
						new ObjectValuePair<>(
							PageRuleResourceImpl.class,
							"getBySiteExternalReferenceCodePageRule"));
					put(
						"query#byExternalReferenceCodePageRuleAction",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getBySiteExternalReferenceCodePageRuleAction"));
					put(
						"query#byExternalReferenceCodePageRulePageRuleActions",
						new ObjectValuePair<>(
							PageRuleActionResourceImpl.class,
							"getBySiteExternalReferenceCodePageRulePageRuleActionsPage"));
					put(
						"query#byExternalReferenceCodePageRuleCondition",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getBySiteExternalReferenceCodePageRuleCondition"));
					put(
						"query#byExternalReferenceCodePageRulePageRuleConditions",
						new ObjectValuePair<>(
							PageRuleConditionResourceImpl.class,
							"getBySiteExternalReferenceCodePageRulePageRuleConditionsPage"));
					put(
						"query#byExternalReferenceCodeDisplayPageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodeDisplayPageTemplatePageSpecificationsPage"));
					put(
						"query#byExternalReferenceCodeMasterPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodeMasterPagePageSpecificationsPage"));
					put(
						"query#byExternalReferenceCodePageSpecification",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodePageSpecification"));
					put(
						"query#byExternalReferenceCodePageTemplatePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplatePageSpecificationsPage"));
					put(
						"query#byExternalReferenceCodeSitePagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePagePageSpecificationsPage"));
					put(
						"query#byExternalReferenceCodeUtilityPagePageSpecifications",
						new ObjectValuePair<>(
							PageSpecificationResourceImpl.class,
							"getBySiteExternalReferenceCodeUtilityPagePageSpecificationsPage"));
					put(
						"query#byExternalReferenceCodePageTemplateSetPageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage"));
					put(
						"query#byExternalReferenceCodePageTemplates",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplatesPage"));
					put(
						"query#byExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#byExternalReferenceCodePageTemplate",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplate"));
					put(
						"query#siteExternalReferenceCodePageTemplatePermissions",
						new ObjectValuePair<>(
							PageTemplateResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplatePermissionsPage"));
					put(
						"query#byExternalReferenceCodePageTemplateSets",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplateSetsPage"));
					put(
						"query#byExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#byExternalReferenceCodePageTemplateSet",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getBySiteExternalReferenceCodePageTemplateSet"));
					put(
						"query#siteExternalReferenceCodePageTemplateSetPermissions",
						new ObjectValuePair<>(
							PageTemplateSetResourceImpl.class,
							"getSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage"));
					put(
						"query#byExternalReferenceCodeSitePages",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePagesPage"));
					put(
						"query#byExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#byExternalReferenceCodeSitePage",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePage"));
					put(
						"query#siteExternalReferenceCodeSitePagePermissions",
						new ObjectValuePair<>(
							SitePageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeSitePagePermissionsPage"));
					put(
						"query#byExternalReferenceCodeUtilityPages",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getBySiteExternalReferenceCodeUtilityPagesPage"));
					put(
						"query#byExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getBySiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#byExternalReferenceCodeUtilityPage",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getBySiteExternalReferenceCodeUtilityPage"));
					put(
						"query#siteExternalReferenceCodeUtilityPagePermissions",
						new ObjectValuePair<>(
							UtilityPageResourceImpl.class,
							"getSiteSiteExternalReferenceCodeUtilityPagePermissionsPage"));
					put(
						"query#byExternalReferenceCodeSitePageWidgetInstances",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getBySiteExternalReferenceCodeSitePageWidgetInstancesPage"));
					put(
						"query#byExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode",
						new ObjectValuePair<>(
							WidgetPageWidgetInstanceResourceImpl.class,
							"getBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode"));
				}
			};

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateResource>
		_displayPageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<DisplayPageTemplateFolderResource>
		_displayPageTemplateFolderResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FragmentCompositionResource>
		_fragmentCompositionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<MasterPageResource>
		_masterPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageElementResource>
		_pageElementResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageExperienceResource>
		_pageExperienceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleResource>
		_pageRuleResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleActionResource>
		_pageRuleActionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageRuleConditionResource>
		_pageRuleConditionResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageSpecificationResource>
		_pageSpecificationResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateResource>
		_pageTemplateResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<PageTemplateSetResource>
		_pageTemplateSetResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SitePageResource>
		_sitePageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<UtilityPageResource>
		_utilityPageResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<WidgetPageWidgetInstanceResource>
		_widgetPageWidgetInstanceResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<FriendlyUrlHistoryResource>
		_friendlyUrlHistoryResourceComponentServiceObjects;

}