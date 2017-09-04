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
 *******************************************************************************/package com.thalesgroup.clarity.excelimport.examples.requirements;

import java.util.List;
import java.util.Stack;

import org.eclipse.emf.ecore.EObject;
import org.obeonetwork.spreadsheet.importer.IExcelImporter;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementFactory;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;

public class RequirementImporter implements IExcelImporter {

	private RequirementsPkg _destination;
	private Stack<RequirementsPkg> contextStack = new Stack<RequirementsPkg>();
	private int dataField;

	public RequirementImporter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void computeFirstLine(List<String> lineData) {
		// TODO Auto-generated method stub
		// find the summary field
		int columnNumber = 0;
		for (String string : lineData) {
			if (! string.isEmpty()) dataField = columnNumber;
			columnNumber++;
		}
		contextStack.push(_destination);

	}

	@Override
	public void computeOtherLine(int lineNumber, List<String> lineData) {
		String currentRequirementName = "";
		int currentIndex = 0;
		// looking for the requirement/package name and its index
		for (int i=0; i<dataField; i++) {
			if (! lineData.get(i).isEmpty()) {
				currentRequirementName = lineData.get(i);
				currentIndex = i;
				break;
			}
		}
		// looking for the container in the stack
		while (contextStack.size() > currentIndex +1)
			contextStack.pop();
		
		RequirementsPkg container = contextStack.peek();
		
		// requirement or requirementsPkg ? 
		// a requirement have some properties
		boolean isPackage = true;
		for (int i=dataField; i< lineData.size(); i++) {
			if (! lineData.get(i).isEmpty())
				isPackage = false;
		}

		if (isPackage) {
			RequirementsPkg reqPkg = RequirementFactory.eINSTANCE.createRequirementsPkg(currentRequirementName);
			container.getOwnedRequirementPkgs().add(reqPkg);
			contextStack.push(reqPkg);
		} else {
			Requirement req = RequirementFactory.eINSTANCE.createSystemUserRequirement(currentRequirementName);
			container.getOwnedRequirements().add(req);

			//then, adding properties			
			req.setRequirementId(lineData.get(this.dataField));
			req.setSummary(lineData.get(this.dataField - 1));
		}

	}

	@Override
	public void setDestination(EObject destinationObject) {
		if (destinationObject instanceof RequirementsPkg) {
			_destination = (RequirementsPkg) destinationObject;			
		}

	}

	@Override
	public void importEnded() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Requirement hierarchical importer";
	}

}
