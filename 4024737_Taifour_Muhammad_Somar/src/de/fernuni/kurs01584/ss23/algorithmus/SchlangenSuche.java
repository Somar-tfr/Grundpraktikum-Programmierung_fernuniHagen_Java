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
	private double abgabeZeit;
	private Dschungel dschungel;
	private Schlangenarten schlangenarten;
	private ArrayList<Schlange> loesung;
	private int maxPunkte;
	private int aktPunkte;
	private int gmaxPunkte;
	private int gaktPunkte;
	private double startZeit;
    private double zeitLimit;
	
	public SchlangenSuche(Dschungel dschungel, Schlangenarten schlangenarten, double zeitLimit){
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.loesung = new ArrayList<Schlange>();
		this.maxPunkte = 0;
		this.aktPunkte = 0;
		this.gmaxPunkte = 0;
		this.gaktPunkte = 0;
		this.zeitLimit = zeitLimit;
		this.abgabeZeit = 0;
	}
	
	public ArrayList<Schlange> getLoesung(){
		return this.loesung;
	}
	
	public void sucheSchlange() {
		startZeit = System.currentTimeMillis();
		
		ArrayList<Feld> tempFelder = new ArrayList<Feld>();
		ArrayList<ArrayList<Feld>> dschungelMatrix = dschungel.getMatrix();
		for (ArrayList<Feld> zeile : dschungelMatrix) {
			tempFelder.addAll(zeile);
		}
		
		
		
		do {
			for (int h = 0; h < schlangenarten.getSize(); h++) {
				
				Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(h);
				
					// hier muss ich sicher stellen dass der anfangspunkt jedesmal anderes ist
				ArrayList<Feld> zulaessigeFelder = tempFelder.stream()
						    .filter(feld -> feld.getZeichen() == schlangenart.getZeichenkette().charAt(0) && feld.getVerwendbarkeit() > 0)
						    .collect(Collectors.toCollection(ArrayList::new));
				for (int k = 0; k < schlangenart.getAnzahl(); k++) {// moved from 63 under arraylist declaration
					Collections.shuffle(zulaessigeFelder);
					Collections.sort(zulaessigeFelder, feldComparator);
					
					
					
					Schlange schlange = new Schlange();
					
					for (Feld feld : zulaessigeFelder) {
						Schlangenglied glied = new Schlangenglied(feld);
						if(sucheSchlangenGlied(glied, schlangenart, 1) /*&& (aktPunkte > maxPunkte)*/) {
							schlange.setKopf(glied);
							/*schlange.setPunkte(aktPunkte);*/
							schlange.setArt(schlangenart.getId());
							this.gaktPunkte = 0;
							this.gmaxPunkte = 0;
						}
					}
					
					if (schlange.getKopf().getFeld() != null){
					    // check if the solution already has a Schlange of this type
					    long anzahl = loesung.stream().filter(existingSchlange -> existingSchlange.getArt().equals(schlange.getArt())).count();
					    if (anzahl <= schlangenart.getAnzahl()) {
					        loesung.add(schlange);
					        zulaessigeFelder.remove(schlange.getKopf().getFeld());// added new
					    }
					}
					if (loesung.size() == schlangenarten.getAnzahl()){
						double zeit = (System.currentTimeMillis() - startZeit);
						setAbgabeZeit(zeit / 1000);
					}
					
				}
				
				
			}
			
		}while(loesung.size() != schlangenarten.getAnzahl() && (System.currentTimeMillis() - startZeit < zeitLimit));
		double zeit = (System.currentTimeMillis() - startZeit);
		setAbgabeZeit( zeit/ 1000);
	}
	
	private boolean sucheSchlangenGlied(Schlangenglied vorGlied, Schlangenart schlangenart, int iterator) {
		// TODO Auto-generated method stub
		if(iterator == schlangenart.getSize()) {
			
			
			return true;
		}
		if( iterator == 1) {
			this.gaktPunkte = 0;
		}

		
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, vorGlied.getFeld())
			    .stream()
			    .filter(feld -> (feld.getVerwendbarkeit() > 0 && feld.getZeichen() == schlangenart.getZeichenkette().charAt(iterator)))
			    .collect(Collectors.toCollection(ArrayList::new));
		// should try to remove previous glieder
		//nachbarn.removeAll(arr)
		Collections.shuffle(nachbarn);
		Collections.sort(nachbarn, feldComparator);
		
		for (Feld feld : nachbarn) {
			Schlangenglied glied = new Schlangenglied(feld);
			this.gaktPunkte += feld.getPunkte();
			vorGlied.setNext(glied);
			
			if((this.gaktPunkte > this.gmaxPunkte) && sucheSchlangenGlied(glied, schlangenart, iterator + 1) ) {
				this.gmaxPunkte = this.gaktPunkte ;
				
				
				
				
				
				return true;
			}else {
				this.gaktPunkte -= feld.getPunkte();
				vorGlied.setNext(null);
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
	public void setAbgabeZeit(double abgabezeit) {
		this.abgabeZeit = abgabezeit;
	}
	public double getAbgabeZeit() {
		// TODO Auto-generated method stub
		return this.abgabeZeit;
	}

	public int getAnzahl() {
		// TODO Auto-generated method stub
		return this.loesung.size();
	}
	
}
