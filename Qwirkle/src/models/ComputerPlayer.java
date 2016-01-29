package models;
import util.MoveUtils;
import util.TileUtils;

/**
 * Maakt een speler met enige vorm van kunstmatige intelligentie.
 * @author Arend Pool en Bob breemhaar
 *
 */
public class ComputerPlayer extends Player {
	/**
	 * Geeft de denktijd van de computer aan.
	 */
	private int thinkingTime;
	
	/**
	 * Maakt een nieuwe speler aan met een gegeven naam en denktijd.
	 * @param name
	 * @param thinkingTime
	 */
	public ComputerPlayer(String name, int thinkingTime) {
		super(name);
		this.thinkingTime = thinkingTime;
	}
	
	/**
	 * Bepaald de volgende zet van de Computer. Hiervoor controlleert de computer eerst
	 * of het vakje in het midden leeg is, hier moet namelijk altijd de eerste tegel liggen.
	 * Vervolgens kijkt de methode van elk vak of deze leeg is. Als deze niet leeg is 
	 * controlleerd de methode of om deze tegel heen een tegel kan liggen die er mag liggen
	 * volgens de spelregels. Als dit kan legt de computer hier de tegel neer. Anders kijkt
	 * de Computer of een andere tegel wel ergens kan liggen. Als de Computer geen tegels
	 * heeft die ergens kan liggen zal deze een tegel ruilen.
	 * @return 
	 */
	public String[] determineMove(Game game) {
		try {
			synchronized (this) {
				wait(thinkingTime);
			}
		} catch (InterruptedException e) {
			System.out.println("De computers denktijd is verstoord.");
		}
		if (MoveUtils.initialMove == true) {
			String[] tile = determineTile(Integer.toString(1));
			String[] move = {Integer.toString(0), Integer.toString(0), tile[0], tile[1]};
			return move;
		} else {
			for (Tile tile: getHand()) {
				for (int x = 0; x < Board.DIM; x++) {
					for (int y = 0; y < Board.DIM; y++) {
						if (!game.getBoard().isEmptyField(x, y)) {
							if (MoveUtils.isValidMove(x + 1, y, tile, game.getBoard())) {
								String[] move = {Integer.toString(x - 89), Integer.toString(y - 90),
										Integer.toString(TileUtils.symbolToInt(tile.getSymbol())),
										Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x - 1, y, tile, game.getBoard())) {
								String[] move = {Integer.toString(x - 91), Integer.toString(y - 90),
										Integer.toString(TileUtils.symbolToInt(tile.getSymbol())),
										Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x, y - 1, tile, game.getBoard())) {
								String[] move = {Integer.toString(x - 90), Integer.toString(y - 91),
										Integer.toString(TileUtils.symbolToInt(tile.getSymbol())),
										Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x, y + 1, tile, game.getBoard())) {
								String[] move = {Integer.toString(x - 90), Integer.toString(y - 89),
										Integer.toString(TileUtils.symbolToInt(tile.getSymbol())),
										Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							}
						}
					}
				}
			}
		}
		if (getHand().size() > 0) {
			Tile tile = getHand().get(0);
			String[] swap = {Integer.toString(TileUtils.symbolToInt(tile.getSymbol())),
					Integer.toString(TileUtils.colorToInt(tile.getColor()))};
			return swap;
		}
		return null;
	}	
}
