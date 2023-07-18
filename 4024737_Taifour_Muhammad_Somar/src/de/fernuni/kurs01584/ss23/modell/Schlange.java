package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Schlange {
	// TODO: Attribute
	//private Schlangenglied anfang;
	private Schlangenglied kopf;
	private int punkte;
	private int length;
	private String art;
	
	//erstelle Kaa Schlange
	public Schlange() {
		
		this.kopf = new Schlangenglied();
		this.punkte = 0;
		this.length = 0;
		this.art = "";
	}
	
	// TODO: Konstruktoren

	
	public Schlangenglied getKopf(){
		return this.kopf;
	}
	
	public int getPunkte() {
		return this.punkte;
	}
	
	
	public int getLength() {
		
		return this.length;
	}
	
	public String getArt() {
		return this.art;
	}
	
	public void setArt(String art) {
		this.art = art;
	}
	
	public void setKopf(Schlangenglied kopf) {
		this.kopf = kopf;
	}
	
	public void resetKopf() {
		this.kopf = null;
	}
	
	public void setPunkte(int punkte) {
		this.punkte = punkte;
		
	}
	
	public void print() {
		Schlangenglied glied = new Schlangenglied();
		glied = this.kopf;
		if (glied.getFeld() != null) {
			System.out.print(glied.getFeld().getZeichen() + " : ");
			System.out.print(glied.getFeld().getId() );
		}
		
		while ((glied.getNext() != null)) {
			glied = glied.getNext();
			System.out.print( "->" );
			System.out.print(glied.getFeld().getZeichen() + " : ");
			System.out.print(glied.getFeld().getId() );
			
		};
		System.out.print( "." );
		System.out.println();
	}
	
	// TODO: Methoden
}
