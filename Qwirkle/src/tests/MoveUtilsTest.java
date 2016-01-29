package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Player;
import models.Tile;
import util.MoveUtils;

public class MoveUtilsTest {

	/**
	 * Declareert een Spel om de tests mee te draaien.
	 */
	private Game testGame;
	private Player testPlayer;
	
	@Before
	public void setUp() throws Exception {
		testGame = new Game(0);
		testPlayer = new HumanPlayer("test");
	}

	@Test
	public void testIsValidMove(){
		Board board = testGame.getBoard();
		testGame.getMoves().setInitialMove(false);
		board.setTile(90, 90, new Tile("groen", "cirkel"));
		assertTrue("De gelegde tegel is groen", board.getField(90, 90).getColor().equals("groen"));
		assertTrue("Deze move mag gelegd worden",MoveUtils.isValidMove(91, 90, new Tile("groen", "vierkant"), board));
		assertFalse("Deze tegel zit aan geen tegel vast", MoveUtils.isValidMove(98, 85, new Tile("rood", "ruit"), board));
		board.setTile(91, 90, new Tile("groen", "vierkant"));
		assertFalse("Een rode ruit mag hier niet", MoveUtils.isValidMove(90, 89, new Tile("rood", "ruit"), board));
		assertTrue("Een rode circel mag hier wel", MoveUtils.isValidMove(90, 89, new Tile("rood", "cirkel"), board));
		board.reset();
		board.setTile(90, 90, new Tile("groen", "plus"));
		board.setTile(90, 91, new Tile("groen", "ruit"));
		board.setTile(90, 92, new Tile("groen", "vierkant"));
		board.setTile(90, 93, new Tile("groen", "ster"));
		board.setTile(87, 89, new Tile("rood", "cirkel"));
		board.setTile(89, 89, new Tile("blauw", "cirkel"));
		board.setTile(88, 89, new Tile("oranje", "cirkel"));
		assertTrue(MoveUtils.isValidMove(90, 89, new Tile("groen", "cirkel"), board));
		assertFalse(MoveUtils.isValidMove(90,89, new Tile("groen", "ster"), board));
		board.reset();
		board.setTile(90, 90, new Tile("groen", "kruis"));
		board.setTile(91, 90, new Tile("groen", "cikel"));
		board.setTile(93, 90, new Tile("groen", "vierkant"));
		board.setTile(94, 90, new Tile("groen", "ster"));
		board.setTile(92, 89, new Tile("rood", "plus"));
		board.setTile(92, 88, new Tile("blauw", "plus"));
		board.setTile(92, 91, new Tile("oranje", "plus"));
		assertTrue(MoveUtils.isValidMove(92, 90, new Tile("groen", "plus"), board));
	}
	
	@Test 
	public void generateScore(){
		Board board = testGame.getBoard();
		board.reset();
		board.setTile(90, 90, new Tile("groen", "plus"));
		board.setTile(90, 91, new Tile("groen", "ruit"));
		board.setTile(90, 92, new Tile("groen", "vierkant"));
		board.setTile(90, 93, new Tile("groen", "ster"));
		board.setTile(87, 89, new Tile("rood", "cirkel"));
		board.setTile(89, 89, new Tile("blauw", "cirkel"));
		board.setTile(88, 89, new Tile("oranje", "cirkel"));
		Tile tile = new Tile("groen", "cirkel");
		board.setTile(90, 89, tile);
		testGame.getMoves().rememberMove(tile);
		testGame.getMoves().generateScore(testPlayer, board);
		assertEquals(9, testPlayer.getScore());
		board.reset();
		Player a = new HumanPlayer("asd");
		board.setTile(90, 91, new Tile("groen", "ruit"));
		board.setTile(90, 92, new Tile("groen", "vierkant"));
		board.setTile(90, 93, new Tile("groen", "ster"));
		Tile tile1 = new Tile("oranje", "vierkant");
		Tile tile2 = new Tile("oranje", "ster");
		Tile tile3 = new Tile("oranje", "cirkel");
		Tile tile4 = new Tile("oranje", "plus");
		board.setTile(91, 95, tile4);
		board.setTile(91, 92, tile1);
		board.setTile(91, 93, tile2);
		board.setTile(91, 94, tile3);
		testGame.getMoves().rememberMove(tile1);
		testGame.getMoves().rememberMove(tile2);
		testGame.getMoves().rememberMove(tile3);
		testGame.getMoves().rememberMove(tile4);
		testGame.getMoves().generateScore(a, board);
		assertEquals(8, a.getScore());
		board.reset();
		Player b = new HumanPlayer("asd");
		board.setTile(90, 90, new Tile("groen", "kruis"));
		board.setTile(91, 90, new Tile("groen", "cikel"));
		board.setTile(93, 90, new Tile("groen", "vierkant"));
		board.setTile(94, 90, new Tile("groen", "ster"));
		board.setTile(92, 89, new Tile("rood", "plus"));
		board.setTile(92, 88, new Tile("blauw", "plus"));
		board.setTile(92, 91, new Tile("oranje", "plus"));
		Tile gp = new Tile("groen", "plus");
		board.setTile(92, 90, gp);
		testGame.getMoves().rememberMove(gp);
		testGame.getMoves().generateScore(b, board);
		assertEquals(9, b.getScore());
		board.reset();
		Player c = new HumanPlayer("asd");
		board.setTile(90, 90, new Tile("groen", "kruis"));
		board.setTile(91, 90, new Tile("groen", "cikel"));
		board.setTile(92, 90, new Tile("groen", "vierkant"));
		board.setTile(93, 90,  gp);
		Tile gr = new Tile("groen", "ruit");
		board.setTile(94, 90, gr);
		testGame.getMoves().rememberMove(gr);
		testGame.getMoves().rememberMove(gp);
		testGame.getMoves().generateScore(c, board);
		assertEquals(5, c.getScore());
		board.reset();
		Player d = new HumanPlayer("asd");
		board.setTile(90, 90, new Tile("groen", "kruis"));
		board.setTile(91, 90, new Tile("groen", "cikel"));
		board.setTile(92, 90, new Tile("groen", "vierkant"));
		board.setTile(93, 90, new Tile("groen", "ruit"));
		board.setTile(94, 90, new Tile("groen", "plus"));
		board.setTile(95, 91, new Tile("rood", "ster"));
		Tile gs = new Tile("groen", "ster");
		board.setTile(95, 90, gs);
		testGame.getMoves().rememberMove(gs);
		testGame.getMoves().generateScore(d, board);
		assertEquals(14, d.getScore());
	}
	

	@Test
	public void testValidSharedLine(){
		Tile tile1 = new Tile("groen", "cirkel");
		Tile tile2 = new Tile("groen", "ruit");
		Tile tile3 = new Tile("groen", "vierkant");
		Tile tile4 = new Tile("groen", "ster");
		Tile tile5 = new Tile("rood", "ruit");
		Tile tile6 = new Tile("geel", "ruit");
		Tile tile7 = new Tile("groen", "plus");
		testGame.getBoard().setTile(90, 90, tile1);
		testGame.getBoard().setTile(89, 90, tile2);
		testGame.getBoard().setTile(88, 90, tile3);
		testGame.getBoard().setTile(87, 90, tile4);
		assertTrue(testGame.getMoves().validSharedLine(89, 91, tile5));
		testGame.getBoard().setTile(89, 91, tile5);
		testGame.getMoves().rememberMove(tile5);
		assertTrue(testGame.getMoves().validSharedLine(89, 89, tile6));
		testGame.getBoard().setTile(89, 89, tile6);
		testGame.getMoves().rememberMove(tile6);
		assertFalse(testGame.getMoves().validSharedLine(86, 90, tile7));
	}

}
