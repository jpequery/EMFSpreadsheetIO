package org.obeonetwork.excel.exporter;

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
