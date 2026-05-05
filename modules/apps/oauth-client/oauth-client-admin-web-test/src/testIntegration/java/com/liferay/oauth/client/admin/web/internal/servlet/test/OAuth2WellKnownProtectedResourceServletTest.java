/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.admin.web.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth.client.persistence.model.OAuthClientPRLocalMetadata;
import com.liferay.oauth.client.persistence.service.OAuthClientPRLocalMetadataLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Moreno
 */
@FeatureFlag("LPD-XXXXX")
@RunWith(Arquillian.class)
public class OAuth2WellKnownProtectedResourceServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() throws Exception {
		for (OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata :
				_oAuthClientPRLocalMetadataLocalService.
					getCompanyOAuthClientPRLocalMetadata(
						TestPropsValues.getCompanyId())) {

			_oAuthClientPRLocalMetadataLocalService.
				deleteOAuthClientPRLocalMetadata(
					oAuthClientPRLocalMetadata.
						getOAuthClientPRLocalMetadataId());
		}
	}

	@Test
	public void testDoGet() throws Exception {
		Http.Options options = new Http.Options();

		options.setFollowRedirects(false);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		String baseURL = StringBundler.concat(
			Http.HTTP_WITH_SLASH, company.getVirtualHostname(),
			":8080/o/.well-known/oauth-protected-resource");

		options.setLocation(baseURL);

		HttpUtil.URLtoString(options);

		List<OAuthClientPRLocalMetadata> oAuthClientPRLocalMetadatas =
			_oAuthClientPRLocalMetadataLocalService.
				getCompanyOAuthClientPRLocalMetadata(
					TestPropsValues.getCompanyId());

		Assert.assertTrue(oAuthClientPRLocalMetadatas.isEmpty());

		Http.Response response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		String resource1 =
			Http.HTTPS_WITH_SLASH + RandomTestUtil.randomString() + ".com";
		String authorizationServer1 = Http.HTTPS_WITH_SLASH +
			RandomTestUtil.randomString() + ".com";
		String scope1 = RandomTestUtil.randomString();

		OAuthClientPRLocalMetadata oAuthClientPRLocalMetadata1 =
			_oAuthClientPRLocalMetadataLocalService.
				addOAuthClientPRLocalMetadata(
					null, TestPropsValues.getUserId(), resource1,
					new String[] {authorizationServer1},
					new String[] {"header"}, resource1, true,
					"https://docs.example.com", new String[] {scope1}, false,
					null);

		// Bare /.well-known/oauth-protected-resource lookup falls back to the
		// single enabled record.

		options.setLocation(baseURL);

		String responseJSON = HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_OK, response.getResponseCode());

		Assert.assertEquals(
			responseJSON, oAuthClientPRLocalMetadata1.getMetadataJSON());

		// Disabling the entry should yield a 404.

		_oAuthClientPRLocalMetadataLocalService.
			updateOAuthClientPRLocalMetadata(
				oAuthClientPRLocalMetadata1.getOAuthClientPRLocalMetadataId(),
				resource1, new String[] {authorizationServer1},
				new String[] {"header"}, resource1, false,
				"https://docs.example.com", new String[] {scope1}, false, null);

		options.setLocation(baseURL);

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());

		// A path that does not match any persisted record should be 404.

		options.setLocation(
			StringBundler.concat(
				baseURL, "/", RandomTestUtil.randomString(), ".com"));

		HttpUtil.URLtoString(options);

		response = options.getResponse();

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND, response.getResponseCode());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private OAuthClientPRLocalMetadataLocalService
		_oAuthClientPRLocalMetadataLocalService;

}
