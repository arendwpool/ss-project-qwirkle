package models;
/**
 *De klasse die een speelbord representeerd maakt met een naam en tegels.
 * @author Bob Breemhaar en Arend Pool.
 *
 */
public class Board {
	private	Tiles[] [] coordinaten;
	private static final int DIM = 179;
	private boolean initialMove;

	/*
	 * Constructor van het Board
	 */
	public Board(){	
		reset(); //TODO reset cree�ren?
		initialMove = true;
	}
	
	/*
	 * cree�rt een kopie van het speelveld
	 */
	public Board deepCopy(){
		coordinaten = new Tiles[DIM][DIM];
		for (int x = 0; x < coordinaten.length; x++) {
			for (int y = 0; y < coordinaten.length; y++) {
				coordinaten[x][y] = null;
				}
			}
		return null; //TODO FIX dit LUL
		}
	
	/* 
	 * returns de inhoud van field i.
	 */
	
	
	/*
	 * returns de inhoud verwezen door het paar (x,y) .
	 */
	public Tiles getField(int x, int y){
		return coordinaten[x][y];
	}
	
	
	/*
	 * returns true als een verwezen field pair(x,y) leeg is
	 */
	public boolean isEmptyField(int x, int y){
		return (coordinaten[x][y] == null);
	}
	
	
	
	public boolean isValidMove(int x, int y, Tiles tileA, Tiles tileB) throws InvalidMoveException{
		if(isEmptyField(x, y) == false){
			return false;
			throw new InvalidMoveException();
		}else if(initialMove == true && x == 90 && y == 90){
			initialMove = false;
			return true;
		
		}else if((x != 90 && y == 90) || (x == 90 && y != 90) || (x != 90 && y != 90)){
			if(compareSymbool(tileA, tileB) == true && compareColor(tileA, tileB) == true){
				return false;
			}else if(compareSymbool(tileA, tileB) == false && compareColor(tileA, tileB) == false){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}//TODO Meer invalid shit vinden
	}
	
	public boolean compareSymbool(Tiles tileA, Tiles tileB){
		return(tileA.getSymbol() == tileB.getSymbol());
		
	}
	
	public boolean compareColor(Tiles tileA, Tiles tileB){
		return(tileA.getColor() == tileB.getColor());
	}
	
	/* 
	 * Moet de Move van de player verwerken.
	 */
	public void processMove(int x, int y, Tiles tile){
			coordinaten[x][y] = tile;
		
		
	}
	
	public void reset() {
		for (int x = 0; x < DIM*DIM; x++) {
			for (int y = 0; y < DIM*DIM; y++) {
				coordinaten[x][y] = null;
			}
		}
	}

}
