package de.fernuni.kurs01584.ss23.darstellung;

import java.util.ArrayList;

import de.fernuni.kurs01584.ss23.modell.*;
import de.fernuni.kurs01584.ss23.algorithmus.*;



public class Darstellung {
	public static void main(String[] args) {
		//test
		Schlangenarten schlangenarten = new Schlangenarten();
		Nachbarschaftsstruktur nachb = new Nachbarschaftsstruktur("Distanz",1);
		Schlangenart schlangenart0 = new Schlangenart("DIE", nachb );
		schlangenarten.add(schlangenart0);
		
		DschungelGenerator generator = new DschungelGenerator(5,5,"abcd", schlangenarten);
		Dschungel neueDschungel = generator.erzeugeDschungel();
		//Darstellung.printDschungelZeichen(neueDschungel);
		//Darstellung.printDschungelFeldId(neueDschungel);
		//Darstellung.printDschungelPunkte(neueDschungel);
		Darstellung.printDarstellung(neueDschungel);
		
	}
	/*Dschungel dschungel = null;
	
	public Darstellung(Dschungel dschungel){
		this.dschungel = dschungel;
	}*/
	public static void printDarstellung(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		printDschungelInformation(dschungel)	;
		
		//finde den laengsten Feld id
		int maxLenId = 0;
		int maxLenPunkte = 0;
		int maxLenVerwendbarkeit = 0;
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				//maxLenId = Math.max(maxLenId, element.getId().length());
				maxLenPunkte = Math.max(maxLenPunkte, String.valueOf(element.getPunkte()).length());
				maxLenVerwendbarkeit = Math.max(maxLenVerwendbarkeit, String.valueOf(element.getVerwendbarkeit()).length());
				}
		}
		
		// format erstellen
		
		//String formatId = "%-" + maxLenId + "s ";
		String formatPunkte = "%-" + maxLenPunkte + "d";
		String formatVerwendbarkeit = "%-" + maxLenVerwendbarkeit + "d";
		
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				System.out.print( element.getZeichen() + " "+
						"(p:" + String.format(formatPunkte, element.getPunkte())+ ") "+
						"(v:" + String.format(formatVerwendbarkeit, element.getVerwendbarkeit()) + ") | "
						);
				}
				
			
			System.out.println();
		}
		
		
		
	}
	public static void printDschungelZeichen(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		printDschungelInformation(dschungel)	;
		System.out.println("Dschungel Zeichen\n"
						 + "=================");
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				
				System.out.print(element.getZeichen());
				System.out.print(" ");
				}
				
			
			System.out.println();
			//System.out.println(("-   ").repeat(dschungel.getSpalten())) ;
			

		}
		System.out.println();

	}
	
	public static void printDschungelFeldId(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		printDschungelInformation(dschungel)	;
		System.out.println("Dschungel Feld Id\n"
						 + "=================");
		//finde den laengsten Feld id
		int maxLen = 0;
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				maxLen = Math.max(maxLen, element.getId().length());
				}
		}
		
		// format erstellen
		
		String formatSpecifier = "%-" + maxLen + "s ";
		
		
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				System.out.print(String.format(formatSpecifier, element.getId()));
				}
				
			
			System.out.println();
		}
	}
	
	public static void printDschungelPunkte(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		printDschungelInformation(dschungel)	;
		System.out.println("Dschungel Punkte\n"
						 + "================");
		int maxLen = 0;
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				maxLen = Math.max(maxLen, String.valueOf(element.getPunkte()).length());
				}
		}
		
		// format erstellen
		
		String formatSpecifier = "%-" + maxLen + "d ";
		
		
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				System.out.print(String.format(formatSpecifier, element.getPunkte()));
				}
				
			
			System.out.println();
		}
		
	}
	
	public static void printDschungelVerwendbarkeit(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		printDschungelInformation(dschungel)	;	
		System.out.println("Dschungel Verwedndbarkeit\n"
						 + "=========================");
		int maxLen = 0;
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				maxLen = Math.max(maxLen, String.valueOf(element.getVerwendbarkeit()).length());
				}
		}
		
		// format erstellen
		
		String formatSpecifier = "%-" + maxLen + "d ";
		
		
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				System.out.print(String.format(formatSpecifier, element.getVerwendbarkeit()));
				}
				
			
			System.out.println();
		}
		
	}
	
	private static void printDschungelInformation(Dschungel dschungel) {
		System.out.println("Anzahl der Zeilen       : " + dschungel.getZeilen() +
		         "\nAnzahl der Spalten      : " + dschungel.getSpalten() + 
		         "\nDie verwendeten Zeichen : " + dschungel.getZeichenMenge() +
				 "\n=======================================");
		//FEHLEN SCHLANGENATEN INFORMATIONEN
	}
	
	//hier für die gefundene schlangen
}
