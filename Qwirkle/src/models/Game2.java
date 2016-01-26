package models;

import java.util.ArrayList;
import java.util.Map;

import exceptions.InvalidMoveException;
import util.MoveUtils;

public class Game2 {
	private int id;
	private ArrayList<Player2> players = new ArrayList<>();
	private Pile pile;
	private Board board;
	private ArrayList<Tile> tilesToSwap = new ArrayList<>();
	
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

	public ArrayList<ClientHandler2> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	public Pile getPile() {
		return pile;
	}

	public void makeMove(String x, String y, Tile tile) throws InvalidMoveException{
		// TODO Auto-generated method stub
		
	}
	
	private Player2 getPlayerByClient(String name) {
		for (Player2 pl : players) {
			if (pl.getName().equals(name)) {
				return pl;
			}
		}
		return null;
	}

	public void swapTile(String shape, String color, String name) {
		Player2 pl = getPlayerByClient(name);
		pl.getHand().remove(tile);
		tilesToSwap.add(tile);
		pile.getTiles().add(tile);
		Tile newTile = giveRandomTile(pile);
		pl.getHand().add(newTile);
		pile.getTiles().remove(newTile);
	}
	
	public void swapTiles() {
		tilesToSwap
	}
	
	public Tile giveRandomTile(Pile pile) {
		pile.shuffle();
		Tile tile = pile.getTiles().get(0);
		return tile;
	}

	public void finishMove(String name) throws InvalidMoveException {
		//MoveUtils.processMove(getPlayerByClient(name), this);
	}
	
	public ArrayList<Tile> getHandByPlayerName(String name) {
		return getPlayerByClient(name).getHand();
	}
	
	public Board getBoard() {
		return board;
	}
}
