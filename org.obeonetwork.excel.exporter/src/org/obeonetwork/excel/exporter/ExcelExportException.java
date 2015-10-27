package org.obeonetwork.excel.exporter;

import java.io.IOException;

@SuppressWarnings("serial")
public class ExcelExportException extends Exception {

	public ExcelExportException(IOException e) {
		super (e);
	}

}
