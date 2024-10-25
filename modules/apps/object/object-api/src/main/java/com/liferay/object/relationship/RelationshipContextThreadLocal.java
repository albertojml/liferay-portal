/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.relationship;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Alberto Javier Moreno Lage
 */
public class RelationshipContextThreadLocal {

	public static RelationshipContext getAndSetRelationshipContext(
		RelationshipContext relationshipContext) {

		RelationshipContext oldRelationshipContext = getRelationshipContext();

		setRelationshipContextThreadLocal(relationshipContext);

		return oldRelationshipContext;
	}

	public static RelationshipContext getRelationshipContext() {
		return _relationshipContextThreadLocal.get();
	}

	public static void setRelationshipContextThreadLocal(
		RelationshipContext relationshipContext) {

		_relationshipContextThreadLocal.set(relationshipContext);
	}

	private static final CentralizedThreadLocal<RelationshipContext>
		_relationshipContextThreadLocal = new CentralizedThreadLocal<>(
			RelationshipContextThreadLocal.class +
				"._nestedFieldsContextThreadLocal");

}