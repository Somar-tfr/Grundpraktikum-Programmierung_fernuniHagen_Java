package de.fernuni.kurs01584.ss23.modell;

public class Schlangenglied{
	// TODO: Attribute
	private int index;
	private Schlangenglied next;
	private Schlangenglied prev;
	private Feld feld;
	private int punkte;
	
	
	// TODO: Konstruktoren
	public Schlangenglied(Feld feld){
		
		
		this.next = null;
		this.feld = feld;
		this.punkte = feld.getPunkte();
	}
	// TODO: Methoden
	//setters
	public void setIndex(int index) {
		this.index = index;
	}
	
	
	//getters
	public int getIndex() {
		return index;
	}
	
	public void setNext(Schlangenglied theNext) {
		this.next = theNext;
	}
	
	public void setFeld(Feld feld) {
		this.feld = feld;
	}
	
	public Schlangenglied getNext() {
		return next;
	}
	public Feld getFeld() {
		return feld;
	}
	
	public int getPunkte() {
		return this.punkte;
	}

}
