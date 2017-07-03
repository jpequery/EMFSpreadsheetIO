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
/**
 * basic interface for spreadsheet import. 
 * @author Jérôme
 *
 */
public interface IExcelImporter {
	/**
	 * called for the first line. This line supposed to contain
	 * title for each columns, the content of the data line will 
	 * be parsed by computeOtherLine. An empty cell gives a 
	 * empty string.
	 * @param lineData data from a line
	 */
	public void computeFirstLine (List<String> lineData);
	
	/**
	 * called for each line after the first one, to compute a line
	 * of data. the tool guarantee the number of elements in linedata will be the 
	 * same for every lines, even with empty cells. 
	 * @param lineNumber the number of the line, from 1 to ... (0 is the title line)
	 * @param lineData data from a line
	 */
	public void computeOtherLine (int lineNumber, List<String> lineData);
	
	/**
	 * set the destination object for the importer. Must be 
	 * an EObject. 
	 * @param destinationObject the original command object 
	 */
	public void setDestination(EObject destinationObject);
	
	/**
	 * called at he end of the import. It can be the moment to 
	 * create/validates linkes between imported objects and 
	 * update the consistency of the model
	 */
	public void importEnded();

	/**
	 * return the name of the importer, to be visible in import GUI
	 * @return
	 */
	public String getName();
}
