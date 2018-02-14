package com.agi.catd2.emotion.auxiliary;

public class ReadGeoData {
	 	public String Created;
	    public float geolocLat;
	    public float geolocLong;
	    public String Text;
	    public ReadGeoData(String s, float lat, float lon, String text){
	    this.Created=s;
	    this.geolocLat=lat;
	    this.geolocLong=lon;
	    this.Text=text;
	    }
}