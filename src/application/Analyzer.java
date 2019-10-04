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
	ArrayList<String> codes;
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
			Paper paper = new Paper();

			// While the buffer is able to read another line continue looping
			while ((line = buffer.readLine()) != null) {

				// If the line is blank read next line
				if (line.equals(""))
					continue;

				// If the record ends store the paper and create a new one
				if (line.substring(0, 2).equals("ER")) {
					papers.add(paper);
					paper = new Paper();
					continue;
				}
				// Gets the file name from the JI code line
				// This must be checked every line since JI may not be selected
				if (line.substring(0, 2).equals("JI")) {
					if (line.substring(0, 2).contains("JI")) {
						fileName = line.substring(2) + fileCount;
					}
				}
				// each code in the array codes gets sent to process codes
				// where the code, paper and the line are placed in the Hashmap
				if (codes.contains(line.substring(0, 2))) {
					processCodes(line, line.substring(0, 2), paper, buffer);
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

	private void processCodes(String line, String code, Paper paper, BufferedReader buffer) throws IOException {
		paper.addToMap(code, line.substring(2));

		// Place the ids in the paper's array
		if (code.equals("ID"))
			paper.processIds(line.substring(2));

		String temp = line.substring(2);

		// Check if the next line's code is empty. If so add it to temp and place
		// That value in the paper's map.
		if ((line = buffer.readLine()) != null && line.substring(0, 2).equals("  ")) {
			temp = temp + line.substring(2);
			paper.addToMap(code, temp);
		}
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
