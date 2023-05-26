package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Dschungel {
	private int zeilen;
	private int spalten;
	private String zeichenMenge;
	private ArrayList<ArrayList<Feld>> dschungelMatrix;
	
	// TODO: Konstruktoren
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
	
	// TODO: Methoden
	public Feld getFeld(int zNummer, int sNummer) {
		return dschungelMatrix.get(zNummer).get(sNummer);
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
		System.out.println(test.getFeld(2,1));
		
	}
}
