package de.fernuni.kurs01584.ss23.modell;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SchlangenartTest {

    private Schlangenart schlangenart;

    @BeforeAll
    static void initAll() {
        // Gemeinsame Initialisierung für alle Tests (falls notwendig)
    }

    @BeforeEach
    void init() {
        // Vor jedem Test: Neue Schlangenart erstellen
        Nachbarschaftsstruktur nachbarschaftsstruktur = new Nachbarschaftsstruktur("Distanz" , 1);
        schlangenart = new Schlangenart("ABC", nachbarschaftsstruktur);
    }

    @AfterEach
    void tearDown() {
        // Nach jedem Test: Schlangenart zurücksetzen (falls notwendig)
        schlangenart = null;
    }

    @AfterAll
    static void tearDownAll() {
        // Aufräumarbeiten nach allen Tests (falls notwendig)
    }

    @DisplayName("testGetZeichenkette")
    @Test
    void testGetZeichenkette() {
        assertEquals("ABC", schlangenart.getZeichenkette());
    }

    @DisplayName("testGetPunkte")
    @Test
    void testGetPunkte() {
        assertEquals(0, schlangenart.getPunkte());
    }

    @DisplayName("testGetAnzahl")
    @Test
    void testGetAnzahl() {
        assertEquals(0, schlangenart.getAnzahl());
    }

    @DisplayName("testGetId")
    @Test
    void testGetId() {
        schlangenart.setID("ID_123");
        assertEquals("ID_123", schlangenart.getId());
    }

    @DisplayName("testGetNachbarschaftsstruktur")
    @Test
    void testGetNachbarschaftsstruktur() {
        // Teste, ob die Nachbarschaftsstruktur nicht null ist
        assertNotNull(schlangenart.getNachbarschaftsstruktur());
    }

    @DisplayName("testGetSize")
    @Test
    void testGetSize() {
        assertEquals(3, schlangenart.getSize());
    }

    @DisplayName("testSetPunkte")
    @Test
    void testSetPunkte() {
        schlangenart.setPunkte(100);
        assertEquals(100, schlangenart.getPunkte());
    }

    @DisplayName("testSetAnzahl")
    @Test
    void testSetAnzahl() {
        schlangenart.setAnzahl(5);
        assertEquals(5, schlangenart.getAnzahl());
    }
}