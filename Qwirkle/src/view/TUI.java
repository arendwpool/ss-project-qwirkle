package view;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class TUI implements Observer {
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Voer uw naam in:"};
	private static final String[] MAIN_MENU = {"Hallo [naam]!", "Nieuw spel starten", "Opties", "Top-5", "Afsluiten"};
	private static final String[] OPTIONS_MENU = {null, "Ander interface laden", "Terug"};
	private static final String[] TOP_5_MENU = {null, "Terug"};
	private static final String[] NEW_GAME_MENU = {"Onderstaande opties zijn te veranderen door het gepaste nummer te selecteren. Als de opties kloppen \"Start\" selecteren.", 
													"Online", "Tegen computer", "Aantal tegenstanders", "Start", "Terug"};
	private static final String[] ONLINE_MENU = {null, "IP adres invoeren", "Poort invoeren", "Terug"};
	private static final String[] IP_MENU = {null, "Voer het gewenste adres in:"};
	private static final String[] PORT_MENU = {null, "Voer het gewenste poortnummer in: "};
	private static final String[] AGAINST_PC_MENU = {"Typ uw gewenste moeilijkheidsgraad in(1 voor makkelijk, 2 voor normaal en 3 voor moeilijk).", "Moeilijkheidsgraad: "};
	private static final String[] OPPONENTS_MENU = {"Typ in hoeveel tegenstanders u wil hebben(minimaal 1, maximaal 3).", "Aantal tegenstanders: "};
	
	private static final int AMOUNT_CHARACTERS = 50;
	private String playerName;
	private Scanner scanner = new Scanner(System.in);
	boolean terminated = false;
	private int noOfPlayers;
	private String ip;
	private int port;
	public TUI(){
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public void start(){
		renderMenu(PRE_MENU);
		determineOption(PRE_MENU);
		renderMenu(MAIN_MENU);
		while(terminated == false){
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
			menuItems[0] = menuItems[0].replace("[naam]", playerName);
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
				case "1": 	renderMenu(NEW_GAME_MENU);
						  	determineOption(NEW_GAME_MENU);
							createSpace();
							break;
				case "2": 	renderMenu(OPTIONS_MENU);
						  	determineOption(OPTIONS_MENU);
							createSpace();
							break;
				case "3": 	renderMenu(TOP_5_MENU);
							determineOption(TOP_5_MENU);
							createSpace();
							break;
				case "4": 	terminated = false;
						  	break;
			}
		}else if(menu.equals(NEW_GAME_MENU)){
			switch (choice){
				case "1": 	renderMenu(ONLINE_MENU);
			  				determineOption(ONLINE_MENU);
			  				createSpace();
			  				break;
				case "2": 	renderMenu(AGAINST_PC_MENU);
			  				determineOption(AGAINST_PC_MENU);
			  				createSpace();
			  				break;
				case "3": 	renderMenu(OPPONENTS_MENU);
			  				determineOption(OPPONENTS_MENU);
			  				createSpace();
			  				break;
				case "4":	renderBoard();
							break;
				case "5": 	renderMenu(MAIN_MENU);
							determineOption(MAIN_MENU);
							break;
			}
		}else if(menu.equals(OPTIONS_MENU)){
			switch(choice){
				case "2": 	renderMenu(MAIN_MENU);
							createSpace();
			}
		}else if(menu.equals(TOP_5_MENU)){
			switch(choice){
			case "1": 	renderMenu(MAIN_MENU);
						createSpace();
		}
			
		}else if(menu.equals(ONLINE_MENU)){
			switch(choice){
				case "1": 	renderMenu(IP_MENU);
							createSpace();
				case "2":	renderMenu(PORT_MENU);
							createSpace();
				case "3":   renderMenu(NEW_GAME_MENU);
							createSpace();
		}
			
		}else if(menu.equals(AGAINST_PC_MENU)){
			int i = Integer.parseInt(choice);
			if(i > 0 && i < 4){
				//TODO implement moeilijkheidsgraad = i;
				renderMenu(NEW_GAME_MENU);
				createSpace();
			}else{
				System.out.print("Geen geldige invoer, kiezen uit: 1, 2 en 3");
				renderMenu(AGAINST_PC_MENU);
				createSpace();
			}
			
		}else if(menu.equals(OPPONENTS_MENU)){
			int i = Integer.parseInt(choice);
			if(i > 0 && i < 4){
				noOfPlayers = i;
				renderMenu(NEW_GAME_MENU);
				createSpace();
			}else{
				System.out.print("Geen geldige invoer, kiezen uit: 1, 2 en 3");
				renderMenu(AGAINST_PC_MENU);
				createSpace();
			}
			
		}else if(menu.equals(IP_MENU)){
			if (isValidIP(choice)){
				ip = choice;
				renderMenu(NEW_GAME_MENU);
				createSpace();
			}else{
				System.out.print("Geen geldig ip adres.");
				renderMenu(IP_MENU);
				createSpace();
			}
			
		}else if(menu.equals(PORT_MENU)){
			int portNo = Integer.parseInt(choice);
			if (portNo <= 8000 && portNo > 0){
				port = portNo;
				renderMenu(NEW_GAME_MENU);
				createSpace();
			}else{
				System.out.print("Geen geldig poortnummner, deze moet onder de 8000 en boven 0 zijn.");
				renderMenu(PORT_MENU);
				createSpace();
			}
			
		}else{
			//TODO implement: wat als er een verkeerde optie is geselecteerd?
		}
	}
	
	public void createSpace(){
		System.out.print("\n \n");
	}
	
	public boolean isValidIP(String ip){
		String[] ints = ip.split(".");
		boolean isValidInt = true;
		for(String integer : ints){
			int i = Integer.parseInt(integer);
				if(i > 255){
					isValidInt = false;
				}
		}
		return (isValidInt == true && ints.length == 4);
	}
	
}
