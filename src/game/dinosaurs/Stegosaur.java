package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.*;

import java.util.List;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
	// used to give a unique name for each stegosaur
	private static int stegosaurCount = 1;

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
		if (this.getHitPoints() > 0)
			this.setUnconsciousCount(0);

		// update pregnant count, if it's pregnant
		if (this.getPregnantCount() > 0)
			this.setPregnantCount(this.getPregnantCount() + 1);

		// update maturity status, ie baby or adult
		if (this.hasCapability(Status.BABY) && this.getBabyCount() >= 30) {
			this.removeCapability(Status.BABY);
			this.setBabyCount(0);
			this.addCapability(Status.ADULT);
		} else if (this.hasCapability(Status.BABY)) {
			this.setBabyCount(this.getBabyCount() + 1);
		}

		// update (minus) food level by 1 each turn
		this.setHitPoints(this.getHitPoints() - 1);

		int stegosaurLocationX = map.locationOf(this).x();
		int stegosaurLocationY = map.locationOf(this).y();

		Location here = map.locationOf(this);

		for (Exit exit : here.getExits()) { //for each possible exit for the stegosaur to go to
			Location destination = exit.getDestination(); //this represents each adjacent square of the current allosaur
			// regardless of whether that square has an Actor/Tree or wtv
			List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
//            exit.name is like North/North-East etc...

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
				display.println("Stegosaur at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");

				// if fruit on bush/on ground under a tree
				if (destination.getDisplayChar() == 'f') {
					return new EatAction(destination.getItems());
				}
			}



		}
		return new DoNothingAction();
	}

}
