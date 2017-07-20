package org.obeonetwork.spreadsheet.examples.mmext;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.obeonetwork.spreadsheet.exporter.IExcelMetamodelExtension;

public class EContainerMetamodelExtension implements IExcelMetamodelExtension {

	public EContainerMetamodelExtension() {
		// nothing to do
	}

	@Override
	public List<String> generateExtensions(EObject eObject, List<String> pvToExport) {
		if (eObject instanceof ENamedElement) {
			ENamedElement namedElement = (ENamedElement) eObject;
			List<String> result = new ArrayList<String> (); 
			ENamedElement container = (ENamedElement) namedElement.eContainer();
			if (container != null)
				result.add (container.getName());
			return result;
		}
		return null;
	}

}
