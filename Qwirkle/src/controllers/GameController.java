package controllers;

import exceptions.FullGameException;
import models.Board;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import models.Tile;
import view.BoardTUI;
import view.StartTUI;
import view.TUI;

public class GameController {
	private Game game;
	private StartTUI ui;
	private BoardTUI bui;
	private Player player;
	private String ip;
	private String playerName;
	
	public GameController(){
		ui = new StartTUI(this);
		game = new Game(new Board(), new Pile(), 2);
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
	
	public void start(){
		ui.start();
	}
	
	public void createLocalPlayer(String name){
		player = new HumanPlayer(name, game);
	}
	
	public Player getLocalPlayer(){
		return player;
	}
	public void startGame(){
		game.start();
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
	
	public String getPlayerName(){
		return playerName;
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
