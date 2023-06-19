package de.fernuni.kurs01584.ss23.modell;

public class Feld {
	private int zeile;
	private int spalte;
	private char zeichen;
	private int verwendbarkeit;
	private int punkte;
	private String id;
	
	public Feld(int zeile, int spalte) {
		//super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = 1;
		this.punkte = 1;
		this.zeichen = ' ';
	}
	public Feld(int zeile, int spalte, int verwendbarkeit, int punkte) {
		//super();
		if (zeile < 0 || spalte < 0 || verwendbarkeit < 0 || punkte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' , 'spalte' , 'verwendbarket' , 'punlte'  keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = verwendbarkeit;
		this.punkte = punkte;
		
	}

	//setter funktionen
	public void setId(int spalten) {
		
		int nummer = (zeile * spalten) + spalte;
		id = "F" + nummer;
	}
	
	public void setZeichen(char eingabeZeichen) {
		zeichen = eingabeZeichen;
	}
	
	
	//getter funktionen
	public int getZeile() {
		return zeile;
	}

	public int getSpalte() {
		return spalte;
	}

	public String getId() {
		return id;
	}
	
	public int getVerwendbarkeit() {
		return verwendbarkeit;
	}
	
	public int getPunkte() {
		return punkte;
	}
	
	public char getZeichen() {
		return zeichen;
	}
	
	public void verwenden() {
		if (verwendbarkeit > 0) {
			verwendbarkeit -= 1;
		}else {
			System.out.println("nicht verwendbar?"); //?
		}
	}


}
