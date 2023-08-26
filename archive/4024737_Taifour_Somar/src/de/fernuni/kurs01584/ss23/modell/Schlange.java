package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

/**
 * Die Klasse Schlange repräsentiert eine Schlange im Dschungel.
 * Eine Schlange besteht aus Schlangengliedern, wobei der Kopf das erste Schlangenglied ist.
 * Die Schlange hat eine bestimmte Punktzahl, eine Länge und eine Art.
 * 
 * @author Somar
 * 
 */

public class Schlange {
	//Attribute

	private Schlangenglied kopf;
	private int punkte;
	private int length;
	private String art;
	
	/**
     * Konstruktor für eine neue Kaa-Schlange.
     * Eine Kaa-Schlange hat einen Kopf, aber noch keine Punkte, Länge oder Art.
     */
	public Schlange() {
		
		this.kopf = new Schlangenglied();
		this.punkte = 0;
		this.length = 0;
		this.art = "";
	}
	
	//Konstruktoren

	/**
     * Gibt den Kopf der Schlange zurück.
     *
     * @return Das erste Schlangenglied (Kopf) der Schlange.
     */
	public Schlangenglied getKopf(){
		return this.kopf;
	}
	
	/**
     * Gibt die Punktzahl der Schlange zurück.
     *
     * @return Die Punktzahl der Schlange.
     */
	public int getPunkte() {
		return this.punkte;
	}
	
	/**
     * Gibt die Länge der Schlange zurück (Anzahl der Schlangenglieder).
     *
     * @return Die Länge der Schlange.
     */
	public int getLength() {
		
		return this.length;
	}
	
	/**
     * Gibt die Art der Schlange zurück.
     *
     * @return Die Art der Schlange.
     */
	public String getArt() {
		return this.art;
	}
	
	/**
     * Setzt die Art der Schlange.
     *
     * @param art Die Art, die für die Schlange gesetzt werden soll.
     */
	public void setArt(String art) {
		this.art = art;
	}
	
	/**
     * Setzt den Kopf der Schlange auf ein bestimmtes Schlangenglied.
     *
     * @param kopf Das Schlangenglied, das als Kopf der Schlange gesetzt werden soll.
     */
	public void setKopf(Schlangenglied kopf) {
		this.kopf = kopf;
	}
	
	/**
     * Setzt die Punktzahl der Schlange.
     *
     * @param punkte Die Punktzahl, die für die Schlange gesetzt werden soll.
     */
	public void setPunkte(int punkte) {
		this.punkte = punkte;
		
	}
	

}
