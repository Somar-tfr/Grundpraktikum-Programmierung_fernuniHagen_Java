package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Schlangenarten {
	ArrayList<Schlangenart> schlangenarten;
	int index = 0;
	String A = "A";
	
	
	public void addSchlangenart(Schlangenart s) {
		String id = A + index;
		s.setID(id);
		
		schlangenarten.add(s);
		
		index += 1;
	}
	
	public void clearSchlangenarten() {
		schlangenarten.clear();
	}

}
