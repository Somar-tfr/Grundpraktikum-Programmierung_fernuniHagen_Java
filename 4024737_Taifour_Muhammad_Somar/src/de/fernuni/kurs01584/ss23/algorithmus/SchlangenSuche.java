package de.fernuni.kurs01584.ss23.algorithmus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import de.fernuni.kurs01584.ss23.modell.*;


public class SchlangenSuche {
	//alle Felder iterieren? 
	//oder eine andere algorithmus benutzen die parallel mehrere felder sucht??
	
	public static ArrayList<Schlange> sucheSchlangen(Dschungel dschungel,
													Schlangenarten schlangenarten,
													int zeitAngabe,
													char angabeZeichen){
		/* nimmt eine Dschungel, schlangenarten, zeitangeben, angabeZeichen als eingabe
		 * und gibt die in dem Dschungel gefundene Schlangen von den schlangenarten
		 *  innerhalb der vorgegebenen Zeit 
		 * Zurück, mit dem angabeZeichen option.
		 * 
		 */
		ArrayList<Schlange> ergebnisSchlangen = null;
		ArrayList<ArrayList<Feld>>matrix = dschungel.getMatrix();

		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				// zeichen mit dem ersten buchstabe in dem jeweiligen schlange vergleichen
				ArrayList<Schlange> testSchlangen = null;
				if (schlangenZulaessigBT( element,  schlangenarten,  testSchlangen)) {
					ergebnisSchlangen.add(erfuelltKreterien(testSchlangen));
				}
				
				
				
				
				
				}
		}
		
		return ergebnisSchlangen;
		

	}
	
	
	private static Schlange erfuelltKreterien(ArrayList<Schlange> testSchlangen) {
		// TODO Auto-generated method stub
		return null;
	}


	private static boolean schlangenZulaessigBT(Feld inElement, Schlangenarten inSchlangenarten, ArrayList<Schlange> ioTestSchlangen){
		/*nimmt einen Feld element, schlangenarten, arraylist schlangen und nachbarschaftsstruktur
		 * und sucht in die Nachbarschaftsstruktur vom feld die Schlangenarten in dem gegebenen zeit
		 * rekursiv
		 * addiert die gefundine schlangen die die kreterium erfüllen
		 * und geben true aus falls schlangen gefunden sind und false falls keine
		 * VIELLEICHT MUSS ICH DIESE ALGORITHMUS IN ZWEI TEILE ZERLEGEN
		 * EINS FOR SUCHE FÜR EINE SCHLANGE
		 * UND DIE ANDERE FÜR DIE ALLE GEFUNDENE SCHLANGEN?  ES MUSS NUR EINE 
		 * SCHLANGE AM ENDE ZURÜCK GEGEBEN UND NICHT EINE LISTE
		 * DIE MIT DEM BESTEN ZEIT UND PUNKTE UND WEITERE KRETERIEN
		 */
		//code kommt hier!
		
		
		return false;
	}
	
	public static void reset() {
		
	}
	
		
	
	
	
	
	//wenn es gleich mit eine oder mehrere schlangen führe den funktion / threads?
	
	
	//rekursive funktion die die buchstaben mit schlangen vergleicht, hachtabelle? 
	
	
	//wenn eine schlange gefunden würde schreibe mit punkte und zeit
	
	
	//gebe die mit maximale punkte innerhalb das zeit zurück
	
	
	//die gefundene schlange in einer liste antragen und zurückgeben
	
	
	//eine reset funktion?
	
	/*
	METHODE sucheSchlange() {
		 WENN (aktuelle Punkte > bisher maximale Punkte) {
			 speichere Lösung
		 }
		 WENN (Zeitvorgabe erreicht) {
			 beende Suche und gebe Lösung zurück
		 }
		 erzeuge zulässige Startfelder
		 priorisiere und sortiere zulässige Startfelder
		 FÜR (Startfeld in zulässige Startfelder) {
			 bestimme zulässige Schlangenarten für Startfeld
			 priorisiere und sortiere zulässige Schlangenarten
			 FÜR (Schlangenart in Schlangenarten)
			 	erzeuge neue Schlange mit Schlangenkopf
			 			für Schlangenart
			 	setze Schlangenkopf auf Startfeld
			 	sucheSchlangenglied(Schlangenkopf)
			 	entferne Schlangenkopf und Schlange
		 	}
		 }
		
		 }
		
		 METHODE sucheSchlangenglied(vorherigesGlied) {
			 WENN (vorherigesGlied ist letztes Schlangenglied) {
				 	sucheSchlange()
				 	RUECKGABE
		 }
		 erzeuge zulässige Nachbarfelder für vorherigesGlied
		 priorisiere und sortiere zulässige Nachbarfelder
		 FÜR (Nachbarfeld in zulässige Nachbarfelder) {
			 erzeuge neues Schlangenglied
			 setze Schlangenglied auf Nachbarfeld
			 sucheSchlangenglied(Schlangenglied)
			 entferne Schlangenglied
		 }
		 }*/

	
	
}
