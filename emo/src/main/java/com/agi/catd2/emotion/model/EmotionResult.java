package com.agi.catd2.emotion.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class EmotionResult {
	private String body;
	private List<TripletWordEmotionResult> score;
	
	public EmotionResult(String body, List<TripletWordEmotionResult> score) {
		super();
		this.body = body;
		this.score = score;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<TripletWordEmotionResult> getScore() {
		return score;
	}

	public void setScore(List<TripletWordEmotionResult> score) {
		this.score = score;
	}
	
	public void addScore(TripletWordEmotionResult triplet) {
		this.score.add(triplet);
	}

	@Override
	public String toString() {
		return body + ":" + score.toString();
	}
	
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		json.put("body", body);
		JSONArray jsonArray = new JSONArray();
		for (TripletWordEmotionResult triplet : score) {
			jsonArray.put(triplet.getJSON());
		}
		json.put("results", jsonArray);
		return json;
	}
}
