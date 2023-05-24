package de.fernuni.kurs01584.ss23.modell;

public class Dschungel {
	private int zeilen;
	private int spalten;
	private String zeichenMenge;
	
	// TODO: Konstruktoren
	public Dschungel(int zeilen, int spalten,String zeichenMenge) {
		if (zeilen <= 0 || spalten <= 0 || zeichenMenge.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' , 'spalten' , keine negativen Werte annehmen.\n 'zeichenMenge' darf nicht leer sein");
		}
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.zeichenMenge = zeichenMenge;
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
}
