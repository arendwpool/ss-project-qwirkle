package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
    
	
	private static final String USAGE = "usage: " + Server.class.getName() + " <port>";
    private static final int port = 1337;
    private ServerSocket sockey;
    private List<ClientHandler> threads;
    
    public static void main(String[] args) {
    	Server server = new Server();
    }
    
    public Server() {
    	
    }
    
    public void run() {
    	
    }
    
    public void serverStart() {
    	
    }
    
    public void readInput() {
    	
    }
    
    public void determineCommandString() {
    	
    }
    
    public void addHandler() {
    	
    }
    
    public void removeHandler() {
    	
    }
    
    public void broadcast() {
    	
    }
    
    public void shutdown() {
    	
    }

}