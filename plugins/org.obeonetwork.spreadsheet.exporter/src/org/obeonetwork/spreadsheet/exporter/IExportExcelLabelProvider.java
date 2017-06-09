package org.obeonetwork.spreadsheet.exporter;

import java.util.Collection;

public interface IExportExcelLabelProvider {
	String toString (Object object);
	
	String toString (Collection<? extends Object> objects);
}
