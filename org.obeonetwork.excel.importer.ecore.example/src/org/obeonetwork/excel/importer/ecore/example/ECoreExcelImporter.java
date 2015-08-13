package org.obeonetwork.excel.importer.ecore.example;

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
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
		
		String abs = lineData.get(1);
		if (abs.equalsIgnoreCase("true"))
			cl.setAbstract(true);
		
		if (lineData.size() > 2) {
			String parent = lineData.get(2);
			if (! parent.isEmpty()) {
				EClassifier parentClass = destination.getEClassifier(parent);
				if (parentClass != null)
					cl.getESuperTypes().add((EClass) parentClass);
			}
		}

		if (lineData.size() > 3) {
			String attName = lineData.get(3);
			if (! attName.isEmpty()) {
					EAttribute att = EcoreFactory.eINSTANCE.createEAttribute();
					att.setName (attName);
					cl.getEStructuralFeatures().add (att);
			}
		}
		
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

	@Override
	public String getName() {
		return "ECore Import";
	}

}
