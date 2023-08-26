package de.fernuni.kurs01584.ss23.modell;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Die Testklasse demonstriert verschiedene Annotationen und Methoden fuer den
 * Lebenszyklus von Testinstanzen (BeforeAll, BeforeEach, AfterEach, AfterAll)
 * und fuer verschiedene Moeglichkeiten zur Implementierung von Tests (Test,
 * RepeatedTest, ParameterizedTest) sowie geschachtelte Tests (Nested).
 */
class FeldTest {

	@BeforeAll
	static void initAll() {
		System.out.println("Vor allen Testfaellen.");
	}

	@BeforeEach
	void init() {
		Feld feld = null;
		System.out.println("Vor jedem Testfall.");
	}

	@AfterEach
	void tearDown() {
		System.out.println("Nach jedem Testfall.");
	}

	@AfterAll
	static void tearDownAll() {
		System.out.println("Nach allen Testfaellen.");
	}
	
	@DisplayName("test FeldKonstruktor Mit Zeile Und Spalte")
	@Test
    void testFeldKonstruktorMitZeileUndSpalte() {
        int zeile = 3;
        int spalte = 5;

        Feld feld = new Feld(zeile, spalte);

        assertEquals(zeile, feld.getZeile());
        assertEquals(spalte, feld.getSpalte());
        assertEquals(' ', feld.getZeichen());
        assertEquals(1, feld.getVerwendbarkeit());
        assertEquals(1, feld.getPunkte());
    }

	@DisplayName("test FeldKonstruktor Mit Negativer Zeile")
    @Test
    void testFeldKonstruktorMitNegativerZeile() {
        int zeile = -1;
        int spalte = 5;

        assertThrows(IllegalArgumentException.class, () -> {
            new Feld(zeile, spalte);
        });
    }

	@DisplayName("testFeldKonstruktorMitNegativerSpalte")
    @Test
    void testFeldKonstruktorMitNegativerSpalte() {
        int zeile = 3;
        int spalte = -1;

        assertThrows(IllegalArgumentException.class, () -> {
            new Feld(zeile, spalte);
        });
    }
    
	@DisplayName("testFeldKonstruktorMitAllenParametern")
	@Test
    void testFeldKonstruktorMitAllenParametern() {
        int zeil = 2;
        int spalt = 4;
        int verwendbarkei = 3;
        int punkt = 5;
        char zeiche = ' ';

        Feld feld = new Feld(zeil, spalt, verwendbarkei, punkt);

        assertEquals(zeil, feld.getZeile());
        assertEquals(spalt, feld.getSpalte());
        assertEquals(verwendbarkei, feld.getVerwendbarkeit());
        assertEquals(punkt, feld.getPunkte());
    }

	@DisplayName("testFeldKonstruktorMitNegativenParametern")
    @Test
    void testFeldKonstruktorMitNegativenParametern() {
        int zeile = 2;
        int spalte = 4;
        int verwendbarkeit = -3;
        int punkte = 5;

        assertThrows(IllegalArgumentException.class, () -> {
            new Feld(zeile, spalte, verwendbarkeit, punkte);
        });
    }
    
	@DisplayName("testSetId")
    @Test
    void testSetId() {
        int zeile = 1;
        int spalte = 2;
        int spaltenImDschungel = 4;
        Feld feld = new Feld(zeile, spalte);
        feld.setId(spaltenImDschungel);

        assertEquals("F6", feld.getId());
    }

	@DisplayName("testSetIdMitNegativenSpaltenImDschungel")
    @Test
    void testSetIdMitNegativenSpaltenImDschungel() {
        int zeile = 1;
        int spalte = 2;
        int spaltenImDschungel = -4;
        Feld feld = new Feld(zeile, spalte);

        assertThrows(IllegalArgumentException.class, () -> {
            feld.setId(spaltenImDschungel);
        });
    }
    
	@DisplayName("testSetZeichenGesamt")
    @Test
    void testSetZeichenGesamt() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte);
        char zeichen = 'X';
        feld.setZeichen(zeichen);

        assertEquals(zeichen, feld.getZeichen());
    }
    
	@DisplayName("testSetPunkte")
    @Test
    void testSetPunkte() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte);
        int punkte = 5;
        feld.setPunkte(punkte);

        assertEquals(punkte, feld.getPunkte());
    }

	@DisplayName("testSetPunkteMitNegativemWert")
    @Test
    void testSetPunkteMitNegativemWert() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte);

        assertThrows(IllegalArgumentException.class, () -> {
            feld.setPunkte(-5);
        });
    }
    
	@DisplayName("testSetVerwendbarkeit")
    @Test
    void testSetVerwendbarkeit() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte);
        int verwendbarkeit = 3;
        feld.setVerwendbarkeit(verwendbarkeit);

        assertEquals(verwendbarkeit, feld.getVerwendbarkeit());
    }

	@DisplayName("testSetVerwendbarkeitMitNegativemWert")
    @Test
    void testSetVerwendbarkeitMitNegativemWert() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte);

        assertThrows(IllegalArgumentException.class, () -> {
            feld.setVerwendbarkeit(-3);
        });
    }
	
	@DisplayName("testVerwenden")
    @Test
    void testVerwenden() {
        int zeile = 1;
        int spalte = 2;
        int verwendbarkeit = 3;
        Feld feld = new Feld(zeile, spalte, verwendbarkeit, 5);
        feld.verwenden();

        assertEquals(verwendbarkeit - 1, feld.getVerwendbarkeit());
    }

	@DisplayName("testVerwendenMitNichtVerwendbaremFeld")
    @Test
    void testVerwendenMitNichtVerwendbaremFeld() {
        int zeile = 1;
        int spalte = 2;
        Feld feld = new Feld(zeile, spalte, 0, 5);

        assertThrows(IllegalArgumentException.class, () -> {
            feld.verwenden();
        });
    }


	@DisplayName("Einfacher positiver Test.")
	@Test
	void testeGetZeile() {
		int zeile = 1;
		Feld feld = new Feld(zeile, 0);
		assertEquals(feld.getZeile(), zeile, () -> "Der Zeilenwert '" + feld.getZeile()
				+ "' entspricht nicht dem vorgegebenen Wert '" + zeile + "'.");
	}

	@DisplayName("Einfacher negativer Test.")
	@Test
	void testeGetSpalte() {
		int spalte = -1;
		assertThrows(IllegalArgumentException.class, () -> new Feld(0, spalte),
				() -> "Fuer den (negativen) Spaltenwert '" + spalte + "' wird keine "
						+ "Ausnahme erzeugt.");
	}
	
	@DisplayName("Einfacher positiver getVerwendbarkeit Test")
	@Test
	void testNeutGetVerwendbarkeit() {
		int verwendbarkeit = 1;
		Feld feld = new Feld(0,0);
		assertEquals(feld.getVerwendbarkeit(), verwendbarkeit, () ->"Der Verwendbarkeit '" +
		feld.getVerwendbarkeit() + "'entspricht nicht dem vorgegebenen Wert'" +
				verwendbarkeit + "'.");
		
	}
	
	@DisplayName("Einfacher negativer getVerwendbarkeit Test")
	@Test
	void testNegGetVerwendbarkeit() {
		int verwendbarkeit = -1;
		assertThrows(IllegalArgumentException.class, () -> new Feld(0, 0,verwendbarkeit ,0),
				() -> "Fuer den (negativen) Verwendbarkeitswert '" + verwendbarkeit + "' wird keine Ausnahme erzeugt.");
	}
	
	@DisplayName("Einfacher aktiver getVerwendbarkeit Test")
	@Test
	void testAktGetVerwendbarkeit() {
		int verwendbarkeit = 1;
		Feld feld = new Feld(0,0,1, 0);
		assertEquals(feld.getVerwendbarkeit(), verwendbarkeit, 
				() ->"Der Verwendbarkeit '" + feld.getVerwendbarkeit() + "'entspricht nicht dem vorgegebenen Wert'" + verwendbarkeit + "'.");
	}
	
	@DisplayName("Einfacher setZeichen getZeichen Test")
	@Test
	void testSetZeichen() {
		char zeichen = 'a';
		Feld feld = new Feld(0,0);
		
		feld.setZeichen(zeichen);
		
		assertEquals(feld.getZeichen(), zeichen, () -> "Der Zeichen '" + feld.getZeichen() + "'entspricht nicht dem vorgegebenen Wert'" + zeichen + "'.");
	}
	
	@DisplayName("Einfacher setId getId Test")
	@Test
	void testGetId() {
		String id = "F0";
		int spalten = 1;
		Feld feld = new Feld(0,0);
		
		feld.setId(spalten);
		
		assertEquals(feld.getId(), id, () -> "Der Id '" + feld.getId() + "'entspricht nicht dem vorgegebenen Wert'" + id + "'.");
				
	}
	

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Wiederholte_Tests {
		static Random zufallszahlenGenerator;

		@BeforeAll
		static void initAll() {
			zufallszahlenGenerator = new Random(1);
		}

		@DisplayName("Wiederholter negativer Test.")
		@RepeatedTest(10)
		void testeGetSpalte(RepetitionInfo repetitionInfo) {
			int spalte = zufallszahlenGenerator.nextInt(-100000, -1);
			assertThrows(IllegalArgumentException.class, () -> new Feld(0, spalte),
					() -> "Fuer den (negativen) Spaltenwert '" + spalte + "' wird keine Ausnahme erzeugt.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parametrisierte_Tests {
		@DisplayName("Parameterisierter positiver Test.")
		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 5, 7, 11, 64, 1024 })
		void testeKonstruktorUndGetSpaltePositiv(int spalte) {
			Feld feld = new Feld(0, spalte);
			assertEquals(feld.getSpalte(), spalte, "Der Spaltenwert '" + feld.getSpalte()
					+ "' entspricht nicht dem vorgegebenen Wert '" + spalte + "'.");
		}

		static IntStream erzeugeNegativeParameterwerte() {
			return IntStream.iterate(-1, i -> i - 10).limit(20);
		}

		@DisplayName("Parameterisierter negativer Test.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorUndGetZeileNegativ(int zeile) {
			assertThrows(IllegalArgumentException.class, () -> new Feld(zeile, 0),
					() -> "Fuer den (negativen) Zeilenwert '" + zeile + "' wird keine Ausnahme erzeugt.");
		}
	}
	
}
