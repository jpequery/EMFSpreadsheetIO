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
 *******************************************************************************/package org.obeonetwork.spreadsheet.exporter.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.obeonetwork.spreadsheet.exporter.IExcelExporter;

public class ExcelExporterExtensionDescriptor {
	public static final String EXCEL_EXPORT_ATTRIBUTE_EXTENSION_NAME = "name";

	public static final String EXCEL_EXPORT_ATTRIBUTE_EXTENSION_CLASSNAME = "className";

	private String _name;

	private IExcelExporter _exporter;

	public ExcelExporterExtensionDescriptor (IConfigurationElement configuration) {
		_name = configuration.getAttribute(EXCEL_EXPORT_ATTRIBUTE_EXTENSION_NAME);		
		try {
			_exporter = (IExcelExporter) configuration.createExecutableExtension(EXCEL_EXPORT_ATTRIBUTE_EXTENSION_CLASSNAME);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public IExcelExporter getExcelImporter() {
		return _exporter;
	}

	public String getExtensionClassName() {
		return EXCEL_EXPORT_ATTRIBUTE_EXTENSION_NAME;
	}

}
