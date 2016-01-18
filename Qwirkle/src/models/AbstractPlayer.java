package models;

import java.util.ArrayList;

import exceptions.FalseAmountOfTilesException;
import exceptions.FullGameException;

/**
 * Abstracte klasse die een nieuwe speler maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public abstract class AbstractPlayer {
	/**
	 * De naam van de betreffende speler.
	 */
	private String name;
	
	/**
	 * Koppelt de speler aan een spel
	 */
	private Game game;
	/**
	 * De tegels die de betreffende speler heeft
	 */
	static ArrayList<Tile> hand;
	
	/**
	 * De score van de Speler
	 */
	private int score;
	
	/**
	 * Cre�ert een nieuw object Player
	 */
	public AbstractPlayer(String name, Game game){
		this.name = name;
		this.game = game;
		score = 0;
		try{
			game.addPlayer(this);
		}catch (FullGameException e){
			//TODO implement
		}
		hand = new ArrayList<Tile>();
		
	}
	
	/**
	 * Geef de naam van de betreffende speler
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Geef een set met de tegels die de betreffende speler heeft
	 */
	public static ArrayList<Tile> getHand(){
		return hand;
	}
	
	/**
	 * Vervangt gegeven tegels met de tegels waarvoor deze vervangen worden
	 * @throws FalseAmountOfTilesException 
	 */
	public void replaceTiles(ArrayList<Tile> tilesToTrade, ArrayList<Tile> tilesToGive) throws FalseAmountOfTilesException{
		tilesToTrade.add(hand.get(0 /*TODO dit zouden wij fixen via split met TUI?*/ )) ;
		if (tilesToTrade.size() <= 6 && tilesToTrade.size() >= 0){
			game.replaceTiles(tilesToTrade);
		}else{
			throw new FalseAmountOfTilesException();
		}
	}
	
	public int getScore(){
		return score;
	}
	
	public void addScore(int points){
		score += points;
	}
}
