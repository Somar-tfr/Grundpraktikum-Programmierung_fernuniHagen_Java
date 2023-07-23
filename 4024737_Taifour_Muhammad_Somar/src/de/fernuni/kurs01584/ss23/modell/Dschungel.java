package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.fernuni.kurs01584.ss23.algorithmus.DschungelGenerator;
import de.fernuni.kurs01584.ss23.algorithmus.SchlangenSuche;

/**
 * Klasse, welche den Dschungel repräsentiert. Ein Dschungel besteht aus mehreren Feldern, die in einer Matrix angeordnet sind.
 */
public class Dschungel {
	private int zeilen;
	private int spalten;
	private String zeichenMenge;
	private ArrayList<ArrayList<Feld>> dschungelMatrix;
	
	//Konstruktoren
	
	/**
	 * Konstruktor zur Erstellung eines neuen Dschungels.
	 *
	 * @param zeilen Anzahl der Zeilen im Dschungel.
	 * @param spalten Anzahl der Spalten im Dschungel.
	 * @param zeichenMenge Die Zeichen, die im Dschungel verwendet werden können.
	 * @throws IllegalArgumentException wenn zeilen oder spalten kleiner oder gleich 0 sind, oder zeichenMenge leer ist.
	 */
	public Dschungel(int zeilen, int spalten,String zeichenMenge) {
		if (zeilen <= 0 || spalten <= 0 || zeichenMenge.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' , 'spalten' , keine negativen Werte annehmen.\n 'zeichenMenge' darf nicht leer sein");
		}
		this.zeilen = zeilen;
		this.spalten = spalten;
		this.zeichenMenge = zeichenMenge;
		
		this.dschungelMatrix = new ArrayList<>();
		
		for(int i = 0 ; i < zeilen; i++) {
			ArrayList<Feld> zeile = new ArrayList<>();
			
			for (int j = 0; j< spalten; j++) {
				Feld feld = new Feld(i,j);
				feld.setId(spalten);
				zeile.add(feld);
			}
			dschungelMatrix.add(zeile);
		}
		
	}
	
	//getters

	/**
	 * Gibt die Anzahl der Zeilen im Dschungel zurück.
	 *
	 * @return Die Anzahl der Zeilen.
	 */
	public int getZeilen() {
		return zeilen;
	}

	/**
	 * Gibt die Anzahl der Spalten im Dschungel zurück.
	 *
	 * @return Die Anzahl der Spalten.
	 */
	public int getSpalten() {
		return spalten;
	}
	
	/**
	 * Gibt die Zeichen zurück, die im Dschungel verwendet werden können.
	 *
	 * @return Die verwendbaren Zeichen.
	 */
	public String getZeichenMenge() {
		return zeichenMenge;
	}
	
	/**
	 * Gibt das Feld an einer bestimmten Position im Dschungel zurück.
	 *
	 * @param zNummer Die Zeilennummer des Felds.
	 * @param sNummer Die Spaltennummer des Felds.
	 * @return Das Feld an der gegebenen Position.
	 */
	public Feld getFeld(int zNummer, int sNummer) {
		return dschungelMatrix.get(zNummer).get(sNummer);
	}
	
	/**
	 * Gibt die Gesamtzahl der Felder im Dschungel zurück.
	 *
	 * @return Die Gesamtzahl der Felder.
	 */
	public int getFelderAnzahl() {
		return (zeilen * spalten);
	}
	
	/**
	 * Prüft, ob ein Feld in einer bestimmten Position im Dschungel existiert.
	 *
	 * @param zeile Die Zeilennummer des Felds.
	 * @param spalte Die Spaltennummer des Felds.
	 * @return True, wenn das Feld existiert, sonst false.
	 */
	public boolean feldExistiert(int zeile, int spalte){
		if (zeile >= 0 && zeile < zeilen ) {
			if (spalte >= 0 && spalte < spalten) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gibt die Matrix des Dschungels zurück.
	 *
	 * @return Die Matrix des Dschungels.
	 */
	public ArrayList<ArrayList<Feld>> getMatrix() {
		return dschungelMatrix;
	}

	/**
	 * Gibt das Feld mit der gegebenen ID zurück.
	 *
	 * @param s Die ID des Felds.
	 * @return Das Feld mit der gegebenen ID, oder null wenn es nicht existiert.
	 */
	public Feld getFeldById(String feldId) {
		try {
			Optional<Feld> kriteria = getFelderList().stream()
				    .filter(feld -> feld.getId().equals(feldId))
				    .findFirst();
			if (kriteria.isPresent()) {
				return kriteria.get();
			}else {
				return null;
			}
				    
			
		}catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gibt eine Liste aller Felder im Dschungel zurück.
	 *
	 * @return Eine Liste aller Felder im Dschungel.
	 */
	private ArrayList<Feld> getFelderList(){
		ArrayList<Feld> felderList = new ArrayList<Feld>();
		getMatrix().forEach(i -> i.forEach(j -> felderList.add(j)));
		return felderList;
	}

	
}
