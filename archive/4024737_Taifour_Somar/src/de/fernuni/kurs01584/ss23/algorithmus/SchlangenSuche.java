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
	
	
/**
 * Diese Klasse definiert den Suchalgorithmus für Schlangen in einem Dschungel.
 */
public class SchlangenSuche {
	private double abgabeZeit;
	private Dschungel dschungel;
	private Schlangenarten schlangenarten;
	private ArrayList<Schlange> loesung;
	private int gmaxPunkte;
	private int gaktPunkte;
	private double startZeit;
    private double zeitLimit;
	
    /**
     * Erzeugt eine neue Instanz von SchlangenSuche.
     *
     * @param dschungel     Der Dschungel, in dem die Schlangen gesucht werden.
     * @param schlangenarten Die Liste der Schlangenarten, die im Dschungel vorkommen.
     * @param zeitLimit     Die maximale Zeit, die für die Suche zugewiesen wurde.
     */
	public SchlangenSuche(Dschungel dschungel, Schlangenarten schlangenarten, double zeitLimit){
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.loesung = new ArrayList<Schlange>();
		this.gmaxPunkte = 0;
		this.gaktPunkte = 0;
		this.zeitLimit = zeitLimit;
		this.abgabeZeit = 0;
	}
	
	/**
     * Gibt die gefundenen Schlangen zurück.
     *
     * @return Eine Liste der gefundenen Schlangen.
     */
	public ArrayList<Schlange> getLoesung(){
		return this.loesung;
	}
	
	/**
     * Sucht nach Schlangen im Dschungel.
     */
	public void sucheSchlange() {
		startZeit = System.currentTimeMillis();
		
		ArrayList<Feld> tempFelder = new ArrayList<Feld>();
		ArrayList<ArrayList<Feld>> dschungelMatrix = dschungel.getMatrix();
		
		//eine liste mit alle felder erstellen
		for (ArrayList<Feld> zeile : dschungelMatrix) {
			tempFelder.addAll(zeile);
		}
		
		//durch schlangenarten iterieren
		do {
			for (int h = 0; h < schlangenarten.getSize(); h++) {
				
				Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(h);
				
				//zulaessige Felder die die gleiche zeichen wie die in schlangenart  && feldVerwendbarkeit >0
				ArrayList<Feld> zulaessigeFelder = tempFelder.stream()
						    .filter(feld -> feld.getZeichen() == schlangenart.getZeichenkette().charAt(0) && feld.getVerwendbarkeit() > 0)
						    .collect(Collectors.toCollection(ArrayList::new));
				
				//sortiere zulaessigeFelder nach punkte
				for (int k = 0; k < schlangenart.getAnzahl(); k++) {// moved from 63 under arraylist declaration
					Collections.shuffle(zulaessigeFelder);
					Collections.sort(zulaessigeFelder, feldComparator);
					

					Schlange schlange = new Schlange();
					
					//iteration durch alle felder
					for (Feld feld : zulaessigeFelder) {
						
						//rekursion
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
					    // pruefe ob diese lösung bereits da ist
					    long anzahl = loesung.stream().filter(existingSchlange -> existingSchlange.getArt().equals(schlange.getArt())).count();
					    if (anzahl <= schlangenart.getAnzahl()) {
					        loesung.add(schlange);
					        zulaessigeFelder.remove(schlange.getKopf().getFeld());// added new
					    }
					}
					
					//stoppe den Zeit wenn alle schlangen gefunden sind
					if (loesung.size() == schlangenarten.getAnzahl()){
						double zeit = (System.currentTimeMillis() - startZeit);
						setAbgabeZeit(zeit / 1000);
					}
					
				}
				
				
			}
			
		}while(loesung.size() != schlangenarten.getAnzahl() && (System.currentTimeMillis() - startZeit < zeitLimit));
		
		//schreibe zeit am ende
		double zeit = (System.currentTimeMillis() - startZeit);
		setAbgabeZeit( zeit/ 1000); // in ms
	}
	
	/**
     * Sucht nach dem nächsten Schlangenglied, das der Schlange hinzugefügt werden soll, Backtracking.
     *
     * @param vorGlied     Das vorherige Schlangenglied.
     * @param schlangenart Die Art der Schlange, die gesucht wird.
     * @param iterator     Der Iterator für die aktuelle Position in der Schlange.
     * @return Gibt true zurück, wenn das nächste Schlangenglied gefunden wurde, sonst false.
     */
	private boolean sucheSchlangenGlied(Schlangenglied vorGlied, Schlangenart schlangenart, int iterator) {
		
		//n0 die tiefste rekursion
		if(iterator == schlangenart.getSize()) {
			
			
			return true;
		}
		
		if( iterator == 1) {
			this.gaktPunkte = 0;
		}

		//initiere zulaessige nachbarfelder
		ArrayList<Feld> nachbarn = schlangenart.getNachbarschaftsstruktur().getNachbarschaft(dschungel, vorGlied.getFeld())
			    .stream()
			    .filter(feld -> (feld.getVerwendbarkeit() > 0 && feld.getZeichen() == schlangenart.getZeichenkette().charAt(iterator)))
			    .collect(Collectors.toCollection(ArrayList::new));
		
		//sortiere nach punkte
		Collections.shuffle(nachbarn);
		Collections.sort(nachbarn, feldComparator);
		
		for (Feld feld : nachbarn) {
			Schlangenglied glied = new Schlangenglied(feld);
			this.gaktPunkte += feld.getPunkte();
			vorGlied.setNext(glied);
			
			//rekursion
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
	
	/**
     * Ein Comparator für Felder. Felder werden nach ihrer Punktzahl sortiert.
     */
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
	    }
	};
	
	/**
     * Legt die Zeit fest, zu der die Lösung abgegeben wird.
     *
     * @param abgabezeit Die Zeit, zu der die Lösung abgegeben wird.
     */
	public void setAbgabeZeit(double abgabezeit) {
		this.abgabeZeit = abgabezeit;
	}
	
	/**
     * Gibt die Zeit zurück, zu der die Lösung abgegeben wurde.
     *
     * @return Die Zeit, zu der die Lösung abgegeben wurde.
     */
	public double getAbgabeZeit() {
		// TODO Auto-generated method stub
		return this.abgabeZeit;
	}

	/**
     * Gibt die Anzahl der gefundenen Schlangen zurück.
     *
     * @return Die Anzahl der gefundenen Schlangen.
     */
	public int getAnzahl() {
		// TODO Auto-generated method stub
		return this.loesung.size();
	}
	

}
