package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Schlange {
	// TODO: Attribute
	//private Schlangenglied anfang;
	private ArrayList<Schlangenglied> glieder;
	
	//MUSS NOCH ANPASSENم
	// TODO: Konstruktoren

	
	public ArrayList<Schlangenglied> getSchlange(){
		return this.glieder;
	}
	
	public void gliedAddieren(Schlangenglied glied) {
		this.glieder.add(glied);
	}
	
	public int getLength() {
		return this.glieder.size();
	}
	
	public Schlangenglied getGlied(int index) {
		return this.glieder.get(index);
	}
	
	public void clearSchlange() {
		this.glieder.clear();
	}
	
	// TODO: Methoden
}
