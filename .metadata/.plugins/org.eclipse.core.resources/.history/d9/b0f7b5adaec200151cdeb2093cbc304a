package network;
import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import protocol.Protocol;

public class Servershit {
	private ArrayList<ClientHandlershit> players;
	private ServerSocket serverSocket;
	
	//TODO weghalen: voor test
	private String IP = "127.0.0.1";
	
	public static void main(String[] args) {
		Servershit server = new Servershit(1234);
		server.run();
	}
	
	//TODO parameter weghalen?
	public Servershit(int port) {
        players = new ArrayList<ClientHandlershit>();
        try {
			serverSocket = new ServerSocket(Integer.parseInt(Protocol.PORT));
		} catch (IOException e) {
			System.out.println("Could not create server socket on port " + Protocol.PORT);
		}
    }
	
	public void run() {
		while (true) {
			System.out.println("Wachten op niewe spelers");
			try {
				Socket sock = serverSocket.accept();
				ClientHandlershit clientHandler = new ClientHandlershit(this, sock);
				players.add(clientHandler);
				System.out.println("Er is een nieuwe speler online");
				clientHandler.start();
			} catch (IOException e) {
				System.out.println("Er kon geen nieuwe connectie worden gemaakt");
			}
		}
	}    
  
    public ArrayList<ClientHandlershit> getHandlers() {
    	return players;
    }
    
    public void showActivity(String msg) {
        for (ClientHandlershit ch : players) {
        	ch.sendMessage(msg);
        }
    }
    
    public void determineCommand(String command) {
    	String[] words = command.split(Protocol.MESSAGESEPERATOR);
    	
    }
}
