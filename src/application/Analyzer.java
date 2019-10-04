package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Analyzer {
	public static int fileCount = 1;
	ArrayList<Paper> papers = new ArrayList<Paper>();
	private ArrayList<String> codes;
	HashMap<String, Integer> idCounts = new HashMap<String, Integer>();
	String fileName = "";
	LinkedList<String> q = new LinkedList<String>();

	Analyzer(ArrayList<String> codes) {
		this.codes = codes;
	}

	public void processFile(String file, ArrayList<String> list) {
		codes.clear();
		codes = list;
		processFile(file);
	}

	public void processFile(String file) {
		BufferedReader buffer = null;
		try {
			FileReader fileReader = new FileReader(file);
			buffer = new BufferedReader(fileReader);
			String line = buffer.readLine();

			// While the buffer is able to read another line continue looping
			while ((line = buffer.readLine()) != null) {
				// Check if the line is blank
				if (line.contentEquals("") || line.substring(0, 2).equals("  ")) {
					continue;
				}

				// If the code is found in the begining of the line
				// begin analyzing the lines and add the paper
				// that returns to papers ArrayList
				if (codes.contains(line.substring(0, 2))) {
					Paper paper = analyzeRecord(buffer, line);
					papers.add(paper);
				}
			}
			processAllIds();
			buffer.close();
			fileCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		Excel excel = new Excel();
		excel.createDocument(papers, codes, idCounts, fileName);
	}

	private Paper analyzeRecord(BufferedReader buffer, String line) throws IOException {
		ArrayList<String> qList = new ArrayList<String>();
		Paper paper = new Paper();
		
		qList.add(line);
		while ((line = buffer.readLine()) != null 
				&& !(line.substring(0, 2).equals("ER"))) {
			qList.add(line);
		}

		for (int i = 0; i < qList.size(); i++) {
			String s = qList.get(i).substring(3);
			String code = qList.get(i).substring(0, 2);
			if (code.equals("JI")) 
				fileName = s;
			
			if (codes.contains(code)) {
				while (i != qList.size() 
						&& qList.get(i + 1).substring(0,2).equals("  ")) {
					s = s + " " + qList.get(++i).substring(3);
				}
				processCodes(s, code, paper);
			}
		}
		return paper;
	}

	private void processCodes(String line, String code, Paper paper) {
		paper.addToMap(code, line);

		// Place the ids in the paper's array
		if (code.equals("ID"))
			paper.processIds(line);
	}

	// Processes the ids so that they can be displayed in
	// Excel as a separate table.
	private void processAllIds() {
		for (Paper paper : papers) {
			List<String> ids = paper.getIds();
			for (String id : ids) {
				// If the id is in the idCounts increment the count by one
				if (idCounts.containsKey(id))
					idCounts.put(id, idCounts.get(id) + 1);
				else // otherwise put it in the map with the value of one
					idCounts.put(id, 1);
			}
		}
	}

}
