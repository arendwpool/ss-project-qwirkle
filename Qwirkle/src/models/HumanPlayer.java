package models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import protocol.Protocol;

public class HumanPlayer extends ClientPlayer {
	/**
	 * construeert een nieuwe menselijke speler. Deze speler krijgt een hand met tiles mee.
	 * Elke speler met een score van 0. Uiteindelijk wordt de speler aan het spel toegevoegd.
	 * @param name
	 * @param game
	 */
	public HumanPlayer(String name) {
		super(name);
	}
	
	

	/**
	 * Vraagt aan de speler wat hij wil doen voor zijn beurt. De input geeft hij vervolgens
	 * door aan determinMove als hij een tegel wilt leggen. Als de speler een tegel wil
	 * ruilen geeft de methode dit door aan determineSwap.
	 */
	public String determineMove() {
		Scanner in = new Scanner(System.in);
		outerloop:
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
							return "move " + move;
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("2")) {
					System.out.println("Typ het nummer in van de tegel die u wil ruilen");
					System.out.println("Typ niets in en druk op enter als u terug wil gaan.");
					while (true) {
						try {
							String swap = in.nextLine();
							if (swap.equals("")) {
								break;
							}
							return "swap "+ swap;
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("3")) {
					return "done ";
				} else {
					return null;
				}
			}
		}
	}

	

	

	/**
	 * Kijkt wat de speler als volgende zet wil doen. Hiervoor split hij een geven 
	 * String bij elke spatie. Hij vertaalt deze input dan, en geeft dit door aan makeMove.
	 * @param string
	 */
	public String determineMove(String[] string) {
		try {
			int tileNumber = Integer.parseInt(string[1]) - 1;
			Tile tile = this.getHand().get(tileNumber);		
			int x = Integer.parseInt(string[2])+90;
			int y = Integer.parseInt(string[3])+90;
			return x + Protocol.MESSAGESEPERATOR + y + Protocol.MESSAGESEPERATOR + tile.getSymbol() + Protocol.MESSAGESEPERATOR + tile.getColor();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Geen geldige invoer.");
		}
		return null;
	}
	
	/**
	 * Hetzelfde principe als determineMove. Deze geeft de vertaalde String door aan
	 * game.SwapTiles
	 * @param string
	 */
	public String determineSwap(String[] string) {
		try {
			ArrayList<Tile> tiles = this.getHand();
			ArrayList<Tile> tilesToSwap = new ArrayList<Tile>();
			int tileNumber = Integer.parseInt(string[1]) - 1;
			Tile tile = tiles.get(tileNumber);
			tilesToSwap.add(tile);
			return tile.getSymbol() + Protocol.MESSAGESEPERATOR + tile.getColor();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Geen geldige invoer");
		}
		return null;
	}

}
