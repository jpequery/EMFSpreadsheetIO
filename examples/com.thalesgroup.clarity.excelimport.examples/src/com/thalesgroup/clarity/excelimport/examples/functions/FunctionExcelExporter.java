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
 *******************************************************************************/package com.thalesgroup.clarity.excelimport.examples.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.obeonetwork.spreadsheet.exporter.IAdvancedExcelExporter;
import org.obeonetwork.spreadsheet.exporter.IExportExcelLabelProvider;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.ModellingcorePackage;
import org.polarsys.capella.core.data.capellacore.CapellacorePackage;
import org.polarsys.capella.core.data.ctx.SystemFunction;
import org.polarsys.capella.core.data.fa.FaPackage;

public class FunctionExcelExporter implements IAdvancedExcelExporter, IExportExcelLabelProvider {

	EObject _startupObject;
	int _maxDeep;
	
	public FunctionExcelExporter() {
		// nothing to do ....
	}

	@Override
	public List<EObject> getObjectsToExport(EObject startupObject) {
		_startupObject = startupObject;
		List<EObject> result = new ArrayList<>();
		TreeIterator<EObject> iter = _startupObject.eAllContents();
		while (iter.hasNext()) {
			EObject next = iter.next();
			if (next instanceof SystemFunction)
				result.add(next);
		}
		_maxDeep = 1;
		for (EObject eObject : result) {
			int deep = computeDeep (eObject, _startupObject);
			if (deep > _maxDeep) _maxDeep= deep;
		}
		return result;
	}

	private int computeDeep(EObject eObject, EObject startupObject) {
		int result = 1;
		while (eObject.eContainer() != startupObject){
			eObject = eObject.eContainer();
			result++;
		}
		return result;
	}

	@Override
	public List<EStructuralFeature> getFeaturesToExport() {
		List <EStructuralFeature> result = new ArrayList<EStructuralFeature>();
		result.add(ModellingcorePackage.Literals.ABSTRACT_NAMED_ELEMENT__NAME);
		result.add(CapellacorePackage.Literals.CAPELLA_ELEMENT__SUMMARY);
		result.add(FaPackage.Literals.ABSTRACT_FUNCTION__OWNED_FUNCTIONS);
		return result;
	}

	@Override
	public List<String> getPropertyValuesToExport() {
		return null;
	}

	@Override
	public String getName() {
		return "System function Exporter";
	}

	@Override
	public IExportExcelLabelProvider getExporterLabelProvider() {
		return this;
	}

	@Override
	public String toString(Object object) {
		if (object instanceof AbstractNamedElement) {
			AbstractNamedElement ane = (AbstractNamedElement) object;
			return ane.getName();
		};
		return object.toString();
	}

	@Override
	public String toString(Collection<? extends Object> objects) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object object : objects) {
			sb.append(toString(object));
			if (objects.size()>i)
				sb.append(", ");
			i++;
		}
		return sb.toString();
	}

	@Override
	public List<String> prepend(int line, Object object) {
		List<String> result = new ArrayList<>();
		for (int i=0; i< _maxDeep; i++)
			result.add("");
		if (object != null && object instanceof AbstractNamedElement){
			AbstractNamedElement e = (AbstractNamedElement) object;
			int index = computeDeep(e, _startupObject);
			result.set(index-1, e.getName());
		}
		return result;
	}

	@Override
	public List<String> postpend(int line, Object object) {
		return Collections.EMPTY_LIST;
	}

}
