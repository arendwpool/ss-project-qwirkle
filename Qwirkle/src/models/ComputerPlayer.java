package models;

import java.util.ArrayList;

import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import util.MoveUtils;

public class ComputerPlayer extends Observable implements Player {
	private ArrayList<Tile> hand;
	private final String name = "Computer";
	private int score;
	
	/**
	 * Koppelt de speler aan een spel.
	 */
	private Game game;
	
	public ComputerPlayer(Game game) {
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
			for(Tile tile: hand){
				for(int x = 0; x < Board.DIM; x++){
					for(int y = 0; y < Board.DIM; y++){
						if(MoveUtils.isValidMove(x, y, tile, game.getBoard())){
							makeMove(x, y, tile);
							System.out.println("PC heeft wat gedaan");
						}
					}
				}/*
				if(MoveUtils.madeMove() == false){
					try {
						MoveUtils.replaceTiles(hand, this, game.getPile());
					} catch (NoTilesLeftInPileException | InvalidMoveException e) {
						// TODO Auto-generated catch block
					}
				}*/
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
	

	@Override
	public void addScore(int points) {
		score += points;		
	}
	public void signalController(){
		setChanged();
		notifyObservers("PCMadeMove");
	}
}
