package models;

import java.util.ArrayList;

<<<<<<< HEAD
	public HumanPlayer(String name, Game game) {
		super(name, game);
=======
public class HumanPlayer implements Player {
	private ArrayList<Tile> hand;
	
	public HumanPlayer(String name) {
		
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
	public ArrayList<Tile> getHand() {
		
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
		
>>>>>>> 090ee0f4d912ba26aff9fe83735a012ca4cdc742
	}

}
