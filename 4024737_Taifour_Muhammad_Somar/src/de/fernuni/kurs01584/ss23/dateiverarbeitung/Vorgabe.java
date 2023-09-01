package de.fernuni.kurs01584.ss23.dateiverarbeitung;

import de.fernuni.kurs01584.ss23.modell.*;
import de.fernuni.kurs01584.ss23.algorithmus.*;
import java.io.*;
import java.util.*;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class Vorgabe {
	private String eingabeDatei;
	private String ausgabeDatei;
	private SchlangenSuche loesung;
	private Dschungel hauptDschungel;
	private Schlangenarten hauptSchlangenarten;
	
	private double zeitVorgabe;//immer in ms
	private double zeitAbgabe;//immer in ms
	private String zeitVorgabeEinheit;
	
	private boolean erfolgLoese;
	private boolean erfolgErzeuge;
	private ArrayList<Schlange> xmlSchlangenloesung;
	
	/**
	 * Konstruktur für ein objekt Vorgabe
	 * für den erzeugung und lösung eines sucheschlange
	 * 
	 * @param eingabe : ein pfad zu einem xml dokument
	 * @param ausgabe : ein pfad zu einem xml dokument mit dem gewünschten ausgabe
	 */
	public Vorgabe(String eingabe, String ausgabe){
		this.eingabeDatei = eingabe;
		this.ausgabeDatei = ausgabe;
		this.erfolgLoese = false;
		this.erfolgErzeuge = false;
	}	
	
	/**
	 * Konstruktur für ein objekt Vorgabe
	 * für den früfungsfall 
	 * 
	 * @param eingabe : ein pfad zu einem xml dokument
	 */
	public Vorgabe(String eingabe){
		this.eingabeDatei = eingabe;
		
		this.erfolgLoese = false;
		this.erfolgErzeuge = false;
	}	
	
	
	
	/**
	 * Liest Daten aus einer XML-Datei ein und verarbeitet sie, um den Dschungel und
	 * Informationen über Schlangen für eine Schlangen-Suchsimulation einzurichten.
	 * Anschließend führt sie die Schlangensuche durch und berechnet die dafür benötigte Zeit.
	 *
	 * @throws JDOMException Falls ein Fehler beim Parsen der XML-Datei auftritt.
	 * @throws IOException Falls ein Ein-/Ausgabefehler beim Lesen der XML-Datei auftritt.
	 */
	public void readLoese() throws JDOMException, IOException {
		try {
			
			String name = getEingabeDatei();
			//Dokument builder erstellen
			SAXBuilder reader = new SAXBuilder();		
			
			//Parse den XML in einem Dokument Objekt
			
			Document document = reader.build(new File(name));
			
			//auf Elemente und Daten im Dokument zugreifen
			Element rootElement = document.getRootElement();
			//Zeit
			Element zeitElement = rootElement.getChild("Zeit");
			Attribute einheit = zeitElement.getAttribute("einheit");
			Element vorgabeElement = zeitElement.getChild("Vorgabe");
			double vorgabe = Double.parseDouble(vorgabeElement.getValue());
			
			setEinheit(einheit.getValue());
			vorgabe = leseZeitKonvertieren(vorgabe, getEinheit());
			
			
			setZeitVorgabe(vorgabe);
			
			//dschungel
	        Element dschungelElement = rootElement.getChild("Dschungel");
	        Attribute dschungelZeilen = dschungelElement.getAttribute("zeilen");
	        int dschungelZeilenInt = dschungelZeilen.getIntValue();
	        
	        Attribute dschungelSpalten = dschungelElement.getAttribute("spalten");
	        int dschungelSpaltenInt = dschungelSpalten.getIntValue();
	        
	        Attribute dschungelZeichen = dschungelElement.getAttribute("zeichen");
	        String dschungelZeichenStr = dschungelZeichen.getValue();
	        
	        
	        //schlangenarten
	        Element schlangenartenElement = rootElement.getChild("Schlangenarten");
	        
	        List<Element> schlangenElement = schlangenartenElement.getChildren();
	        
	        Schlangenarten schlangenarten = new Schlangenarten();
	        schlangenElement.forEach(i -> {
	        	Attribute schlangenartId = i.getAttribute("id");
	        	String schlangenartIdStr = schlangenartId.getValue();
	        	
	        	Attribute schlangenartPunkte = i.getAttribute("punkte");
				int schlangenartPunkteInt = 0;
				try {
					schlangenartPunkteInt = schlangenartPunkte.getIntValue();
				} catch (DataConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        	
	        	Attribute schlangenartAnzahl = i.getAttribute("anzahl");
					int schlangenartAnzahlInt = 0;
					try {
						schlangenartAnzahlInt = schlangenartAnzahl.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	        	Element zeichenkettenElement = i.getChild("Zeichenkette");
	        	String zeichenkette = zeichenkettenElement.getValue();
	        	
	        	Element nachbarstrkElement = i.getChild("Nachbarschaftsstruktur");
	        	Attribute typ = nachbarstrkElement.getAttribute("typ");
	        	String typString = typ.getValue();
	        	
	        	List<Element> parameterElemente = nachbarstrkElement.getChildren();
	        	Nachbarschaftsstruktur nchbrsstr = null;
	        	
	        	if (parameterElemente.size() == 1){
	        		int par = 0;
					try {
						par = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		 nchbrsstr = new Nachbarschaftsstruktur(typString, par);
	        	}else if(parameterElemente.size() == 2) {
	        		int par1 = 0;
					try {
						par1 = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		int par2 = 0;
					try {
						par2 = parameterElemente.get(1).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		nchbrsstr = new Nachbarschaftsstruktur(typString, par1, par2);
	        	}
	        	
	        	
	        	
	        	
	        	
	        	 
	        	Schlangenart schlangenart = new Schlangenart(zeichenkette, nchbrsstr);
	        	schlangenart.setID(schlangenartIdStr);
	        	schlangenart.setAnzahl(schlangenartAnzahlInt);
	        	schlangenart.setPunkte(schlangenartPunkteInt);
	        	
	        	 
	        	schlangenarten.add(schlangenart);
	        
	        });
	        setSchlangenarten(schlangenarten);
	        //muss schauen dass für schlangen mit anzahl mehr als 1 entsprechend weitere loop in generator durchzuführen
	        if (dschungelElement.getChildren().size() == 0){
	        	DschungelGenerator dschungelGenerator = new DschungelGenerator(dschungelZeilenInt, dschungelSpaltenInt,dschungelZeichenStr, schlangenarten );
	        	dschungelGenerator.erzeugeDschungel();
		        Dschungel dschungel = dschungelGenerator.erzeugeDschungel();
		        
		        setDschungel(dschungel);
	        } else {
	        	Dschungel dschungel = new Dschungel(dschungelZeilenInt, dschungelSpaltenInt, dschungelZeichenStr);
	        	List<Element> dschungelkinder = dschungelElement.getChildren();
	        	dschungelkinder.forEach ( i -> {
	        		Attribute FeldId = i.getAttribute("id");
	        		String FeldIdStr = FeldId.getValue();
	        		
	        		Attribute feldZeile = i.getAttribute("zeile");
	        		int feldZeileInt = 0;
					try {
						feldZeileInt = feldZeile.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldSpalte = i.getAttribute("spalte");
	        		int feldSpalteInt = 0;
					try {
						feldSpalteInt = feldSpalte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldVerwendbarkeit = i.getAttribute("verwendbarkeit");
	        		int feldVerwendbarkeitInt = 0;
					try {
						feldVerwendbarkeitInt = feldVerwendbarkeit.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldPunkte = i.getAttribute("punkte");
	        		int feldPunkteInt = 0;
					try {
						feldPunkteInt = feldPunkte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		char FeldZeichen = i.getValue().charAt(0);
	        		
	        		Feld mowgli = dschungel.getFeld(feldZeileInt, feldSpalteInt);
	        		mowgli.setId(dschungelSpaltenInt);
	        		mowgli.setPunkte(feldPunkteInt);
	        		mowgli.setVerwendbarkeit(feldVerwendbarkeitInt);
	        		mowgli.setZeichen(FeldZeichen);
	        		
	        		
	        	});
	        	setDschungel(dschungel);
	        	 
	        }
	        
	        
	        SchlangenSuche loesung = new SchlangenSuche(getDschungel(),getSchlangenarten(), getZeitVorgabe() );
	        loesung.sucheSchlange();
	        double zeitAbg = loesung.getAbgabeZeit();
	        /*zeitAbg = leseZeitKonvertieren(zeitAbg, getEinheit());
	        String zeitEinheit = einheit.getValue();*/
	        //setEinheit(zeitEinheit);
	        setZeitAbgabe(zeitAbg);
	        setLoesung(loesung);
	        
	        
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	/**
	 * Liest Daten aus einer XML-Datei ein und erzeugt den Dschungel und Schlangeninformationen
	 * für eine Schlangen-Suchsimulation.
	 *
	 * @throws JDOMException Falls ein Fehler beim Parsen der XML-Datei auftritt.
	 * @throws IOException Falls ein Ein-/Ausgabefehler beim Lesen der XML-Datei auftritt.
	 */
	public void readErzeuge() throws JDOMException, IOException {
		try {
			
			String name = getEingabeDatei();
			//Dokument builder erstellen
			SAXBuilder reader = new SAXBuilder();		
			
			//Parse den XML in einem Dokument Objekt
			
			Document document = reader.build(new File(name));
			
			//auf Elemente und Daten im Dokument zugreifen
			Element rootElement = document.getRootElement();
			//Zeit
			Element zeitElement = rootElement.getChild("Zeit");
			Attribute einheit = zeitElement.getAttribute("einheit");
			Element vorgabeElement = zeitElement.getChild("Vorgabe");
			double vorgabe = Double.parseDouble(vorgabeElement.getValue());
			setEinheit(einheit.getValue());
			vorgabe = leseZeitKonvertieren(vorgabe, getEinheit());
			
			
			setZeitVorgabe(vorgabe);
			
			//dschungel
	        Element dschungelElement = rootElement.getChild("Dschungel");
	        Attribute dschungelZeilen = dschungelElement.getAttribute("zeilen");
	        int dschungelZeilenInt = dschungelZeilen.getIntValue();
	        
	        Attribute dschungelSpalten = dschungelElement.getAttribute("spalten");
	        int dschungelSpaltenInt = dschungelSpalten.getIntValue();
	        
	        Attribute dschungelZeichen = dschungelElement.getAttribute("zeichen");
	        String dschungelZeichenStr = dschungelZeichen.getValue();
	        
	        //schlangenarten
	        Element schlangenartenElement = rootElement.getChild("Schlangenarten");
	        
	        List<Element> schlangenElement = schlangenartenElement.getChildren();
	        
	        Schlangenarten schlangenarten = new Schlangenarten();
	        schlangenElement.forEach(i -> {
	        	Attribute schlangenartId = i.getAttribute("id");
	        	String schlangenartIdStr = schlangenartId.getValue();
	        	
	        	Attribute schlangenartPunkte = i.getAttribute("punkte");
				int schlangenartPunkteInt = 0;
				try {
					schlangenartPunkteInt = schlangenartPunkte.getIntValue();
				} catch (DataConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        	
	        	Attribute schlangenartAnzahl = i.getAttribute("anzahl");
					int schlangenartAnzahlInt = 0;
					try {
						schlangenartAnzahlInt = schlangenartAnzahl.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	        	Element zeichenkettenElement = i.getChild("Zeichenkette");
	        	String zeichenkette = zeichenkettenElement.getValue();
	        	
	        	Element nachbarstrkElement = i.getChild("Nachbarschaftsstruktur");
	        	Attribute typ = nachbarstrkElement.getAttribute("typ");
	        	String typString = typ.getValue();
	        	
	        	List<Element> parameterElemente = nachbarstrkElement.getChildren();
	        	Nachbarschaftsstruktur nchbrsstr = null;
	        	
	        	if (parameterElemente.size() == 1){
	        		int par = 0;
					try {
						par = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		 nchbrsstr = new Nachbarschaftsstruktur(typString, par);
	        	}else if(parameterElemente.size() == 2) {
	        		int par1 = 0;
					try {
						par1 = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		int par2 = 0;
					try {
						par2 = parameterElemente.get(1).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		nchbrsstr = new Nachbarschaftsstruktur(typString, par1, par2);
	        	}
	        	
	        	
	        	
	        	
	        	 
	        	Schlangenart schlangenart = new Schlangenart(zeichenkette, nchbrsstr);
	        	schlangenart.setID(schlangenartIdStr);
	        	schlangenart.setAnzahl(schlangenartAnzahlInt);
	        	schlangenart.setPunkte(schlangenartPunkteInt);
	        	
	        	 
	        	schlangenarten.add(schlangenart);
	        });
	        setSchlangenarten(schlangenarten);
	        if (dschungelElement.getChildren().size() == 0){
	        	DschungelGenerator dschungelGenerator = new DschungelGenerator(dschungelZeilenInt, dschungelSpaltenInt,dschungelZeichenStr, schlangenarten );
		        Dschungel dschungel = dschungelGenerator.erzeugeDschungel();
		        setDschungel(dschungel);
	        } else {
	        	Dschungel dschungel = new Dschungel(dschungelZeilenInt, dschungelSpaltenInt, dschungelZeichenStr);
	        	List<Element> dschungelkinder = dschungelElement.getChildren();
	        	dschungelkinder.forEach ( i -> {
	        		Attribute FeldId = i.getAttribute("id");
	        		String FeldIdStr = FeldId.getValue();
	        		
	        		Attribute feldZeile = i.getAttribute("zeile");
	        		int feldZeileInt = 0;
					try {
						feldZeileInt = feldZeile.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldSpalte = i.getAttribute("spalte");
	        		int feldSpalteInt = 0;
					try {
						feldSpalteInt = feldSpalte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldVerwendbarkeit = i.getAttribute("verwendbarkeit");
	        		int feldVerwendbarkeitInt = 0;
					try {
						feldVerwendbarkeitInt = feldVerwendbarkeit.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldPunkte = i.getAttribute("punkte");
	        		int feldPunkteInt = 0;
					try {
						feldPunkteInt = feldPunkte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		char FeldZeichen = i.getValue().charAt(0);
	        		
	        		Feld mowgli = dschungel.getFeld(feldZeileInt, feldSpalteInt);
	        		mowgli.setId(dschungelSpaltenInt);
	        		mowgli.setPunkte(feldPunkteInt);
	        		mowgli.setVerwendbarkeit(feldVerwendbarkeitInt);
	        		mowgli.setZeichen(FeldZeichen);
	        		
	        		
	        	});
	        	setDschungel(dschungel);
	        }
	       
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Schreibt die Ergebnisse der Schlangen-Suchsimulation in eine XML-Datei.
	 *
	 * @throws FileNotFoundException Falls die Ausgabedatei nicht gefunden werden kann.
	 * @throws IOException Falls ein Ein-/Ausgabefehler beim Schreiben der XML-Datei auftritt.
	 */
	public void writeLoese() throws FileNotFoundException, IOException {
		String name = getAusgabeDatei();
		
        String currentDir = System.getProperty("user.dir");

        String dateiPfad = /*currentDir  + "/" +*/ name ;
		
		
		Document document = new Document();
		Element root = new Element("Schlangenjagd");
		
		document.setRootElement(root);
		
		//Zeit Element
		Element zeit = new Element("Zeit");
		String eingabeEinheit = getEinheit();
        zeit.setAttribute("einheit", getEinheit());

        Element vorgabe = new Element("Vorgabe");
        
        double vorgabeZeitEinheitlich = schreibZeitKonvertieren(getZeitVorgabe(), getEinheit());
        String zeitVorgabeStr = String.valueOf(vorgabeZeitEinheitlich);
        vorgabe.setText(zeitVorgabeStr);
        zeit.addContent(vorgabe);
        
        Element abgabe = new Element("Abgabe");
        
        double abgabeZeitEinheitlich = schreibZeitKonvertieren(getZeitAbgabe(), getEinheit());
        
        
        String zeitAbgabeStr = String.valueOf(abgabeZeitEinheitlich);
        abgabe.setText(zeitAbgabeStr);
        zeit.addContent(abgabe);
       
        root.addContent(zeit);
        
        //dschungel element
        
        Element dschungel = new Element("Dschungel");
        String zeilenStr = String.valueOf(getDschungel().getZeilen());
        String spaltenStr = String.valueOf(getDschungel().getSpalten());
        String zeichen = getDschungel().getZeichenMenge();
        dschungel.setAttribute("zeilen", zeilenStr);
        dschungel.setAttribute("spalten", spaltenStr);
        dschungel.setAttribute("zeichen", zeichen);
        
        int zeilen = getDschungel().getZeilen();
        int spalten = getDschungel().getSpalten();
        
        //feld elemet
        for(int i = 0;  i < zeilen; i++) {
        	for (int j = 0; j < spalten; j++) {
        		Feld feld = getDschungel().getFeld(i, j);
        		
        		Element feldE = new Element("Feld");
        		feldE.setAttribute("id", feld.getId());
        		feldE.setAttribute("zeile", String.valueOf(feld.getZeile()));
        		feldE.setAttribute("spalte", String.valueOf(feld.getSpalte()));
        		feldE.setAttribute("verwendbarkeit", String.valueOf(feld.getVerwendbarkeit()));
        		feldE.setAttribute("punkte", String.valueOf(feld.getPunkte()));
        		
        		feldE.setText(String.valueOf(feld.getZeichen()));
        		
        		dschungel.addContent(feldE);
        		
        	}
        }
        root.addContent(dschungel);
        //Schlangenarten
        Element schlangenartenE = new Element("Schlangenarten");
        Schlangenarten schlangenarten = getSchlangenarten();
        for (int i = 0; i < schlangenarten.getSize(); i++) {
        	Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
        	
        	Element schlangenartE = new Element ("Schlangenart");
        	schlangenartE.setAttribute("id", schlangenart.getId());
        	schlangenartE.setAttribute("punkte", String.valueOf(schlangenart.getPunkte()));
        	schlangenartE.setAttribute("anzahl", String.valueOf(schlangenart.getAnzahl()));
        	
        	//Zecihenkette
        	Element zeichenketteE = new Element("Zeichenkette");
        	zeichenketteE.setText(schlangenart.getZeichenkette());
        	schlangenartE.addContent(zeichenketteE);
        	
        	//Nachbarschaftsstruktur
        	Element nachbE = new Element("Nachbarschaftsstruktur");
        	nachbE.setAttribute("typ", schlangenart.getNachbarschaftsstruktur().getTyp());
        	//werte
        	//PROBLEM HIER
        	Element par1E = new Element ("Parameter");
        	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert1()));
        	nachbE.addContent(par1E);
        	
        	if (schlangenart.getNachbarschaftsstruktur().getTyp().equals("Sprung") ) {
        		Element par2E = new Element ("Parameter");
            	par2E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert2()));
            	nachbE.addContent(par2E);
        	}
        	schlangenartE.addContent(nachbE);
        	
        	schlangenartenE.addContent(schlangenartE);
        	
        
        }
        
        root.addContent(schlangenartenE);
        
      //loesung
        Element schlangenE = new Element ("Schlangen");
        
        SchlangenSuche schlagnensuche = getLoesung();
        ArrayList<Schlange> schlangen = schlagnensuche.getLoesung();
        if (schlangen.size() > 0) {
        	setErfolgLoeseTrue();
        }
        for (int i = 0; i < schlagnensuche.getAnzahl(); i++) {
        	Schlange schlange = schlangen.get(i);
        	Element schlangeE = new Element("Schlange");
        	schlangeE.setAttribute("art", schlange.getArt());
        	Schlangenglied glied = schlange.getKopf();
        	if (glied.getFeld() != null) {
    			Element gliedE = new Element("Schlangenglied");
    			gliedE.setAttribute("feld", (glied.getFeld().getId()));
    			schlangeE.addContent(gliedE);
    		}
        	while ((glied.getNext() != null)) {
    			glied = glied.getNext();
    			Element gliedE = new Element("Schlangenglied");
    			gliedE.setAttribute("feld", (glied.getFeld().getId()));
    			schlangeE.addContent(gliedE);
    			
    		};
    		schlangenE.addContent(schlangeE);
        	
        }
        root.addContent(schlangenE);
		//doctype
		DocType dt = new DocType("Schlangenjagd", "schlangenjagd.dtd");
        document.setDocType(dt);
        
        try {
        	XMLOutputter outputter = new XMLOutputter();
    		outputter.setFormat(Format.getPrettyFormat());
    		// Create parent directories if they don't exist//HIER WIRD ANGEPASST PROBLEM 27.08.2023
    		File file = new File(getAusgabeDatei());
            file.getParentFile().mkdirs();
            
    		outputter.output(document, new FileOutputStream(file));
        }catch (IOException e){
        	e.printStackTrace();
        }
		
		
	}
	
	/**
	 * Schreibt die erzeugten Dschungel- und Schlangeninformationen in eine XML-Datei.
	 *
	 * @throws FileNotFoundException Falls die Ausgabedatei nicht gefunden werden kann.
	 * @throws IOException Falls ein Ein-/Ausgabefehler beim Schreiben der XML-Datei auftritt.
	 */
	public void writeErzeuge() throws FileNotFoundException, IOException {
		String name = getAusgabeDatei();
		
        /*String currentDir = System.getProperty("user.dir");*/

        String dateiPfad = /*currentDir  + "/" +*/ name ;
		
		
		Document document = new Document();
		Element root = new Element("Schlangenjagd");
		
		document.setRootElement(root);
		
		//Zeit Element
		Element zeit = new Element("Zeit");
        zeit.setAttribute("einheit", String.valueOf(getEinheit()));

        Element vorgabe = new Element("Vorgabe");
        double vorgabeZeitEinheitlich = schreibZeitKonvertieren(getZeitVorgabe(), getEinheit());
        String zeitVorgabeStr = String.valueOf(vorgabeZeitEinheitlich);
        vorgabe.setText(zeitVorgabeStr);
        zeit.addContent(vorgabe);
        root.addContent(zeit);
        
        Element dschungel = new Element("Dschungel");
        String zeilenStr = String.valueOf(getDschungel().getZeilen());
        String spaltenStr = String.valueOf(getDschungel().getSpalten());
        String zeichen = getDschungel().getZeichenMenge();
        dschungel.setAttribute("zeilen", zeilenStr);
        dschungel.setAttribute("spalten", spaltenStr);
        dschungel.setAttribute("zeichen", zeichen);
        
        int zeilen = getDschungel().getZeilen();
        int spalten = getDschungel().getSpalten();
        
        //feld elemet
        for(int i = 0;  i < zeilen; i++) {
        	for (int j = 0; j < spalten; j++) {
        		Feld feld = getDschungel().getFeld(i, j);
        		
        		Element feldE = new Element("Feld");
        		feldE.setAttribute("id", feld.getId());
        		feldE.setAttribute("zeile", String.valueOf(feld.getZeile()));
        		feldE.setAttribute("spalte", String.valueOf(feld.getSpalte()));
        		feldE.setAttribute("verwendbarkeit", String.valueOf(feld.getVerwendbarkeit()));
        		feldE.setAttribute("punkte", String.valueOf(feld.getPunkte()));
        		
        		feldE.setText(String.valueOf(feld.getZeichen()));
        		
        		dschungel.addContent(feldE);
        		
        	}
        }
        root.addContent(dschungel);
        //Schlangenarten
        Element schlangenartenE = new Element("Schlangenarten");
        Schlangenarten schlangenarten = getSchlangenarten();
        for (int i = 0; i < schlangenarten.getSize(); i++) {
        	Schlangenart schlangenart = schlangenarten.getSchlangeByIndex(i);
        	
        	Element schlangenartE = new Element ("Schlangenart");
        	schlangenartE.setAttribute("id", schlangenart.getId());
        	schlangenartE.setAttribute("punkte", String.valueOf(schlangenart.getPunkte()));
        	schlangenartE.setAttribute("anzahl", String.valueOf(schlangenart.getAnzahl()));
        	
        	//Zecihenkette
        	Element zeichenketteE = new Element("Zeichenkette");
        	zeichenketteE.setText(schlangenart.getZeichenkette());
        	schlangenartE.addContent(zeichenketteE);
        	
        	//Nachbarschaftsstruktur
        	Element nachbE = new Element("Nachbarschaftsstruktur");
        	nachbE.setAttribute("typ", schlangenart.getNachbarschaftsstruktur().getTyp());
        	//werte
        	Element par1E = new Element ("Parameter");
        	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert1()));
        	nachbE.addContent(par1E);
        	if (schlangenart.getNachbarschaftsstruktur().getTyp() == "Sprung") {
        		Element par2E = new Element ("Parameter");
            	par2E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert2()));
            	nachbE.addContent(par2E);
        	}
        	schlangenartE.addContent(nachbE);
        	
        	schlangenartenE.addContent(schlangenartE);
        	
        
        }
        
        root.addContent(schlangenartenE);
       
		//doctype
		DocType dt = new DocType("Schlangenjagd", "schlangenjagd.dtd");
        document.setDocType(dt);
        
        try {
        	XMLOutputter outputter = new XMLOutputter();
    		outputter.setFormat(Format.getPrettyFormat());
            File file = new File(dateiPfad);
         // Create parent directories if they don't exist
            file.getParentFile().mkdirs();
    		outputter.output(document, new FileOutputStream(file));
    		setErfolgErzeugeTrue();
        }catch (IOException e){
        	e.printStackTrace();
        }
		
		
	}
	
	/**
	 * Liest die Informationen aus der Eingabe-XML-Datei und überprüft sie auf Gültigkeit.
	 *
	 * @throws JDOMException Falls ein Fehler beim Parsen der XML-Datei auftritt.
	 * @throws IOException Falls ein Ein-/Ausgabefehler beim Lesen der XML-Datei auftritt.
	 */
	public void readPruefe() throws JDOMException, IOException {
		
		
		try {
			
			String name = getEingabeDatei();
			//Dokument builder erstellen
			SAXBuilder reader = new SAXBuilder();		
			
			//Parse den XML in einem Dokument Objekt
			
			Document document = reader.build(new File(name));
			
			//auf Elemente und Daten im Dokument zugreifen
			Element rootElement = document.getRootElement();
			//Zeit
			Element zeitElement = rootElement.getChild("Zeit");
			Attribute einheit = zeitElement.getAttribute("einheit");
			Element vorgabeElement = zeitElement.getChild("Vorgabe");
			double vorgabe = Double.parseDouble(vorgabeElement.getValue());
			
			setEinheit(einheit.getValue());
			
			vorgabe = leseZeitKonvertieren(vorgabe, getEinheit());
			setZeitVorgabe(vorgabe);
			System.out.println(vorgabe);
			System.out.println(zeitElement.getChildren());
			
			Element abgabeElement = zeitElement.getChild("Abgabe");
			double abgabe = Double.parseDouble(abgabeElement.getValue());
			abgabe = leseZeitKonvertieren(abgabe, getEinheit());
			
			setZeitAbgabe(abgabe);
			
			//dschungel
	        Element dschungelElement = rootElement.getChild("Dschungel");
	        Attribute dschungelZeilen = dschungelElement.getAttribute("zeilen");
	        int dschungelZeilenInt = dschungelZeilen.getIntValue();
	        
	        Attribute dschungelSpalten = dschungelElement.getAttribute("spalten");
	        int dschungelSpaltenInt = dschungelSpalten.getIntValue();
	        
	        Attribute dschungelZeichen = dschungelElement.getAttribute("zeichen");
	        String dschungelZeichenStr = dschungelZeichen.getValue();
	        
	        
	        //schlangenarten
	        Element schlangenartenElement = rootElement.getChild("Schlangenarten");
	        
	        List<Element> schlangenElement = schlangenartenElement.getChildren();
	        
	        Schlangenarten schlangenarten = new Schlangenarten();
	        schlangenElement.forEach(i -> {
	        	Attribute schlangenartId = i.getAttribute("id");
	        	String schlangenartIdStr = schlangenartId.getValue();
	        	
	        	Attribute schlangenartPunkte = i.getAttribute("punkte");
				int schlangenartPunkteInt = 0;
				try {
					schlangenartPunkteInt = schlangenartPunkte.getIntValue();
				} catch (DataConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        	
	        	Attribute schlangenartAnzahl = i.getAttribute("anzahl");
					int schlangenartAnzahlInt = 0;
					try {
						schlangenartAnzahlInt = schlangenartAnzahl.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	        	Element zeichenkettenElement = i.getChild("Zeichenkette");
	        	String zeichenkette = zeichenkettenElement.getValue();
	        	
	        	Element nachbarstrkElement = i.getChild("Nachbarschaftsstruktur");
	        	Attribute typ = nachbarstrkElement.getAttribute("typ");
	        	String typString = typ.getValue();
	        	
	        	List<Element> parameterElemente = nachbarstrkElement.getChildren();
	        	Nachbarschaftsstruktur nchbrsstr = null;
	        	
	        	if (parameterElemente.size() == 1){
	        		int par = 0;
					try {
						par = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		 nchbrsstr = new Nachbarschaftsstruktur(typString, par);
	        	}else if(parameterElemente.size() == 2) {
	        		int par1 = 0;
					try {
						par1 = parameterElemente.get(0).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		int par2 = 0;
					try {
						par2 = parameterElemente.get(1).getAttribute("wert").getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
	        		nchbrsstr = new Nachbarschaftsstruktur(typString, par1, par2);
	        	}
	        	
	        	
	        	
	        	
	        	
	        	 
	        	Schlangenart schlangenart = new Schlangenart(zeichenkette, nchbrsstr);
	        	schlangenart.setID(schlangenartIdStr);
	        	schlangenart.setAnzahl(schlangenartAnzahlInt);
	        	schlangenart.setPunkte(schlangenartPunkteInt);
	        	
	        	 
	        	schlangenarten.add(schlangenart);
	        
	        });
	        setSchlangenarten(schlangenarten);
	        //muss schauen dass für schlangen mit anzahl mehr als 1 entsprechend weitere loop in generator durchzuführen
	        if (dschungelElement.getChildren().size() == 0){
	        	DschungelGenerator dschungelGenerator = new DschungelGenerator(dschungelZeilenInt, dschungelSpaltenInt,dschungelZeichenStr, schlangenarten );
	        	dschungelGenerator.erzeugeDschungel();
		        Dschungel dschungel = dschungelGenerator.erzeugeDschungel();
		        
		        setDschungel(dschungel);
	        } else {
	        	Dschungel dschungel = new Dschungel(dschungelZeilenInt, dschungelSpaltenInt, dschungelZeichenStr);
	        	List<Element> dschungelkinder = dschungelElement.getChildren();
	        	dschungelkinder.forEach ( i -> {
	        		Attribute FeldId = i.getAttribute("id");
	        		String FeldIdStr = FeldId.getValue();
	        		
	        		Attribute feldZeile = i.getAttribute("zeile");
	        		int feldZeileInt = 0;
					try {
						feldZeileInt = feldZeile.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldSpalte = i.getAttribute("spalte");
	        		int feldSpalteInt = 0;
					try {
						feldSpalteInt = feldSpalte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldVerwendbarkeit = i.getAttribute("verwendbarkeit");
	        		int feldVerwendbarkeitInt = 0;
					try {
						feldVerwendbarkeitInt = feldVerwendbarkeit.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		Attribute feldPunkte = i.getAttribute("punkte");
	        		int feldPunkteInt = 0;
					try {
						feldPunkteInt = feldPunkte.getIntValue();
					} catch (DataConversionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		char FeldZeichen = i.getValue().charAt(0);
	        		
	        		Feld mowgli = dschungel.getFeld(feldZeileInt, feldSpalteInt);
	        		mowgli.setId(dschungelSpaltenInt);
	        		mowgli.setPunkte(feldPunkteInt);
	        		mowgli.setVerwendbarkeit(feldVerwendbarkeitInt);
	        		mowgli.setZeichen(FeldZeichen);
	        		
	        		
	        	});
	        	setDschungel(dschungel);
	        	 
	        }
	        Element schlangenE = rootElement.getChild("Schlangen");
	        List<Element> schlangenkinder = schlangenE.getChildren();
	        ArrayList<Schlange> schlangen = new ArrayList<Schlange>();
	        schlangenkinder.forEach(i->{
	        	Schlange schlange = new Schlange();
	        	Attribute schlangenArt = i.getAttribute("art");
	        	String schlangenArtS = schlangenArt.getValue();
	        	
	        	schlange.setArt(schlangenArtS);
	        	List<Element> gliederkinder = i.getChildren();
	        	//getFeldById from dschungel
	        	Element kopfE = gliederkinder.get(0);
	        	Attribute kopfEFeld = kopfE.getAttribute("feld");
	        	String kopfFeldS = kopfEFeld.getValue();
	        	Feld SchlangenKopfFeld = getDschungel().getFeldById(kopfFeldS);

	        	Schlangenglied glied0 = new Schlangenglied(SchlangenKopfFeld);
	        	schlange.setKopf(glied0);
	        	Schlangenglied prevGlied = glied0;
	        	for (int n = 1; n < gliederkinder.size(); n++) {
	        		
	        		Element gliedE = gliederkinder.get(n);
	        		Attribute gliedEFeld = gliedE.getAttribute("feld");
	        		String gliedFeldS = gliedEFeld.getValue();
	        		
	        		Feld gliedFeld = getDschungel().getFeldById(gliedFeldS);
	        		Schlangenglied glied = new Schlangenglied(gliedFeld);
	        		
	        		prevGlied.setNext(glied);
	        		prevGlied = glied;
	        		
	        		
	        		
	        	}
	        	schlangen.add(schlange);
	        });
	        
	        
	        
	        setXMLSchlangenLoesung(schlangen);
	        
	        
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
	}
	

	
	/**
	 * Setzt den übergebenen Dschungel als den Haupt-Dschungel für das Schlangenjagd-Spiel.
	 *
	 * @param dschungel Der Dschungel, der als Haupt-Dschungel gesetzt werden soll.
	 */
	private void setDschungel(Dschungel dschungel) {
		this.hauptDschungel = dschungel;
	}
	
	/**
	 * Setzt die übergebene Schlangenarten als die Haupt-Schlangenarten für das Schlangenjagd-Spiel.
	 *
	 * @param schlangenarten Die Schlangenarten, die als Haupt-Schlangenarten gesetzt werden sollen.
	 */
	private void setSchlangenarten(Schlangenarten schlangenarten) {
		this.hauptSchlangenarten = schlangenarten;
	}
	
	/**
	 * Setzt die übergebene Zeitvorgabe für das Schlangenjagd-Spiel.
	 *
	 * @param zeitVorgabe Die Zeitvorgabe in Sekunden.
	 */
	private void setZeitVorgabe(double zeitVorgabe) {
		this.zeitVorgabe = zeitVorgabe;
	}
	
	/**
	 * Setzt die übergebene Zeitabgabe für das Schlangenjagd-Spiel.
	 *
	 * @param zeitAbg Die Zeitabgabe in Sekunden.
	 */
	private void setZeitAbgabe(double zeitAbg) {
		this.zeitAbgabe = zeitAbg;
	}
	
	/**
	 * Setzt die übergebene Zeitabgabe Einheit für das Schlangenjagd-Spiel.
	 *
	 * @param einheit Die Zeitabgabe Einheit.
	 */
	private void setEinheit(String einheit) {
		this.zeitVorgabeEinheit = einheit;
	}
	
	/**
	 * Setzt die übergebene Schlangensuche als die Lösung für das Schlangenjagd-Spiel.
	 *
	 * @param loesung Die Schlangensuche, die als Lösung gesetzt werden soll.
	 */
	private void setLoesung(SchlangenSuche loesung) {
		this.loesung = loesung;
	}
	
	/**
	 * Gibt den Haupt-Dschungel für das Schlangenjagd-Spiel zurück.
	 *
	 * @return Der Haupt-Dschungel.
	 */
	public Dschungel getDschungel() {
		return this.hauptDschungel;
	}
	
	/**
	 * Gibt die Haupt-Schlangenarten für das Schlangenjagd-Spiel zurück.
	 *
	 * @return Die Haupt-Schlangenarten.
	 */
	public Schlangenarten getSchlangenarten() {
		return this.hauptSchlangenarten;
	}
	
	/**
	 * Gibt die Lösung für das Schlangenjagd-Spiel zurück, die durch die Schlangensuche ermittelt wurde.
	 *
	 * @return Die Lösung des Schlangenjagd-Spiels.
	 */
	private SchlangenSuche getLoesung() {
		return this.loesung;
	}
	
	
	/**
	 * Gibt die Zeitvorgabe für das Schlangenjagd-Spiel zurück.
	 *
	 * @return Die Zeitvorgabe in Sekunden.
	 */
	private double getZeitVorgabe() {
		return this.zeitVorgabe;
	}
	
	/**
	 * Gibt die Zeitabgabe für das Schlangenjagd-Spiel zurück.
	 *
	 * @return Die Zeitabgabe in Sekunden.
	 */
	private double getZeitAbgabe() {
		return this.zeitAbgabe;
	}
	
	private String getEinheit() {
		return this.zeitVorgabeEinheit;
	}
	
	
	
	/**
	 * Gibt den Dateinamen der Eingabe-XML-Datei zurück.
	 *
	 * @return Der Dateiname der Eingabe-XML-Datei.
	 */
	private String getEingabeDatei() {
		return this.eingabeDatei;
	}
	
	/**
	 * Gibt den Dateinamen der Ausgabe-XML-Datei zurück.
	 *
	 * @return Der Dateiname der Ausgabe-XML-Datei.
	 */
	private String getAusgabeDatei() {
		return this.ausgabeDatei;
	}
	
	/**
	 * Gibt den Status zurück, ob das Schlangenjagd-Spiel erfolgreich gelöst wurde.
	 *
	 * @return true, wenn das Spiel erfolgreich gelöst wurde, ansonsten false.
	 */
	public boolean getErfolgLoese() {
		return this.erfolgLoese;
	}
	
	/**
	 * Setzt den Status, dass das Schlangenjagd-Spiel erfolgreich gelöst wurde.
	 */
	private void setErfolgLoeseTrue() {
		this.erfolgLoese = true;
	}
	
	/**
	 * Gibt den Status zurück, ob das Schlangenjagd-Spiel erfolgreich erzeugt wurde.
	 *
	 * @return true, wenn das Spiel erfolgreich erzeugt wurde, ansonsten false.
	 */
	public boolean getErfolgErzeuge() {
		return this.erfolgErzeuge;
	}
	
	/**
	 * Setzt den Status, dass das Schlangenjagd-Spiel erfolgreich erzeugt wurde.
	 */
	private void setErfolgErzeugeTrue() {
		this.erfolgErzeuge = true;
	}
	
	/**
	 * Setzt die XML-Schlangenlösung, die aus einer XML-Datei gelesen wurde.
	 *
	 * @param xmlSchlangenLoesung Die XML-Schlangenlösung, die gesetzt werden soll.
	 */
	private void setXMLSchlangenLoesung(ArrayList<Schlange> xmlSchlangenLoesung){
		this.xmlSchlangenloesung = xmlSchlangenLoesung;
		
	}
	
	/**
	 * Gibt die XML-Schlangenlösung zurück, die aus einer XML-Datei gelesen wurde.
	 *
	 * @return Die XML-Schlangenlösung.
	 */
	public ArrayList<Schlange> getXMLSchlangenLoesung(){
		return this.xmlSchlangenloesung;
		
	}
	private double leseZeitKonvertieren(double eingabe , String string ) {
		double ausgabe = 0;
		
		if (string.equals("s")){
			ausgabe = eingabe * 1000;
		}
		if (string.equals("min")){
			ausgabe = eingabe * 1000 * 60;
		}
		if (string.equals("h")){
			ausgabe = eingabe * 1000 * 60 * 60;
		}
		if (string.equals("d")){
			ausgabe = eingabe * 1000 * 60 * 60 *24;
		}
		
		if (string.equals("ms")){
			ausgabe = eingabe;
		}
		return ausgabe;
	}
	
	private double schreibZeitKonvertieren(double eingabe, String einheit) {
		double ausgabe = 0;
		
		if (einheit.equals("s")){
			ausgabe = eingabe / 1000;
		}
		if (einheit.equals("min")){
			ausgabe = eingabe / ( 1000 * 60 );
		}
		if (einheit.equals("h")){
			ausgabe = eingabe / ( 1000 * 60 * 60 );
		}
		if (einheit.equals("d")){
			ausgabe = eingabe / ( 1000 * 60 * 60 *24 );
		}
		
		if (einheit.equals("ms")){
			ausgabe = eingabe;
		}
		return ausgabe;
	}
	

	
	
}
