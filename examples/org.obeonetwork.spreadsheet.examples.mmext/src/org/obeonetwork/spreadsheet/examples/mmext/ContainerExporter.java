package org.obeonetwork.spreadsheet.examples.mmext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.obeonetwork.spreadsheet.exporter.IExcelExporter;
import org.obeonetwork.spreadsheet.exporter.IExportExcelLabelProvider;

public class ContainerExporter implements IExcelExporter {

	public ContainerExporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		return "Container Metamodel Extension";
	}

	@Override
	public List<EObject> getObjectsToExport(EObject startupObject) {
		List<EObject> result = new ArrayList<>();
		TreeIterator<EObject> iter = startupObject.eAllContents();
		while (iter.hasNext()) {
			EObject next = iter.next();
			if (next instanceof ENamedElement)
				result.add(next);
		}
		return result;
	}

	@Override
	public List<EStructuralFeature> getFeaturesToExport() {
		return Collections.singletonList((EStructuralFeature)EcorePackage.Literals.ENAMED_ELEMENT__NAME);
	}

	@Override
	public List<String> getPropertyValuesToExport() {
		return Collections.singletonList("container");
	}

	@Override
	public IExportExcelLabelProvider getExporterLabelProvider() {
		// TODO Auto-generated method stub
		return null;
	}

}
