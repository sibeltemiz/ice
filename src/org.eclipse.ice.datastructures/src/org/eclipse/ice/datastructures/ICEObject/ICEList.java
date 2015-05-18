/*******************************************************************************
 * Copyright (c) 2012, 2014 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay Billings,
 *   Jordan H. Deyton, Dasha Gorin, Alexander J. McCaskey, Taylor Patterson,
 *   Claire Saunders, Matthew Wang, Anna Wojtowicz
 *******************************************************************************/
package org.eclipse.ice.datastructures.ICEObject;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

/**
 * <p>
 * A generic list container used by ICE to transmit lists in XML.
 * </p>
 * 
 * @author Jay Jay Billings
 */
@XmlRootElement(name = "ICEList")
public class ICEList<varType> {
	/**
	 * <p>
	 * The stored list.
	 * </p>
	 * 
	 */
	private ArrayList<varType> storedList;

	/**
	 * <p>
	 * This operation retrieves the list.
	 * </p>
	 * 
	 * @return <p>
	 *         The list.
	 *         </p>
	 */
	@XmlElement(name = "ListElement")
	public ArrayList<varType> getList() {
		return storedList;
	}

	/**
	 * <p>
	 * This operation retrieves stores the list.
	 * </p>
	 * 
	 * @param list
	 *            <p>
	 *            The list to store
	 *            </p>
	 */
	public void setList(ArrayList<varType> list) {

		storedList = list;

	}
}