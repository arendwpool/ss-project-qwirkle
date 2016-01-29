package network;

import java.util.Scanner;
/**
 * Werkt niet. Bedoeld om de server met een gegeven commando af te sluiten
 * @author Arend
 *
 */
public class ReadServerInput extends Thread {
	private Server server;
	Scanner scanner;

	public ReadServerInput(Server server) {
		this.server = server;
	}
	
	public void run() {
		while(true) {
			scanner = new Scanner(System.in);
			String input = "";
			while (scanner.hasNextLine())
			input = scanner.nextLine();
			if (input.equals("shutdown")) {
				server.shutDown();
			}
			//System.out.println(input); TODO oplossen: line not found error
			scanner.close();
		}
	}
}
