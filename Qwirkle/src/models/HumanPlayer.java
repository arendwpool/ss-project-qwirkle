package models;

import java.util.Scanner;

import exceptions.InvalidMoveException;
import network.Protocol;
import util.TileUtils;

public class HumanPlayer extends Player {

	private boolean madeMove = false;
	private boolean swapped = false;
	private Scanner in;
	public HumanPlayer(String name) {
		super(name);
	}
	
	/**
	 * Vraagt aan de speler wat hij wil doen voor zijn beurt. De input geeft hij vervolgens
	 * door aan determinMove als hij een tegel wilt leggen. Als de speler een tegel wil
	 * ruilen geeft de methode dit door aan determineSwap.
	 */
	public String[] determineMove() {
		in = new Scanner(System.in);
		while(true) {
			while (true) {
				System.out.println("tegel leggen ............. 1");
				System.out.println("Ruilen ................... 2");
				System.out.println("klaar .................... 3");
				String choice = in.nextLine();
				if (choice.equals("1")) {
					System.out.println("Typ uw keuze in de vorm: [tegelnummer] [getal boven bord] [getal links van bord]");
					System.out.println("Typ niets in en druk op enter als u terug wil gaan.");
					while (true) {
						try {
							String move = in.nextLine();
							if (move.equals("")) {
								break;
							}
							if (move.length() == 5 && swapped == false) {
								madeMove = true;
								return determineMove(move);
							} else {
								if(swapped == false)
								break;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("2")) {
					System.out.println("Voer een tegelnummer in");
					System.out.println("Typ niets in en druk op enter als u terug wil gaan.");
					while (true) {
						try {
							String swap = in.nextLine();
							if (swap.equals("")) {
								break;
							}
							String[] slicedSwap = swap.split(Protocol.MESSAGESEPERATOR);
							if (slicedSwap.length > 1) {
								System.out.println("Dit mag niet...");
								break;
							}
							if (madeMove == false) {
								swapped = true;
								String[] toSwap = determineTile(slicedSwap[0]);
								return toSwap;								
							} else {
								System.out.println("Dit mag niet...");
							}
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("3")) {
					String[] doneCommand = {Protocol.CLIENT_CORE_DONE};
					//in.close(); TODO vraag
					swapped = false;
					madeMove = false;
					return doneCommand;
				}
			}
		}
	}
	/**
	 * Kijkt wat de speler als volgende zet wil doen. Hiervoor split hij een geven 
	 * String bij elke spatie. Hij vertaalt deze input dan, en geeft dit door aan makeMove.
	 * @param string
	 */
	public String[] determineMove(String move) {
		String[] slicedMove = move.split(Protocol.MESSAGESEPERATOR);
		int x = Integer.parseInt(slicedMove[1]);
		int y = Integer.parseInt(slicedMove[2]);
		String[] tile = determineTile(slicedMove[0]);
		String[] toReturn = {Integer.toString(x), Integer.toString(y), tile[0], tile[1]};
		return toReturn;
	}
	
	public String[] determineTile(String tileNumber) {
		int tileNo = Integer.parseInt(tileNumber) - 1;
		Tile tile = getHand().get(tileNo);
		String[] toReturn = {Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
		return toReturn;
	}
	
}
