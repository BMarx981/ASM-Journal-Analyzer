package application;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {
	ArrayList<HashMap<String, String>> map = new ArrayList<HashMap<String, String>>();
	
	ArrayList<Paper> papers = new ArrayList<Paper>();
	ArrayList<String> codes;
	HashMap<String, Integer> idCounts;
	String fileName = "";

	Excel() {}
	
	Excel(ArrayList<HashMap<String, String>> mainMap) {
		this.map = mainMap;
	}
	
	public void createDocument(ArrayList<Paper> papers, ArrayList<String> codes, HashMap<String, Integer> counts, String name) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		fileName = name;
        
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("CHM Data");
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);
        int cellNum = 0;
		for (String code : codes) {
			Cell cell = row.createCell(cellNum++);
			cell.setCellValue(code);
		}
		for (Paper paper : papers) {
			row = sheet.createRow(rowNum++);
			cellNum = 0;
			for (String code : codes) {
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(paper.map.get(code));
			}
			
		}
		rowNum += 3;
		row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(0);
		cell.setCellValue("ID Frequency");
		cell = row.createCell(1);
		cell.setCellValue("ID Term");
		for (String id : counts.keySet()) {
			row = sheet.createRow(rowNum++);
			cell = row.createCell(0);
			String value = counts.get(id).toString();
			cell.setCellValue(value);
			
			cell = row.createCell(1);
			cell.setCellValue(id.trim());
		}
		for (int i = 0; i < 50; i++) {
			sheet.autoSizeColumn(i);
		}
		saveDoc(workbook);
        
	}
	
	public void saveDoc(XSSFWorkbook book) {
		try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(File.separator +"Users" 
            		+ File.separator + "brianmarx" + File.separator + "Desktop" + File.separator 
            		+ "" + fileName + ".xlsx"));
            book.write(out);
            out.close();
            System.out.println("CHMData.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
