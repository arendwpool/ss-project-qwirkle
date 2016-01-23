package controllers;

import java.util.ArrayList;

import exceptions.InvalidMoveException;
import models.Board;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import view.BoardTUI;
import view.StartTUI;

public class GameController {
	private Game game;
	private StartTUI ui;
	private BoardTUI bui;
	private Player player;
	private String ip; //TODO doorgeven aan server
	private String playerName;
	private boolean isHuman;
	private boolean done;
	private boolean quit;
	
	public GameController(){
		game = new Game(new Board(), new Pile());
		ui = new StartTUI(this);
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
	
	public void setQuit(boolean bl){
		quit = bl;
	}
	
	public void start(){
		ui.start();
		if (isHuman == true) {
		createLocalPlayer(playerName);
		((HumanPlayer) player).addObserver(bui);
		} else {
			Player pc = new ComputerPlayer(game);
			((ComputerPlayer) pc).addObserver(bui);
		}
		Player pc2 = new ComputerPlayer(game); //Voor tests
		((ComputerPlayer) pc2).addObserver(bui);
		game.start();
		game.determineInitialPlayer();
		if (quit == false) {
			bui.start();
			updateGame();
		}
	}

	public void updateGame(){
		while (game.gameOver() == false) {
			bui.showCurrentPlayer();
			while (true) {
				game.getCurrentPlayer().determineMove();
				if (done == true) {
					break;
				}
			}
			done = false;
			game.gameOver();
			game.nextPlayer();
		}
		bui.showEndGame();
	}
	
	public void done(Player player){
		try {
			game.finishMove(player);
			done = true;
		} catch (InvalidMoveException e) {
			System.out.println("U moet eerst een zet maken, of ruilen...");
		}
	}
	
	public void createLocalPlayer(String name){
		player = new HumanPlayer(name, game);
	}
	
	public void isHuman(boolean bl) {
		isHuman = bl;
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
	
}
