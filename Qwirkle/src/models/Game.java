package models;

import java.util.ArrayList;
import java.util.Set;

import exceptions.FalseAmountOfTilesException;

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
	 * Genereer alle tegels die in een spel zitten. Dit zijn er 108: 3 van elke tegel.
	 */
	//@ensures this.tiles = new ArrayList<Tiles>();
	public void generateTiles(){
		tiles = new ArrayList<Tiles>();
		for (int color = 0; color < Tiles.NUMBER_COLORS * Tiles.NUMBER_DUPLICATES; color++){
			for(int symbol = 0; symbol < Tiles.NUMBER_SYMBOLS; symbol++){
				tiles.add(new Tiles(Tiles.kleuren[color%Tiles.NUMBER_COLORS], Tiles.symbolen[symbol]));
			}
		}
		
	}
	
	public boolean hasWinner(){
		//TODO implement: winnaar is de speler met de meeste punten als er geen tiles meer over zijn.
		return false;
	}
	
	/**
	 * Geeft aan of het spel over is.
	 * @return hasWinner();
	 */
	/*@pure*/
	public boolean gameOver(){
		return noTilesLeft();
	}
	
	public Player winner(){
		//TODO implement: return de speler met de meeste punten.
		return null;
	}
	
	public void tradeTiles(Player player, ArrayList<Tiles> tilesToTrade){
		if (noTilesLeft() == false){
			ArrayList<Tiles> tilesToGive = new ArrayList<Tiles>();
			for(Tiles tile : player.getTiles()){
				if (tilesToTrade.contains(tile)){
					tilesToGive.add(tile);
				}
			}
			try{
				player.replaceTiles(tilesToTrade, tilesToGive);
			}catch (FalseAmountOfTilesException e){
				;
			}
		}
	}
	
	
	public void start(){
		
	}
	
	public void update(){
		
	}
	
	public boolean isValidMove(){
		return false;
	}
	
	public ArrayList<Tiles> getTiles(){
		return tiles;
	}
	
	public boolean noTilesLeft(){
		return false;
	}
}
