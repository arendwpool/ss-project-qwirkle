package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import models.Pile;

public class PileTest {
	
	private Pile testPile;
	
	private String[] generatedTiles = {"roodcirkel", "roodkruis", "roodruit", "roodvierkant", "roodster", "roodplus",
			"oranjecirkel", "oranjekruis", "oranjeruit", "oranjevierkant", "oranjester", "oranjeplus", "geelcirkel", 
			"geelkruis", "geelruit", "geelvierkant", "geelster", "geelplus", "groencirkel", "groenkruis", "groenruit", 
			"groenvierkant", "groenster", "groenplus", "blauwcirkel", "blauwkruis", "blauwruit", "blauwvierkant", "blauwster",
			"blauwplus", "paarscirkel", "paarskruis", "paarsruit", "paarsvierkant", "paarsster", "paarsplus", "roodcirkel", 
			"roodkruis", "roodruit", "roodvierkant", "roodster", "roodplus", "oranjecirkel", "oranjekruis", "oranjeruit", 
			"oranjevierkant", "oranjester", "oranjeplus", "geelcirkel", "geelkruis", "geelruit", "geelvierkant", "geelster", 
			"geelplus", "groencirkel", "groenkruis", "groenruit", "groenvierkant", "groenster", "groenplus", "blauwcirkel", 
			"blauwkruis", "blauwruit", "blauwvierkant", "blauwster", "blauwplus", "paarscirkel", "paarskruis", "paarsruit",
			"paarsvierkant", "paarsster", "paarsplus", "roodcirkel", "roodkruis", "roodruit", "roodvierkant", "roodster", 
			"roodplus", "oranjecirkel", "oranjekruis", "oranjeruit", "oranjevierkant", "oranjester", "oranjeplus", "geelcirkel", 
			"geelkruis", "geelruit", "geelvierkant", "geelster", "geelplus", "groencirkel", "groenkruis", "groenruit",
			"groenvierkant", "groenster", "groenplus", "blauwcirkel", "blauwkruis", "blauwruit", "blauwvierkant", 
			"blauwster", "blauwplus", "paarscirkel", "paarskruis", "paarsruit", "paarsvierkant", "paarsster", "paarsplus"};
	
	@Before
	public void setUp() throws Exception {
		/**
		 * instantieert testPile
		 */
		testPile = new Pile();
	}
	
	/**
	 * test de methode generateTiles van de klasse Game. Omdat de gegenereerde lijst uit Tiles bestaat,
	 * zetten we de lijst om in een textuele representatie.
	 */
	@Test
	public void testGenerateTiles() {
		testPile.generateTiles();
		String[] tilesToString = new String[Pile.NUMBER_TILES];
		for(int i = 0; i < testPile.getTiles().size(); i++){
			String tile = testPile.getTiles().get(i).getColor() + testPile.getTiles().get(i).getSymbol();
			tilesToString[i] = tile;
		}
		assertArrayEquals(tilesToString, generatedTiles);
	}
	
	@Test
	public void testShuffle() {
		testPile.generateTiles();
		testPile.shuffle();
		String[] tilesToString = new String[Pile.NUMBER_TILES];
		for(int i = 0; i < testPile.getTiles().size(); i++){
			String tile = testPile.getTiles().get(i).getColor() + testPile.getTiles().get(i).getSymbol();
			tilesToString[i] = tile;
		}
		assertNotEquals(tilesToString, generatedTiles);
		System.out.print(tilesToString[1] + " " + tilesToString[2] + " " +  tilesToString[3]);
		System.out.print("\n" +generatedTiles[1] + " " + generatedTiles[2] + " " + generatedTiles[3]);
	}
	
	@Test
	public void testTilesLeft(){
		
		assertEquals(generatedTiles.length, Pile.NUMBER_TILES);
		testPile.generateTiles();
		testPile.getTiles().remove(0);
		assertNotEquals(testPile.getTiles().size(), Pile.NUMBER_TILES);
	}
}

