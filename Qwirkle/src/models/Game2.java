package models;

import java.util.ArrayList;
import exceptions.InvalidMoveException;
import util.MoveUtils;
import util.TileUtils;

public class Game2 {
	private int id;
	private ArrayList<Player2> players = new ArrayList<>();
	private Pile pile;
	private Board board;
	private ArrayList<Tile> tilesToSwap = new ArrayList<>();
	private ArrayList<Tile> lastSet = new ArrayList<>();
	private Player2 currentPlayer;
	/**
	 * Bepaalt of het de eerste move van het spel iets of niet.
	 */
	private static boolean initialMove = true;
	
	public Game2(int id) {
		this.id = id;
		pile = new Pile();
		board = new Board();
		System.out.println("Game has started with id: " + id);
	}
	
	public int getId() {
		return id;
	}

	public void addPlayer(String name) {
		players.add(new Player2(name));
	}

	public ArrayList<Player2> getPlayers() {
		return players;
	}

	public Pile getPile() {
		return pile;
	}

	public void makeMove(String xString, String yString, Tile tile, String name) throws InvalidMoveException{
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(xString);
		Player2 player = getPlayerByClient(name);
		if (tilesToSwap.size() == 0) {
			if (MoveUtils.isValidMove(x, y, tile, board) && board.validSharedLine(x, y, tile)) {
				board.setTile(x, y, tile);
				lastSet.add(tile);
				player.getHand().remove(tile);
			} 
		} else if(initialMove == true){
			board.setTile(90, 90, tile);
			lastSet.add(tile);
			player.getHand().remove(tile);
			initialMove = false;
		} else {
			throw new InvalidMoveException();
		}
		
	}
	
	public Player2 getPlayerByClient(String name) {
		for (Player2 pl : players) {
			if (pl.getName().equals(name)) {
				return pl;
			}
		}
		return null;
	}
	
	public boolean tileInHand(String shape, String color, String name) {
		Tile newTile = new Tile(color, shape);
		Player2 pl = getPlayerByClient(name);
		if (pl.getHand().contains(newTile)) {
			return true;
		}
		return false;
	}
	
	public void swapTile(String shape, String color, String name) throws InvalidMoveException {
		if (lastSet.size() == 0 && initialMove == false && pile.getTiles().size() > 0) { 
			Tile newTile = new Tile(color, shape);
			tilesToSwap.add(newTile);
			Player2 pl = getPlayerByClient(name);
			pl.getHand().remove(newTile);
		} else {
			throw new InvalidMoveException();
		}
	}

	public Tile giveRandomTile(Pile pile) {
		pile.shuffle();
		Tile tile = pile.getTiles().get(0);
		return tile;
	}

	public void finishMove(String name) throws InvalidMoveException {
		if(lastSet.size() > 0 ){
			MoveUtils.generateScore(getPlayerByClient(name), board);
			lastSet.clear();
		} else if (tilesToSwap.size() > 0) {
			pile.getTiles().addAll(tilesToSwap);
			TileUtils.setHand(getPlayerByClient(name), pile);
		}
	}
	
	public ArrayList<Tile> getHandByPlayerName(String name) {
		return getPlayerByClient(name).getHand();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void nextPlayer() {
		int current = players.indexOf(currentPlayer);
		int next = (current + 1) % players.size();
		currentPlayer = players.get(next);
	}
	
	/**
	 * Geeft de speler terug die nu aan de beurt is.
	 * @return this.currentPlayer
	 */
	public Player2 getCurrentPlayer() {
		return currentPlayer;
	}

	public void start() {
		determineInitialPlayer();
	}
	
	/**
	 * Zet de lokale variabele currentPlayer naar een speler die het spel mag beginnen. Dit is de 
	 * speler die in het begin de langste rij kan neerleggen op het bord. Deze methode kijkt eerst 
	 * naar alle tegels die de spelers in hun hand hebben en gaat op deze manier na welke speler mag
	 * beginnen.
	 */
	public void determineInitialPlayer() {	
		int longestRow = 0;
		Player2 withLongestRow = null;
		for (Player2 player : players) {
			ArrayList<Tile> hand = player.getHand();
			for (int i = 0; i < player.getHand().size(); i++) {
				ArrayList<Tile> colors = new ArrayList<Tile>();
				ArrayList<Tile> symbols = new ArrayList<Tile>();
				colors.add(player.getHand().get(i));
				symbols.add(player.getHand().get(i));
				for (int j = 0; j < player.getHand().size(); j++) {
					if (!colors.contains(hand.get(j))) {
						if (hand.get(j).getColor().equals(hand.get(i).getColor())) {
							colors.add(hand.get(j));
						}
					}
					if (!symbols.contains(hand.get(j))){
						if (hand.get(j).getSymbol().equals(hand.get(i).getSymbol())) {
							symbols.add(hand.get(j));
						}
					}
				}
				if (colors.size() > symbols.size()) {
					if (colors.size() > longestRow) {
						longestRow = colors.size();
						withLongestRow = player;
					}
				} else {
					if (symbols.size() > longestRow) {
						longestRow = symbols.size();
						withLongestRow = player;
					}
				}
			}
		}
		currentPlayer = withLongestRow;
	}
}