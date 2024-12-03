/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.PageTemplateSet;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
public class PageTemplateSetResourceTest
	extends BasePageTemplateSetResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet =
			testGetBySiteExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				testGroup.getExternalReferenceCode(), randomPageTemplateSet());

		pageTemplateSetResource.
			deleteBySiteExternalReferenceCodePageTemplateSet(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateCollectionLocalService.
				fetchLayoutPageTemplateCollectionByExternalReferenceCode(
					pageTemplateSet.getExternalReferenceCode(),
					testGroup.getGroupId()));

		PageTemplateSet liveGroupPageTemplateSet =
			testGetBySiteExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				testGroup.getExternalReferenceCode(), randomPageTemplateSet());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateSetResource.
					deleteBySiteExternalReferenceCodePageTemplateSet(
						testGroup.getExternalReferenceCode(),
						liveGroupPageTemplateSet.getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet =
			testGetBySiteExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				testGroup.getExternalReferenceCode(), randomPageTemplateSet());

		_testGetBySiteExternalReferenceCodePageTemplateSet(pageTemplateSet);

		_enableLocalStaging();

		_testGetBySiteExternalReferenceCodePageTemplateSet(pageTemplateSet);
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodePageTemplateSetsPageWithPagination()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodePageTemplateSetsPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet = randomPageTemplateSet();

		pageTemplateSetResource.putBySiteExternalReferenceCodePageTemplateSet(
			testGroup.getExternalReferenceCode(),
			pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);

		pageTemplateSet.setDescription(RandomTestUtil.randomString());

		PageTemplateSet patchPageTemplateSet =
			pageTemplateSetResource.
				patchBySiteExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, patchPageTemplateSet);
		assertValid(patchPageTemplateSet);

		pageTemplateSet.setName(RandomTestUtil.randomString());

		patchPageTemplateSet =
			pageTemplateSetResource.
				patchBySiteExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, patchPageTemplateSet);
		assertValid(patchPageTemplateSet);

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateSetResource.
					patchBySiteExternalReferenceCodePageTemplateSet(
						testGroup.getExternalReferenceCode(),
						pageTemplateSet.getExternalReferenceCode(),
						pageTemplateSet));
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodePageTemplateSet()
		throws Exception {

		PageTemplateSet pageTemplateSet = randomPageTemplateSet();

		PageTemplateSet putPageTemplateSet =
			pageTemplateSetResource.
				putBySiteExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode(),
					pageTemplateSet);

		assertEquals(pageTemplateSet, putPageTemplateSet);
		assertValid(putPageTemplateSet);

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				pageTemplateSetResource.
					putBySiteExternalReferenceCodePageTemplateSet(
						testGroup.getExternalReferenceCode(),
						pageTemplateSet.getExternalReferenceCode(),
						pageTemplateSet));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testPutBySiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodePageTemplateSetPermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "externalReferenceCode", "name"};
	}

	@Override
	protected PageTemplateSet
			testGetBySiteExternalReferenceCodePageTemplateSetsPage_addPageTemplateSet(
				String siteExternalReferenceCode,
				PageTemplateSet pageTemplateSet)
		throws Exception {

		return pageTemplateSetResource.
			putBySiteExternalReferenceCodePageTemplateSet(
				siteExternalReferenceCode,
				pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodePageTemplateSetsPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected PageTemplateSet
			testPostBySiteExternalReferenceCodePageTemplateSet_addPageTemplateSet(
				PageTemplateSet pageTemplateSet)
		throws Exception {

		return pageTemplateSetResource.
			putBySiteExternalReferenceCodePageTemplateSet(
				testGroup.getExternalReferenceCode(),
				pageTemplateSet.getExternalReferenceCode(), pageTemplateSet);
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

	private void _testGetBySiteExternalReferenceCodePageTemplateSet(
			PageTemplateSet pageTemplateSet)
		throws Exception {

		PageTemplateSet getPageTemplateSet =
			pageTemplateSetResource.
				getBySiteExternalReferenceCodePageTemplateSet(
					testGroup.getExternalReferenceCode(),
					pageTemplateSet.getExternalReferenceCode());

		assertEquals(pageTemplateSet, getPageTemplateSet);
		assertValid(getPageTemplateSet);
	}

	@Inject
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}