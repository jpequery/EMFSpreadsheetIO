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

import org.obeonetwork.spreadsheet.exporter.extensions.ExcelExporterExtensionDescriptor;
import org.obeonetwork.spreadsheet.exporter.extensions.ExcelExporterExtensionRegistry;

public class ExcelExporterManager {
	private IExcelExporter _lastSelected = null;
	
	public static final ExcelExporterManager eINSTANCE = new ExcelExporterManager();
	
	private ExcelExporterManager (){
		// nothing to do
	}

	public List<IRegisteredExcelExporter> getAllExporters() {
		List<ExcelExporterExtensionDescriptor> extensions = ExcelExporterExtensionRegistry.getRegisteredExtensions();
		
		List<IRegisteredExcelExporter> result = new ArrayList<IRegisteredExcelExporter>(extensions.size());
		
		for (ExcelExporterExtensionDescriptor extension : extensions) {
			result.add(extension.getExcelExporter());
		}
		
		return result;
	}

}
