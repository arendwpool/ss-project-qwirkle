package util;

import models.Game;
import models.Pile;
import models.Player;
import models.Tile;
import network.Protocol;
import network.Server;

public class TileUtils {
	/**
	 * Geeft een willekeurige tegel terug. Hiervoor wordt eerst de zak geshuffeld, waarna de eerste
	 * tegel uit de Pile wordt teruggegeven. De teruggegeven tegel wordt dan ook uit de zak 
	 * verwijderd.
	 * @return tile
	 */
	public static Tile giveRandomTile(Pile pile) {
		pile.shuffle();
		Tile tile = pile.getTiles().get(0);
		return tile;
	}
	
	/**
	 * 
	 * Kijkt hoeveel tegels een gegeven speler in zijn hand heeft. Als dit minder dan 6 is worden er 
	 * een aantal willekeurige tegels gegeven zodat de hand weer 6 tegels heeft.
	 * @param pl
	 * @param server 
	 */
	public static void setHand(Player pl, Pile pile, Server server, Game game) {
		int noOfTilesToGive = Game.DEFAULT_HAND_SIZE - pl.getHand().size();
		for(int i = 0; i < noOfTilesToGive; i++){
			Tile tile = giveRandomTile(pile);
			pl.getHand().add(tile);
			server.broadcastToPlayer(Protocol.SERVER_CORE_SEND_TILE + Protocol.MESSAGESEPERATOR + symbolToInt(tile.getSymbol()) + Protocol.MESSAGESEPERATOR + colorToInt(tile.getColor()), pl.getName());
			pile.getTiles().remove(tile);
		}
	}
	
	/**
	 * Vergelijkt Symbolen van twee verschillende Tiles.
	 * @param tileA
	 * @param tileB
	 */
	public static boolean compareSymbol(Tile tileA, Tile tileB) {
		return tileA.getSymbol().equals(tileB.getSymbol());
		
	}
	
	/**
	 * Vergelijkt Kleuren van twee verschillende Tiles.
	 * @param tileA
	 * @param tileB
	 */
	public static boolean compareColor(Tile tileA, Tile tileB) {
		return tileA.getColor().equals(tileB.getColor());
	}

	
	/**
	 * Controleert of er geen tegels meer zijn in de Pile en er een speler is die geen tegels meer 
	 * heeft in zijn hand. 
	 * @return !playerHasNoTiles || false
	 */
	public static boolean noTilesLeft(Game game) {
		if (game.getPile().getTiles().size() == 0) {
			boolean playerHasNoTiles = false;
			for (Player player : game.getPlayers()) {
				if (player.getHand().size() == 0) {
					playerHasNoTiles = true;
				}
			}
			return !playerHasNoTiles;
		} else {
			return false;
		}
	}
	
	/**
	 * Berekent de integer die de server en client begrijpen van een gegeven kleur.
	 * @param color
	 * @return i + 1 || 0
	 */
	public static int colorToInt(String color) {
		for (int i = 0; i < Tile.kleuren.length; i++) {
			if (Tile.kleuren[i].equals(color)) {
				return i + 1;
			}
		}
		return 0;
	}
	/**
	 * Geeft de String terug van een gegeven integer.
	 * @param integer
	 * @return Tile.kleuren[integer-1]
	 */
	public static String intToColor(int integer) {
		return Tile.kleuren[integer-1];
	}
	
	/**
	 * Berekent de integer die de server en client begrijpen van een gegeven symbool.
	 * @param symbol
	 * @return i + 1 || 0
	 */
	public static int symbolToInt(String symbol) {
		for (int i = 0; i < Tile.symbolen.length; i++) {
			if (Tile.symbolen[i].equals(symbol)) {
				return i + 1;
			}
		}
		return 0;
	}
	
	/**
	 * Geeft de String terug van een gegeven integer.
	 * @param integer
	 * @return Tile.symbolen[integer-1]
	 */
	public static String intToSymbol(int integer) {
		return Tile.symbolen[integer-1];
	}
}