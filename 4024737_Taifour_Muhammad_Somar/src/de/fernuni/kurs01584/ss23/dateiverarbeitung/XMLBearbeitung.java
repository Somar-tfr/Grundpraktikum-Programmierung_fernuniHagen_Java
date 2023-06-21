package de.fernuni.kurs01584.ss23.dateiverarbeitung;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.*;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;



public class XMLBearbeitung {
	public static void main(String[] args)  {
		try {
			read("./res/sj_p1_probleminstanz.xml");
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void read(String name) throws JDOMException, IOException {
		//Dokument builder erstellen
		SAXBuilder reader = new SAXBuilder();		
		
		//Parse den XML in einem Dokument Objekt
		
		Document document = reader.build(new File(name));
		
		//auf Elemente und Daten im Dokument zugreifen
		
		Element rootElement = document.getRootElement();
        Element element = rootElement.getChild("Dschungel");
        String value = element.getText();
        
        System.out.println("Element value: " + value);

        
		System.out.println(document.getRootElement());
		//System.out.println(document.get);
		System.out.println(document.getRootElement().getAttributeValue("Zeit"));
		
	}
	
	private static void write() throws FileNotFoundException, IOException {
		Document document = new Document();
		Element root = new Element("document");
		root.setAttribute("file", "file.xml");
		root.addContent(new Element("style"));//hier addieren
		document.setRootElement(root);
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileOutputStream(new File("file.xml")));
				
	}
	
	
}
