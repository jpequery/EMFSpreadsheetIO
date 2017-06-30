/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/package org.obeonetwork.spreadsheet.exporter.mm.capella;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.obeonetwork.spreadsheet.exporter.IExcelExporter;
import org.obeonetwork.spreadsheet.exporter.IExcelMetamodelExtension;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.BooleanPropertyValue;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyValue;
import org.polarsys.capella.core.data.capellacore.FloatPropertyValue;
import org.polarsys.capella.core.data.capellacore.IntegerPropertyValue;
import org.polarsys.capella.core.data.capellacore.StringPropertyValue;
import org.polarsys.kitalpha.emde.model.ElementExtension;

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
		List<String> result = new ArrayList<String>(); 
		for (AbstractPropertyValue pv : getPV (eObject, pvToExport)) {
			result.add(getPVValue (pv));
		}		
		return result;	
	}
}
