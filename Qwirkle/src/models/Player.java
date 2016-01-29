package models;

import java.util.ArrayList;
import util.TileUtils;

/**
 * Abstractie van een speler
 * @author Arend Pool en Bob Breemhaar
 *
 */
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
	
	/**
	 * Zet de score van een speler op een gegeven waarde.
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Wijzigt de score van de speler door er punten aan toe te voegen.
	 * @param points
	 */
	public void addScore(int points) {
		score += points;
	}
	
	/**
	 * Bepaalt wat de volgende zet wordt van de speler.
	 * @return null
	 */
	public String[] determineMove() {
		return null;
		
	}
	
	/**
	 * Vertaalt een gegeven zet naar integers die zowel de server als client begrijpen.
	 * @param move
	 * @return
	 */
	public String[] determineMove(String move) {
		return null;
	}

	/**
	 * Bepaalt van een gegeven integer welke tegel daarbij hoort.
	 * @param tileNumber
	 * @return toReturn
	 */
	public String[] determineTile(String tileNumber) {
		int tileNo = Integer.parseInt(tileNumber) - 1;
		Tile tile = getHand().get(tileNo);
		String[] toReturn = {Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
		return toReturn;
	}

}