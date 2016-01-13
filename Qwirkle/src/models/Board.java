package models;
/**
 *De klasse die een speelbord representeerd maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Board {
    private int Coordinaten[] [];
    private	Tiles[] [] coordinaten;
    private static final int DIM = 0;
	
	/*
	 * Constructor van het Board
	 */
	public Board(){	
		reset(); //TODO reset cree�ren?
	}
	
	/*
	 * cree�rt een kopie van het speelveld
	 */
	public Board deepCopy(){
		coordinaten = new Tile[][];
		for (int x = 0; x < coordinaten.length; x++) {
			for (int y = 0; y < coordinaten.length; y++) {
				coordinaten[x][y] = new Tile(Color.Empty, Shape.EMPTY);
				}
			}
		}
	
	
	/*
	 * berekend een index.
	 */
	public int[] [] getSideFields(){
		int[][] sideFields = new int[2][2];
		
	}
	
	/* 
	 * returns de inhoud van field i.
	 */
	
	
	/*
	 * returns de inhoud verwezen door het paar (x,y) .
	 */
	public Tiles getField(int x, int y){
		return coordinaten[x] [y];
	}
	
	
	/*
	 * returns true als een verwezen field pair(x,y) leeg is
	 */
	public boolean isEmptyField(int y, int x){
		return (coordinaten[x][y].toString().equals(""));
	}
	
	/* 
	 * Moet de Move van de player verwerken.
	 */
	public void makeMove(int x, int y, Tiles tile){
		if(moveIsValid == True){
			coordinaten[x][y] = tile;
		}
		
	}
	
	public void reset() {
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				coordinaten[x][y] = null;
			}
		}
	}

}
