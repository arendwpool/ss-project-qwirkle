package tests;

import static org.junit.Assert.*;

import util.TileUtils;
import org.junit.Before;
import org.junit.Test;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import models.Tile;
import network.Server;

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
	public void testSetHand(){
		/*TileUtils.setHand(testPlayer, testGame.getPile(), new Server(), testGame);
		assertEquals(102, testGame.getPile().getTiles().size());
		TileUtils.setHand(testPlayer2, testGame.getPile(), new Server(), testGame);
		assertEquals(96, testGame.getPile().getTiles().size());
		assertFalse(testPlayer.getHand().equals(testPlayer2.getHand()));*/ //TODO oplossen? kan niet getest worden vanwege broadcast in server
		
	}
	
	@Test
	public void testConversions() {
		assertEquals(4, TileUtils.colorToInt("groen"));
		assertEquals(1, TileUtils.symbolToInt("cirkel"));
		assertTrue(TileUtils.intToSymbol(1).equals("cirkel"));
	}
}
