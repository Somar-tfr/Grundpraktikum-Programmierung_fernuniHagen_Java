package de.fernuni.kurs01584.ss23.modell;

public class Feld {
	private int zeile;
	private int spalte;

	// TODO: (weitere) Attribute
	private String zeichen;
	private int verwendbarkeit;
	private int punkte;
	
	public Feld(int zeile, int spalte) {
		super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
	}

	// TODO: (weitere) Konstruktoren
	
	//getter funktionen

	public int getZeile() {
		return zeile;
	}

	public int getSpalte() {
		return spalte;
	}

	// TODO: (weitere) Methoden
	public String getZeichen() {
		return zeichen;
	}
	
	public int getVerwendbarkeit() {
		return verwendbarkeit;
	}
	
	public int getPunkte() {
		return punkte;
	}
	
	

}
