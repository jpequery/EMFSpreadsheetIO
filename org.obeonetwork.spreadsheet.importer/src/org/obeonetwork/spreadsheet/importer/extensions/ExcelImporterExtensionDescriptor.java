package org.obeonetwork.spreadsheet.importer.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.obeonetwork.spreadsheet.importer.IExcelImporter;

public class ExcelImporterExtensionDescriptor {
	public static final String EXCEL_IMPORT_ATTRIBUTE_EXTENSION_NAME = "name";

	public static final String EXCEL_IMPORT_ATTRIBUTE_EXTENSION_CLASSNAME = "className";

	private String _name;

	private IExcelImporter _importer;

	public ExcelImporterExtensionDescriptor (IConfigurationElement configuration) {
		_name = configuration.getAttribute(EXCEL_IMPORT_ATTRIBUTE_EXTENSION_NAME);		
		try {
			_importer = (IExcelImporter) configuration.createExecutableExtension(EXCEL_IMPORT_ATTRIBUTE_EXTENSION_CLASSNAME);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public IExcelImporter getExcelImporter() {
		return _importer;
	}

	public String getExtensionClassName() {
		return EXCEL_IMPORT_ATTRIBUTE_EXTENSION_NAME;
	}

}
