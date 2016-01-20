package models;

import java.util.ArrayList;

import util.MoveUtils;

/**
 *De klasse die een speelbord representeerd maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Board {
	/**
	 * cree�rt een tweedimensionale array voor de coordinaten.
	 */
	private	Tile[][] coordinaten;
	
	/**
	 * Weergeeft de maximale breedte en hoogte van het bord.
	 */
	public static final int DIM = 179;
	
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
		reset();
	}
	
	
	/**
	 * Cree�rt een kopie van het speelveld.
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
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				if (isEmptyField(x, y) == false) {
					viewOfMaxX = x + 1;
					break;
				}
			}
		}
		for (int x = DIM - 1; x >= 0; x--) {
			for (int y = 0; y < DIM; y++) {
				if (isEmptyField(x, y) == false) {
					viewOfMinX = x - 1;
					break;
				}
			}
		}
		for (int y = 0; y < DIM; y++) {
			for (int x = 0; x < DIM; x++) {
				if (isEmptyField(x, y) == false) {
					viewOfMaxY = y + 1;
					break;
				}
			}
		}
		for (int y = DIM - 1; y >= 0; y--) {
			for (int x = 0; x < DIM; x++) {
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
		ArrayList<Tile> copy = new ArrayList<Tile>(MoveUtils.getLastMoves());
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
	 * Maakt het hele board leeg.
	 */
	public void reset() {
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				setTile(x, y, new Tile("empty", "empty"));
			}
		}
	}
	
	//TODO Hoeveelheid stenen op het bord kunnen controleren checken

}
