package de.fernuni.kurs01584.ss23.algorithmus;

import de.fernuni.kurs01584.ss23.modell.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SchlangenSucheTest {
    private Dschungel dschungel;
    private Schlangenarten schlangenarten;
    private SchlangenSuche schlangenSuche;

    @BeforeEach
    public void setup() {
        // TODO: Initialize dschungel and schlangenarten according to your actual data
        dschungel = new Dschungel(10, 10, "abcdefg");
        schlangenarten = new Schlangenarten();
        schlangenSuche = new SchlangenSuche(dschungel, schlangenarten, 5000);
    }

    @Test
    @DisplayName("Test for constructor with valid arguments")
    public void testConstructor_withValidArguments() {
        assertNotNull(schlangenSuche.getLoesung());
        assertEquals(0, schlangenSuche.getAnzahl());
    }

    @Test
    @DisplayName("Test for set and get AbgabeZeit")
    public void testSetAndGetAbgabeZeit() {
        double expectedAbgabeZeit = 1000;
        schlangenSuche.setAbgabeZeit(expectedAbgabeZeit);
        assertEquals(expectedAbgabeZeit, schlangenSuche.getAbgabeZeit());
    }

    @Test
    @DisplayName("Test for sucheSchlange method")
    public void testSucheSchlange() {
        schlangenSuche.sucheSchlange();
        ArrayList<Schlange> result = schlangenSuche.getLoesung();
        assertNotNull(result);

        assertEquals(schlangenarten.getAnzahl(), result.size());
    }
}