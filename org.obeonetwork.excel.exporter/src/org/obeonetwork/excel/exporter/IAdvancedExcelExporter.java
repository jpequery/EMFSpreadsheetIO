package org.obeonetwork.excel.exporter;

import java.util.List;

public interface IAdvancedExcelExporter extends IExcelExporter {
	/**
	 * Give the user the hability to insert a set of new column at the beginning of each line
	 * of each line, title end 
	 * @param line the line to complete
	 * @param object the object displayed on the line. For the title line, the clicked statrtup object is passed. 
	 * @return the content of colums
	 */
	public List<String> prepend (int line, Object object);

	/**
	 * Give the user the hability to insert a set of new column at the end of each line
	 * of each line, title end 
	 * @param line the line to complete
	 * @param object the object displayed on the line. For the title line, the clicked statrtup object is passed. 
	 * @return the content of colums
	 */
	public List<String> postpend (int line, Object object);
}
