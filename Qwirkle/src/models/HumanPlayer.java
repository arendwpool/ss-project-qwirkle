package models;

import java.util.ArrayList;

<<<<<<< HEAD
<<<<<<< HEAD
	public HumanPlayer(String name, Game game) {
		super(name, game);
=======
=======
>>>>>>> ab909275468af63fc03589089e0ce6ca35df6be8
public class HumanPlayer implements Player {
	private ArrayList<Tile> hand;
	
	public HumanPlayer(String name) {
		hand = new ArrayList<Tile>();
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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
>>>>>>> 090ee0f4d912ba26aff9fe83735a012ca4cdc742
=======
=======
	public HumanPlayer(String name, Game game) {
		super(name, game);
>>>>>>> 8._basic_TUI_implementatie_voor_eigen_relevante_methoden
>>>>>>> ab909275468af63fc03589089e0ce6ca35df6be8
	}

}
