package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.*;
import game.portableItems.Fruit;
import game.portableItems.MealKitType;

import java.util.List;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
	// used to give a unique name for each stegosaur
	private static int stegosaurCount = 1;
	private boolean displayed = false;
	private boolean moved = false;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 50 hit points.
	 * 
	 * @param status an enum value of either BABY or ALIVE
	 */
	public Stegosaur(Enum status) {
		super("Stegosaur" + stegosaurCount, 'd', 60);
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

			// check nearby if got stegosaur, if yes, move towards it & breed
			List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
			for (Exit there : nearby){
				if (there.getDestination().getDisplayChar()=='d' && !moved && there.getDestination().getActor()!=this){
					// follow behaviour
					Action actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
					if (actionBreed != null){
						actionBreed.execute(this,map);
						moved = true;
					}
				}
			}

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
						return  new BreedAction(adjcStegosaur);
					}
				}
			}

			// if can't breed, then search for food if hungry
			else if (this.getHitPoints() < 90) {
				// display hungry message
				if (!displayed){
					display.println("Stegosaur at (" + stegosaurLocationX + "," + stegosaurLocationY + ") is getting hungry!");
//					display.println("Hit point is "+ this.getHitPoints()); // for checking purpose only, will delete
					displayed = true;
				}
				// if fruit on bush/on ground under a tree & still can be move
				if (destination.getDisplayChar() == 'f' && this.getHitPoints()!=0) {
					// moveActor to food source
					map.moveActor(this,destination);
					// then eat it
					action = new EatAction(destination.getItems());
//					Item itemToBeEaten = destination.getItems().get(destination.getItems().size() - 1);
//					destination.removeItem(itemToBeEaten);
				}
				// adjacent square has player & has fruit
				else if(destination.getDisplayChar() == '@'){
					Player player = (Player) destination.getActor();
					for (Item item: player.getInventory()){
						if (item.getDisplayChar() =='f'){
							action = new EatAction(item);
						}
						// player fed vmk
						else if(item.getDisplayChar() =='f' && item.hasCapability(MealKitType.VEGETARIAN)){
							action = new EatAction(item);
						}
					}
				}
			}
			else{
				Action wander = getBehaviour().get(0).getAction(this, map);
				if (wander != null)
					return wander;
			}
		}
//		// check if stegosaur is unconscious, first turn
//		if (this.getHitPoints() == 0 && this.getUnconsciousCount()==0) { // check
//			boolean alive = this.isConscious();
//			alive = false;
//			this.setUnconsciousCount(this.getUnconsciousCount()+1);
//		}
		if (this.getUnconsciousCount()==20){
			Item corpse = new PortableItem("dead " + this, '%');
			map.locationOf(this).addItem(corpse);
			map.removeActor(this);
			display.println(this + "is dead at (" + map.locationOf(this).x() + ","
					+ map.locationOf(this).y() +")");
		}
		displayed = false; // reset
		return action;
	}

}
