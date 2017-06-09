package org.obeonetwork.spreadsheet.exporter.wizard;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ExcelExportWizardDialog extends Dialog {
	
	private EClass _eClassSelection;
			
	@Override
	public boolean close() {
		if (listWidget.getSelectionCount() != 0) {
			String [] strs = listWidget.getSelection();
			for (EClass ec : listOfEclasses) {
				if (ec.getName().equals(strs[0])) _eClassSelection = ec;
			}
		}
		return super.close();
	}

	private java.util.List<EClass> listOfEclasses = new ArrayList<EClass>();
	

	List listWidget; 
	
	public void setListOfEclasses(java.util.Set<EClass> listOfEclasses) {
		this.listOfEclasses.clear();
		this.listOfEclasses.addAll (listOfEclasses);
	}

	protected ExcelExportWizardDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		this.getShell().setText("Excel Exporter Wizard");
		parent.setLayout(new GridLayout(1, true));
		Text txt = new Text(parent, 0);
		txt.setText("Select the type to export : ");
		listWidget = new List(parent, SWT.NONE);
		for (EClass class_ : listOfEclasses) {
			listWidget.add(class_.getName());
			
		}
		
		return parent;
	}

	public EClass getEClassSelection() {

		return _eClassSelection;
	}
	
	

}
