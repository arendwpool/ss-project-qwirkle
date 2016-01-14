package view;

import java.util.Observable;
import java.util.Observer;


public abstract class TUI implements Observer {
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Voer uw naam in:"};
	private static final String[] MAIN_MENU = {null, "Nieuw spel starten", "Opties", "Top-5"};
	private static final String[] NEW_GAME_MENU = {"Hallo [naam]! Onderstaande opties zijn te veranderen door het gepaste nummer te selecteren. Als de opties kloppen \"Start\" selecteren.", 
													"Online", "Tegen computer", "Aantal tegenstanders", "Start", "Terug"};
	private static final String[] OPTIONS_MENU = {null, "Ander interface laden"};
	private static final int AMOUNT_CHARACTERS = 50;
	private String playerName = "Arend";
	public TUI(){
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void start(){
		boolean terminated = false;
		while(terminated == false){
			renderMenu(NEW_GAME_MENU);
			terminated = true;
			determineOption();
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
			menuItems[0].replace("[naam]", playerName);
			System.out.println(menuItems[0]);
		}
		for(int i = 1; i < menuItems.length; i++){
			System.out.println(menuItems[i] + dots(menuItems[i]) + i);
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
	
	public void determineOption(){
		
	}
	
}
