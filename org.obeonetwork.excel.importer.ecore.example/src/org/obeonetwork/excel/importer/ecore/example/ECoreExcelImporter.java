package org.obeonetwork.excel.importer.ecore.example;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.obeonetwork.excel.importer.IExcelImporter;

public class ECoreExcelImporter  implements IExcelImporter {

	private EPackage destination;

	@Override
	public void computeFirstLine(List<String> lineData) {
		// nothing to do on the first line
		
	}

	@Override
	public void computeOtherLine(int lineNumber, List<String> lineData) {
		String className = lineData.get(0);
		EClass cl = EcoreFactory.eINSTANCE.createEClass();
		cl.setName (className);
		destination.getEClassifiers().add(cl);
	}
		

	@Override
	public void setDestination(EObject destinationObject) {
		if (destinationObject instanceof EPackage) {
			destination = (EPackage) destinationObject;
			
		}
		
	}

	@Override
	public void importEnded() {
		// noithing to do at the end
		
	}

}
