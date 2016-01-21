package view;

import java.util.Observable;
import java.util.Observer;

import controllers.GameController;
import models.ComputerPlayer;
import models.Player;

public class StartTUI extends TUI implements Observer{
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
		// TODO Auto-generated method stub
	}
	
	public void start(){
		renderMenu(PRE_MENU);
		int choice = determineOption();
		if (choice == 1) {
			createSpace();
			renderMenu(NAME_MENU);
			playerName = determineString();
			gc.setPlayerName(playerName);
			createSpace();
			renderMenu(IP_MENU);
			String ip = determineString();
			while (!gc.isValidIP(ip)) {
				System.out.println("Geen geldig ip adres...");
				renderMenu(IP_MENU);
				ip = determineString();
				createSpace();
			}
			gc.setIP(ip);
			renderMenu(MAIN_MENU);
			choice = determineOption();
			createSpace();
			if (choice == 1) {
				//Doet niets, dit zorgt dat de controller door kan gaan
			}
		} else if (choice == 2) {
			//TODO doe iets
		}
	}
}
