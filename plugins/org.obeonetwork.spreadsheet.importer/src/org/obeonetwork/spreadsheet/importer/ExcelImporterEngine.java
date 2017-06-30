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
 *******************************************************************************/package org.obeonetwork.spreadsheet.importer;

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
				// removing padding top lines
				while (line.size() ==  0) {
					line= parseLine (rowIterator.next());					
				}
				_importer.computeFirstLine(line);
				
				
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					line = parseLine (row);
					_importer.computeOtherLine(row.getRowNum(), line);
				}
				
				_importer.importEnded();
				
			} catch (Exception e) {
				throw new ExcelImportException(e);
			}
		}

		private List<String> parseLine(Row line) {
			if (line.getLastCellNum()<0) 
				return new ArrayList<String>();
			List<String> result = new ArrayList<String>(line.getLastCellNum());
			Iterator<Cell> lineIterator = line.cellIterator();
			
			// the iterator doesn't return empty cells, so we need to
			// insert them as empty string in the resulting list
			int currentColumn = 0;
			while (lineIterator.hasNext()) {				
				Cell cell = lineIterator.next();
				while (currentColumn < cell.getColumnIndex()){
					// rattrapage auto due aux colonnes vides
					result.add("");
					currentColumn ++;
				}
				String strCell = "";
				int type = cell.getCellType();
				if (type == Cell.CELL_TYPE_NUMERIC){
					strCell = String.valueOf(cell.getNumericCellValue());					
				} else {
					strCell = cell.getStringCellValue();
				}
				result.add(strCell.trim()); // trim a valider à l'usage
				currentColumn++;
			}
			return result;			
		}
}
