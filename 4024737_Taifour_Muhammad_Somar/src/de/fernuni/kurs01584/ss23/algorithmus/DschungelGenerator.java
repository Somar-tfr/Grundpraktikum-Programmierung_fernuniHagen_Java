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
			
			ArrayList<Feld> felderlist = new ArrayList<Feld>();//felderliste für  felder die recursiv belegt sind und die schlange zulässig ist
			
			ArrayList<ArrayList<Feld>> pruefMatrix = new ArrayList<>();//eine prüfmatrix um sicher zu stellen ob alle felder vom random besucht sind
			//pruefMatrix initieeren
			for(int k = 0 ; k < zeilen; k++) {
				ArrayList<Feld> zeile = new ArrayList<>();
				
				for (int l = 0; l < spalten; l++) {
					
					zeile.add(null);
				}//for l
				pruefMatrix.add(zeile);
			}// for k
			
			//ein random feld wählen
			Feld feld = null;
			Feld nextFeld;
			
			//looop für ein random position in dem dschungel für die schlange
			while(true) {
				
				Random randomPosition = new Random();
				int randPosZ = randomPosition.nextInt(zeilen - 1);
				int randPosS = randomPosition.nextInt(spalten - 1);
				
				nextFeld = dschungel.getFeld(randPosZ, randPosS);
				
				//die gewälte feld in paralell matrix markieren
				if(pruefMatrix.get(randPosZ).get(randPosS) == null){
					pruefMatrix.get(randPosZ).add(randPosS, nextFeld);
				}
				
				//sicher stellen dass den random gewählten feld leer ist und rekursiv dass die bis letzte char geht
				if ((nextFeld.getZeichen() == ' ') && (istZulaessigBT(dschungel, schlangenart, gliederAnzahl-1,feld, nextFeld, felderlist))){
					//istZulaessigBT(dschungel, schlangenart, gliederAnzahl-1,feld, nextFeld, felderlist);
					felderlist.add(nextFeld);
					break;
				}
				//prüfen ob alle felder besucht sind
				if(alleMatrixFelderBesucht(pruefMatrix)) {
					break;
					//Muss ich hier eine error geben falls nicht all die schlangenarten positionert sind?
				}
				
			}
			
			/*felderlist.forEach(n->System.out.print(n.getId()));
			System.out.println();*///ein test
			//der inhalt vom gefundenen felderliste mit schlange belegen
			for(int j = 0; j < gliederAnzahl; j++) {
				felderlist.get(j).setZeichen(schlangenart.getZeichenkette().charAt(j)) ;
			}
			
			
			/*
			feld.setZeichen(schlangenart.getZeichenkette().charAt(0));
			//should a while be here?? while alle schlangen noch nicht gegeben sind?
			//iterate through zeichenkette
			int j = 1;
			while ( j < gliederAnzahl) {
				//muss man ein rekord habenn ob alle nachbarschaftsstrukturen bereits besucht sind
				ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, feld);
				int nachbarnGroesse = nachbarn.size() - 1 ;
				Feld nextFeld;
				do {
					Random randomNacharschaftsFeld = new Random();
					int randFeld = randomNacharschaftsFeld.nextInt(nachbarnGroesse);
					nextFeld = nachbarn.get(randFeld);
					if (nextFeld.getZeichen() == ' '){
							feldIstLeer = true;
							nachbarn.remove(randFeld);
					}
					//für den fall dass es kein platz mehr für eine neue schlangenart gefunden ist
					if (nachbarn.size() == 0) {
						continue;
					}
				}while(!feldIstLeer);
				if (nachbarn.size() == 0) {
					j = j - 1;
					continue;
				}
				feldIstLeer = false;
				
				nextFeld.setZeichen(schlangenart.getZeichenkette().charAt(j));
				j++;
				 
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
	/*
	private ArrayList<Feld> zulaessigeFelder(Dschungel dschungel,Schlangenart schlangenart, Feld feld){
		ArrayList<Feld> zulaessigeFelder = new ArrayList<Feld>();
		int gliederAnzahl = schlangenart.getZeichenLength();
		
		
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, feld);
		nachbarn.remove(feld);
		
		
		
		if(nachbarn.size() = 0) {
			return false;
		}
		
		//Random randomPosition = new Random();
		if (gliederAnzahl == 1) {
			do {
				if (nachbarn.size() == 0) {
					break;
				}
				Random randomNacharschaftsFeld = new Random();
				int randFeld = randomNacharschaftsFeld.nextInt(nachbarn.size());
				Feld nextFeld = nachbarn.get(randFeld);
				if (nextFeld.getZeichen() == ' '){
						feldIstLeer = true;
						zulaessigeFelder.add(nextFeld);
						//return zulaessigeFelder;
				}
				//für den fall dass es kein platz mehr für eine neue schlangenart gefunden ist
				
			}while(!feldIstLeer);
			
			return zulaessigeFelder;
		}
		do {
			if (nachbarn.size() == 0) {
				break;
			}
			Random randomNacharschaftsFeld = new Random();
			int randFeld = randomNacharschaftsFeld.nextInt(nachbarn.size());
			Feld nextFeld = nachbarn.get(randFeld);
			if (nextFeld.getZeichen() == ' '){
					feldIstLeer = true;
					nachbarn.remove(randFeld);
			}
			//für den fall dass es kein platz mehr für eine neue schlangenart gefunden ist
			
		}while(!feldIstLeer);
		
		return zulaessigeFelder;
	}*/
	//zu optemieren
	private boolean istZulaessigBT(Dschungel dschungel,Schlangenart schlangenart,int schlangenZeichenIterator, Feld thisFeld, Feld nextFeld, ArrayList<Feld> felderlist) {
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, nextFeld);
		nachbarn.remove(thisFeld);
		
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
					felderlist.add(nextFeld);	
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
			if (nextFeld.getZeichen() == ' ') {
				//System.out.println(nextFeld.getZeichen());
				if(istZulaessigBT(dschungel, schlangenart, schlangenZeichenIterator-1,thisFeld, nextFeld, felderlist)) {
					felderlist.add(nextFeld);
					return true;
				}
			}
			nachbarn.remove(nextFeld);
		}
		
		/*
		while (true) {
			if (nachbarn.size() == 0) {
				return false;
			}
			Random randomNacharschaftsFeld = new Random();
			int randFeld = randomNacharschaftsFeld.nextInt(nachbarn.size());
			Feld nextFeld = nachbarn.get(randFeld);
			if (nextFeld.getZeichen() == ' '){
				felderlist.add(nextFeld);	
				return true;
					
			}
			nachbarn.remove(randFeld);
			//für den fall dass es kein platz mehr für eine neue schlangenart gefunden ist
			
		}
		
		
		isZulaessig(dschungel, schlangenart,  feld, felderlist);
		}*/
		
	}
	private boolean alleMatrixFelderBesucht(ArrayList<ArrayList<Feld>> pruefMatrix) {
		for(int i = 0 ; i < zeilen; i++) {
			
			for (int j = 0; j< spalten; j++) {
				if (pruefMatrix.get(i).get(j) != null) {
					return false;
				}
			}
			
		}
		return true;
	}
	

}
