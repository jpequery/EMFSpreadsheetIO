package org.obeonetwork.spreadsheet.exporter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * The cross exporter is an interface to implement to export as a matrix.
 * @author Jérôme Pequery
 *
 */
public interface ICrossExporter extends IRegisteredExcelExporter {
	/**
	 * 
	 * @return a list of Object represented by columns
	 */
	List<EObject> getColumnObjects ();
	
	
	/**
	 * 
	 * @return a list of Object represented by lines
	 */
	List<EObject> getLineObjects ();
	
	/**
	 * return the content of the cell at crossing of line and column
	 * @param column the column object, returned from getColumnObjects
	 * @param line the line object, returned from getLineObjects
	 * @return a string value
	 */
	String getCrossValue (EObject column, EObject line);
 }
