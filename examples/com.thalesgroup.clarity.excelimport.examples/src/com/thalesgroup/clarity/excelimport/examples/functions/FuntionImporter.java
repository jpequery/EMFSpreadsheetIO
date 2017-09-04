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
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.obeonetwork.spreadsheet.importer.IExcelImporter;
import org.polarsys.capella.core.data.ctx.CtxFactory;
import org.polarsys.capella.core.data.ctx.SystemFunction;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.FaFactory;
import org.polarsys.capella.core.data.fa.FunctionInputPort;
import org.polarsys.capella.core.data.fa.FunctionOutputPort;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.la.LaFactory;
import org.polarsys.capella.core.data.la.LogicalFunction;
import org.polarsys.capella.core.data.pa.PaFactory;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

class FeData {
	public String name;
	public AbstractFunction source;
	public String destName;
}

public class FuntionImporter implements IExcelImporter {

	private AbstractFunction _destination;
	Stack<AbstractFunction> contextStack = new Stack<AbstractFunction>();
	int summaryField = -1;

	private List<FeData> feCreationDataSet = new ArrayList<FeData>();
	private HashMap<String, AbstractFunction> nameLookup = new HashMap<String, AbstractFunction>();

	public FuntionImporter() {
		// nothing todo
	}

	@Override
	public void computeFirstLine(List<String> lineData) {
		// find the summary field1
		int columnNumber = 0;
		for (String string : lineData) {
			if (string.equals("summary"))
				summaryField = columnNumber;
			columnNumber++;
		}
		contextStack.push(_destination);
	}

	@Override
	public void computeOtherLine(int lineNumber, List<String> lineData) {
		String currentFunctionName = "";
		int currentIndex = 0;
		// looking for the function name and its index
		for (int i = 0; i < summaryField; i++) {
			if (!lineData.get(i).isEmpty()) {
				currentFunctionName = lineData.get(i);
				currentIndex = i;
			}
		}
		// looking for the container in the stack
		while (contextStack.size() > currentIndex + 1)
			contextStack.pop();

		AbstractFunction container = contextStack.peek();
		AbstractFunction currentFunction = ensureFunctionCreation(container, currentFunctionName);
		nameLookup.put(currentFunctionName, currentFunction);

		container.getOwnedFunctions().add(currentFunction);
		currentFunction.setSummary(lineData.get(summaryField));

		if (lineData.size() > summaryField + 1) {
			String dest = lineData.get(summaryField + 1);
			String nameFe = lineData.get(summaryField + 2);
			
			if (!dest.isEmpty()) {
				int index = 0;
				for (String string : dest.split(",")) {
					FeData data = new FeData();
					data.source = currentFunction;
					data.destName = string.trim();
					data.name = nameFe.split(",")[index++].trim();
					feCreationDataSet.add(data);					
				}
			}
		}

		boolean bool = Boolean.parseBoolean(lineData.get(summaryField + 3));
		
		
		contextStack.push(currentFunction);
	}

	private AbstractFunction ensureFunctionCreation(AbstractFunction container, String currentFunctionName) {
		for (AbstractFunction abstractFunction : container.getOwnedFunctions()) {
			if (abstractFunction.getName().equals(currentFunctionName))
				return abstractFunction;
		}

		// creation of the function
		AbstractFunction result = null;
		if (container instanceof SystemFunction)
			result = CtxFactory.eINSTANCE.createSystemFunction(currentFunctionName);
		else if (container instanceof LogicalFunction)
			result = LaFactory.eINSTANCE.createLogicalFunction(currentFunctionName);
		else if (container instanceof PhysicalFunction)
			result = PaFactory.eINSTANCE.createPhysicalFunction(currentFunctionName);
		container.getOwnedFunctions().add(result);

		return result;
	}

	@Override
	public void setDestination(EObject destinationObject) {
		// TODO Auto-generated method stub
		if (destinationObject instanceof AbstractFunction)
			_destination = (AbstractFunction) destinationObject;
	}

	@Override
	public void importEnded() {
		for (FeData data : feCreationDataSet) {
			AbstractFunction destination = nameLookup.get(data.destName);

			FunctionalExchange fe = FaFactory.eINSTANCE.createFunctionalExchange(data.name);
			FunctionInputPort fip = FaFactory.eINSTANCE.createFunctionInputPort(data.name + "_i");
			FunctionOutputPort fop = FaFactory.eINSTANCE.createFunctionOutputPort(data.name + "_o");

			data.source.getOutputs().add(fop);
			destination.getInputs().add(fip);
			fe.setSource(fop);
			fe.setTarget(fip);
			_destination.getOwnedFunctionalExchanges().add(fe);
		}

	}

	@Override
	public String getName() {
		return "Hierarchical function importer";
	}

}
