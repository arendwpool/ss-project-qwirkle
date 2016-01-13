package models;

import java.util.ArrayList;

import exceptions.FalseAmountOfTilesException;
import exceptions.FullGameException;

/**
 * Abstracte klasse die een nieuwe speler maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public abstract class Player {
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
	private ArrayList<Tiles> tiles;
	
	/**
	 * De score van de Speler
	 */
	private int score;
	
	/**
	 * Cre�ert een nieuw object Player
	 */
	public Player(String name, Game game){
		this.name = name;
		this.game = game;
		score = 0;
		try{
			game.addPlayer(this);
		}catch (FullGameException e){
			//TODO implement
		}
		tiles = new ArrayList<Tiles>();
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
	public ArrayList<Tiles> getTiles(){
		return tiles;
	}
	
	/**
	 * Zet tegels in de tiles Set
	 */
	public void setTiles(ArrayList<Tiles> tilesToGive){
		tiles.addAll(tilesToGive);
	}
	
	/**
	 * Vervangt gegeven tegels met de tegels waarvoor deze vervangen worden
	 * @throws FalseAmountOfTilesException 
	 */
	public void replaceTiles(ArrayList<Tiles> tilesToReplace, ArrayList<Tiles> tilesToGive) throws FalseAmountOfTilesException{
		if (tilesToReplace.size() == tilesToGive.size()){
			tiles.removeAll(tilesToReplace);
			setTiles(tilesToGive);
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
