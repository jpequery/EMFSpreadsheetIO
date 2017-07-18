package org.obeonetwork.spreadsheet.exporter;

import java.util.Collection;

public interface IMultipageExcelExporter extends IRegisteredExcelExporter {

	public Collection<String> getPageNames();

	public IExcelExporter getExporter(String name);

	public IExportExcelLabelProvider getExporterLabelProvider();

}
