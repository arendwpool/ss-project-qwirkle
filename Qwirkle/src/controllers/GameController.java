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

/**
 * De Controller die alle klassen met elkaar laat samenwerken
 * @author Arend Pool en Bob breemhaar
 *
 */
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
	
	/**
	 * Constructor die een nieuw spel instantieerd, twee TUI's instantieerd en deze TUI's
	 * als observers maakt van het gemaakt spel.
	 */
	public GameController(){
		game = new Game(new Board(), new Pile());
		ui = new StartTUI(this);
		bui = new BoardTUI(this, game);
		game.addObserver(ui);
		game.addObserver(bui);
	}
	/**
	 * De main methode van het spel.
	 * @param args
	 */
	public static void main(String[] args){
		GameController controller = new GameController();
		controller.start();
	}
	
	/**
	 * Geeft het BoardTUI instantie terug.
	 * @return bui
	 */
	public BoardTUI getBoardTUI() {
		return bui;
	}
	
	/**
	 * Geeft aan welke speler de volgende zet mag maken.
	 * @return game.getCurrentPlayer()
	 */
	public Player getCurrentPlayer(){
		return game.getCurrentPlayer();
	}
	
	/**
	 * Zet de boolean quit naar een gegeven waarde. Met deze waarde wordt bepaald
	 * of het programma moet afsluiten of doorgaan.
	 * @param bl
	 */
	public void setQuit(boolean bl){
		quit = bl;
	}
	
	/**
	 * Start de controller. Eerst wordt StartTUI gestart om gegevens van de speler
	 * te acterhalen. Als een speler aangeeft een menselijke speler te willen 
	 * zijn wordt een HumanPlayer instantie aangemaakt. Anders zal er een 
	 * ComputerPlayer worden gemaakt. Hierna zal een spel worden gestart en wordt
	 * er bepaald welke speler de eerste zet mag maken. Daarna wordt het BoardTUI
	 * gestart en wordt het spel geupdated.
	 */
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

	/**
	 * Update het spel. Zolang het spel niet over is zal er aan een speler worden 
	 * gevraagd wat zijn volgende move zal zijn. Als een speler heeft aangegeven
	 * klaar te zijn, wordt er gekeken of het spel over is, en zal de volgende
	 * speler zijn beurt kunnen maken.
	 */
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
	
	/**
	 * Deze methode wordt aangeroepen als een speler heeft aangegeven klaar te zijn
	 * met zijn beurt. Als finishMove een InvalidMoveException gooit wordt er eerst
	 * aan de speler gemaakt dat deze iets moet doen voordat hij door kan gaan.
	 * @param player
	 */
	public void done(Player player){
		try {
			game.finishMove(player);
			done = true;
		} catch (InvalidMoveException e) {
			System.out.println("U moet eerst een zet maken, of ruilen...");
		}
	}
	
	/**
	 * Maakt met een gegeven naam een lokale, menselijke speler aan.
	 * @param name
	 */
	public void createLocalPlayer(String name){
		player = new HumanPlayer(name, game);
	}
	
	/**
	 * Deze methode zet de boolean isHuman op true of false.
	 * @param bl
	 */
	public void isHuman(boolean bl) {
		isHuman = bl;
	}
	
	/*
	 * Geeft de lokale speler terug.
	 */
	public Player getLocalPlayer(){
		return player;
	}
	
	/*
	 * Geeft de spelinstantie terug.
	 */
	public Game getGame(){
		return game;
	}
	
	/**
	 * Zet het ip-adres op een gegeven ip.
	 * @param ip
	 */
	public void setIP(String ip){
		this.ip = ip;
	}
	
	/**
	 * Zet de naam van de lokale speler op een gegeven naam.
	 * @param name
	 */
	public void setPlayerName(String name){
		this.playerName = name;
	}

	/**
	 * Controlleerd of een gegeven IP adres wel legitiem is.
	 * @param ip
	 * @return isValidInt == true && ints.length == 4
	 */
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
