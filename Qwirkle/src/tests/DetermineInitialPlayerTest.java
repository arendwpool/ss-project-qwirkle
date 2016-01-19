package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import exceptions.FullGameException;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Player;
import models.Tile;

public class DetermineInitialPlayerTest {

	private Game testGame;
	@Before
	public void setUp() throws Exception {
		testGame = new Game(new Board(), 4);
		Player player1 = new HumanPlayer("1", testGame);
		Player player2 = new HumanPlayer("2", testGame);
		Player player3 = new HumanPlayer("3", testGame);
		Player player4 = new HumanPlayer("4", testGame);
		try {
			testGame.addPlayer(player1);
			testGame.addPlayer(player2);
			testGame.addPlayer(player3);
			testGame.addPlayer(player4);
			testGame.setHand(player1);
			testGame.setHand(player2);
			testGame.setHand(player3);
			testGame.setHand(player4);
			System.out.println(testGame.getPile().getTiles().size());
			for(Player player : testGame.getPlayers().keySet()){
				//TODO reparenen: om een of andere manier worden de tegels niet uit de pile verwijderd
				for(Tile tile : player.getHand()){
					System.out.println(player.getName() + ": " + tile.getColor()+tile.getSymbol());
				}
			}
		} catch (FullGameException e) {
			//Gebeurt niet in deze test
			System.out.println("TestNextPlayer vol");
		}
	}

	@Test
	public void testDetermineInitialPlayer(){
		testGame.determineInitialPlayer();
		Player player = testGame.getCurrentPlayer();
		System.out.println(player.getName());
		assertTrue(testGame.getPlayers().keySet().contains(player));
	}

}
