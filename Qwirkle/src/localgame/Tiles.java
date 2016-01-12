package localgame;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * De klasse die alle tegels maakt die een spel kan hebben.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Tiles {
	/**
	 * De mogelijke symbolen die een tegel kan hebben.
	 */
	public static final String[] symbolen = {"circel", "kruis", "ruit", "vierkant", "ster", "plus"};
	/**
	 * De mogelijke kleuren die een tegel kan hebben.
	 */
	public static final String[] kleuren = {"rood", "oranje", "geel", "groen", "blauw", "paars"};
	/**
	 * Aantal tegels in een spel.
	 */
	public static final int NUMBER_TILES = 108;
	/**
	 * Een map die een tegel voorstelt.
	 */
	public Map<String, String> tileMap = new HashMap<String, String>();
	/**
	 * Een array van losse maps die tegels voorstellen.
	 */
	public Map<String, String>[] tiles;
	
	public Tiles(){
		//TODO implement
	}
	
	/**
	 * Genereer een nieuwe array met tegels.
	 */
	public void generateTiles(){
		tiles = new Map[NUMBER_TILES];
		int tilesIndex = 0;
		for (int kleur = 0; kleur < kleuren.length*3; kleur++){
			for (int symbool = 0; symbool < kleuren.length*3; symbool++){
				tileMap.put(kleuren[kleur%6], symbolen[symbool%6]);
				while(tilesIndex<NUMBER_TILES){
					tiles[tilesIndex] = tileMap;
					tilesIndex++;
				}
				tileMap.clear();
			}
		}
	}
	/**
	 * Geeft de map met tegels terug.
	 * @return this.tiles.
	 */
	/*@pure*/
	public Map<String, String>[] getTiles(){
		return tiles;
	}
	public static void main(String[] args){
		Tiles a = new Tiles();
		a.generateTiles();
		for(int i = 0; i < 108; i++){
			System.out.println(i+1 + ":	" + a.getTiles()[i]);
		}
	}
}
	
