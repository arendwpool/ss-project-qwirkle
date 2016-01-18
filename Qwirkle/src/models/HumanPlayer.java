package models;

import java.util.ArrayList;

<<<<<<< HEAD
import exceptions.FullGameException;

=======
<<<<<<< HEAD
<<<<<<< HEAD
	public HumanPlayer(String name, Game game) {
		super(name, game);
=======
=======
>>>>>>> ab909275468af63fc03589089e0ce6ca35df6be8
>>>>>>> 62713e245ffb390cc7d25517b009b4097b153fa4
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
		this.game = game;
		this.name = name;
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
	public void makeMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addScore() {
		// TODO Auto-generated method stub
		
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 090ee0f4d912ba26aff9fe83735a012ca4cdc742
=======
=======
	public HumanPlayer(String name, Game game) {
		super(name, game);
>>>>>>> 8._basic_TUI_implementatie_voor_eigen_relevante_methoden
>>>>>>> ab909275468af63fc03589089e0ce6ca35df6be8
>>>>>>> 62713e245ffb390cc7d25517b009b4097b153fa4
	}

}
