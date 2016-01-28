package view;

import java.util.ArrayList;
import java.util.Observable;
import exceptions.PlayerNotFoundException;
import models.ComputerPlayer;
import models.Game;
import models.Player;
import models.Tile;
import network.Client;

/**
 * TUI die een bord weergeeft.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public class BoardTUI extends TUI{
	/**
	 * Maakt een nieuwe instantie van het TUI.
	 * @param gc
	 * @param game
	 */
	public BoardTUI(Game game, Client client) {
		this.game = game;
		this.client = client;
	}

	private Game game;
	private Client client;
	
	/**
	 * Het TUI functioneerd als een observer. Deze methode update het spel op een 
	 * juiste wijze als een update is doorgegeven.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 != null && arg1.equals("MadeMove")) {
			//gc.done((Player) arg0); 
		} else {
			game.getBoard().boardSize();
			update();
		}
	}
	
	/**
	 * Start de TUI door de juiste objecten te weergeven. 	
	 */
	public void update(){
		renderBoard();
		showTiles();
		showScore();
	}
	
	/**
	 * Weergeeft de speler die aan de beurt is.
	 */
	public void showCurrentPlayer() {
		if (game.getCurrentPlayer() instanceof ComputerPlayer) {
			System.out.println("De huidige speler is "+game.getCurrentPlayer().getName());
		} else {
			System.out.println("De huidige speler is "+game.getCurrentPlayer().getName());
		}
	}

	/**
	 * Weergeeft de naam en score van elke speler in een spel.
	 */
	private void showScore() {
		for (Player player : game.getPlayers()) {
			if (player.getName().equals(client.getLocalPlayer().getName())){
				System.out.println("U: " + player.getScore());
			} else {
				System.out.println(player.getName()+": " + player.getScore());
			}
		}
	}
	
	/**
	 * Creeert een vergrotend bord. Kijkt hiervoor naar de waarden die in Board.java worden berekend.
	 */
	public void renderBoard(){
		for(int i = game.getBoard().viewOfMinX - 90; i <= game.getBoard().viewOfMaxX - 90; i++){
			System.out.print("	"+i);
		}
		System.out.println("\n\n");
		for (int y = game.getBoard().viewOfMaxY; y >= game.getBoard().viewOfMinY; y--){
			for(int x = game.getBoard().viewOfMinX; x <= game.getBoard().viewOfMaxX; x++){
				if (game.getBoard().getField(x, y).getColor() == "empty" && game.getBoard().getField(x, y).getSymbol() == "empty"){
					if (x == game.getBoard().viewOfMinX){
						System.out.print(y-90 + "	");
					}
					System.out.print("-	");
					if(x == (game.getBoard().viewOfMaxX)){
						System.out.println("\n");
					}
				}else{
					System.out.print(colorRepresentive(game.getBoard().getField(x, y).getColor())+ "-" + symbolRepresentive(game.getBoard().getField(x, y).getSymbol())+ "	");
					if(y == (game.getBoard().viewOfMaxY)){
						System.out.println();
					}
				}
			}
		}
	}

	/**
	 * Geeft aan hoe een kleur wordt weergegeven op het bord.
	 * @param color
	 * @return representive
	 */
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
	
	/**
	 * Geeft aan hoe een symbool wordt weergegeven op het bord.
	 * @param symbol
	 * @return representnive
	 */
	public String symbolRepresentive(String symbol){
		String representive = "";
		if(symbol.equals("cirkel")){
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
	
	/**
	 * Laat de tegels zien die de lokale speler in zijn hand heeft. Zet hiervoor een nummer
	 * zodat de speler kan kiezen met welke tegels hij zijn beurt wil maken.
	 */
	public void showTiles(){
		try{
			ArrayList<Tile> tiles = client.getLocalPlayer().getHand();
			System.out.println("Uw tegels: ");
			for (int i = 1; i <= tiles.size(); i++) {
				System.out.print(i + ": " + tiles.get(i-1).getColor()+tiles.get(i-1).getSymbol()+"  ");
			}
			System.out.println();
		} catch (NullPointerException e) {
			System.out.println("U heeft geen tegels meer");
		}
	}
	
	/**
	 * Weergeeft hoeveel tegels nog in de zak zitten.
	 */
	public void showPileSize(){
		System.out.println("Er zijn " + game.getPile().getTiles().size() + " tegels in de zak.");
	}
	
	/**
	 * Geeft aan het eind van een spel weer of de speler nog een spel wil spelen.
	 */
	public void showEndGame() {
		/*try {
			System.out.println("De winnaar is: " + game.winner().getName());
		} catch (PlayerNotFoundException e) {
			System.out.println("er is een fout in het systeem opgetreden");
		}*/
	}
}
