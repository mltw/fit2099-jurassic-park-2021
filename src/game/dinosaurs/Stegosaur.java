package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.*;
import game.actions.*;
import game.ground.Lake;
import game.portableItems.Fruit;
import game.portableItems.ItemType;

import java.util.List;

/**
 * Stegosaur is a herbivorous dinosaur which only eats fruit from bush or a fruit laying on ground
 * under a tree.
 * It's main actions will be handled in the playTurn method.
 */
public class Stegosaur extends Dinosaur {
	private static int stegosaurCount = 1; // used to give a unique name for each stegosaur
	private boolean displayedHungry = false;
	private boolean displayThirsty = false;
	private boolean moved = false;
	private boolean eaten = false;
	Action actionBreed;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 50 hit points initially.
	 *
	 * @param status an enum value of either BABY or ALIVE
	 * @see Status
	 */
	public Stegosaur(Enum status) {
		// hitpoint:50
		super("Stegosaur" + stegosaurCount, 'd', 50);
		addCapability(status);
		addCapability(Status.STEGOSAUR);
		maxHitPoints = 100;
		this.setWaterLevel(60); // initial water level;60
		this.setMaxWaterLevel(100); // max water level
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
	 * @return all actions that can be performed on this stegosaur.
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		actions.add(new AttackAction(this));
		actions.add(new FeedAction(this));

		return actions;
}

	/**
	 * This method will determine what action a stegosaur can perform, considering it's hitpoints,
	 * and its surrounding(adjacent square)
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		Action action = null;
		displayedHungry = false; // reset
		moved = false; //reset
		actionBreed = null; //reset
		eaten = false; // reset
		displayThirsty = false; // reset

		eachTurnUpdates(30); // to handle necessary updates for each turn


		int stegosaurLocationX = map.locationOf(this).x();
		int stegosaurLocationY = map.locationOf(this).y();

		Location here = map.locationOf(this);

		for (Exit exit : here.getExits()) { 					//for each possible exit for the stegosaur to go to
			Location destination = exit.getDestination(); 		//each adjacent square of the current stegosaur

			// check if nearby has a stegosaur, if yes, move towards it & breed
			List<Exit> nearby = destination.getExits(); 		//all exits of the adjacent square, ie nearby locations
			for (Exit there : nearby){
				if (there.getDestination().containsAnActor()
						&& there.getDestination().getActor().hasCapability(Status.STEGOSAUR)
						&& !moved && there.getDestination().getActor()!=this){
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

			// 90
			// if stegosaur has possibility to breed, search for mating partner
			else if (this.getHitPoints() > 90 && !isPregnant() && this.hasCapability(Status.ADULT)) {
				// found an adjacent stegosaur
				if (destination.containsAnActor()
						&& destination.getActor().hasCapability(Status.STEGOSAUR)) {
					Stegosaur adjcStegosaur = (Stegosaur) destination.getActor();
					if (!this.isPregnant()
							&& !adjcStegosaur.isPregnant()
							&& adjcStegosaur.hasCapability(Status.ADULT)
							&& !(this.getGender().equals(adjcStegosaur.getGender()))) {
						return  new BreedAction(adjcStegosaur);
					}
				}
			}

			// 12/5
			else if(this.getWaterLevel() <40){
				// display thirsty message
				if (!displayThirsty){
					if (this.getWaterLevel() <40 && this.getWaterLevel() !=0) { // 40
						display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting thirsty!");
						display.println("Water level is " + this.getWaterLevel());
					}
					// display unconscious message
					else if (this.getWaterLevel()==0 && this.getUnconsciousCount() <15){ // 15 unconscious
//						boolean status = destination.getGround().hasCapability(game.ground.Status.LAKE);
						if (((DinosaurGameMap)map).isRained()){
							// one turn == one sip(one sip == 30 water level)
								this.setWaterLevel(10);
								this.setUnconsciousCount(0);
						}
						else{
							display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is unconscious! Get water for it!");
							display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
						}
					}
					displayThirsty = true;
				}
				if (destination.getGround().hasCapability(game.ground.Status.LAKE)){
					Lake ground = (Lake) destination.getGround();
					if (ground.getSips()>0) {
						ground.setSips(ground.getSips() - 1); // one turn == one sip
						display.println("After drinking, sip now is: " + ground.getSips()); // testing
						action = new DrinkAction();
					}
					// if empty(sip==0): lose ability to be drunk by stegosaur
				}

				else if (this.getUnconsciousCount()==15){
					return new DieAction();
				}

			}

			// if can't breed, then search for food if hungry & NOT thirsty
			else if (this.getHitPoints() < 90 && this.getWaterLevel()>=40) {
				// display hungry message
				if (!displayedHungry){
					if (this.isConscious()) {
						display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");
					}
					// display unconscious message
					else if (!this.isConscious() && this.getUnconsciousCount() <20){
						display.println(this + " at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is unconscious! Feed it");
						display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
					}
					displayedHungry = true;
				}

				// if fruit on bush/on ground under a tree & stegosaur still able to move
				if (destination.getItems().stream().filter(c -> c instanceof Fruit).count() >=1) {
					for (Item item: destination.getItems()) {
						if (item.hasCapability(ItemType.FRUIT) && this.getHitPoints() != 0 && !eaten) {
							// check if adjacent square's fruit is on ground first: if yes, means stegosaur can eat, then only move & perform eat action
							if (destination.getItems().get(destination.getItems().size() - 1).hasCapability(game.ground.Status.ON_GROUND)) {
								map.moveActor(this, destination);            // moveActor to food source
								action = new EatAction(item, false);    // eat from 1 adjacent square == 1 turn, so if eaten, then cant eat anymore
								eaten = true;
							}
						}
					}
				}

				// if remain unconscious for 20 turns, stegosaur is dead & will turn into a corpse
				else if (this.getUnconsciousCount()==20){
					return new DieAction();
				}
			}
		}

		// Finally choose which action to return if previously never return any action.
		// Priority from most important to least:
		// eating
		if (action!=null)
			return action;
		// following another dinosaur to check if can breed
		else if (actionBreed != null)
			return actionBreed;
		// searching for nearest food source
		else if (getBehaviour().get(1).getAction(this,map)!=null)
			return getBehaviour().get(1).getAction(this,map);
		// wandering around
		else if (getBehaviour().get(0).getAction(this, map)!=null)
			return getBehaviour().get(0).getAction(this, map);
		// do nothing
		else
			return new DoNothingAction();
		}
	}


