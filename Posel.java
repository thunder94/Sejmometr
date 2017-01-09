package agh_lab9;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Posel {
	
	int pages;
	String cadency;
	
	public String getID(String[] args) throws JsonIOException, JsonSyntaxException, IOException {
		if(args.length!=3) {
			throw new IllegalArgumentException("Nieprawidlowa liczba argumentow!");
		}
		String sURL = "https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=";
		cadency = args[0];
		if(cadency.equals("7")) {
			this.pages = 11;
		} else if(cadency.equals("8")) {
			this.pages = 10;
		}
		else throw new IllegalArgumentException("Nieprawid³owy numer kadencji!");
		sURL = sURL + args[0] + "&page=";
		String newURL;
		for(int i=1; i<=pages; i++) {
			newURL = sURL + Integer.toString(i);
			URL url = new URL(newURL);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.connect();
			JsonParser jparser = new JsonParser();
			JsonElement jelement = jparser.parse(new InputStreamReader((InputStream) request.getContent()));
			JsonObject jobj = jelement.getAsJsonObject();
			JsonArray jarray = jobj.getAsJsonArray("Dataobject");
			String nameAndSurname = args[1] + " " + args[2];
			JsonObject jobjData;
			for(int j=0; j<jarray.size(); j++) {
				jobj = jarray.get(j).getAsJsonObject();
				jobjData = jobj.getAsJsonObject("data");
				if(jobjData.get("ludzie.nazwa").getAsString().equals(nameAndSurname)) {
					return jobj.get("id").getAsString();
				}
			}
		}
		return null;
	}
}
