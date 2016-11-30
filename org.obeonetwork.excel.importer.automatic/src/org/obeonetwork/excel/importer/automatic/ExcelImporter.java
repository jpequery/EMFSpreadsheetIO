package org.obeonetwork.excel.importer.automatic;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.obeonetwork.excel.importer.IExcelImporter;

public class ExcelImporter implements IExcelImporter {

	private int summaryField;
	private EObject _destination;
	private List<String> _titleLine;

	public ExcelImporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void computeFirstLine(List<String> lineData) {
		_titleLine = lineData;
		int columnNumber = 0;
		for (String string : lineData) {
			if (string.equals("summary"))
				summaryField = columnNumber;
			columnNumber++;
		}
	}

	@Override
	public void computeOtherLine(int lineNumber, List<String> lineData) {
		// TODO Auto-generated method stub
		int currentIndex = 0;
		String metaclassName = lineData.get(0);
		String id = lineData.get(1);
		EObject element = findElementById (id);
		if (element == null) {
			System.out.println("unknownn Object for import : " + id);
		} 
		for (int i=2; i<lineData.size(); i++) {
			EStructuralFeature dataRef = null; 
			for (EStructuralFeature ref : element.eClass().getEAllStructuralFeatures()) {
				if (ref.getName().equals(_titleLine.get(i)))
					dataRef = ref;
			}
			if (dataRef != null) {
				// the code for the new value depends on the Etype of the reference
				String value = lineData.get(i);
				if (dataRef.getEType().getName().equals("EString")) {
					element.eSet(dataRef, value);
				} else if (dataRef.getEType().getName().equals("EBoolean")){
					boolean val = Boolean.parseBoolean(value);
					element.eSet(dataRef, val);
				}else { 
					System.out.println("unknownn type for import : " + dataRef.getEType().getName());
				}
			}
		}
		
	}

	private EObject findElementById(String id) {
		TreeIterator<EObject> e = _destination.eAllContents();
		while (e.hasNext()){
			EObject element = e.next();
			if (getId(element).equals(id)) return element;
		}
		return null;
	}

	/**
	 * look for an id in attributesof theelement. Aattribute named id or
	 * @param element
	 * @return
	 */
	private Object getId(EObject element) {
		for (EAttribute att : element.eClass().getEAllAttributes()) {
			if(att.isID()) {
				return element.eGet(att).toString();
			}
		}
		return null;
	}

	@Override
	public void setDestination(EObject destinationObject) {
			_destination = destinationObject;	}

	@Override
	public void importEnded() {
	}

	@Override
	public String getName() {
		return "Automatic importer with guessing";
	}
	


}
