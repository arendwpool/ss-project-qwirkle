package models;

import java.util.ArrayList;

/**
 * Klasse die het de regels van het spel implementeerd.
 * @author Bob Breemhaar en Arend Pool
 *
 */
public class Game {
	/*
	 * ArrayList met alle tegels in een spel.
	 */
	private ArrayList<Tiles> tiles;
	
	/**
	 * Geeft aan welke speler de volgende move moet maken.
	 */
	private Player currentPlayer;
	
	/*
	 * Geeft de hoeveelheid meespelende spelers mee. 
	 */
	private int noOfPlayers;
	
	/**
	 * Genereer alle tegels die in een spel zitten.
	 */
	public void generateTiles(){
		tiles = new ArrayList<Tiles>();
		for (int color = 0; color < Tiles.NUMBER_COLORS; color++){
			for(int symbol = 0; symbol < Tiles.NUMBER_SYMBOLS; symbol++){
				tiles.add(new Tiles(Tiles.kleuren[color], Tiles.symbolen[symbol]));
			}
		}
		
	}
	public static void main(String[] args){
		Game a = new Game();
		a.generateTiles();
		for(int i = 0; i < 36; i++){
		System.out.println(i+1 + a.tiles.get(i).getColor()+" "+ a.tiles.get(i).getSymbol());
		}
	}
	
	public void gameOver(){
		
	}
	
	public Player winner(){
		return null;
	}
	
	public void tradeTiles(){
		
	}
	
	public void start(){
		
	}
	
	public boolean isValidMove(){
		return false;
	}
}
