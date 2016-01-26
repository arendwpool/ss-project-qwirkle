package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler2 extends Thread {
		
	private Server2 server;
    private BufferedReader in;
    private BufferedWriter out;
    private String playerName;
	
	public ClientHandler2(Server2 server, Socket client){
		this.server = server;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
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
	
	public void sendMessage(String msg) {
		try {
			out.write(msg);
			out.newLine();
			out.flush();
		} catch (IOException e) {
		}
	}
	
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	
	public String getPlayerName() {
		return this.playerName;
	}
}
