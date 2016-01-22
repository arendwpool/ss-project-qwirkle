package models;

import java.util.ArrayList;

import exceptions.InvalidMoveException;

public interface Player  {
	
	public String getName();
	
	public void determineMove();
	
	/**
	 * Geef een set met de tegels die de betreffende speler heeft
	 */
	ArrayList<Tile> getHand();
	
	public void makeMove(int x, int y, Tile tile) throws InvalidMoveException;
	
	public int getScore();
	
	public void addScore(int points);

}
