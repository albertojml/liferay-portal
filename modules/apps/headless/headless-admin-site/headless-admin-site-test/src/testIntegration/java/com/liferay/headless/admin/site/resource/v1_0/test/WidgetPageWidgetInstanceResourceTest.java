/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.headless.admin.site.client.dto.v1_0.WidgetPageWidgetInstance;
import com.liferay.headless.admin.site.client.problem.Problem;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@FeatureFlags("LPD-35443")
@RunWith(Arquillian.class)
public class WidgetPageWidgetInstanceResourceTest
	extends BaseWidgetPageWidgetInstanceResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_layout = LayoutTestUtil.addTypePortletLayout(testGroup.getGroupId());
	}

	@Override
	@Test
	public void testDeleteBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			testPostBySiteExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)_layout.getLayoutType();

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		Assert.assertTrue(layoutTypePortlet.hasPortletId(portletId));

		widgetPageWidgetInstanceResource.
			deleteBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(), portletId);

		_layout = _layoutLocalService.fetchLayout(_layout.getPlid());

		layoutTypePortlet = (LayoutTypePortlet)_layout.getLayoutType();

		Assert.assertFalse(layoutTypePortlet.hasPortletId(portletId));

		try {
			widgetPageWidgetInstanceResource.
				deleteBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId);

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
	public void testGetBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostBySiteExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		String portletId = PortletIdCodec.encode(
			postWidgetPageWidgetInstance.getWidgetName(),
			postWidgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance getWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				getBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId);

		assertEquals(postWidgetPageWidgetInstance, getWidgetPageWidgetInstance);
		assertValid(getWidgetPageWidgetInstance);

		try {
			widgetPageWidgetInstanceResource.
				getBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(),
					RandomTestUtil.randomString());

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
	public void testPatchBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance postWidgetPageWidgetInstance =
			testPostBySiteExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				randomWidgetPageWidgetInstance());

		String portletId = PortletIdCodec.encode(
			postWidgetPageWidgetInstance.getWidgetName(),
			postWidgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance patchWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				patchBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId,
					postWidgetPageWidgetInstance);

		assertEquals(
			postWidgetPageWidgetInstance, patchWidgetPageWidgetInstance);
		assertValid(patchWidgetPageWidgetInstance);

		try {
			widgetPageWidgetInstanceResource.
				patchBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(),
					RandomTestUtil.randomString(),
					randomWidgetPageWidgetInstance());

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
	public void testPutBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode()
		throws Exception {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			randomWidgetPageWidgetInstance();

		String portletId = PortletIdCodec.encode(
			widgetPageWidgetInstance.getWidgetName(),
			widgetPageWidgetInstance.getWidgetInstanceId());

		WidgetPageWidgetInstance putWidgetPageWidgetInstance =
			widgetPageWidgetInstanceResource.
				putBySiteExternalReferenceCodeWidgetInstanceWidgetInstanceExternalReferenceCode(
					testGroup.getExternalReferenceCode(),
					_layout.getExternalReferenceCode(), portletId,
					widgetPageWidgetInstance);

		assertEquals(widgetPageWidgetInstance, putWidgetPageWidgetInstance);
		assertValid(putWidgetPageWidgetInstance);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"externalReferenceCode", "parentSectionId", "position",
			"widgetInstanceId", "widgetName"
		};
	}

	@Override
	protected WidgetPageWidgetInstance randomWidgetPageWidgetInstance()
		throws Exception {

		WidgetPageWidgetInstance widgetPageWidgetInstance =
			new WidgetPageWidgetInstance();

		String portletName = AssetPublisherPortletKeys.ASSET_PUBLISHER;

		String portletId = PortletIdCodec.encode(portletName);

		widgetPageWidgetInstance.setExternalReferenceCode(portletId);

		widgetPageWidgetInstance.setParentSectionId("column-1");
		widgetPageWidgetInstance.setPosition(_position++);
		widgetPageWidgetInstance.setWidgetInstanceId(
			PortletIdCodec.decodeInstanceId(portletId));
		widgetPageWidgetInstance.setWidgetName(portletName);

		return widgetPageWidgetInstance;
	}

	@Override
	protected WidgetPageWidgetInstance
			testGetBySiteExternalReferenceCodeSitePageWidgetInstancesPage_addWidgetPageWidgetInstance(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode,
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		return widgetPageWidgetInstanceResource.
			postBySiteExternalReferenceCodeSitePageWidgetInstance(
				siteExternalReferenceCode, sitePageExternalReferenceCode,
				widgetPageWidgetInstance);
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeSitePageWidgetInstancesPage_getIrrelevantSiteExternalReferenceCode()
		throws Exception {

		return irrelevantGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeSitePageWidgetInstancesPage_getSiteExternalReferenceCode()
		throws Exception {

		return testGroup.getExternalReferenceCode();
	}

	@Override
	protected String
			testGetBySiteExternalReferenceCodeSitePageWidgetInstancesPage_getSitePageExternalReferenceCode()
		throws Exception {

		return _layout.getExternalReferenceCode();
	}

	@Override
	protected WidgetPageWidgetInstance
			testPostBySiteExternalReferenceCodeSitePageWidgetInstance_addWidgetPageWidgetInstance(
				WidgetPageWidgetInstance widgetPageWidgetInstance)
		throws Exception {

		return widgetPageWidgetInstanceResource.
			postBySiteExternalReferenceCodeSitePageWidgetInstance(
				testGroup.getExternalReferenceCode(),
				_layout.getExternalReferenceCode(), widgetPageWidgetInstance);
	}

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private int _position;

}