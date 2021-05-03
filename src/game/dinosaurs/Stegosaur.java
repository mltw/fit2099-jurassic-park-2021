package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.*;
import game.actions.*;

import java.util.List;

/**
 * Stegosaur is a herbivorous dinosaur which only eats fruit from bush or a fruit laying on ground
 * under a tree.
 * It's main actions will be handled in the playTurn method.
 */
public class Stegosaur extends Dinosaur {
	private static int stegosaurCount = 1; // // used to give a unique name for each stegosaur
	private boolean displayed = false;
	private boolean moved = false;
	private boolean eaten = false;
	Action actionBreed;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 50 hit points initially.
	 * 
	 * @param status an enum value of either BABY or ALIVE
	 */
	public Stegosaur(Enum status) {
		super("Stegosaur" + stegosaurCount, 'd', 50);
		addCapability(status);
		maxHitPoints = 100;
		if(hasCapability(Status.BABY)) {
			this.setBabyCount(1);
			this.setHitPoints(10); //if is baby, starting hit points is 10
		}

		stegosaurCount++;
	}

	/**
	 * A stegosaur can be attacked or fed by a player.
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		actions.add(new AttackAction(this));
		actions.add(new FeedAction(this));

		return actions;
}

	/**
	 * This method will determine what action a stegosaur to perform, considering it's hitpoints,
	 * and its surrounding(adjacent square)
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action action = getBehaviour().get(0).getAction(this, map);

		eachTurnUpdates(30); 							// to handle necessary updates for each turn

		int stegosaurLocationX = map.locationOf(this).x();
		int stegosaurLocationY = map.locationOf(this).y();

		Location here = map.locationOf(this);

		for (Exit exit : here.getExits()) { 					//for each possible exit for the stegosaur to go to
			Location destination = exit.getDestination(); 		//each adjacent square of the current stegosaur

			// check nearby if has a stegosaur, if yes, move towards it & breed
			List<Exit> nearby = destination.getExits(); 		//all exits of the adjacent square, ie nearby locations
			for (Exit there : nearby){
				if (there.getDestination().getDisplayChar()=='d' && !moved && there.getDestination().getActor()!=this){
					actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
					if (actionBreed != null){
						moved = true;
					}
				}
			}

			// Egg hatched & should turn into a baby stegosaur
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
						return  new BreedAction(adjcStegosaur);
					}
				}
			}

			// if can't breed, then search for food if hungry
			else if (this.getHitPoints() < 90) {
				// display hungry message
				if (!displayed){
					if (this.isConscious()) {
						display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");
					}
					// display unconscious message
					else if (!this.isConscious() && this.getUnconsciousCount() <20){
						display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is unconscious! Feed it");
						display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
					}
					displayed = true;
				}
				// if fruit on bush/on ground under a tree & stegosaur still able to move
				if (destination.getDisplayChar() == 'f' && this.getHitPoints()!=0 && !eaten) {
					// check if adjacent square's fruit is on ground first: if yes, means stegosaur can eat, then only move & perform eat action
					if (destination.getItems().get(destination.getItems().size() - 1).hasCapability(game.ground.Status.ON_GROUND)){
						map.moveActor(this,destination);			// moveActor to food source
						action = new EatAction(destination.getItems());	// eat from 1 adjacent square == 1 turn, so if eaten, then cant eat anymore
						eaten = true;
					}
				}

			}
		}

		// if remain unconscious for 20 turns, stegosaur is dead & will turn into a corpse
		if (this.getUnconsciousCount()==20){
			return new DieAction();
		}

		displayed = false; // reset
		eaten = false; // reset

		// finally choose which action to return if previously never return any action.
		// Eating is prioritised here, followed, then following another dinosaur to prepare to breed,
		// then wandering around, then lastly do nothing.
		if (action!=null)
			return action;
		else if (actionBreed != null)
			return actionBreed;
		else{
			Action wander = getBehaviour().get(0).getAction(this, map);
			if (wander != null)
				return wander;
			return new DoNothingAction();
		}
	}

}
