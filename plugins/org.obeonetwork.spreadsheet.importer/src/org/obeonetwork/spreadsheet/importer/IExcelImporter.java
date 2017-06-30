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
 *******************************************************************************/package org.obeonetwork.spreadsheet.importer;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IExcelImporter {
	public void computeFirstLine (List<String> lineData);
	
	public void computeOtherLine (int lineNumber, List<String> lineData);
	
	public void setDestination(EObject destinationObject);
	
	public void importEnded();

	public String getName();
}
