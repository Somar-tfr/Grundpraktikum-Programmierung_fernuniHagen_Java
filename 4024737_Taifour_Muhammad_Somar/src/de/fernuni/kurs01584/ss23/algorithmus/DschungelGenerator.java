package de.fernuni.kurs01584.ss23.algorithmus;

import java.util.ArrayList;
import java.util.Random;
import de.fernuni.kurs01584.ss23.modell.*;
import java.util.*;

/**
 * Der DschungelGenerator erzeugt ein Dschungel Objekt mit spezifizierten Eigenschaften.
 * Er stellt sicher, dass zulässige Zeichen und Schlangenarten im Dschungel platziert werden.
 */
public class DschungelGenerator {


	private int zeilen;
	private int spalten;
	private String zulaessigeZeichen;
	private Schlangenarten schlangenarten;
	private Random randomGenerator = new Random();
	private int vNummer;

	/**
     * Konstruiert einen neuen DschungelGenerator mit spezifizierten Eigenschaften.
     *
     * @param zeilen Anzahl der Zeilen im Dschungel
     * @param spalten Anzahl der Spalten im Dschungel
     * @param zulaessigeZeichen Zeichen, die im Dschungel verwendet werden können
     * @param schlangenarten Ein Satz von Schlangenarten, die im Dschungel platziert werden können
     * @throws IllegalArgumentException wenn ungültige Argumente bereitgestellt werden
     */
	public DschungelGenerator(int zeilen,
			int spalten,
			String zulaessigeZeichen,
			Schlangenarten schlangenarten
			){
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
	
	/**
     * Erzeugt einen Dschungel und füllt ihn mit zufällig verteilten Schlangen und zufälligen Zeichen.
     * 
     * @return Dschungel Objekt mit initialisierter Belegung.
     */
	public Dschungel erzeugeDschungel() {
		System.out.println("Dschungel wird generiert.. ");
		Dschungel dschungel = new Dschungel(zeilen, spalten, zulaessigeZeichen);
		verwendbarkeitErstellen();
		//Zufällige schlangen verteilung
		
		int schlangenartenAnzahl = schlangenarten.getSize();
		

		//muss man die paralell asführen??
		// schlangenarten iterieren
		HashSet<Feld> verwendeteFelder = new HashSet<>();
		for(int i = 0; i < schlangenartenAnzahl; i++) {
			//muss sicher stellen dass die zeichenketten in schlange zulaessig sind
			
			
			
			//schlangenart wählen und benötigte informationen speichern
			Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
			int gliederAnzahl = schlangenart.getSize();
			int schlangenAnzahl = schlangenart.getAnzahl();
			
			for(int j = 0 ; j < schlangenAnzahl; j++) {
				
				ArrayList<Feld> felderlist = new ArrayList<Feld>();//felderliste für  felder die recursiv belegt sind und die schlange zulässig ist
				
				//looop für ein random position in dem dschungel für die schlange
				
				while(!istZulaessigBT(dschungel, schlangenart, gliederAnzahl -1, null, felderlist, verwendeteFelder)) {
					
			        felderlist.clear(); // clear the list and set to generate a new snake
			        
			    } //diese do while addiert um sicher zu stellen dass es eine schlange zu ende platziert wird
			}
			

			}


		//Belegung verbliebenden Felder mit zufällige zeichen
		int randMax = zulaessigeZeichen.length() - 1; // eine random nummer für
		for (int i = 0; i < dschungel.getZeilen(); i++) {
			for (int j = 0; j < dschungel.getSpalten(); j++) {
				Feld feld = dschungel.getFeld(i, j);
				//prüfe ob das feld leer ist
				if ( feld.getZeichen() == ' ') {
					char zeichen = ' ';
					if (randMax == 0){
						 zeichen = zulaessigeZeichen.charAt(0);
					}else {
						 zeichen = zulaessigeZeichen.charAt(randomGenerator.nextInt(randMax));
					}
					
					feld.verwendbarkeitPlus();
					feld.setZeichen(zeichen);
					//feld.setVerwendbarkeit(1);
					//feld.verwendbarkeitPlus();
					feld.setPunkte(1); //hier muss ich anpassen
				}//if feld nicht belegt
			}//spalten iteration
		}//zeilen iteration
		

		return dschungel;
	}

	/**
     * Überprüft, ob die Platzierung einer Schlange in einem bestimmten Feld zulässig ist, 
     * und platziert sie, falls zulässig, mit Backtracking.
     *
     * @param dschungel Der Dschungel, in dem die Schlange platziert werden soll
     * @param schlangenart Die Art der Schlange, die platziert werden soll
     * @param schlangenZeichenIterator Der Iterator, der die aktuelle Position in der Schlange anzeigt
     * @param thisFeld Das aktuelle Feld, in dem die Schlange platziert werden soll
     * @param felderlist Eine Liste von Feldern, die bereits von der Schlange belegt sind
     * @param verwendeteFelder Ein Set von Feldern, die bereits von der Schlange verwendet wurden
     * @return true, wenn die Platzierung der Schlange in dem Feld zulässig ist, sonst false
     */
	private boolean istZulaessigBT(Dschungel dschungel,
			Schlangenart schlangenart,
			int schlangenZeichenIterator, 
			Feld thisFeld,
			ArrayList<Feld> felderlist,
			HashSet<Feld> verwendeteFelder) {
		

		//die n0 position
		if (schlangenZeichenIterator == -1) {
			//System.out.println(thisFeld.getVerwendbarkeit()+ " "+ getVNummer());

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
		
		//SCHAUE OB DIE ANZAHL AN VERWENDETEN FELDER KLEINER ALS DIE VNUMMER
		//die negative positionen die bereits belegt sind
		
		
		if((Collections.frequency(verwendeteFelder,thisFeld) >= getVNummer()) || (thisFeld.getZeichen() != ' ' && (Collections.frequency(verwendeteFelder,thisFeld) > getVNummer()) )) {
		
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
			
			thisFeld.verwendbarkeitPlus();
			
			
			felderlist.add(thisFeld);
			
			if(istZulaessigBT(dschungel, schlangenart, schlangenZeichenIterator - 1, feld, felderlist, verwendeteFelder)) {
				
				return true;
			}else {
				verwendeteFelder.remove(thisFeld);
				
				thisFeld.setZeichen(' ');
				thisFeld.setPunkte(prevPunkte);
				
				thisFeld.verwenden();
				
				 
				felderlist.remove(thisFeld);
				
			}
				
				
		}
		return false;

		
		
	}
	private void verwendbarkeitErstellen() {
		int felderSollAnzahl = 0;
		int felderIstAnzahl = zeilen * spalten;
		
		
		int schlangenartenAnzahl = schlangenarten.getSize();
		for(int i = 0; i < schlangenartenAnzahl; i++) {
			Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
			int gliederAnzahl = schlangenart.getSize();
			int schlangenAnzahl = schlangenart.getAnzahl();
			
			felderSollAnzahl += (gliederAnzahl * schlangenAnzahl) + 1;
		}
		
		if (felderIstAnzahl < felderSollAnzahl) {
			if (felderSollAnzahl % felderIstAnzahl != 0) {
				int verwendbarkeit = (int) Math.ceil((double) felderSollAnzahl / felderIstAnzahl);
				setvNummer(10 * verwendbarkeit);
			}else {
				int verwendbarkeit = felderSollAnzahl / felderIstAnzahl;
				setvNummer( 10 * verwendbarkeit);	
			}
			
		}else {
			setvNummer(1);
		}		
	}
	
	private void setvNummer(int nummer){
		this.vNummer = nummer;
	}
	private int getVNummer() {
		return this.vNummer;
	}
	

}
