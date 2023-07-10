package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Nachbarschaftsstruktur {
	private String typ;
	private int parameter1;
	private int parameter2;

	
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
	
	public ArrayList<Feld> getNachbarschaft(Dschungel dschungel, Feld feld) {
		ArrayList<Feld> nachbarschaft = new ArrayList<>();
		
		//senarien basiert auf dem position des angefragten felds in dem Dschungel Variante Distanz
		int feldZeile = feld.getZeile();
		int feldSpalte = feld.getSpalte();
		
		switch (typ){ 
		
			case "Distanz":
			
		
					for ( int i = feldZeile - parameter1; i <= feldZeile + parameter1; i++) {
						for (int j = feldSpalte - parameter1; j <= feldSpalte + parameter1; j++) {
							if (!( i == feldZeile && j == feldSpalte) && dschungel.feldExistiert(i, j)) {
								nachbarschaft.add(dschungel.getFeld(i, j));
							}
						}
					}
		
					
				return nachbarschaft;
				//break;
				
			case "Sprung":
				
				
				int maxSprung = Integer.max(parameter1, parameter2);
				
					int i1 = feldZeile + parameter1;
					int i2 = feldZeile - parameter1;
					int i3 = feldZeile + parameter2;
					int i4 = feldZeile - parameter2;
					
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
				//break;
				
	  		/*default:
				return null;*/
		
	}
		
	
}
