package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import models.Player;

public class ClientHandler extends Thread{
	private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private String clientName;
    private Player player;
    private boolean isHuman;
    
    public ClientHandler(Server server, Socket socket) throws IOException {
    	this.server = server;
    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
    
    public void setPlayer(Player player){
    	this.player = player;
    }
    
    public void run() {
    	while (true) {
    		translateMessage();
    		if (isHuman == true){
    			server.broadcast("true");
    		} else {
    			server.broadcast("false");
    		}
    	}
    }
    
    public void translateMessage() {
    	try {
			String msg = in.readLine();
			if (msg.equals("humanplayer")) {
				isHuman = true;
			} else if (msg.equals("compupterplayer")) {
				isHuman = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
}
