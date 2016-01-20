package view;

import java.util.ArrayList;
import java.util.Observable;

import controllers.GameController;
import exceptions.InvalidMoveException;
import models.Game;
import models.Tile;

public class BoardTUI extends TUI{
	public BoardTUI(GameController gc, Game game) {
		super(gc);
		this.game = game;
		// TODO Auto-generated constructor stub
	}

	private Game game;
	private String move;
	private String swap;
	private static final String[] BOARD_MENU = {"Als u aan de beurt ben kan u tegels ruilen of neerleggen. Dit gaat per tegel, aan het eind van uw beurt drukt u op: klaar.", "Een tegel neerleggen: ", "Een tegel ruilen", "Klaar"};
	private static final String[] SET_TILE_MENU = {"Typ uw keuze in de vorm: [tegelnummer] [x] [y]", "Uw keuze:"};
	private static final String[] SWAP_TILE_MENU = {"Typ uw keuze in de vorm: tegelnummer tegelnummer etc.", "Uw keuze"};
	
	@Override
	public void update(Observable arg0, Object arg1) {
		game.getBoard().boardSize();
		System.out.print("hallo");
		start();
	}
	
	public void start(){
		renderBoard();
		showTiles();
		if (game.getCurrentPlayer().equals(gc.getLocalPlayer())) {
			renderMenu(BOARD_MENU);
			int choice = determineOption();
			if (choice == 1) {
				renderMenu(SET_TILE_MENU);
				String move = determineString();
			} else if (choice == 2) {
				renderMenu(SWAP_TILE_MENU);
			}
		}
	}
	
	public void renderBoard(){
		System.out.print("	");
		for(int i = game.getBoard().viewOfMinX - 90; i <= game.getBoard().viewOfMaxX - 90; i++){
			System.out.print("	"+i);
		}
		System.out.println("\n\n\n");
		for (int y = game.getBoard().viewOfMaxY; y >= game.getBoard().viewOfMinY; y--){
			for(int x = game.getBoard().viewOfMinX; x <= game.getBoard().viewOfMaxX; x++){
				if (game.getBoard().getField(x, y).getColor() == "empty" && game.getBoard().getField(x, y).getSymbol() == "empty"){
					if (x == game.getBoard().viewOfMinX){
						System.out.print(y-90 + "	");
					}
					System.out.print("	-");
					if(x == (game.getBoard().viewOfMaxX)){
						System.out.println("\n");
					}
				}else{
					System.out.print("	"+colorRepresentive(game.getBoard().getField(x, y).getColor())+ "-" + symbolRepresentive(game.getBoard().getField(x, y).getSymbol()));
					if(y == (game.getBoard().viewOfMaxY)){
						System.out.println();
					}
				}
			}
		}
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
	
	public void showTiles(){
		ArrayList<Tile> tiles = gc.getLocalPlayer().getHand();
		System.out.println("Uw tegels: ");
		for (int i = 1; i <= tiles.size(); i++) {
			System.out.print(i + ": " + tiles.get(i-1).getColor()+tiles.get(i-1).getSymbol()+"  ");
		}
		System.out.println();
	}
	
	public void determineMove(String string) {
		ArrayList<Tile> tiles = gc.getLocalPlayer().getHand();
		String[] moveArray = string.split(" ");
		int tileNumber = Integer.parseInt(moveArray[0]) - 1;
		Tile tile = tiles.get(tileNumber);		
		int x = Integer.parseInt(moveArray[1])+90;
		int y = Integer.parseInt(moveArray[2])+90;
		try {
			game.makeMove(x, y, tile);
		} catch (InvalidMoveException e) {
			System.out.println("Deze move mag niet");
		}
	}
}
