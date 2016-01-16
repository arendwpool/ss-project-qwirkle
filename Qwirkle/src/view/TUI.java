package view;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import models.Game;
import models.Tile;


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
	private static final String[] BOARD_MENU = {"Als u aan de beurt ben kan u tegels ruilen of neerleggen. Dit gaat per tegel, aan het eind van uw beurt drukt u op: klaar.", "Een tegel neerleggen: ", "Een tegel ruilen", "Klaar"};
	private static final String[] SET_TILE_MENU = {"Typ uw keuze in de vorm: [kleur] [symbool] [x] [y]", "Uw keuze:"}; //TODO veranderen in [tegelnummer].[x].[y]
	private static final String[] SWAP_TILE_MENU = {"Typ uw keuze in de vorm: tegelnummer tegelnummer etc.", "Uw keuze"};
	
	private static final int AMOUNT_CHARACTERS = 50;
	private String playerName;
	private Scanner scanner = new Scanner(System.in);
	boolean terminated = false;
	private int noOfPlayers;
	private String ip;
	private int port;
	private Game game;
	
	
	public TUI(Game game){
		this.game = game;
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
		for (int x = game.getBoard().viewOfMinX; x <= game.getBoard().viewOfMaxX; x++){
			for(int y = game.getBoard().viewOfMinY; y <= game.getBoard().viewOfMaxY; y++){
				if (game.getBoard().getField(x, y).getColor() == "empty" && game.getBoard().getField(x, y).getSymbol() == "empty"){
					System.out.print("|" +x +"," + y +"|");
					if(y == (game.getBoard().viewOfMaxY)){
						System.out.println();
					}
				}else{
					System.out.print("|-"+colorRepresentive(game.getBoard().getField(x, y).getColor())+ "-" + symbolRepresentive(game.getBoard().getField(x, y).getSymbol()) +"-|");
					if(y == (game.getBoard().viewOfMaxY)){
						System.out.println();
					}
				}
			}
		}
		renderMenu(BOARD_MENU);
		determineOption(BOARD_MENU);
		//System.out.println("Uw regels: ");
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
	
	//TODO eventueel veranderen door arrays te maken?
	public void determineOption(String[] menu){
		createSpace();
		String choice = scanner.nextLine();
		if(menu.equals(PRE_MENU)){
			playerName = choice; //TODO naam mag niet leeg zijn
		}else if(menu.equals(MAIN_MENU)){
			if(choice.equals("1")){
				renderMenu(NEW_GAME_MENU);
				determineOption(NEW_GAME_MENU);
			}else if(choice.equals("2")){
				renderMenu(OPTIONS_MENU);
				determineOption(OPTIONS_MENU);
			}else if(choice.equals("3")){
				renderMenu(TOP_5_MENU);
				determineOption(TOP_5_MENU);
			}else if(choice.equals("4")){
				terminated = true;
			}
		}else if(menu.equals(NEW_GAME_MENU)){
			if(choice.equals("1")){
				renderMenu(ONLINE_MENU);
				determineOption(ONLINE_MENU);
			}else if(choice.equals("2")){
				renderMenu(AGAINST_PC_MENU);
				determineOption(AGAINST_PC_MENU);
			}else if(choice.equals("3")){
				renderMenu(OPPONENTS_MENU);
				determineOption(OPPONENTS_MENU);
			}else if(choice.equals("4")){
				renderBoard(); //TODO voorwaarden geven
			}else if (choice.equals("5")){
				renderMenu(MAIN_MENU);
				determineOption(MAIN_MENU);
			}
		}else if(menu.equals(OPTIONS_MENU)){
			if(choice.equals("2")){
				renderMenu(MAIN_MENU);
			}
		}else if(menu.equals(TOP_5_MENU)){
			if(choice.equals("1")){
				renderMenu(MAIN_MENU);
			}
		}else if(menu.equals(ONLINE_MENU)){
			if(choice.equals("1")){
				renderMenu(IP_MENU);
				determineOption(IP_MENU);
			}else if(choice.equals("2")){
				renderMenu(PORT_MENU);
			}else if(choice.equals("3")){
				renderMenu(NEW_GAME_MENU);
			}			
		}else if(menu.equals(AGAINST_PC_MENU)){
			int i = Integer.parseInt(choice);
			if(i > 0 && i < 4){
				//TODO implement moeilijkheidsgraad = i;
				renderMenu(NEW_GAME_MENU);
			}else{
				System.out.print("Geen geldige invoer, kiezen uit: 1, 2 en 3");
				renderMenu(AGAINST_PC_MENU);
			}
			
		}else if(menu.equals(OPPONENTS_MENU)){
			int i = Integer.parseInt(choice);
			if(i > 0 && i < 4){
				noOfPlayers = i;
				renderMenu(NEW_GAME_MENU);
			}else{
				System.out.print("Geen geldige invoer, kiezen uit: 1, 2 en 3");
				renderMenu(AGAINST_PC_MENU);
			}
			
		}else if(menu.equals(IP_MENU)){
			if (isValidIP(choice)){
				ip = choice;
				renderMenu(NEW_GAME_MENU);
				determineOption(NEW_GAME_MENU);
			}else{
				System.out.println("Geen geldig ip adres.");
				renderMenu(IP_MENU);
				determineOption(IP_MENU);
			}
			
		}else if(menu.equals(PORT_MENU)){
			int portNo = Integer.parseInt(choice);
			if (portNo <= 8000 && portNo > 0){
				port = portNo;
				renderMenu(NEW_GAME_MENU);
			}else{
				System.out.print("Geen geldig poortnummner, deze moet onder de 8000 en boven 0 zijn.");
				renderMenu(PORT_MENU);
			}
		}else if(menu.equals(BOARD_MENU)){
			if(choice.equals("1")){
				renderMenu(SET_TILE_MENU);
				determineOption(SET_TILE_MENU);
			}else if(choice.equals("2")){
				renderMenu(SWAP_TILE_MENU);
				determineOption(SWAP_TILE_MENU);
			}else if(choice.equals("3")){
				//TODO beurt beŽindigen
			}
		}else if(menu.equals(SET_TILE_MENU)){
			renderMenu(SET_TILE_MENU);
			String[] move = choice.split(" ");
			boolean containsColor = false;
			boolean containsSymbol = false;
			int x = Integer.parseInt(move[2]);
			int y = Integer.parseInt(move[3]);
			for(String kleur : Tile.kleuren){
				if(kleur.equals(move[0])){
					containsColor = true;
					break;
				}
			}
			for(String symbol : Tile.symbolen){
				if(symbol.equals(move[1])){
					containsSymbol = true;
				}
			}
			if(containsColor && containsSymbol && move.length > 0){
				determineMove(move[0], move[1], x, y);
				createSpace();
				renderBoard();
			}else{
				System.out.print("Ongeldige invoer");
				renderMenu(SET_TILE_MENU);
				determineOption(SET_TILE_MENU);
			}
			
			
		}else{
			//TODO implement: wat als er een verkeerde optie is geselecteerd?
		}
	}
	
	public void createSpace(){
		System.out.print("\n \n");
	}
	
	public boolean isValidIP(String ip){
		ip = ip.replace(".", " ");
		String[] ints = ip.split(" ");
		boolean isValidInt = true;
		for(String integer : ints){
			int i = Integer.parseInt(integer);
				if(i > 255){
					isValidInt = false;
				}
		}
		return (isValidInt == true && ints.length == 4);
	}
	
	public String colorRepresentive(String color){
		String representive = "";
		if(color.equals("groen")){
			representive = "G";
		}else if (color.equals("rood")){
			representive = "R";
		}else if (color.equals("blauw")){
			representive = "B";
		}else if (color.equals("paars")){
			representive = "P";
		}else if (color.equals("geel")){
			representive = "Y";
		}else if (color.equals("oranje")){
			representive = "O";
		}
		return representive;
	}
	
	public String symbolRepresentive(String symbol){
		String representive = "";
		if(symbol.equals("circel")){
			representive = "C";
		}else if (symbol.equals("ruit")){
			representive = "R";
		}else if (symbol.equals("plus")){
			representive = "P";
		}else if (symbol.equals("ster")){
			representive = "S";
		}else if (symbol.equals("vierkant")){
			representive = "V";
		}else if (symbol.equals("kruis")){
			representive = "K";
		}
		return representive;
	}
	
	public void determineMove(String kleur, String symbool, int x, int y){ //TODO aanpassen als een speler tegels kan hebben
		game.getBoard().setTile(x, y, new Tile(kleur, symbool));
		game.getBoard().boardSize();
	}
}
