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
		if (args.length < 2 || args.length > 3){
			throw new IllegalArgumentException(
					"Ein vollstaendiger Parameteraufruf kann wie folgt aussehen:\r\n"
					+ "ablauf=ld eingabe=res\\sj1.xml ausgabe=res\\sj1_loesung.xml"
					+ "\nl (loesen): Fuer eine gegebene Probleminstanz wird nach einer neuen Loesung gesucht"
					+ "\ne (erzeugen): Eine neue Probleminstanz wird auf Basis der gegebenen Parameter\r\n"
					+ "erzeugt und bei Angabe einer Ausgabedatei gespeichert"
					+ "\np (pruefen): Die Zulaessigkeit der gegebenen Loesung wird ueberprueft. Bei Unzulaessigkeit\r\n"
					+ "werden die Art und Anzahl der verletzten Bedingungen in der Konsole ausgegeben"
					+ "\nb (bewerten): Die Gesamtpunktzahl der Loesung wird unabhaengig von der Zulaessigkeit\r\n"
					+ "berechnet und in der Konsole ausgegeben"
					+ "\nd (darstellen): Die Probleminstanz und die zugehoerige Loesung werden in der Konsole\r\n"
					+ "dargestellt\n");
		}
		String ablauf = args[0].substring(args[0].indexOf('=') + 1);
		String eingabe = args[1].substring(args[1].indexOf('=') + 1);
		String ausgabe = "";
		//Vorgabe vorgabeDatei = new Vorgabe(eingabe);
		if (args.length == 3) {
			//vorgabeDatei = new Vorgabe(eingabe, ausgabe);
			ausgabe = args[2].substring(args[2].indexOf('=') + 1);
		}
		
		Schlangenjagd schlangenJagd = new Schlangenjagd();
		if(ablauf.contains("l") && !ablauf.contains("e")) {
			schlangenJagd.loeseProbleminstanz(eingabe, ausgabe);
			
		}
		if(ablauf.contains("e") && !ablauf.contains("l")) {
			schlangenJagd.erzeugeProbleminstanz(eingabe,ausgabe);
			
		}
		if(ablauf.contains("e") && ablauf.contains("l")) {
			String zwischenOrt = "res/zwischen.xml";
			schlangenJagd.erzeugeProbleminstanz(eingabe,zwischenOrt);
			schlangenJagd.loeseProbleminstanz(zwischenOrt, ausgabe);
			
		}
		
		if(ablauf.contains("d")) {
			if(!ablauf.contains("l") && !ablauf.contains("e")) {
				schlangenJagd.darstellen(eingabe);
			}else if (!ablauf.contains("e")){
				schlangenJagd.darstellen(ausgabe);
			}else {
				
				throw new IllegalArgumentException(
						"Bei nur Erzeugen kann man keine Loesung darstellen!!");
			
			}
			
		}
		
		if(ablauf.contains("p")) {
			List<Fehlertyp> fehler = new ArrayList<>();
			if(!ablauf.contains("l") && !ablauf.contains("e")) {
				fehler = schlangenJagd.pruefeLoesung(eingabe);
				
			}else{
				fehler = schlangenJagd.pruefeLoesung(ausgabe);
			}
			if(fehler != null) {
				System.out.println("Anzahl der Fehlern gefunden : " + fehler.size());
			
				System.out.println("Fehlern gefunden Sind : " );
				fehler.forEach(i -> System.out.print(i + " ,"));
				System.out.println();
			}
			
			
			
		}
		if(ablauf.contains("b")) {
			int punkte = 0;
			if(!ablauf.contains("l") && !ablauf.contains("e")) {
				 punkte = schlangenJagd.bewerteLoesung(eingabe);
				
			}else  {
				punkte = schlangenJagd.bewerteLoesung(ausgabe);
			}
			System.out.println("Bewrtung zur loesung : Anzahl der erreichte Punkte : " + punkte);
		}
		
		
		
		
		// muss den fehler zeigen
		
		
		
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
		ArrayList<String> verwendbarkeit = new ArrayList<String>() ;
		for(Schlange schlange: vorgabeDatei.getXMLSchlangenLoesung()) {
			String art = schlange.getArt();
			
			Schlangenart schlangenart = schlangenarten.getSchlangeByArt(art);
			ArrayList<Schlangenglied> gliederListe = new ArrayList<Schlangenglied>();
			
			Nachbarschaftsstruktur nachStrukt = schlangenart.getNachbarschaftsstruktur();
			
			
			Schlangenglied glied = schlange.getKopf();
			gliederListe.add(glied);
			while(glied.getNext() != null) {
				ArrayList<Feld> nachbarn = new ArrayList<Feld>();
				/*if(nachStrukt.getTyp() == "Distanz") {
					nachbarn = nachStrukt.getNachbarschaft(dschungel, glied.getFeld());
				}else if (nachStrukt.getTyp() == "Sprung")*/
				nachbarn = nachStrukt.getNachbarschaft(dschungel, glied.getFeld());
				//System.out.println("[" + glied.getFeld().getZeile() + " , " +glied.getFeld().getSpalte() + "]" );
				//nachbarn.forEach(i -> System.out.print("(" + i.getZeile() + " , " + i.getSpalte() + ")"));
				//System.out.println();
				glied =  glied.getNext();
				gliederListe.add(glied);
				if(!nachbarn.contains(glied.getFeld())) {
					//System.out.println("Struktur : " + nachStrukt.getTyp() + nachStrukt.getWert1() + nachStrukt.getWert2());
					//System.out.println("up"+ nachbarn.contains(glied.getFeld()));
					fehler.add(Fehlertyp.NACHBARSCHAFT);
				}
				
			}
			
			if (gliederListe.size() != schlangenart.getSize()){
				fehler.add(Fehlertyp.GLIEDER);
			}
				
				
			//hier soll ich die schlangen mit gliederfehler ausschließen können
			if (schlangenart.getSize() == gliederListe.size()){
				for (int i = 0; i < schlangenart.getSize(); i++) {
					if (schlangenart.getZeichenkette().charAt(i) != gliederListe.get(i).getFeld().getZeichen()) {
						fehler.add(Fehlertyp.ZUORDNUNG);
					}
					
				}
			}
			
			
			//gliederListe.remove(0);
			
			for(Schlangenglied g : gliederListe) {
				if(g == schlange.getKopf()) {
					continue;
				}//8:53
				System.out.println("vorhanden: " + Collections.frequency(verwendbarkeit, g.getFeld().getId()));
				System.out.println("vaerwendbarkeit: " +  g.getFeld().getVerwendbarkeit() );
				if(Collections.frequency(verwendbarkeit, g.getFeld().getId()) >= g.getFeld().getVerwendbarkeit()) {
					fehler.add(Fehlertyp.VERWENDUNG);
					System.out.println("feld id : " +   g.getFeld().getId());
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
	
	public void darstellen(String darstellungsdatei) {
		Vorgabe vorgabeDatei = new Vorgabe(darstellungsdatei);
		
		try {
			vorgabeDatei.readPruefe();
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Dschungel dschungel = vorgabeDatei.getDschungel();
		
		Schlangenarten schlangenarten = vorgabeDatei.getSchlangenarten();
		ArrayList<Schlange> schlangen = vorgabeDatei.getXMLSchlangenLoesung();
		Darstellung.printDarstellung(dschungel);
		Darstellung.printSchlangenarten(schlangenarten);
		Darstellung.printLoesung(schlangen, schlangenarten, dschungel);
		
		
	}
}
