package org.obeonetwork.spreadsheet.importer;

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
