package localgame;
/**
 * De klasse die een bepaalde tegel representeerd.
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
	 * De kleur van deze tegel.
	 */
	private String color;
	
	/**
	 * Het symbool van deze tegel.
	 */
	private String symbol;
	
	/**
	 * Constructor die een tegel samenstelt.
	 */
	public Tiles(){
		
	}
	
	/**
	 * Geeft de tegel een kleur.
	 * @param color
	 */
	public void setColor(String color){
		this.color = color;
	}
	
	/**
	 * Geeft de tegel een symbool.
	 * @param symbol
	 */
	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Weergeeft de kleur van de tegel.
	 * @return this.color
	 */
	/*@Pure*/
	public String getColor(){
		return color;
	}
	
	/**
	 * Weergeeft de symbool van de tegel.
	 * @return this.symbol
	 */
	/*@Pure*/
	public String getSymbol(){
		return symbol;
	}
	
}
	
