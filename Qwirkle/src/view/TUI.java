package view;

import java.util.InputMismatchException;
import java.util.Observer;
import java.util.Scanner;

import controllers.GameController;
import network.Servershit;


public abstract class TUI implements Observer {
	
	private static final int AMOUNT_CHARACTERS = 75;
	private Scanner scanner;
	boolean terminated = false;
	protected GameController gc;
	protected String playerName;
	protected static final String[] NAME_MENU = {null, "Voer uw naam in:"};
	
	
	public TUI(){
		
	}
	
	public void start(){
		
	}
	
	/**
	 * 
	 * @param noMenuItems
	 * @param menuItems(op index 0 altijd een uitleg bij een menu! zonder uitleg null houden!
	 */
	public void renderMenu(String[] menuItems){
		if(menuItems[0] != null && menuItems[0].contains("[naam]")){
			menuItems[0] = menuItems[0].replace("[naam]", playerName);
			System.out.println(menuItems[0]);
		}
		for(int i = 1; i < menuItems.length; i++){
			System.out.println(menuItems[i] + dots(menuItems[i]) + i);
		}
		if(menuItems.equals(NAME_MENU)){
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

	public int determineOption(){
		int in = 0;
		while (true) {
			scanner = new Scanner(System.in);
			try {
				in = scanner.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Voer een nummer in");
			}
		}
		return in;
	}
	public String determineString(){
		String string = "";
		while (true) {
			scanner = new Scanner(System.in);
			string = scanner.nextLine();
			break;
		}
		return string;
	}
	
	public void createSpace(){
		System.out.print("\n \n");
	}
		
}
