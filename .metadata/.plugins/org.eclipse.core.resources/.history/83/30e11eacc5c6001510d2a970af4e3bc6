package tests;

import static org.junit.Assert.*;

import util.TileUtils;
import org.junit.Before;
import org.junit.Test;
import models.Game;
import models.HumanPlayer;
import models.Player;
import models.Tile;

public class TileUtilsTest {
	private Player testPlayer;
	private Player testPlayer2;
	private Game testGame;

	@Before
	public void setUp() throws Exception {
		testGame = new Game(0);
		testPlayer = new HumanPlayer("Test");
		testPlayer2 = new HumanPlayer("Test2");
		testGame.addPlayer(testPlayer);
		testGame.addPlayer(testPlayer2);
	}
	
	@Test
	public void testGiveRandomTile(){
		Tile tile = TileUtils.giveRandomTile(testGame.getPile());
		assertTrue(tile != null);
	}
	
	@Test
	public void testConversions() {
		assertEquals(4, TileUtils.colorToInt("groen"));
		assertEquals(1, TileUtils.symbolToInt("cirkel"));
		assertTrue(TileUtils.intToSymbol(1).equals("cirkel"));
	}
}
