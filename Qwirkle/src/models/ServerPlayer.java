package models;

/**
 * Dit is een subklasse van de abstracte klasse Player. Deze klasse maakt
 * een speler die voor de online aspecten is bedoelt. Deze slaat alleen informatie
 * op, meer hoeft de server of client niet te weten.
 * @author Arend Pool en Bob Breemhaar
 *
 */
public class ServerPlayer extends Player {
	/**
	 * Construeert een Serverspeler met een gegeven naam.
	 * @param name
	 * @param game
	 */
	public ServerPlayer(String name) {
		super(name);
	}
}