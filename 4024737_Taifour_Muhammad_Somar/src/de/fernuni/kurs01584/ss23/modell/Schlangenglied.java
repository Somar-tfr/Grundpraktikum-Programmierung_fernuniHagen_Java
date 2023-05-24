package de.fernuni.kurs01584.ss23.modell;

public class Schlangenglied {
	// TODO: Attribute
	private int index;
	
	// TODO: Konstruktoren
	Schlangenglied(int index){
		if (index < 0 ) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenglied' duerfen die Attribute 'index' keine negativen Werte annehmen.");
		}
		this.index = index;
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

}
