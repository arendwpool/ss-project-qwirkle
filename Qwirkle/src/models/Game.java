package models;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import exceptions.FalseAmountOfTilesException;
import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;

/**
 * Klasse die het de regels van het spel implementeerd.
 * @author Bob Breemhaar en Arend Pool
 *
 */
public class Game {
	/*
	 * ArrayList met alle tegels in een spel.
	 */
	private ArrayList<Tile> tiles;
	
	/**
	 * Geeft aan welke speler de volgende move moet maken.
	 */
	private AbstractPlayer currentPlayer;
	
	/*
	 * Geeft de hoeveelheid meespelende spelers mee. 
	 */
	private int noOfPlayers;
	
	/**
	 * Een hashmap van spelers die meedoen gemapt aan hun ID
	 */
	private Map<AbstractPlayer, Integer> players;
	/**
	 * Het nieuwe bord dat bij dit spel hoort
	 */
	private Board board;
	
	/**
	 * Contrueert  een nieuw spel.
	 */
	public Game(Board board){
		this.board = board;
		players = new HashMap<AbstractPlayer, Integer>();
	}
	/**
	 * Genereer alle tegels die in een spel zitten. Dit zijn er 108: 3 van elke tegel.
	 */
	//@ensures this.tiles = new ArrayList<Tiles>();
	public void generateTiles(){
		tiles = new ArrayList<Tile>();
		for (int color = 0; color < Tile.NUMBER_COLORS * Tile.NUMBER_DUPLICATES; color++){
			for(int symbol = 0; symbol < Tile.NUMBER_SYMBOLS; symbol++){
				tiles.add(new Tile(Tile.kleuren[color%Tile.NUMBER_COLORS], Tile.symbolen[symbol]));
			}
		}
		
	}
	
	/**
	 * controleert of het spel een winnaar heeft
	 * @return winner() != null
	 */
	public boolean hasWinner(){
		return winner() != null;
	}
	
	/**
	 * Geeft aan of het spel over is.
	 * @return hasWinner();
	 */
	/*@pure*/
	public boolean gameOver(){
		return noTilesLeft();
	}
	
	/**
	 * Geeft aan welke speler winnaar is, geeft null als er nog geen winnaar is.
	 * @return Player withHighscore || null
	 */
	public AbstractPlayer winner(){
		if(noTilesLeft() == true){
			int score = 0;
			AbstractPlayer withHighscore = null;
			for(AbstractPlayer player : players.keySet()){
				if(player.getScore() > score){
					score = player.getScore();
					withHighscore = player;
				}
			}
			return withHighscore;
		}else{
			return null;
		}
	}
	
	/**
	 * Verwisselt tegels met de tilesTotrade van een gegeven speler.
	 * @param player
	 * @param tilesToTrade
	 * @throws NoTilesLeftInPileException
<<<<<<< HEAD
	 */
	public void tradeTiles(AbstractPlayer player, ArrayList<Tile> tilesToTrade) throws NoTilesLeftInPileException{
=======
	 *///TODO migreren naar Pile.java?
	public void tradeTiles(Player player, ArrayList<Tile> tilesToTrade) throws NoTilesLeftInPileException{
>>>>>>> d456d1373d3d90bda30f5292bd9dacd2843d73dd
		if (noTilesLeft() == false){
			ArrayList<Tile> tilesToGive = new ArrayList<Tile>();
			for(Tile tile : player.getTiles()){
				if (tilesToTrade.contains(tile)){
					tilesToGive.add(tile);
				}
			}
			try{
				player.replaceTiles(tilesToTrade, tilesToGive);
			}catch (FalseAmountOfTilesException e){
				//TODO implementeer de actie als er te weinig tegels zijn.
			}
		}else{
			throw new NoTilesLeftInPileException();
		}
	}
	
	/**
	 * Start het spel.
	 */
	public void start(){
		//TODO te implementeren als de tijd rijp is
	}
	
	/**
	 * Update de situatie van het spel na elke actie van een speler.
	 */
	public void update(){
		//TODO te implementeren als de tijd rijp is
		
	}
	
	/**
	 * probeert een move te maken, vangt een InvalidMoveException als de move invalid is.
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void makeMove(int x, int y, Tile tile){
		try{
			board.processMove(x, y, tile);//TODO Player toevoegen aan de parameter om aan te duiden wie de speler is?
		}catch (InvalidMoveException e){
			//TODO implement actie na de catch
		}
	}
	
	/**
	 * Geeft een volle pile van alle tegels
	 * @return this.pile
	 */
	public ArrayList<Tile> getPile(){
		return tiles;
	}
	
	/**
	 * Controleert of er geen tegels meer zijn in een spel. 
	 * @return !playerHasNoTiles || false
	 */
	public boolean noTilesLeft(){
		// tiles is nooit leeg, dit moet vervangen worden met Pile.java.
		if(tiles.size() == 0){
			boolean playerHasNoTiles = false;
			for(AbstractPlayer player : players.keySet()){
				if (player.getTiles().size() == 0){
					playerHasNoTiles = true;
				}
			}
			return !playerHasNoTiles;
		}else{
			return false;
		}
	}
	
	/**
	 * Voegt een speler toe aan een spel.
	 * @param player
	 * @throws FullGameException 
	 */
	public void addPlayer(AbstractPlayer player) throws FullGameException{
		if(players.keySet().size() < 4){
			players.put(player, players.keySet().size());
		}else{
			throw new FullGameException();
		}
	}
	
	/**
	 * Geeft het bord instantie van dit spel
	 * @return
	 */
	public Board getBoard(){
		return board;
	}
	
	/**
	 * genereer de score van een bepaalde move en stuurt deze door aan de methode addScore
	 * @param player
	 */
<<<<<<< HEAD
	public void generateScore(AbstractPlayer player){
		//TODO implement
		int points = 0;
		player.addScore(points);
	}
	
	public void finishMove(){
		board.clearLastMoves();
=======
	public void generateScore(){
		Point point = null;
		boolean retainMultipleX = false;
		boolean retainMultipleY = false;
		ArrayList<Tile> row = null;
		ArrayList<Tile> column = null;
		int score = 0;
		for(Tile tile : board.getLastMoves()){
			point = tile.getLocation();
			int x = (int) point.getX();
			int y = (int) point.getY();
			row = board.tilesOnXAxis(x, y);
			column = board.tilesOnYAxis(x, y);
			ArrayList<Tile> commonX = new ArrayList<Tile>(board.getLastMoves());
			ArrayList<Tile> commonY = new ArrayList<Tile>(board.getLastMoves());
			commonX.retainAll(row);
			commonY.retainAll(column);
			retainMultipleX = commonX.size() > 1;
			retainMultipleY = commonY.size() > 1;
			if(retainMultipleX == true){
				score += column.size();
			}else if(retainMultipleY == true){
				score += row.size();
			}
		}
		if(retainMultipleX == true){
			score += row.size();
		}else if(retainMultipleY == true){
			score += column.size();
		}else{
			score += row.size();
			score += column.size();
		}
		currentPlayer.addScore(score); // TODO currentPlayer moet eventueel vervangen worden als de implementatie verandert.
>>>>>>> d456d1373d3d90bda30f5292bd9dacd2843d73dd
	}
}