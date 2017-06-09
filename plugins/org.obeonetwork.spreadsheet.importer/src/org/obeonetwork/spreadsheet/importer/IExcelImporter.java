package org.obeonetwork.spreadsheet.importer;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IExcelImporter {
	public void computeFirstLine (List<String> lineData);
	
	public void computeOtherLine (int lineNumber, List<String> lineData);
	
	public void setDestination(EObject destinationObject);
	
	public void importEnded();

	public String getName();
}
