package com.agi.catd2.emotion.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MapperUpperSentiments {
	
	private HashMap<String, String> map = new HashMap<String, String>();
	private String pathDataFile;
	
	public MapperUpperSentiments(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}
	
	public void load() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(pathDataFile));
		String line;
		while((line=br.readLine())!=null) {
			String upperSentiment = line.split(":")[0];
			String[] sentiments = line.split(":")[1].split(",");
			
			for (int i = 0; i < sentiments.length; i++) {
				if (!map.containsKey(sentiments[i])) {
					map.put(sentiments[i], upperSentiment);
				} else {
					System.err.println("Error loading grouped sentiments file: Repeated elements.");
					System.out.println(sentiments[i]);
				}
			}
			
		}
		br.close();
	}
	
	public String getUpperSentiment(String sentiment) {
		if (map.containsKey(sentiment)) {
			return map.get(sentiment);
		} else {
			//System.err.println("Error: Impossible to find the upper sentiment.");
			return null;
		}
	}

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}

	public String getPathDataFile() {
		return pathDataFile;
	}

	public void setPathDataFile(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}
	
	
}
