package models;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import exceptions.InvalidMoveException;
import exceptions.PlayerNotFoundException;
import protocol.Protocol;
import view.StartTUI;
import view.TUI;

public class Server2 {
	private int port = 1337;
	private ServerSocket server;
	private ArrayList<ClientHandler2> clients = new ArrayList<ClientHandler2>();
	private ArrayList<Game2> games = new ArrayList<Game2>();
	private ArrayList<ClientHandler2> joint = new ArrayList<ClientHandler2>();
	private ArrayList<Tile> tilesToSwap = new ArrayList<>();
	TUI ui = new StartTUI();
	

	public void getClientMessage(String msg, ClientHandler2 client) {
		String[] slicedMessage = msg.split(Protocol.MESSAGESEPERATOR);
		ui.print(msg + " - " + client.getPlayerName());
		switch(slicedMessage[0]) {
			case Protocol.CLIENT_CORE_JOIN: join(client);
			break;
			case Protocol.CLIENT_CORE_START: start();
			break;
			case Protocol.CLIENT_CORE_EXTENSION: ex(slicedMessage);
			break;
			case Protocol.CLIENT_CORE_LOGIN: login(slicedMessage[1]);
			break;
			case Protocol.CLIENT_CORE_SWAP: swap(slicedMessage[1], slicedMessage[2], client);
			break;
			case Protocol.CLIENT_CORE_DONE: done(client);
			break;
			case Protocol.CLIENT_CORE_MOVE: move(slicedMessage[1], slicedMessage[2], slicedMessage[3], slicedMessage[4], client);
			break;
		}
	}
	
	public void startServer() {
		ui.print("Toets een gewenst portnummer in, typ 0 voor de standaard poort: ");
		boolean portHasBeenSet = false;
		while (!portHasBeenSet) {
			try {
				int input = readInt();
				if (input > 0) {
					port = input;
				}
				server = new ServerSocket(port);
				portHasBeenSet = true;
			} catch (IOException e) {
				ui.print("Het ingevoerde nummer is ongeldig, probeer opnieuw of typ 0 voor de standaard poort: ");
				port = 1337;
			}
		}
	}
	
	public void run() {
		startServer();
		while (true) {
			try {
				ClientHandler2 clientHandler = new ClientHandler2(this, server.accept());
				ui.print("Er is iemand geconnect");
				clients.add(clientHandler);
				int name = new Random().nextInt(999);
				while (!checkIfNameExists("" + name)) {
					name = new Random().nextInt(999);
				}								
				clientHandler.setPlayerName("" + name);
				clientHandler.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private boolean checkIfNameExists(String name) {
		for (ClientHandler2 ch : clients) {
			if (ch.getPlayerName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Server2 server = new Server2();
		server.run();
	}
	
	private int readInt() {
		int input = 0;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			try {
				if (scanner.hasNextLine()) {
					input = scanner.nextInt();
				}
				break;
			} catch (InputMismatchException e) {
				ui.print("Voer een nummer in");
			}
		}
		scanner.close();
		return input;
	}
	
	private void move(String x, String y, String shape, String color, ClientHandler2 client){
		Tile tile = new Tile(color, shape);
		Game2 game;
		try {
			game = getGameByPlayer(client.getPlayerName());
			if (game.getPile().getTiles().contains(tile)) {
				try {
					game.makeMove(x, y, tile);
					client.sendMessage(Protocol.SERVER_CORE_MOVE_ACCEPTED);
					broadcast(Protocol.SERVER_CORE_MOVE_MADE + Protocol.MESSAGESEPERATOR + x + Protocol.MESSAGESEPERATOR + y + Protocol.MESSAGESEPERATOR + shape + Protocol.MESSAGESEPERATOR + color);
				} catch (InvalidMoveException e) {
					client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
					ui.print("Bij spel " + game.getId() + " is een move geweigerd van speler " + client.getPlayerName() + " omdat de zet niet geldig is.");
				}
			} else {
				client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
				ui.print("Bij spel " + game.getId() + " is een move geweigerd van speler " + client.getPlayerName() + " omdat de tegel niet in de zak is.");
			}
		} catch (PlayerNotFoundException e1) {
			client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
			ui.print("Whoopi goldberg");
		}		
	}
	
	private Game2 getGameByPlayer(String name) throws PlayerNotFoundException {
		for (Game2 game : games) {
			for (ClientHandler2 ch : game.getPlayers()) {
				if (ch.getPlayerName().equals(name)) {
					return game;
				}				
			}
		}
		throw new PlayerNotFoundException();
	}

	private void done(ClientHandler2 client) {
		try {
			Game2 game = getGameByPlayer(client.getPlayerName());
			game.finishMove(client.getPlayerName());
		} catch (PlayerNotFoundException e) {
			ui.print("Whoopi goldberg");
		}
	}

	private void swap(String shape, String color, ClientHandler2 client) {
		Tile tile = new Tile(color, shape);
		tilesToSwap.add(tile);
		Game2 game;
		try {
			game = getGameByPlayer(client.getPlayerName());
			ArrayList<Tile> hand = game.getHandByPlayerName(client.getPlayerName());
			if (hand.contains(tile)) {
				game.swapTile(tile, client.getPlayerName());
			}
		} catch (PlayerNotFoundException e) {
			ui.print("Whoopi goldberg");
		}
	}

	private void login(String name) {
		ui.print("Deze server ondersteund geen login.");
	}

	private void join(ClientHandler2 client) {
		joint.add(client);
		ui.print(client.getPlayerName() + " is gejoined");
		client.sendMessage(Protocol.SERVER_CORE_JOIN_ACCEPTED);
	}

	private void ex(String[] extensions) {
		ui.print("Deze server ondersteund geen extenties.");
	}

	public void start() {
		if (clients.size() >= 2) {
			int size = 0;
			Game2 game = new Game2(games.size());
			if (clients.size() > 4) {
				size = 4;
			}
			else {
				size = clients.size();
			}
			for (int i = 0; i < size; i ++) {
				ui.print("" + size);
				game.addPlayer(clients.get(0).getPlayerName());
				clients.remove(0);
				games.add(game);
			}
		}
		else {
			ui.print("Niet genoeg spelers in de wachtrij...");
		}
	}
	
	public void broadcast(String msg) {
		for (ClientHandler2 ch : clients) {
			ch.sendMessage(msg);
		}
	}
}
