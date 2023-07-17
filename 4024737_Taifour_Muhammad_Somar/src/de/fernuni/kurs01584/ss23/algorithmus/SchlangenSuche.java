package de.fernuni.kurs01584.ss23.algorithmus;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
	


import de.fernuni.kurs01584.ss23.modell.*;
	
	

public class SchlangenSuche {
	private Dschungel dschungel;
	private Schlangenarten schlangenarten;
	private ArrayList<Schlange> loesung;
	private int maxPunkte;
	private int aktPunkte;
	
	public SchlangenSuche(Dschungel dschungel, Schlangenarten schlangenarten){
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.loesung = new ArrayList<Schlange>();
		this.maxPunkte = 0;
		this.aktPunkte = 0;
	}
	
	public ArrayList<Schlange> getLoesung(){
		return this.loesung;
	}
	
	public void sucheSchlange() {
		ArrayList<Feld> tempFelder = new ArrayList<Feld>();
		ArrayList<ArrayList<Feld>> dschungelMatrix = dschungel.getMatrix();
		for (ArrayList<Feld> zeile : dschungelMatrix) {
			tempFelder.addAll(zeile);
		}
		
		
		
		do {
			for (int h = 0; h < schlangenarten.getAnzahl(); h++) {
				
				Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(h);
				ArrayList<Feld> zulaessigeFelder = tempFelder.stream()
					    .filter(feld -> feld.getZeichen() == schlangenart.getZeichenkette().charAt(0) && feld.getVerwendbarkeit() > 0)
					    .collect(Collectors.toCollection(ArrayList::new));
				
				Collections.shuffle(zulaessigeFelder);
				Collections.sort(zulaessigeFelder, feldComparator);
				
				
				
				Schlange schlange = new Schlange();
				
				for (Feld feld : zulaessigeFelder) {
					System.out.println("for looop");
					Schlangenglied glied = new Schlangenglied(feld);
					if(sucheSchlangenGlied(glied, schlangenart, 1) /*&& (aktPunkte > maxPunkte)*/) {
						System.out.println("hier!");
						schlange.setKopf(glied);
						schlange.setPunkte(aktPunkte);
						schlange.setArt(schlangenart.getId());
						aktPunkte = 0;
					}
				}
				loesung.add(schlange);
			}
			
		}while(loesung.size() != schlangenarten.getAnzahl());
		
	}
	
	private boolean sucheSchlangenGlied(Schlangenglied vorGlied, Schlangenart schlangenart, int iterator) {
		// TODO Auto-generated method stub
		System.out.println("suche1");
		Feld feldG = vorGlied.getFeld();
		if(iterator == schlangenart.getSize()) {
			System.out.println("iterator last");
			return true;
		}
		
		
		/*if(schlangenart.getZeichenkette().charAt(iterator) != feldG.getZeichen()) {
			System.out.println("false");
			return false;
		}*/
		
		
		
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, vorGlied.getFeld())
			    .stream()
			    .filter(feld -> (feld.getVerwendbarkeit() > 0 && feld.getZeichen() == schlangenart.getZeichenkette().charAt(iterator)))
			    .collect(Collectors.toCollection(ArrayList::new));
		Collections.shuffle(nachbarn);
		Collections.sort(nachbarn, feldComparator);
		int temp = 0;
		for (Feld feld : nachbarn) {
			Schlangenglied glied = new Schlangenglied(feld);
			aktPunkte += feld.getPunkte();
			
			if(sucheSchlangenGlied(glied, schlangenart, iterator + 1) /*&& (aktPunkte > internMaxPunkte)*/) {
				System.out.println("in suche loop");
				
				
				if(iterator == 1) {
					aktPunkte += schlangenart.getPunkte();
				}
				
				vorGlied.setNext(glied);
				
				return true;
			}
			
			
			aktPunkte -= feld.getPunkte();
			if(iterator == 0) {
				aktPunkte -= schlangenart.getPunkte();
			}
		}
		
		return false;
	}
	

	Comparator<Feld> feldComparator = new Comparator<Feld>() {
	    @Override
	    public int compare(Feld o1, Feld o2) {
	        // Define your comparison logic here
	    	if (o1.getPunkte() > o2.getPunkte()){
	    		return -1;
	    	}
	    	
	    	if (o1.getPunkte() == o2.getPunkte()) {
	    		return 0;
	    	}
	    	return 1;
	    	
	    	
	        // Return a negative integer, zero, or a positive integer
	        // based on whether o1 is less than, equal to, or greater than o2
	    }
	};
	//hier kann angapasst dass die nach die maximale punkte sortiert sein können
	
}
