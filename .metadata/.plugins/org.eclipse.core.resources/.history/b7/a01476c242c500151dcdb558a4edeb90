package models;
import java.util.ArrayList;
import java.util.Observable;

import exceptions.FullGameException;
import exceptions.InvalidMoveException;
import exceptions.NoTilesLeftInPileException;
import exceptions.PlayerNotFoundException;
import util.MoveUtils;
import util.TileUtils;

/**
 * Klasse die het de regels van het spel implementeert.
 * @author Bob Breemhaar en Arend Pool
 *
 */
public class Game extends Observable{
	/**
	 * Geeft de grootte van een hand aan in normale omstandigheden.
	 */
	public static final int DEFAULT_HAND_SIZE = 6;
	/**
	 * Geeft aan welke speler nu aan de beurt is.
	 */
	private ClientPlayer currentPlayer;
	
	/**
	 * Een hashmap van spelers die meedoen gemapt aan hun ID. Dit is een nummer die gebruikt
	 * wordt om aan te geven welke speler aan de beurt is.
	 */
	private ArrayList<ClientPlayer> players;
	
	/**
	 * Het nieuwe bord dat bij dit spel hoort. Dit board wordt ook meegegeven in de parameters 
	 * van de constructor
	 */
	private Board board;
	
	/**
	 * De nieuwe pile die bij het spel hoort.
	 */
	private Pile pile;
	
	private boolean finishedMove;
	
	/**
	 * Contrueert  een nieuw spel. Hierbij worden het board element en het aantal spelers opgeslagen
	 * in de lokale variabelen. Er wordt in de constructor ook een nieuwe Pile instantie gemaakt.
	 * Deze Pile is een representatie van de zak met stenen in het spel. Er wordt ook een HashMap
	 * geïnstantieerd die later wordt gebruikt om de players aan een ID te koppelen.
	 */
	public Game(Board board,Pile pile ) {
		this.board = board;
		this.pile = pile;
		players = new ArrayList<ClientPlayer>();
		finishedMove = false;
	}
	
	/**
	 * Controleert of het spel een winnaar heeft, hiervoor kijkt hij of winner() een
	 * player terug geeft, of een object dat null is.
	 * @return winner() != null
	 */
	public boolean hasWinner() {
		return winner() != null;
	}
	
	/**
	 * Geeft aan of het spel over is. Dit is het geval als een speler zijn laatste tegel
	 * heeft neergelegd en de Pile leeg is.
	 * @return noTilesLeft();
	 */
	/*@pure*/
	public boolean gameOver() {
		return TileUtils.noTilesLeft(this);
	}
	
	/**
	 * Geeft aan welke speler winnaar is, geeft null als er nog geen winnaar is. Een speler
	 * is de winnaar als het spel af is, en de meeste punten heeft.
	 * @return Player withHighscore || null //TODO vraag
	 */
	public ClientPlayer winner() {
		if (gameOver() == true) {
			int score = 0;
			ClientPlayer withHighscore = null;
			for (ClientPlayer player : players) {
				if (player.getScore() > score) {
					score = player.getScore();
					withHighscore = player;
				}
			}
			return withHighscore;
		} else {
			return null;
		}
	}
	
	
	/**
	 * Start het spel. Het spel zal hiervoor eerst schoongemaakt worden, vervolgens geeft
	 * de methode aan de klasse Pile aan dat deze de tegels in een spel moet genereren.
	 * Vervolgens worden tegels uitgedeeld aan alle meespelende spelers.
	 */
	public void start() {
		reset();
		pile.generateTiles();
		for (ClientPlayer player : players) {
			TileUtils.setHand(player, pile);
		}
	}
	
	/**
	 * Reset het spel. MAakt hiervoor eerst het bord schoon. Ook maakt deze de Pile leeg.
	 * Als laatst worden de handen van de spelers leeggemaakt.
	 */
	private void reset() {
		board.reset();
		pile.getTiles().clear();
		for (ClientPlayer player : players) {
			player.getHand().clear();
		}
	}
	
	/**
	 * Geeft een pile van alle tegels in het spel.
	 * @return pile
	 */
	public Pile getPile() {
		return pile;
	}
	
	/**
	 * Voegt een gegeven speler toe aan een spel. Dit kan totdat de hoeveelheid spelers in het spel 
	 * gelijk is aan het aantal spelers meegegeven in de constructor. Als het spel vol is wordt er 
	 * een exceptie gegooit.
	 * @param player
	 * @throws FullGameException 
	 */
	public void addPlayer(ClientPlayer player) throws FullGameException {
		if (players.size() <= 4) {
			players.add(player);
		} else {
			throw new FullGameException();
		}
	}
	
	/**
	 * Geeft het bord instantie van dit spel.
	 * @return
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Geeft een Map terug van de spelers in het spel.
	 * @return this.players
	 */
	public ArrayList<ClientPlayer> getPlayers() {
		return players;
	}
	
	/**
	 * Geeft de speler terug die nu aan de beurt is.
	 * @return this.currentPlayer
	 */
	public ClientPlayer getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Zet de lokale variabele currentPlayer naar de speler die de volgende beurt krijgt. Hiervoor 
	 * wordt eerst door middel van het ID van de huidige speler berekend wat de ID is van de 
	 * volgende speler. De speler met dit ID wordt dantoegewezen aan currentPlayer.
	 */
	public void nextPlayer() {
		int current = players.indexOf(currentPlayer);
		int next = (current + 1) % players.size();
		currentPlayer = players.get(next);
	}
	
	/**
	 * Zet de lokale variabele currentPlayer naar een speler die het spel mag beginnen. Dit is de 
	 * speler die in het begin de langste rij kan neerleggen op het bord. Deze methode kijkt eerst 
	 * naar alle tegels die de spelers in hun hand hebben en gaat op deze manier na welke speler mag
	 * beginnen.
	 */
	public void determineInitialPlayer() {	
		int longestRow = 0;
		ClientPlayer withLongestRow = null;
		for (ClientPlayer player : players) {
			ArrayList<Tile> hand = player.getHand();
			for (int i = 0; i < player.getHand().size(); i++) {
				ArrayList<Tile> colors = new ArrayList<Tile>();
				ArrayList<Tile> symbols = new ArrayList<Tile>();
				colors.add(player.getHand().get(i));
				symbols.add(player.getHand().get(i));
				for (int j = 0; j < player.getHand().size(); j++) {
					if (!colors.contains(hand.get(j))) {
						if (hand.get(j).getColor().equals(hand.get(i).getColor())) {
							colors.add(hand.get(j));
						}
					}
					if (!symbols.contains(hand.get(j))){
						if (hand.get(j).getSymbol().equals(hand.get(i).getSymbol())) {
							symbols.add(hand.get(j));
						}
					}
				}
				if (colors.size() > symbols.size()) {
					if (colors.size() > longestRow) {
						longestRow = colors.size();
						withLongestRow = player;
					}
				} else {
					if (symbols.size() > longestRow) {
						longestRow = symbols.size();
						withLongestRow = player;
					}
				}
			}
		}
		currentPlayer = withLongestRow;
	}
	
	/**
	 * Geeft de speler de mogelijkheid om tegels te verwisselen
	 * @param tilesToTrade
	 * @param player
	 * @throws NoTilesLeftInPileException
	 * @throws InvalidMoveException 
	 */
	public void swapTiles(ArrayList<Tile> tilesToTrade, ClientPlayer player) throws NoTilesLeftInPileException, InvalidMoveException{
		MoveUtils.replaceTiles(tilesToTrade, player, pile);
		setChanged();
		notifyObservers();
	}
	/**
	 * Moet de Move van de player verwerken.
	 * @param x
	 * @param y
	 * @param tile
	 */
	public void makeMove(int x, int y, Tile tile, ClientPlayer player) throws InvalidMoveException {
		MoveUtils.makeMove(x, y, tile, player, this);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Geeft aan dat de speler klaar is met zijn beurt.
	 * @param player
	 * @throws InvalidMoveException
	 */
	public void finishMove(ClientPlayer player) throws InvalidMoveException{
		MoveUtils.processMove(player, this);
		TileUtils.setHand(player, pile);
		finishedMove = true;
	}
	
	/**
	 * Met deze methode kan handmatig de currentPlayer worden toegewezen.
	 * voor de testklasse.
	 * @param player
	 */
	public void setCurrentPlayer(ClientPlayer player) {
		currentPlayer = player;
	}
	
	/**
	 * Deze geeft het ID nummer terug van een gegeven speler. Als een speler niet gevonden kan 
	 * worden gooit de methode een exception.
	 * @param player
	 * @return players.get(player)
	 * @throws PlayerNotFoundException
	 */
	public int getPlayerID(ClientPlayer player) throws PlayerNotFoundException {
		if (players.contains(player)) {
			return players.indexOf(player) + 1;
		} else {
			throw new PlayerNotFoundException();
		}
	}
	
	/**
	 * Geeft de boolean finishedMove terug.
	 * @return
	 */
	public boolean finishedMove(){
		return finishedMove;
	}
	
	/**
	 * Zet de bollean finishedMove op een gegeven waarde.
	 * @param bl
	 */
	public void setFinishedMove(boolean bl) {
		finishedMove = bl;
	}
}