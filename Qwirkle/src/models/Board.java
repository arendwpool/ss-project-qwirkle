package models;

import java.util.ArrayList;

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
	public int viewOfMinX = 89;
	
	/**
	 * geeft de maximale startview van x weer.
	 */
	public int viewOfMaxX = 91;
	
	/**
	 * geeft de minimale startview van y weer.
	 */
	public int viewOfMinY = 89;
	
	/**
	 * geeft de maximale startview van y weer.
	 */
	public int viewOfMaxY = 91;
	
	/**
	 * geeft het midden punt van x weer.
	 */
	public int startPointX = 90;
	
	/**
	 * geeft het midden punt van y weer.
	 */
	public int startPointY = 90;

	/**
	 * Construeert een bord voor een spel en reset dit bord.
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
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				board.setTile(x, y, this.getField(x, y));
			}
		}
		return board;
	}
	
	/**
	 * De methode controlleert de laagste en hoogste x en y as en maakt daarbij
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
	 * Geeft de tegel terug die op een gegeven x en y ligt.
	 */
	public Tile getField(int x, int y) {
		return coordinaten[x][y];
	}
	
	
	/**
	 * returns true als een verwezen field pair(x,y) leeg is.
	 * @return getField(x, y).getColor() == "empty" && getField(x,y).getSymbol() == "empty";
	 */
	public boolean isEmptyField(int x, int y) {
		return getField(x, y).getColor().equals("empty") &&
				getField(x, y).getSymbol().equals("empty");
	}
	
	/**
	 * Geeft alle tegels op een rij weer op een gegeven x en y coordinaat. Hiervoor kijkt de
	 * methode eerst of er links een tegel ligt. Als dit het geval wordt deze in een lijst 
	 * gezet. Als er links geen tegel meer ligt weet de methode dat het einde van de rij links
	 * is bereikt. Het zal hierna het zelfde proces doen aan de rechterkant.
	 * @return xAxis;
	 */
	public ArrayList<Tile> tilesOnXAxis(int x, int y) {
		int xParam = x;
		int yParam = y;
		boolean leftIsEmpty = false;
		boolean rightIsEmpty = false;
		int xOrig = x;
		ArrayList<Tile> xAxis = new ArrayList<Tile>();
		if (!isEmptyField(x, y)) {
			xAxis.add(getField(x, y));
		}
		while (leftIsEmpty == false) {
			if (xParam != 0) {
				xParam--;
				if (isEmptyField(xParam, yParam) == false) {
					xAxis.add(getField(xParam, yParam));
				} else {
					break; 
				}
			} else {
				break;
			}
		}
		xParam = xOrig;
		while (rightIsEmpty == false) {
			if (xParam != Board.DIM) {
				xParam++;
				if (isEmptyField(xParam, yParam) == false) {
					xAxis.add(getField(xParam, yParam));
				} else {
					break; 
				}
			} else {
				break;
			}
		}
		return xAxis;
	}
	
	/**
	 * Doet hetzelfde als tilesOnXAxis, alleen voor een kolom.
	 * @return yAxis;
	 */
	public ArrayList<Tile> tilesOnYAxis(int x, int y) {
		int xParam = x;
		int yParam = y;
		boolean upperIsEmpty = false;
		boolean lowerIsEmpty = false;
		int yOrig = y;
		ArrayList<Tile> yAxis = new ArrayList<Tile>();
		if (!isEmptyField(xParam, yParam)) {
			yAxis.add(getField(xParam, yParam));
		}
		while (lowerIsEmpty == false) {
			if (yParam != 0) {
				yParam--;
				if (isEmptyField(xParam, yParam) == false) {
					yAxis.add(getField(x, y));
				} else {
					break; 
				}
			} else {
				break;
			}
		}
		yParam = yOrig;
		while (upperIsEmpty == false) {
			if (yParam != Board.DIM) {
				yParam++;
				if (isEmptyField(xParam, yParam) == false) {
					yAxis.add(getField(xParam, yParam));
				} else {
					break; 
				}
			} else {
				break;
			}
		}
		return yAxis;
	}
	

	
	/**
	 * bindt een coordinaat aan een tile.
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
	
	/**
	 * Controlleerd van twee gegeven lijsten of de tegels in deze lijsten dezelfde
	 * x of y coordinaat hebben.
	 * @param list1
	 * @param list2
	 * @return sameX || sameY
	 */
	public boolean sameAxis(ArrayList<Tile> list1, ArrayList<Tile> list2) {
		boolean sameX = true;
		boolean sameY = true;
		for (Tile tile : list1) {
			for (Tile tileToCompare : list2) {
				if (tile.getLocation().getX() != tileToCompare.getLocation().getX()) {
					sameX = false;
				} else if (tile.getLocation().getY() != tileToCompare.getLocation().getY()) {
					sameY = false;
				}
			}
		}
		return sameX || sameY;
	}


}
