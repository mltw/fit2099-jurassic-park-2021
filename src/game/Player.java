package game;

import edu.monash.fit2099.engine.*;
import game.actions.PurchaseAction;
import game.actions.SearchFruitAction;
import game.ground.Bush;
import game.ground.Status;
import game.ground.Tree;

import java.util.List;
import java.util.Locale;

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
			for (Item item : exit.getDestination().getItems()){
				if (item.getDisplayChar() == '$')
					actions.add(new PurchaseAction());
			}
		}

		// player able to pick up fruits from bush: perform SearchFruitAction
		if (map.locationOf(this).getGround().hasCapability(Status.BUSH)){
			actions.add(new SearchFruitAction("bush"));
		}
		// player able to pick up fruits from a tree/fruits lying on the ground of tree : perform SearchFruitAction
		else if (map.locationOf(this).getGround().hasCapability(Status.TREE)){
			actions.add(new SearchFruitAction("tree"));
		}

		// to move to another map
		// y = 0 of first map means the most north of first map, ie can move to south of the second map
		if (map.locationOf(this).y() == 0
				&& ((DinosaurGameMap) map).getName().equals("gameMap1")) {
			actions.add(new MoveActorAction(Application.gameMap2.at(map.locationOf(this).x(),
						Application.gameMap2.getYRange().max()),"to new map!"));
		}
		// y = max height of second map means the most south of second map, ie can move to north of the first map
		else if (map.locationOf(this).y() == Application.gameMap2.getYRange().max()
					&& ((DinosaurGameMap) map).getName().equals("gameMap2")){
			actions.add(new MoveActorAction(Application.gameMap.at(map.locationOf(this).x(),
						0),"back to old map!"));
		}

		return menu.showMenu(this, actions, display);
	}

	/** Getter
	 * Retrieve player's hit points.
	 * @return the player's hit points.
	 */
	public int getHitPoints(){
		return this.hitPoints;
	}

	/** Getter
	 * Retrieve player's eco points.
	 * @return the player's eco points.
	 */
	public int getEcoPoints() {
		return ecoPoints;
	}

	/** Setter
	 * For Player's eco points.
	 * @param points the eco points of the Player
	 */
	public void setEcoPoints(int points) {
		ecoPoints = points;
	}

	/**
	 * To increment player's eco points
	 * @param points points to be incremented
	 */
	public static void addEcoPoints(int points){
		ecoPoints += points;
	}
}
