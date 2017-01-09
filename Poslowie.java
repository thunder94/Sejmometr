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

public class Poslowie {
	
	int wyjazdyMax=0;
	String wyjazdyMaxPosel;
	int wyjazdyDniMax=0;
	String wyjazdyDniMaxPosel;
	double mostExpensive=0;
	String mostExpensivePosel;
	String italyVisited = new String();
	
	public double poslowieStats(int pages, String cadency) throws IOException {
		double sumOfsums=0;
		int poslowieCount=0;
		String sURL = "https://api-v3.mojepanstwo.pl/dane/poslowie.json?conditions[poslowie.kadencja]=";
		sURL = sURL + cadency + "&page=";
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
			for(int j=0; j<jarray.size(); j++) {
				jobj = jarray.get(j).getAsJsonObject();
				String id = jobj.get("id").getAsString();
				Wydatki wydatki = new Wydatki(id);
				double sumAdd = wydatki.sumOfExpenses();
				if(sumAdd!=0) {
					sumOfsums = sumOfsums + sumAdd;
					poslowieCount++;
				}
				Wyjazdy wyjazdy = new Wyjazdy();
				int actualWyjazdy = wyjazdy.wyjazdyStats(id);
				if(actualWyjazdy > this.wyjazdyMax) {
					this.wyjazdyMax = actualWyjazdy;
					this.wyjazdyMaxPosel = wyjazdy.nameAndSurname;
				}
				int actualWyjazdyDni = wyjazdy.wyjazdyDni;
				if(actualWyjazdyDni > this.wyjazdyDniMax) {
					this.wyjazdyDniMax = actualWyjazdyDni;
					this.wyjazdyDniMaxPosel = wyjazdy.nameAndSurname;
				}
				double actualMostExpensive = wyjazdy.maxKoszt;
				if(actualMostExpensive > this.mostExpensive) {
					this.mostExpensive = actualMostExpensive;
					this.mostExpensivePosel = wyjazdy.nameAndSurname;
				}
				if(wyjazdy.Wlochy) {
					italyVisited = italyVisited + " " + wyjazdy.nameAndSurname;
				}
			}
		}
		return sumOfsums/poslowieCount;
	}
}
