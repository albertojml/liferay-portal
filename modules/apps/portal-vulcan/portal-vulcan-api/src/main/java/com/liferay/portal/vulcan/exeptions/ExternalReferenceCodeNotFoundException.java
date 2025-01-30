/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.exeptions;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ExternalReferenceCodeNotFoundException extends PortalException {

	public ExternalReferenceCodeNotFoundException(
		String className, String externalReferenceCode, String scope,
		String scopeKey) {

		super(
			StringBundler.concat(
				className, " with externalReferenceCode ",
				externalReferenceCode, " could not be found in ", scope, " ",
				scopeKey));

		_className = className;
		_externalReferenceCode = externalReferenceCode;
		_scope = scope;
		_scopeKey = scopeKey;

		_taskItemDelegateName = null;
	}

	public ExternalReferenceCodeNotFoundException(
		String className, String externalReferenceCode, String scope,
		String scopeKey, String taskItemDelegateName) {

		super(
			StringBundler.concat(
				className, "#", taskItemDelegateName,
				" with externalReferenceCode ", externalReferenceCode,
				" could not be found in ", scope, " ", scopeKey));

		_className = className;
		_externalReferenceCode = externalReferenceCode;
		_scope = scope;
		_scopeKey = scopeKey;
		_taskItemDelegateName = taskItemDelegateName;
	}

	public String getClassName() {
		return _className;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getScope() {
		return _scope;
	}

	public String getScopeKey() {
		return _scopeKey;
	}

	public String getTaskItemDelegateName() {
		return _taskItemDelegateName;
	}

	private final String _className;
	private final String _externalReferenceCode;
	private final String _scope;
	private final String _scopeKey;
	private final String _taskItemDelegateName;

}