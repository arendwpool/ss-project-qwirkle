package models;

import java.util.ArrayList;

public class ComputerPlayer implements Player {
	private ArrayList<Tile> hand;
	
	public ComputerPlayer() {
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
		// TODO Auto-generated method stub
		return hand;
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void makeMove(int x, int y, Tile tile, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addScore(int points) {
		// TODO Auto-generated method stub
		
	}

}
