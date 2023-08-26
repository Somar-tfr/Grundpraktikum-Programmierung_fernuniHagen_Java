package de.fernuni.kurs01584.ss23.modell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SchlangengliedTest {

    private Schlangenglied schlangenglied;
    private Feld feld1;
    private Feld feld2;
    private Schlangenglied nextGlied;

    @BeforeEach
    void setUp() {
        schlangenglied = new Schlangenglied();
        feld1 = new Feld(2,1); // replace with your field initialization
        feld2 = new Feld(0,0); // replace with your field initialization
        nextGlied = new Schlangenglied(feld2);
    }

    @Test
    @DisplayName("Test des Standardkonstruktors")
    void testDefaultConstructor() {
        assertNull(schlangenglied.getNext());
        assertNull(schlangenglied.getFeld());
    }

    @Test
    @DisplayName("Test des Feld-Konstruktors")
    void testFeldConstructor() {
        Schlangenglied gliedWithFeld = new Schlangenglied(feld1);
        assertNull(gliedWithFeld.getNext());
        assertEquals(feld1, gliedWithFeld.getFeld());
    }

    @Test
    @DisplayName("Test der Methode setNext")
    void testSetNext() {
        schlangenglied.setNext(nextGlied);
        assertEquals(nextGlied, schlangenglied.getNext());
    }

    @Test
    @DisplayName("Test der Methode setFeld")
    void testSetFeld() {
        schlangenglied.setFeld(feld1);
        assertEquals(feld1, schlangenglied.getFeld());
    }
}
