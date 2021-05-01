package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.*;
import game.portableItems.Fruit;

import java.util.List;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
	// used to give a unique name for each stegosaur
	private static int stegosaurCount = 1;
	private boolean displayed = false;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 50 hit points.
	 * 
	 * @param status an enum value of either BABY or ALIVE
	 */
	public Stegosaur(Enum status) {
		super("Stegosaur" + stegosaurCount, 'd', 50);
		addCapability(status);
		maxHitPoints = 100;
		if(hasCapability(Status.BABY)) {
			this.setBabyCount(1);
			this.setHitPoints(10); //if is baby, starting hit points
		}

		stegosaurCount++;
	}


	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {

		Actions actions = new Actions();

		// a Stegosaur can be attacked or fed by Player
		actions.add(new AttackAction(this));
		actions.add(new FeedAction(this));

		return actions;
}

	/**
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action action = new DoNothingAction();

		eachTurnUpdates(30);

		int stegosaurLocationX = map.locationOf(this).x();
		int stegosaurLocationY = map.locationOf(this).y();

		Location here = map.locationOf(this);

		for (Exit exit : here.getExits()) { //for each possible exit for the stegosaur to go to
			Location destination = exit.getDestination(); //each adjacent square of the current stegosaur
			if (this.getPregnantCount() >= 10) {
				action = new LayEggAction();
			}

			// if stegosaur has possibility to breed, search for mating partner
			else if (this.getHitPoints() > 50 && !isPregnant() && this.hasCapability(Status.ADULT)) {
				// found an adjacent stegosaur
				if (destination.getDisplayChar() == 'd') {
					Stegosaur adjcStegosaur = (Stegosaur) destination.getActor();
					if (!this.isPregnant()
							&& !adjcStegosaur.isPregnant()
							&& adjcStegosaur.hasCapability(Status.ADULT)
							&& !(this.getGender().equals(adjcStegosaur.getGender()))) {
						action =  new BreedAction(adjcStegosaur);
					}
				}
			}

			// if can't breed, then search for food if hungry
			else if (this.getHitPoints() < 90) {
				// display hungry message
				if (!displayed){
					display.println("Stegosaur at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");
					display.println("Hit point is "+ this.getHitPoints()); // for checking purpose only, will delete
					displayed = true;
				}
				// if fruit on bush/on ground under a tree & still can be move
				if (destination.getDisplayChar() == 'f' && this.getHitPoints()!=0) {
					action = new EatAction(destination.getItems());
					Item itemToBeEaten = destination.getItems().get(destination.getItems().size() - 1);
					destination.removeItem(itemToBeEaten);
//					String result = action.execute(this, map);
//					display.println(result);
				}
				// adjacent square has player & has fruit
				else if(destination.getDisplayChar() == '@'){
					Player player = (Player) destination.getActor();
					for (Item item: player.getInventory()){
						if (item.getDisplayChar() =='f'){
							action = new EatAction(player.getInventory());
						}
					}// check

				}
				// player fed vmk
			}
			else{
				Action wander = getBehaviour().get(0).getAction(this, map);
				if (wander != null)
					return wander;
			}
		}
		// check if stegosaur is unconscious, first turn
		if (this.getHitPoints() == 0 && this.getUnconsciousCount()==0) { // check
			boolean alive = this.isConscious();
			alive = false;
			this.setUnconsciousCount(this.getUnconsciousCount()+1);
		}
		else if (this.getUnconsciousCount()==20){
			Item corpse = new PortableItem("dead " + this, '%');
			map.locationOf(this).addItem(corpse);
			map.removeActor(this);
		}
		displayed = false; // reset
		return action;
	}

}
