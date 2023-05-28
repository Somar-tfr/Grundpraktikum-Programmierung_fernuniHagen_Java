package de.fernuni.kurs01584.ss23.modell;

public class Schlangenglied{
	// TODO: Attribute
	private int index;
	private Schlangenglied next;
	private Feld feld;
	
	// TODO: Konstruktoren
	Schlangenglied(int index, Feld feld){
		if (index < 0 ) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenglied' duerfen die Attribute 'index' keine negativen Werte annehmen.");
		}
		this.index = index;
		this.next = null;
		this.feld = null;
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

}
