package de.fernuni.kurs01584.ss23.algorithmus;

import de.fernuni.kurs01584.ss23.modell.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DschungelGeneratorTest {

    private DschungelGenerator generator;
    private Schlangenarten schlangenarten;

    @BeforeEach
    public void setup() {
        // Hier initialisieren wir das Schlangenarten-Objekt. Das wird angenommen und sollte entsprechend angepasst werden.
        schlangenarten = new Schlangenarten();
        generator = new DschungelGenerator(5, 5, "abc", schlangenarten);
    }

    @Test
    @DisplayName("Test for invalid arguments in constructor")
    public void testConstructor_WithInvalidArguments_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new DschungelGenerator(-1, 5, "abc", schlangenarten));
        assertThrows(IllegalArgumentException.class, () -> new DschungelGenerator(5, -1, "abc", schlangenarten));
        assertThrows(IllegalArgumentException.class, () -> new DschungelGenerator(5, 5, null, schlangenarten));
        assertThrows(IllegalArgumentException.class, () -> new DschungelGenerator(5, 5, "abc", null));
    }

    @Test
    @DisplayName("Test for generating a valid Dschungel")
    public void testErzeugeDschungel_WithValidArguments_ShouldReturnValidDschungel() {
        Dschungel dschungel = generator.erzeugeDschungel();
        assertNotNull(dschungel);
        assertEquals(5, dschungel.getZeilen());
        assertEquals(5, dschungel.getSpalten());
    }

    @Test
    @DisplayName("Test for checking if all fields are filled")
    public void testErzeugeDschungel_ShouldFillAllFields() {
        Dschungel dschungel = generator.erzeugeDschungel();
        for (int i = 0; i < dschungel.getZeilen(); i++) {
            for (int j = 0; j < dschungel.getSpalten(); j++) {
                Feld feld = dschungel.getFeld(i, j);
                assertTrue(feld.getZeichen() != ' ');
            }
        }
    }

    
}
