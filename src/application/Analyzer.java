package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Analyzer {
	ArrayList<Paper> papers = new ArrayList<Paper>();
	ArrayList<String> codes;
	HashMap<String, Integer> idCounts = new HashMap<String, Integer>();
	String fileName = "";
	
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
			 
			while((line = buffer.readLine()) != null) {

				//If the line is blank read next line
				if (line.equals("")) continue;
				
				//if the record ends store the paper and create a new one
				if (line.substring(0, 2).equals("ER")) {
					papers.add(paper);
					paper = new Paper();
					continue;
				}
				if (line.substring(0, 2).equals("JI")) {
					if(line.substring(0, 2).contains("JI")) {
						fileName = line.substring(2);
					}
						
				}
				//each code in the array codes gets sent to process codes
				//where the code, paper and the line are placed in the Hashmap
				for(String s : codes) {
					processCodes(line, s, paper);
				}
			}
			processAllIds();
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Excel excel = new Excel();
		excel.createDocument(papers, codes, idCounts, fileName);
	}
	
	private void processCodes(String line, String code, Paper paper) {
		if(line.substring(0, 2).contains(code)) {
			paper.addToMap(code, line.substring(2));
			//process the ids if the key is ID
			if (code.equals("ID")) 
				paper.processIds(line.substring(2));
			
		}
	}
	
	private void processAllIds() {
		for (Paper paper : papers) {
			List<String> ids = paper.getIds();
			for (String id : ids) {
				//If the id is in the idCounts increment the count by one
				if (idCounts.containsKey(id)) 
					idCounts.put(id, idCounts.get(id) + 1);
				else //otherwise put it in the map with the value of one
					idCounts.put(id, 1);
			}
		}
	}
	
	
}
