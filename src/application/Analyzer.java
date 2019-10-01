package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Analyzer {
	ArrayList<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
	ArrayList<String> codes;
	
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
			HashMap<String, String> map = new HashMap<String, String>();
			 
			while((line = buffer.readLine()) != null) {

				if (line.equals("") ) continue;
				
				if (line.substring(0, 2).equals("ER")) {
					tempList.add(map);
					map = new HashMap<String, String>();
					continue;
				}
				
				for(String s : codes) {
					if(line.substring(0, 2).contains(s)) {
						map.put(s, line.substring(2));
					}
				}
			}
			for (HashMap<String, String> h : tempList) {
				System.out.println(h);
			}
			System.out.println(tempList.size());
			buffer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
