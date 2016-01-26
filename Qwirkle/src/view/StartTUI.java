package view;

import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class StartTUI extends TUI{
	/**
	 * De string op index 0 is altijd een beschrijving bij een menu
	 */
	private static final String[] PRE_MENU = {null, "Ik ben een Menselijke speler", "Ik ben een Computerspeler"};
	private static final String[] MAIN_MENU = {"Hallo [naam]!", "Start", "Afsluiten"};
	private static final String[] IP_MENU = {null, "Voer het gewenste ip adres in:"};
	
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
	}
}
