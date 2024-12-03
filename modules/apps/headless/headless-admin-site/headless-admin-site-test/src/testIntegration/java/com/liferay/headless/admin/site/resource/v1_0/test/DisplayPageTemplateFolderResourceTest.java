/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.headless.admin.site.client.dto.v1_0.DisplayPageTemplateFolder;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
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
 * @author Bárbara Cabrera
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class DisplayPageTemplateFolderResourceTest
	extends BaseDisplayPageTemplateFolderResourceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		displayPageTemplateFolderResource.
			deleteBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				postDisplayPageTemplateFolder.getExternalReferenceCode());

		Assert.assertNull(
			_layoutPageTemplateCollectionService.
				fetchLayoutPageTemplateCollection(
					postDisplayPageTemplateFolder.getExternalReferenceCode(),
					testGroup.getGroupId()));

		DisplayPageTemplateFolder liveGroupDisplayPageTemplateFolder =
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateFolderResource.
					deleteBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplateFolder.
							getExternalReferenceCode()));
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				testGroup.getExternalReferenceCode(),
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getBySiteExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					postDisplayPageTemplateFolder.getExternalReferenceCode());

		assertEquals(
			postDisplayPageTemplateFolder, getDisplayPageTemplateFolder);
		assertValid(getDisplayPageTemplateFolder);

		_enableLocalStaging();

		assertEquals(
			postDisplayPageTemplateFolder,
			displayPageTemplateFolderResource.
				getBySiteExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					postDisplayPageTemplateFolder.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testGetSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder displayPageTemplateFolder =
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		Assert.assertNull(
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		_testPatchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(),
			parentDisplayPageTemplateFolder.getExternalReferenceCode());

		_testPatchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
			displayPageTemplateFolder.getExternalReferenceCode(), null);

		_assertProblemException(
			"NOT_FOUND",
			() ->
				displayPageTemplateFolderResource.
					patchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						RandomTestUtil.randomString(),
						randomDisplayPageTemplateFolder()));

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateFolderResource.
					patchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						displayPageTemplateFolder.getExternalReferenceCode(),
						displayPageTemplateFolder));
	}

	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		super.testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder();

		_testPostBySiteExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode();

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateFolderResource.
					postBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						randomDisplayPageTemplateFolder()));
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			_testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(),
				RandomTestUtil.randomString());

		Assert.assertNull(
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		displayPageTemplateFolder =
			_testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(),
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		Assert.assertEquals(
			parentDisplayPageTemplateFolder.getExternalReferenceCode(),
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		displayPageTemplateFolder =
			_testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				displayPageTemplateFolder, null);

		Assert.assertEquals(
			parentDisplayPageTemplateFolder.getExternalReferenceCode(),
			displayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());

		DisplayPageTemplateFolder liveGroupDisplayPageTemplateFolder =
			_testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder(),
				RandomTestUtil.randomString());

		_enableLocalStaging();

		_assertProblemException(
			"BAD_REQUEST",
			() ->
				displayPageTemplateFolderResource.
					putBySiteExternalReferenceCodeDisplayPageTemplateFolder(
						testGroup.getExternalReferenceCode(),
						liveGroupDisplayPageTemplateFolder.
							getExternalReferenceCode(),
						parentDisplayPageTemplateFolder));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testPutBySiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage()
		throws Exception {

		super.
			testPutSiteSiteExternalReferenceCodeDisplayPageTemplateFolderPermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "externalReferenceCode", "name"};
	}

	@Override
	protected DisplayPageTemplateFolder randomDisplayPageTemplateFolder()
		throws Exception {

		DisplayPageTemplateFolder displayPageTemplateFolder =
			super.randomDisplayPageTemplateFolder();

		displayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				(String)null);

		return displayPageTemplateFolder;
	}

	@Override
	protected DisplayPageTemplateFolder
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
				String siteExternalReferenceCode,
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return displayPageTemplateFolderResource.
			postBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				siteExternalReferenceCode, displayPageTemplateFolder);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected DisplayPageTemplateFolder
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				DisplayPageTemplateFolder displayPageTemplateFolder)
		throws Exception {

		return testGetBySiteExternalReferenceCodeDisplayPageTemplateFoldersPage_addDisplayPageTemplateFolder(
			testGroup.getExternalReferenceCode(), displayPageTemplateFolder);
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

	private void _testPatchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
			String displayPageTemplateFolderExternalReferenceCode,
			String parentDisplayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		DisplayPageTemplateFolder getDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				getBySiteExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.setExternalReferenceCode(
			displayPageTemplateFolderExternalReferenceCode);
		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder patchDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				patchBySiteExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolderExternalReferenceCode,
					randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, patchDisplayPageTemplateFolder);
		assertValid(patchDisplayPageTemplateFolder);

		if (parentDisplayPageTemplateFolderExternalReferenceCode == null) {
			parentDisplayPageTemplateFolderExternalReferenceCode =
				getDisplayPageTemplateFolder.
					getParentDisplayPageTemplateFolderExternalReferenceCode();
		}

		Assert.assertEquals(
			parentDisplayPageTemplateFolderExternalReferenceCode,
			patchDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
	}

	private void _testPostBySiteExternalReferenceCodeDisplayPageTemplateFolderWithExistingParentExternalReferenceCode()
		throws Exception {

		DisplayPageTemplateFolder parentDisplayPageTemplateFolder =
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder());

		DisplayPageTemplateFolder randomDisplayPageTemplateFolder =
			randomDisplayPageTemplateFolder();

		randomDisplayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolder.getExternalReferenceCode());

		DisplayPageTemplateFolder postDisplayPageTemplateFolder =
			testPostBySiteExternalReferenceCodeDisplayPageTemplateFolder_addDisplayPageTemplateFolder(
				randomDisplayPageTemplateFolder);

		assertEquals(
			randomDisplayPageTemplateFolder, postDisplayPageTemplateFolder);
		Assert.assertEquals(
			randomDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode(),
			postDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
		assertValid(postDisplayPageTemplateFolder);
		Assert.assertNotNull(
			postDisplayPageTemplateFolder.
				getParentDisplayPageTemplateFolderExternalReferenceCode());
	}

	private DisplayPageTemplateFolder
			_testPutBySiteExternalReferenceCodeDisplayPageTemplateFolder(
				DisplayPageTemplateFolder displayPageTemplateFolder,
				String parentDisplayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		displayPageTemplateFolder.
			setParentDisplayPageTemplateFolderExternalReferenceCode(
				parentDisplayPageTemplateFolderExternalReferenceCode);

		DisplayPageTemplateFolder putDisplayPageTemplateFolder =
			displayPageTemplateFolderResource.
				putBySiteExternalReferenceCodeDisplayPageTemplateFolder(
					testGroup.getExternalReferenceCode(),
					displayPageTemplateFolder.getExternalReferenceCode(),
					displayPageTemplateFolder);

		assertEquals(displayPageTemplateFolder, putDisplayPageTemplateFolder);
		assertValid(putDisplayPageTemplateFolder);

		return putDisplayPageTemplateFolder;
	}

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Inject
	private StagingLocalService _stagingLocalService;

}