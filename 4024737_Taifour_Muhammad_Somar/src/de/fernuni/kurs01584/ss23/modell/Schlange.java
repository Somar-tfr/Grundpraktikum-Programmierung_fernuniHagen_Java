package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Schlange {
	// TODO: Attribute
	//private Schlangenglied anfang;
	private ArrayList<Schlangenglied> glieder;
	private int punkte;
	private int zeit;
	
	public Schlange() {
		this.glieder = new ArrayList<Schlangenglied>();
		this.punkte = 0;
		this.zeit = 0;
	}
	public void print() {
		System.out.print("P -> ");
		for(Schlangenglied glied : glieder) {
			System.out.print("id: " + glied.getFeld().getId() + " : " + glied.getFeld().getZeichen() + " ->");
		}
		System.out.print(" null !");
		System.out.println();
	}
	
	//MUSS NOCH ANPASSENم
	// TODO: Konstruktoren

	
	public ArrayList<Schlangenglied> getSchlange(){
		return this.glieder;
	}
	
	public int getPunkte() {
		return this.punkte;
	}
	
	public int getZeit() {
		return this.zeit;
	}
	
	public int getSize() {
		
		return glieder.size();
	}
	
	public Schlangenglied getGlied(int index) {
		return this.glieder.get(index);
	}
	
	public void addGlied(Schlangenglied glied) {
		glieder.add(glied);
		this.punkte += glied.getPunkte();
	}
	
	public void removeGlied(Schlangenglied glied) {
		glieder.remove(glied);
		this.punkte -= glied.getPunkte();
	}
	
	
	
	public void clearSchlange() {
		this.glieder.clear();
		this.punkte = 0;
		this.zeit = 0;
	}
	public void setPunkte(int punkte) {
		// TODO Auto-generated method stub
		this.punkte = punkte;
		
	}
	
	// TODO: Methoden
}
