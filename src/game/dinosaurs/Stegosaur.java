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

		if(hasCapability(Status.BABY)) {
			this.setBabyCount(1);
			this.setHitPoints(10); //if is baby, overwrite its hit points
		}

		stegosaurCount++;
	}


	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		return new Actions(new AttackAction(this));
	}



	/**
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// check if stegosaur is still unconscious
		if (this.getHitPoints() < 0) // check
			this.setUnconsciousCount(0);

		// if pregnant: update pregnant count
		if (this.getPregnantCount() > 0)
			this.setPregnantCount(this.getPregnantCount() + 1);

		// update maturity status
		if (this.hasCapability(Status.BABY) && this.getBabyCount() >= 30) {
			this.removeCapability(Status.BABY);
			this.setBabyCount(0);
			this.addCapability(Status.ADULT);
		} else if (this.hasCapability(Status.BABY)) {
			this.setBabyCount(this.getBabyCount() + 1);
		}

		// update food level by 1 each turn
		this.setHitPoints(this.getHitPoints() - 1);

		int stegosaurLocationX = map.locationOf(this).x();
		int stegosaurLocationY = map.locationOf(this).y();

		Location here = map.locationOf(this);

		for (Exit exit : here.getExits()) { //for each possible exit for the stegosaur to go to
			Location destination = exit.getDestination(); //each adjacent square of the current stegosaur
			if (this.getPregnantCount() >= 10) {
				return new LayEggAction();
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
						return new BreedAction(adjcStegosaur);
					}
				}
			}

			// if can't breed, then search for food if hungry
			else if (this.getHitPoints() < 90) {
				// display hungry message
				if (!displayed){
					display.println("Stegosaur at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");
					display.println("Hit point is "+ this.getHitPoints());
					displayed = true;
				}
				// if fruit on bush/on ground under a tree
				if (destination.getDisplayChar() == 'f') {
					return new EatAction(destination.getItems());
				}
				// adjacent square has player & has fruit
				else if(destination.getDisplayChar() == '@'){
					Player player = (Player) destination.getActor();
					for (Item item: player.getInventory()){
						if (item.getDisplayChar() =='f'){
							return new EatAction(player.getInventory());
						}
					}// check

				}
			}


		}
		displayed = false; // reset
		return new DoNothingAction();
	}

}
