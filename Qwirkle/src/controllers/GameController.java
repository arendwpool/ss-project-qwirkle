package controllers;

import java.util.ArrayList;

import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import models.Board;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import models.Tile;
import view.BoardTUI;
import view.StartTUI;

public class GameController {
	private Game game;
	private StartTUI ui;
	private BoardTUI bui;
	private Player player;
	private String ip;
	private String playerName;
	private static final String SEPERATOR = " ";
	
	public GameController(){
		ui = new StartTUI(this);
		game = new Game(new Board(), new Pile(), 2); // TODO veranderen
		bui = new BoardTUI(this, game);
		game.addObserver(ui);
		game.addObserver(bui);
	}
	public static void main(String[] args){
		GameController controller = new GameController();
		controller.start();
	}
	
	public BoardTUI getBoardTUI() {
		return bui;
	}
	
	public Player getCurrentPlayer(){
		return game.getCurrentPlayer();
	}
	
	public void start(){
		ui.start();
		createLocalPlayer(playerName);
		Player pc = new ComputerPlayer(game); // TODO weghalen later
		((ComputerPlayer) pc).addObserver(bui);
		((HumanPlayer) player).addObserver(bui);
		game.start();
		game.determineInitialPlayer();
		bui.start();
		updateGame();
	}

	public void updateGame(){
		while (game.gameOver() == false) {
			bui.showCurrentPlayer();
			game.getCurrentPlayer().determineMove();
			game.nextPlayer();
		}
	}
	
	public void done(Player player){
		try {
			game.finishMove(player);
		} catch (InvalidMoveException e) {
			e.printStackTrace();
			//System.out.println("er is iets verkeerd gegaan");
		}
	}
	
	public void createLocalPlayer(String name){
		player = new HumanPlayer(name, game);
	}
	
	public Player getLocalPlayer(){
		return player;
	}
	
	public Game getGame(){
		return game;
	}
	
	public void setIP(String ip){
		this.ip = ip;
	}
	
	public void setPlayerName(String name){
		this.playerName = name;
	}

	public boolean isValidIP(String ip){
		ip = ip.replace(".", " ");
		String[] ints = ip.split(" ");
		boolean isValidInt = true;
		for(String integer : ints){
			int i = Integer.parseInt(integer);
				if(i > 255){
					isValidInt = false;
				}
		}
		return (isValidInt == true && ints.length == 4);
	}
	
	public void determineSwap(String string, Player player) {
		ArrayList<Tile> tiles = player.getHand();
		String[] swapArray = string.split(SEPERATOR);
		ArrayList<Tile> tilesToSwap = new ArrayList<Tile>();
		for (int i = 0; i < swapArray.length; i++) {
			int tileNumber = Integer.parseInt(swapArray[i]) - 1;
			Tile tile = tiles.get(tileNumber);
			tilesToSwap.add(tile);
		}
		try {
			game.swapTiles(tilesToSwap, player);
		} catch (NoTilesLeftInPileException e) {
			System.out.println("Er zitten geen tegels meer in de zak");
		} catch (InvalidMoveException e) {
			System.out.println("U mag nu niet ruilen");
		}		
	}
}
