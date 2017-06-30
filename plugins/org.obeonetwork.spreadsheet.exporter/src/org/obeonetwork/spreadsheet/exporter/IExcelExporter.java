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

public interface IExcelExporter {
	
	// gruik ?
	static List<String> ALL_PROPERTY_VALUES = new ArrayList<String>();

	List<EObject> getObjectsToExport(EObject _startupObject);

	List<EStructuralFeature> getFeaturesToExport();
	
	List<String> getPropertyValuesToExport ();

	String getName();
	
	IExportExcelLabelProvider getExporterLabelProvider ();

}
