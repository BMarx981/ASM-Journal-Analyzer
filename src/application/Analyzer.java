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
//			System.out.println(fileReader.getEncoding().toString());
			buffer = new BufferedReader(fileReader);
			String line = buffer.readLine();
			Paper paper = new Paper();
			 
			while((line = buffer.readLine()) != null) {

				if (line.equals("")) continue;
				
				if (line.substring(0, 2).equals("ER")) {
					papers.add(paper);
					paper = new Paper();
					continue;
				}
				
				for(String s : codes) {
					processCodes(line, s, paper);
				}
			}
			for (Paper h : papers) {
				System.out.println(h.map);
			}
			processAllIds();
			System.out.println();
			System.out.println(idCounts);
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processCodes(String line, String code, Paper paper) {
		if(line.substring(0, 2).contains(code)) {
			paper.addToMap(code, line.substring(2));
			if (code.equals("ID")) 
				paper.processIds(line.substring(2));
			
		}
	}
	
	private void processAllIds() {
		for (Paper paper : papers) {
			List<String> ids = paper.getIds();
			for (String id : ids) {
				if (idCounts.containsKey(id)) 
					idCounts.put(id, idCounts.get(id) + 1);
				else 
					idCounts.put(id, 1);
			}
		}
	}
	
	
}
