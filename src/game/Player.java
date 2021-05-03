package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the Player.
 */
public class Player extends Actor {
//	private static int ecoPointsFromPreviousRound = 0;
	private static int ecoPoints = 100;
	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// find if there's a Vending Machine instance in adjacent square
		for (Exit exit : map.locationOf(this).getExits()){
			if (exit.getDestination().getDisplayChar() == '$'){
				actions.add(new PurchaseAction());
			}
		}

		if (map.locationOf(this).getGround().getDisplayChar() == 'v'){
			actions.add(new SearchFruitAction("bush"));
		}
		else if (map.locationOf(this).getGround().getDisplayChar() == '+'
				|| map.locationOf(this).getGround().getDisplayChar() == 't'
				|| map.locationOf(this).getGround().getDisplayChar() == 'T'){
			actions.add(new SearchFruitAction("tree"));
		}

		return menu.showMenu(this, actions, display);
	}

	/**
	 * A getter for the Player's hit points.
	 * @return the player's hit points.
	 */
	public int getHitPoints(){
		return this.hitPoints;
	}

	public int getEcoPoints() {
		return ecoPoints;
	}

	public void setEcoPoints(int points) {
		ecoPoints = points;
	}

	public static void addEcoPoints(int points){
		ecoPoints += points;
	}
}
