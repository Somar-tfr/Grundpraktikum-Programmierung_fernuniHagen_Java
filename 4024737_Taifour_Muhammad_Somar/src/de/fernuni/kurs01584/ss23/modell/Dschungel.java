package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

import de.fernuni.kurs01584.ss23.algorithmus.DschungelGenerator;

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
	/*
	public Feld getFeldById(String id) {
		return dschungelMatrix.get
	}*/
	
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
				//System.out.print(element.getId());
				System.out.print(element.getZeichen());
				}
			
			System.out.println();
		}
	}
	
	//a test method
		public void printid(/*String zeichen*/) {
			for(ArrayList<Feld> zeile : dschungelMatrix) {
				for (Feld element : zeile) {
					//System.out.print(element.getId());
					System.out.print(element.getId());
					}
				
				System.out.println();
			}
		}
		
	//a test main
	public static void main(String[] args) {
		
		
		Schlangenarten schlangenarten = new Schlangenarten();
		Nachbarschaftsstruktur nachb = new Nachbarschaftsstruktur("Distanz",1);
		Schlangenart schlangenart0 = new Schlangenart("DIE", nachb );
		Schlangenart schlangenart1 = new Schlangenart("DIES", nachb );
		Schlangenart schlangenart2 = new Schlangenart("DIESE", nachb );
		Schlangenart schlangenart3 = new Schlangenart("DIESES", nachb );
		Schlangenart schlangenart4 = new Schlangenart("DIESEN", nachb );
		Schlangenart schlangenart5 = new Schlangenart("DIESER", nachb );
		
		schlangenarten.add(schlangenart0);
		schlangenarten.add(schlangenart1);
		schlangenarten.add(schlangenart2);
		schlangenarten.add(schlangenart3);
		schlangenarten.add(schlangenart4);
		schlangenarten.add(schlangenart5);
		
		schlangenarten.print();
		
		DschungelGenerator  testgenerator = new DschungelGenerator(5,10,"ABCDEFGHIJKLMNOPQRSTUVWXYZ",schlangenarten, 1);
		Dschungel test = testgenerator.erzeugeDschungel();
		
		test.print();
		test.printid();
		//test.getFeld(2,1).setZeichen('A');
		//System.out.println(test.getFeld(2,1).getZeichen());
		System.out.println("---");
		
		//Nachbarschaftsstruktur nach = new Nachbarschaftsstruktur("Distanz", 1);
		//Nachbarschaftsstruktur spr = new Nachbarschaftsstruktur("Sprung", 2, 3);
		
		//(nach.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
		System.out.println("---");
		
		//(spr.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
	}
}
