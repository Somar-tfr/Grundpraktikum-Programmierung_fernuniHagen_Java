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
	
	private double zeitVorgabe;
	private double zeitAbgabe;
	
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
	
	
	/*public static void main(String[] args) throws JDOMException, IOException  {
		Vorgabe vorgabe = new Vorgabe("res/sj_p1_loesung.xml");
		vorgabe.readPruefe();
		
	}*/
	
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
			
			setZeitVorgabe(vorgabe);
			
			if (einheit.equals("s")){
				vorgabe = vorgabe * 1000;
			}
			
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
	        
	        SchlangenSuche loesung = new SchlangenSuche(getDschungel(),getSchlangenarten(), vorgabe );
	        loesung.sucheSchlange();
	        double zeitAbg = loesung.getAbgabeZeit();
	        setZeitAbgabe(zeitAbg);
	        setLoesung(loesung);
	        
	        
	        /*return dschungel;*/
	        /*Element element = rootElement.getChild("Dschungel");
	        String value = element.getText();
	        
	        System.out.println("Element value: " + value);

	        
			System.out.println(document.getRootElement());
			//System.out.println(document.get);
			System.out.println(document.getRootElement().getAttributeValue("Zeit"));*/
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*return null;*/
		
		
		
	}
	
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
			
			setZeitVorgabe(vorgabe);
			
			if (einheit.equals("s")){
				vorgabe = vorgabe * 1000;
			}
			
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
	        /*
	        SchlangenSuche loesung = new SchlangenSuche(getDschungel(),getSchlangenarten(), vorgabe );
	        loesung.sucheSchlange();
	        double zeitAbg = loesung.getAbgabeZeit();
	        setZeitAbgabe(zeitAbg);
	        setLoesung(loesung);
	        */
	        
	        /*return dschungel;*/
	        /*Element element = rootElement.getChild("Dschungel");
	        String value = element.getText();
	        
	        System.out.println("Element value: " + value);

	        
			System.out.println(document.getRootElement());
			//System.out.println(document.get);
			System.out.println(document.getRootElement().getAttributeValue("Zeit"));*/
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*return null;*/
		
		
	}
	
	public void writeLoese() throws FileNotFoundException, IOException {
		String name = getAusgabeDatei();
		
        String currentDir = System.getProperty("user.dir");

        String dateiPfad = currentDir  + "/" + name ;
		//String dateiname = name + ".xml";
		
		
		Document document = new Document();
		Element root = new Element("Schlangenjagd");
		//root.setAttribute("file", dateiname);
		//root.addContent(new Element("style"));//hier addieren
		document.setRootElement(root);
		
		//Zeit Element
		Element zeit = new Element("Zeit");
        zeit.setAttribute("einheit", "s");

        Element vorgabe = new Element("Vorgabe");
        String zeitVorgabeStr = String.valueOf(getZeitVorgabe());
        vorgabe.setText(zeitVorgabeStr);
        zeit.addContent(vorgabe);
        
        Element abgabe = new Element("Abgabe");
        
        String zeitAbgabeStr = String.valueOf(getZeitAbgabe());
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
        	Element par1E = new Element ("Parameter");
        	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert1()));
        	nachbE.setContent(par1E);
        	if (schlangenart.getNachbarschaftsstruktur().getTyp() == "Sprung") {
        		Element par2E = new Element ("Parameter");
            	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert2()));
            	nachbE.setContent(par2E);
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
            
    		outputter.output(document, new FileOutputStream(new File(dateiPfad)));
        }catch (IOException e){
        	e.printStackTrace();
        }
		
		
	}
	
	
	public void writeErzeuge() throws FileNotFoundException, IOException {
		String name = getAusgabeDatei();
		
        String currentDir = System.getProperty("user.dir");

        String dateiPfad = currentDir  + "/" + name ;
		//String dateiname = name + ".xml";
		
		
		Document document = new Document();
		Element root = new Element("Schlangenjagd");
		//root.setAttribute("file", dateiname);
		//root.addContent(new Element("style"));//hier addieren
		document.setRootElement(root);
		
		//Zeit Element
		Element zeit = new Element("Zeit");
        zeit.setAttribute("einheit", "s");

        Element vorgabe = new Element("Vorgabe");
        String zeitVorgabeStr = String.valueOf(getZeitVorgabe());
        vorgabe.setText(zeitVorgabeStr);
        zeit.addContent(vorgabe);
        /*
        Element abgabe = new Element("Abgabe");
        
        String zeitAbgabeStr = String.valueOf(getZeitAbgabe());
        abgabe.setText(zeitAbgabeStr);
        zeit.addContent(abgabe);
       
        root.addContent(zeit);
        */
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
        	Element par1E = new Element ("Parameter");
        	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert1()));
        	nachbE.setContent(par1E);
        	if (schlangenart.getNachbarschaftsstruktur().getTyp() == "Sprung") {
        		Element par2E = new Element ("Parameter");
            	par1E.setAttribute("wert", String.valueOf(schlangenart.getNachbarschaftsstruktur().getWert2()));
            	nachbE.setContent(par2E);
        	}
        	schlangenartE.addContent(nachbE);
        	
        	schlangenartenE.addContent(schlangenartE);
        	
        
        }
        
        root.addContent(schlangenartenE);
        /*
      //loesung
        Element schlangenE = new Element ("Schlangen");
        
        SchlangenSuche schlagnensuche = getLoesung();
        ArrayList<Schlange> schlangen = schlagnensuche.getLoesung();
        if (schlangen.size() > 0) {
        	setErfolgTrue();
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
        root.addContent(schlangenE);*/
		//doctype
		DocType dt = new DocType("Schlangenjagd", "schlangenjagd.dtd");
        document.setDocType(dt);
        
        try {
        	XMLOutputter outputter = new XMLOutputter();
    		outputter.setFormat(Format.getPrettyFormat());
            
    		outputter.output(document, new FileOutputStream(new File(dateiPfad)));
    		setErfolgErzeugeTrue();
        }catch (IOException e){
        	e.printStackTrace();
        }
		
		
	}
	
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
			
			setZeitVorgabe(vorgabe);
			
			if (einheit.equals("s")){
				vorgabe = vorgabe * 1000;
			}
			
			Element abgabeElement = zeitElement.getChild("Abgabe");
			double abgabe = Double.parseDouble(vorgabeElement.getValue());
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	
	private void setDschungel(Dschungel dschungel) {
		this.hauptDschungel = dschungel;
	}
	
	private void setSchlangenarten(Schlangenarten schlangenarten) {
		this.hauptSchlangenarten = schlangenarten;
	}
	
	private void setZeitVorgabe(double zeitVorgabe) {
		this.zeitVorgabe = zeitVorgabe;
	}
	
	private void setZeitAbgabe(double zeitAbg) {
		this.zeitAbgabe = zeitAbg;
	}
	
	private void setLoesung(SchlangenSuche loesung) {
		this.loesung = loesung;
	}
	
	public Dschungel getDschungel() {
		return this.hauptDschungel;
	}
	
	public Schlangenarten getSchlangenarten() {
		return this.hauptSchlangenarten;
	}
	
	private SchlangenSuche getLoesung() {
		return this.loesung;
	}
	
	
	
	private double getZeitVorgabe() {
		return this.zeitVorgabe;
	}
	
	private double getZeitAbgabe() {
		return this.zeitAbgabe;
	}
	
	private String getEingabeDatei() {
		return this.eingabeDatei;
	}
	
	private String getAusgabeDatei() {
		return this.ausgabeDatei;
	}
	
	public boolean getErfolgLoese() {
		return this.erfolgLoese;
	}
	
	private void setErfolgLoeseTrue() {
		this.erfolgLoese = true;
	}
	
	public boolean getErfolgErzeuge() {
		return this.erfolgErzeuge;
	}
	
	private void setErfolgErzeugeTrue() {
		this.erfolgErzeuge = true;
	}
	
	private void setXMLSchlangenLoesung(ArrayList<Schlange> xmlSchlangenLoesung){
		this.xmlSchlangenloesung = xmlSchlangenLoesung;
		
	}
	
	public ArrayList<Schlange> getXMLSchlangenLoesung(){
		return this.xmlSchlangenloesung;
		
	}

	
	
}
