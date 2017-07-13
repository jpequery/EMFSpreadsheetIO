/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/package org.obeonetwork.spreadsheet.exporter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * support for excel file for properties : giving a metaclass, 
 * this exporter select a set of properties to export by column 
 * @author Jpequery
 *
 */
public interface IExcelExporter extends IRegisteredExcelExporter {
	
	// gruik ?
	static List<String> ALL_PROPERTY_VALUES = new ArrayList<String>();

	/**
	 * retrieve the list of all objects to export (one by line)
	 * @param _startupObject
	 * @return
	 */
	List<EObject> getObjectsToExport(EObject _startupObject);

	/**
	 * retrieve the list of all structural feature to export
	 * @return
	 */
	List<EStructuralFeature> getFeaturesToExport();
	
	/**
	 * retrieve the list of propertyValue to export.
	 * Capella SPecific. If doen't apply, return  null
	 * @return
	 */
	List<String> getPropertyValuesToExport ();


	
	/**
	 * return a label provider for the export: how can we display
	 * EObject or List<EObject> in the resulting file
	 * @return
	 */
	IExportExcelLabelProvider getExporterLabelProvider ();

}
