package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

/**
 * Klasse, welche die Struktur der Nachbarschaft repräsentiert. 
 * Die Nachbarschaft kann entweder vom Typ "Distanz" oder "Sprung" sein.
 */
public class Nachbarschaftsstruktur {
	private String typ;
	private int parameter1;
	private int parameter2;

	/**
	 * Konstruktor zur Erstellung einer neuen Nachbarschaftsstruktur mit drei Parametern.
	 *
	 * @param typ Der Typ der Nachbarschaftsstruktur. Kann "Distanz" oder "Sprung" sein.
	 * @param parameter1 Der erste Parameter zur Definition der Nachbarschaftsstruktur.
	 * @param parameter2 Der zweite Parameter zur Definition der Nachbarschaftsstruktur.
	 * @throws IllegalArgumentException wenn typ leer ist, oder parameter1 oder parameter2 negativ sind.
	 */
	public Nachbarschaftsstruktur(String typ, int parameter1, int parameter2){
		if (parameter1 < 0 || parameter2 < 0 || typ.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Nachbarschaftsstruktur' darf die Attribut 'parameter' keine negativen Werte und nicht höher als 2 annehmen.\n 'typ' darf nicht leer sein");
			
			
		}
		if (!(typ.equals("Distanz") || typ.equals("Sprung"))) {
			throw new IllegalArgumentException(
					"'typ' darf entweder 'Distanz' oder 'Sprung' sein.");
		}
		this.typ = typ;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		
	}
	
	/**
	 * Konstruktor zur Erstellung einer neuen Nachbarschaftsstruktur mit Zwei Parametern.
	 *
	 * @param typ Der Typ der Nachbarschaftsstruktur. Kann "Distanz" oder "Sprung" sein.
	 * @param parameter1 Der Parameter zur Definition der Nachbarschaftsstruktur.
	 * @throws IllegalArgumentException wenn typ leer ist oder parameter1 negativ ist.
	 */
	public Nachbarschaftsstruktur(String typ, int parameter1){
		if (parameter1 < 0 || typ.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Nachbarschaftsstruktur' darf die Attribut 'parameter' keine negativen Werte und nicht höher als 2 annehmen.\n 'typ' darf nicht leer sein");
			
			
		}
		if (!(typ.equals("Distanz") || typ.equals("Sprung"))) {
			throw new IllegalArgumentException(
					"'typ' darf entweder 'Distanz' oder 'Sprung' sein.");
		}
		this.typ = typ;
		this.parameter1 = parameter1;
		this.parameter2 = 0;
		
	}
	
	/**
	 * Ermittelt die Nachbarschaft basierend auf der Position des Felds im Dschungel und dem Typ der Nachbarschaftsstruktur.
	 *
	 * @param dschungel Der Dschungel, in dem das Feld existiert.
	 * @param feld Das Feld, für das die Nachbarschaft ermittelt werden soll.
	 * @return Eine Liste der Nachbarschaftsfelder.
	 */
	public ArrayList<Feld> getNachbarschaft(Dschungel dschungel, Feld feld) {
		//Nachbarschaftsliste initializieren
		ArrayList<Feld> nachbarschaft = new ArrayList<>();
		
		//senarien basiert auf dem position des angefragten felds in dem Dschungel Variante Distanz
		int feldZeile = feld.getZeile();
		int feldSpalte = feld.getSpalte();
		
		// behanlde jede typ variante
		switch (typ){ 
		
			case "Distanz":
			
					//iteration durch alle felder in dem matrix die in distanz parameter 1 vom feld sind
					for ( int i = feldZeile - parameter1; i <= feldZeile + parameter1; i++) {
						for (int j = feldSpalte - parameter1; j <= feldSpalte + parameter1; j++) {
							
							//pruefe dass es nicht die eingabe feld ist und dass solscher feld exestiert 
							if (!( i == feldZeile && j == feldSpalte) && dschungel.feldExistiert(i, j)) {
								nachbarschaft.add(dschungel.getFeld(i, j));
							}
						}
					}
		
					
				return nachbarschaft;
				
			case "Sprung":
				
				//initiere die größere sprung 2 parametern
				int maxSprung = Integer.max(parameter1, parameter2);
				
				//initiere den zeilen positionen des ziel feldes
				int i1 = feldZeile + parameter1;
				int i2 = feldZeile - parameter1;
				int i3 = feldZeile + parameter2;
				int i4 = feldZeile - parameter2;
				//initiere den spalten positionen des ziel feldes
				int j1 = feldSpalte + parameter2;
				int j2 = feldSpalte - parameter2;
				int j3 = feldSpalte + parameter1;
				int j4 = feldSpalte - parameter1;

				for ( int i = feldZeile - maxSprung; i <= feldZeile + maxSprung; i++) {
					for (int j = feldSpalte - maxSprung; j <= feldSpalte + maxSprung; j++) {
						//sprung prüfen ES GIBT EIN FEHLER HIER!!!
						if (	  ((i == i1 && j == j1)
								|| (i == i2 && j == j1)
								|| (i == i1 && j == j2)
								|| (i == i2 && j == j2) 
								|| (i == i3 && j == j3)
								|| (i == i4 && j == j3)
								|| (i == i3 && j == j4)
								|| (i == i4 && j == j4)) 
								
								&&(!( i == feldZeile && j == feldSpalte))
								&& (dschungel.feldExistiert(i, j))) {

								
							nachbarschaft.add(dschungel.getFeld(i, j));
						}
						
					}
				}
					
				}
				
				return nachbarschaft;
				
		
	}

	/**
	 * Gibt den Typ der Nachbarschaftsstruktur zurück.
	 *
	 * @return Der Typ der Nachbarschaftsstruktur.
	 */
	public String getTyp() {
		return this.typ;
	}

	/**
	 * Gibt den ersten Parameter der Nachbarschaftsstruktur zurück.
	 *
	 * @return Der erste Parameter der Nachbarschaftsstruktur.
	 */
	public int getWert1() {
		return this.parameter1;
	}
	
	/**
	 * Gibt den zweiten Parameter der Nachbarschaftsstruktur zurück.
	 *
	 * @return Der zweite Parameter der Nachbarschaftsstruktur.
	 */
	public int getWert2() {
		return this.parameter2;
	}
		
	
}
