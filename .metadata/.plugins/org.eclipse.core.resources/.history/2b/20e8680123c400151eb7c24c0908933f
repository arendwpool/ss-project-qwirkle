package models;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;

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
							determineMove(move);
							break;
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("2")) {
					System.out.println("Typ uw keuze in de vorm: [tegelnummer] [tegelnummer] etc.");
					System.out.println("Typ niets in en druk op enter als u terug wil gaan.");
					while (true) {
						try {
							String swap = in.nextLine();
							if (swap.equals("")) {
								break;
							}
							determineSwap(swap);
							break;
						} catch (NumberFormatException e) {
							System.out.println("Invoer is niet geldig");
						}
					}
				} else if (choice.equals("3")) {
					setChanged();
					notifyObservers("MadeMove");
					break outerloop;
				}
			//TODO vraag over in.close();
			}
		}
	}

	

	

	/**
	 * Kijkt wat de speler als volgende zet wil doen. Hiervoor split hij een geven 
	 * String bij elke spatie. Hij vertaalt deze input dan, en geeft dit door aan makeMove.
	 * @param string
	 */
	public void determineMove(String string) {
		try {
			String[] moveArray = string.split(" ");
			int tileNumber = Integer.parseInt(moveArray[0]) - 1;
			Tile tile = hand.get(tileNumber);		
			int x = Integer.parseInt(moveArray[1])+90;
			int y = Integer.parseInt(moveArray[2])+90;
			makeMove(x, y, tile);
		} catch (InvalidMoveException e) {
			System.out.println("Deze move mag niet");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Geen geldige invoer.");
		}
	}
	
	/**
	 * Hetzelfde principe als determineMove. Deze geeft de vertaalde String door aan
	 * game.SwapTiles
	 * @param string
	 */
	public void determineSwap(String string) {
		try {
			ArrayList<Tile> tiles = hand;
			String[] swapArray = string.split(" ");
			ArrayList<Tile> tilesToSwap = new ArrayList<Tile>();
			for (int i = 0; i < swapArray.length; i++) {
				int tileNumber = Integer.parseInt(swapArray[i]) - 1;
				Tile tile = tiles.get(tileNumber);
				tilesToSwap.add(tile);
			}
			game.swapTiles(tilesToSwap, this);
		} catch (NoTilesLeftInPileException e) {
			System.out.println("Er zitten geen tegels meer in de zak");
		} catch (InvalidMoveException e) {
			System.out.println("U mag nu niet ruilen");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Geen geldige invoer");
		}
	}

}
