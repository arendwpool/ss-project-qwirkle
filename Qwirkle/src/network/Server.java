package network;
import java.awt.List;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import server.Protocol;

public class Server {
	private static final int port = Integer.parseInt(Protocol.PORT);
	private ArrayList<ClientHandler> threads;
	private ServerSocket serverSocket;
	
	//TODO weghalen: voor test
	private String IP = "127.0.0.1";
	
	public static void main(String[] args) {
		Server server = new Server(port);
		server.run();
	}
	
	public Server(int port) {
        threads = new ArrayList<ClientHandler>();
        try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			print("Could not create server socket on port " + port);
		}
    }
	
    public void run() {
    	while (true) {
    		try {
            	ClientHandler ch = new ClientHandler(this, serverSocket.accept());
            	addHandler(ch);
            	ch.start();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    
    public void print(String message){
        System.out.println(message);
    }
    
  
    public void broadcast(String msg) {
        for (ClientHandler ch : threads) {
        	ch.sendMessage(msg);
        }
    }
    
    
    public void addHandler(ClientHandler handler) {
        threads.add(handler);
    }
    
    public void removeHandler(ClientHandler handler) {
        threads.remove(handler);
    }
}
