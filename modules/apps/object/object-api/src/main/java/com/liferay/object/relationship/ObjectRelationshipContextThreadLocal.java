/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.relationship;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Alberto Javier Moreno Lage
 */
public class ObjectRelationshipContextThreadLocal {

	public static ObjectRelationshipContext getObjectRelationshipContext() {
		return _objectRelationshipContextThreadLocal.get();
	}

	public static void setObjectRelationshipContextThreadLocal(
		ObjectRelationshipContext relationshipContext) {

		_objectRelationshipContextThreadLocal.set(relationshipContext);
	}

	private static final CentralizedThreadLocal<ObjectRelationshipContext>
		_objectRelationshipContextThreadLocal = new CentralizedThreadLocal<>(
			ObjectRelationshipContextThreadLocal.class +
				"._objectRelationshipContextThreadLocal");

}