package de.fernuni.kurs01584.ss23.modell;

public class Schlangenart {
	// TODO: Attribute
	private String zeichenkette;
	private int punkte;
	private int anzahl;
	
	
	// TODO: Konstruktoren
	Schlangenart(String zeichenkette){
		if (zeichenkette.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' duerfen die Attribute 'zeichenkette' nicht leer sein");
		}
		this.zeichenkette = zeichenkette;
		this.punkte = 0;
		this.anzahl = 0;
		
	}
	
	// TODO: Methoden
	//getters
	public String getZeichenkette() {
		return zeichenkette;
	}
	public int getPunkte() {
		return punkte;
	}
	public int getAnzahl() {
		return anzahl;
	}
	
	//setters
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	
}
