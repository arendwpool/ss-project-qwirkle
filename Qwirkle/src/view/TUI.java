package view;

import java.util.Scanner;



public abstract class TUI{
	
	private static final int AMOUNT_CHARACTERS = 75;
	boolean terminated = false;
	protected String playerName;
	protected static final String[] NAME_MENU = {null, "Voer uw naam in:"};
	
	
	public TUI(){
		
	}
	
	public void update(){
		
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

	public int determineInt() {
		Scanner scanner = new Scanner(System.in);
		int input = Integer.parseInt(scanner.nextLine());
		return input;
	}
	
	public String determineString(){
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		return input;
	}
	
	public void createSpace(){
		System.out.print("\n \n");
	}
	
	public void print(String msg){
		System.out.println(msg);
	} 
}
