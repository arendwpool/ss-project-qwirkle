package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import models.Board;
import models.Tile;
import util.MoveUtils;

public class BoardTest {
	
	private Board testBoard;
	
	@Before
	public void setUp() throws Exception {
		
		testBoard = new Board();
	}
	
	@Test
	public void testGetField(){
		Tile tile = new Tile("groen", "circel");
		testBoard.setTile(90, 90, tile);
		testBoard.getField(90, 90);
		assertTrue(testBoard.getField(90, 90) == tile);
	}
	
	@Test
	public void testIsEmptyField(){
		Tile tile = new Tile("groen", "circel");
		assertTrue(testBoard.isEmptyField(90, 90));
		testBoard.setTile(90, 90, tile);
		assertFalse(testBoard.isEmptyField(90, 90));
	}
	
	@Test
	public void testTilesOnXAxis(){
		ArrayList<Tile> controleLijst = new ArrayList<Tile>();
		Tile tile1 = new Tile("groen", "circel");
		Tile tile2 = new Tile("groen", "ruit");
		Tile tile3 = new Tile("groen", "vierkant");
		Tile tile4 = new Tile("groen", "circel");
		testBoard.setTile(90, 90, tile1);
		testBoard.setTile(88, 90, tile2);
		testBoard.setTile(89, 90, tile3);
		testBoard.setTile(89, 89, tile4);
		testBoard.tilesOnXAxis(91, 90);
		controleLijst.add(tile1);
		controleLijst.add(tile3);
		controleLijst.add(tile2);
		//System.out.print(testBoard.tilesOnXAxis(90, 90));
		//System.out.print(controleLijst);
		assertTrue(controleLijst.equals(testBoard.tilesOnXAxis(90, 90)));
		controleLijst.add(tile4);
		assertFalse(controleLijst.equals(testBoard.tilesOnXAxis(90, 90)));
	}
	
	@Test
	public void testTilesOnYAxis(){
		ArrayList<Tile> controleLijst = new ArrayList<Tile>();
		Tile tile1 = new Tile("groen", "circel");
		Tile tile2 = new Tile("groen", "ruit");
		Tile tile3 = new Tile("groen", "vierkant");
		Tile tile4 = new Tile("groen", "circel");
		testBoard.setTile(90, 90, tile1);
		testBoard.setTile(88, 90, tile2);
		testBoard.setTile(89, 90, tile3);
		testBoard.setTile(89, 89, tile4);
		testBoard.tilesOnYAxis(89, 89);
		controleLijst.add(tile4);
		controleLijst.add(tile3);
		//System.out.print(testBoard.tilesOnYAxis(89, 89));
		//System.out.print(controleLijst);
		assertTrue(controleLijst.equals(testBoard.tilesOnYAxis(89, 89)));
		controleLijst.add(tile1);
		controleLijst.add(tile2);
		assertFalse(controleLijst.equals(testBoard.tilesOnYAxis(89, 89)));
	}
	
	@Test
	public void testSharedLine() {
		ArrayList<Tile> controleLijst = new ArrayList<Tile>();
		Tile tile1 = new Tile("groen", "circel");
		Tile tile2 = new Tile("groen", "ruit");
		Tile tile3 = new Tile("groen", "vierkant");
		Tile tile4 = new Tile("groen", "circel");
		testBoard.setTile(90, 90, tile1);
		testBoard.setTile(88, 89, tile2);
		testBoard.setTile(89, 90, tile3);
		testBoard.setTile(89, 89, tile4);
		MoveUtils.rememberMove(tile1);
		controleLijst.add(tile1);
		controleLijst.add(tile2);
		controleLijst.add(tile3);
		testBoard.sharedLine(tile2, controleLijst);	
		assertTrue(testBoard.sharedLine(tile2, controleLijst));
		assertFalse(testBoard.sharedLine(tile4, controleLijst));
		controleLijst.clear();
		MoveUtils.clearLastMoves();
		MoveUtils.rememberMove(tile3);
		controleLijst.add(tile3);
		controleLijst.add(tile4);
		assertTrue(testBoard.sharedLine(tile4, controleLijst));
		assertFalse(testBoard.sharedLine(tile1, controleLijst));
		
	}
	
	@Test
	public void testSetTile() {
		Tile tile = new Tile("groen", "circel");
		assertFalse(testBoard.getField(90, 90) == tile);
		testBoard.setTile(90, 90, tile);
		assertTrue(testBoard.getField(90, 90) == tile);
	}
	
	@Test
	public void testReset(){
		Tile tile1 = new Tile("groen", "circel");
		Tile tile2 = new Tile("groen", "ruit");
		Tile tile3 = new Tile("groen", "vierkant");
		Tile tile4 = new Tile("groen", "circel");
		testBoard.setTile(7, 11, tile1);
		testBoard.setTile(18, 43, tile2);
		testBoard.setTile(2, 65, tile3);
		testBoard.setTile(89, 8, tile4);
		boolean containsEmpty = true;
		for (int x = 0; x < Board.DIM; x++) {
			for (int y = 0; y < Board.DIM; y++) {
				if (!testBoard.isEmptyField(x, y)){
					containsEmpty = false;
					break;
				}
				
			}
		}
		assertFalse(containsEmpty);
		testBoard.reset();
		boolean allIsEmpty = true;
		for (int x = 0; x < Board.DIM; x++) {
			for (int y = 0; y < Board.DIM; y++) {
				if (!testBoard.isEmptyField(x, y)){
					allIsEmpty = false;
					break;
				}
				
			}
		}
		assertTrue(allIsEmpty);
	}
	
}