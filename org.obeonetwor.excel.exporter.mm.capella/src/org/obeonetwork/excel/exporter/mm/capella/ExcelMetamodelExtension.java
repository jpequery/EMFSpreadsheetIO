package org.obeonetwork.excel.exporter.mm.capella;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.obeonetwork.excel.exporter.AbstractPropertyValue;
import org.obeonetwork.excel.exporter.BooleanPropertyValue;
import org.obeonetwork.excel.exporter.CapellaElement;
import org.obeonetwork.excel.exporter.ElementExtension;
import org.obeonetwork.excel.exporter.EnumerationPropertyValue;
import org.obeonetwork.excel.exporter.FloatPropertyValue;
import org.obeonetwork.excel.exporter.IExcelExporter;
import org.obeonetwork.excel.exporter.IExcelMetamodelExtension;
import org.obeonetwork.excel.exporter.IntegerPropertyValue;
import org.obeonetwork.excel.exporter.StringPropertyValue;

public class ExcelMetamodelExtension implements IExcelMetamodelExtension {

	public ExcelMetamodelExtension() {
		// TODO Auto-generated constructor stub
	}

	private String getPVValue(AbstractPropertyValue pv) {
		if (pv instanceof StringPropertyValue)
			return ((StringPropertyValue)pv).getValue();
		if (pv instanceof EnumerationPropertyValue)
			return ((EnumerationPropertyValue)pv).getValue().toString();
		if (pv instanceof BooleanPropertyValue)
			return "true";
		if (pv instanceof FloatPropertyValue)
			return Float.toString(((FloatPropertyValue)pv).getValue());
		if (pv instanceof IntegerPropertyValue)
			return Integer.toString(((IntegerPropertyValue)pv).getValue());
		throw new RuntimeException("IncompatiblePropertyValue " + pv.toString());
	}

	private List<AbstractPropertyValue> getPV(EObject eObject, List<String> pvToExport) {
		if (eObject instanceof CapellaElement) {
			CapellaElement capellaElement = (CapellaElement) eObject;
			if (pvToExport == IExcelExporter.ALL_PROPERTY_VALUES)
				return capellaElement.getAppliedPropertyValues();
			List<AbstractPropertyValue> result = new ArrayList<AbstractPropertyValue>(capellaElement.getAppliedPropertyValues().size());
			
			for (AbstractPropertyValue apv : capellaElement.getAppliedPropertyValues()) {
				if (pvToExport.contains(apv.getName()))
					result.add (apv);
			}
			return result;
		}
		return new ArrayList<AbstractPropertyValue>(0);
	}
	
	private Object exportAttribute(EObject eObject, EStructuralFeature feature) {
		Object obj = null;
		if (eObject.eClass().getEAllAttributes().contains(feature))
			obj =eObject.eGet(feature);
		if (obj == null && eObject instanceof CapellaElement) {
			CapellaElement capellaElement = (CapellaElement) eObject;
			for (ElementExtension extension: capellaElement.getOwnedExtensions()) {
				if (extension.eClass().getEAllAttributes().contains(feature))
					obj =extension.eGet(feature);
				if (obj != null) break;
			}
		}
		return obj;
	}

	@Override
	public List<String> generateExtensions(EObject eObject, List<String> pvToExport) {
		List<String> result = new ArrayList<>(String); 
		for (AbstractPropertyValue pv : getPV (eObject, pvToExport)) {
			result.add(getPVValue (pv));
			return result;	
		}		
	}
}
