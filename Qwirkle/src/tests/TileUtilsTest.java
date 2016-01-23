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

public class TileUtilsTest {
	private Player testPlayer;
	private Player testPlayer2;
	private Game testGame;

	@Before
	public void setUp() throws Exception {
		Pile pile = new Pile();
		testGame = new Game(new Board(), pile);
		testPlayer = new HumanPlayer("Test", testGame);
		testPlayer2 = new HumanPlayer("Test2", testGame);
	}
	
	@Test
	public void testGiveRandomTile(){
		Tile tile = TileUtils.giveRandomTile(testGame.getPile());
		assertTrue(tile != null);
	}
	
	@Test
	public void testSetHand(){
		TileUtils.setHand(testPlayer, testGame.getPile());
		assertEquals(102, testGame.getPile().getTiles().size());
		TileUtils.setHand(testPlayer2, testGame.getPile());
		assertEquals(96, testGame.getPile().getTiles().size());
		assertFalse(testPlayer.getHand().equals(testPlayer2.getHand()));
		
	}
}
