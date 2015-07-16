/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jordan Deyton - Initial API and implementation and/or initial documentation
 *   
 *******************************************************************************/
package org.eclipse.ice.viz.service.paraview.proxy;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import org.eclipse.ice.viz.service.connections.IVizConnection;
import org.eclipse.ice.viz.service.paraview.web.IParaViewWebClient;

/**
 * A proxy serves as an intermediary between client code and a particular file
 * loaded into a view in ParaView. Each proxy is responsible for <i>exactly</i>
 * one particular file for a given view. If another file needs to be
 * manipulated, another proxy should be created for that file.
 * <p>
 * Furthermore, a proxy provides an interface to ParaViewPlots for access to the
 * contents of the file.
 * </p>
 * 
 * @author Jordan Deyton
 *
 */
public interface IParaViewProxy {

	/**
	 * Gets the categories of "features" in the file that can be rendered. This
	 * may include categories such as "point arrays" or "cell arrays".
	 * 
	 * @return A set of categories of "features" in the file.
	 */
	public Set<String> getFeatureCategories();

	/**
	 * Gets the file's "features" that can be rendered for the specified
	 * category.
	 * 
	 * @param category
	 *            The category whose features will be returned. This should be a
	 *            value retrieved from {@link #getFeatureCategories()}.
	 * @return A set of features that can be rendered. This set may be unique to
	 *         the specified category. Returns {@code null} if the category name
	 *         is invalid.
	 */
	public Set<String> getFeatures(String category);

	/**
	 * Gets the ID of the underlying "file" object.
	 * 
	 * @return The ID of the underlying file, or {@code -1} if the proxy was not
	 *         opened.
	 */
	public int getFileId();

	/**
	 * Gets the current properties for the proxy.
	 * 
	 * @return A map of the current properties for the proxy. The keys are
	 *         property names, while the values are their current values.
	 */
	public Map<String, String> getProperties();

	/**
	 * Gets the current value for the proxy's specified property.
	 * 
	 * @param name
	 *            The name of the property to retrieve.
	 * @return The current value of the property, or {@code null} if the
	 *         property name is invalid.
	 */
	public String getProperty(String name)
			throws NullPointerException, IllegalArgumentException;

	/**
	 * Gets the set of allowed values for the specified property.
	 * 
	 * @param name
	 *            The property whose allowed values will be returned.
	 * @return A set containing all allowed values for the specified property,
	 *         or {@code null} if the property name is invalid.
	 */
	public Set<String> getPropertyAllowedValues(String name);

	/**
	 * Gets the ID of the underlying "representation" object.
	 * 
	 * @return The ID of the underlying representation, or {@code -1} if the
	 *         proxy was not opened.
	 */
	public int getRepresentationId();

	/**
	 * Gets a list of timesteps for the loaded proxy.
	 * 
	 * @return A list containing the allowed timesteps for the proxy. This list
	 *         may be empty, but should not be {@code null}.
	 */
	public List<Double> getTimesteps();

	/**
	 * Gets the current URI associated with the proxy.
	 * 
	 * @return The URI pointing to the proxy's file.
	 */
	public URI getURI();

	/**
	 * Gets the ID of the underlying "view" object.
	 * 
	 * @return The ID of the underlying view, or {@code -1} if the proxy was not
	 *         opened.
	 */
	public int getViewId();

	/**
	 * Opens the designated URI, if possible, using the specified ParaView
	 * connection.
	 * 
	 * @param connection
	 *            The connection to use when opening the URI. Should not be
	 *            {@code null}.
	 * @return True if the proxy could be opened using the connection (also when
	 *         it is already open), false otherwise.
	 * @throws NullPointerException
	 *             If the specified connection is {@code null}.
	 */
	public Future<Boolean> open(IVizConnection<IParaViewWebClient> connection)
			throws NullPointerException;

	/**
	 * Sets the current feature that is rendered via ParaView.
	 * 
	 * @param category
	 *            The category for the feature. This should be one of the keys
	 *            in the map returned by {@link #getFeatures()}.
	 * @param feature
	 *            The feature or "type" to be rendered. This should be in the
	 *            set (from the feature map) associated with the category key.
	 * @return True if the feature was changed, false otherwise.
	 * @throws NullPointerException
	 *             If either of the specified arguments are {@code null}.
	 * @throws IllegalArgumentException
	 *             If either the category is invalid or the feature is not valid
	 *             for the category.
	 */
	public Future<Boolean> setFeature(String category, String feature)
			throws NullPointerException, IllegalArgumentException;

	/**
	 * Sets the specified property to the new value.
	 * 
	 * @param name
	 *            The property to updated. Allowed values can be retrieved via
	 *            {@link #getProperties()}.
	 * @param value
	 *            The new value of the property. Allowed values can be retrieved
	 *            via {@link #getPropertyAllowedValues(String)}.
	 * @return True if the property was changed, false otherwise.
	 * @throws NullPointerException
	 *             If either of the specified arguments are null.
	 * @throws IllegalArgumentException
	 *             If either the property name is invalid or the value is not
	 *             valid for the property.
	 */
	public Future<Boolean> setProperty(String name, String value)
			throws NullPointerException, IllegalArgumentException;

	/**
	 * Updates the proxy's current properties to include all properties listed
	 * in the specified map. This can be used for a bulk update of properties
	 * that triggers only a single refresh of the rendered view.
	 * <p>
	 * If a given property or value in the provided map is invalid, then the
	 * property is skipped.
	 * </p>
	 * 
	 * @param properties
	 *            The new properties to set.
	 * @return The number of properties that were <i>updated</i>. Properties
	 *         whose values were already set do not count.
	 * @throws NullPointerException
	 *             If the map is {@code null}.
	 */
	public Future<Integer> setProperties(Map<String, String> properties)
			throws NullPointerException;

	/**
	 * Sets the timestep for the loaded proxy.
	 * 
	 * @param timestep
	 *            The new timestep index.
	 * @return True when the operation has completed.
	 */
	public Future<Boolean> setTimestep(int timestep);

}
