package de.fernuni.kurs01584.ss23.dateiverarbeitung;

import de.fernuni.kurs01584.ss23.modell.*;
import de.fernuni.kurs01584.ss23.algorithmus.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;



public class Vorgabe {
	private String eingabeDatei;
	private String ausgabeDatei;
	private SchlangenSuche schlangensuche;
	private Dschungel hauptDschungel;
	Vorgabe(String eingabe, String ausgabe){
		this.eingabeDatei = eingabe;
		this.ausgabeDatei = ausgabe;
	}	
	
	public static void main(String[] args)  {
		
		
	}

	private void read(String name) throws JDOMException, IOException {
		try {
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
			int vorgabe = Integer.parseInt(vorgabeElement.getValue());
			
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
	        	schlangenart.setID(dschungelZeichenStr);
	        	schlangenart.setAnzahl(schlangenartAnzahlInt);
	        	schlangenart.setPunkte(schlangenartPunkteInt);
	        	
	        	 
	        	
	        	schlangenarten.add(schlangenart);
	        
	        });
	        
	        //muss schauen dass für schlangen mit anzahl mehr als 1 entsprechend weitere loop in generator durchzuführen
	        if (dschungelElement.getChildren() == null){
	        	DschungelGenerator dschungelGenerator = new DschungelGenerator(dschungelZeilenInt, dschungelSpaltenInt,dschungelZeichenStr, schlangenarten );
		        
		        Dschungel dschungel = dschungelGenerator.erzeugeDschungel();
		        this.hauptDschungel = dschungel;
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
	        	this.hauptDschungel = dschungel;
	        }
	        
	        
	        this.schlangensuche = new SchlangenSuche(this.hauptDschungel,schlangenarten, vorgabe );
	        
	        write(schlangensuche);
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
	
	private void write(SchlangenSuche schlangensuche) throws FileNotFoundException, IOException {
		Document document = new Document();
		Element root = new Element("document");
		root.setAttribute("file", "file.xml");
		root.addContent(new Element("style"));//hier addieren
		document.setRootElement(root);
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileOutputStream(new File("file.xml")));
				
	}
	
	
}
