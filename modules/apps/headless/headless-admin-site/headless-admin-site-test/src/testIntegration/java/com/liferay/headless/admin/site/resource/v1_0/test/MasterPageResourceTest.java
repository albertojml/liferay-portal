/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.MasterPage;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
public class MasterPageResourceTest extends BaseMasterPageResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage postMasterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		Assert.assertNotNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postMasterPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		masterPageResource.deleteBySiteExternalReferenceCodeMasterPage(
			testGroup.getExternalReferenceCode(),
			postMasterPage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntryByExternalReferenceCode(
					postMasterPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		_assertProblemException(
			"NOT_FOUND",
			() ->
				masterPageResource.deleteBySiteExternalReferenceCodeMasterPage(
					testGroup.getExternalReferenceCode(),
					postMasterPage.getExternalReferenceCode()));

		MasterPage liveGroupMasterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				masterPageResource.deleteBySiteExternalReferenceCodeMasterPage(
					testGroup.getExternalReferenceCode(),
					liveGroupMasterPage.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage masterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_testGetBySiteExternalReferenceCodeMasterPage(masterPage);

		_assertProblemException(
			"NOT_FOUND",
			() -> masterPageResource.getBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString()));

		_enableLocalStaging();

		_testGetBySiteExternalReferenceCodeMasterPage(masterPage);
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagesPageWithPagination()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeMasterPagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagesPageWithSortDateTime()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeMasterPagesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagesPageWithSortDouble()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeMasterPagesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagesPageWithSortInteger()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeMasterPagesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeMasterPagesPageWithSortString()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeMasterPagesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodeMasterPage()
		throws Exception {

		MasterPage masterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_updateLayoutPageTemplateEntryStatus(
			masterPage.getExternalReferenceCode());

		_testPatchBySiteExternalReferenceCodeMasterPage(
			Boolean.TRUE,
			_getMasterPage(
				Boolean.TRUE, masterPage.getExternalReferenceCode()));

		_testPatchBySiteExternalReferenceCodeMasterPage(
			Boolean.TRUE,
			_getMasterPage(null, masterPage.getExternalReferenceCode()));

		_testPatchBySiteExternalReferenceCodeMasterPage(
			Boolean.FALSE,
			_getMasterPage(
				Boolean.FALSE, masterPage.getExternalReferenceCode()));

		_assertProblemException(
			"NOT_FOUND",
			() -> masterPageResource.patchBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), randomMasterPage()));

		MasterPage liveGroupMasterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> masterPageResource.patchBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				liveGroupMasterPage.getExternalReferenceCode(),
				_getMasterPage(
					null, liveGroupMasterPage.getExternalReferenceCode())));
	}

	@Ignore
	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeMasterPagePageSpecification()
		throws Exception {

		super.testPostBySiteExternalReferenceCodeMasterPagePageSpecification();
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeMasterPage()
		throws Exception {

		_testPutBySiteExternalReferenceCodeMasterPage(randomMasterPage());

		MasterPage masterPage =
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				randomMasterPage());

		_testPutBySiteExternalReferenceCodeMasterPage(
			_getMasterPage(null, masterPage.getExternalReferenceCode()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() -> masterPageResource.putBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode(),
				_getMasterPage(null, masterPage.getExternalReferenceCode())));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testPutBySiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeMasterPagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeMasterPagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected MasterPage randomMasterPage() throws Exception {
		MasterPage masterPage = super.randomMasterPage();

		masterPage.setMarkedAsDefault(Boolean.FALSE);

		return masterPage;
	}

	@Override
	protected MasterPage
			testGetBySiteExternalReferenceCodeMasterPagesPage_addMasterPage(
				String siteExternalReferenceCode, MasterPage masterPage)
		throws Exception {

		return masterPageResource.postBySiteExternalReferenceCodeMasterPage(
			siteExternalReferenceCode, masterPage);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeMasterPagesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeMasterPagesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected MasterPage
			testPostBySiteExternalReferenceCodeMasterPage_addMasterPage(
				MasterPage masterPage)
		throws Exception {

		return testGetBySiteExternalReferenceCodeMasterPagesPage_addMasterPage(
			testGroup.getExternalReferenceCode(), masterPage);
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

	private MasterPage _getMasterPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		MasterPage masterPage = randomMasterPage();

		masterPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		masterPage.setMarkedAsDefault(markedAsDefault);

		return masterPage;
	}

	private void _testGetBySiteExternalReferenceCodeMasterPage(
			MasterPage masterPage)
		throws Exception {

		MasterPage getMasterPage =
			masterPageResource.getBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode());

		assertEquals(masterPage, getMasterPage);
		assertValid(getMasterPage);
	}

	private void _testPatchBySiteExternalReferenceCodeMasterPage(
			Boolean expectedMarkedAsDefault, MasterPage masterPage)
		throws Exception {

		MasterPage patchMasterPage =
			masterPageResource.patchBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode(), masterPage);

		assertEquals(masterPage, patchMasterPage);
		assertValid(patchMasterPage);

		Assert.assertEquals(
			expectedMarkedAsDefault, patchMasterPage.getMarkedAsDefault());
	}

	private void _testPutBySiteExternalReferenceCodeMasterPage(
			MasterPage masterPage)
		throws Exception {

		MasterPage putMasterPage =
			masterPageResource.putBySiteExternalReferenceCodeMasterPage(
				testGroup.getExternalReferenceCode(),
				masterPage.getExternalReferenceCode(), masterPage);

		assertEquals(masterPage, putMasterPage);
		assertValid(putMasterPage);
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
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}