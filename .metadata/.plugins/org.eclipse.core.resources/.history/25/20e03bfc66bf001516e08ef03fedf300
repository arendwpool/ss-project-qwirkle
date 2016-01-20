package tests;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import models.Board;
import models.Tile;

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
		assertTrue(testBoard.isEmptyField(90, 90) == true);
		testBoard.setTile(90, 90, tile);
		assertFalse(testBoard.isEmptyField(90, 90) == true);
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
		System.out.print(testBoard.tilesOnXAxis(90, 90));
		System.out.print(controleLijst);
		assertTrue(controleLijst == testBoard.tilesOnXAxis(90, 90));
		controleLijst.add(tile4);
		assertFalse(controleLijst ==  testBoard.tilesOnXAxis(90, 90));
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
		System.out.print(testBoard.tilesOnYAxis(89, 89));
		System.out.print(controleLijst);
		assertTrue(controleLijst == testBoard.tilesOnYAxis(89, 89));
		controleLijst.add(tile1);
		controleLijst.add(tile2);
		assertFalse(controleLijst ==  testBoard.tilesOnYAxis(89, 89));
	}
}