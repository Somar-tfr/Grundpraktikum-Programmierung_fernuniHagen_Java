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
	int aktuellePunkte;
	int maxPunkte;
	
	int aktuelleMatrixPunkte;
	int maxMatrixPunkte;
	Schlange schlange;
	Schlange tempSchlange;
	Schlangenart tempSchlangenart;
	ArrayList<Schlange> schlangenArray;
	ArrayList<ArrayList<Schlange>> loesungen;
	Map<Schlangenart, Integer> keyValuePairs;
	int zeitAngabe;
	char angabeZeichen;
	ArrayList<ArrayList<Integer>> tempVerwendbarkeit;
	
	
	
	public SchlangenSuche(Dschungel dschungel,
			Schlangenarten schlangenarten,
			int zeiAngabe,
			char angabeZeichen) {
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.zeitAngabe = zeitAngabe;
		this.angabeZeichen = angabeZeichen;
		this.aktuellePunkte = 0;
		this.maxPunkte = 0;
		
		int aktuelleMatrixPunkte = 0;
		int maxMatrixPunkte = 0;
		
		this.schlange = new Schlange();
		this.tempSchlange = new Schlange();
		this.keyValuePairs = new HashMap<>();
		for(Schlangenart schlangenart: schlangenarten) {
			this.keyValuePairs.put(schlangenart, schlangenart.getAnzahl());
		}
		this.schlangenArray = new ArrayList<Schlange>();
		this.loesungen =  new ArrayList<ArrayList<Schlange>>() ;
		this.tempVerwendbarkeit = new ArrayList<ArrayList<Integer>>();
	}
	
	public ArrayList<Schlange> getResult(){
		return schlangenArray;
	}
	
	public void sucheSchlange2() {
		setTempVerwendbarkeit();
		int count = 5000;
		
		do {
			ArrayList<Feld> zulaessigeFelder = new ArrayList<Feld>();
			for(ArrayList<Feld> zeile : dschungel.getMatrix()) {
				for (Feld element : zeile) {
					if(element.getVerwendbarkeit() > 0) {
						zulaessigeFelder.add(element);
					}
				}
			}
			Collections.shuffle(zulaessigeFelder);
			Collections.sort(zulaessigeFelder, feldComparator);
			int countin = dschungel.getFelderAnzahl();
			do {
				for (Feld startFeld: zulaessigeFelder) {
					
					
					
					ArrayList<Schlangenart> tempSchlangenarten = new ArrayList<Schlangenart>();
					for(Schlangenart schlangenart : schlangenarten) {
						
						if (schlangenart.getZeichenkette().charAt(0) == startFeld.getZeichen()
								&& keyValuePairs.get(schlangenart) > 0) {
							tempSchlangenarten.add(schlangenart);
							
							;
						}
					}
					
					
					Collections.shuffle(tempSchlangenarten);
					Collections.sort(tempSchlangenarten, schlangenartComparator);
					for (Schlangenart schlangenart : tempSchlangenarten) {
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
							
							/*if (zeitAngabe == 0) {
								break;
							}
							zeitAngabe = zeitAngabe - 1;*/
	
						}
						
						
					}
				}
				countin -= 1;
				System.out.println("countin = " + countin);
			}while(countin > 0);
			loesungen.add(schlangenArray);
			resetVerwendbarkeit();
			
			if (/*this.zeitAngabe == 0 || */this.schlangenArray.size() == this.schlangenarten.getAnzahl()) {
				
				break;
			}
			
			count -= 1;
			System.out.println("count = " + count);
		}while(count > 0);
		
		Collections.sort(this.loesungen,loesungComparator );
		this.schlangenArray = this.loesungen.get(0);
		
		
		
		
		
	}
	
	

	
		 private boolean sucheSchlangenGlied(Schlangenglied vorGlied, int iterator, Schlangenart schlangenart) {
		// TODO Auto-generated method stub
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
		 
		 
		 private void reduceZeit() {
			 this.zeitAngabe -= 1;
		 }
		 private void setTempVerwendbarkeit() {
			 
				
				ArrayList<ArrayList<Integer>> x = new ArrayList<ArrayList<Integer>>();
				ArrayList<Integer> y = new ArrayList<Integer>();
				for (int i = 0; i < dschungel.getZeilen(); i++) {
					
					for (int j = 0; j < dschungel.getSpalten(); j++) {
						int v = dschungel.getFeld(i, j).getVerwendbarkeit();
						
						y.add(v);
						
					}
					x.add(y);
				}
				this.tempVerwendbarkeit = x;
		 }
		 
		 private void resetVerwendbarkeit() {
			 for (int i = 0; i < dschungel.getZeilen(); i++) {
					
					for (int j = 0; j < dschungel.getSpalten(); j++) {
						int v = dschungel.getFeld(i, j).getVerwendbarkeit();
						
						dschungel.getFeld(i, j).setVerwendbarkeit(this.tempVerwendbarkeit.get(i).get(j));
						
					}
				}
		 }
		 
		 private int getArrayPunkte(ArrayList<Schlange> o) {
				// TODO Auto-generated method stub
			 int pnkt = 0;
			 for (Schlange i : o) {
				 pnkt += i.getPunkte();
			 }
				return pnkt;
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
	    	
	    	
	        
	    }
	};
	
	Comparator<ArrayList<Schlange>> loesungComparator = new Comparator<ArrayList<Schlange>>() {
	    @Override
	    public int compare(ArrayList<Schlange> o1, ArrayList<Schlange> o2) {
	        // Define your comparison logic here
	    	if (getArrayPunkte(o1) > getArrayPunkte(o2)){
	    		return -1;
	    	}
	    	
	    	if (getArrayPunkte(o1) == getArrayPunkte(o2)) {
	    		return 0;
	    	}
	    	return 1;
	    	
	    	
	        
	    }

		
	};

	
	
}
