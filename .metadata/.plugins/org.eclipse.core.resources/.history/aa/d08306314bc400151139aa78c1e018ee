package models;

import java.util.ArrayList;

public class Player2 {
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
	
	/**
	 * construeert een nieuwe menselijke speler. Deze speler krijgt een hand met tiles mee.
	 * Elke speler met een score van 0. Uiteindelijk wordt de speler aan het spel toegevoegd.
	 * @param name
	 * @param game
	 */
	public Player2(String name) {
		hand = new ArrayList<Tile>();
		this.name = name;
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

	/**
	 * Wijzigt de score van de speler door er punten aan toe te voegen.
	 * @param points
	 */
	public void addScore(int points) {
		score += points;
	}
}