package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import models.Player;
import models.ServerPlayer;
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
	private static final String[] START_MENU = {null, "Start spel"};
	private boolean isHuman = false;
	private String ip;
	private String playerName;
	private int port = 1337;
	private BufferedReader in;
	private BufferedWriter out;
	private Socket socket;
	private Player localPlayer;
	private boolean gameStarted = false;
	private Game game;
	private TUI bui;
	
	public static void main(String[] arsg) {
		Client client = new Client();
		client.startClient();
	}
	public void getServerMessage(String msg) {
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
			case Protocol.SERVER_CORE_PLAYERS: sendPlayers();
		}
	}
	
	private void sendPlayers() {
		// TODO Auto-generated method stub
		
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
	
	public void run() {
		String text = "";
		try {
			while (text != null) {
				text = in.readLine();
				if (text != null && !text.equals("\n")) {
					getServerMessage(text);
				}
				else {
					ui.print("Foute input, probeer opnieuw.");
				}
			}
		} catch (IOException e) {
			//
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
		Map<String, Integer> score = new HashMap<String, Integer>();
		for (int i = 1; i < nameScore.length; i += 2) {
			score.put(nameScore[i], Integer.parseInt(nameScore[i + 1]));
			//toon scores -> eindmenu...
		}
	}

	private void score(String[] nameScore) {
		Map<String, Integer> score = new HashMap<String, Integer>();
		for (int i = 1; i < nameScore.length; i += 2) {
			score.put(nameScore[i], Integer.parseInt(nameScore[i + 1]));
			//toon scores
		}
	}

	private void moveMade(String x, String y, String shape, String color) {
	}

	private void moveDenied() {
	}

	private void moveAccepted() {
	}

	private void exception(String name) {
	}

	private void swapDenied() {
	}

	private void swapAccepted() {
	}

	private void done() {
	}

	private void turn(String name) {
		/*if (localPlayer.getName().equals(name)) {
			String option = localPlayer.determineMove();
			if (option ...) {
				sendMessage("move ....");
			}
		}*/
	}

	private void receiveTile(String shape, String color) {
	}

	private void startDenied() {
	}

	private void joinAccepted(String name) {
		ui.print("Uw naam is " + name + ". U zit nu in de wachtrij, selecteer start als er genoeg mensen zijn om mee te spelen: ");
		playerName = name;
		ui.renderMenu(START_MENU);
		while (gameStarted == false) {
			int input = ui.determineInt(); //TODO vraag of deze vroegtijdig geskipt kan worden
			try {
				getServerMessage(in.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (input == 1) {
				sendMessage(Protocol.CLIENT_CORE_START);
			} else {
				ui.print("Voer 1 in om te starten...");
			}
		}
			
	}

	private void starting(String[] players) {
		ui.print("Spel wordt gestart met " + (players.length - 1) + " spelers.");
		String[] names = new String[players.length - 1];
		game = new Game(0);
		for (String player : players) {
			if (!player.equals(playerName)) {
				game.addPlayer(new ServerPlayer(player));
			}
		}
		createLocalPlayer();
		game.addPlayer(localPlayer);
		gameStarted = true; //TODO is misschien niet meer nodig
		for (int i = 1; i < players.length; i++) {
			names[i - 1] = players[i];
		}
		game.start();
		bui = new BoardTUI(game);
		bui.start();
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
}