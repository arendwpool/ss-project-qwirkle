package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public abstract class TUI implements Observer {
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Voer uw naam in:"};
	private static final String[] MAIN_MENU = {null, "Nieuw spel starten", "Opties", "Top-5", "Afsluiten"};
	private static final String[] NEW_GAME_MENU = {"Hallo [naam]! Onderstaande opties zijn te veranderen door het gepaste nummer te selecteren. Als de opties kloppen \"Start\" selecteren.", 
													"Online", "Tegen computer", "Aantal tegenstanders", "Start", "Terug"};
	private static final String[] OPTIONS_MENU = {null, "Ander interface laden", "Terug"};
	private static final int AMOUNT_CHARACTERS = 50;
	private String playerName = "Arend";
	private Scanner scanner = new Scanner(System.in);
	boolean terminated = false;
	
	public TUI(){
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void start(){
		while(terminated == false){
			renderMenu(PRE_MENU);
			determineOption(PRE_MENU);
			createSpace();
			renderMenu(MAIN_MENU);
			determineOption(MAIN_MENU);
		}
	}
	
	public void renderBoard(){
		
	}
	
	/**
	 * 
	 * @param noMenuItems
	 * @param menuItems(op index 0 altijd een uitleg bij een menu! zonder uitleg null houden!
	 */
	public void renderMenu(String[] menuItems){ //TODO vervangen van Array?
		if(menuItems[0] != null){
			menuItems[0].replace("[naam]", "Arend");
			System.out.println(menuItems[0]);
		}
		for(int i = 1; i < menuItems.length; i++){
			System.out.println(menuItems[i] + dots(menuItems[i]) + i);
		}
		if(menuItems.equals(PRE_MENU)){
			System.out.print("Typ uw naam en druk op enter: ");
		}else{
			System.out.print("\n" + "Voer uw keuze in:" + dots("Voer uw keuze in: ") + ".");
		}
	}
	
	public String dots(String string){
		int stringLength = string.toCharArray().length;
		String dots = "";
		for(int i = 0; i < AMOUNT_CHARACTERS - stringLength; i++){
			dots += ".";
		}
		return dots;
	}
	
	public void determineOption(String[] menu){
		String choice = scanner.nextLine();
		if(menu.equals(PRE_MENU)){
			playerName = choice; //TODO naam mag niet leeg zijn
		}else if(menu.equals(MAIN_MENU)){
			switch (choice){
				case "1": renderMenu(NEW_GAME_MENU);
						  break;
				case "2": renderMenu(OPTIONS_MENU);
						  break;
				case "3": //TODO implement; break;
				case "4": terminated = false;
						  break;
			}
		}else if(menu.equals(NEW_GAME_MENU)){
			switch (choice){
			case "1": //TODO implement renderMenu(NEW_GAME_MENU);
					  break;
			case "2": //TODO implement renderMenu(OPTIONS_MENU);
					  break;
			case "3": //TODO implement; break;
			case "5": renderMenu(MAIN_MENU);
			}
		}else if(menu.equals(OPTIONS_MENU)){
			switch(choice){

			case "2": renderMenu(MAIN_MENU);
			}
		}
	}
	
	public void createSpace(){
		System.out.print("\n \n");
	}
	
}
