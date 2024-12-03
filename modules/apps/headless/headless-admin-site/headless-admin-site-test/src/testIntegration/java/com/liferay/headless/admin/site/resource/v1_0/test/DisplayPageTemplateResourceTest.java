/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.ClassSubtypeReference;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplate;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.DisplayPageTemplateResource;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.constants.LayoutPageTemplateConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 * @author Lourdes Fernández Besada
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class DisplayPageTemplateResourceTest
	extends BaseDisplayPageTemplateResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate postDisplayPageTemplate =
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postDisplayPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId()));

		displayPageTemplateResource.
			deleteBySiteExternalReferenceCodeDisplayPageTemplate(
				testGroup.getExternalReferenceCode(),
				postDisplayPageTemplate.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postDisplayPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId()));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					deleteBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						postDisplayPageTemplate.getExternalReferenceCode()));

		DisplayPageTemplate liveGroupDisplayPageTemplate =
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					deleteBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplate.
							getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate displayPageTemplate =
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_testGetBySiteExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					getBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetBySiteExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);

		_testGetBySiteExternalReferenceCodeDisplayPageTemplateWithNestedFields(
			displayPageTemplate);
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage();

		_testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithNestedFields();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithPagination()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortDateTime()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortDouble()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortInteger()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortString()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		DisplayPageTemplate postDisplayPageTemplate =
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		Assert.assertNull(postDisplayPageTemplate.getParentFolder());

		_testPatchBySiteExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(),
			new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						_getLayoutPageTemplateCollectionExternalReferenceCode(
							testGroup.getGroupId()));
				}
			},
			Boolean.FALSE);
		_testPatchBySiteExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(), null,
			Boolean.FALSE);

		_updateLayoutPageTemplateEntryStatus(
			postDisplayPageTemplate.getExternalReferenceCode());

		_testPatchBySiteExternalReferenceCodeDisplayPageTemplate(
			postDisplayPageTemplate.getExternalReferenceCode(), null,
			Boolean.TRUE);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateResource.
					patchBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString(),
						randomDisplayPageTemplate()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					patchBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						postDisplayPageTemplate.getExternalReferenceCode(),
						postDisplayPageTemplate));
	}

	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		super.testPostBySiteExternalReferenceCodeDisplayPageTemplate();

		_testPostBySiteExternalReferenceCodeDisplayPageTemplateWithParentFolder();
	}

	@Ignore
	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate()
		throws Exception {

		super.
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate();
	}

	@Ignore
	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeDisplayPageTemplatePageSpecification()
		throws Exception {

		super.
			testPostBySiteExternalReferenceCodeDisplayPageTemplatePageSpecification();
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeDisplayPageTemplate()
		throws Exception {

		_testPutBySiteExternalReferenceCodeDisplayPageTemplate(
			randomDisplayPageTemplate());

		DisplayPageTemplate displayPageTemplate =
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				randomDisplayPageTemplate());

		_testPutBySiteExternalReferenceCodeDisplayPageTemplate(
			displayPageTemplate);

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateResource.
					putBySiteExternalReferenceCodeDisplayPageTemplate(
						testGroup.getExternalReferenceCode(),
						displayPageTemplate.getExternalReferenceCode(),
						displayPageTemplate));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutBySiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplatePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected DisplayPageTemplate randomDisplayPageTemplate() throws Exception {
		DisplayPageTemplate displayPageTemplate =
			super.randomDisplayPageTemplate();

		displayPageTemplate.setContentTypeReference(
			_getRandomClassSubtypeReference());
		displayPageTemplate.setMarkedAsDefault(Boolean.FALSE);

		return displayPageTemplate;
	}

	@Override
	protected DisplayPageTemplate
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_addDisplayPageTemplate(
				String siteExternalReferenceCode,
				String displayPageTemplateFolderExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return displayPageTemplateResource.
			postBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplate(
				siteExternalReferenceCode,
				displayPageTemplateFolderExternalReferenceCode,
				displayPageTemplate);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getDisplayPageTemplateFolderExternalReferenceCode()
		throws Exception {

		return _getLayoutPageTemplateCollectionExternalReferenceCode(
			testGroup.getGroupId());
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getIrrelevantDisplayPageTemplateFolderExternalReferenceCode()
		throws Exception {

		return _getLayoutPageTemplateCollectionExternalReferenceCode(
			irrelevantGroup.getGroupId());
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderDisplayPageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected DisplayPageTemplate
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage_addDisplayPageTemplate(
				String siteExternalReferenceCode,
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return displayPageTemplateResource.
			postBySiteExternalReferenceCodeDisplayPageTemplate(
				siteExternalReferenceCode, displayPageTemplate);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected DisplayPageTemplate
			testPostBySiteExternalReferenceCodeDisplayPageTemplate_addDisplayPageTemplate(
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		return testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage_addDisplayPageTemplate(
			testGroup.getExternalReferenceCode(), displayPageTemplate);
	}

	private void _assertNestedFields(DisplayPageTemplate displayPageTemplate)
		throws Exception {

		FriendlyUrlHistory friendlyUrlHistory =
			displayPageTemplate.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					displayPageTemplate.getExternalReferenceCode(),
					testGroup.getGroupId());

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateEntry.getPlid());

		Map<Locale, String> friendlyURLMap = layout.getFriendlyURLMap();

		Assert.assertEquals(
			jsonObject.toString(), friendlyURLMap.size(), jsonObject.length());

		for (Map.Entry<Locale, String> entry : friendlyURLMap.entrySet()) {
			String key = LocaleUtil.toBCP47LanguageId(entry.getKey());

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());
			Assert.assertEquals(
				jsonArray.toString(), entry.getValue(), jsonArray.getString(0));
		}
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

	private DisplayPageTemplate _getDisplayPageTemplate(
		List<DisplayPageTemplate> displayPageTemplates,
		String externalReferenceCode) {

		for (DisplayPageTemplate displayPageTemplate : displayPageTemplates) {
			if (Objects.equals(
					displayPageTemplate.getExternalReferenceCode(),
					externalReferenceCode)) {

				return displayPageTemplate;
			}
		}

		return null;
	}

	private DisplayPageTemplateResource _getDisplayPageTemplateResource()
		throws Exception {

		User omniadminUser = UserTestUtil.addOmniadminUser();

		String password = RandomTestUtil.randomString();

		_userLocalService.updatePassword(
			omniadminUser.getUserId(), password, password, false, true);

		return DisplayPageTemplateResource.builder(
		).authentication(
			omniadminUser.getEmailAddress(), password
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "friendlyUrlHistory"
		).build();
	}

	private String _getLayoutPageTemplateCollectionExternalReferenceCode(
			long groupId)
		throws Exception {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), groupId,
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						groupId, TestPropsValues.getUserId()));

		return layoutPageTemplateCollection.getExternalReferenceCode();
	}

	private ClassSubtypeReference _getRandomClassSubtypeReference() {
		if (RandomTestUtil.randomBoolean()) {
			return new ClassSubtypeReference() {
				{
					setClassName(AssetCategory.class.getName());
				}
			};
		}

		InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class,
				"com.liferay.journal.model.JournalArticle");

		List<InfoItemFormVariation> infoItemFormVariations = new ArrayList<>(
			infoItemFormVariationsProvider.getInfoItemFormVariations(
				testGroup.getGroupId()));

		Assert.assertFalse(infoItemFormVariations.isEmpty());

		infoItemFormVariations.sort(
			Comparator.comparing(InfoItemFormVariation::getKey));

		InfoItemFormVariation infoItemFormVariation =
			infoItemFormVariations.get(0);

		return new ClassSubtypeReference() {
			{
				setClassName("com.liferay.journal.model.JournalArticle");
				setSubTypeExternalReference(
					() -> new ItemExternalReference() {
						{
							setCollectionType(CollectionType.COLLECTION);
							setExternalReferenceCode(
								infoItemFormVariation::
									getExternalReferenceCode);
						}
					});
			}
		};
	}

	private void _testGetBySiteExternalReferenceCodeDisplayPageTemplate(
			DisplayPageTemplate displayPageTemplate)
		throws Exception {

		DisplayPageTemplate getDisplayPageTemplate =
			displayPageTemplateResource.
				getBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode());

		assertEquals(displayPageTemplate, getDisplayPageTemplate);
		assertValid(getDisplayPageTemplate);
	}

	private void _testGetBySiteExternalReferenceCodeDisplayPageTemplatesPageWithNestedFields()
		throws Exception {

		Page<DisplayPageTemplate> page =
			displayPageTemplateResource.
				getBySiteExternalReferenceCodeDisplayPageTemplatesPage(
					testGroup.getExternalReferenceCode(), null, null, null,
					Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		DisplayPageTemplate displayPageTemplate =
			testGetBySiteExternalReferenceCodeDisplayPageTemplatesPage_addDisplayPageTemplate(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplate());

		DisplayPageTemplateResource curDisplayPageTemplateResource =
			_getDisplayPageTemplateResource();

		page =
			curDisplayPageTemplateResource.
				getBySiteExternalReferenceCodeDisplayPageTemplatesPage(
					testGroup.getExternalReferenceCode(), null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		_assertNestedFields(
			_getDisplayPageTemplate(
				(List<DisplayPageTemplate>)page.getItems(),
				displayPageTemplate.getExternalReferenceCode()));
	}

	private void
			_testGetBySiteExternalReferenceCodeDisplayPageTemplateWithNestedFields(
				DisplayPageTemplate displayPageTemplate)
		throws Exception {

		DisplayPageTemplateResource curDisplayPageTemplateResource =
			_getDisplayPageTemplateResource();

		_assertNestedFields(
			curDisplayPageTemplateResource.
				getBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode()));
	}

	private void _testPatchBySiteExternalReferenceCodeDisplayPageTemplate(
			String displayPageTemplateExternalReferenceCode,
			DisplayPageTemplateFolder displayPageTemplateFolder,
			Boolean markedAsDefault)
		throws Exception {

		DisplayPageTemplate getDisplayPageTemplate =
			displayPageTemplateResource.
				getBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateExternalReferenceCode);

		DisplayPageTemplate randomDisplayPageTemplate =
			randomDisplayPageTemplate();

		randomDisplayPageTemplate.setExternalReferenceCode(
			displayPageTemplateExternalReferenceCode);
		randomDisplayPageTemplate.setMarkedAsDefault(markedAsDefault);
		randomDisplayPageTemplate.setParentFolder(displayPageTemplateFolder);

		DisplayPageTemplate patchDisplayPageTemplate =
			displayPageTemplateResource.
				patchBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateExternalReferenceCode,
					randomDisplayPageTemplate);

		assertEquals(randomDisplayPageTemplate, patchDisplayPageTemplate);
		assertValid(patchDisplayPageTemplate);

		if (displayPageTemplateFolder == null) {
			displayPageTemplateFolder =
				getDisplayPageTemplate.getParentFolder();
		}

		DisplayPageTemplateFolder curDisplayPageTemplateFolder =
			patchDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			displayPageTemplateFolder.getExternalReferenceCode(),
			curDisplayPageTemplateFolder.getExternalReferenceCode());

		if (markedAsDefault == null) {
			markedAsDefault = getDisplayPageTemplate.getMarkedAsDefault();
		}

		Assert.assertEquals(
			markedAsDefault, patchDisplayPageTemplate.getMarkedAsDefault());
	}

	private void _testPostBySiteExternalReferenceCodeDisplayPageTemplateWithParentFolder()
		throws Exception {

		DisplayPageTemplate displayPageTemplate = randomDisplayPageTemplate();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), testGroup.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						testGroup, TestPropsValues.getUserId()));

		displayPageTemplate.setParentFolder(
			() -> new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						layoutPageTemplateCollection.
							getExternalReferenceCode());
				}
			});

		DisplayPageTemplate postDisplayPageTemplate =
			displayPageTemplateResource.
				postBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(), displayPageTemplate);

		assertEquals(displayPageTemplate, postDisplayPageTemplate);
		assertValid(postDisplayPageTemplate);

		DisplayPageTemplateFolder displayPageTemplateFolder =
			displayPageTemplate.getParentFolder();
		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			postDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			displayPageTemplateFolder.getExternalReferenceCode(),
			postDisplayPageTemplateFolder.getExternalReferenceCode());
	}

	private void _testPutBySiteExternalReferenceCodeDisplayPageTemplate(
			DisplayPageTemplate displayPageTemplate)
		throws Exception {

		DisplayPageTemplate putDisplayPageTemplate =
			displayPageTemplateResource.
				putBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode(),
					displayPageTemplate);

		assertEquals(displayPageTemplate, putDisplayPageTemplate);
		assertValid(putDisplayPageTemplate);

		Assert.assertNull(putDisplayPageTemplate.getParentFolder());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					null, TestPropsValues.getUserId(), testGroup.getGroupId(),
					LayoutPageTemplateConstants.
						PARENT_LAYOUT_PAGE_TEMPLATE_COLLECTION_ID_DEFAULT,
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString(),
					LayoutPageTemplateCollectionTypeConstants.DISPLAY_PAGE,
					ServiceContextTestUtil.getServiceContext(
						testGroup, TestPropsValues.getUserId()));

		displayPageTemplate.setParentFolder(
			new DisplayPageTemplateFolder() {
				{
					setExternalReferenceCode(
						layoutPageTemplateCollection.
							getExternalReferenceCode());
				}
			});

		putDisplayPageTemplate =
			displayPageTemplateResource.
				putBySiteExternalReferenceCodeDisplayPageTemplate(
					testGroup.getExternalReferenceCode(),
					displayPageTemplate.getExternalReferenceCode(),
					displayPageTemplate);

		assertEquals(displayPageTemplate, putDisplayPageTemplate);
		assertValid(putDisplayPageTemplate);

		DisplayPageTemplateFolder curDisplayPageTemplateFolder =
			putDisplayPageTemplate.getParentFolder();

		Assert.assertEquals(
			layoutPageTemplateCollection.getExternalReferenceCode(),
			curDisplayPageTemplateFolder.getExternalReferenceCode());
	}

	private void _updateLayoutPageTemplateEntryStatus(
			String externalReferenceCode)
		throws Exception {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				getLayoutPageTemplateEntryByExternalReferenceCode(
					externalReferenceCode, testGroup.getGroupId());

		_layoutPageTemplateEntryLocalService.updateStatus(
			TestPropsValues.getUserId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Inject
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private StagingLocalService _stagingLocalService;

	@Inject
	private UserLocalService _userLocalService;

}