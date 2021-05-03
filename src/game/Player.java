package game;

import edu.monash.fit2099.engine.*;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

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
				break;
			}
			else if(exit.getDestination().getDisplayChar() == 'f'){
				// pick a fruit that is lying on ground/bush
				PortableItem itemToBePicked = (PortableItem) exit.getDestination().getItems().get(exit.getDestination().getItems().size()-1);
				actions.add(new PickUpAction(itemToBePicked));
				break;
			}
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
}
