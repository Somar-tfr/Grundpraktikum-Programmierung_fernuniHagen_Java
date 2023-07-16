package de.fernuni.kurs01584.ss23.algorithmus;

import java.util.ArrayList;
import java.util.Random;
import de.fernuni.kurs01584.ss23.modell.*;
import java.util.*;

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
	private Random randomGenerator = new Random();

	public DschungelGenerator(int zeilen,
			int spalten,
			String zulaessigeZeichen,
			Schlangenarten schlangenarten
			/*,int schlangenanzahl*/){
		if (zeilen < 1 || spalten < 1 || zulaessigeZeichen == null || schlangenarten == null /*||  schlangenanzahl < 1*/) {
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
	}
	public Dschungel erzeugeDschungel() {
		Dschungel dschungel = new Dschungel(zeilen, spalten, zulaessigeZeichen);

		//Zufällige schlangen verteilung

		int schlangenartenAnzahl = schlangenarten.getSize();
		

		//muss man die paralell asführen??
		// schlangenarten iterieren
		for(int i = 0; i < schlangenartenAnzahl; i++) {
			//muss sicher stellen dass die zeichenketten in schlange zulaessig sind
			
			/*if(schlangeZeichenkettenZulaessig) {

			}*/

			//schlangenart wählen und benötigte informationen speichern
			Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
			int gliederAnzahl = schlangenart.getSize();
			int schlangenAnzahl = schlangenart.getAnzahl();
			
			for(int j = 0 ; j < schlangenAnzahl; j++) {
				ArrayList<Feld> felderlist = new ArrayList<Feld>();//felderliste für  felder die recursiv belegt sind und die schlange zulässig ist

				//MUSS NOCH DIE PUNKTE (UND VERWENDBARKEIT?) ADDIEREN!!
				//ein random feld wählen
				//Feld feld = null;
				//Feld nextFeld;
				//int schlangenlangenMaxIndex = gliederAnzahl - 1;//23.06 10:00
				//looop für ein random position in dem dschungel für die schlange
				HashSet<Feld> verwendeteFelder = new HashSet<>();
				istZulaessigBT(dschungel, schlangenart, gliederAnzahl -1, null, felderlist, verwendeteFelder);

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




		//Belegung verbliebenden Felder mit zufällige zeichen
		int randMax = zulaessigeZeichen.length() - 1; // eine random nummer für
		for (int i = 0; i < dschungel.getZeilen(); i++) {
			for (int j = 0; j < dschungel.getSpalten(); j++) {
				Feld feld = dschungel.getFeld(i, j);
				//prüfe ob das feld leer ist
				if ( feld.getZeichen() == ' ') {
					
					char zeichen = zulaessigeZeichen.charAt(randomGenerator.nextInt(randMax));
					feld.setZeichen(zeichen);
					feld.setVerwendbarkeit(1);
					feld.setPunkte(1); //hier muss ich anpassen
				}//if feld nicht belegt
			}//spalten iteration
		}//zeilen iteration

		return dschungel;
	}

	//zu optemieren
	private boolean istZulaessigBT(Dschungel dschungel,
			Schlangenart schlangenart,
			int schlangenZeichenIterator, 
			Feld thisFeld,
			ArrayList<Feld> felderlist,
			HashSet<Feld> verwendeteFelder) {
		//ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, thisFeld);
		//nachbarn.remove(thisFeld);

		//die n0 position
		if (schlangenZeichenIterator == -1) {
			return true;
		}
			
		char appendChar = schlangenart.getZeichenkette().charAt(schlangenart.getSize() - schlangenZeichenIterator -1 );
		int appendPunkte = schlangenart.getPunkte();
		//erstes Lauf
		if (thisFeld == null) {
			int randPosZ = randomGenerator.nextInt(zeilen - 1);
			int randPosS = randomGenerator.nextInt(spalten - 1);
			thisFeld = dschungel.getFeld(randPosZ, randPosS);

		}
		

		//die negative positionen die bereits belegt sind
		if(thisFeld.getZeichen() != ' ' || verwendeteFelder.contains(thisFeld)) {
			return false;
		}
		
		
		//Rekursive Position
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, thisFeld);
		
		Collections.shuffle(nachbarn);
		
		for(Feld feld: nachbarn) {
			verwendeteFelder.add(thisFeld);
			thisFeld.setZeichen(appendChar);
			int prevPunkte = thisFeld.getPunkte();
			thisFeld.setPunkte(appendPunkte);
			thisFeld.setVerwendbarkeit(1);
			felderlist.add(thisFeld);
			
			if(istZulaessigBT(dschungel, schlangenart, schlangenZeichenIterator - 1, feld, felderlist, verwendeteFelder)) {
				System.out.println(thisFeld.getId());
				return true;
			}else {
				verwendeteFelder.remove(thisFeld);
				
				thisFeld.setZeichen(' ');
				thisFeld.setPunkte(prevPunkte);
				thisFeld.setVerwendbarkeit(0);
				felderlist.remove(thisFeld);
				
			}
				
				
		}
		return false;

		
		
	}
	

}
