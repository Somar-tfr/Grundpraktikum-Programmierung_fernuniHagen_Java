package de.fernuni.kurs01584.ss23.modell;

/**
 * Die Klasse Schlangenart repräsentiert eine bestimmte Art von Schlangen im Dschungel.
 * Jede Schlangenart hat eine eindeutige ID, eine Zeichenkette, eine Punktzahl, eine Anzahl und eine Nachbarschaftsstruktur.
 * 
 * @author Somar 
 */
public class Schlangenart {
	//Attribute
	private String id;
	private String zeichenkette;
	private int punkte;
	private int anzahl;
	private Nachbarschaftsstruktur nachbarschaftsstruktur;
	
	//Konstruktoren
	
	/**
	 * Konstruktor für die Klasse Schlangenart.
	 * Erzeugt eine neue Schlangenart mit der gegebenen Zeichenkette und Nachbarschaftsstruktur.
	 *
	 * @param zeichenkette Die Zeichenkette, die die Schlangenart repräsentiert.
	 * @param nachbarschaftsstruktur Die Nachbarschaftsstruktur für diese Schlangenart.
	 * @throws IllegalArgumentException Wenn die Zeichenkette leer ist.
	 */
	public Schlangenart(String zeichenkette, Nachbarschaftsstruktur nachbarschaftsstruktur){
		if (zeichenkette.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' duerfen die Attribute 'zeichenkette' nicht leer sein");
		}
		this.zeichenkette = zeichenkette;
		this.nachbarschaftsstruktur = nachbarschaftsstruktur;
		this.punkte = 0;
		this.anzahl = 0;
		
	}
	
	//Methoden
	
	//getters
	
	/**
	 * Gibt die Zeichenkette der Schlangenart zurück.
	 *
	 * @return Die Zeichenkette der Schlangenart.
	 */
	public String getZeichenkette() {
		return zeichenkette;
	}
	
	/**
	 * Gibt die Punktzahl der Schlangenart zurück.
	 *
	 * @return Die Punktzahl der Schlangenart.
	 */
	public int getPunkte() {
		return punkte;
	}
	
	/**
	 * Gibt die Anzahl der Schlangen dieser Art im Dschungel zurück.
	 *
	 * @return Die Anzahl der Schlangen dieser Art.
	 */
	public int getAnzahl() {
		return anzahl;
	}
	
	/**
	 * Gibt die ID der Schlangenart zurück.
	 *
	 * @return Die ID der Schlangenart.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gibt die Nachbarschaftsstruktur der Schlangenart zurück.
	 *
	 * @return Die Nachbarschaftsstruktur der Schlangenart.
	 */
	public Nachbarschaftsstruktur getNachbarschaftsstruktur() {
		return nachbarschaftsstruktur;
	}
	
	/**
	 * Gibt die Größe (Länge) der Zeichenkette der Schlangenart zurück.
	 *
	 * @return Die Größe der Zeichenkette der Schlangenart.
	 */
	public int getSize() {
		return zeichenkette.length();
	}
	
	//setters
	
	/**
	 * Setzt die Punktzahl für die Schlangenart.
	 *
	 * @param punkte Die Punktzahl, die für die Schlangenart gesetzt werden soll.
	 * @throws IllegalArgumentException Wenn die übergebene punkte negativ ist.
	 */
	public void setPunkte(int punkte) {
		if (punkte < 0) {
			throw new IllegalArgumentException("anzahl muss positiver int sein!");
		}
		this.punkte = punkte;
	}
	
	/**
	 * Setzt die Anzahl der Schlangen dieser Art im Dschungel.
	 *
	 * @param anzahl Die Anzahl der Schlangen, die für die Schlangenart gesetzt werden soll.
	 * @throws IllegalArgumentException Wenn die übergebene anzahl negativ ist.
	 */
	public void setAnzahl(int anzahl) {
		if (anzahl < 0) {
			throw new IllegalArgumentException("anzahl muss positiver int sein!");
		}
		this.anzahl = anzahl;
	}
	
	/**
	 * Setzt die ID für die Schlangenart.
	 *
	 * @param id Die ID, die für die Schlangenart gesetzt werden soll.
	 */
	public void setID(String i) {
		id = i;
	}
	
	
	
	
	
}
