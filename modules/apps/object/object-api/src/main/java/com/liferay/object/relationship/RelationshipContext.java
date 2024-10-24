/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.relationship;

import com.liferay.object.model.ObjectEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alberto Javier Moreno Lage
 */
public class RelationshipContext {

	public RelationshipContext(int depth) {
		_depth = depth;
	}

	public void addObjectEntry(ObjectEntry objectEntry) {
		_objectEntries.add(objectEntry);
	}

	public void clearObjectEntries() {
		_objectEntries.clear();
	}

	@Override
	public RelationshipContext clone() throws CloneNotSupportedException {
		return (RelationshipContext)super.clone();
	}

	public void decrementCurrentDepth() {
		_currentDepth--;
	}

	public int getCurrentDepth() {
		return _currentDepth;
	}

	public int getDepth() {
		return _depth;
	}

	public List<ObjectEntry> getObjectEntries() {
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

	public void setObjectEntries(List<ObjectEntry> objectEntries) {
		_objectEntries = objectEntries;
	}

	private int _currentDepth;
	private boolean _deactivateRequiredRelationshipValidation;
	private final int _depth;
	private List<ObjectEntry> _objectEntries = new ArrayList<>();

}