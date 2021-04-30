package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
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
	 * Figure out what to do next.
	 * 
	 * FIXME: Stegosaur wanders around at random, or if no suitable MoveActions are available, it
	 * just stands there.  That's boring.
	 * 
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// get Stegosaur's location
		Location stegosaur1 = map.locationOf(this);
//		if (hitPoints >50 && !isPregnant()){
//
//		}
//		Action wander = behaviour.getAction(this, map);
//		if (wander != null)
//			return wander;
//
		return new DoNothingAction();
	}

}
