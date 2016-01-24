package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controllers.GameController;
import models.ComputerPlayer;
import models.HumanPlayer;
import models.Player;
import view.BoardTUI;
import view.StartTUI;
import view.TUI;

public class Client extends Thread{

	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	private TUI ui;
	private TUI bui;
	private GameController gc;
	private String ip;
	private InetAddress host;
	private boolean isHuman;
	private boolean quit;
	private Player player;
	private String playerName;	
	
	public Client() throws IOException {
		ui = new StartTUI(this);
		gc = new GameController();
	}
	
	public void run() {
		ui.start();
		if (quit == false) {
			try {
				host = InetAddress.getByName(ip);
				sock = new Socket(host, 1337);
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (isHuman == true) {
				try {
					out.write("humanplayer");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					out.write("computerplayer");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bui.start();
		}
	}
	
	public void setIP(String ip) {
		this.ip = ip;
	}
	
	public void setQuit(boolean bl){
		quit = bl;
	}
	
	public void isHuman(boolean bl) {
		isHuman = bl;
	}

	public void setPlayerName(String name){
		this.playerName = name;
	}

	public Player getLocalPlayer(){
		return player;
	}
	
	public boolean isValidIP(String ip){
		ip = ip.replace(".", " ");
		String[] ints = ip.split(" ");
		boolean isValidInt = true;
		for(String integer : ints){
			int i = Integer.parseInt(integer);
				if(i > 255){
					isValidInt = false;
				}
		}
		return (isValidInt == true && ints.length == 4);
	}
	
	public static void main(String[] args) {
		InetAddress addres;
		Client client = null;
		try {
			addres = InetAddress.getByName("127.0.0.1");
			client = new Client(); //TODO weghalen, voor test
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.run();
		
		
	}
}
