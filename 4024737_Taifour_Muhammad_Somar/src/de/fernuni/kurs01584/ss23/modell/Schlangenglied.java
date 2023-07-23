package de.fernuni.kurs01584.ss23.modell;

/**
 * Die Klasse Schlangenglied repräsentiert ein Glied einer Schlange im Dschungel.
 * Ein Schlangenglied kann ein Feld im Dschungel halten.
 */
public class Schlangenglied{
	//Attribute
	private Schlangenglied next;
	private Feld feld;
	
	
	//Konstruktoren
	/**
     * Konstruktor für ein Schlangenglied ohne ein Feld.
     * Das Schlangenglied hat keinen nächsten Nachfolger (next) .
     */
	public Schlangenglied() {
		this.next = null;
		this.feld = null;
	}
	
	 /**
     * Konstruktor für ein Schlangenglied mit einem bestimmten Feld im Dschungel.
     * Das Schlangenglied hat keinen nächsten Nachfolger (next) des übergebenen Feldes.
     *
     * @param feld Das Feld, das das Schlangenglied im Dschungel einnimmt.
     */
	public Schlangenglied(Feld feld){
		this.next = null;
		this.feld = feld;
	}
	
	// Methoden
	//setters
	

	/**
     * Setzt das nächste Schlangenglied in der Schlange.
     *
     * @param theNext Das nächste Schlangenglied, das in der Schlange folgt.
     */
	public void setNext(Schlangenglied theNext) {
		this.next = theNext;
	}
	
	/**
     * Setzt das Feld, das das Schlangenglied im Dschungel einnimmt.
     *
     * @param feld Das Feld, das das Schlangenglied im Dschungel einnimmt.
     */
	public void setFeld(Feld feld) {
		this.feld = feld;
	}
	
	//getters
	
	
	
	/**
     * Gibt das nächste Schlangenglied in der Schlange zurück.
     *
     * @return Das nächste Schlangenglied in der Schlange.
     */
	public Schlangenglied getNext() {
		return next;
	}
	
	/**
     * Gibt das Feld zurück, das das Schlangenglied im Dschungel einnimmt.
     *
     * @return Das Feld, das das Schlangenglied im Dschungel einnimmt.
     */
	public Feld getFeld() {
		return feld;
	}
	
	

}
