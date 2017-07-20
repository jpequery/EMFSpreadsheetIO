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
 *******************************************************************************/package org.obeonetwork.spreadsheet.exporter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.obeonetwork.spreadsheet.exporter.extensions.ExcelExporterMetamodelExtensionDescriptor;
import org.obeonetwork.spreadsheet.exporter.extensions.ExcelExporterMetamodelExtensionRegistry;



public class ExcelExporterEngine {
	private IFile _excelFile;
	private EObject _startupObject;
	FileOutputStream _file;

	IRegisteredExcelExporter _exporter;
	private IExportExcelLabelProvider _formater;

	public IExportExcelLabelProvider getFormater() {
		return _formater;
	}

	public void setFormater(IExportExcelLabelProvider formater) {
		this._formater = formater;
	}

	public ExcelExporterEngine(EObject sourceObject) {
		_startupObject = sourceObject;
	}

	public void setExporter(IRegisteredExcelExporter exporter) {
		_exporter = exporter;
	}

	protected XSSFWorkbook createWorkbook(IFile excelFile) throws IOException {
		 return new XSSFWorkbook();
	 }

	 protected XSSFSheet openExcelFile(IFile excelFile) throws IOException {
		 //

		 // Get the workbook instance for XLS file
		 XSSFWorkbook workbook = createWorkbook(excelFile);

		 // Get first sheet from the workbook
		 XSSFSheet sheet = workbook.createSheet("Excel sheet");

		 return sheet;
	 }

	 public void run () throws ExcelExportException {
		 try {
			 Map<XSSFSheet, IExcelExporter> pageExporters = new HashMap<XSSFSheet, IExcelExporter>();
			 List<XSSFSheet> pageOrder = new ArrayList<XSSFSheet>();
			 XSSFSheet initSheet = null;
			 XSSFWorkbook workbook = null;
			 if (_exporter instanceof IMultipageExcelExporter) {
				 IMultipageExcelExporter multiExporter = (IMultipageExcelExporter)_exporter;
				 workbook = createWorkbook(_excelFile);
				 for (String pageName : multiExporter.getPageNames()) {
					 XSSFSheet page = workbook.createSheet(pageName);
					 pageExporters.put(page, multiExporter.getExporter(pageName));
					 pageOrder.add(page);
				 }
				 setFormater(multiExporter.getExporterLabelProvider());
			 }else{
				 initSheet = openExcelFile(_excelFile);
				 workbook = initSheet.getWorkbook();
				 pageExporters.put(initSheet, (IExcelExporter) _exporter);
				 pageOrder.add(initSheet);
				 setFormater(((IExcelExporter) _exporter).getExporterLabelProvider());
			 }

			 for (XSSFSheet sheet : pageOrder) {
				 IExcelExporter exporter = pageExporters.get(sheet);
				 List<EObject> objectsToExport = exporter.getObjectsToExport (_startupObject);

				 List<EStructuralFeature> featuresToExport = exporter.getFeaturesToExport ();
				 List<String> pvToExport = exporter.getPropertyValuesToExport();

				 // creating the header
				 XSSFRow row = sheet.createRow(0);
				 int column = 0;
				 if (exporter instanceof IAdvancedExcelExporter) {
					 IAdvancedExcelExporter axe = (IAdvancedExcelExporter) exporter;
					 for (String str : axe.prepend(0, _startupObject)) {
						 XSSFCell cel = row.createCell(column++);
						 cel.setCellValue(str);
					 }

				 }
				 for (EStructuralFeature feature : featuresToExport) {
					 XSSFCell cel = row.createCell(column++);
					 cel.setCellValue(feature.getName());
				 }
	             for (String string : pvToExport) {
	                 XSSFCell cel = row.createCell(column++);
	                 cel.setCellValue (string);
	             }

				 // content of the file
				 int line = 1;
				 for (EObject eObject : objectsToExport) {
					 column = 0;
					 XSSFRow datarow = sheet.createRow(line++);
					 if (exporter instanceof IAdvancedExcelExporter) {
						 IAdvancedExcelExporter axe = (IAdvancedExcelExporter) exporter;
						 for (String str: axe.prepend(line, eObject)) {
							 XSSFCell cel = datarow.createCell(column++);
							 cel.setCellValue(str);
						 }

					 }
					 for (EStructuralFeature feature : featuresToExport) {
						 XSSFCell cel = datarow.createCell(column++);
						 Object obj = null;
						 obj = exportAttribute(eObject, feature);
						 if (obj==null)
							 obj = exportReference(eObject, feature);

						 obj = formatValue(obj);
						 cel.setCellValue(obj==null?"null":obj.toString());
					 }

					 for (ExcelExporterMetamodelExtensionDescriptor ext : ExcelExporterMetamodelExtensionRegistry.getRegisteredExtensions()) {
						 IExcelMetamodelExtension extension = ext.getExcelMetamodelExtension();
						 List<String> extStr = extension.generateExtensions (eObject, pvToExport);
						 for (String string : extStr) {
							 XSSFCell cel = datarow.createCell(column++);
							 cel.setCellValue (string);
						 }
					 }


					 if (exporter instanceof IAdvancedExcelExporter) {
						 IAdvancedExcelExporter axe = (IAdvancedExcelExporter) exporter;
						 List<String> strs = axe.postpend(line, eObject);
						 if (strs != null){
							 for (String str: strs) {
								 XSSFCell cel = datarow.createCell(column++);
								 cel.setCellValue(str);
							 }
						 }
					 }

				 }

			 }

			 URI uri = _startupObject.eResource().getURI();
			 URI excelURI = uri.trimFileExtension().appendFileExtension("xlsx");
			 IFile excelFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path (excelURI.toPlatformString(true)));
			 _file = new FileOutputStream(excelFile.getRawLocation().makeAbsolute().toFile());
			 workbook.write(_file);
			 workbook.close();

		 } catch (IOException e){
			 throw new ExcelExportException(e);
		 }
	 }

	
	private Object formatValue(Object obj) {
		if (_formater == null) return obj;
		if (obj instanceof Collection) {
			Collection col = (Collection) obj;
			return _formater.toString(col);
		}
		return _formater.toString(obj);
	}

	private Object exportReference(EObject eObject, EStructuralFeature feature) {
		Object obj = null;
		if (eObject.eClass().getEAllReferences().contains(feature))
			obj =eObject.eGet(feature);
		return obj;
	}

	private Object exportAttribute(EObject eObject, EStructuralFeature feature) {
		Object obj = null;
		if (eObject.eClass().getEAllAttributes().contains(feature))
			obj =eObject.eGet(feature);
		return obj;
	}


}
