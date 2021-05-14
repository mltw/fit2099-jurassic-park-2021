package game;

import edu.monash.fit2099.engine.*;
import game.actions.ExitAction;
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

	/**
	 * eco points of the player
	 */
	private static int ecoPoints;

	/**
	 * a Menu instance to display a menu
	 */
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
		this.setEcoPoints(0); // a player instance would have 0 instance initially in each game
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// add Player number of moves
		Application.setPlayerNumberOfMoves( Application.getPlayerNumberOfMoves() + 1);

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

		// add an exit action to allow the player to choose to exit the game
		actions.add(new ExitAction());

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
			actions.add(new MoveActorAction(Application.getGameMap2().at(map.locationOf(this).x(),
						Application.getGameMap2().getYRange().max()),"to new map!"));
		}
		// y = max height of second map means the most south of second map, ie can move to north of the first map
		else if (map.locationOf(this).y() == Application.getGameMap().getYRange().max()
					&& ((DinosaurGameMap) map).getName().equals("gameMap2")){
			actions.add(new MoveActorAction(Application.getGameMap().at(map.locationOf(this).x(),
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
	public static int getEcoPoints() {
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
