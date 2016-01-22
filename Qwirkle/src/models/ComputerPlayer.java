package models;

import java.util.ArrayList;
import java.util.Observable;

import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import util.MoveUtils;

public class ComputerPlayer extends Observable implements Player {
	private ArrayList<Tile> hand;
	private final String name = "Computer";
	private int score;
	public boolean moveMade = false;
	
	/**
	 * Koppelt de speler aan een spel.
	 */
	private Game game;
	
	public ComputerPlayer(Game game) {
		hand = new ArrayList<Tile>();
		this.game = game;
		score = 0;
		try {
			game.addPlayer(this);
		} catch (FullGameException e) {
			//TODO implement
		}
	}
	
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void determineMove() {
		if (game.getBoard().isEmptyField(90, 90)) {
			makeMove(90, 90, hand.get(0));
		} else {
			tileLoop:
			for(Tile tile: hand){
				for(int x = 1; x < Board.DIM-1; x++){
					for(int y = 1; y < Board.DIM-1; y++){
						if (!game.getBoard().isEmptyField(x, y)) {
							if(MoveUtils.isValidMove((x+1), (y), tile, game.getBoard())){
								makeMove(x+1, y, tile);
								break tileLoop;
							} else if (MoveUtils.isValidMove(x-1, y, tile, game.getBoard())) {
								makeMove(x-1, y, tile);
								break tileLoop;
							} else if (MoveUtils.isValidMove(x, y-1, tile, game.getBoard())) {
								makeMove(x, y-1, tile);
								break tileLoop;
							} else if (MoveUtils.isValidMove(x, y+1, tile, game.getBoard())) {
								makeMove(x, y+1, tile);
								break tileLoop;
							}
						}
					}
				}
			}
			if(MoveUtils.madeMove() == false){
				try {
					ArrayList<Tile> toTrade = new ArrayList<Tile>();
					toTrade.add(hand.get(1));
					MoveUtils.replaceTiles(toTrade, this, game.getPile());
					signalController();
				} catch (NoTilesLeftInPileException | InvalidMoveException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}	
	@Override
	public ArrayList<Tile> getHand() {
		return hand;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void makeMove(int x, int y, Tile tile) {
		try {
			game.makeMove(x, y, tile, this);
			signalController();
		} catch (InvalidMoveException e) {
			// TODO implementeren
		}
	}

	public void signalController(){
		setChanged();
		notifyObservers("MadeMove");
	}
	@Override
	public void addScore(int points) {
		score += points;		
	}

}
