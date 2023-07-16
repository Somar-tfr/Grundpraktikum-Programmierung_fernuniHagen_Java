package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;
import java.util.Iterator;

public class Schlangenarten implements Iterable<Schlangenart>{
	ArrayList<Schlangenart> schlangenarten;
	int index;
	String A;
	
	public Schlangenarten() {
		this.index = 0;
		this.A = "A";
		this.schlangenarten = new ArrayList<Schlangenart>();
	}
	
	
	public void add(Schlangenart s) {
		String id = A + index;
		s.setID(id);
		
		this.schlangenarten.add(s);
		
		index += 1;
	}
	
	public int getAnzahl() {
		return schlangenarten.stream().mapToInt(i -> i.getAnzahl()).sum();
	}
	
	public ArrayList<Schlangenart> getSchlangenarten(){
		return schlangenarten;
	}
	
	public int getSize() {
		return schlangenarten.size();
	}
	
	public Schlangenart getSchlangeByIndex(int i) {
		return schlangenarten.get(i);
	}
	
	public void clearSchlangenarten() {
		schlangenarten.clear();
	}
	public void print() {
		schlangenarten.forEach(i -> System.out.println(i));
	}
	
	@Override
    public Iterator<Schlangenart> iterator() {
        return schlangenarten.iterator();
    }

}
