package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

import de.fernuni.kurs01584.ss23.algorithmus.DschungelGenerator;
import de.fernuni.kurs01584.ss23.algorithmus.SchlangenSuche;

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
	
	//andere weg??
	public ArrayList<ArrayList<Feld>> getMatrix() {
		return dschungelMatrix;
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
		schlangenart0.setAnzahl(1);
		Schlangenart schlangenart1 = new Schlangenart("DIES", nachb );
		schlangenart1.setAnzahl(1);
		Schlangenart schlangenart2 = new Schlangenart("DIESE", nachb );
		schlangenart2.setAnzahl(1);
		Schlangenart schlangenart3 = new Schlangenart("DIESES", nachb );
		schlangenart3.setAnzahl(1);
		Schlangenart schlangenart4 = new Schlangenart("DIESEN", nachb );
		schlangenart4.setAnzahl(1);
		Schlangenart schlangenart5 = new Schlangenart("DIESER", nachb );
		schlangenart5.setAnzahl(1);
		
		//DONAUDAMPFSCHIFFFAHRTSELEKTRIZITÄTENHAUPTBETRIEBSWERKBAUUNTERBEAMTENGESELLSCHAF
		schlangenarten.add(schlangenart0);
		schlangenarten.add(schlangenart1);
		schlangenarten.add(schlangenart2);
		schlangenarten.add(schlangenart3);
		schlangenarten.add(schlangenart4);
		schlangenarten.add(schlangenart5);
		
		
		schlangenarten.print();
		
		DschungelGenerator  testgenerator = new DschungelGenerator(5,10,"ABCDEFGHIJKLMNOPQRSTUVWXYZ",schlangenarten);
		Dschungel test = testgenerator.erzeugeDschungel();
		
		test.print();
		test.printid();
		test.printverwendbarkeit();
		//test.getFeld(2,1).setZeichen('A');
		//System.out.println(test.getFeld(2,1).getZeichen());
		System.out.println("---");
		
		//Nachbarschaftsstruktur nach = new Nachbarschaftsstruktur("Distanz", 1);
		//Nachbarschaftsstruktur spr = new Nachbarschaftsstruktur("Sprung", 2, 3);
		
		//(nach.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
		System.out.println("---");
		SchlangenSuche schlangensuche = new SchlangenSuche(test, schlangenarten, 200, 'a');
		schlangensuche.sucheSchlange2();
		ArrayList<Schlange> schlangen = schlangensuche.getResult();
		
		System.out.println(schlangen.size());
		if (schlangen.size() != 0){
			for (Schlange s : schlangen) {
				s.print();
			}
			//System.out.println(schlangen.get(0).getGlied(0));

		}
		test.printverwendbarkeit();
		
		//(spr.getNachbarschaft(test, test.getFeld(2, 1))).forEach(i -> System.out.println(i.getId()));
	}

	private void printverwendbarkeit() {
		// TODO Auto-generated method stub
		for(ArrayList<Feld> zeile : dschungelMatrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				System.out.print(element.getVerwendbarkeit());
				}
			
			System.out.println();
		}
	}
}
