package localgame;

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
	
	public void generateTiles(){
		
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
