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
	Dschungel dschungel;
	Schlangenarten schlangenarten;
	int zeitAngabe;
	int temp;
	char angabeZeichen;
	int aktuellePunkte;
	int maxPunkte;
	Schlange schlange;
	Schlange tempSchlange;
	Schlangenart tempSchlangenart;
	ArrayList<Schlange> schlangenArray;
	Map<Schlangenart, Integer> keyValuePairs;
	
	
	
	public SchlangenSuche(Dschungel dschungel,
			Schlangenarten schlangenarten,
			int zeiAngabe,
			char angabeZeichen) {
		this.temp = 0;
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.zeitAngabe = zeitAngabe;
		this.angabeZeichen = angabeZeichen;
		this.aktuellePunkte = 0;
		this.maxPunkte = 0;
		this.schlange = new Schlange();
		this.tempSchlange = new Schlange();
		this.keyValuePairs = new HashMap<>();
		for(Schlangenart schlangenart: schlangenarten) {
			this.keyValuePairs.put(schlangenart, schlangenart.getAnzahl());
		}
		this.schlangenArray = new ArrayList<Schlange>();
	}
	
	public ArrayList<Schlange> getResult(){
		return schlangenArray;
	}
	
	public void sucheSchlange2() {
		
		/*if (aktuellePunkte > maxPunkte) {
			schlange = tempSchlange;
			schlangenArray.add(schlange);
			aktuellePunkte = 0;
			return;
		}
		
		/*
		if (zeitAngabe >= 0) {
			schlangenArray.add(schlange);
			return;
		}*/
		do {
			iterateTemp();
		ArrayList<Feld> zulaessigeFelder = new ArrayList<Feld>();
		for(ArrayList<Feld> zeile : dschungel.getMatrix()) {
			for (Feld element : zeile) {
				if(element.getVerwendbarkeit() > 0) {
					zulaessigeFelder.add(element);
				}
			}
		}
		Collections.sort(zulaessigeFelder, feldComparator);
			
		
			for (Feld startFeld: zulaessigeFelder) {
				
				
				
				ArrayList<Schlangenart> tempSchlangenarten = new ArrayList<Schlangenart>();
				for(Schlangenart schlangenart : schlangenarten) {
					System.out.println("keyValuePairs.values()");
					System.out.println(keyValuePairs.values());
					System.out.println(keyValuePairs.keySet());
					if (schlangenart.getZeichenkette().charAt(0) == startFeld.getZeichen()
							&& keyValuePairs.get(schlangenart) > 0) {
						tempSchlangenarten.add(schlangenart);
						
						;
					}
				}
				System.out.println("weiter3");
				System.out.println("tempschlangenarten size: " + tempSchlangenarten.size());
				Collections.sort(tempSchlangenarten, schlangenartComparator);
				for (Schlangenart schlangenart : tempSchlangenarten) {
					System.out.println("Iteration!");
					tempSchlange = new Schlange();
					Schlangenglied glied = new Schlangenglied(startFeld);
					tempSchlange.addGlied(glied);
					aktuellePunkte = startFeld.getPunkte();
					
					if (sucheSchlangenGlied(glied, 1, schlangenart)){
						tempSchlange.setPunkte(maxPunkte) ;
						schlangenArray.add(tempSchlange);
						
						//
						reduceValue(schlangenart);
						//
						aktuellePunkte = 0;
						maxPunkte = 0;
						System.out.println("+++++++++++++++++++++++++");
						System.out.println("ZeitAngabe :" + zeitAngabe);
						System.out.println("+++++++++++++++++++++++++");
						/*if (zeitAngabe == 0) {
							break;
						}
						zeitAngabe = zeitAngabe - 1;*/

					}
					
					
				}
			}
			if (zeitAngabe == 0) {
				System.out.println("+++++++++++++++++++++++++");
				System.out.println("ZeitAngabe :" + zeitAngabe);
				System.out.println("+++++++++++++++++++++++++");
				break;
			}
			reduceZeit();
			System.out.println("+++++++++++++++++++++++++");
			System.out.println("ZeitAngabe :" + zeitAngabe);
			System.out.println("+++++++++++++++++++++++++");
			
		}while(zeitAngabe > 0);
		System.out.println("TTEEMMPP == " + this.temp);
		
		
		
		//return false;
	}
	
	

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
		
		 }*/
		 private boolean sucheSchlangenGlied(Schlangenglied vorGlied, int iterator, Schlangenart schlangenart) {
		// TODO Auto-generated method stub
			 System.out.println("Gliedloop!");
			if (iterator == schlangenart.getSize() ) {
				
				if(aktuellePunkte > maxPunkte) {
		            schlange = tempSchlange;
		            maxPunkte = aktuellePunkte;
		            
		        }
		        return true;
				
			}
			ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, vorGlied.getFeld())
				    .stream()
				    .filter(feld -> (feld.getVerwendbarkeit() > 0 && feld.getZeichen() == schlangenart.getZeichenkette().charAt(iterator )))
				    .collect(Collectors.toCollection(ArrayList::new));
			Collections.sort(nachbarn, feldComparator);
			for (Feld nachbarFeld : nachbarn) {
				
				
				Schlangenglied glied = new Schlangenglied(nachbarFeld);
				vorGlied.setNext(glied);
				nachbarFeld.setVerwendbarkeit(nachbarFeld.getVerwendbarkeit() - 1);
				
				
				tempSchlange.addGlied(glied);
				aktuellePunkte += nachbarFeld.getPunkte();
				
				if ( sucheSchlangenGlied(glied, iterator + 1, schlangenart)) {
					
					return true;
				}
				vorGlied.setNext(null);
				nachbarFeld.setVerwendbarkeit(nachbarFeld.getVerwendbarkeit() + 1);
				tempSchlange.removeGlied(glied);
				aktuellePunkte -= nachbarFeld.getPunkte();
				
			}
			
			return false;
		 }
		 // ich habe ein problem mit reduce value
		 private void reduceValue(Schlangenart key){
			Integer newValue = this.keyValuePairs.get(key) - 1;
			this.keyValuePairs.remove(key);
			this.keyValuePairs.put(key, newValue);
		 }
		 
		 private void addValue(Schlangenart key){
				Integer newValue = this.keyValuePairs.get(key) + 1;
				this.keyValuePairs.remove(key);
				this.keyValuePairs.put(key, newValue);
			 }
		 
		 private int valuesSum() {
			 int sum = this.keyValuePairs.values().stream().mapToInt(Integer::intValue).sum();
			 return sum;
		 }
		 private void iterateTemp() {
			 this.temp += 1;
		 }
		 
		 private void reduceZeit() {
			 this.zeitAngabe -= 1;
		 }
		
		 /*METHODE sucheSchlangenglied(vorherigesGlied) {
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
	
	Comparator<Schlangenart> schlangenartComparator = new Comparator<Schlangenart>() {
	    @Override
	    public int compare(Schlangenart o1, Schlangenart o2) {
	        // Define your comparison logic here
	    	if (o1.getSize() > o2.getSize()){
	    		return -1;
	    	}
	    	
	    	if (o1.getSize() == o2.getSize()) {
	    		return 0;
	    	}
	    	return 1;
	    	
	    	
	        // Return a negative integer, zero, or a positive integer
	        // based on whether o1 is less than, equal to, or greater than o2
	    }
	};

	
	
}
