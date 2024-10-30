/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.relationship;

import com.liferay.object.model.ObjectEntry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alberto Javier Moreno Lage
 */
public class RelationshipContext {

	public void addObjectEntry(ObjectEntry objectEntry) {
		_objectEntries.put(objectEntry.getObjectEntryId(), objectEntry);
	}

	public void decrementCurrentDepth() {
		_currentDepth--;
	}

	public int getCurrentDepth() {
		return _currentDepth;
	}

	public Map<Long, ObjectEntry> getObjectEntries() {
		return _objectEntries;
	}

	public void incrementCurrentDepth() {
		_currentDepth++;
	}

	public boolean isDeactivateRequiredRelationshipValidation() {
		return _deactivateRequiredRelationshipValidation;
	}

	public void setDeactivateRequiredRelationshipValidation(
		boolean deactivateRequiredRelationshipValidation) {

		_deactivateRequiredRelationshipValidation =
			deactivateRequiredRelationshipValidation;
	}

	public void setObjectEntries(Map<Long, ObjectEntry> objectEntries) {
		_objectEntries = objectEntries;
	}

	private int _currentDepth;
	private boolean _deactivateRequiredRelationshipValidation;
	private Map<Long, ObjectEntry> _objectEntries = new HashMap<>();

}