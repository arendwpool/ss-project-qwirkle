package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import models.Player;
import models.ServerPlayer;
import models.Tile;
import util.MoveUtils;
import util.TileUtils;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import view.BoardTUI;
import view.StartTUI;
import view.TUI;

public class Client extends Thread{	
	private TUI ui = new StartTUI();
	private int currentQuestion = 0;
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Ik ben een Menselijke speler", "Ik ben een Computerspeler"};
	private static final String[] IP_MENU = {null, "Voer het gewenste ip adres in:"};
	private static final String[] PORT_MENU = {"Toets een gewenst portnummer in", "typ 0 voor de standaard poort: "};
	private static final String[] END_MENU = {"Het spel is afgelopen, toets 1 om nog een keer, elke andere toets om te stoppen", "Ik wil weer in de wachtrij"};
	private boolean terminated = false;
	private boolean isHuman = false;
	private String ip;
	private String playerName;
	private int port = 1337;
	private BufferedReader in;
	private BufferedWriter out;
	private Socket socket;
	private Player localPlayer;
	private Game game;
	private TUI bui;
	private boolean gameStarted = false;
	private Tile tileToBeSwapped;
	
	public static void main(String[] arsg) {
		Client client = new Client();
		client.startClient();
	}
	public synchronized void getServerMessage(String msg) {
		String[] slicedMessage = msg.split(Protocol.MESSAGESEPERATOR);
		switch(slicedMessage[0]) {
			case Protocol.SERVER_CORE_JOIN_ACCEPTED: joinAccepted(slicedMessage[1]);
			break;
			case Protocol.SERVER_CORE_START: starting(slicedMessage);
			break;
			case Protocol.SERVER_CORE_START_DENIED: startDenied();
			break;
			case Protocol.SERVER_CORE_TURN: turn(slicedMessage[1]);
			break;
			case Protocol.SERVER_CORE_SEND_TILE: receiveTile(slicedMessage[1], slicedMessage[2]);
			break;
			case Protocol.SERVER_CORE_DONE: done();
			break;
			case Protocol.SERVER_CORE_SWAP_ACCEPTED: swapAccepted();
			break;
			case Protocol.SERVER_CORE_SWAP_DENIED: swapDenied();
			break;
			case Protocol.SERVER_CORE_GAME_ENDED: gameEnded(slicedMessage);
			break;
			case Protocol.SERVER_CORE_TIMEOUT_EXCEPTION: exception(slicedMessage[2]);
			break;
			case Protocol.SERVER_CORE_MOVE_ACCEPTED: moveAccepted();
			break;
			case Protocol.SERVER_CORE_MOVE_DENIED: moveDenied();
			break;
			case Protocol.SERVER_CORE_MOVE_MADE: moveMade(slicedMessage[1], slicedMessage[2], slicedMessage[3], slicedMessage[4]);
			break;
			case Protocol.SERVER_CORE_SCORE: score(slicedMessage);
			break;
			case Protocol.SERVER_CORE_PLAYERS: sendPlayers(slicedMessage);
		}
	}
	
	private void moveAccepted() {
		if (localPlayer instanceof ComputerPlayer)
		sendMessage(Protocol.CLIENT_CORE_DONE);
		
	}
	private void sendPlayers(String[] slicedMessage) {
		//TODO handig om te gebruiken?
		for(int i = 1; i < slicedMessage.length; i++) {
			ui.print(slicedMessage[i]);
		}
	}
	
	public void startClient() {
		while (currentQuestion != 3) {
			askQuestions();
		}
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			sendMessage("ex");
			sendMessage("join");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}
	
	private void askQuestions() {
		if (currentQuestion == 0) {
			if (question1()) {
				currentQuestion ++;
			}
			else {
				ui.print("Voer een geldige optie in (1 of 2)");
			}
		}
		if (currentQuestion == 1) {
			if (question2()) {
				currentQuestion ++;
			} else {
				ui.print("Voer een geldig ip adres in");
			}
		}
		if (currentQuestion == 2) {
			if (question3()) {
				currentQuestion ++;
			} else {
				ui.print("Voer een geldig poortnummer in.");
			}
		}
		
	}
	
	private boolean question1() {
		ui.renderMenu(PRE_MENU);
		int choice = ui.determineInt();
		if (choice == 1) {
			isHuman = true;
			return true;
		} else if (choice == 2) {
			isHuman = false;
			return true;
		}
		else {
			return false;
		}
	}
	private boolean question2() {
		ui.renderMenu(IP_MENU);
		String ip = "";
		ip = ui.determineString();
		if (isValidIP(ip)) {
		this.ip = ip;
		return true;
}
		
		return false;
	}
	private boolean question3() {
		ui.renderMenu(PORT_MENU);
		int input = ui.determineInt();
		if (input > 0) {
			port = input;
			return true;
		} else if (input == 0) {
			return true;
		}
		return false;
	}
	
	private boolean question4() {
		ui.renderMenu(END_MENU);
		int input = ui.determineInt();
		if (input == 1) {
			return true;
		} else {
			terminated = true;
			return false;
		}
	}
	
	public void run() {
		String text = "";
		try {
			while (terminated == false) {
				text = in.readLine();
				if (text != null && !text.equals("\n")) {
					getServerMessage(text);
				}
				else {
					ui.print("Foute input, probeer opnieuw.");
				}
			}
		} catch (IOException e) {
			//TODO implement
		}
		
	}
	
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			
		}
	}	

	private void gameEnded(String[] nameScore) {
		for (int i = 1; i < nameScore.length; i += 2) {
			ui.print(nameScore[i]+ " heeft een eindscore van " + nameScore[i+1]) ;
		}
		ui.print("De winnaar is: " + game.winner().getName());
		boolean bl =question4();
		if (bl == true)
			sendMessage(Protocol.CLIENT_CORE_JOIN);		
	}

	private void score(String[] nameScore) {
		for (int i = 1; i < nameScore.length; i += 2) {
			Player player = game.getPlayerByClient(nameScore[i]);
			player.setScore(Integer.parseInt(nameScore[i+1]));
		}
		bui.update();
	}

	private void moveMade(String x, String y, String shape, String color) {
		MoveUtils.setInitialMove(false);
		int xInt = Integer.parseInt(x)+90;
		int yInt = Integer.parseInt(y)+90;
		int shapeInt = Integer.parseInt(shape);
		int colorInt = Integer.parseInt(color);
		Tile tile = new Tile(TileUtils.intToColor(colorInt), TileUtils.intToSymbol(shapeInt));
		game.getBoard().setTile(xInt, yInt, tile);
		ui.print("Er is een nieuwe tegel gelegd op x = " + x + " y = " + y);
		for (Tile tileInHand : game.getCurrentPlayer().getHand()) {
			if (TileUtils.compareColor(tile, tileInHand) == true && TileUtils.compareSymbol(tile, tileInHand) == true){
				game.getCurrentPlayer().getHand().remove(tileInHand);
				break;
			}
		}
		game.getBoard().boardSize();
		bui.update();
		if (game.getCurrentPlayer() instanceof HumanPlayer) {
			turn(game.getCurrentPlayer().getName());
		}
		
	}

	private void moveDenied() {
		ui.print("Dit mag niet..."); 
		if (localPlayer instanceof ComputerPlayer) {
			sendMessage(Protocol.CLIENT_CORE_DONE);
		} else {
			turn(game.getCurrentPlayer().getName());
		}
		bui.update();
	}

	private void exception(String name) {
	}

	private void swapDenied() {
		ui.print("Dit mag niet...");
		turn(game.getCurrentPlayer().getName());
	}

	private void swapAccepted() {
		ui.print("Uw tegel staat klaar om geruild te worden.");
		for (Tile tileInHand : game.getCurrentPlayer().getHand()) {
			if (TileUtils.compareColor(tileToBeSwapped, tileInHand) == true && TileUtils.compareSymbol(tileToBeSwapped, tileInHand) == true){
				game.getCurrentPlayer().getHand().remove(tileInHand);
				break;
			}
		}
		bui.update();
		turn(game.getCurrentPlayer().getName());
	}

	private void done() {
		bui.update();
	}

	private void turn(String name) {
		game.setCurrentPlayer(game.getPlayerByClient(name));
		if (name.equals(localPlayer.getName())) {
			String[] move = null;
			if (localPlayer instanceof HumanPlayer) {
				move = localPlayer.determineMove();
			} else {
				move = ((ComputerPlayer)localPlayer).determineMove(game);
			}
			if (move != null && !move[0].equals(Protocol.CLIENT_CORE_DONE)) {
				if(move.length == 4) {
					int x = Integer.parseInt(move[0]);
					int y = Integer.parseInt(move[1]);
					int shape = Integer.parseInt(move[2]);
					int color = Integer.parseInt(move[3]);
					sendMessage(Protocol.CLIENT_CORE_MOVE + Protocol.MESSAGESEPERATOR + x + Protocol.MESSAGESEPERATOR + y + Protocol.MESSAGESEPERATOR + shape + Protocol.MESSAGESEPERATOR + color);
				} else if (move.length == 2) {
					int shape = Integer.parseInt(move[0]);
					int color = Integer.parseInt(move[1]);
					tileToBeSwapped = new Tile(TileUtils.intToColor(color), TileUtils.intToSymbol(shape));
					sendMessage(Protocol.CLIENT_CORE_SWAP + Protocol.MESSAGESEPERATOR + shape + Protocol.MESSAGESEPERATOR + color);
				}
			} else {
				sendMessage(Protocol.CLIENT_CORE_DONE);
			}
		}
	}

	private void receiveTile(String shape, String color) {
		int symbol = Integer.parseInt(shape);
		int colorInt = Integer.parseInt(color);
		Tile tile = new Tile(TileUtils.intToColor(colorInt), TileUtils.intToSymbol(symbol));
		localPlayer.getHand().add(tile);
	}

	private void startDenied() {
		ui.print("U kan nu nog niet starten, probeer opnieuw als er genoeg spelers zijn.");
		joinAccepted(playerName);
	}

	private void joinAccepted(String name) {
		if (gameStarted == false) {
			ui.print("Uw naam is " + name + ". U zit nu in de wachtrij, druk op een willekeurige toets om te starten.");
			playerName = name;
			createLocalPlayer();
			String input = ui.determineString(); //TODO vraag of deze vroegtijdig geskipt kan worden
			if (input != null) {
				sendMessage(Protocol.CLIENT_CORE_START);
			} else {
				ui.print("Voer 1 in om te starten...");
			}
		}
	}


	private void starting(String[] players) {
		gameStarted  = true;
		ui.print("Spel wordt gestart met " + (players.length - 1) + " spelers.");
		String[] names = new String[players.length - 1];
		game = new Game(0);
		for (int i = 1; i < players.length; i++) {
			if (!players[i].equals(playerName)) {
				game.addPlayer(new ServerPlayer(players[i]));
			}
		}
		game.addPlayer(localPlayer);
		for (int i = 1; i < players.length; i++) {
			names[i - 1] = players[i];
		}
		game.start();
		bui = new BoardTUI(game, this);
	}

	/**
	 * Controlleerd of een gegeven IP adres wel legitiem is.
	 * @param ip
	 * @return isValidInt == true && ints.length == 4
	 */
	private static boolean isValidIP(String ip){
		String[] ints = ip.split("\\.");
		boolean isValidInt = true;
		for(String integer : ints){
			int i = Integer.parseInt(integer);
				if(i > 255){
					isValidInt = false;
				}
		}
		return (isValidInt == true && ints.length == 4);
	}
	
	public void createLocalPlayer() {
		if (isHuman == true) {
			localPlayer = new HumanPlayer(playerName);
		} else {
			localPlayer = new ComputerPlayer(playerName);
		}
	}
	
	public Player getLocalPlayer() {
		return localPlayer;
	}
	
}