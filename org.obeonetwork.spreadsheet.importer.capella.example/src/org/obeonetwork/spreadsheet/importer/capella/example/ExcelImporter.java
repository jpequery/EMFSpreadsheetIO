package org.obeonetwork.spreadsheet.importer.capella.example;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.obeonetwork.spreadsheet.importer.IExcelImporter;
import org.polarsys.capella.core.data.la.LaFactory;
import org.polarsys.capella.core.data.la.LogicalComponent;

public class ExcelImporter implements IExcelImporter {

	private LogicalComponent destination;

	
	public ExcelImporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void computeFirstLine(List<String> lineData) {
		//nothing to do

	}

	@Override
	public void computeOtherLine(int lineNumber, List<String> lineData) {
		String lcName = lineData.get(0);
		LogicalComponent lc = LaFactory.eINSTANCE.createLogicalComponent();
		lc.setName (lcName);
		destination.getOwnedLogicalComponents().add(lc);	}

	@Override
	public void setDestination(EObject destinationObject) {
		if (destinationObject instanceof LogicalComponent) {
			destination = (LogicalComponent) destinationObject;
			
		}
	}

	@Override
	public void importEnded() {
		// nothing to do

	}

	@Override
	public String getName() {
		return "capella logicalcomponent import list";
	}

}
