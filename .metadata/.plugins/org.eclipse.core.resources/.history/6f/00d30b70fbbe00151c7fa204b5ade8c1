package models;

import java.util.ArrayList;

import exceptions.InvalidMoveException;

/**
 *De klasse die een speelbord representeerd maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Board {
	/**
	 * creeërt een tweedimensionale array voor de coordinaten.
	 */
	private	Tile[][] coordinaten;
	
	/**
	 * Weergeeft de maximale breedte en hoogte van het bord.
	 */
	private static final int DIM = 179;
	/**
	 * Bepaalt of het de eerste move van het spel iets of niet.
	 */
	public static boolean initialMove;
	
	/**
	 * Geef een set met de tegels die de betreffende speler heeft neergelegd.
	 */
	private ArrayList<Tile> lastSet;
	
	/**
	 * geeft de minimale startview van x weer.
	 */
	public int viewOfMinX = 88;
	
	/**
	 * geeft de maximale startview van x weer.
	 */
	public int viewOfMaxX = 92;
	
	/**
	 * geeft de minimale startview van y weer.
	 */
	public int viewOfMinY = 88;
	
	/**
	 * geeft de maximale startview van y weer.
	 */
	public int viewOfMaxY = 92;
	
	/**
	 * geeft het midden punt van x weer.
	 */
	public int startPointX = 90;
	
	/**
	 * geeft het midden punt van y weer.
	 */
	public int startPointY = 90;

	/**
	 * Construeert een bord voor een spel. de coordinaten worden aan
	 */
	public Board() {	
		coordinaten = new Tile[DIM][DIM];
		reset(); //TODO reset creeëren?
		initialMove = true;
	}
	
	
	/**
	 * Creeërt een kopie van het speelveld.
	 * @return board
	 */
	public Board deepCopy() {
		Board board = new Board();
		coordinaten = new Tile[DIM][DIM];
		for (int x = 0; x < coordinaten.length; x++) {
			for (int y = 0; y < coordinaten.length; y++) {
				board.setTile(x, y, this.getField(x, y));
			}
		}
		return board;
	}
	
	/**
	 * De klasse controlleert de laagste en hoogste x en y as en maakt daarbij
	 * de nieuwe coordinaten van de grote van het bord.
	 */
	
	public void boardSize() {
		for (int x = 0; x <= DIM; x++) {
			for (int y = 0; y <= DIM; y++) {
				if (isEmptyField(x, y) == false) {
					viewOfMaxX = x + 1;
					break;
				}
			}
		}
		for (int x = DIM; x >= 0; x--) {
			for (int y = 0; y <= DIM; y++) {
				if (isEmptyField(x, y) == false) {
					viewOfMinX = x - 1;
					break;
				}
			}
		}
		for (int y = 0; y <= DIM; y++) {
			for (int x = 0; x <= DIM; x++) {
				if (isEmptyField(x, y) == false) {
					viewOfMaxY = y + 1;
					break;
				}
			}
		}
		for (int y = DIM; y >= 0; y--) {
			for (int x = 0; x <= DIM; x++) {
				if (isEmptyField(x, y) == false) {
					viewOfMinY = y - 1;
					break;
				}
			}
		} 
	}
	
	
	/**
	 * returns de inhoud verwezen door het paar (x,y) .
	 */
	public Tile getField(int x, int y) {
		return coordinaten[x][y];
	}
	
	
	/**
	 * returns true als een verwezen field pair(x,y) leeg is.
	 * @return getField(x, y).getColor() == "empty" && getField(x,y).getSymbol() == "empty";
	 */
	public boolean isEmptyField(int x, int y) {
		return getField(x, y).getColor() == "empty" && getField(x, y).getSymbol() == "empty";
	}
	
	/**
	 * Deze Arraylist is gemaakt om alle aanliggende tegels te onthouden.
	 * @return xAxis;
	 */
	//TODO eventueel code verbeteren door de hoeveelheid duplicaatcode te verminderen
	public ArrayList<Tile> tilesOnXAxis(int x, int y) {
		boolean leftIsEmpty = false;
		boolean rightIsEmpty = false;
		int xOrig = x;
		ArrayList<Tile> xAxis = new ArrayList<Tile>();
		xAxis.add(getField(x, y));
		while (leftIsEmpty == false) {
			x--;
			if (isEmptyField(x, y) == false) {
				xAxis.add(getField(x, y));
			} else {
				break; 
			}
		}
		x = xOrig;
		while (rightIsEmpty == false) {
			x++;
			if (isEmptyField(x, y) == false) {
				xAxis.add(getField(x, y));
			} else {
				break; 
			}
		}
		return xAxis;
	}
	
	/**
	 * Deze Arraylist is gemaakt om alle aanliggende tegels te onthouden.
	 * @return yAxis;
	 */
	public ArrayList<Tile> tilesOnYAxis(int x, int y){
		boolean upperIsEmpty = false;
		boolean lowerIsEmpty = false;
		int yOrig = y;
		ArrayList<Tile> yAxis = new ArrayList<Tile>();
		yAxis.add(getField(x, y));
		while (lowerIsEmpty == false) {
			y--;
			if (isEmptyField(x, y) == false) {
				yAxis.add(getField(x, y));
			} else {
				break; 
			}
		}
		y = yOrig;
		while (upperIsEmpty == false) {
			y++;
			if (isEmptyField(x, y) == false) {
				yAxis.add(getField(x, y));
			} else {
				break; 
			}
		}
		return yAxis;
	}
	
	/**
	 * Deze klasse kijkt of alle aangelegde steenjes dezelfde x of y coordinaten hebben.
	 * @param tileToSet
	 * @param axis
	 * @return contains;
	 */
	public boolean sharedLine(Tile tileToSet, ArrayList<Tile> axis) {
		ArrayList<Tile> copy = new ArrayList<Tile>(lastSet);
		copy.add(tileToSet);
		boolean contains = true;
		for (Tile tile : copy) {
			if (!axis.contains(tile)) {
				contains = false;
			}
		}
		return contains;
	}
	
	/**
	 * Controleert of de aangelegde stenen wel volgens de spelregels mogen.
	 */
	//TODO analyzeren: nadat locatie is toegevoegd aan tile, is dit te verbeteren?
	public boolean isValidMove(int x, int y, Tile tile) {
		Board board = deepCopy();
		board.setTile(x, y, tile);
		ArrayList<Tile> xAxis = tilesOnXAxis(x, y);
		ArrayList<Tile> yAxis = tilesOnYAxis(x, y);
		if (isEmptyField(x, y) == false) {
			return false;
		} else if (xAxis.size() > 6 || yAxis.size() > 6) {
			return false;
		} else if (initialMove == true) { //In Controller x en y 90,90 maken, bij elke eerste zet.
			return true;
		} else if (initialMove == false && xAxis.size() == 1 && yAxis.size() == 1) {
			return false;
		} else {
			for (Tile tileToCompare : xAxis) {
				Tile tileOrig = tileToCompare;
				xAxis.remove(tile);
				for (Tile tileToCompareWith : xAxis) {
					if (compareColor(tileToCompareWith, tileOrig) == true &&
									compareSymbol(tileToCompareWith, tileOrig) == true) {
						return false;
					} else if (compareColor(tileToCompareWith, tileOrig) == true 
									|| compareSymbol(tileToCompareWith, tileOrig) == true) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false; //TODO oplossing voor nu
	}
	
	/**
	 * bind een coordinaat aan een tile.
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void setTile(int x, int y, Tile tile) {
		this.coordinaten[x][y] = tile;
		tile.setLocation(x, y);
	}
	
	/**
	 * Vergelijkt Symbolen van twee verschillende Tiles.
	 * @param tileA
	 * @param tileB
	 */
	public boolean compareSymbol(Tile tileA, Tile tileB) {
		return tileA.getSymbol() == tileB.getSymbol();
		
	}
	
	/**
	 * Vergelijkt Kleuren van twee verschillende Tiles.
	 * @param tileA
	 * @param tileB
	 */
	public boolean compareColor(Tile tileA, Tile tileB) {
		return tileA.getColor() == tileB.getColor();
	}
	
	/**
	 * Moet de Move van de player verwerken.
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void processMove(int x, int y, Tile tile) throws InvalidMoveException {
		if (isValidMove(x, y, tile) && (sharedLine(tile, tilesOnYAxis(x, y)) 
						|| sharedLine(tile, tilesOnXAxis(x, y)))) {
			setTile(x, y, tile);
			initialMove = false;
			rememberMove(tile);
		} else {
			rememberMove(null);
			throw new InvalidMoveException();
		}
		//TODO in zijn geheel opnieuw implementeren
	}
	
	/**
	 * Onthoud de laatste move die een speler gedaan heeft.
	 * @param tile
	 */
	public void rememberMove(Tile tile) {
		lastSet.add(tile);
	}
	
	/**
	 * leegt de set met de laaste moves van de speler.
	 */
	public void clearLastMoves() {
		lastSet.clear();
	}
	/**
	 * Weergeeft de lijst met de laastse moves.
	 * @return lastSet
	 */
	public ArrayList<Tile> getLastMoves() {
		return lastSet;
	}
	/**
	 * Maakt het hele board leeg.
	 */
	public void reset() {
		for (int x = 0; x < (DIM); x++) {
			for (int y = 0; y < (DIM); y++) {
				setTile(x, y, new Tile("empty", "empty"));
			}
		}
	}
	
	//TODO Hoeveelheid stenen op het bord kunnen controleren checken

}
