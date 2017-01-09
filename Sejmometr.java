package agh_lab9;

import java.io.IOException;

public class Sejmometr {

	public static void main(String[] args) {
		try {
			Posel posel = new Posel();
			String id = posel.getID(args);
			if(id==null) throw new IllegalArgumentException("Wybrany posel nie odnaleziony!");
			Wydatki wydatki = new Wydatki(id);
			System.out.println("Suma wydatk�w: " + wydatki.sumOfExpenses());
			System.out.println("Suma wydatk�w na drobne naprawy: " + wydatki.drobneNaprawy());
			Poslowie poslowie = new Poslowie();
			System.out.print("�rednia warto�� sumy wydatk�w wszystkich pos��w: ");
			System.out.println(poslowie.poslowieStats(posel.pages, posel.cadency));
			System.out.print("Maksymalna liczba wyjazd�w: " + poslowie.wyjazdyMaxPosel + " - ");
			System.out.println(poslowie.wyjazdyMax);
			System.out.print("Najwiecej dni za granica: " + poslowie.wyjazdyDniMaxPosel + " - ");
			System.out.println(poslowie.wyjazdyDniMax);
			System.out.print("Najdro�sza podr� zagraniczna: " + poslowie.mostExpensivePosel + " - ");
			System.out.println(poslowie.mostExpensive);
			System.out.println("Poslowie, kt�rzy odwiedzili W�ochy:");
			System.out.println(poslowie.italyVisited);
		} catch (IOException e) {
			System.out.println(e);
		}   
	}
}
