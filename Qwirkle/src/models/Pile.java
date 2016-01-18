package models;

import java.util.ArrayList;
import java.util.Collections;

public class Pile {
	
	ArrayList<Tile> tiles;
	
	/**
	 * Aantal tegels in een spel.
	 */
	public static final int NUMBER_TILES = 108;
	/**
	 * het aantal verschillende symbolen van de tegels.
	 */
	public static final int NUMBER_SYMBOLS = 6;
	
	/**
	 * Het aantal verschillende kleluren van de tegels.
	 */
	public static final int NUMBER_COLORS = 6;
	
	/**
	 * Het aantal duplicate tegels in een spel.
	 */
	public static final int NUMBER_DUPLICATES = 3;
	
	
	
	
	public Pile() {
		generateTiles();
		shuffle();
	
	}
	
	public void generateTiles() {
		tiles = new ArrayList<Tile>();
		for (int color = 0; color < NUMBER_COLORS * NUMBER_DUPLICATES; color++) {
			for (int symbol = 0; symbol < NUMBER_SYMBOLS; symbol++) {
				tiles.add(new Tile(Tile.kleuren[color% NUMBER_COLORS], Tile.symbolen[symbol]));
			}
		}
		
	}
	
	public ArrayList<Tile> getPile(){
		return tiles;
	}
	
	public void shuffle() {
		Collections.shuffle(tiles);
	}
	
	public void tilesLeft() {
		System.out.println(tiles.size());
	}
	
	public static void main(String[] args) {
		Pile pile = new Pile();
		for (Tile tile : pile.tiles) {
			System.out.println(tile.getColor() + tile.getSymbol());
		}
	}
}
