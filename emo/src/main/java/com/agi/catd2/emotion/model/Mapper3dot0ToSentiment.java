package com.agi.catd2.emotion.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Mapper3dot0ToSentiment {
	private HashMap<Integer, HashSet<String>> map = new HashMap<Integer, HashSet<String>>();
	private String pathDataFile;
	
	public Mapper3dot0ToSentiment(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}
	
	public void load() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(pathDataFile));
		String line;
        while((line=br.readLine())!=null) {
        	int id = Integer.parseInt(line.split(":")[0]);
        	HashSet<String> set = new HashSet<String>();
        	for (int i = 0; i < line.split(":")[1].split(",").length; i++) {
        		set.add(line.split(":")[1].split(",")[i]);
        	}
        	this.map.put(id, set);
        }
        br.close();
	}
	
	
	// Return null if there is not sentiment attached to the id
	public HashSet<String> getSentiments(int id) {
		if (this.map.containsKey(id)) {
			return map.get(id);
		} else {
			return null;
		}
	}

	public HashMap<Integer, HashSet<String>> getMap() {
		return map;
	}

	public void setMap(HashMap<Integer, HashSet<String>> map) {
		this.map = map;
	}

	public String getPathDataFile() {
		return pathDataFile;
	}

	public void setPathDataFile(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}	
}
