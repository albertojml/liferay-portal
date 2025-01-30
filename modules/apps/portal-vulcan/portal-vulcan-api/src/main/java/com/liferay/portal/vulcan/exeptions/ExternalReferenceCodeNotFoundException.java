/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.exeptions;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ExternalReferenceCodeNotFoundException
	extends NoSuchModelException {

	public ExternalReferenceCodeNotFoundException(
		String className, String externalReferenceCode, String message,
		String scope, String scopeKey, String taskItemDelegateName) {

		super(message);

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