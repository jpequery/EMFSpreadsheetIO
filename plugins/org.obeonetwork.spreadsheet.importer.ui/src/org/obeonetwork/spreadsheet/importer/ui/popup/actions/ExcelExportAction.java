package org.obeonetwork.spreadsheet.importer.ui.popup.actions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.obeonetwork.spreadsheet.exporter.ExcelExportException;
import org.obeonetwork.spreadsheet.exporter.ExcelExporterEngine;
import org.obeonetwork.spreadsheet.exporter.ExcelExporterManager;
import org.obeonetwork.spreadsheet.exporter.IExcelExporter;
import org.obeonetwork.spreadsheet.importer.ui.ExporterSelectionDialog;

public class ExcelExportAction implements IObjectActionDelegate {

	private Shell shell;
	
	private EObject sourceObject;
	
	/**
	 * Constructor for Action1.
	 */
	public ExcelExportAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		List<IExcelExporter> exporters = ExcelExporterManager.eINSTANCE.getAllExporters ();
		
		ExporterSelectionDialog dialog = new ExporterSelectionDialog(shell, exporters);
		
		if (dialog.open() == Dialog.OK){
			IExcelExporter exporter = dialog.getSelectedExporter ();
			final ExcelExporterEngine engine = new ExcelExporterEngine(sourceObject);
			engine.setExporter(exporter);
			
			
				TransactionalEditingDomain ted = TransactionUtil.getEditingDomain(sourceObject);
				RecordingCommand myCommand = new RecordingCommand(ted) {
					
					@Override
					protected void doExecute() {
						try {
							engine.run();
						} catch (ExcelExportException e) {
							e.printStackTrace();
						}
					}
				}; 			
			ted.getCommandStack().execute(myCommand);
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			for (Object obj : ss.toList()) {
//				if (obj instanceof IFile) {
//					IFile file = (IFile) obj;
//					if (file.getFileExtension().equals("xlsx")) {
//						excelFile = (IFile) obj;
//					}
//										
//				}
				if (obj instanceof EObject) {
					sourceObject = (EObject) obj;					
				}
			}
			
		}
	}

}
