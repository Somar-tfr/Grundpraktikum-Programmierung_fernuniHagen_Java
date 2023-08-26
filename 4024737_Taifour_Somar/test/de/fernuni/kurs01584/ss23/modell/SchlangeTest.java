package de.fernuni.kurs01584.ss23.modell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SchlangeTest {

    private Schlange schlange;

    @BeforeAll
    static void initAll() {
        // Gemeinsame Initialisierung für alle Tests (falls notwendig)
    }

    @BeforeEach
    void init() {
        // Vor jedem Test: Neue Schlange erstellen
        schlange = new Schlange();
    }

    @AfterEach
    void tearDown() {
        // Nach jedem Test: Schlange zurücksetzen (falls notwendig)
        schlange = null;
    }

    @AfterAll
    static void tearDownAll() {
        // Aufräumarbeiten nach allen Tests (falls notwendig)
    }

    @Test
    @DisplayName("Test getKopf()")
    void testGetKopf() {
        assertNotNull(schlange.getKopf());
    }

    @Test
    @DisplayName("Test getPunkte()")
    void testGetPunkte() {
        assertEquals(0, schlange.getPunkte());
    }

    @Test
    @DisplayName("Test getLength()")
    void testGetLength() {
        assertEquals(0, schlange.getLength());
    }

    @Test
    @DisplayName("Test getArt()")
    void testGetArt() {
        schlange.setArt("Python");
        assertEquals("Python", schlange.getArt());
    }

    @Test
    @DisplayName("Test setKopf()")
    void testSetKopf() {
        Schlangenglied neuerKopf = new Schlangenglied();
        schlange.setKopf(neuerKopf);
        assertEquals(neuerKopf, schlange.getKopf());
    }

    @Test
    @DisplayName("Test setPunkte()")
    void testSetPunkte() {
        schlange.setPunkte(100);
        assertEquals(100, schlange.getPunkte());
    }
}