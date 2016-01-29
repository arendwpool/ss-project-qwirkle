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
import util.TileUtils;
import models.ComputerPlayer;
import models.Game;
import models.HumanPlayer;
import view.BoardTUI;
import view.StartTUI;
import view.TUI;

/**
 * De client van het spel. Dit geeft de commando's van de server door aan de speler, en verwerkt
 * de commando's van de speler door deze naar de server te sturen.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public class Client extends Thread{	
	/**
	 * Het interface dat gebruikt wordt om menu's te laten zien aan de spelers, en om de input 
	 * van de spelers te vertalen.
	 */
	private TUI ui = new StartTUI();
	
	/**
	 * Als de client wordt gestart worden een aantal vragen gesteld. Deze integer geeft aan bij
	 * welk vraagnummer het programma is.
	 */
	private int currentQuestion = 0;
	
	/**
	 * De menu's die in dit programma weergegeven worden. De zin op index 0 worden als beschrijving
	 * weergegeven.
	 */
	private static final String[] PRE_MENU = {null, "Ik ben een Menselijke speler", "Ik ben een Computerspeler"};
	private static final String[] IP_MENU = {null, "Voer het gewenste ip adres in:"};
	private static final String[] THINKING_TIME_MENU = {null, "Voer de denktijd van de computer in (in tienden van seconden):"};
	private static final String[] PORT_MENU = {"Toets een gewenst portnummer in", "typ 0 voor de standaard poort: "};
	private static final String[] END_MENU = {"Het spel is afgelopen, toets 1 om nog een keer, elke andere toets om te stoppen", "Ik wil weer in de wachtrij"};
	
	/**
	 * Geeft aan of het programma getermineerd moet worden of niet.
	 */
	private boolean terminated = false;
	
	/**
	 * Wordt ingesteld na de vraag aan de speler of deze een menselijke speler wil zijn, 
	 * of een computerspeler.
	 */
	private boolean isHuman = false;
	
	/**
	 * Wordt ingesteld na de vraag aan de speler met welk ip adres hij wil verbinden.
	 */
	private String ip;
	
	/**
	 * Wordt ingesteld als een speler verbindt met een server. De server geeft deze 
	 * naam via een commando door.
	 */
	private String playerName;
	
	/**
	 * Volgens het protocol is de standaard poort voor het verbinden met een server is 1337.
	 * Als de speler aangeeft een andere poort te willen gebruiken wordt dit nummer aangepast.
	 */
	private int port = 1337;
	
	/**
	 * Leest alle inkomende berichten van een inputstreem van een socket.
	 */
	private BufferedReader in;
	
	/**
	 * Stuurt gegeven berichten naar de outputstream van een socket.
	 */
	private BufferedWriter out;
	
	/**
	 * De socket waarmee de client verbonden is.
	 */
	private Socket socket;
	
	/**
	 * Dit is de speler die lokaal met de client interacteert.
	 */
	private Player localPlayer;
	
	/**
	 * Het spel dat gespeeld wordt door de speler die met de client interacteert.
	 */
	private Game game;
	
	/**
	 * De interface die alle in-game gerelateerde menu's weergeeft.
	 */
	private TUI bui;
	
	/**
	 * Geeft aan of een speler in een spel zit.
	 */
	private boolean gameStarted = false;
	
	/**
	 * Als een speler een tegel wil ruilen wordt deze hier opgeslagen om later
	 * terug naar te verwijzen. TODO waarnaar?
	 */
	private Tile tileToBeSwapped;
	
	/**
	 * Als de speler aangegeven heeft dat hij een computerspeler wil zijn krijgt
	 * hij de vraag wat de denktijd van de computer moet zijn. Dit getal wordt
	 * hier opgeslagen.
	 */
	private int thinkingTime;
	
	/**
	 * Wordt uitgevoerd als de client wordt opgestart. Maakt een nieuw client-
	 * instanite en geeft deze het startsein.
	 * @param arsg
	 */
	public static void main(String[] arsg) {
		Client client = new Client();
		client.startClient();
	}
	
	/**
	 * Deze methode vertaalt het commando dat binnenkomt via de inputstream van 
	 * de socket. D.m.v. een switch wordt de juiste methode aangeroepen bij het 
	 * bijpassende commando.
	 * @param msg
	 */
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
			break;
			case Protocol.SERVER_CORE_DISCONNECT: exit(slicedMessage[1]);
			break;
		}
	}
	
	/**
	 * Deze methode wordt aangeroepen als een speler in het spel heeft aangegeven het
	 * spel te verlaten. Deze zal uit de lijst van spelers in het spel worden verwijdert.
	 * Deze methode wordt alleen door de server aangesproken als er genoeg spelers in 
	 * het spel zijn om door te kunnen gaan met het spel.
	 * @param string
	 */
	private void exit(String string) {
		game.getPlayers().remove(game.getPlayerByClient(string));
	}
	
	/**
	 * Deze methode wordt aangesproken door de server als een gegeven move is gedaan.
	 * Deze methode doet niets als de lokale speler een mens is, omdat alle benodigde
	 * stappen worden uitgevoerd door moveMade().
	 */
	private void moveAccepted() {
		if (localPlayer instanceof ComputerPlayer)
		sendMessage(Protocol.CLIENT_CORE_DONE);
		
	}
	
	/**
	 * Wordt aangesproken als een server de spelers binnen een spel terugstuurt. 
	 * Echter wordt dit nooit aangevraagd door deze client, dus wordt deze methode
	 * nooit aangeroepen als de server niet uit zichzelf dit gegeven stuurt.
	 * @param slicedMessage
	 */
	private void sendPlayers(String[] slicedMessage) {
		for(int i = 1; i < slicedMessage.length; i++) {
			ui.print(slicedMessage[i]);
		}
	}
	
	/**
	 * Start deze client op. Hiervoor worden eerst de benodigde vragen gesteld en 
	 * verwerkt, daarna zal met deze gegevens een verbinding worden gemaakt met een
	 * socket. Nadat er een verbinding is gemaakt zullen de eerste initiele commando's
	 * worden gestuurd naar de server.
	 */
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
	
	/**
	 * Stelt de vragen vanaf het punt currentQuestion. Als er een foute input is
	 * zal de vragenlijst hervat worden vanaf het punt currentQuestion.
	 */
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
		if (currentQuestion == 3 && isHuman == false) {
			if (question4() == false) {
				ui.print("Voer een geldig nummer in");
			}
		}
		
	}
	
	/**
	 * Stelt de vraag of de speler een menselijke speler wil zijn, of een computer.
	 * Als de vraag op een correcte wijze is beantwoord zal er true worden 
	 * teruggegeven.
	 * @return true || false
	 */
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
	
	/**
	 * Stelt de vraag op welk ip adres de speler wil gaan spelen.
	 * Als de vraag op een correcte wijze is beantwoord zal er true worden 
	 * teruggegeven.
	 * @return true || false
	 */
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
	
	/**
	 * Vraagt naar het poortnummer waarmee de speler wil verbinden. Als 0 wordt
	 * beantwoord zal de standaard poort worden gebruikt.
	 * Als de vraag op een correcte wijze is beantwoord zal er true worden 
	 * teruggegeven.
	 * @return
	 */
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
	
	/**
	 * Deze vraag wordt gesteld als de speler aangegeven heeft een computerspeler 
	 * te willen zijn.
	 * Als de vraag op een correcte wijze is beantwoord zal er true worden 
	 * teruggegeven.
	 * @return true || false
	 */
	private boolean question4() {
		ui.renderMenu(THINKING_TIME_MENU);
		int input = ui.determineInt();
		if (input >= 0) {
			thinkingTime = input*100;
			return true;
		} else if (input < 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Deze vraag wordt pas gesteld als het spel af is. De speler krijgt dan de optie
	 * om een nieuw spel te starten, of om het programma af te sluiten.
	 * Als de vraag op een correcte wijze is beantwoord zal er true worden 
	 * teruggegeven.
	 * @return true || false
	 */
	private boolean question5() {
		ui.renderMenu(END_MENU);
		int input = ui.determineInt();
		if (input == 1) {
			return true;
		} else {
			terminated = true;
			return false;
		}
	}
	
	/**
	 * De client bereidt de klasse Thread uit. Als deze client wordt aangeroepen met de
	 * methode start() zal de client als een thread zijn werk doen. Deze methode leest alle
	 * inkomende berichten en stuurt deze door naar getServerMessage() die dan de juiste
	 * methode aanroept.
	 */
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
	
	/**
	 * Stuurt een gegeven bericht naar de outputstream van de socket. Deze zal dan
	 * weer doorgestuurt worden naar de server.
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			
		}
	}	

	/**
	 * Als de server aangegeven heeft dat het spel afgelopen is, wordt deze methode aangeroepen.
	 * De boolean gameStarted wordt weer op false gezet, en het lokale spel wordt weer leeg
	 * gemaakt. Vervolgens worden de scores weergegeven, en wordt aangeven wie de winnaar is. 
	 * Tot slot krijgt de speler de optie om een nieuw spel te starten.
	 * @param nameScore
	 */
	private void gameEnded(String[] nameScore) {
		gameStarted = false;
		game = null;
		for (int i = 1; i < nameScore.length; i += 2) {
			ui.print(nameScore[i]+ " heeft een eindscore van " + nameScore[i+1]) ;
		}
		ui.print("De winnaar is: " + game.winner().getName());
		boolean bl = question5();
		if (bl == true)
			sendMessage(Protocol.CLIENT_CORE_JOIN);		
	}

	/**
	 * Na elke beurt stuurt de server de scores op. Deze worden vervolgens opgeslagen in de
	 * spelerobjecten van het lokale spel.
	 * @param nameScore
	 */
	private void score(String[] nameScore) {
		for (int i = 1; i < nameScore.length; i += 2) {
			Player player = game.getPlayerByClient(nameScore[i]);
			player.setScore(Integer.parseInt(nameScore[i+1]));
		}
		bui.update();
	}

	/**
	 * Deze methode wordt aangeroepen als de server een zet goedgekeurd heeft, en de zet
	 * heeft verwerkt. De zet wordt vervolgens in de client verwerkt in het lokale spel.
	 * Als de speler een menselijke speler is word de methode turn weer aangeroepen zodat
	 * de speler een nieuwe keus kan maken.
	 * @param x
	 * @param y
	 * @param shape
	 * @param color
	 */
	private void moveMade(String x, String y, String shape, String color) {
		game.getMoves().setInitialMove(false);
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

	/**
	 * Deze methode weergeeft een string die aan de speler doorgeeft dat de zet
	 * door de server foutgekeurd is. Als de speler een computer is wordt het "done"
	 * commando verstuurd. In principe krijgt de computer nooit een moveDenied commando.
	 */
	private void moveDenied() {
		ui.print("Dit mag niet..."); 
		if (localPlayer instanceof ComputerPlayer) {
			sendMessage(Protocol.CLIENT_CORE_DONE);
		} else {
			turn(game.getCurrentPlayer().getName());
		}
		bui.update();
	}

	/**
	 * TODO implement de exceptie
	 * @param name
	 */
	private void exception(String name) {
	}

	/**
	 * Als een speler een tegel wil ruilen, en de server keurt dit niet goed, wordt dit
	 * met een string doorgegeven aan de speler.
	 */
	private void swapDenied() {
		ui.print("Dit mag niet...");
		turn(game.getCurrentPlayer().getName());
	}

	/**
	 * Als de server een ruil wel goedkeurt wordt dit aangegeven aan de speler. Vervolgens
	 * wordt de tegel uit de hand van de speler verwijderd en wordt turn() weer aangeroepen,
	 * zodat de speler nog een tegel kan ruilen.
	 */
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

	/**
	 * Als de server doorgegeven heeft klaar te zijn met het uitdelen van de tegels stuurt
	 * deze het "done" commando. Deze methode update dan BoardTUI zodat de speler zijn
	 * tegels te zien krijgt.
	 */
	private void done() {
		bui.update();
	}

	/**
	 * Vraagt om de input van de speler. Deze input wordt hier correct afgehandeld door
	 * gepaste commando's te sturen aan de server. Dit kan zijn:  "move", "swap", "done"
	 * of "quit". Ook kan de speler een hint aanvragen. D.m.v. een computerspeler 
	 * krijgt de speler een string te zien met een mogelijke zet. 
	 * @param name
	 */
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
				} else if (move[0].equals("hint")) {
					ComputerPlayer playerForHint = new ComputerPlayer("forHint", 1);
					for (Tile tile : localPlayer.getHand()) {
						playerForHint.getHand().add(tile);
					}
					try {
						String[] hint = playerForHint.determineMove(game);
						String color = TileUtils.intToColor(Integer.parseInt(hint[3]));
						String shape = TileUtils.intToSymbol(Integer.parseInt(hint[2]));
						ui.print("U kan " + color+shape + " neerleggen op x = " + hint[0] + " en y = " + hint[1]);
					} catch (NullPointerException e) {
						ui.print("Er is nu geen geldige move, ruil ��n of meer tegels");
					}
					turn(game.getCurrentPlayer().getName());
				} else if (move[0].equals("quit")) {
					sendMessage(Protocol.SERVER_CORE_EXIT);
					try {
						out.close();
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					terminated = true;
				}
			} else {
				sendMessage(Protocol.CLIENT_CORE_DONE);
			}
		}
	}

	/**
	 * De server stuurt dit commando als deze een tegel aan een client geeft.  De client
	 * vertaalt dit commando naar een tegel, die dan in de hand van de speler wordt 
	 * gestopt.
	 * @param shape
	 * @param color
	 */
	private void receiveTile(String shape, String color) {
		int symbol = Integer.parseInt(shape);
		int colorInt = Integer.parseInt(color);
		Tile tile = new Tile(TileUtils.intToColor(colorInt), TileUtils.intToSymbol(symbol));
		localPlayer.getHand().add(tile);
	}

	/**
	 * Als er te weinig spelers zijn wordt een start afgewezen. De client weergeeft
	 * dan een string met een melding aan de speler.
	 */
	private void startDenied() {
		ui.print("U kan nu nog niet starten, probeer opnieuw als er genoeg spelers zijn.");
		joinAccepted(playerName);
	}

	/**
	 * Als een speler succesvol met de server is verbonden krijgt de client dit bericht.
	 * Er wordt een willekeurig gekozen id meegegeven door de server die dan toegewezen
	 * wordt aan playerName. Met deze naam wordt een lokale speler aangemaakt.
	 * @param name
	 */
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

	/**
	 * Als een spel gestart wordt stuurt de server de spelers mee. Deze spelers worden
	 * in het lokale spel gestopt. vervolgens wordt het spel gestart, en wordt een 
	 * BoardTUI ge�nstantieerd.
	 * @param players
	 */
	private void starting(String[] players) {
		game = new Game(0);
		gameStarted  = true;
		ui.print("Spel wordt gestart met " + (players.length - 1) + " spelers.");
		String[] names = new String[players.length - 1];
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
	
	/**
	 * Maakt een lokale speler met de gegevens: playerName en isHuman.
	 */
	public void createLocalPlayer() {
		if (isHuman == true) {
			localPlayer = new HumanPlayer(playerName);
		} else {
			localPlayer = new ComputerPlayer(playerName, thinkingTime);
		}
	}
	
	/**
	 * Geeft het spelerobject localPlayer terug.
	 */
	public Player getLocalPlayer() {
		return localPlayer;
	}
	
}