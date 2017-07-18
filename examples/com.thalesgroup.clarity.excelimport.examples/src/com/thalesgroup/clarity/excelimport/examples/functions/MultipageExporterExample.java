package com.thalesgroup.clarity.excelimport.examples.functions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.obeonetwork.spreadsheet.exporter.IExcelExporter;
import org.obeonetwork.spreadsheet.exporter.IExportExcelLabelProvider;
import org.obeonetwork.spreadsheet.exporter.IMultipageExcelExporter;

public class MultipageExporterExample implements IMultipageExcelExporter {

	private Map<String, IExcelExporter> exporters;
	private FunctionExcelExporter functionExcelExporter;

	public MultipageExporterExample() {
		exporters = new HashMap<String, IExcelExporter>();
		functionExcelExporter = new FunctionExcelExporter();
		exporters.put(functionExcelExporter.getName(), functionExcelExporter);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MultipageExporterExample";
	}

	@Override
	public Collection<String> getPageNames() {
		// TODO Auto-generated method stub
		return exporters.keySet();
	}

	@Override
	public IExcelExporter getExporter(String name) {
		// TODO Auto-generated method stub
		return exporters.get(name);
	}

	@Override
	public IExportExcelLabelProvider getExporterLabelProvider() {
		// TODO Auto-generated method stub
		return functionExcelExporter;
	}

}
