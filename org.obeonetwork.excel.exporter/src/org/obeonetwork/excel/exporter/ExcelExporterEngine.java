package org.obeonetwork.excel.exporter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

import com.sun.javafx.PlatformUtil;

public class ExcelExporterEngine {
	private IFile _excelFile;
	private EObject _startupObject;
	FileOutputStream _file;

	IExcelExporter _exporter;

	public ExcelExporterEngine(EObject sourceObject) {
		_startupObject = sourceObject;
	}

	public void setExporter(IExcelExporter exporter) {
		_exporter = exporter;
	}

	protected XSSFSheet openExcelFile(IFile excelFile) throws IOException {
//		

		// Get the workbook instance for XLS file
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Get first sheet from the workbook
		XSSFSheet sheet = workbook.createSheet("Excel sheet");

		return sheet;
	}

	public void run () throws ExcelExportException {
		try {
			XSSFSheet sheet = openExcelFile(_excelFile);
			
			List<EObject> objectsToExport = _exporter.getObjectsToExport (_startupObject);

			List<EStructuralFeature> featuresToExport = _exporter.getFeaturesToExport ();

			// creating the header
			XSSFRow row = sheet.createRow(0);
			int column = 0;
			for (EStructuralFeature feature : featuresToExport) {
				XSSFCell cel = row.createCell(column++);
				cel.setCellValue(feature.getName());
			}

			// content of the file
			int line = 1;
			for (EObject eObject : objectsToExport) {
				column = 0;
				XSSFRow datarow = sheet.createRow(line++);
				for (EStructuralFeature feature : featuresToExport) {
					XSSFCell cel = datarow.createCell(column++);
 					Object obj =eObject.eGet(feature);
					cel.setCellValue(obj==null?"null":obj.toString());
				}
				
			}
			URI uri = _startupObject.eResource().getURI();
			URI excelURI = uri.trimFileExtension().appendFileExtension("xlsx");
			IFile excelFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path (excelURI.toPlatformString(true))); 
			_file = new FileOutputStream(excelFile.getRawLocation().makeAbsolute().toFile());
			sheet.getWorkbook().write(_file);
		} catch (IOException e){
			throw new ExcelExportException(e);
		}
	}
}
