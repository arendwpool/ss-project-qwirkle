package models;

import java.util.ArrayList;
import exceptions.FullGameException;

public class HumanPlayer implements Player {
	/**
	 * De naam van de betreffende speler.
	 */
	private String name;
	
	/**
	 * Koppelt de speler aan een spel.
	 */
	private Game game;
	/**
	 * De tegels die de betreffende speler heeft.
	 */
	static ArrayList<Tile> hand;
	
	/**
	 * De score van de Speler.
	 */
	private int score;
	
	/**
	 * construeert een nieuwe menselijke speler. Deze speler krijgt een hand met tiles mee.
	 * Elke speler met een score van 0. Uiteindelijk wordt de speler aan het spel toegevoegd.
	 * @param name
	 * @param game
	 */
	public HumanPlayer(String name, Game game) {
		hand = new ArrayList<Tile>();
		this.name = name;
		this.game = game;
		score = 0;
		try {
			game.addPlayer(this);
		} catch (FullGameException e) {
			//TODO implement
		}
	}
	
	/**
	 *  geeft de naam van de speler.
	 *  @return this.name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Geeft een suggestie move aan een speler, zoals de computer het zou doen.
	 * De speler hoeft zich echter niet aan suggestie te houden.
	 */
	@Override
	public int determineMove() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Geeft een set met de tegels die de betreffende speler in zijn "hand" heeft.
	 * @return this.hand
	 */
	@Override
	public ArrayList<Tile> getHand() {
		return hand;
	}

	/**
	 * laat de speler een Move maken. Hierbij wordt de x en y as,
	 * de tile die de speler neer wilt leggen en de speler zelf gebruikt.
	 * @param x
	 * @param y
	 * @param tile
	 * @param player
	 */
	@Override
	public void makeMove(int x, int y, Tile tile, Player player) {
		game.makeMove(x, y, tile, player);
		
	}

	/**
	 * Weergeeft de score van de speler.
	 * @return this.score
	 */
	@Override
	public int getScore() {
		return score;
	}

	/**
	 * Wijzigt de score van de speler door er punten aan toe te voegen.
	 * @param points
	 */
	@Override
	public void addScore(int points) {
		score += points;
	}



}
