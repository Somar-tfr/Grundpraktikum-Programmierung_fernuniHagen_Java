package de.fernuni.kurs01584.ss23.modell;

/**
 * Ein Feld wird benutzt um informationen in Dschungel Matrix zu Speichern
 * inkl. Zeile, Spalte, Zeichen, Verwendbarkeit, puknte, und ID
 * 
 * @author Somar 
 *
 */
public class Feld {
	private int zeile;
	private int spalte;
	private char zeichen;
	private int verwendbarkeit;
	private int punkte;
	private String id;
	
	/**
	 * Feld Konstruktor Kurze Version mit Zeile und Spalte eingaben.
	 *  Verwendbarket, punkte haben die wert "1", Zeichen sind leer
	 * 
	 * @param zeile : auf welche Zeile im Dschungel Matrix
	 * @param spalte : auf welche Spalte im Dschungel Matrix
	 */
	public Feld(int zeile, int spalte) {
		//super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = 0; //geändert von 1 zu null
		this.punkte = 1;
		this.zeichen = ' ';
	}
	
	/**
	 * Feld Konstruktor lange Version mit Zeile, Spalte, Verwendbarket, punkte eingaben.
	 * Zeichen sind leer.
	 * 
	 * @param zeile : auf welche Zeile im Dschungel Matrix
	 * @param spalte : auf welche Spalte im Dschungel Matrix
	 * @param verwendbarkeit : wie oft Darf dieses Feld verwendet werden
	 * @param punkte : wie viele Punkte gibt es aus
	 */
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
	
	/**
	 * Setzt die ID für ein Feld in einem Dschungel mit gegebenen Zeilen und Spalten.
	 *
	 * @param spalten Die Anzahl der Spalten im Dschungel.
	 * @throws IllegalArgumentException Wenn spalten negativ ist.
	 */
	public void setId(int spalten) {
		if (spalten <= 0) {
			throw new IllegalArgumentException("Spalten muss positiver int sein!");
		}
		int nummer = (zeile * spalten) + spalte;
		id = "F" + nummer;
	}
	
	/**
	 * Setzt das Zeichen für ein Feld im Dschungel.
	 *
	 * @param zeichen Das Zeichen, das für das Feld im Dschungel gesetzt werden soll.
	 */
	public void setZeichen(char zeichen) {
		this.zeichen = zeichen;
	}
	
	/**
	 * Setzt die Punktzahl für ein Feld im Dschungel.
	 *
	 * @param punkte Die Punktzahl, die für das Feld im Dschungel gesetzt werden soll.
	 * @throws IllegalArgumentException Wenn die übergebene Punktzahl negativ ist.
	 */
	public void setPunkte(int punkte) {
		if (punkte < 0) {
			throw new IllegalArgumentException("Punkte muss positiver int sein!");
		}
		this.punkte = punkte;
		
	}
	
	/**
	 * Setzt die Verwendbarkeit für ein Feld im Dschungel.
	 *
	 * @param verwendbarkeit Die Verwendbarkeit, die für das Feld im Dschungel gesetzt werden soll.
	 * @throws IllegalArgumentException Wenn die übergebene verwendbarkeit negativ ist.

	 */
	public void setVerwendbarkeit(int verwendbarkeit) {
		if (verwendbarkeit < 0) {
			throw new IllegalArgumentException("Verwendbarkeit muss positiver int sein!");
		}
		this.verwendbarkeit = verwendbarkeit;
	}
	
	
	//getter funktionen
	
	/**
	 * Gibt die Zeilennummer des Feldes im Dschungel zurück.
	 *
	 * @return Die Zeilennummer des Feldes.
	 */
	public int getZeile() {
		return zeile;
	}

	/**
	 * Gibt die Spaltennummer des Feldes im Dschungel zurück.
	 *
	 * @return Die Spaltennummer des Feldes.
	 */
	public int getSpalte() {
		return spalte;
	}

	/**
	 * Gibt die ID des Feldes im Dschungel zurück.
	 *
	 * @return Die ID des Feldes.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Gibt die Verwendbarkeit des Feldes im Dschungel zurück.
	 *
	 * @return Die Verwendbarkeit des Feldes.
	 */
	public int getVerwendbarkeit() {
		return verwendbarkeit;
	}
	
	/**
	 * Gibt die Punktzahl des Feldes im Dschungel zurück.
	 *
	 * @return Die Punktzahl des Feldes.
	 */
	public int getPunkte() {
		return punkte;
	}
	
	/**
	 * Gibt das Zeichen des Feldes im Dschungel zurück.
	 *
	 * @return Das Zeichen des Feldes.
	 */
	public char getZeichen() {
		return zeichen;
	}
	
	/**
	 * Verwendet das Feld im Dschungel, sofern es verwendbar ist.
	 * Wenn die Verwendbarkeit kleiner oder gleich 0 ist, wird eine {@link IllegalArgumentException} mit der Meldung "Feld nicht Verwendbar!" ausgelöst.
	 * Andernfalls wird die Verwendbarkeit um 1 verringert.
	 *
	 * @throws IllegalArgumentException Wenn das Feld nicht verwendbar ist.
	 */
	public void verwenden() {
		if (verwendbarkeit <= 0) {
			throw new IllegalArgumentException("Feld nicht Verwendbar!");
		}
		verwendbarkeit -= 1;
	}
	
	/**
	 * erhoeht die Verwendbarkeit des Feld im Dschungel.
	 */
	public void verwendbarkeitPlus() {
		verwendbarkeit += 1;
	}
	
	


}
