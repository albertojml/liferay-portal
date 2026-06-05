/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.rest.dto.v1_0.ExportProcessRequest;
import com.liferay.exportimport.rest.dto.v1_0.ImportProcessRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Alberto Moreno
 */
public class ParameterMapUtilTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToParameterMapAddsExportProcessLookAndFeelOptions() {
		ExportProcessRequest exportProcessRequest = new ExportProcessRequest();

		exportProcessRequest.setFavicon(true);
		exportProcessRequest.setLayoutSetSettings(true);
		exportProcessRequest.setLogo(true);
		exportProcessRequest.setThemeReference(true);

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			exportProcessRequest);

		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.FAVICON));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.LAYOUT_SET_SETTINGS));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.LOGO));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.THEME_REFERENCE));
	}

	@Test
	public void testToParameterMapAddsImportProcessLookAndFeelOptions() {
		ImportProcessRequest importProcessRequest = new ImportProcessRequest();

		importProcessRequest.setFavicon(true);
		importProcessRequest.setLayoutSetSettings(true);
		importProcessRequest.setLogo(true);
		importProcessRequest.setThemeReference(true);

		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			importProcessRequest);

		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.FAVICON));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.LAYOUT_SET_SETTINGS));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.LOGO));
		Assert.assertArrayEquals(
			new String[] {"true"},
			parameterMap.get(PortletDataHandlerKeys.THEME_REFERENCE));
	}

	@Test
	public void testToParameterMapOmitsUnsetExportProcessLookAndFeelOptions() {
		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			new ExportProcessRequest());

		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.FAVICON));
		Assert.assertFalse(
			parameterMap.containsKey(
				PortletDataHandlerKeys.LAYOUT_SET_SETTINGS));
		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.LOGO));
		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.THEME_REFERENCE));
	}

	@Test
	public void testToParameterMapOmitsUnsetImportProcessLookAndFeelOptions() {
		Map<String, String[]> parameterMap = ParameterMapUtil.toParameterMap(
			new ImportProcessRequest());

		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.FAVICON));
		Assert.assertFalse(
			parameterMap.containsKey(
				PortletDataHandlerKeys.LAYOUT_SET_SETTINGS));
		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.LOGO));
		Assert.assertFalse(
			parameterMap.containsKey(PortletDataHandlerKeys.THEME_REFERENCE));
	}

}