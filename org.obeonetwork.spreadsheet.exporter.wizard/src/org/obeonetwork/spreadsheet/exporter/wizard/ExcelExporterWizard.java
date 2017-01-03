package org.obeonetwork.spreadsheet.exporter.wizard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.ui.PlatformUI;
import org.obeonetwork.spreadsheet.exporter.IAdvancedExcelExporter;
import org.obeonetwork.spreadsheet.exporter.IExportExcelLabelProvider;

public class ExcelExporterWizard implements IAdvancedExcelExporter {
	
	ExcelExportWizardDialog dialog = new ExcelExportWizardDialog  (PlatformUI.getWorkbench().getDisplay().getActiveShell());
	
	private EClass selectedEClass;

	public ExcelExporterWizard() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * selection des metaclasses à exporter : 
	 * on prends le sous-arbre, et on priopose les metaclasses de ces 
	 * éléments. Retourne la listes des objets de cette metaclasse 
	 * du sous-arbre.   
	 */
	@Override
	public List<EObject> getObjectsToExport(EObject startupObject) {		
		Set<EClass> set = new HashSet<EClass>();
		
		TreeIterator<EObject> it = startupObject.eAllContents();
		while (it.hasNext()){
			EObject obj = it.next();
			set.add(obj.eClass());
		}
		
		
		dialog.setListOfEclasses(set);
		if (dialog.open() == Dialog.OK) {
			selectedEClass = dialog.getEClassSelection ();
		} else {
			return null;
		}
		
		List<EObject> result = new ArrayList<EObject>();
		TreeIterator<EObject> content = startupObject.eAllContents();
		while (content.hasNext()) {
			EObject current = content.next();
			if (selectedEClass.isSuperTypeOf(current.eClass())) {
				result.add (current);
			}			
		}
		
		return result;
	}

	@Override
	public List<EStructuralFeature> getFeaturesToExport() {
		List<EStructuralFeature> res = new ArrayList<EStructuralFeature>();
		for (EAttribute att  : selectedEClass.getEAllAttributes()) {
			res.add(att);
		}
		return res;
	}

	@Override
	public String getName() {
		return "Excel Export Wizard";
	}

	@Override
	public List<String> getPropertyValuesToExport() {
		return ALL_PROPERTY_VALUES;
	}

	@Override
	public IExportExcelLabelProvider getExporterLabelProvider() {
		return null;
	}

	@Override
	public List<String> prepend(int line, Object object) {
		List<String> result = new ArrayList<String>();
		if (object instanceof EObject) {
			EObject eObject = (EObject) object;
			result.add((eObject.eClass().getName()));			
		}
		return result;
	}

	@Override
	public List<String> postpend(int line, Object object) {
		return null;
	}

}
