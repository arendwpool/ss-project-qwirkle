package models;
import util.MoveUtils;
import util.TileUtils;

/**
 * Maakt een speler met enige vorm van kunstmatige intelligentie
 * @author Arend Pool en Bob breemhaar
 *
 */
public class ComputerPlayer extends Player {
	public ComputerPlayer(String name) {
		super(name);
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
		if (MoveUtils.initialMove == true) {
			String[] tile = determineTile(Integer.toString(1));
			String[] move = {Integer.toString(0), Integer.toString(0), tile[0], tile[1]};
			return move;
		} else {
			for(Tile tile: getHand()){
				for(int x = 0; x < Board.DIM; x++){
					for(int y = 0; y < Board.DIM; y++){
						if (!game.getBoard().isEmptyField(x, y)) {
							if(MoveUtils.isValidMove(x+1, y, tile, game.getBoard())){
								String[] move = {Integer.toString(x-89), Integer.toString(y-90), Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x-1, y, tile, game.getBoard())) {
								String[] move = {Integer.toString(x-91), Integer.toString(y-90), Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x, y-1, tile, game.getBoard())) {
								String[] move = {Integer.toString(x-90), Integer.toString(y-91), Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							} else if (MoveUtils.isValidMove(x, y+1, tile, game.getBoard())) {
								String[] move = {Integer.toString(x-90), Integer.toString(y-89), Integer.toString(TileUtils.symbolToInt(tile.getSymbol())), Integer.toString(TileUtils.colorToInt(tile.getColor()))};
								return move;
							}
						}
					}
				}
			}
		}
		/*if(MoveUtils.madeMove() == false){
			return determineTile(Integer.toString(1));
		}*/
		return null;
	}	
}
