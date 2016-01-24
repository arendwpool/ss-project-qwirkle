package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import protocol.Protocol;

public class Server extends Thread {
    
	
	private static final String USAGE = "usage: " + Server.class.getName() + " <port>";
    private int port = Integer.parseInt(Protocol.PORT);
    protected ServerSocket serverSocket;
    private boolean isTerminated = false;
    private List<ClientHandler> threads;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(0);
        }
        Server server = new Server(Integer.parseInt(args[0]));
        server.run();
        
    }
    
    public Server(int portArg) throws IOException {
        this.port = portArg;
        threads = new ArrayList<ClientHandler>();
        try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			print("Could not create server socket on port " + port);
		}
    }
    
    public void run() {
        // TODO insert body
    	while(isTerminated == false){
    		try{
    		ClientHandler clientHandler = new ClientHandler(this, serverSocket.accept());
    		addHandler(clientHandler);
    		clientHandler.announce();
    		clientHandler.start();
    		} catch(IOException e){
    			e.printStackTrace();
    		}
    	}
    }
    
    public void print(String message){
        System.out.println(message);
    }
    
    public void serverStart() {
    	
    }
    
    public void readInput() {
    	
    }
    
    public void determineCommandString() {
    	
    }
    
    /**
     * Add a ClientHandler to the collection of ClientHandlers.
     * @param handler ClientHandler that will be added
     */
    public void addHandler(ClientHandler handler) {
        threads.add(handler);
    }
    
    /**
     * Remove a ClientHandler from the collection of ClientHanlders. 
     * @param handler ClientHandler that will be removed
     */
    public void removeHandler(ClientHandler handler) {
    	if (threads.contains(handler)) {
            threads.remove(handler);
        }
    }
    
    /**
     * stuurt een bericht door gebruik te maken van de collectie van de verbonden Clienthandlers
     * naar alle verbonden Clients.
     * @param message 
     * @throws IOException 
     */
    public void broadcast(String message) throws IOException {
    	 for (ClientHandler handler : threads) {
         	handler.sendMessage("Server: " + message);
         }
    }
    
    public void shutdown() {
		System.out.println("Server shutting down.......");
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.exit(0);
		}
		System.exit(0);
	}

}