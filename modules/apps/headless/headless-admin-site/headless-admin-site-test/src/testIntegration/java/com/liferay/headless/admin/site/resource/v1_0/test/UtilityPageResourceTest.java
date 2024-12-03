/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.site.client.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.client.dto.v1_0.UtilityPage;
import com.liferay.headless.admin.site.client.pagination.Page;
import com.liferay.headless.admin.site.client.pagination.Pagination;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.headless.admin.site.client.resource.v1_0.UtilityPageResource;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;
import com.liferay.layout.utility.page.service.LayoutUtilityPageEntryLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class UtilityPageResourceTest extends BaseUtilityPageResourceTestCase {

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage postUtilityPage =
			testPostBySiteExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		Assert.assertNotNull(
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					postUtilityPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		utilityPageResource.deleteBySiteExternalReferenceCodeUtilityPage(
			testGroup.getExternalReferenceCode(),
			postUtilityPage.getExternalReferenceCode());

		Assert.assertNull(
			_layoutUtilityPageEntryLocalService.
				fetchLayoutUtilityPageEntryByExternalReferenceCode(
					postUtilityPage.getExternalReferenceCode(),
					testGroup.getGroupId()));

		try {
			utilityPageResource.deleteBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				postUtilityPage.getExternalReferenceCode());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage postUtilityPage =
			testPostBySiteExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		UtilityPage getUtilityPage =
			utilityPageResource.getBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				postUtilityPage.getExternalReferenceCode());

		assertEquals(postUtilityPage, getUtilityPage);
		assertValid(getUtilityPage);

		try {
			utilityPageResource.getBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}

		UtilityPageResource curUtilityPageResource = _getUtilityPageResource();

		_assertNestedFields(
			curUtilityPageResource.getBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				postUtilityPage.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPage()
		throws Exception {

		super.testGetBySiteExternalReferenceCodeUtilityPagesPage();

		_testGetBySiteExternalReferenceCodeUtilityPagesPageWithNestedFields();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPageWithPagination()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeUtilityPagesPageWithPagination();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortDateTime()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortDateTime();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortDouble()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortDouble();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortInteger()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortInteger();
	}

	@Ignore
	@Override
	@Test
	public void testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortString()
		throws Exception {

		super.
			testGetBySiteExternalReferenceCodeUtilityPagesPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetSiteSiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testGetSiteSiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	@Test
	public void testPatchBySiteExternalReferenceCodeUtilityPage()
		throws Exception {

		UtilityPage utilityPage =
			testPostBySiteExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		_testPatchBySiteExternalReferenceCodeUtilityPage(
			Boolean.FALSE,
			_getUtilityPage(
				Boolean.FALSE, utilityPage.getExternalReferenceCode()));
		_testPatchBySiteExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(
				Boolean.TRUE, utilityPage.getExternalReferenceCode()));
		_testPatchBySiteExternalReferenceCodeUtilityPage(
			Boolean.TRUE,
			_getUtilityPage(null, utilityPage.getExternalReferenceCode()));

		try {
			utilityPageResource.patchBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				RandomTestUtil.randomString(), randomUtilityPage());

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("NOT_FOUND", problem.getStatus());
			Assert.assertNull(problem.getTitle());
		}
	}

	@Ignore
	@Override
	@Test
	public void testPostBySiteExternalReferenceCodeUtilityPagePageSpecification()
		throws Exception {

		super.testPostBySiteExternalReferenceCodeUtilityPagePageSpecification();
	}

	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeUtilityPage()
		throws Exception {

		_testPutBySiteExternalReferenceCodeUtilityPage(randomUtilityPage());

		UtilityPage utilityPage =
			testPostBySiteExternalReferenceCodeUtilityPage_addUtilityPage(
				randomUtilityPage());

		_testPutBySiteExternalReferenceCodeUtilityPage(
			_getUtilityPage(null, utilityPage.getExternalReferenceCode()));
	}

	@Ignore
	@Override
	@Test
	public void testPutBySiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testPutBySiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Ignore
	@Override
	@Test
	public void testPutSiteSiteExternalReferenceCodeUtilityPagePermissionsPage()
		throws Exception {

		super.testPutSiteSiteExternalReferenceCodeUtilityPagePermissionsPage();
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected UtilityPage randomUtilityPage() throws Exception {
		UtilityPage utilityPage = super.randomUtilityPage();

		utilityPage.setType(UtilityPage.Type.ERROR);

		return utilityPage;
	}

	@Override
	protected UtilityPage
			testGetBySiteExternalReferenceCodeUtilityPagesPage_addUtilityPage(
				String siteExternalReferenceCode, UtilityPage utilityPage)
		throws Exception {

		return utilityPageResource.postBySiteExternalReferenceCodeUtilityPage(
			siteExternalReferenceCode, utilityPage);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeUtilityPagesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeUtilityPagesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected UtilityPage
			testPostBySiteExternalReferenceCodeUtilityPage_addUtilityPage(
				UtilityPage utilityPage)
		throws Exception {

		return testGetBySiteExternalReferenceCodeUtilityPagesPage_addUtilityPage(
			testGroup.getExternalReferenceCode(), utilityPage);
	}

	private void _assertNestedFields(UtilityPage utilityPage) throws Exception {
		FriendlyUrlHistory friendlyUrlHistory =
			utilityPage.getFriendlyUrlHistory();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.getString(friendlyUrlHistory.getFriendlyUrlPath_i18n()));

		LayoutUtilityPageEntry layoutUtilityPageEntry =
			_layoutUtilityPageEntryLocalService.
				getLayoutUtilityPageEntryByExternalReferenceCode(
					utilityPage.getExternalReferenceCode(),
					testGroup.getGroupId());

		Layout layout = _layoutLocalService.getLayout(
			layoutUtilityPageEntry.getPlid());

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

	private UtilityPage _getUtilityPage(
			Boolean markedAsDefault, String masterPageExternalReferenceCode)
		throws Exception {

		UtilityPage utilityPage = randomUtilityPage();

		utilityPage.setExternalReferenceCode(masterPageExternalReferenceCode);
		utilityPage.setMarkedAsDefault(markedAsDefault);

		return utilityPage;
	}

	private UtilityPage _getUtilityPage(
		String externalReferenceCode, List<UtilityPage> utilityPages) {

		for (UtilityPage utilityPage : utilityPages) {
			if (Objects.equals(
					utilityPage.getExternalReferenceCode(),
					externalReferenceCode)) {

				return utilityPage;
			}
		}

		return null;
	}

	private UtilityPageResource _getUtilityPageResource() throws Exception {
		User omniadminUser = UserTestUtil.addOmniadminUser();

		String password = RandomTestUtil.randomString();

		_userLocalService.updatePassword(
			omniadminUser.getUserId(), password, password, false, true);

		return UtilityPageResource.builder(
		).authentication(
			omniadminUser.getEmailAddress(), password
		).locale(
			LocaleUtil.getDefault()
		).parameters(
			"nestedFields", "friendlyUrlHistory"
		).build();
	}

	private void _testGetBySiteExternalReferenceCodeUtilityPagesPageWithNestedFields()
		throws Exception {

		Page<UtilityPage> page =
			utilityPageResource.getBySiteExternalReferenceCodeUtilityPagesPage(
				testGroup.getExternalReferenceCode(), null, null, null,
				Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		UtilityPage utilityPage =
			testGetBySiteExternalReferenceCodeUtilityPagesPage_addUtilityPage(
				testGroup.getExternalReferenceCode(), randomUtilityPage());

		UtilityPageResource curUtilityPageResource = _getUtilityPageResource();

		page =
			curUtilityPageResource.
				getBySiteExternalReferenceCodeUtilityPagesPage(
					testGroup.getExternalReferenceCode(), null, null, null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 1, page.getTotalCount());

		_assertNestedFields(
			_getUtilityPage(
				utilityPage.getExternalReferenceCode(),
				(List<UtilityPage>)page.getItems()));
	}

	private void _testPatchBySiteExternalReferenceCodeUtilityPage(
			Boolean expectedMarkedAsDefault, UtilityPage utilityPage)
		throws Exception {

		UtilityPage pathUtilityPage =
			utilityPageResource.patchBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				utilityPage.getExternalReferenceCode(), utilityPage);

		assertEquals(utilityPage, pathUtilityPage);
		assertValid(pathUtilityPage);

		Assert.assertEquals(
			expectedMarkedAsDefault, pathUtilityPage.getMarkedAsDefault());
	}

	private void _testPutBySiteExternalReferenceCodeUtilityPage(
			UtilityPage utilityPage)
		throws Exception {

		UtilityPage putUtilityPage =
			utilityPageResource.putBySiteExternalReferenceCodeUtilityPage(
				testGroup.getExternalReferenceCode(),
				utilityPage.getExternalReferenceCode(), utilityPage);

		assertEquals(utilityPage, putUtilityPage);
		assertValid(putUtilityPage);
	}

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutUtilityPageEntryLocalService
		_layoutUtilityPageEntryLocalService;

	@Inject
	private UserLocalService _userLocalService;

}