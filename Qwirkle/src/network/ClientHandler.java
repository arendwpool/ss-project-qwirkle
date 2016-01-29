package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
}