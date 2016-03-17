package org.obeonetwork.excel.exporter;

import java.util.List;

public interface IAdvancedExcelExporter extends IExcelExporter {
	public List<String> prepend (int line, Object object);
	public List<String> postpend (int line, Object object);
}
