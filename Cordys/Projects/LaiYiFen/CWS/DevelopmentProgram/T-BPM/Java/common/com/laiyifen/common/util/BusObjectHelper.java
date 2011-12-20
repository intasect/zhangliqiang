package com.laiyifen.common.util;

import java.util.Enumeration;
import com.cordys.cpc.bsf.busobject.BusObject;
import com.cordys.cpc.bsf.busobject.BusObjectIterator;
import com.cordys.cpc.bsf.classinfo.AttributeInfo;
import com.cordys.cpc.bsf.classinfo.ClassInfo;
import com.cordys.cpc.bsf.classinfo.RelationInfo_Composite;
import com.cordys.cpc.bsf.relation.MultiRelation;
import com.cordys.cpc.bsf.relation.SingleRelation;

public class BusObjectHelper {

	/**
	 * Recursively goes through the object and removes all the empty children.
	 * 
	 * @param claimCase
	 * @return true if the object is empty
	 */
	public static boolean removeEmptyChildren(BusObject object) {
		if (object == null) {
			return true;
		}
		ClassInfo classInfo = object._classInfo();
		Enumeration<?> relations = classInfo.getRelationInfos();
		boolean isEmpty = true;
		while (relations.hasMoreElements()) {
			Object o = relations.nextElement();
			if (o instanceof RelationInfo_Composite) {
				RelationInfo_Composite relation = (RelationInfo_Composite) o;
				if (relation.isMultiOcc()) {
					MultiRelation childrenRelation = object._getMultiRelation(
							relation.getIdentifier(), true);
					childrenRelation.load();
					BusObjectIterator<?> children = childrenRelation
							.getObjects();
					while (children.hasMoreElements()) {
						BusObject child = children.nextElement();
						if (isNullObject(child)) {
							if (removeEmptyChildren(child)) {
								childrenRelation.removeObject(child);
							} else {
								isEmpty = false;
							}
						} else {
							removeEmptyChildren(child);
							isEmpty = false;
						}
					}
				} else {
					SingleRelation childRelation = object._getSingleRelation(
							relation.getIdentifier(), true);
					childRelation.load();
					BusObject child = childRelation.getLocalObject();
					if (isNullObject(child)) {
						if (removeEmptyChildren(child)) {
							childRelation.setLocalObject(null);
						} else {
							isEmpty = false;
						}
					} else {
						removeEmptyChildren(child);
						isEmpty = false;
					}
				}
			}
		}
		return isEmpty;
	}

	/**
	 * Tests whether the object is null or whether all attributes inside the
	 * object are null.
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNullObject(BusObject object) {
		if (object == null) {
			return true;
		}
		AttributeInfo[] attributes = object._classInfo().getAttributeInfos2();
		for (AttributeInfo attributeInfo : attributes) {
			if (!object.isNull(attributeInfo.getName()))
				return false;
		}
		return true;
	}

	/**
	 * Unlink the children of the specific class types from the parent
	 * busobject.
	 * 
	 * @param <T>
	 * @param busObject
	 * @param relationID
	 * @param childClass
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BusObject> void unlinkChildren(
			BusObject busObject, Class<?> childClass) {
		Enumeration<?> relations = busObject._classInfo().getRelationInfos();
		while (relations.hasMoreElements()) {
			Object o = relations.nextElement();
			if (o instanceof RelationInfo_Composite) {
				RelationInfo_Composite relation = (RelationInfo_Composite) o;
				try {
					if (relation.getRelatedClass().equals(childClass)) {
						if (relation.isMultiOcc()) {
							MultiRelation relationChildren = busObject
									._getMultiRelation(
											relation.getIdentifier(), true);
							relationChildren.load();
							BusObjectIterator<T> newChildren = relationChildren
									.getObjects();

							while (newChildren.hasMoreElements()) {
								BusObject child = newChildren.nextElement();
								relationChildren.removeObject(child);
							}

						}
					}
				} catch (ClassNotFoundException e) {
					// Cannot do much, let's ignore
				}
			}
		}

	}

}
