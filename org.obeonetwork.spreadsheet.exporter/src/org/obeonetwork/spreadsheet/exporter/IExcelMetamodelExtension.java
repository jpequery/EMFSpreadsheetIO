package org.obeonetwork.spreadsheet.exporter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IExcelMetamodelExtension {
	List<String> generateExtensions(EObject eObject, List<String> pvToExport);
}
