package org.obeonetwork.spreadsheet.importer;

import java.util.List;
import java.util.Map;

public interface IMultipageExcelImporter extends IExcelImporter{

	public Map<String, ? extends IExcelImporter> getPageImporters();

	public List<String> getPageOrder();



}
