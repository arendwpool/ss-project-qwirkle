package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.FullGameException;
import models.Board;
import models.Game;
import models.Pile;
import view.StartTUI;

public class Server {
	private int port;
	private ArrayList<ClientHandler> clientHandlers;
	private StartTUI ui;
	private ServerSocket serverSocket;
	private int noOnlinePlayers;
	private int playerId = 0;
	
	public Server() {
		ui = new StartTUI(null);
		clientHandlers = new ArrayList< ClientHandler>();
		while (true) {
			Scanner in = new Scanner(System.in);
			System.out.println("Voer een poortnummer in: ");
			int port = ui.determineOption();
			if (port >= 0 && port <= 8000) {
				this.port = port;
				try {
					serverSocket = new ServerSocket(port);
					break;
				} catch (IOException e) {
					System.out.println("Er kon geen verbinding worden gmaakt, voer een ander poortnummer in.");
				}
			} else {
				System.out.println("Voer een geldig poortnummer in.");
			}
		}
	}
	
	public void run() {
		System.out.println("Wachten op niewe spelers");
		while (true) {
			try {
				Socket sock = serverSocket.accept();
				ClientHandler clientHandler = new ClientHandler(this, sock);
				clientHandlers.add(clientHandler);
				System.out.println("Er is een nieuwe speler online");
				noOnlinePlayers++;
				clientHandler.start();
			} catch (IOException e) {
				System.out.println("Er kon geen nieuwe connectie worden gemaakt");
			}
		}
	} 
	
	public void determineCommand(String command){
		
	}
	
	public void sendCommand(String msg) {
		
	}
	
	public void broadcast(String msg) {
		System.out.print(msg);
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.run();
	}
	
}