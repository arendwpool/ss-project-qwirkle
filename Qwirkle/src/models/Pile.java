package models;

import java.util.ArrayList;
import java.util.Collections;

public class Pile {
	
	private ArrayList<Tile> tiles;
	
	
	public Pile(){
		generateTiles();
		shuffle();
	
	}
	
	public void generateTiles(){
		tiles = new ArrayList<Tile>();
		for (int color = 0; color < Tile.NUMBER_COLORS * Tile.NUMBER_DUPLICATES; color++){
			for(int symbol = 0; symbol < Tile.NUMBER_SYMBOLS; symbol++){
				tiles.add(new Tile(Tile.kleuren[color%Tile.NUMBER_COLORS], Tile.symbolen[symbol]));
			}
		}
		
	}
	
	public void shuffle(){
		Collections.shuffle(tiles);
	}
	
	public void makeHand(){
		shuffle();
		ArrayList<Tile> hand = new ArrayList<Tile>();
		hand.add(tiles.get(0));
		tiles.remove(0);
	}
	
	public void setHand(){
		for(i = 0; i < (6 - hand.size()); i++){
			makeHand();
		}
	}
	
	public void removeTile(Tile tile){
		
		tiles.remove(0);
	}
	
	public void addTile(Tile tile){
		tiles.add(tile);
	}
	
	public void tilesLeft(){
		System.out.println(tiles.size());
	}
	
	public static void main(String[] args){
		Pile pile = new Pile();
		for(Tile tile : pile.tiles){
			System.out.println(tile.getColor() + tile.getSymbol());
		}
	}
}
