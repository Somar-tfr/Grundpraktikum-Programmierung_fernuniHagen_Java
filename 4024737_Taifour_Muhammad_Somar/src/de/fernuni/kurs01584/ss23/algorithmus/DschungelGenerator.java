package de.fernuni.kurs01584.ss23.algorithmus;

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
		
		
		//Belegung verbliebenden Felder mit zufällige zeichen
		int randMax = zulaessigeZeichen.length() - 1;

		for (int i = 0; i < dschungel.getZeilen(); i++) {
			for (int j = 0; j < dschungel.getSpalten(); j++) {
				Feld feld = dschungel.getFeld(i, j);
				if ( feld.getZeichen() == ' ') {
					Random random = new Random();
					int randZeichen = random.nextInt(randMax);
					char zeichen = zulaessigeZeichen.charAt(randZeichen);
					feld.setZeichen(zeichen);
					
				}//if feld nicht belegt
			}//spalten iteration
		}//zeilen iteration
		
		
		
		return dschungel;
	}

}
