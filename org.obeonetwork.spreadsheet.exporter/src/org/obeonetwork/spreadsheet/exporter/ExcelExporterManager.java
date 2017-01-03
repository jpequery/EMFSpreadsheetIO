package org.obeonetwork.spreadsheet.exporter;

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

	public List<IExcelExporter> getAllExporters() {
		List<ExcelExporterExtensionDescriptor> extensions = ExcelExporterExtensionRegistry.getRegisteredExtensions();
		
		List<IExcelExporter> result = new ArrayList<IExcelExporter>(extensions.size());
		
		for (ExcelExporterExtensionDescriptor extension : extensions) {
			result.add(extension.getExcelImporter());
		}
		
		return result;
	}

}
