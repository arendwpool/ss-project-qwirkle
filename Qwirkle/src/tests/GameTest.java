package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import exceptions.FullGameException;
import exceptions.PlayerNotFoundException;
import models.Board;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import models.Pile;
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
		Pile pile = new Pile();
		testGame = new Game(new Board(), pile);
		testPlayer = new HumanPlayer("test", testGame);
	}
	
	@Test
	public void testNextPlayer(){
		Player test2 = new HumanPlayer("1", testGame);
		Player test3 = new HumanPlayer("2", testGame);
		Player test4 = new HumanPlayer("3", testGame);
		testGame.setCurrentPlayer(testPlayer);
		System.out.println(testGame.getPlayers());
		try{
			assertTrue("De eerste speler heeft ID 1", testGame.getPlayerID(testGame.getCurrentPlayer()) == 1);
			testGame.nextPlayer();
			assertTrue("De volgende speler heeft ID 2", testGame.getPlayerID(testGame.getCurrentPlayer()) == 2);
			testGame.nextPlayer();
			assertTrue("De volgende speler heeft ID 3", testGame.getPlayerID(testGame.getCurrentPlayer()) == 3);
			testGame.nextPlayer();
			assertTrue("De volgende speler heeft ID 4", testGame.getPlayerID(testGame.getCurrentPlayer()) == 4);
			testGame.nextPlayer();
			assertTrue("De volgende speler heeft ID 1", testGame.getPlayerID(testGame.getCurrentPlayer()) == 1);
			testGame.nextPlayer();
			assertTrue("De volgende speler heeft ID 2(2de keer)", testGame.getPlayerID(testGame.getCurrentPlayer()) == 2);
		}catch(PlayerNotFoundException e){
			assertTrue("Speler is gevonden", 1 == 2);
		}

		Game game = new Game(new Board(), new Pile());
		Player test5 = new HumanPlayer("2", game);
		Player test6 = new HumanPlayer("2", game);
		game.setCurrentPlayer(test5);
		try {
			System.out.println(game.getPlayerID(game.getCurrentPlayer()));
			assertTrue("De volgende speler heeft ID 1", game.getPlayerID(game.getCurrentPlayer()) == 1);
			game.nextPlayer();
			System.out.println(game.getPlayerID(game.getCurrentPlayer()));
			assertTrue("De volgende speler heeft ID 2", game.getPlayerID(game.getCurrentPlayer()) == 2);
			game.nextPlayer();
			System.out.println(game.getPlayerID(game.getCurrentPlayer()));
			assertTrue("De volgende speler heeft ID 1", game.getPlayerID(game.getCurrentPlayer()) == 1);
			game.nextPlayer();
			System.out.println(game.getPlayerID(game.getCurrentPlayer()));
			assertTrue("De volgende speler heeft ID 2", game.getPlayerID(game.getCurrentPlayer()) == 2);
		} catch (PlayerNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDetermineInitialPlayer(){
		Player player1 = new HumanPlayer("1", testGame);
		Player player2 = new HumanPlayer("2", testGame);
		Player player3 = new ComputerPlayer(testGame);
		TileUtils.setHand(player1, testGame.getPile());
		TileUtils.setHand(player2, testGame.getPile());
		TileUtils.setHand(player3, testGame.getPile());
		TileUtils.setHand(testPlayer, testGame.getPile());
		for(Player player : testGame.getPlayers()){
			for(Tile tile : player.getHand()){
				System.out.println(player.getName() + ": " + tile.getColor()+tile.getSymbol());
			}
		}
		testGame.determineInitialPlayer();
		Player player = testGame.getCurrentPlayer();
		System.out.println(player.getName());
		assertTrue(player != null);
	
	//TODO te testen: generateScore, finishMove, noTilesLeft, hasWinner, winner, start, getPlayerID update en gameOver
	}
}
