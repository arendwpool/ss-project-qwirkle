package models;

import java.util.ArrayList;
import exceptions.FullGameException;


public class HumanPlayer implements Player {
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
	
	
	public HumanPlayer(String name, Game game) {
		hand = new ArrayList<Tile>();
		this.name = name;
		this.game = game;
		score = 0;
		try{
			game.addPlayer(this);
		}catch (FullGameException e){
			//TODO implement
		}
	}
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int determineMove() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public ArrayList<Tile> getHand(){
		return hand;
	}

	@Override
	public void makeMove(int x, int y, Tile tile) {
		game.makeMove(x, y, tile);
		
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void addScore(int points){
		score += points;
	}

}
