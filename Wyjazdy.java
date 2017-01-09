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

public class Wyjazdy {
	
	String nameAndSurname;
	int wyjazdyDni=0;
	double maxKoszt=0;
	boolean Wlochy=false;
	
	public int wyjazdyStats(String id) throws IOException {
		String sURL = "https://api-v3.mojepanstwo.pl/dane/poslowie/" + id + ".json?layers[]=wyjazdy";
		URL url = new URL(sURL);
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jparser = new JsonParser();
		JsonElement jelement = jparser.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject jobj = jelement.getAsJsonObject();
		JsonObject jobjData = jobj.getAsJsonObject("data");
		this.nameAndSurname = jobjData.get("ludzie.nazwa").getAsString();
		int liczbaWyjazdow = jobjData.get("poslowie.liczba_wyjazdow").getAsInt();
		if(liczbaWyjazdow!=0) {
			jobj = jobj.getAsJsonObject("layers");
			try {
			JsonArray jarray = jobj.getAsJsonArray("wyjazdy");
			for(int i=0; i<jarray.size(); i++) {
				jobj = jarray.get(i).getAsJsonObject();
				wyjazdyDni = wyjazdyDni + jobj.get("liczba_dni").getAsInt();
				double actualKoszt = jobj.get("koszt_suma").getAsDouble();
				if(actualKoszt > maxKoszt) {
					maxKoszt = actualKoszt;
				}
				if(jobj.get("kraj").getAsString().equals("W\u0142ochy")) {
					Wlochy = true;
				}
			}
			} catch (ClassCastException e) {
				return 0;
			}
		}
		return liczbaWyjazdow;
	}
}
