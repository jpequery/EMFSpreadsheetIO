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
 *******************************************************************************/package org.obeonetwork.spreadsheet.importer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.obeonetwork.spreadsheet.importer.extensions.ExcelImporterExtensionDescriptor;
import org.obeonetwork.spreadsheet.importer.extensions.ExcelImporterExtensionRegistry;


public class ExcelImporterManager {
	private IExcelImporter _lastSelected = null;
	
	public static final ExcelImporterManager eINSTANCE = new ExcelImporterManager();
	
	private ExcelImporterManager (){
		// nothing to do
	}

	
	/**
	 * return the list of all available importers. 
	 * @return all importers
	 */
	public List<IExcelImporter> getAllImporters() {
		List<ExcelImporterExtensionDescriptor> extensions = ExcelImporterExtensionRegistry.getRegisteredExtensions();
		
		List<IExcelImporter> result = new ArrayList<IExcelImporter>(extensions.size());
		
		for (ExcelImporterExtensionDescriptor extension : extensions) {
			result.add(extension.getExcelImporter());
		}
		
		return result;
	}


	/**
	 * return all imprters compaible with thhhhhe object passed as parameter
	 * @param destinationObject
	 * @return
	 */
	public List<IExcelImporter> getImporters(EObject destinationObject) {
		List<IExcelImporter> result = new ArrayList<IExcelImporter>();
		for (IExcelImporter importer : getAllImporters()) {
			try {
				importer.setDestination(destinationObject);
				result.add(importer);
			} catch (IllegalArgumentException e) {
				// it's ok ... just not compatible
			} catch (ClassCastException e){
				// well ... 
			}
		}
		return getAllImporters ();
	}
	
	
	
}
