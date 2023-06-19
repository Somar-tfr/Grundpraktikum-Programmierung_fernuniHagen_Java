package de.fernuni.kurs01584.ss23.modell;

import java.util.ArrayList;

public class Nachbarschaftsstruktur {
	private String typ;
	private int parameter;
	
	Nachbarschaftsstruktur(String typ, int parameter){
		if (parameter < 1 || parameter > 2 || typ.length() == 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Nachbarschaftsstruktur' darf die Attribut 'parameter' keine negativen Werte und nicht höher als 2 annehmen.\n 'typ' darf nicht leer sein");
			
			
		}
		if (!(typ.equals("Distanz") || typ.equals("Sprung"))) {
			throw new IllegalArgumentException(
					"'typ' darf entweder 'Distanz' oder 'Sprung' sein.");
		}
		this.typ = typ;
		this.parameter = parameter;
		
	}
	
	public ArrayList<Feld> getNachbarschaft(Dschungel name, Feld feld) {
		ArrayList<Feld> nachbarschaft = new ArrayList<>();
		switch (typ){ 
		
			case "Distanz":
			
				int laengeZumRand = parameter - 1;
				
				boolean senarioInnereFelder 	  = (feld.getSpalte() > laengeZumRand) && (feld.getZeile()> laengeZumRand) && (feld.getSpalte()< name.getSpalten() - laengeZumRand) && (feld.getZeile()< name.getZeilen() - laengeZumRand);
				boolean senarioObereInnereFelder  = (feld.getZeile() == laengeZumRand) && (feld.getSpalte() > laengeZumRand) && (feld.getSpalte()< name.getSpalten() - laengeZumRand);
				boolean senarioUntereInnereFelder = (feld.getZeile() == name.getZeilen() - laengeZumRand) && (feld.getSpalte() > laengeZumRand) && (feld.getSpalte()< name.getSpalten() - laengeZumRand);
				boolean senarioLinkeInnereFelder  = (feld.getSpalte() == laengeZumRand) && (feld.getZeile()> laengeZumRand) && (feld.getZeile()< name.getZeilen() - laengeZumRand);
				boolean	senarioRechteInnereFelder = (feld.getSpalte() == name.getSpalten() - laengeZumRand) && (feld.getZeile()> laengeZumRand) && (feld.getZeile()< name.getZeilen() - laengeZumRand);
				boolean senarioObenLinkeFelder 	  = (feld.getZeile() ==  laengeZumRand) && (feld.getSpalte() == laengeZumRand);
				boolean senarioObenRechteFelder   = (feld.getZeile() == laengeZumRand) && (feld.getSpalte() == name.getSpalten() - laengeZumRand);
				boolean senarioUntenLinkeFelder   = (feld.getZeile() == name.getZeilen() - laengeZumRand) && (feld.getSpalte() ==  laengeZumRand);
				boolean senarioUntenRechteFelder  = (feld.getZeile() == name.getZeilen() - laengeZumRand) && (feld.getSpalte() == name.getSpalten() - laengeZumRand);
						
				if (senarioInnereFelder) {
					for ( int i = feld.getZeile() - parameter; i <= feld.getZeile() + parameter; i++) {
						for (int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + parameter; j++) {
							
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
		
				}else if(senarioObereInnereFelder) {
					for ( int i = feld.getZeile() - laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for (int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + parameter; j++) {
							
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioUntereInnereFelder) {
					for ( int i = feld.getZeile() + laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for (int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + parameter; j++) {
							
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioLinkeInnereFelder) {
						for ( int i = feld.getZeile() - parameter; i <= feld.getZeile() + parameter; i++) {
							for (int j = feld.getSpalte() - laengeZumRand; j <= feld.getSpalte() + parameter; j++) {
								
								nachbarschaft.add(name.getFeld(i, j));
							}
					}
				}else if(senarioRechteInnereFelder) {
					for ( int i = feld.getZeile() - laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for (int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + laengeZumRand; j++) {
							
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioObenLinkeFelder) {
					for(int i = feld.getZeile() - laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for(int j = feld.getSpalte() - laengeZumRand; j <= feld.getSpalte() + parameter; j++) {
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioObenRechteFelder) {
					for(int i = feld.getZeile() - laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for(int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + laengeZumRand; j++) {
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioUntenLinkeFelder) {
					for(int i = feld.getZeile() + laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for(int j = feld.getSpalte() - laengeZumRand; j <= feld.getSpalte() + parameter; j++) {
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}else if(senarioUntenRechteFelder) {
					for(int i = feld.getZeile() + laengeZumRand; i <= feld.getZeile() + parameter; i++) {
						for (int j = feld.getSpalte() - parameter; j <= feld.getSpalte() + laengeZumRand; j++) {
							nachbarschaft.add(name.getFeld(i, j));
						}
					}
				}
					
				return nachbarschaft;
				//break;
				
			case "Spalte":
				return null;//nachbarschaft;
				//break;
			default:
				return null; //?
		}
	}
		
	
}
