package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import exceptions.InvalidMoveException;
import exceptions.PlayerNotFoundException;
import models.Player;
import models.ServerPlayer;
import models.Game;
import models.Tile;
import view.StartTUI;
import view.TUI;

public class Server {
	private int port = 1337;
	private ServerSocket server;
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	private ArrayList<Game> games = new ArrayList<Game>();
	private ArrayList<ClientHandler> joint = new ArrayList<ClientHandler>();
	private ArrayList<Tile> tilesToSwap = new ArrayList<Tile>();
	TUI ui = new StartTUI();
	

	public void getClientMessage(String msg, ClientHandler client) {
		String[] slicedMessage = msg.split(Protocol.MESSAGESEPERATOR);
		try {
			ui.print(msg + " - " + client.getPlayerName() +" in game " + getGameByPlayer(client.getPlayerName()));
		} catch (PlayerNotFoundException e) {
			ui.print(msg + " - " + client.getPlayerName());
		}
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
				ClientHandler clientHandler = new ClientHandler(this, server.accept());
				ui.print("Er is iemand geconnect");
				clients.add(clientHandler);
				int name = new Random().nextInt(999);
				while (!checkIfNameExists("" + name)) {
					name = new Random().nextInt(999);
				}								
				clientHandler.setPlayerName("" + name);
				clientHandler.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private boolean checkIfNameExists(String name) {
		for (ClientHandler ch : clients) {
			if (ch.getPlayerName() != null && ch.getPlayerName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Server server = new Server();
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
	
	private void move(String x, String y, String shape, String color, ClientHandler client){
		Tile tile = new Tile(color, shape);
		Game game;
		try {
			game = getGameByPlayer(client.getPlayerName());
			try {
				game.makeMove(x, y, tile, client.getPlayerName());
				client.sendMessage(Protocol.SERVER_CORE_MOVE_ACCEPTED);
				broadcastToAll(Protocol.SERVER_CORE_MOVE_MADE + Protocol.MESSAGESEPERATOR + x + Protocol.MESSAGESEPERATOR + y + Protocol.MESSAGESEPERATOR + shape + Protocol.MESSAGESEPERATOR + color);
			} catch (InvalidMoveException e) {
				client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
				ui.print("Bij spel " + game.getId() + " is een move geweigerd van speler " + client.getPlayerName() + " omdat de zet niet geldig is.");
			}
		} catch (PlayerNotFoundException e1) {
			client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
			ui.print("Whoopi goldberg");
		}		
	}
	
	private Game getGameByPlayer(String name) throws PlayerNotFoundException {
		for (Game game : games) {
			for (Player player: game.getPlayers()) {				
				if (player.getName().equals(name)) {
					return game;
				}				
			}
		}
		throw new PlayerNotFoundException();
	}

	private void done(ClientHandler client) {
		try {
			Game game = getGameByPlayer(client.getPlayerName());
			game.finishMove(client.getPlayerName());
		} catch (PlayerNotFoundException | InvalidMoveException e) {
			ui.print("Whoopi goldberg");
		}
	}

	private void swap(String shape, String color, ClientHandler client) {
		Game game;
		try {
			game = getGameByPlayer(client.getPlayerName());
			if (game.tileInHand(shape, color, client.getPlayerName())) {
				game.swapTile(shape, color, client.getPlayerName());
			}
			else {
				broadcastToPlayer(Protocol.SERVER_CORE_SWAP_DENIED, client.getPlayerName());
			}
		} catch (PlayerNotFoundException e) {
			ui.print("Whoopi goldberg");
		} catch (InvalidMoveException e) {
			//TODO broadcats naar speler
		}
	}

	private void login(String name) {
		ui.print("Deze server ondersteund geen login.");
	}

	private void join(ClientHandler client) {
		joint.add(client);
		ui.print(client.getPlayerName() + " is gejoined, nu in de lobby: ");
		for (ClientHandler player : joint) {
			ui.print(player.getPlayerName());
		}
		client.sendMessage(Protocol.SERVER_CORE_JOIN_ACCEPTED + Protocol.MESSAGESEPERATOR + client.getPlayerName());
	}

	private void ex(String[] extensions) {
		ui.print("Deze server ondersteund geen extenties.");
	}

	public void start() {
		if (joint.size() >= 2) {
			int size = joint.size();
			Game game = new Game(games.size()+1);
			if (joint.size() > 4) {
				size = 4;
			} else {
				size = joint.size();
			}
			for (int i = 0; i < size; i ++) {
				game.addPlayer(new ServerPlayer(joint.get(0).getPlayerName()));
				joint.remove(0);
			}
			String players = "";
			for (Player player : game.getPlayers()) {
				players += Protocol.MESSAGESEPERATOR + player.getName();
			}
			games.add(game);
			String msg = Protocol.SERVER_CORE_START + players;
			broadcastToPlayersInGame(msg, game);
			game.start();
		}
		else {
			ui.print("Niet genoeg spelers in de wachtrij...");
		}
	}
	
	public void broadcastToAll(String msg) {
		for (ClientHandler ch : clients) {
			ch.sendMessage(msg);
		}
	}
	
	public void broadcastToPlayersInGame(String msg, String name) {
		Game game = null;
		try {
			game = getGameByPlayer(name);
		} catch (PlayerNotFoundException e) {
			e.printStackTrace();
		}
		for (Player player : game.getPlayers()) {
			getClientByPlayer(player.getName()).sendMessage(msg);
		}
	}
	
	public void broadcastToPlayersInGame(String msg, Game game) {
		for (Player player : game.getPlayers()) {
			getClientByPlayer(player.getName()).sendMessage(msg);
		}
	}
	
	public void broadcastToPlayer(String msg, String name) {
		getClientByPlayer(name).sendMessage(msg);
	}
	
	public ClientHandler getClientByPlayer(String name) {
		for (ClientHandler ch : clients) {
			if (ch.getPlayerName().equals(name)) {
				return ch;
			}
		}
		return null;
	}
}