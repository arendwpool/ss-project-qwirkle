package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import models.Game;
import models.HumanPlayer;
import models.Player;
import models.Tile;
import util.TileUtils;

/**
 * 
 * @author Bob Breemhaar en Arend Pool
 *
 */
public class GameTest {

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
	public void testNextPlayer() {
		Player test2 = new HumanPlayer("1");
		Player test3 = new HumanPlayer("2");
		Player test4 = new HumanPlayer("3");
		testGame.addPlayer(testPlayer);
		testGame.addPlayer(test2);
		testGame.addPlayer(test3);
		testGame.addPlayer(test4);
		testGame.setCurrentPlayer(testPlayer);
		assertTrue("De eerste speler is \"test\".", testGame.getCurrentPlayer().getName().equals(
							testPlayer.getName()));
		testGame.nextPlayer();
		assertTrue("De eerste speler is \"2\".", testGame.getCurrentPlayer().getName().equals(
							test2.getName()));
		testGame.nextPlayer();
		assertTrue("De eerste speler is \"3\".", testGame.getCurrentPlayer().getName().equals(
							test3.getName()));
		testGame.nextPlayer();
		assertTrue("De eerste speler is \"4\".", testGame.getCurrentPlayer().getName().equals(
							test4.getName()));
		testGame.nextPlayer();
		assertTrue("De eerste speler is \"test\" (tweede keer).", testGame.getCurrentPlayer().
							getName().equals(testPlayer.getName()));

		Game game = new Game(0);
		Player test5 = new HumanPlayer("5");
		Player test6 = new HumanPlayer("6");
		game.addPlayer(test5);
		game.addPlayer(test6);
		game.setCurrentPlayer(test5);
		assertTrue("De eerste speler is \"5\".", game.getCurrentPlayer().
							getName().equals(test5.getName()));
		game.nextPlayer();
		assertTrue("De eerste speler is \"6\".", game.getCurrentPlayer().
							getName().equals(test6.getName()));
		game.nextPlayer();
		assertTrue("De eerste speler is \"5\".", game.getCurrentPlayer().
							getName().equals(test5.getName()));
	}
	
	@Test
	public void testGiveRandomTile(){
		Tile tile = testGame.giveRandomTile(testGame.getPile());
		assertTrue(tile != null);
	}
	
	@Test
	public void testDetermineInitialPlayer(){
		Player player1 = new HumanPlayer("1");
		Player player2 = new HumanPlayer("2");
		Player player3 = new HumanPlayer("3");
		Tile[] a = {new Tile ("groen", "circel"), new Tile ("rood", "circel"), new Tile ("blauw", "circel"),
				new Tile ("paars", "vierkant"), new Tile ("groen", "ruit"), new Tile ("geel", "circel")};
		for(Player player : testGame.getPlayers()){
			for(Tile tile : player.getHand()){
				System.out.println(player.getName() + ": " + tile.getColor()+tile.getSymbol());
			}
		}
		testGame.determineInitialPlayer();
		Player player = testGame.getCurrentPlayer();
		System.out.println(player.getName());
		assertTrue(player != null);
	}
}
