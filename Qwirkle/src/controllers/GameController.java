package controllers;

import exceptions.FullGameException;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;
import models.Tile;
import view.TUI;

public class GameController {
	private Game game;
	private TUI ui;
	
	public GameController(){
		game = new Game(new Board(), new Pile(), 4);
		ui = new TUI(game);
	}
	public static void main(String[] args){
		GameController controller = new GameController();
		controller.start();
	}
	
	public void start(){
		ui.start();
	}
}
