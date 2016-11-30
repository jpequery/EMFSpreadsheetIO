package org.obeonetwork.excel.exporter.extensions;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.obeonetwork.excel.exporter.IExcelExporter;
import org.obeonetwork.excel.exporter.IExcelMetamodelExtension;

public class ExcelExporterMetamodelExtensionDescriptor {
	public static final String EXCEL_EXPORT_METAMODEL_EXTENSION_NAME = "name";

	public static final String EXCEL_EXPORT_METAMODEL_ATTRIBUTE_EXTENSION_CLASSNAME = "className";

	private String _name;

	private IExcelMetamodelExtension _exporter;

	public ExcelExporterMetamodelExtensionDescriptor (IConfigurationElement configuration) {
		_name = configuration.getAttribute(EXCEL_EXPORT_METAMODEL_EXTENSION_NAME);		
		try {
			_exporter = (IExcelMetamodelExtension) configuration.createExecutableExtension(EXCEL_EXPORT_METAMODEL_ATTRIBUTE_EXTENSION_CLASSNAME);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	public IExcelMetamodelExtension getExcelMetamodelExtension() {
		return _exporter;
	}

	public String getExtensionClassName() {
		return EXCEL_EXPORT_METAMODEL_ATTRIBUTE_EXTENSION_CLASSNAME;
	}

}
