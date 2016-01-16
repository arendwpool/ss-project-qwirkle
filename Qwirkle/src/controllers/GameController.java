package controllers;

import exceptions.FullGameException;
import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Player;
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
		controller.game.getBoard().setTile(90, 90, new Tile("groen", "circel"));
		Player player = new HumanPlayer("arend", controller.game);
		try {
			controller.game.addPlayer(player);
		} catch (FullGameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		controller.game.getBoard().setTile(88, 92, new Tile("groen", "circel"));
		controller.game.getBoard().setTile(98, 82, new Tile("rood", "kruis"));
		controller.game.getBoard().boardSize();
		controller.start();
	}
	
	public void start(){
		ui.start();
	}
}
