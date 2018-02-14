package com.agi.catd2.emotion.model;

import java.util.HashMap;

public class PrimaryEmotion {
	private int anger;
	private int anticipation;
	private int disgust;
	private int fear;
	private int joy;
	private int negative;
	private int positive;
	private int sadness;
	private int suprise;
	private int trust;
	private int WDA_anger;
	private int WDA_anticipation;
	private int WDA_disgust;
	private int WDA_fear;
	private int WDA_joy;
	private int WDA_negative;
	private int WDA_positive;
	private int WDA_sadness;
	private int WDA_suprise;
	private int WDA_trust;
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
	private HashMap<String,Integer> thistion=new HashMap<String,Integer>();
	public PrimaryEmotion(){
		this.anger=this.anticipation=this.disgust=this.fear=this.joy=this.negative=this.positive=this.sadness=this.suprise=this.trust=0;
		this.WDA_anger=this.WDA_anticipation=this.WDA_disgust=this.WDA_fear=this.WDA_joy=this.WDA_negative=this.WDA_positive=this.WDA_sadness=this.WDA_suprise=this.WDA_trust=0;
		this.final_anger=this.final_anticipation=this.final_disgust=this.final_fear=this.final_joy=this.final_negative=this.final_positive=this.final_sadness=this.final_suprise=this.final_trust=0;
	}
	public void setanger(int v) {
		this.anger=v+this.anger;
	}
	public void setanticipation(int v) {
		this.anticipation=v+this.anticipation;
	}
	public void setdisgust(int v) {
		this.disgust=v+this.disgust;
	}
	public void setfear(int v) {
		this.fear=v+this.fear;
	}
	public void setjoy(int v) {
		this.joy=v+this.joy;
	}
	public void setnegative(int v) {
		this.negative=v+this.negative;
	}
	public void setpositive(int v) {
		this.positive=v+this.positive;
	}
	public void setsadness(int v) {
		this.sadness=v+this.sadness;
	}
	public void setsuprise(int v) {
		this.suprise=v+this.suprise;
	}
	public void settrust(int v) {
		this.trust=v+this.trust;
	}
	//methods to retrieve values
	public int getanger() {return this.anger;}
	public int getanticipation() {return this.anticipation;}
	public int getdisgust() {return this.disgust;}
	public int getfear() {return this.fear;}
	public int getjoy() {return this.joy;}
	public int getnegative() {return this.negative;}
	public int getpositive() {return this.positive;}
	public int getsadness() {return this.sadness;}
	public int getsuprise() {return this.suprise;}
	public int gettrust() {return this.trust;}
	public void reset() {
		this.anger=this.anticipation=this.disgust=this.fear=this.joy=this.negative=this.positive=this.sadness=this.suprise=this.trust=0;
		this.WDA_anger=this.WDA_anticipation=this.WDA_disgust=this.WDA_fear=this.WDA_joy=this.WDA_negative=this.WDA_positive=this.WDA_sadness=this.WDA_suprise=this.WDA_trust=0;
	}
	public HashMap<String,Integer> get_total_emo(){
		this.thistion.put("anger", this.final_anger);
		this.thistion.put("anticipation", this.final_anticipation);
		this.thistion.put("disgust", this.final_disgust);
		this.thistion.put("fear", this.final_fear);
		this.thistion.put("joy", this.final_joy);
		this.thistion.put("negative", this.final_negative);
		this.thistion.put("positive", this.final_positive);
		this.thistion.put("sadness", this.final_sadness);
		this.thistion.put("suprise", this.final_suprise);
		this.thistion.put("trust", this.final_trust);
		return thistion;
	}
	public void wordnet_emotion(String emotion ) {
		switch(emotion) {
		case "anger": this.WDA_anger=1;break;
		case "anticipation": this.WDA_anticipation=1;break;
		case "disgust": this.WDA_disgust=1;break;
		case "fear" : this.WDA_fear=1;break;
		case "joy" : this.WDA_joy=1;break;
		case "negative" : this.WDA_negative=1;break;
		case "positive" : this.WDA_positive=1;break;
		case "sadness" : this.WDA_sadness=1;break;
		case "suprise": this.WDA_suprise=1;break;
		case "trust" :this.WDA_trust=1;break;
		}
	}
	public void majority_vote() {
		if(this.anger==1 && this.WDA_anger==1) {this.final_anger+=1;}else {this.final_anger+=Math.max(this.anger,this.WDA_anger);}
		if(this.anticipation==1 && this.WDA_anticipation==1) {this.final_anticipation+=1;} else {this.final_anticipation+=Math.max(this.anticipation, this.WDA_anticipation);}
		if(this.disgust==1 && this.WDA_disgust==1) {this.final_disgust+=1;} else {this.final_disgust+=Math.max(this.disgust,this.WDA_disgust);}
		if(this.fear==1 && this.WDA_fear==1) {this.final_fear+=1;} else {this.final_fear+=Math.max(this.fear, this.WDA_fear);}
		if(this.joy==1 && this.WDA_joy==1) {this.final_joy+=1;} else {this.final_joy+=Math.max(this.joy, this.WDA_joy);}
		if(this.negative==1 && this.WDA_negative==1) {this.final_negative+=1;} else {this.final_negative+=Math.max(this.negative, this.WDA_negative);}
		if(this.positive==1 && this.WDA_positive==1) {this.final_positive+=1;} else {this.final_positive+=Math.max(this.positive, this.WDA_positive);}
		if(this.sadness==1 && this.WDA_sadness==1) {this.final_sadness+=1;} else {this.final_sadness+=Math.max(this.sadness, this.WDA_sadness);}
		if(this.suprise==1 && this.WDA_suprise==1) {this.final_suprise+=1;} else {this.final_suprise+=Math.max(this.WDA_suprise, this.suprise);}
		if(this.trust==1 && this.WDA_trust==1) {this.final_trust+=1;} else {this.final_trust+=Math.max(this.trust,this.WDA_trust);}
	}
	
}
	
