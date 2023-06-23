package de.fernuni.kurs01584.ss23.algorithmus;

import java.util.ArrayList;
import java.util.Random;
import de.fernuni.kurs01584.ss23.modell.*;


//import de.fernuni.kurs01584.ss23.modell.*;

public class DschungelGenerator {
	/*Die Beschränkung auf einfache
	*Probleminstanzen bedeutet, dass bei der geforderten Funktionalität zur Erzeugung von
	*Schlangen Überschneidungen ausgeschlossen werden und jedes Feld maximal einmal
	*verwendet werden darf. Für die Schlangensuche gilt diese Einschränkung nicht.
	*/
	private int zeilen;
	private int spalten;
	private String zulaessigeZeichen;
	private Schlangenarten schlangenarten;
	private int schlangenAnzahl;
	
	public DschungelGenerator(int zeilen, 
			int spalten, 
			String zulaessigeZeichen, 
			Schlangenarten schlangenarten,
			int schlangenanzahl){
		if (zeilen < 1 || spalten < 1 || zulaessigeZeichen == null || schlangenarten == null ||  schlangenanzahl < 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute "
					+ "'zeilen' und 'spalten' 'schlangenanzahl' "
					+ "nur positive Werte annehmen, 'zulaessigeZeichen' "
					+ "und 'schlangenarten' dürfen nicht leer sein .");
		}
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.zulaessigeZeichen = zulaessigeZeichen;
		this.schlangenarten = schlangenarten;
		this.schlangenAnzahl = schlangenanzahl;
		
	}
	public Dschungel erzeugeDschungel() {
		Dschungel dschungel = new Dschungel(zeilen, spalten, zulaessigeZeichen);
		
		
		//Zufällige schlangen verteilung
		
		int schlangenartenAnzahl = schlangenarten.getAnzahl();
		int randMax = zulaessigeZeichen.length() - 1; // eine random nummer für 
		
				
		// schlangenarten iterieren
		for(int i = 0; i < schlangenartenAnzahl; i++) {
			//muss sicher stellen dass die zeichenketten in schlange zulaessig sind
			
			/*if(schlangeZeichenkettenZulaessig) {
				
			}*/
			
			//schlangenart wählen und benötigte informationen speichern
			Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
			int gliederAnzahl = schlangenart.getZeichenLength();
			//int schlangenlangenMaxIndex = schlangenart.getZeichenLength() - 1;
			ArrayList<Feld> felderlist = new ArrayList<Feld>();//felderliste für  felder die recursiv belegt sind und die schlange zulässig ist
			System.out.println(schlangenart.getZeichenkette());
			ArrayList<ArrayList<Feld>> pruefMatrix = new ArrayList<>();//eine prüfmatrix um sicher zu stellen ob alle felder vom random besucht sind
			//pruefMatrix initieeren
			for(int k = 0 ; k < zeilen; k++) {
				ArrayList<Feld> zeile = new ArrayList<>();
				
				for (int l = 0; l < spalten; l++) {
					
					zeile.add(null);
				}//for l
				pruefMatrix.add(zeile);
			}// for k
			//MUSS NOCH DIE PUNKTE UND VERWENDBARKEIT ADDIEREN!!
			//ein random feld wählen
			Feld feld = null;
			Feld nextFeld;
			int schlangenlangenMaxIndex = gliederAnzahl - 1;//23.06 10:00
			char erstesChar = schlangenart.getZeichenkette().charAt(schlangenlangenMaxIndex);//23.06 10:00
			//looop für ein random position in dem dschungel für die schlange
			while(true) {
				
				Random randomPosition = new Random();
				int randPosZ = randomPosition.nextInt(zeilen - 1);
				int randPosS = randomPosition.nextInt(spalten - 1);
				
				nextFeld = dschungel.getFeld(randPosZ, randPosS);
				
				//die gewälte feld in paralell matrix markieren
				/*if(pruefMatrix.get(randPosZ).get(randPosS) == null){
					pruefMatrix.get(randPosZ).add(randPosS, nextFeld);
				}*/
				
				//sicher stellen dass den random gewählten feld leer ist und rekursiv dass die bis letzte char geht
				if ((istZulaessigBT(dschungel, schlangenart, gliederAnzahl-1,feld, nextFeld, felderlist))&& (nextFeld.getZeichen() == ' ') ){
					//istZulaessigBT(dschungel, schlangenart, gliederAnzahl-1,feld, nextFeld, felderlist);
					nextFeld.setZeichen(erstesChar);  //23.06 10:00
					felderlist.add(nextFeld);
					System.out.println(nextFeld.getId());
					System.out.println(gliederAnzahl);
					System.out.println(nextFeld.getZeichen());
					
					//felderlist nach jede schlange entleeren;
					for(int j = schlangenlangenMaxIndex; j >= 0; j--) {
						//System.out.println(schlangenlangenMaxIndex);
						//System.out.println(j);
						
						felderlist.remove(j);
					}
					break;
				}else {
					//um die felderliste wieder leer zu machen und die zeichen zurückzusetzen DAS KANN IN EIMEN SEPERATEN FUNKTION
					for(int j = felderlist.size() - 1; j >= 0; j--) { // hier angepasst 23.06.12:07
						//System.out.println(schlangenlangenMaxIndex);
						//System.out.println(j);
						felderlist.get(j).setZeichen(' ') ;
						felderlist.remove(j);
					}
				}
				//DAS PROBLEM IST DAS DIE FELDER OHNE ZEICHEN BLEIBEN BIS ERST NACH DEM LAUF!! ALSO DIE ZWEITE KONDITION KOMMT IMMER ALS WAHR!!!
				
				//prüfen ob alle felder besucht sind
				/*if(alleMatrixFelderBesucht(pruefMatrix)) {
					break;
					//Muss ich hier eine error geben falls nicht all die schlangenarten positionert sind?
				}*/
				
			}
			
		/*	
			//der inhalt vom gefundenen felderliste mit schlange belegen
			for(int j = 0; j < gliederAnzahl; j++) {
				felderlist.get(j).setZeichen(schlangenart.getZeichenkette().charAt(j)) ;
			}*/
				
			
		}
		
		//Belegung verbliebenden Felder mit zufällige zeichen

		for (int i = 0; i < dschungel.getZeilen(); i++) {
			for (int j = 0; j < dschungel.getSpalten(); j++) {
				Feld feld = dschungel.getFeld(i, j);
				//prüfe ob das feld leer ist
				if ( feld.getZeichen() == ' ') {
					Random randomZeichen = new Random();
					int randZeichen = randomZeichen.nextInt(randMax);
					char zeichen = zulaessigeZeichen.charAt(randZeichen);
					feld.setZeichen(zeichen);
					
				}//if feld nicht belegt
			}//spalten iteration
		}//zeilen iteration
	
		return dschungel;
	}
	
	//zu optemieren
	private boolean istZulaessigBT(Dschungel dschungel,Schlangenart schlangenart,int schlangenZeichenIterator, Feld thisFeld, Feld nextFeld, ArrayList<Feld> felderlist) {
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, nextFeld);
		nachbarn.remove(thisFeld);
		 
		 char appendChar = schlangenart.getZeichenkette().charAt(schlangenZeichenIterator - 1);//23.06 10:00
		
		//die n0 position mit dem letzten element
		if (schlangenZeichenIterator == 1){
			//wiederhole für alle nachbarschaftsstruktur
			
			while (true) {
				if (nachbarn.size() == 0) {
					//falls keine weitere generierung möglich
					return false;
				}
				//finde eine random nachbarstrk die nicht belegt ist
				Random randomNacharschaftsFeld = new Random();
				int randFeld = randomNacharschaftsFeld.nextInt(nachbarn.size());
				thisFeld = nextFeld;
				nextFeld = nachbarn.get(randFeld);
				if (nextFeld.getZeichen() == ' '){
					nextFeld.setZeichen(appendChar);  //23.06 10:00
					felderlist.add(nextFeld);	
					System.out.println(nextFeld.getId());
					System.out.println(nextFeld.getZeichen());
					System.out.println(schlangenZeichenIterator);//test
					
					return true;
						
				}
				//um den feld nicht nochmal zu besuchen
				nachbarn.remove(randFeld);
				
			}
		}
		
		//die rekursion 
		while(true) {
			if (nachbarn.size() == 0) {
				return false;
			}
			//finde eine random nachbarstrk die nicht belegt ist
			Random randomNacharschaftsFeld = new Random();
			int randFeld = randomNacharschaftsFeld.nextInt(nachbarn.size());
			thisFeld = nextFeld;
			nachbarn.remove(thisFeld);
			nextFeld = nachbarn.get(randFeld);
			
			//rekursive funktion
			if (istZulaessigBT(dschungel, schlangenart, schlangenZeichenIterator-1,thisFeld, nextFeld, felderlist) && (nextFeld.getZeichen() == ' ')) {
				//System.out.println(nextFeld.getZeichen());
				
				nextFeld.setZeichen(appendChar);//23.06 10:00
				felderlist.add(nextFeld);
				System.out.println(nextFeld.getId());
				System.out.println(nextFeld.getZeichen());
				System.out.println(schlangenZeichenIterator);//test
				return true;
				
			}else {
			for(int j = felderlist.size() - 1; j >= 0; j--) { //?????????
			felderlist.get(j).setZeichen(' ') ;
			felderlist.remove(j);
				};
			}
			nachbarn.remove(nextFeld);
		}
		
	}
	/*
	private boolean alleMatrixFelderBesucht(ArrayList<ArrayList<Feld>> pruefMatrix) {
		for(int i = 0 ; i < zeilen; i++) {
			
			for (int j = 0; j< spalten; j++) {
				if (pruefMatrix.get(i).get(j) != null) {
					return false;
				}
			}
			
		}
		return true;
	}*/
	

}
