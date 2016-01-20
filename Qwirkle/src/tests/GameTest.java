package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import exceptions.FullGameException;
import exceptions.PlayerNotFoundException;
import models.Board;
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
		testGame = new Game(new Board(), 4);
		testPlayer = new HumanPlayer("test", testGame);
	}
	
	@Test
	public void testAddPlayer(){
		try{
			testGame.addPlayer(testPlayer);
			testGame.addPlayer(new HumanPlayer("naam", testGame));//TODO parameter game weghalen)
			testGame.addPlayer(new HumanPlayer("andere", testGame));
			testGame.addPlayer(new HumanPlayer("test", testGame));
		}catch(FullGameException e){
			System.out.println("testAddPlayer vol");
			assertTrue(1==2);
		}
		assertTrue("Er zijn nu 4 spelers", testGame.getPlayers().keySet().size() == 4);
		try{
			testGame.addPlayer(new HumanPlayer("error", testGame));
		}catch(FullGameException e){
			assertTrue("Er is een error", e != null);
		}try{
			testGame.addPlayer(new HumanPlayer("error", testGame));
		}catch(FullGameException e){
			assertTrue("Er is een error", e != null);
		}
	}
	
	@Test
	public void testNextPlayer(){
		try {
			testGame.addPlayer(testPlayer);
			testGame.addPlayer(new HumanPlayer("1", testGame));
			testGame.addPlayer(new HumanPlayer("2", testGame));
			testGame.addPlayer(new HumanPlayer("3", testGame));
		} catch (FullGameException e) {
			//Gebeurt niet in deze test
			System.out.println("TestNextPlayer vol");
		}
		testGame.setCurrentPlayer(testPlayer);
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
	}
	
	@Test
	public void testDetermineInitialPlayer(){
		Player player1 = new HumanPlayer("1", testGame);
		Player player2 = new HumanPlayer("2", testGame);
		Player player3 = new HumanPlayer("3", testGame);
		try {
			testGame.addPlayer(player1);
			testGame.addPlayer(player2);
			testGame.addPlayer(player3);
			testGame.addPlayer(testPlayer);
			TileUtils.setHand(player1, testGame.getPile());
			TileUtils.setHand(player2, testGame.getPile());
			TileUtils.setHand(player3, testGame.getPile());
			TileUtils.setHand(testPlayer, testGame.getPile());
			System.out.println(testGame.getPile().getTiles().size());
			for(Player player : testGame.getPlayers().keySet()){
				//TODO reparenen: om een of andere manier worden de tegels niet uit de pile verwijderd
				for(Tile tile : player.getHand()){
					System.out.println(player.getName() + ": " + tile.getColor()+tile.getSymbol());
				}
			}
		} catch (FullGameException e) {
			//Gebeurt niet in deze test
			
		testGame.determineInitialPlayer();
		Player player = testGame.getCurrentPlayer();
		System.out.println(player.getName());
		assertTrue(testGame.getPlayers().keySet().contains(player));
		}
	
	//TODO te testen: generateScore, finishMove, noTilesLeft, hasWinner, winner, start, getPlayerID update en gameOver
	}
}
