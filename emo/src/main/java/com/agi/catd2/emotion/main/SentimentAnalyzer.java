package com.agi.catd2.emotion.main;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.agi.catd2.emotion.auxiliary.AuxiliaryProccessingTools;
import com.agi.catd2.emotion.model.EmotionResult;
import com.agi.catd2.emotion.model.Mapper3dot0ToSentiment;
import com.agi.catd2.emotion.model.MapperUpperSentiments;
import com.agi.catd2.emotion.model.PrimaryEmotion;
import com.agi.catd2.emotion.model.TripletWordEmotionResult;
import com.agi.catd2.emotion.model.emolexicon;

import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.dictionary.Dictionary;

public class SentimentAnalyzer {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, JWNLException {
		String pathGroupedSentiments = "src/main/resources/sentiments-collection";
		String pathMap3Dot0ToSentiments = "src/main/resources/3dot0ToSentimentDict";
		String pathOutputFile = "src/main/resources/output.txt";
		String Emolex="src/main/resources/NRC.txt";//line added 
		Boolean NRCEmotion=false;
		Boolean WordnetEmotion=false;
		// Load the resources
		Mapper3dot0ToSentiment mapper3dot0ToSentiment = new Mapper3dot0ToSentiment(pathMap3Dot0ToSentiments);
		mapper3dot0ToSentiment.load();
		
		MapperUpperSentiments mapperUpperSentiments = new MapperUpperSentiments(pathGroupedSentiments);
		mapperUpperSentiments.load();
		
		
		
		/* Prepare a file to store the results */
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathOutputFile), StandardCharsets.UTF_8);
		
		
		// Instantiate the WordNet dictionary
		Dictionary d = Dictionary.getDefaultResourceInstance();
		
		
		// START THE PROCESSING
		String pathInputFile = "src/main/resources/filtered-162.json";
		
		// Iterate over the lines of the input file
		BufferedReader br = new BufferedReader(new FileReader(pathInputFile)); 
        String line;
        while((line=br.readLine())!=null) {
        	
        	String body = AuxiliaryProccessingTools.getBody(line);
        	
        	
        	/*
        	 * To overwrite area
        	 */
        	body = "New Cars Offers  httpstcom3BEtz0T9p Deals Crimestopper FS32 2Way FMFM Keyless Entry Car Alarm Vehicle Se alarm httpstcoJ5a5iotsor" + 
        			".";
        	//body= AuxiliaryProccessingTools.processBody(body);
        	
        	
        	List<String> processedBody = AuxiliaryProccessingTools.processBody(body);
        	System.out.println(processedBody);
        	
        	
        	//insert the NRC code here 
        	emolexicon emo=new emolexicon(Emolex);
    		emo.load();
    		ArrayList<HashMap<String,Integer>> temp=new ArrayList<HashMap<String,Integer>>();
    		String key;
        	PrimaryEmotion totalemo=new PrimaryEmotion();
        	
        	
        	// Instantiate the class in charge of storing the results
        	EmotionResult emotionResult = new EmotionResult(body, new ArrayList<TripletWordEmotionResult>());
        	
        	// Start iterating over the words
        	for (String word : processedBody) {
        		//user NRC emolex to find emotion
        		temp=emo.get_emotion_index(word);
        		totalemo.reset();
        		if (temp!=null) {
        			NRCEmotion=true;
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
		    			}//forloop
        		}//if statement
        		//end inserted code 
        		// Use WordNet
        		IndexWord indexWord = d.lookupIndexWord(POS.VERB, word);
        		if(indexWord == null) {
        			indexWord = d.lookupIndexWord(POS.ADVERB, word);
        			
        		}
        		if(indexWord == null) {
        			indexWord = d.lookupIndexWord(POS.ADJECTIVE, word);
        			
        		}
        		if(indexWord == null) {
        			indexWord = d.lookupIndexWord(POS.NOUN, word);
        			
        		}
        		
        		// The word needs to exist in WordNet
        		// then do majority vote 
        		if (indexWord != null) {
        			
        			// Get the synsets
        			int[] synsetOffesets = AuxiliaryProccessingTools.convertFloatArrayIntoIntegers(indexWord.getSynsetOffsets());
        			
        			// Use a HashSet to store the sentiments detected for all the possible synsets
        			HashSet<String> detectedSentiments= new HashSet<String>();
        			
        			// Iterate over the synset values for a single lemma
        			for (int i = 0; i < synsetOffesets.length; i++) {
        				HashSet<String> sentiments = mapper3dot0ToSentiment.getSentiments(synsetOffesets[i]);
        				
        				// The synset needs to have an emotion attached
        				if (sentiments != null) {
        					detectedSentiments.addAll(sentiments);
        				}
        			}
        			
        			// Iterate over the detected sentiments and update the emotionResult instance
        			for (String detectedSentiment : detectedSentiments) {
        				String upperSentiment = mapperUpperSentiments.getUpperSentiment(detectedSentiment);
        				
        				// Make sure there is a upper sentiment attached to the sentiment
        				
        				if (upperSentiment != null) {
        					WordnetEmotion=true;
        					totalemo.wordnet_emotion(upperSentiment);
        					TripletWordEmotionResult triplet = new TripletWordEmotionResult(word, upperSentiment, detectedSentiment);
        					emotionResult.addScore(triplet);
        				}
        			}
        			
        		}
        		totalemo.majority_vote();
        	}// keyword itieration 
        	for (HashMap.Entry<String, Integer> entry : totalemo.get_total_emo().entrySet()) {
        		System.out.println("Emotion : " + entry.getKey() + " Count : " + entry.getValue());
        	}
    	
        	if (emotionResult.getScore().size() != 0) {
        		System.out.println(emotionResult.toString());
        		//writer.write(emotionResult.toString() + "\n");
        	}
        	
        }
		br.close();
		
		
		writer.flush();
		writer.close();
	}

}
