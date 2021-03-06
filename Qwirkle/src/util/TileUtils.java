package util;

import models.Game;
import models.Pile;
import models.Player;
import models.Tile;

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
	 * @param player
	 */
	public static void setHand(Player player, Pile pile) {
		int noOfTilesToGive = Game.DEFAULT_HAND_SIZE - player.getHand().size();
		for(int i = 0; i < noOfTilesToGive; i++){
			Tile tile = giveRandomTile(pile);
			player.getHand().add(tile);
			pile.removeTile(tile);
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
}
