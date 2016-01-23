package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.lang.Thread;


public class Client extends Thread {
	
	private static final String USAGE
    = "usage: Qwirkle.server.Client <address> <port>";
	private String string = "";
	private int clientID;
	private String clientName;
	private String PlayerName;
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	Scanner uInput = new Scanner(System.in);
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(USAGE);
			System.exit(0);
		}
		
		InetAddress host=null;
		int port =0;

		try {
			host = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.err.println("ERROR: no valid hostname!");
			System.exit(0);
		}

		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("ERROR: no valid portnummer!");
			System.exit(0);
		}

		try {
			Client client = new Client(host, port);
			client.start();
			
			do{
				String input = readString("");
				client.sendCommand(input);
			}while(true);
			
		} catch (IOException e) {
			System.err.println("ERROR: COULDN'T MAKE A CLIENT!");
			System.exit(0);
		}

	}
	
	
	public Client(InetAddress host, int port) throws IOException {
		sock = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
		
		
	}
	
	public void run() {
		/*try {
			String text = in.readLine();
			while(text != null && !text.toLowerCase().equals("EXIT")){
				System.out.println(text);
				text = in.readLine();
			}
			if (text == "EXIT"){
				shutdown();
			}
		} catch(NullPointerException e){
			System.err.println("ERROR: NO INPUT");
		
		} catch(IOException e){
			e.printStackTrace();
		}
		try{
			shutdown();
		} catch(IOException e) {
			e.printStackTrace();
		}*/
		boolean isTerminated = false;
		System.out.println("enter your Clientname: ");
		while(!isTerminated){
			try{
				clientName = uInput.nextLine();
				out.write("Welcome " +  clientName);
				out.newLine();
				out.flush();
				setClientID();
				out.write("Your client ID is: " + clientID);
				preMenu(clientName, clientID);
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void setClientID(){
		//TODO implement dit via server
	}
	public void preMenu(String clientName, int clientID){
		System.out.println("Dear " + clientName + ",");
		System.out.println("How do you like to play? " + "\n"
		+ "Play as Human:               1" + "\n" +"Let the computer play:       2");
		System.out.print("\n" + "Voer uw keuze in:............." );
		String sortPlayer = uInput.nextLine();
		if(true){
			
		}
	}
	
	
	public int getClientID(){
		return clientID;
	}
	
	public void sendCommand(String command){
		try {
			out.write(command);
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	public void shutdown() throws IOException{
		System.out.print("Closing socket connection...");
    	sock.close();
		in.close();
    	out.close();
	}
	
	public static String readString(String tekst) {
		return tekst;
	}

}
