package de.fernuni.kurs01584.ss23.hauptkomponente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

import org.jdom2.JDOMException;

import de.fernuni.kurs01584.ss23.*;
import de.fernuni.kurs01584.ss23.algorithmus.*;
import de.fernuni.kurs01584.ss23.darstellung.Darstellung;
import de.fernuni.kurs01584.ss23.dateiverarbeitung.*;
import de.fernuni.kurs01584.ss23.modell.*;


public class Schlangenjagd implements SchlangenjagdAPI {
	// TODO: Implementierung von Schnittstelle und Programm-Einstieg
	public static void main(String[] args) {
		Schlangenjagd jagd = new Schlangenjagd();
		System.out.println(jagd.loeseProbleminstanz("res/sj_p1_unvollstaendig.xml", "res/xmlAusgabeDatei1.xml"));
		System.out.println(jagd.erzeugeProbleminstanz("res/sj_p1_unvollstaendig.xml", "res/xmlAusgabeDatei2.xml"));
		System.out.println(jagd.pruefeLoesung("res/sj_p1_loesung.xml"));
		System.out.println(jagd.bewerteLoesung("res/sj_p1_loesung.xml"));
		
	}
	@Override
	public boolean loeseProbleminstanz(String xmlEingabeDatei, String xmlAusgabeDatei){
		// TODO Implementierung der API-Methode zur Loesung von Probleminstanzen.
		
		Vorgabe vorgabeDatei = new Vorgabe(xmlEingabeDatei, xmlAusgabeDatei);
		try {
			vorgabeDatei.readLoese();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			
			//e.printStackTrace();
			return false;
		}
		try {
			vorgabeDatei.writeLoese();
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (vorgabeDatei.getErfolgLoese()) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean erzeugeProbleminstanz(String xmlEingabeDatei, String xmlAusgabeDatei) {
		// TODO Implementierung der API-Methode zur Erzeugung von Probleminstanzen.
		Vorgabe vorgabeDatei = new Vorgabe(xmlEingabeDatei, xmlAusgabeDatei);
		try {
			vorgabeDatei.readErzeuge();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			vorgabeDatei.writeErzeuge();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vorgabeDatei.getErfolgErzeuge()) {
			return true;
		}
			
		return false;
		
	}

	@Override
	public List<Fehlertyp> pruefeLoesung(String xmlEingabeDatei) {
		// TODO Implementierung der API-Methode zur Pruefung von Loesungen.
		Vorgabe vorgabeDatei = new Vorgabe(xmlEingabeDatei);
		
		try {
			vorgabeDatei.readPruefe();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dschungel dschungel = vorgabeDatei.getDschungel();
		List<Fehlertyp> fehler = new ArrayList<>();
		Schlangenarten schlangenarten = vorgabeDatei.getSchlangenarten();
		//Schlangenart schlangenart = schlangenarten.getSchlangeByArt("A0");
		//13:34
		for(Schlange schlange: vorgabeDatei.getXMLSchlangenLoesung()) {
			String art = schlange.getArt();

			Schlangenart schlangenart = schlangenarten.getSchlangeByArt(art);
			ArrayList<Schlangenglied> gliederListe = new ArrayList<Schlangenglied>();
			
			Nachbarschaftsstruktur nachStrukt = schlangenart.getNachbarschaftsstruktur();
			
			
			Schlangenglied glied = schlange.getKopf();
			gliederListe.add(glied);
			while(glied.getNext() != null) {
				ArrayList<Feld> nachbarn = new ArrayList<Feld>();
				nachbarn = nachStrukt.getNachbarschaft(dschungel, glied.getFeld());
				
				glied =  glied.getNext();
				gliederListe.add(glied);
				if(!nachbarn.contains(glied.getFeld())) {
					fehler.add(Fehlertyp.NACHBARSCHAFT);
				}
				
			}
			
			if (gliederListe.size() != schlangenart.getSize()){
				fehler.add(Fehlertyp.GLIEDER);
			}
				
				
			
			for (int i = 0; i < schlangenart.getSize(); i++) {
				if (schlangenart.getZeichenkette().charAt(i) != gliederListe.get(i).getFeld().getZeichen()) {
					fehler.add(Fehlertyp.ZUORDNUNG);
				}
				
			}
			
			ArrayList<String> verwendbarkeit = new ArrayList<String>() ;
			for(Schlangenglied g : gliederListe) {
				if(Collections.frequency(verwendbarkeit, g.getFeld().getId()) > g.getFeld().getVerwendbarkeit()) {
					fehler.add(Fehlertyp.VERWENDUNG);
				}
				verwendbarkeit.add(g.getFeld().getId());
			}
			
			
			
			
			
		}
		
		
		
		if (fehler.size() > 0){
			return fehler;
		}
		return null;
		
		
	}

	@Override
	public int bewerteLoesung(String xmlEingabeDatei) {
		// TODO Implementierung der API-Methode zur Bewertung von Loesungen.
Vorgabe vorgabeDatei = new Vorgabe(xmlEingabeDatei);
		
		try {
			vorgabeDatei.readPruefe();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dschungel dschungel = vorgabeDatei.getDschungel();
		int punkte = 0;
		
		Schlangenarten schlangenarten = vorgabeDatei.getSchlangenarten();
		ArrayList<Schlange> schlangen = vorgabeDatei.getXMLSchlangenLoesung();
		for(Schlange s : schlangen){
			punkte += schlangenarten.getSchlangeByArt(s.getArt()).getPunkte();
			
			Schlangenglied glied = s.getKopf();
			punkte += glied.getFeld().getPunkte();
			while(glied.getNext() != null) {
				
				glied =  glied.getNext();
				punkte += glied.getFeld().getPunkte();
				
			}
			
			
		}
		
		return punkte;
	}

	@Override
	public String getName() {
		// TODO Implementierung der API-Methode zur Rueckgabe Ihres vollstaenigen Namens.
		return "Somar Taifour";
	}

	@Override
	public String getMatrikelnummer() {
		// TODO Implementierung der API-Methode zur Rueckgabe Ihrer Matrikelnummer.
		return "4024737";
	}

	@Override
	public String getEmail() {
		// TODO Implementierung der API-Methode zur Rueckgabe Ihrer E-Mail Adresse.
		return "somar.taifour@outlook.com";
	}
}
