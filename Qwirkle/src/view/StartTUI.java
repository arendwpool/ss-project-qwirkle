package view;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;

import controllers.GameController;

public class StartTUI extends TUI{
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Ik ben een Menselijke speler", "Ik ben een Computerspeler"};
	private static final String[] MAIN_MENU = {"Hallo [naam]!", "Start", "Afsluiten"};
	private static final String[] IP_MENU = {null, "Voer het gewenste ip adres in:"};
	
	public StartTUI(GameController gc) {
		super(gc);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// Deze methode update niets
	}
	
	public void start(){
		while (true) {
			renderMenu(PRE_MENU);
			int choice = determineOption();
			if (choice == 1) {
				gc.isHuman(true);
				createSpace();
				renderMenu(NAME_MENU);
				while (true) {
					playerName = determineString();
					if (playerName.length() > 2 && playerName.length() < 13) {
						gc.setPlayerName(playerName);
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
						while (!gc.isValidIP(ip)) {
							System.out.println("Geen geldig ip adres...");
							renderMenu(IP_MENU);
							ip = determineString();
							createSpace();
						}
						gc.setIP(ip);
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
						gc.setQuit(true);
						break;
					} else {
						System.out.println("Voer een geldige optie in");
					}
				}
				break;
			} else if (choice == 2) {
				gc.isHuman(false);
				renderMenu(IP_MENU);
				String ip = determineString();
				while (!gc.isValidIP(ip)) {
					System.out.println("Geen geldig ip adres...");
					renderMenu(IP_MENU);
					ip = determineString();
					createSpace();
				}
				gc.setIP(ip);
				break;
			} else {
				System.out.println("Voer een geldige optie in.");
			}
		}
	}
}
