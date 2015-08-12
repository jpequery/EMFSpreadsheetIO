package org.obeonetwork.excel.importer;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

public class ExcelImporterEngine {

		private IFile _excelFile;
		private EObject _destination;
		
		IExcelImporter _importer;

		public ExcelImporterEngine (IFile excelFile, EObject destination){
			_excelFile = excelFile;
			_destination = destination;
		}
		
		public void setImporter (IExcelImporter importer){
			_importer = importer;
		}

		protected XSSFSheet openExcelFile (IFile excelFile) throws IOException{
			FileInputStream file = new FileInputStream(excelFile.getRawLocation().makeAbsolute().toFile());
            
			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook (file);
			 
			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			return sheet;
		}
		
		public void run () throws ExcelImportException {
			try {
				XSSFSheet sheet = openExcelFile(_excelFile);
				
				_importer.setDestination(_destination);
				Iterator<Row> rowIterator = sheet.iterator();
				List<String> line= parseLine (rowIterator.next());
				_importer.computeFirstLine(line);
				
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					line = parseLine (row);
					_importer.computeOtherLine(row.getRowNum(), line);
				}
				
				
			} catch (Exception e) {
				throw new ExcelImportException(e);
			}
		}

		private List<String> parseLine(Row line) {
			List<String> result = new ArrayList<String>(line.getLastCellNum());
			Iterator<Cell> lineIterator = line.cellIterator();
			
			while (lineIterator.hasNext()) {
				Cell cell = lineIterator.next();
				String strCell = "";
				int type = cell.getCellType();
				if (type == Cell.CELL_TYPE_NUMERIC){
					strCell = String.valueOf(cell.getNumericCellValue());					
				} else {
					strCell = cell.getStringCellValue();
				}
				result.add(strCell);
			}
			return result;			
		}
}
