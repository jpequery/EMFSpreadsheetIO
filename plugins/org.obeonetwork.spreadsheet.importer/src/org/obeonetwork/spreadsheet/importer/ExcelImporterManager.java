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

import org.obeonetwork.spreadsheet.importer.extensions.ExcelImporterExtensionDescriptor;
import org.obeonetwork.spreadsheet.importer.extensions.ExcelImporterExtensionRegistry;


public class ExcelImporterManager {
	private IExcelImporter _lastSelected = null;
	
	public static final ExcelImporterManager eINSTANCE = new ExcelImporterManager();
	
	private ExcelImporterManager (){
		// nothing to do
	}

	public List<IExcelImporter> getAllImporters() {
		List<ExcelImporterExtensionDescriptor> extensions = ExcelImporterExtensionRegistry.getRegisteredExtensions();
		
		List<IExcelImporter> result = new ArrayList<IExcelImporter>(extensions.size());
		
		for (ExcelImporterExtensionDescriptor extension : extensions) {
			result.add(extension.getExcelImporter());
		}
		
		return result;
	}
	
	
	
}
