package de.fernuni.kurs01584.ss23.modell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NachbarschaftsstrukturTest {

    private Nachbarschaftsstruktur nachbarschaftsstruktur1;
    private Nachbarschaftsstruktur nachbarschaftsstruktur2;
    private Dschungel dschungel;
    private Feld feld;

    @BeforeEach
    void setUp() {
        nachbarschaftsstruktur1 = new Nachbarschaftsstruktur("Distanz", 1);
        nachbarschaftsstruktur2 = new Nachbarschaftsstruktur("Sprung", 2 ,2);
        dschungel = new Dschungel(20,20,"abcdefghijklmnopqrstuvwxyz"); // replace with your Jungle initialization
        feld = new Feld(10,10); // replace with your field initialization
    }

    @Test
    @DisplayName("Test der Nachbarschaftsstruktur mit drei Parametern")
    void testNachbarschaftsstrukturWithThreeParameters() {
        assertEquals("Distanz", nachbarschaftsstruktur1.getTyp());
        assertEquals(1, nachbarschaftsstruktur1.getWert1());
        assertEquals(0, nachbarschaftsstruktur1.getWert2());
    }

    @Test
    @DisplayName("Test der Nachbarschaftsstruktur mit zwei Parametern")
    void testNachbarschaftsstrukturWithTwoParameters() {
        assertEquals("Sprung", nachbarschaftsstruktur2.getTyp());
        assertEquals(2, nachbarschaftsstruktur2.getWert1());
        assertEquals(2, nachbarschaftsstruktur2.getWert2());
    }

    @Test
    @DisplayName("Test der Methode getNachbarschaft für Distanz")
    void testGetNachbarschaftForDistanz() {
        ArrayList<Feld> nachbarschaft = nachbarschaftsstruktur1.getNachbarschaft(dschungel, feld);
        // Addiere assertion 
    }

    @Test
    @DisplayName("Test der Methode getNachbarschaft für Sprung")
    void testGetNachbarschaftForSprung() {
        ArrayList<Feld> nachbarschaft = nachbarschaftsstruktur2.getNachbarschaft(dschungel, feld);
        // Addiere assertion 
    }
}