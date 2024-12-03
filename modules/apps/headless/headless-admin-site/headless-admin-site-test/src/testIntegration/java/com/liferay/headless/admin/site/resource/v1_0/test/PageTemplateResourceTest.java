/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.ContentPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageTemplate;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class PageTemplateResourceTest extends BasePageTemplateResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate pageTemplate =
			testPostBySiteExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate());

		_testDeleteBySiteExternalReferenceCodePageTemplate(
			testGroup, pageTemplate.getExternalReferenceCode());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateResource.
					deleteBySiteExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						pageTemplate.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) -> {
				_postBySiteExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode());

				_testDeleteBySiteExternalReferenceCodePageTemplate(
					group, widgetPageTemplate.getExternalReferenceCode());
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						deleteBySiteExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							RandomTestUtil.randomString())));
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate pageTemplate =
			testPostBySiteExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate());

		_testGetBySiteExternalReferenceCodeMasterPage(pageTemplate);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				pageTemplateResource.getBySiteExternalReferenceCodePageTemplate(
					testGroup.getExternalReferenceCode(),
					RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetBySiteExternalReferenceCodeMasterPage(pageTemplate);

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) ->
				_postBySiteExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode()));

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						getBySiteExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							RandomTestUtil.randomString())));
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage();
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatesPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodePageTemplatesPage();

		long totalCount =
			_getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
				testGroup.getExternalReferenceCode());

		_enableLocalStaging();

		Assert.assertEquals(
			totalCount,
			_getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
				testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) -> {
				long curTotalCount =
					_getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode());

				_postBySiteExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode());

				Assert.assertEquals(
					curTotalCount + 1,
					_getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode()));
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					_getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
						group.getExternalReferenceCode())));
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatesPageWithPagination()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplatesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatesPageWithSortDateTime()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplatesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatesPageWithSortDouble()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplatesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplatesPageWithSortInteger()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplatesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		ContentPageTemplate contentPageTemplate =
			(ContentPageTemplate)
				pageTemplateResource.
					postBySiteExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						_getContentPageTemplate(testGroup));

		_testPatchBySiteExternalReferenceCodePageTemplate(
			_getUpdatedContentPageTemplate(
				testGroup, contentPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		WidgetPageTemplate widgetPageTemplate =
			(WidgetPageTemplate)
				pageTemplateResource.
					postBySiteExternalReferenceCodePageTemplate(
						testGroup.getExternalReferenceCode(),
						_getWidgetPageTemplate(testGroup));

		_testPatchBySiteExternalReferenceCodePageTemplate(
			_getUpdatedWidgetPageTemplate(
				testGroup, widgetPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPatchBySiteExternalReferenceCodePageTemplate(
				contentPageTemplate, testGroup.getExternalReferenceCode()));

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPatchBySiteExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, curWidgetPageTemplate) -> {
				_postBySiteExternalReferenceCodePageTemplate(
					curWidgetPageTemplate, group.getExternalReferenceCode());

				_testPatchBySiteExternalReferenceCodePageTemplate(
					_getUpdatedWidgetPageTemplate(
						group,
						curWidgetPageTemplate.getExternalReferenceCode()),
					group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() -> {
						ContentPageTemplate curContentPageTemplate =
							_getContentPageTemplate(group);

						pageTemplateResource.
							putBySiteExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								curContentPageTemplate.
									getExternalReferenceCode(),
								curContentPageTemplate);
					});
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() -> {
					PageTemplate pageTemplate = _getPageTemplate(group);

					pageTemplateResource.
						putBySiteExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							pageTemplate.getExternalReferenceCode(),
							pageTemplate);
				}));
	}

	@Override
	@Test
	public void testPostBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		_testPostBySiteExternalReferenceCodePageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPostBySiteExternalReferenceCodePageTemplatePageSpecification()
		throws Exception {

		super.
			testPostBySiteExternalReferenceCodePageTemplatePageSpecification();
	}

	@Override
	@Test
	public void testPostBySiteExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		_testPostBySiteExternalReferenceCodePageTemplateSetPageTemplate();
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		ContentPageTemplate contentPageTemplate = _getContentPageTemplate(
			testGroup);

		_testPutBySiteExternalReferenceCodePageTemplate(
			contentPageTemplate, testGroup.getExternalReferenceCode());

		_testPutBySiteExternalReferenceCodePageTemplate(
			_getUpdatedContentPageTemplate(
				testGroup, contentPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(
			testGroup);

		_testPutBySiteExternalReferenceCodePageTemplate(
			widgetPageTemplate, testGroup.getExternalReferenceCode());

		_testPutBySiteExternalReferenceCodePageTemplate(
			_getUpdatedWidgetPageTemplate(
				testGroup, widgetPageTemplate.getExternalReferenceCode()),
			testGroup.getExternalReferenceCode());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPutBySiteExternalReferenceCodePageTemplate(
				contentPageTemplate, testGroup.getExternalReferenceCode()));

		_assertProblemException(
			"BAD_REQUEST",
			() -> _testPutBySiteExternalReferenceCodePageTemplate(
				widgetPageTemplate, testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, curWidgetPageTemplate) -> {
				_testPutBySiteExternalReferenceCodePageTemplate(
					curWidgetPageTemplate, group.getExternalReferenceCode());

				_testPutBySiteExternalReferenceCodePageTemplate(
					_getUpdatedWidgetPageTemplate(
						group,
						curWidgetPageTemplate.getExternalReferenceCode()),
					group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() -> {
						ContentPageTemplate curContentPageTemplate =
							_getContentPageTemplate(group);

						pageTemplateResource.
							putBySiteExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								curContentPageTemplate.
									getExternalReferenceCode(),
								curContentPageTemplate);
					});
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() -> {
					PageTemplate pageTemplate = _getPageTemplate(group);

					pageTemplateResource.
						putBySiteExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							pageTemplate.getExternalReferenceCode(),
							pageTemplate);
				}));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testPutBySiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodePageTemplatePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "description_i18n", "externalReferenceCode", "name",
			"name_i18n", "pageTemplateSet"
		};
	}

	@Override
	protected PageTemplate randomIrrelevantPageTemplate() throws Exception {
		return _getPageTemplate(irrelevantGroup);
	}

	@Override
	protected PageTemplate randomPageTemplate() throws Exception {
		return _getPageTemplate(testGroup);
	}

	@Override
	protected PageTemplate
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage_addPageTemplate(
				String siteExternalReferenceCode,
				String pageTemplateSetExternalReferenceCode,
				PageTemplate pageTemplate)
		throws Exception {

		return pageTemplateResource.
			postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
				siteExternalReferenceCode, pageTemplateSetExternalReferenceCode,
				pageTemplate);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage_getIrrelevantPageTemplateSetExternalReferenceCode()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(irrelevantGroup);

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage_getPageTemplateSetExternalReferenceCode()
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(testGroup);

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplateSetPageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplate
			testGetBySiteExternalReferenceCodePageTemplatesPage_addPageTemplate(
				String siteExternalReferenceCode, PageTemplate pageTemplate)
		throws Exception {

		return pageTemplateResource.postBySiteExternalReferenceCodePageTemplate(
			siteExternalReferenceCode, pageTemplate);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplate
			testPostBySiteExternalReferenceCodePageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		return testGetBySiteExternalReferenceCodePageTemplatesPage_addPageTemplate(
			testGroup.getExternalReferenceCode(), pageTemplate);
	}

	@Override
	protected PageTemplate
			testPostBySiteExternalReferenceCodePageTemplateSetPageTemplate_addPageTemplate(
				PageTemplate pageTemplate)
		throws Exception {

		PageTemplateSet pageTemplateSet = pageTemplate.getPageTemplateSet();

		return pageTemplateResource.
			postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode(), pageTemplate);
	}

	private void _assertProblemException(
			String status, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals(status, problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	private void _enableLocalStaging() throws Exception {
		_stagingLocalService.enableLocalStaging(
			TestPropsValues.getUserId(), testGroup, true, false,
			ServiceContextTestUtil.getServiceContext(
				testGroup, TestPropsValues.getUserId()));
	}

	private long _getBySiteExternalReferenceCodePageTemplatesPageTotalCount(
			String siteExternalReferenceCode)
		throws Exception {

		Page<PageTemplate> page =
			pageTemplateResource.
				getBySiteExternalReferenceCodePageTemplatesPage(
					siteExternalReferenceCode, null, null, null, null, null);

		return page.getTotalCount();
	}

	private ContentPageTemplate _getContentPageTemplate(Group group)
		throws Exception {

		return new ContentPageTemplate() {
			{
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				pageTemplateSet = _getPageTemplateSet(group);
				type = Type.CONTENT_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private LayoutPageTemplateCollection _getLayoutPageTemplateCollection(
			Group group)
		throws Exception {

		return _layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				null, TestPropsValues.getUserId(), group.getGroupId(),
				LayoutPageTemplateConstants.
					PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				LayoutPageTemplateCollectionTypeConstants.BASIC,
				ServiceContextTestUtil.getServiceContext(
					group, TestPropsValues.getUserId()));
	}

	private PageTemplate _getPageTemplate(Group group) throws Exception {
		List<UnsafeSupplier<PageTemplate, Exception>> unsafeSuppliers =
			Arrays.asList(
				() -> _getContentPageTemplate(group),
				() -> _getWidgetPageTemplate(group));

		UnsafeSupplier<PageTemplate, Exception> unsafeSupplier =
			unsafeSuppliers.get(
				RandomTestUtil.randomInt(0, unsafeSuppliers.size() - 1));

		return unsafeSupplier.get();
	}

	private PageTemplateSet _getPageTemplateSet(Group group) throws Exception {
		if (group.isCompany() || group.isDepot()) {
			return null;
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_getLayoutPageTemplateCollection(group);

		return new PageTemplateSet() {
			{
				setDateCreated(layoutPageTemplateCollection::getCreateDate);
				setDateModified(layoutPageTemplateCollection::getModifiedDate);
				setDescription(layoutPageTemplateCollection::getDescription);
				setExternalReferenceCode(
					layoutPageTemplateCollection::getExternalReferenceCode);
				setName(layoutPageTemplateCollection::getName);
			}
		};
	}

	private ContentPageTemplate _getUpdatedContentPageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		ContentPageTemplate contentPageTemplate =
			(ContentPageTemplate)
				pageTemplateResource.getBySiteExternalReferenceCodePageTemplate(
					group.getExternalReferenceCode(),
					pageTemplateExternalReferenceCode);

		contentPageTemplate.setName(
			StringUtil.toLowerCase(RandomTestUtil.randomString()));
		contentPageTemplate.setPageTemplateSet(_getPageTemplateSet(group));

		return contentPageTemplate;
	}

	private WidgetPageTemplate _getUpdatedWidgetPageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		WidgetPageTemplate widgetPageTemplate =
			(WidgetPageTemplate)
				pageTemplateResource.getBySiteExternalReferenceCodePageTemplate(
					group.getExternalReferenceCode(),
					pageTemplateExternalReferenceCode);

		widgetPageTemplate.setActive(RandomTestUtil.randomBoolean());
		widgetPageTemplate.setDescription_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
				RandomTestUtil.randomString()
			).build());

		String name = StringUtil.toLowerCase(RandomTestUtil.randomString());

		widgetPageTemplate.setName(name);
		widgetPageTemplate.setName_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()), name
			).build());

		widgetPageTemplate.setPageTemplateSet(_getPageTemplateSet(group));

		return widgetPageTemplate;
	}

	private WidgetPageTemplate _getWidgetPageTemplate(Group group)
		throws Exception {

		return new WidgetPageTemplate() {
			{
				active = RandomTestUtil.randomBoolean();
				creatorExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				datePublished = RandomTestUtil.nextDate();
				description_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
					RandomTestUtil.randomString()
				).build();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				hiddenFromNavigation = RandomTestUtil.randomBoolean();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());

				name = StringUtil.toLowerCase(RandomTestUtil.randomString());

				name_i18n = HashMapBuilder.put(
					LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()), name
				).build();

				pageTemplateSet = _getPageTemplateSet(group);
				type = PageTemplate.Type.WIDGET_PAGE_TEMPLATE;
				uuid = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	private void _postBySiteExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.postBySiteExternalReferenceCodePageTemplate(
				siteExternalReferenceCode, pageTemplate));
	}

	private void _postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		PageTemplateSet pageTemplateSet = pageTemplate.getPageTemplateSet();

		assertEquals(
			pageTemplate,
			pageTemplateResource.
				postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
					siteExternalReferenceCode,
					pageTemplateSet.getExternalReferenceCode(), pageTemplate));
	}

	private void _testDeleteBySiteExternalReferenceCodePageTemplate(
			Group group, String pageTemplateExternalReferenceCode)
		throws Exception {

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode, group.getGroupId()));

		pageTemplateResource.deleteBySiteExternalReferenceCodePageTemplate(
			group.getExternalReferenceCode(),
			pageTemplateExternalReferenceCode);

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					pageTemplateExternalReferenceCode, group.getGroupId()));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				pageTemplateResource.
					deleteBySiteExternalReferenceCodePageTemplate(
						group.getExternalReferenceCode(),
						pageTemplateExternalReferenceCode));
	}

	private void _testGetBySiteExternalReferenceCodeMasterPage(
			PageTemplate pageTemplate)
		throws Exception {

		PageTemplate getPageTemplate =
			pageTemplateResource.getBySiteExternalReferenceCodePageTemplate(
				testGroup.getExternalReferenceCode(),
				pageTemplate.getExternalReferenceCode());

		assertEquals(pageTemplate, getPageTemplate);
		assertValid(getPageTemplate);
	}

	private void _testPatchBySiteExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.patchBySiteExternalReferenceCodePageTemplate(
				siteExternalReferenceCode,
				pageTemplate.getExternalReferenceCode(), pageTemplate));
	}

	private void _testPostBySiteExternalReferenceCodePageTemplate()
		throws Exception {

		PageTemplate randomPageTemplate = randomPageTemplate();

		PageTemplate postPageTemplate =
			testPostBySiteExternalReferenceCodePageTemplate_addPageTemplate(
				randomPageTemplate);

		assertEquals(randomPageTemplate, postPageTemplate);
		assertValid(postPageTemplate);

		_postBySiteExternalReferenceCodePageTemplate(
			_getContentPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_postBySiteExternalReferenceCodePageTemplate(
			_getWidgetPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> _postBySiteExternalReferenceCodePageTemplate(
				_getPageTemplate(testGroup),
				testGroup.getExternalReferenceCode()));

		_withCompanyGroupWidgetPageTemplate(
			(group, widgetPageTemplate) -> {
				_postBySiteExternalReferenceCodePageTemplate(
					widgetPageTemplate, group.getExternalReferenceCode());

				_assertProblemException(
					"BAD_REQUEST",
					() ->
						pageTemplateResource.
							postBySiteExternalReferenceCodePageTemplate(
								group.getExternalReferenceCode(),
								_getContentPageTemplate(group)));
			});

		_withDepotEntry(
			group -> _assertProblemException(
				"BAD_REQUEST",
				() ->
					pageTemplateResource.
						postBySiteExternalReferenceCodePageTemplate(
							group.getExternalReferenceCode(),
							_getPageTemplate(group))));
	}

	private void _testPostBySiteExternalReferenceCodePageTemplateSetPageTemplate()
		throws Exception {

		PageTemplate randomPageTemplate = randomPageTemplate();

		PageTemplate postPageTemplate =
			testPostBySiteExternalReferenceCodePageTemplateSetPageTemplate_addPageTemplate(
				randomPageTemplate);

		assertEquals(randomPageTemplate, postPageTemplate);
		assertValid(postPageTemplate);

		_postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
			_getContentPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());

		_postBySiteExternalReferenceCodePageTemplateSetPageTemplate(
			_getWidgetPageTemplate(testGroup),
			testGroup.getExternalReferenceCode());
	}

	private void _testPutBySiteExternalReferenceCodePageTemplate(
			PageTemplate pageTemplate, String siteExternalReferenceCode)
		throws Exception {

		assertEquals(
			pageTemplate,
			pageTemplateResource.putBySiteExternalReferenceCodePageTemplate(
				siteExternalReferenceCode,
				pageTemplate.getExternalReferenceCode(), pageTemplate));
	}

	private void _withCompanyGroupWidgetPageTemplate(
			UnsafeBiConsumer<Group, WidgetPageTemplate, Exception>
				unsafeBiConsumer)
		throws Exception {

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		Group group = company.getGroup();

		WidgetPageTemplate widgetPageTemplate = _getWidgetPageTemplate(group);

		try {
			unsafeBiConsumer.accept(group, widgetPageTemplate);
		}
		finally {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				_layoutPageTemplateEntryLocalService.
					fetchLayoutPageTemplateEntryByExternalReferenceCode(
						widgetPageTemplate.getExternalReferenceCode(),
						group.getGroupId());

			if (layoutPageTemplateEntry != null) {
				_layoutPageTemplateEntryLocalService.
					deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
			}
		}
	}

	private void _withDepotEntry(
			UnsafeConsumer<Group, Exception> unsafeConsumer)
		throws Exception {

		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()
			).build(),
			new HashMap<>(), ServiceContextTestUtil.getServiceContext());

		try {
			unsafeConsumer.accept(depotEntry.getGroup());
		}
		finally {
			_depotEntryLocalService.deleteDepotEntry(depotEntry);
		}
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}