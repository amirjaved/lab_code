
package com.agi.catd2.emotion.main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
import com.agi.catd2.emotion.auxiliary.ReadGeoData;
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


public class LondonCrimeSentimentAnalyzer {
	// code for the emotion crime database ....not to be included in the airbus project
	public static double lattop,latbottom,longtop,longbottom;   
    boolean flag_checker=true;
    public int area_number=0;
    public int total_tweet=0;
    public ArrayList<ReadGeoData> geo_data=new ArrayList<>();
    public String eol = System.getProperty("line.separator");
    private BufferedReader br = null;
    public String cvsSplitBy = ",";
    private int final_anger;
	private int final_anticipation;
	private int final_disgust;
	private int final_fear;
	private int final_joy;
	private int final_negative;
	private int final_positive;
	private int final_sadness;
	private int final_suprise;
	private int final_trust;
	private String date="Dec";

	public LondonCrimeSentimentAnalyzer() {
		this.final_anger=this.final_anticipation=this.final_disgust=this.final_fear=this.final_joy=this.final_negative=this.final_positive=this.final_sadness=this.final_suprise=this.final_trust=0;
	}
	public void setanger(int v) {
		this.final_anger=v+this.final_anger;
	}
	public void setanticipation(int v) {
		this.final_anticipation=v+this.final_anticipation;
	}
	public void setdisgust(int v) {
		this.final_disgust=v+this.final_disgust;
	}
	public void setfear(int v) {
		this.final_fear=v+this.final_fear;
	}
	public void setjoy(int v) {
		this.final_joy=v+this.final_joy;
	}
	public void setnegative(int v) {
		this.final_negative=v+this.final_negative;
	}
	public void setpositive(int v) {
		this.final_positive=v+this.final_positive;
	}
	public void setsadness(int v) {
		this.final_sadness=v+this.final_sadness;
	}
	public void setsuprise(int v) {
		this.final_suprise=v+this.final_suprise;
	}
	public void settrust(int v) {
		this.final_trust=v+this.final_trust;
	}
	public void reset_counter() {
		this.final_anger=this.final_anticipation=this.final_disgust=this.final_fear=this.final_joy=this.final_negative=this.final_positive=this.final_sadness=this.final_suprise=this.final_trust=0;
	}
	public String toString() {
		String output=date+","+Integer.toString(this.total_tweet)+","+Integer.toString(this.area_number)+
				","+Integer.toString(this.final_anger)+","+Integer.toString(this.final_anticipation)+
				","+Integer.toString(this.final_disgust)+","+Integer.toString(this.final_fear)+
				","+Integer.toString(this.final_joy)+","+Integer.toString(this.final_negative)+
				","+Integer.toString(this.final_positive)+","+Integer.toString(this.final_sadness)+
				","+Integer.toString(this.final_suprise)+","+Integer.toString(this.final_trust);
		System.out.println(output);
		return output;
	
		
	}
	public void update_emotion(HashMap<String,Integer> emotion)
	{
			setanger(emotion.get("anger"));
			setanticipation(emotion.get("anticipation"));
			setdisgust(emotion.get("disgust"));
			setfear(emotion.get("fear"));
			setjoy(emotion.get("joy"));
			setnegative(emotion.get("negative"));
			setpositive(emotion.get("positive"));
			setsadness(emotion.get("sadness"));
			setsuprise(emotion.get("suprise"));
			settrust(emotion.get("trust"));
    		//System.out.println("Emotion : " + entry.getKey() + " Count : " + entry.getValue());
    	
	}
    public void  loadCSV(String csvfile_input) {
        br = null;
        String line;
        System.out.println(csvfile_input);
        try 
        {
            br = new BufferedReader(new FileReader(csvfile_input));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                String[] value = line.split(cvsSplitBy);
                //System.out.println(value.length);
                if (value.length==4){
                
                if (value.length>4){
                    for(int k=4;k<=value.length;k++){
                    value[3]=value[3]+" "+value[k];
                    }
                }
                try{
                this.geo_data.add(new ReadGeoData(value[0],Float.parseFloat(value[1]),Float.parseFloat(value[2]),value[3]));
                }
                catch(NumberFormatException e){}
                }
                
            }
        } 
        catch (FileNotFoundException e)
        {
            System.out.print("file not found");
        } catch (IOException e) 
        {
            System.out.print("file exception");
        } finally 
        {
                if (br != null) 
                {
                        try
                        {
                                br.close();
                        }
                        catch (IOException e)
                        {
                        }
                }
        } 
    }//end load csv function 
    
	
	/// code for emotion crime ends here 
	
	public static void main(String[] args) throws IOException, JWNLException {
		String pathGroupedSentiments = "src/main/resources/sentiments-collection";
		String pathMap3Dot0ToSentiments = "src/main/resources/3dot0ToSentimentDict";
		String pathOutputFile = "C:/Users/Amir/Desktop/geodata/geo_set1month3.csv";
		String Emolex="src/main/resources/NRC.txt";//line added 
		//String pathInputFile = "src/main/resources/filtered-162.json";
				// input file for vim
		String pathInputFile = "C:/Users/Amir/Desktop/geodata/set1month3.csv";
		Boolean NRCEmotion=false;
		Boolean WordnetEmotion=false;
		// variable for the emotion code 
		lattop = 51.276136;
		latbottom = 51.685798;
		longtop = -0.49024;
		longbottom = 0.320002;
		    
	    double i, j;
	    
		//varibale list ends here 
		
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
		
		//load the emotion crime database file 
		LondonCrimeSentimentAnalyzer SentiEmo=new LondonCrimeSentimentAnalyzer();
		SentiEmo.loadCSV(pathInputFile);
		// Iterate over the lines of the input file
		//BufferedReader br = new BufferedReader(new FileReader(pathInputFile)); //uncomment it for other file 
        //String line;
        writer.write("Date,Total_Tweets,Area_Number,Anger,Anticipation,Disgust,Fear,Joy,Negative,Positive,Sadness,Suprise,Trust\n");
        
        
        ///loops for the london square kilometer grid
        
        
        for (i = lattop; i < latbottom; i += 0.01) {
            for (j = longbottom; j > longtop; j -= 0.014) {
            	SentiEmo.total_tweet = 0;
                List<String> tweet_result_set = new ArrayList<>();
                SentiEmo.area_number += 1;
                for (int l=0;l<SentiEmo.geo_data.size();l++){
                    if ((SentiEmo.geo_data.get(l).geolocLat >= i && SentiEmo.geo_data.get(l).geolocLat <= i + 0.01) && (SentiEmo.geo_data.get(l).geolocLong >= j - 0.014 && SentiEmo.geo_data.get(l).geolocLong <= j)) {
                            tweet_result_set.add(SentiEmo.geo_data.get(l).Text);
                            SentiEmo.total_tweet += 1;
                    }
                }
                SentiEmo.reset_counter();
                for(String body:tweet_result_set) {
                	
                	//body = AuxiliaryProccessingTools.getBody_csv(body);
                	String tweet="";
                	
                	/*
                	 * To overwrite area
                	 */
                	if (!body.isEmpty()) {
                	System.out.println(body);
                	//body = "New Cars Offers  httpstcom3BEtz0T9p Deals Crimestopper FS32 2Way FMFM Keyless Entry Car Alarm Vehicle Se alarm httpstcoJ5a5iotsor" ;
                	//		".";
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
                		tweet=tweet+word+" ";
                		temp=emo.get_emotion_index(word);
                		totalemo.reset();
                		if (temp!=null) {
                			NRCEmotion=true;
                			System.out.println(word);
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
                			for (int k = 0; k < synsetOffesets.length; k++) {
                				HashSet<String> sentiments = mapper3dot0ToSentiment.getSentiments(synsetOffesets[k]);
                				
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
                	SentiEmo.update_emotion(totalemo.get_total_emo());//update emotion for one tweet 
                	 System.out.println(SentiEmo.final_fear);
                	//writer.write(totalemo.toString(tweet)+"\n");
                }
        		
                }///end of tweet iteration in an area 
               writer.write(SentiEmo.toString()+"\n");
               //writer.flush();
       			//writer.close();
       		
            }//j loop ends longitude 
           }// i loop latitude 
      
        writer.flush();
		writer.close();
	}

}
