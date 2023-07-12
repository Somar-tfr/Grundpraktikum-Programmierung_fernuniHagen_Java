package de.fernuni.kurs01584.ss23.algorithmus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import de.fernuni.kurs01584.ss23.modell.*;


public class SchlangenSuche {
	private int maxZeit;
	private Dschungel dschungel;
	private int punkte;
	private Random randomGenerator = new Random();
	
	SchlangenSuche(Dschungel dschungel, int maxZeit){
		this.dschungel = dschungel;
		this.maxZeit = maxZeit;
		this.punkte = 0;
	}
	
	public static void sucheSchlange(Dschungel dschungel, Schlangenarten schlangenarten) { // muss hier anpassen und mehr überlegen
		ArrayList<ArrayList<Feld>>matrix = dschungel.getMatrix();
		
		for(ArrayList<Feld> zeile : matrix) {
			for (Feld element : zeile) {
				HashSet<Feld> verwendeteFelder = new HashSet<>();
				if (istZulaessigBt(dschungel, schlangenarten, ,element, ,verwendeteFelder)) {
					
				}
				}
		}
	}
	
	private boolean istZulaessigBT(Dschungel dschungel,Schlangenarten schlangenarten,int schlangenZeichenIterator, Feld thisFeld, ArrayList<Feld> felderlist,HashSet<Feld> verwendeteFelder) {
		//ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, thisFeld);
		//nachbarn.remove(thisFeld);
		
		//ITERIERE SCHLANGENARTEN VERGLEICH MIT FELD, ERSTE MIT ERSTE DANN ZWEITE MIT SZEITE USW
		int zeilen = dschungel.getZeilen();
		int spalten = dschungel.getSpalten();
		//die n0 position
		if (schlangenZeichenIterator == -1) {
			return true;
		}
			
		char appendChar = schlangenarten.getZeichenkette().charAt(schlangenart.getSize() - schlangenZeichenIterator -1 );
		int appendPunkte = schlangenarten.getPunkte();
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
		ArrayList<Feld> nachbarn = schlangenarten.getNachbarschaftsstruktur().getNachbarschaft(dschungel, thisFeld);
		
		Collections.shuffle(nachbarn);
		
		for(Feld feld: nachbarn) {
			verwendeteFelder.add(thisFeld);
			thisFeld.setZeichen(appendChar);
			int prevPunkte = thisFeld.getPunkte();
			thisFeld.setPunkte(appendPunkte);
			felderlist.add(thisFeld);
			
			if(istZulaessigBT(dschungel, schlangenarten, schlangenZeichenIterator - 1, feld, felderlist, verwendeteFelder)) {
				System.out.println(thisFeld.getId());
				return true;
			}else {
				verwendeteFelder.remove(thisFeld);
				
				thisFeld.setZeichen(' ');
				thisFeld.setPunkte(prevPunkte);
				felderlist.remove(thisFeld);
				
			}
				
				
		}
		return false;

		
		
	}

}
