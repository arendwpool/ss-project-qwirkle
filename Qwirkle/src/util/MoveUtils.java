package util;

import java.awt.Point;
import java.util.ArrayList;

import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import models.Board;
import models.Game;
import models.Pile;
import models.Player;
import models.Tile;

public class MoveUtils {

	/**
	 * Geeft aan of een speler heeft geruilt in de plaats van een zet maken.
	 */
	private static boolean hasTraded = false;
	
	
	/**
	 * Bepaalt of het de eerste move van het spel iets of niet.
	 */
	private static boolean initialMove = true;
	
	/**
	 * Geef een set met de tegels die de betreffende speler heeft neergelegd.
	 */
	private static ArrayList<Tile> lastSet = new ArrayList<Tile>();
	
	/**
	 * Geeft aan of een speler een blokje heeft neergelegd.
	 */
	private static boolean madeMove = lastSet.size() > 0;

	/**
	 * Verwijderd alle laatst gezette tegels van een speler.
	 * @throws InvalidMoveException 
	 */
	public static void processMove(Player player, Game game) throws InvalidMoveException {
		if(getLastMoves().size() != 0 || hasTraded == true){
			generateScore(player, game.getBoard());
			clearLastMoves();
			hasTraded = false;
			initialMove = false;
			game.nextPlayer();
		}else{
			throw new InvalidMoveException();
		}
	}

	
	/**
	 * genereer de score van een bepaalde move en stuurt deze door aan de methode addScore. Hiervoor
	 * wordt eerst voor elke tegel die een speler heeft neergelegd alle tegels in een rij op de x-as
	 * en alle tegels op een rij op de y-as in lijsten opgeslagen. Vervolgens worden deze lijsten
	 * vergeleken met de lijst van tegels die als laatst zijn neergelegd. De tegels die als laatst
	 * zijn neergelegd in een rij blijven over. Als dit meer dan 1 element is, moeten de punten van
	 * deze rij maar ��n keer opgeteld worden, en niet het aantal keer dat een gelegde tegel in de
	 * rij voorkomt. Als een rij een lengte van zes heeft wordt er een bonus toegevoegd. Nadat de 
	 * score is berekend wordt deze aan de speler toegevoegd.
	 * @param player
	 */
	public static void generateScore(Player player, Board board) {
		Point point = null;
		boolean retainMultipleX = false;
		boolean retainMultipleY = false;
		ArrayList<Tile> row = null;
		ArrayList<Tile> column = null;
		int score = 0;
		for (Tile tile : getLastMoves()) {
			point = tile.getLocation();
			int x = (int) point.getX();
			int y = (int) point.getY();
			row = board.tilesOnXAxis(x, y);
			column = board.tilesOnYAxis(x, y);
			ArrayList<Tile> commonX = deleteCommon(row, getLastMoves());
			ArrayList<Tile> commonY = deleteCommon(column, getLastMoves());
			retainMultipleX = commonX.size() > 1;
			retainMultipleY = commonY.size() > 1;
			if (retainMultipleX == false) {
				score += column.size();
			} else if (retainMultipleY == false) {
				score += row.size();
			}
		}
		if (retainMultipleX == true) {
			score += row.size();
		} else if (retainMultipleY == true) {
			score += column.size();
		}
		player.addScore(score);
	}
	
	public static ArrayList<Tile> deleteCommon(ArrayList<Tile> listToKeep, ArrayList<Tile> listToRemove) {
		ArrayList<Tile> copy = new ArrayList<Tile>(listToKeep);
		copy.removeAll(listToRemove);
		listToKeep.removeAll(copy);
		return listToKeep;
	}
	/**
	 * Controleert of de aangelegde stenen wel volgens de spelregels mogen.
	 */
	//TODO analyzeren: nadat locatie is toegevoegd aan tile, is dit te verbeteren?
	public static boolean isValidMove(int x, int y, Tile tile, Board board) {
		Board boardCopy = board.deepCopy();
		boardCopy.setTile(x, y, tile);
		ArrayList<Tile> xAxis = board.tilesOnXAxis(x, y);
		ArrayList<Tile> yAxis = board.tilesOnYAxis(x, y);
		if (board.isEmptyField(x, y) == false) {
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
					if (TileUtils.compareColor(tileToCompareWith, tileOrig) == true &&
									TileUtils.compareSymbol(tileToCompareWith, tileOrig) == true) {
						return false;
					} else if (TileUtils.compareColor(tileToCompareWith, tileOrig) == true 
									|| TileUtils.compareSymbol(tileToCompareWith, tileOrig) == true) {
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
	 * Onthoud de laatste move die een speler gedaan heeft.
	 * @param tile
	 */
	public static void rememberMove(Tile tile) {
		lastSet.add(tile);
	}

	
	/**
	 * leegt de set met de laaste moves van de speler.
	 */
	public static void clearLastMoves() {
		lastSet.clear();
	}
	/**
	 * Weergeeft de lijst met de laastse moves.
	 * @return lastSet
	 */
	public static ArrayList<Tile> getLastMoves() {
		return lastSet;
	}
	
	/**
	 * Krijgt een lijst met de tegels die geruilt moeten worden. Hiervoor wordt eerst gekeken of de 
	 * tegels die geruild moeten worden wel in de hand van de betreffende speler zijn. Daarna wordt 
	 * er gekeken of de Pile wel genoeg stenen heeft om te ruilen. Als er aan deze twee voorwaarden 
	 * zijn voldaan worden de tegel om te ruilen terug in de Pile gedaan. Vervolgens worden er de 
	 * tegels uit de hand verwijderd van de player. Daarna wordt de methode setHand() aangeroepen 
	 * om de hand weer aan te vullen. Als er niet genoeg tegels zijn wordt er een exceptie gegooit.
	 * Als de speler een zet heeft gedaan wordt er ook een exceptie gegooit.
	 * @param tilesToTrade
	 * @param player
	 * @throws NoTilesLeftInPileException
	 * @throws InvalidMoveException 
	 */
	public static void replaceTiles(ArrayList<Tile> tilesToTrade, Player player, Pile pile) 
			throws NoTilesLeftInPileException, InvalidMoveException {
		if (madeMove == false){
			boolean containsAll = true;
			hasTraded = true;
			for (Tile tile : tilesToTrade) {
				if (!player.getHand().contains(tile)) {
					containsAll = false;
				}
			}
			if (tilesToTrade.size() <= pile.getTiles().size() && containsAll == true) {
				pile.getTiles().addAll(tilesToTrade);
				player.getHand().removeAll(tilesToTrade);
				TileUtils.setHand(player, pile);
			} else {
				throw new NoTilesLeftInPileException();
			}
		} else {
			throw new InvalidMoveException();
		}
	}

	/**
	 * Moet de Move van de player verwerken.
	 * @param x
	 * @param y
	 * @param tile
	 */
	public static void makeMove(int x, int y, Tile tile, Board board) throws InvalidMoveException {
		if (hasTraded == false) {
			if (isValidMove(x, y, tile, board) && (board.sharedLine(tile, board.tilesOnYAxis(x, y)) 
					|| board.sharedLine(tile, board.tilesOnXAxis(x, y)))) {
				board.setTile(x, y, tile);
				rememberMove(tile);
			}
		} else {
			throw new InvalidMoveException();
		}
	}
}
