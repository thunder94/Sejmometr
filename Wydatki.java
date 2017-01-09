package agh_lab9;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Wydatki {
	
	private JsonObject rok2013;
	private JsonObject rok2012;
	private JsonObject rok;
	private int roczniki;
	
	public Wydatki(String id) throws IOException {
		String sURL = "https://api-v3.mojepanstwo.pl/dane/poslowie/" + id + ".json?layers[]=wydatki";
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jparser = new JsonParser();
		JsonElement jelement = jparser.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject jobj = jelement.getAsJsonObject();
		jobj = jobj.getAsJsonObject("layers");
		jobj = jobj.getAsJsonObject("wydatki");
		JsonArray jarray = jobj.getAsJsonArray("roczniki");
		if(jarray.size()==2) {
			this.roczniki=2;
			this.rok2013 = jarray.get(0).getAsJsonObject();
			this.rok2012 = jarray.get(1).getAsJsonObject();
		} else if(jarray.size()==1) {
			this.roczniki=1;
			this.rok = jarray.get(0).getAsJsonObject();
		} else this.roczniki=0;
	}
	
	public double sumOfExpenses() {
		if(roczniki==0) {
			return 0;
		}
		double sumOfExpenses=0;
		if(roczniki==2) {
			JsonArray jarray = rok2013.getAsJsonArray("pola");
			if(jarray.size()==20) {
				for(int i=0; i<20; i++) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}	
			if(jarray.size()==40) {
				for(int i=0; i<40; i=i+2) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}
			jarray = rok2012.getAsJsonArray("pola");
			if(jarray.size()==20) {
				for(int i=0; i<20; i++) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}
			if(jarray.size()==40) {
				for(int i=0; i<40; i=i+2) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}
		} else {
			JsonArray jarray = rok.getAsJsonArray("pola");
			if(jarray.size()==20) {
				for(int i=0; i<20; i++) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}	
			if(jarray.size()==40) {
				for(int i=0; i<40; i=i+2) {
					sumOfExpenses = sumOfExpenses + jarray.get(i).getAsDouble();
				}
			}
		}
		return sumOfExpenses;
	}
	
	public double drobneNaprawy() {
		double drobneNaprawy=0;
		JsonArray jarray = rok2013.getAsJsonArray("pola");
		if(jarray.size()==20) {
			drobneNaprawy = drobneNaprawy + jarray.get(12).getAsDouble();
		}
		if(jarray.size()==40) {
			drobneNaprawy = drobneNaprawy + jarray.get(24).getAsDouble();
		}
		jarray = rok2012.getAsJsonArray("pola");
		if(jarray.size()==20) {
			drobneNaprawy = drobneNaprawy + jarray.get(12).getAsDouble();
		}
		if(jarray.size()==40) {
			drobneNaprawy = drobneNaprawy + jarray.get(24).getAsDouble();
		}
		return drobneNaprawy;
	}
}
