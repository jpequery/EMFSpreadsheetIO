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
 *******************************************************************************/package org.obeonetwork.spreadsheet.importer.ui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.obeonetwork.spreadsheet.exporter.IExcelExporter;
import org.obeonetwork.spreadsheet.importer.IExcelImporter;

public class ExporterSelectionDialog extends org.eclipse.jface.dialogs.Dialog {
	private Combo _combo;
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		Label label = new Label(container, SWT.NONE);
		label.setText("Select an excel exporter");
		
		_combo = new Combo(container, SWT.SINGLE | SWT.READ_ONLY);		
		for (IExcelExporter iExcelExporter : _exporters) {
			_combo.add(iExcelExporter.getName ());
		}
		_combo.select(0);


		return container;
	}



	private List<IExcelExporter> _exporters;
	private IExcelExporter _selectedExporter;

	public ExporterSelectionDialog(Shell parent, List<IExcelExporter> exporters) {
		super(parent);
		_exporters = exporters;
}

	public IExcelExporter getSelectedExporter() {
		return _selectedExporter;
	}

	@Override
	public boolean close() {
		int selection = _combo.getSelectionIndex();
		_selectedExporter =  _exporters.get(selection);
		return super.close();
	}


}
