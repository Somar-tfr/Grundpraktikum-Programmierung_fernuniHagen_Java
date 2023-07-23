package de.fernuni.kurs01584.ss23.modell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dschungel Tests")
public class DschungelTest {

    @Test
    @DisplayName("Test Dschungel constructor")
    public void testDschungelConstructor() {
        Dschungel dschungel = new Dschungel(10, 10, "abc");
        
        assertAll("Properties correctness",
            () -> assertEquals(10, dschungel.getZeilen(), "The number of rows should be 10"),
            () -> assertEquals(10, dschungel.getSpalten(), "The number of columns should be 10"),
            () -> assertEquals("abc", dschungel.getZeichenMenge(), "The character set should be 'abc'"),
            () -> assertEquals(100, dschungel.getFelderAnzahl(), "The total fields should be 100"),
            () -> assertEquals(10, dschungel.getMatrix().size(), "Matrix should have 10 rows"),
            () -> assertTrue(dschungel.getMatrix().stream().allMatch(row -> row.size() == 10), "Each row should have 10 columns")
        );
    }

    @Test
    @DisplayName("Test Dschungel constructor exception handling")
    public void testDschungelConstructorThrowsException() {
        assertAll("Exception handling",
            () -> assertThrows(IllegalArgumentException.class, () -> new Dschungel(-1, 10, "abc"), "Exception for negative rows"),
            () -> assertThrows(IllegalArgumentException.class, () -> new Dschungel(10, 0, "abc"), "Exception for zero columns"),
            () -> assertThrows(IllegalArgumentException.class, () -> new Dschungel(10, 10, ""), "Exception for empty character set")
        );
    }

    @Test
    @DisplayName("Test if field exists in Dschungel")
    public void testFeldExistiert() {
        Dschungel dschungel = new Dschungel(10, 10, "abc");

        assertAll("Field existence",
            () -> assertTrue(dschungel.feldExistiert(5, 5), "Field at (5,5) should exist"),
            () -> assertFalse(dschungel.feldExistiert(10, 10), "Field at (10,10) should not exist")
        );
    }

    @Test
    @DisplayName("Test getFeld and getFeldById methods")
    public void testGetFeldMethods() {
        Dschungel dschungel = new Dschungel(10, 10, "abc");
        Feld feld = dschungel.getFeld(5, 5);

        assertAll("Get field methods",
            () -> assertEquals(feld, dschungel.getFeldById(feld.getId()), "getFeldById should return the same field as getFeld")
        );
    }

}