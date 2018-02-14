package com.agi.catd2.emotion.auxiliary;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONObject;

public class AuxiliaryProccessingTools {
	
	public static int[] convertFloatArrayIntoIntegers(long[] input) {
		int[] resultArray = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			resultArray[i] = (int) input[i];
		}
		return resultArray;
	}
	
	public static ArrayList<String> processBody(String body) {
		ArrayList<String> processedString = new ArrayList<String>();
		body = body.toLowerCase();
		StringTokenizer tokenizer = new StringTokenizer(body, " \t\n\r\f,.:;?![]'");	
    	while (tokenizer.hasMoreTokens()) {
    		processedString.add(tokenizer.nextToken());
    	}
		return processedString;
	}
	
	public static String getBody(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		return json.getJSONObject("resourceData").getString("body");
		//change 
		
	}
	
}
