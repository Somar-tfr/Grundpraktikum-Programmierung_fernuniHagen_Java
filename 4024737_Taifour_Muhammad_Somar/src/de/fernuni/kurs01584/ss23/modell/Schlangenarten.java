package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Die Klasse Schlangenarten repräsentiert eine Sammlung von Schlangenarten die im Dschungel gesetzt werden.
 * Sie enthält eine Liste von Schlangenarten und bietet verschiedene Methoden zur Verwaltung und Abfrage dieser Arten.
 */
public class Schlangenarten implements Iterable<Schlangenart>{
	ArrayList<Schlangenart> schlangenarten;
	int index;
	String A;
	
	/**
     * Konstruktor für die Klasse Schlangenarten.
     * Initialisiert die Liste der Schlangenarten und die Hilfsvariablen.
     */
	public Schlangenarten() {
		this.index = 0;
		this.A = "A";
		this.schlangenarten = new ArrayList<Schlangenart>();
	}
	
	/**
     * Fügt eine neue Schlangenart zur Liste hinzu und weist ihr eine eindeutige ID zu.
     *
     * @param s Die hinzuzufügende Schlangenart.
     */
	public void add(Schlangenart s) {
		String id = A + index;
		s.setID(id);
		
		this.schlangenarten.add(s);
		
		index += 1;
	}
	
	/**
     * Gibt die Gesamtanzahl aller (Schlangenart)en der Schlangenarten zurück.
     *
     * @return Die Gesamtanzahl der (Schlangenart)en.
     */
	public int getAnzahl() {
		return schlangenarten.stream().mapToInt(i -> i.getAnzahl()).sum();
	}
	
	/**
     * Gibt die Liste der Schlangenarten zurück.
     *
     * @return Die Liste der Schlangenarten.
     */
	public ArrayList<Schlangenart> getSchlangenarten(){
		return schlangenarten;
	}
	
	 /**
     * Gibt die Anzahl der Schlangenarten in der Liste zurück.
     *
     * @return Die Anzahl der Schlangenarten.
     */
	public int getSize() {
		return schlangenarten.size();
	}
	
	/**
     * Gibt eine Schlangenart aus der Liste anhand des Index zurück.
     *
     * @param i Der Index der gewünschten Schlangenart.
     * @return Die Schlangenart an der gegebenen Indexposition.
     */
	public Schlangenart getSchlangeByIndex(int i) {
		return schlangenarten.get(i);
	}
	

	/**
     * Gibt eine Schlangenart aus der Liste anhand ihrer ID zurück.
     * Falls die Schlangenart nicht gefunden wird, wird null zurückgegeben.
     *
     * @param s Die ID der gesuchten Schlangenart.
     * @return Die Schlangenart mit der gegebenen ID oder null, falls nicht gefunden.
     */
	public Schlangenart getSchlangeByArt(String s) {
		try {
			Optional<Schlangenart> a = getSchlangenarten().stream()
				    .filter(schlange -> schlange.getId().equals(s))
				    .findFirst();
			if(a.isPresent()) {
				return a.get();
			}else {
				return null;
			}
			
		}catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Override
    public Iterator<Schlangenart> iterator() {
        return schlangenarten.iterator();
    }

}
