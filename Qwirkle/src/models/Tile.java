package models;

import java.awt.Point;
/**
 * De klasse die een bepaalde tegel representeerd.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Tile {
	/**
	 * De mogelijke symbolen die een tegel kan hebben.
	 */
	public static final String[] symbolen = {"cirkel", "kruis", "ruit", "vierkant", "ster", "plus"};
	
	/**
	 * De mogelijke kleuren die een tegel kan hebben.
	 */
	public static final String[] kleuren = {"rood", "oranje", "geel", "groen", "blauw", "paars"};
	
	/**
	 * De kleur van deze tegel.
	 */
	private String color;
	
	/**
	 * Het symbool van deze tegel.
	 */
	private String symbol;
	
	/**
	 * De locatie op het bord van de tegel.
	 */
	private Point point;
	
	/**
	 * Constructor die een tegel samenstelt met een gegeven kleur en symbool.
	 * @param color
	 * @param symbol
	 */
	public Tile(String color, String symbol){
		this.color = color;
		this.symbol = symbol;
	}
	
	/**
	 * Weergeeft de kleur van de tegel.
	 * @return this.color
	 */
	/*@pure*/
	public String getColor(){
		return color;
	}
	
	/**
	 * Weergeeft de symbool van de tegel.
	 * @return this.symbol
	 */
	/*@pure*/
	public String getSymbol(){
		return symbol;
	}
	
	/**
	 * geeft de mogelijkheid om de Tile aan een punt te koppelen.
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y){
		point = new Point(x, y);
	}
	/**
	 * geeft de mogelijkheid om de locatie aan te vragen van deze Tile.
	 * @return point
	 */
	public Point getLocation(){
		return point;
	}
	
}
	
