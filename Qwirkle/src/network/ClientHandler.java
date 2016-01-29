package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import exceptions.PlayerNotFoundException;
import models.Game;
import models.Player;

/**
 * Deze klasse managet de verbinding tussen client en server.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public class ClientHandler extends Thread {
	/**
	 * De server die deze clienthandler instantieert.
	 */
	private Server server;
	
	/**
	 * De inputstream lezer van een socket waarmee verbinding is.
	 */
    private BufferedReader in;
    
    /**
     * De outputstream schrijver van een socket waarmee verbinding is.
     */
    private BufferedWriter out;
    
    /**
     * De naam van de speler van de client waarmee verbinding is.
     */
    private String playerName;
	
    /**
     * Maakt een nieuwe ClientHandler aan met een gegeven server en socket.
     * @param server
     * @param client
     */
	public ClientHandler(Server server, Socket client){
		this.server = server;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			System.out.println("Er kon geen verbinding worden gemaakt.");
		} 	
	}
	
	/**
	 * Deze klasse bereidt de klasse Thread uit. Deze methode wordt aangeroepen 
	 * als de methode start() wordt aangeroepen. De methode leest alle inkomende berichten
	 * van een socket en stuurt deze door naar de server.
	 */
	public void run() {
    	String text = "";
		try {
			while (text != null) {
				text = in.readLine();
				if (!(text == null) && !text.equals("\n")) {
					server.getClientMessage(text, this);
				}
			}
		} catch (IOException e) {
			System.out.println("Er is iets fout gegaan bij het lezen van de commandos in de clienthandler.");
			server.deleteClient(this);
			Game game = null;
			try {
				game = server.getGameByPlayer(playerName);
			} catch (PlayerNotFoundException e1) {
				System.out.println("Het betreffende spel kan niet gevonden worden");
			}
			try {
				for (Player player : game.getPlayers()) {
					if (!player.getName().equals(playerName))
						server.broadcastToPlayer(Protocol.SERVER_CORE_GAME_ENDED, player.getName());
					System.out.println("spel asfas");
					server.getGames().remove(game);
				}
			}catch (NullPointerException e1) {
				//Doe niets, het spel is blijkbaar al gestopt, of is nooit gestart.
			}
		}
    }
	
	/**
	 * Verstuurt een gegeven bericht naar de outputstream van de socket.
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.out.println("Het versturen van het commando is fout gegaan.");
			server.deleteClient(this);
			Game game = null;
			try {
				game = server.getGameByPlayer(playerName);
			} catch (PlayerNotFoundException e1) {
				System.out.println("Het betreffende spel kan niet gevonden worden");
			}
			for (Player player : game.getPlayers()) {
				if (!player.getName().equals(playerName))
				server.broadcastToPlayer(Protocol.SERVER_CORE_GAME_ENDED, player.getName());
				System.out.println("spel asfas");
				server.getGames().remove(game);
			}
		}
	}
	
	/**
	 * Zet de naam van de speler van de client waarmee verbinding is.
	 * @param name
	 */
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	/**
	 * Geeft de naam van de speler van de client waarmee verbinding is terug.
	 * @return
	 */
	public String getPlayerName() {
		return this.playerName;
	}
	
	public void shutDown() {
		try {
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println("Clienthandler kon niet schoon worden afgesloten.");
		}
	}
}