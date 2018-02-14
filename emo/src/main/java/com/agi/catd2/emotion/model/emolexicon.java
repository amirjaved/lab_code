package com.agi.catd2.emotion.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.agi.catd2.emotion.auxiliary.AuxiliaryProccessingTools;

import java.util.ArrayList;

public class emolexicon {
	private HashMap<String,ArrayList<HashMap<String,Integer>>> map = new HashMap<String,ArrayList<HashMap<String,Integer>>>();
	private String pathDataFile;
	public ArrayList<HashMap<String,Integer>> get_emotion_index(String word){
		return this.map.get(word);
	}
	public emolexicon(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}
	
	public void load() throws IOException {
		String errorMessage = null;
		try {
		BufferedReader br = new BufferedReader(new FileReader(pathDataFile));
		String line;
		//ArrayList emo= new ArrayList();
		
        while((line=br.readLine())!=null) {
    
        	String word = line.split("\t")[0];
        	HashMap<String,Integer> emotion = new HashMap<String,Integer>();
        	emotion.put(line.split("\t")[1], Integer.parseInt(line.split("\t")[2]));
        	if (this.map.containsKey(word)){
        		this.map.get(word).add(emotion);
        	}
        	else {
        		//temp.add(emotion);
        		this.map.put(word, new ArrayList<HashMap<String,Integer>>());
        		this.map.get(word).add(emotion);
        	}
        	
        	
        }
        br.close();
		}
		catch(Exception e){
	    System. out.println("In Exception block.");
	    errorMessage = e.getMessage();
		}finally{
			if (errorMessage!=null) {
	    System.out.println(errorMessage);}
	}       
	}
	
	
	// Return null if there is not sentiment attached to the id
	//public HashMap<String,Integer> getSentiments(String id) {
		//if (this.map.containsKey(id)) {
	//		return map.get(id);
		//} else {
		//	return null;
		//}
	//}

//	public HashMap<String,HashMap<String,Integer>> getMap() {
	//	return map;
	//}

//	public void setMap(HashMap<String,HashMap<String,Integer>> map) {
	//	this.map = map;
	//}

	public String getPathDataFile() {
		return pathDataFile;
	}

	public void setPathDataFile(String pathDataFile) {
		this.pathDataFile = pathDataFile;
	}	
	public void get_NRC_Emtion(HashMap<String, Integer> temp) {
		
	}
	public static void main (String[] args) throws IOException  {
		
		
		emolexicon emo=new emolexicon("src/main/resources/NRC.txt");
		emo.load();
		ArrayList<HashMap<String,Integer>> temp=new ArrayList<HashMap<String,Integer>>();
		temp=emo.map.get("abandoned");
		String key;
		String body = "New Cars Offers  httpstcom3BEtz0T9p Deals Crimestopper FS32 2Way FMFM Keyless Entry Car Alarm Vehicle Se httpstcoJ5a5iotsor" + 
    			".";
		List<String> processedBody = AuxiliaryProccessingTools.processBody(body);
    	System.out.println(processedBody);
    	PrimaryEmotion totalemo=new PrimaryEmotion();
    	for(String word: processedBody) {
    		temp=emo.map.get(word);
    		if (temp!=null) {
		for (HashMap<String,Integer> x :temp){
			key=(String) x.keySet().toArray()[0];
			switch(key) {
			case "anger": totalemo.setanger(x.get(key));break;
			case "anticipation": totalemo.setanticipation(x.get(key));break;
			case "disgust": totalemo.setdisgust(x.get(key));break;
			case "fear" : totalemo.setfear(x.get(key));break;
			case "joy" : totalemo.setjoy(x.get(key));break;
			case "negative" : totalemo.setnegative(x.get(key));break;
			case "positive" : totalemo.setpositive(x.get(key));break;
			case "sadness" : totalemo.setsadness(x.get(key));break;
			case "suprise": totalemo.setsuprise(x.get(key));break;
			case "trust" :totalemo.settrust(x.get(key));break;
			}//swtich case
			
			//System.out.println(key);
			//System.out.println(x.get(key));
			}//forloop
    		}//if statement
		}//for loop for tweet keywords
    	for (HashMap.Entry<String, Integer> entry : totalemo.get_total_emo().entrySet()) {
    		System.out.println("Emotion : " + entry.getKey() + " Count : " + entry.getValue());
    	}
	}

}
