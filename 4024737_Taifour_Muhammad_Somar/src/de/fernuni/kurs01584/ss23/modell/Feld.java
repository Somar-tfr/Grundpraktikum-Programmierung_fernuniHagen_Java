package de.fernuni.kurs01584.ss23.modell;

public class Feld {
	private int zeile;
	private int spalte;

	// TODO: (weitere) Attribute
	private String zeichen;
	private int verwendbarkeit;
	private int punkte;
	private int id; //??
	
	public Feld(int zeile, int spalte) {
		super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = 1;
		this.punkte = 1;
	}
	public Feld(int zeile, int spalte, int verwendbarkeit, int punkte) {
		super();
		if (zeile < 0 || spalte < 0 || verwendbarkeit < 0 || punkte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' , 'spalte' , 'verwendbarket' , 'punlte'  keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = verwendbarkeit;
		this.punkte = punkte;
		
	}

	// TODO: (weitere) Konstruktoren
	//setter funktionen
	public void setId(int spalten) {
		
		id = (zeile * spalten) + spalte;
	}
	
	public void setZeichen(String eingabeZeichen) {
		zeichen = eingabeZeichen;
	}
	
	
	//getter funktionen

	public int getZeile() {
		return zeile;
	}

	public int getSpalte() {
		return spalte;
	}

	// TODO: (weitere) Methoden
	public int getId() {
		return id;
	}
	
	public int getVerwendbarkeit() {
		return verwendbarkeit;
	}
	
	public int getPunkte() {
		return punkte;
	}
	
	public String getZeichen() {
		return zeichen;
	}
	


}
