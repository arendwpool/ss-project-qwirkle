package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import models.Board;
import models.Game;
import models.HumanPlayer;
import models.Pile;
import models.Player;

public class ClientHandler extends Thread{
	private Server server;
    private BufferedReader in;
    private BufferedWriter out;
    private String clientName;
    private Player player = null;
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
    			if (player != null) {
    				server.broadcast(player.getName());
    			}
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
			} else if (msg.contains("name")){
				msg.replaceAll("name ", "");
				if (isHuman = true) {
					player = new HumanPlayer(msg, new Game(new Board(), new Pile()));
				}
			} else if (msg.equals("start")) {
				sendMessage("start");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    }
    
    public void sendMessage(String msg){
    	server.determineCommand(msg);
    }
    public Player getPlayer(){
    	return player;
    }
    
}
