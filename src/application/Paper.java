package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Paper {
	HashMap<String, String> map = new HashMap<String, String>();
	List<String> ids = new ArrayList<String>();

	Paper() {
	}

	Paper(HashMap<String, String> inputMap, ArrayList<String> idList) {
		this.map = inputMap;
		this.ids = idList;
	}

	public void addToMap(String key, String value) {
		map.put(key, value);
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void insertID(ArrayList<String> strings) {
		for (String s : strings) {
			ids.add(s);
		}
	}

	public void processIds(String ids) {
		String[] stringArray = ids.split(Pattern.quote(";"));
		this.ids = Arrays.asList(stringArray);
	}

	public List<String> getIds() {
		return ids;
	}

}
