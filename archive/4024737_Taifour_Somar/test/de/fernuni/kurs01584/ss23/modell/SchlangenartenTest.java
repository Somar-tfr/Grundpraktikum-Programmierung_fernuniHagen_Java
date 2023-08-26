package de.fernuni.kurs01584.ss23.modell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SchlangenartenTest {

    private Schlangenarten schlangenarten;

    @BeforeAll
    static void initAll() {
        // Gemeinsame Initialisierung für alle Tests (falls notwendig)
    }

    @BeforeEach
    void init() {
        // Vor jedem Test: Neue Schlangenarten-Liste erstellen
        schlangenarten = new Schlangenarten();
    }

    @AfterEach
    void tearDown() {
        // Nach jedem Test: Schlangenarten-Liste zurücksetzen (falls notwendig)
        schlangenarten = null;
    }

    @AfterAll
    static void tearDownAll() {
        // Aufräumarbeiten nach allen Tests (falls notwendig)
    }

    @Test
    @DisplayName("Test add() and getSize()")
    void testAddAndGetSzie() {
        Schlangenart art1 = new Schlangenart("Python", new Nachbarschaftsstruktur("Distanz", 1));
        Schlangenart art2 = new Schlangenart("Cobra", new Nachbarschaftsstruktur("Distanz", 1));

        schlangenarten.add(art1);
        schlangenarten.add(art2);

        assertEquals(2, schlangenarten.getSize());
    }

    @Test
    @DisplayName("Test getAnzahl()")
    void testGetAnzahl() {
        Schlangenart art1 = new Schlangenart("Python", new Nachbarschaftsstruktur("Distanz", 1));
        Schlangenart art2 = new Schlangenart("Cobra", new Nachbarschaftsstruktur("Distanz", 1));
        art1.setAnzahl(5);
        art2.setAnzahl(3);

        schlangenarten.add(art1);
        schlangenarten.add(art2);

        assertEquals(8, schlangenarten.getAnzahl());
    }

    @Test
    @DisplayName("Test getSchlangenarten()")
    void testGetSchlangenarten() {
        Schlangenart art1 = new Schlangenart("Python", new Nachbarschaftsstruktur("Distanz", 1));
        Schlangenart art2 = new Schlangenart("Cobra", new Nachbarschaftsstruktur("Distanz", 1));

        schlangenarten.add(art1);
        schlangenarten.add(art2);

        ArrayList<Schlangenart> list = schlangenarten.getSchlangenarten();
        assertNotNull(list);
        assertEquals(2, list.size());
        assertEquals(art1, list.get(0));
        assertEquals(art2, list.get(1));
    }

    @Test
    @DisplayName("Test getSchlangeByIndex()")
    void testGetSchlangeByIndex() {
        Schlangenart art1 = new Schlangenart("Python", new Nachbarschaftsstruktur("Distanz", 1));
        Schlangenart art2 = new Schlangenart("Cobra", new Nachbarschaftsstruktur("Distanz", 1));

        schlangenarten.add(art1);
        schlangenarten.add(art2);

        assertEquals(art1, schlangenarten.getSchlangeByIndex(0));
        assertEquals(art2, schlangenarten.getSchlangeByIndex(1));
    }

    @Test
    @DisplayName("Test getSchlangeByArt()")
    void testGetSchlangeByArt() {
        Schlangenart art1 = new Schlangenart("Python", new Nachbarschaftsstruktur("Distanz", 1));
        Schlangenart art2 = new Schlangenart("Cobra", new Nachbarschaftsstruktur("Distanz", 1));

        schlangenarten.add(art1);
        schlangenarten.add(art2);

        assertEquals(art1, schlangenarten.getSchlangeByArt("A0"));
        assertEquals(art2, schlangenarten.getSchlangeByArt("A1"));
        assertNull(schlangenarten.getSchlangeByArt("A2"));
    }
}
