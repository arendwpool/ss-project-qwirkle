package models;

import java.util.ArrayList;
import util.TileUtils;

public abstract class Player{
	/**
	 * De naam van de betreffende speler.
	 */
	private String name;
	/**
	 * De tegels die de betreffende speler heeft.
	 */
	private ArrayList<Tile> hand;
	
	/**
	 * De score van de Speler.
	 */
	private int score = 0;
	public Player(String name) {
		this.name = name;
		hand = new ArrayList<Tile>();
		score = 0;
	}
	/**
	 *  geeft de naam van de speler.
	 *  @return this.name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Geeft een set met de tegels die de betreffende speler in zijn "hand" heeft.
	 * @return this.hand
	 */
	public ArrayList<Tile> getHand() {
		return hand;
	}

	
	/**
	 * Weergeeft de score van de speler.
	 * @return this.score
	 */
	public int getScore() {
		return score;
	}
	
	public int setScore(int score) {
		return this.score = score;
	}
	
	/**
	 * Wijzigt de score van de speler door er punten aan toe te voegen.
	 * @param points
	 */
	public void addScore(int points) {
		score += points;
	}
	
	public String[] determineMove() {
		return null;
		// TODO Auto-generated method stub
		
	}
	
	public String[] determineMove(String move) {
		return null;
	}

	public String[] determineTile(String tileNumber) {
		int tileNo = Integer.parseInt(tileNumber) - 1;
		Tile tile = getHand().get(tileNo);
		String[] toReturn = {Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
		return toReturn;
	}

}