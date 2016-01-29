package view;

import java.util.Scanner;

/**
 * Maakt handige methoden voor input en weergaven beschikbaar.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public abstract class TUI{
	/**
	 * Lengte van een regel van een menu-item.
	 */
	private static final int AMOUNT_CHARACTERS = 75;	
	
	public TUI(){
		
	}
	
	public void update() {
		
	}
	
	/**
	 * Weergeeft een menu van gegeven menu-items.
	 * @param noMenuItems
	 * @param menuItems
	 */
	public void renderMenu(String[] menuItems){
		for(int i = 1; i < menuItems.length; i++){
			System.out.println(menuItems[i] + dots(menuItems[i]) + i);
		}
		System.out.print("\n" + "Voer uw keuze in:" + dots("Voer uw keuze in: ") + ".");
	}
	
	/**
	 * Berekent het aantal punten dat weergegeven moet worden om de regels
	 * dezelfde lengte te geven.
	 * @param string
	 * @return dots
	 */
	public String dots(String string){
		int stringLength = string.toCharArray().length;
		String dots = "";
		for(int i = 0; i < AMOUNT_CHARACTERS - stringLength; i++){
			dots += ".";
		}
		return dots;
	}

	/**
	 * Geeft de integer van een input terug
	 * @return input
	 */
	public int determineInt() {
		Scanner scanner = new Scanner(System.in);
		int input = Integer.parseInt(scanner.nextLine());
		return input;
	}
	
	/**
	 * Geeft de String van een input terug.
	 * @return input
	 */
	public String determineString(){
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		return input;
	}
	
	/**
	 * Zet een paar enters neer om ruimte te cre�eren.
	 */
	public void createSpace(){
		System.out.print("\n \n");
	}
	
	/**
	 * Zet een bericht neer in de console.
	 * @param msg
	 */
	public void print(String msg){
		System.out.println(msg);
	} 
}
