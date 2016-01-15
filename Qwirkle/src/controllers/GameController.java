package controllers;

import models.Board;
import models.Game;
import models.Tile;
import view.TUI;

public class GameController {
	private Game game;
	private TUI ui;
	
	public GameController(){
		game = new Game(new Board());
		ui = new TUI(game);
	}
	public static void main(String[] args){
		GameController controller = new GameController();
		controller.game.getBoard().setTile(89, 89, new Tile("groen", "circel"));
		controller.start();
	}
	
	public void start(){
		ui.start();
	}
}
