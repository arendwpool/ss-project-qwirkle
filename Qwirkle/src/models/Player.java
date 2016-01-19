package models;

import java.util.ArrayList;

public interface Player {
	
	public String getName();
	
	public int determineMove();
	
	ArrayList<Tile> getHand();
	
	public void makeMove(int x, int y, Tile tile, Player player);
	
	public int getScore();
	
	public void addScore(int points);

}
