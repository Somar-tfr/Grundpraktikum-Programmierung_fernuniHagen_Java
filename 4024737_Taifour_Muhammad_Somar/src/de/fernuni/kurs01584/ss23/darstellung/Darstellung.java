package de.fernuni.kurs01584.ss23.darstellung;

import java.util.ArrayList;

import org.jdom2.Element;

import de.fernuni.kurs01584.ss23.modell.*;
import de.fernuni.kurs01584.ss23.algorithmus.*;



public class Darstellung {
	
	
	
	
	
	
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
		
		printDschungelZeichen(dschungel);
		
		
		
	}
	
	public static void printDschungelZeichen(Dschungel dschungel) {
		ArrayList<ArrayList<Feld>> matrix = dschungel.getMatrix();
		//printDschungelInformation(dschungel)	;
		System.out.println("Dschungel Zeichen"
						 + "\n=======================================");
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				//System.out.print(element.getId());
				
				System.out.print(element.getZeichen() + " | ");
				System.out.print(" ");
				}
				
			
			System.out.println();
			//System.out.println(("-   ").repeat(dschungel.getSpalten())) ;
			

		}
		System.out.println();

	}
	

	
	public static void printSchlangenarten(Schlangenarten schlangenarten) {
		System.out.println("\n=======================================");
		System.out.println("SCHLANGENARTEN :");
		for(int i = 0; i < schlangenarten.getSize(); i++) {
			Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
			System.out.println();
			String nachbS = "";
			nachbS += schlangenart.getNachbarschaftsstruktur().getTyp() + " " +
					  schlangenart.getNachbarschaftsstruktur().getWert1();
			if (schlangenart.getNachbarschaftsstruktur().getTyp() == "Sprung"){
					nachbS += schlangenart.getNachbarschaftsstruktur().getWert2();
			}
			System.out.println("SCHLANGENART:" +
					"\nZeichenkette : " + schlangenart.getZeichenkette() +
					"\nNachbarschaftstruktur : " + nachbS + 
					"\nPunkte : " + schlangenart.getPunkte() +
					"\nAnzahl : " + schlangenart.getAnzahl()
					
					);
		}
		System.out.println("\n=======================================");
	}
	
	private static void printSchlange(Schlange schlange, Schlangenarten schlangenarten, Dschungel dschungel) {
		
		//Schlangenart mit Zeichenkette und Nachbarschaftsstruktur,
		String pr = "";
		Schlangenglied glied = schlange.getKopf();
		if (glied.getFeld() != null) {
			pr += glied.getFeld().getZeichen();
		}
    	while ((glied.getNext() != null)) {
			glied = glied.getNext();
			pr += glied.getFeld().getZeichen();
			
		};
		
		
		
		System.out.println("SCHLANGE : ");
		String art = schlange.getArt();
		Schlangenart schlangenart = schlangenarten.getSchlangeByArt(art);
		
		String nachbS = "";
		nachbS += schlangenart.getNachbarschaftsstruktur().getTyp() + " " +
				  schlangenart.getNachbarschaftsstruktur().getWert1();
		if (schlangenart.getNachbarschaftsstruktur().getTyp() == "Sprung"){
				nachbS += schlangenart.getNachbarschaftsstruktur().getWert2();
		}
		
		System.out.println("Schlangenart : " + schlangenart.getId()
							+" . Zecihenkette : " + schlangenart.getZeichenkette()
							+ ". Nachbarschaftsstruktur : " + nachbS
							);
		
		//Hervorhebung der im Zeichendschungel angeordneten Schlange,
		
		int zeilen = dschungel.getZeilen();
		int spalten = dschungel.getSpalten();
		ArrayList <Schlangenglied> glieder = new ArrayList<Schlangenglied>();
		Schlangenglied gliedPrint = schlange.getKopf();
		glieder.add(gliedPrint);
		while(gliedPrint.getNext() != null) {
			gliedPrint = gliedPrint.getNext();
			glieder.add(gliedPrint);
		}
		
		
		char[][] matrix = new char[zeilen][spalten];
		for(int i = 0; i < zeilen; i++) {
			for(int j = 0; j < spalten; j++) {
				matrix[i][j] = '.';
			}
		}
		glieder.forEach(i -> {
			int a = i.getFeld().getZeile();
			int b = i.getFeld().getSpalte();
			char c = i.getFeld().getZeichen();
			matrix[a][b] = c;
		});
		
		
		for(int i = 0; i < zeilen; i++) {
			for(int j = 0; j < spalten; j++) {
				System.out.print(matrix[i][j] + " | ");
			}
			System.out.println();
		}
		
		System.out.println("SCHLANGENGLIEDER : ");
		//Zeile und Spalte der aufeinanderfolgenden Schlangenglieder.
		glieder.forEach(i ->{
			System.out.print(i.getFeld().getZeichen() + " : " + "(" + i.getFeld().getZeile() + " , " + i.getFeld().getSpalte() + " ) -> ");
		});
		System.out.print("|");
		System.out.println("\n");
		
		
		
	}
	
	public static void printLoesung( ArrayList<Schlange> loesung, Schlangenarten schlangenarten, Dschungel dschungel) {
		//ArrayList<Schlange> loesung = schlangenSuche.getLoesung();
		for(int i = 0; i < loesung.size(); i++) {
			printSchlange(loesung.get(i), schlangenarten, dschungel);
		}
		//loesung.forEach(i -> printSchlange(i, schlangenarten, dschungel));
	}
	
	private static void printDschungelInformation(Dschungel dschungel) {
		System.out.println("Anzahl der Zeilen       : " + dschungel.getZeilen() +
		         "\nAnzahl der Spalten      : " + dschungel.getSpalten() + 
		         "\nDie verwendeten Zeichen : " + dschungel.getZeichenMenge() +
		         "\n[x Zeichen (p: Punkte) (v: Verwendbarket)]"+
		         "\n======================================="
				 );
		//FEHLEN SCHLANGENATEN INFORMATIONEN
	}
	private static void printSchlangenInformation(Schlangenarten schlangenarten) {
		
	}
	
	//hier für die gefundene schlangen
}
