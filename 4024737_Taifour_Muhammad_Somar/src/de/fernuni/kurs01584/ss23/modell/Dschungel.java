package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Dschungel {
	private int zeilen;
	private int spalten;
	private String zeichenMenge;
	private ArrayList<ArrayList<Feld>> dschungelMatrix;
	
	//Konstruktoren
	public Dschungel(int zeilen, int spalten,String zeichenMenge) {
		if (zeilen <= 0 || spalten <= 0 || zeichenMenge.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' , 'spalten' , keine negativen Werte annehmen.\n 'zeichenMenge' darf nicht leer sein");
		}
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.zeichenMenge = zeichenMenge;
		
		this.dschungelMatrix = new ArrayList<>();
		
		for(int i = 0 ; i < zeilen; i++) {
			ArrayList<Feld> zeile = new ArrayList<>();
			
			for (int j = 0; j< spalten; j++) {
				Feld feld = new Feld(i,j);
				feld.setId(spalten);
				zeile.add(feld);
			}
			dschungelMatrix.add(zeile);
		}
		
	}
	
	//getters

	public int getZeilen() {
		return zeilen;
	}

	public int getSpalten() {
		return spalten;
	}
	
	public String getZeichenMenge() {
		return zeichenMenge;
	}
	
	public Feld getFeld(int zNummer, int sNummer) {
		return dschungelMatrix.get(zNummer).get(sNummer);
	}
	
	public int getFelderAnzahl() {
		return (zeilen * spalten);
	}
	
	public boolean feldExistiert(int zeile, int spalte){
		if (zeile >= 0 && zeile < zeilen ) {
			if (spalte >= 0 && spalte < spalten) {
				return true;
			}
		}
		return false;
	}
	
	
	//a test method
	public void print(/*String zeichen*/) {
		for(ArrayList<Feld> zeile : dschungelMatrix) {
			for (Feld element : zeile) {
				System.out.print(element.getId());
				}
			
			System.out.println();
		}
	}
	//a test main
	public static void main(String[] args) {
		Dschungel test = new Dschungel(5,6, "abcd");
		test.print();
		test.getFeld(2,1).setZeichen('A');
		System.out.println(test.getFeld(2,1).getZeichen());
		System.out.println("---");
		
		Nachbarschaftsstruktur nach = new Nachbarschaftsstruktur("Distanz", 1);
		Nachbarschaftsstruktur spr = new Nachbarschaftsstruktur("Sprung", 2, 3);
		
		(nach.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
		System.out.println("---");
		
		(spr.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
	}
}
