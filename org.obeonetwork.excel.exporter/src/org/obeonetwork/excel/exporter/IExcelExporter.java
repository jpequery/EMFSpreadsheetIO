package org.obeonetwork.excel.exporter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public interface IExcelExporter {

	List<EObject> getObjectsToExport(EObject _startupObject);

	List<EStructuralFeature> getFeaturesToExport();

	String getName();

}
