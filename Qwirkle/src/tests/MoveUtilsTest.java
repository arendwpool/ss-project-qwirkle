package tests;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import models.Tile;
import util.MoveUtils;
import util.TileUtils;

public class MoveUtilsTest {

	/**
	 * Declareert een Spel om de tests mee te draaien.
	 */
	private Game testGame;
	private Player testPlayer;
	
	@Before
	public void setUp() throws Exception {
		Pile pile = new Pile();
		testGame = new Game(new Board(), pile, 4);
		testPlayer = new HumanPlayer("test", testGame);
	}
	@Test
	
	//TODO testen op traden van stenen die een speler niet heeft, en op een lege pile
	public void testReplaceTiles(){
		ArrayList<Tile> tilesToTrade = new ArrayList<Tile>();
		TileUtils.setHand(testPlayer, testGame.getPile());
		tilesToTrade.add(testPlayer.getHand().get(0));
		tilesToTrade.add(testPlayer.getHand().get(1));
		tilesToTrade.add(testPlayer.getHand().get(2));
		for(Tile tile : testPlayer.getHand()){
			System.out.println("first: " + tile.getColor()+tile.getSymbol());
		}
		try {
			MoveUtils.replaceTiles(tilesToTrade, testPlayer, testGame.getPile());
		} catch (NoTilesLeftInPileException e) {
			//Gebeurt niet in deze test
			System.out.print("FOUT");
		} catch (InvalidMoveException e) {
			//TODO testen als iets invalid is
		}
		for(Tile tile : testPlayer.getHand()){
			System.out.println("after: " + tile.getColor()+tile.getSymbol());
		}
	}
	
	//TODO bobs testen toevoegen

}
