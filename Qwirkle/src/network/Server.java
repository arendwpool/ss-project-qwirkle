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
import util.TileUtils;
import view.StartTUI;
import view.TUI;

/**
 * De server van het spel.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public class Server {
	/**
	 * Volgens het Protocol is dit de standaard poort. Deze wordt aangepast als
	 * een gebruiker dit aangeeft.
	 */
	private int port = 1337;
	
	/**
	 * De serversocket van deze server.
	 */
	private ServerSocket server;
	
	/**
	 * Een lijst met alle ClientHandlers die een verbinding hebben met een bepaalde
	 * client. 
	 */
	private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
	
	/**
	 * Een lijst met alle spellen die op de server gespeelt worden.
	 */
	private ArrayList<Game> games = new ArrayList<Game>();
	
	/**
	 * Een lijst met alle clients die aangegeven hebben te willen joinen. Dit wordt
	 * gebruikt als wachtrij voor een game.
	 */
	private ArrayList<ClientHandler> joint = new ArrayList<ClientHandler>();
	
	/**
	 * Instantieerd een StartTUI klasse.
	 */
	private TUI ui = new StartTUI();
	
	/**
	 * Geeft aan of de server afgesloten moet worden TODO werkt niet.
	 */
	private boolean terminated = false;
	
	/**
	 * Vertaalt aal inkomende berichten van clients, en roept de bijpassende 
	 * methode aan.
	 * @param msg
	 * @param client
	 */
	public synchronized void getClientMessage(String msg, ClientHandler client) {
		String[] slicedMessage = msg.split(Protocol.MESSAGESEPERATOR);
		try {
			ui.print(msg + " - " + client.getPlayerName() + " in game " + 
							getGameByPlayer(client.getPlayerName()).getId());
		} catch (PlayerNotFoundException e) {
			ui.print(msg + " - " + client.getPlayerName());
		}
		switch (slicedMessage[0]) {
			case Protocol.CLIENT_CORE_JOIN: join(client);
			break;
			case Protocol.CLIENT_CORE_START: start(client);
			break;
			case Protocol.CLIENT_CORE_EXTENSION: ex(slicedMessage);
			break;
			case Protocol.CLIENT_CORE_LOGIN: login(slicedMessage[1]);
			break;
			case Protocol.CLIENT_CORE_SWAP: swap(slicedMessage[1], slicedMessage[2], client);
			break;
			case Protocol.CLIENT_CORE_DONE: done(client);
			break;
			case Protocol.CLIENT_CORE_MOVE: 
				move(slicedMessage[1], slicedMessage[2], slicedMessage[3], slicedMessage[4], 
								client);
				break;
			case Protocol.CLIENT_CORE_PLAYERS: getPlayers(client);
			break;
			case Protocol.SERVER_CORE_EXIT: exit(client);
		}
	}
	
	/**
	 * Deze methode wordt aangeroepen als een client heeft aangegeven het spel te
	 * willen verlaten. De methode kijkt of er genoeg spelers zijn om het spel gewoon
	 * te kunnen continueren. Als dit niet het geval is wordt er aan de overgebleven
	 * speler aangeven dat het spel afgelopen is. Als dit wel het geval is wordt de 
	 * speler uit de lijst van spelers in het spel verwijderd. Er wordt dan bepaalt
	 * welke speler de volgende zet mag doen, en dit wordt doorgegeven aan de client
	 * met het "turn" commando.
	 * @param client
	 */
	private void exit(ClientHandler client) {
		Game game = null;
		try {
			game = getGameByPlayer(client.getPlayerName());
		} catch (PlayerNotFoundException e) {
			ui.print("Het betreffende spel kon niet gevonden worden.");
		}		
		if (game.getPlayers().size() > 2) {
			Player player = game.getPlayerByClient(client.getPlayerName());
			game.getPile().getTiles().addAll(player.getHand());
			game.getPlayers().remove(game.getPlayerByClient(player.getName()));
			broadcastToPlayersInGame(Protocol.SERVER_CORE_DISCONNECT + 
								Protocol.MESSAGESEPERATOR + client.getPlayerName(), game);
			if (game.getCurrentPlayer().getName().equals(player.getName())) {
				game.nextPlayer();
				broadcastToPlayersInGame(Protocol.SERVER_CORE_TURN + Protocol.MESSAGESEPERATOR + 
								game.getCurrentPlayer().getName(), game);
			}
		} else {
			String msgScore = "";
			for (Player player : game.getPlayers()) {
				msgScore = msgScore + (Protocol.MESSAGESEPERATOR + player.getName() + 
								Protocol.MESSAGESEPERATOR + player.getScore());
			}
			broadcastToPlayersInGame(Protocol.SERVER_CORE_GAME_ENDED + msgScore, game);
			games.remove(game);
		}
	}

	/**
	 * Als een client de spelers aanvraagt door een commando, wordt dit door deze
	 * methode gedaan.
	 * @param client
	 */
	private synchronized void getPlayers(ClientHandler client) {
		try {
			Game game = getGameByPlayer(client.getPlayerName());
			String msg = Protocol.SERVER_CORE_PLAYERS;
			for (Player player : game.getPlayers()) {
				msg += Protocol.MESSAGESEPERATOR + player.getName();
			}
			client.sendMessage(msg);
		} catch (PlayerNotFoundException e) {
			ui.print("Het betreffende spel kon niet gevonden worden.");
		}
	}

	/**
	 * Dit zet de server op. Er wordt eerst gevraagt met welke poort de gebruriker
	 * wil verbinden. Vervolgens wordt met dit gegeven een serversocket geinstantieerd.
	 */
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
				ui.print("Het ingevoerde nummer is ongeldig," +
								" probeer opnieuw of typ 0 voor de standaard poort: ");
				port = 1337; //TODO de loop komt door weer de scanner fout
			}
		}
	}
	
	/**
	 * Dit start de server op. Er wordt elke keer gewacht tot een client verbinding maakt.
	 * Als een client is verbonden wordt een niet ClientHandler gemaakt en gestart.
	 */
	public void run() {
		startServer();
		while (terminated  == false) {
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
		shutDown();
	}
	
	/**
	 * Controlleert of er in de lijst met clients al een speler is met
	 * een gegeven naam.
	 * @param name
	 * @return
	 */
	private synchronized boolean checkIfNameExists(String name) {
		for (ClientHandler ch : clients) {
			if (ch.getPlayerName() != null && ch.getPlayerName().equals(name)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Wordt uitgevoerd als de server wordt opgestart. Maakt een nieuw server-
	 * instanite en geeft deze het startsein.
	 * @param arsg
	 */
	public synchronized static void main(String[] args) {
		Server server = new Server();
		server.run();
	}
	
	/**
	 * Leest een input van een gebruiker en maakt hier geeft een integer terug.
	 * @return
	 */
	private synchronized int readInt() {
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
	
	/**
	 * Deze methode krijgt een zet meegegeven van een client. Dit gegeven wordt doorgegeven
	 * aan het bijhorende spelobject. Omdat het midden van het bord op x = 90 en y = 90 staat,
	 * maar volgens de protocollen het middelpunt 0, 0 is wordt er 90 opgeteld bij het megegeven
	 * coordinaat. Het spelobject verwerkt deze move, en propageert een InvalidMoveException als
	 * een gegeven move niet geldig is. Deze methode vangt dit geval en stuurt een moveDenied
	 * commando terug naar de client. Als een move wel geldig is wordt dit ook aangegeven aan 
	 * de client.
	 * @param x
	 * @param y
	 * @param shape
	 * @param color
	 * @param client
	 */
	private synchronized void move(String x, String y, String shape,
						String color, ClientHandler client) {
		int xInt = Integer.parseInt(x) + 90;
		int yInt = Integer.parseInt(y) + 90;
		int shapeInt = Integer.parseInt(shape);
		int colorInt = Integer.parseInt(color);
		Tile tile = new Tile(TileUtils.intToColor(colorInt), TileUtils.intToSymbol(shapeInt));
		Game game =  null;
		try {
			game = getGameByPlayer(client.getPlayerName());
			game.makeMove(xInt, yInt, tile, client.getPlayerName());
			client.sendMessage(Protocol.SERVER_CORE_MOVE_ACCEPTED);
			broadcastToPlayersInGame(Protocol.SERVER_CORE_MOVE_MADE + Protocol.MESSAGESEPERATOR +
								x + Protocol.MESSAGESEPERATOR + y +
								Protocol.MESSAGESEPERATOR + shape + 
								Protocol.MESSAGESEPERATOR + color, game);
		} catch (PlayerNotFoundException e1) {
			client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
			ui.print("Whoopi goldberg");
		} catch (InvalidMoveException e) {
			client.sendMessage(Protocol.SERVER_CORE_MOVE_DENIED);
			ui.print("Bij spel " + game.getId() + " is een move geweigerd van speler " + 
							client.getPlayerName() + " omdat de zet niet geldig is.");
		}
	}
	
	/**
	 * Geeft een spelobject terug aan de hand van een gegeven spelernaam. 
	 * @param name
	 * @return
	 * @throws PlayerNotFoundException
	 */
	public synchronized Game getGameByPlayer(String name) throws PlayerNotFoundException {
		for (Game game : games) {
			for (Player player: game.getPlayers()) {				
				if (player.getName().equals(name)) {
					return game;
				}				
			}
		}
		throw new PlayerNotFoundException();
	}

	/**
	 * Als een client het "done" commando heeft verstuurd geeft deze methode de
	 * speler weer tegels, als dit nodig is. Hierna wordt de score berekend, en 
	 * worden alle scores van de spelers teruggegeven aan een client. Als het 
	 * spel over is wordt dit doorgegeven aan de client, anders word de volgende
	 * speler berekend, en doorgegeven aan alle clients binnen een spel. Als de 
	 * speler had aangegeven tegels te willen ruilen, worden deze tegels
	 * terug in de zak gedaan, en wordt de lijst tilesToSwap geleegd.
	 * @param client
	 */
	private synchronized void done(ClientHandler client) {
		try {
			Game game = getGameByPlayer(client.getPlayerName());
			if (game.getPile().getTiles().size() != 0) {
				TileUtils.setHand(game.getPlayerByClient(client.getPlayerName()), 
									game.getPile(), this, game);
			}
			game.getMoves().generateScore(game.getPlayerByClient(client.getPlayerName()), 
								game.getBoard());
			String msgScore = "";
			for (Player player : game.getPlayers()) {
				msgScore = msgScore + (Protocol.MESSAGESEPERATOR + player.getName() + 
									Protocol.MESSAGESEPERATOR + player.getScore());
			}
			if (game.gameOver() == true) {
				broadcastToPlayersInGame(Protocol.SERVER_CORE_GAME_ENDED + msgScore, game);
				games.remove(game);
			} else {
				broadcastToPlayersInGame(Protocol.SERVER_CORE_SCORE + msgScore, game);
				game.getPile().getTiles().addAll(game.getMoves().getTilesToSwap());
				game.getMoves().getTilesToSwap().clear();
				game.nextPlayer();
				broadcastToPlayersInGame(Protocol.SERVER_CORE_TURN + Protocol.MESSAGESEPERATOR + 
									game.getCurrentPlayer().getName(), game);
			}
		} catch (PlayerNotFoundException e) {
			ui.print("Whoopi goldberg");
		}
	}

	/**
	 * Deze methode wordt aangeroepen als een speler een tegel wil ruilen. 
	 * Er wordt eerst nagegaan of de speler wel de tegel in zijn hand heeft.
	 * Als dit niet zo is, of als er geen tegels meer in de zak zijn wordt
	 * een swapDenied commando verstuurd. Als de swap wel geldig is wordt
	 * de tegel verwijderd uit de hand van de speler, en wordt deze opgeslagen
	 * in de lijst tilesToSwap.
	 * @param shape
	 * @param color
	 * @param client
	 */
	private synchronized void swap(String shape, String color, ClientHandler client) {
		Game game;
		int shapeInt = Integer.parseInt(shape);
		int colorInt = Integer.parseInt(color);
		try {
			game = getGameByPlayer(client.getPlayerName());
			if (game.tileInHand(TileUtils.intToSymbol(shapeInt), TileUtils.intToColor(colorInt), 
								client.getPlayerName()) == true) {
				Tile toSwap = new Tile(TileUtils.intToColor(Integer.parseInt(color)), 
									TileUtils.intToSymbol(Integer.parseInt(shape)));
				game.getPile().getTiles().add(toSwap);
				Player player = game.getPlayerByClient(client.getPlayerName());
				for (Tile tileInHand : player.getHand()) {
					if (TileUtils.compareColor(toSwap, tileInHand) == true && 
										TileUtils.compareSymbol(toSwap, tileInHand) == true) {
						player.getHand().remove(tileInHand);
						break;
					}
				}
				game.getMoves().getTilesToSwap().add(toSwap);
				broadcastToPlayer(Protocol.SERVER_CORE_SWAP_ACCEPTED, client.getPlayerName());
			} else {
				broadcastToPlayer(Protocol.SERVER_CORE_SWAP_DENIED, client.getPlayerName());
			}
		} catch (PlayerNotFoundException e) {
			ui.print("Whoopi goldberg");
		}
	}

	/**
	 * Wordt niet ondesteund door deze server.
	 * @param name
	 */
	private synchronized void login(String name) {
		ui.print("Deze server ondersteund geen login.");
	}

	/**
	 * Geeft de speler die wil joinen een willekeurige, unieke naam, en zet
	 * de speler in de wachtrij voor een spel. 
	 * @param client
	 */
	private synchronized void join(ClientHandler client) {
		joint.add(client);
		ui.print(client.getPlayerName() + " is gejoined, nu in de lobby: ");
		for (ClientHandler player : joint) {
			ui.print(player.getPlayerName());
		}
		client.sendMessage(Protocol.SERVER_CORE_JOIN_ACCEPTED + 
							Protocol.MESSAGESEPERATOR + client.getPlayerName());
	}

	/**
	 * Deze server ondersteunt geen extenties.
	 * @param extensions
	 */
	private synchronized void ex(String[] extensions) {
		ui.print("Deze server ondersteund geen extenties.");
	}

	/**
	 * Als een van de spelers in de wachtrij heeft aangegeven te willen
	 * starten wordt er in de wachtrij gekeken of er wel voldoende spelers
	 * zijn. Als dit niet zo is wordt het spel niet gestart, en wordt dit
	 * doorgegeven aan de client. Als dit wel zo is word het spel gestart
	 * en worden de spelers die in het spel zijn gezet uit de wachtrij gehaalt.
	 * De spelersnamen worden hierna doorgestuurt naar de client. Hierna worden 
	 * er tegels uitgedeelt aan de spelers, en wordt de speler die mag beginnen
	 * uitgekozen. Ook dit wordt aan de client doorgegeven met het "turn"commando.
	 * @param client
	 */
	public synchronized void start(ClientHandler client) {
		if (joint.size() >= 2) {
			int size = joint.size();
			Game game = new Game(games.size() + 1);
			if (joint.size() > 4) {
				size = 4;
			} else {
				size = joint.size();
			}
			for (int i = 0; i < size; i++) {
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
			for (Player player : game.getPlayers()) {
				TileUtils.setHand(player, game.getPile(), this, game);
			}
			game.start();
			game.determineInitialPlayer();
			broadcastToPlayersInGame(Protocol.SERVER_CORE_DONE, game);
			broadcastToPlayersInGame(Protocol.SERVER_CORE_TURN + Protocol.MESSAGESEPERATOR + 
								game.getCurrentPlayer().getName(), game);
		} else {
			ui.print("Niet genoeg spelers in de wachtrij...");
			client.sendMessage(Protocol.SERVER_CORE_START_DENIED);
		}
	}
	
	/**
	 * Stuurt een bepaald bericht naar alle verbonden clients.
	 * @param msg
	 */
	public synchronized void broadcastToAll(String msg) {
		for (ClientHandler ch : clients) {
			ch.sendMessage(msg);
		}
	}
	
	/**
	 * Stuurt een commando naar alle spelers in een spel, gegeven een spelersnaam.
	 * @param msg
	 * @param name
	 */
	public synchronized void broadcastToPlayersInGame(String msg, String name) {
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
	
	/**
	 * suurt een commando naar alle spelers in een spel, gegeven een spelobject.
	 * @param msg
	 * @param game
	 */
	public synchronized void broadcastToPlayersInGame(String msg, Game game) {
		for (Player player : game.getPlayers()) {
			getClientByPlayer(player.getName()).sendMessage(msg);
		}
	}
	
	/**
	 * Stuurt een commando naar een beplaade speler, gegeven een naam.
	 * @param msg
	 * @param name
	 */
	public synchronized void broadcastToPlayer(String msg, String name) {
		getClientByPlayer(name).sendMessage(msg);
	}
	
	/**
	 * Geedt de clienthandler terug, gegeven een naam.
	 * @param name
	 * @return
	 */
	public synchronized ClientHandler getClientByPlayer(String name) {
		for (ClientHandler ch : clients) {
			if (ch.getPlayerName().equals(name)) {
				return ch;
			}
		}
		return null;
	}

	/**
	 * Sluit de server af.
	 */
	public void shutDown() {
		for (ClientHandler ch : clients) {
			broadcastToPlayer(Protocol.SERVER_CORE_TIMEOUT_EXCEPTION + Protocol.MESSAGESEPERATOR + 
								ch.getPlayerName(), ch.getPlayerName());
		}
		terminated = true;
	}
	
	public void deleteClient(ClientHandler client) {
		clients.remove(client);
		joint.remove(client);
	}

	public ArrayList<Game> getGames() {
		return games;
	}
}