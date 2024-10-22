/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.relationship;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Alberto Javier Moreno Lage
 */
public class RelationshipValidationThreadLocal {

	public static Boolean getDeactivateRequiredRelationshipValidation() {
		return _deactivateRequiredRelationshipValidation.get();
	}

	public static void setDeactivateRequiredRelationshipValidation(
		boolean deactivateRequiredRelationshipValidation) {

		_deactivateRequiredRelationshipValidation.set(
			deactivateRequiredRelationshipValidation);
	}

	private static final CentralizedThreadLocal<Boolean>
		_deactivateRequiredRelationshipValidation =
			new CentralizedThreadLocal<>(
				RelationshipValidationThreadLocal.class +
					"._deactivateRequiredRelationshipValidation",
				() -> Boolean.FALSE);

}