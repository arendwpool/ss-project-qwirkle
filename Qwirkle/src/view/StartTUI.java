package view;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import controllers.GameController;
import models.Client2;

public class StartTUI extends TUI{
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Ik ben een Menselijke speler", "Ik ben een Computerspeler"};
	private static final String[] MAIN_MENU = {"Hallo [naam]!", "Start", "Afsluiten"};
	private static final String[] IP_MENU = {null, "Voer het gewenste ip adres in:"};
	private Client2 client;
	
	public StartTUI() {
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// Deze methode update niets
	}
	
	/**
	 * Start de TUI. Deze TUI achterhaalt de nodige gegevens van een lokal speler
	 * en geeft dit door aan de controller. Er wordt eerst gevraagd of een speler
	 * een menselijke speler wil zijn, of een computer. Als de speler heeft aangegeven
	 * een mens te willen zijn wordt er om zijn naam gevraagd. Als de speler aan het
	 * eind heeft aangegeven te willen starten wordt deze TUI afgesloten.
	 */
	public void start(){
		while (true) {
			renderMenu(PRE_MENU);
			int choice = determineOption();
			if (choice == 1) {
				client.isHuman(true);
				createSpace();
				renderMenu(NAME_MENU);
				while (true) {
					playerName = determineString();
					if (playerName.length() > 2 && playerName.length() < 13) {
						client.setPlayerName(playerName);
						break;
					} else {
						System.out.println("Voer een naam in van minstens  3 karakters");
					}
				}
				createSpace();
				renderMenu(IP_MENU);
				while (true) {
					String ip = "";
					try {
						ip = determineString();
						while (!client.isValidIP(ip)) {
							System.out.println("Geen geldig ip adres...");
							renderMenu(IP_MENU);
							ip = determineString();
							createSpace();
						}
						client.setIP(ip);
						break;
					} catch (NumberFormatException e) {
						System.out.println("Voer een geldig ip adres in");
					}
				}	
				while (true) {
					renderMenu(MAIN_MENU);
					choice = determineOption();
					createSpace();
					if (choice == 1) {
						//TODO geef startsein
						break;
					} else if (choice == 2) {
						client.setQuit(true);
						break;
					} else {
						System.out.println("Voer een geldige optie in");
					}
				}
				break;
			} else if (choice == 2) {
				client.isHuman(false);
				renderMenu(IP_MENU);
				String ip = determineString();
				while (!gc.isValidIP(ip)) {
					System.out.println("Geen geldig ip adres...");
					renderMenu(IP_MENU);
					ip = determineString();
					createSpace();
				}
				client.setIP(ip);
				break;
			} else {
				System.out.println("Voer een geldige optie in.");
			}
		}
	}
}
