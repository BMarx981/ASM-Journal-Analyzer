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

	Excel() {}
	
	Excel(ArrayList<HashMap<String, String>> mainMap) {
		this.map = mainMap;
	}
	
	public void createDocument(ArrayList<Paper> papers, ArrayList<String> codes, HashMap<String, Integer> counts) {
		XSSFWorkbook workbook = new XSSFWorkbook();
        
        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("CHM Data");
        int rowNum = 0;
        
		for (Paper paper : papers) {
			Row row = sheet.createRow(rowNum++);
			int cellNum = 0;
			for (String code : codes) {

				Cell cell = row.createCell(cellNum++);
				cell.setCellValue(code);
			}
		}
		saveDoc(workbook);
        
	}
	
	public void saveDoc(XSSFWorkbook book) {
		try {
            //Write the workbook in file system Macintosh\ HD⁩/Users⁩/⁨brianmarx⁩
            FileOutputStream out = new FileOutputStream(new File("/Users/brianmarx/Desktop/CHMData.xlsx"));
            book.write(out);
            out.close();
            System.out.println("CHMData.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
