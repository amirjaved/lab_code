package com.agi.catd2.emotion.model;

import org.json.JSONObject;

public class TripletWordEmotionResult {
	private String originalLemma;
	private String upperSentiment;
	private String lowerSentiment;
	
	public TripletWordEmotionResult(String originalLemma, String upperSentiment, String lowerSentiment) {
		super();
		this.originalLemma = originalLemma;
		this.upperSentiment = upperSentiment;
		this.lowerSentiment = lowerSentiment;
	}

	public String getOriginalLemma() {
		return originalLemma;
	}

	public void setOriginalLemma(String originalLemma) {
		this.originalLemma = originalLemma;
	}

	public String getUpperSentiment() {
		return upperSentiment;
	}

	public void setUpperSentiment(String upperSentiment) {
		this.upperSentiment = upperSentiment;
	}

	public String getLowerSentiment() {
		return lowerSentiment;
	}

	public void setLowerSentiment(String lowerSentiment) {
		this.lowerSentiment = lowerSentiment;
	}

	@Override
	public String toString() {
		return "[originalLemma=" + originalLemma + ", upperSentiment=" + upperSentiment
				+ ", lowerSentiment=" + lowerSentiment + "]";
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("originalLemma", this.originalLemma);
		json.put("upperSentiment", this.upperSentiment);
		json.put("lowerSentiment", this.lowerSentiment);
		return json;
	}
}
