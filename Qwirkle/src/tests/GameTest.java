package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.FullGameException;
import exceptions.NoTilesLeftInPileException;
import exceptions.PlayerNotFoundException;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Player;
import models.Tile;

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
	//TODO testen op traden van stenen die een speler niet heeft, en op een lege pile
	public void testReplaceTiles(){
		ArrayList<Tile> tilesToTrade = new ArrayList<Tile>();
		testGame.setHand(testPlayer);
		tilesToTrade.add(testPlayer.getHand().get(0));
		tilesToTrade.add(testPlayer.getHand().get(1));
		tilesToTrade.add(testPlayer.getHand().get(2));
		for(Tile tile : testPlayer.getHand()){
			System.out.println("first: " + tile.getColor()+tile.getSymbol());
		}
		try {
			testGame.replaceTiles(tilesToTrade, testPlayer);
		} catch (NoTilesLeftInPileException e) {
			//Gebeurt niet in deze test
			System.out.print("FOUT");
		}
		for(Tile tile : testPlayer.getHand()){
			System.out.println("after: " + tile.getColor()+tile.getSymbol());
		}
	}
	
	@Test
	public void testGiveRandomTile(){
		int size = testGame.getPile().getTiles().size();
		Tile tile = testGame.giveRandomTile();
		assertTrue(tile != null);
	}
	
	@Test
	public void testSetHand(){
		assertTrue("De hand is leeg", testPlayer.getHand().size() == 0);
		testGame.setHand(testPlayer);
		System.out.println(testGame.getPile().getTiles().size());
		assertFalse("De hand heeft geen null tegels", testPlayer.getHand().contains(null));
		assertTrue("De hand heeft 6 tegels", testPlayer.getHand().size() == 6);
		System.out.println();
		assertTrue("De pile heeft nu 6 tegels minder", testGame.getPile().getTiles().size() == 102);
		Player testPlayer2 = new HumanPlayer("test2", testGame);
		testGame.setHand(testPlayer2);
		for(Tile tile : testPlayer.getHand()){
			System.out.println(tile.getColor()+tile.getSymbol());
		}
		for(Tile tile : testPlayer.getHand()){
			System.out.println(tile.getColor()+tile.getSymbol());
		}
		assertTrue("De tweede speler heeft nu ook 6 tegels", testPlayer2.getHand().size() == 6);
		assertTrue("De pile heeft nu 6 tegels minder", testGame.getPile().getTiles().size() == 96);
		//TODO werkt nog niet?!!
		int noOfEqual = 0;
		for(int i = 0; i < testPlayer.getHand().size(); i++){
			if(testPlayer2.getHand().contains(testPlayer.getHand().get(i))){
				noOfEqual++;
			}
		}
		System.out.println(noOfEqual);
		assertTrue(noOfEqual<testPlayer.getHand().size());
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
	
	//TODO te testen: generateScore, finishMove, noTilesLeft, hasWinner, winner en gameOver
}
