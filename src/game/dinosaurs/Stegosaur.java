package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import game.AttackAction;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'd', 50);
		
		setBehaviour(new WanderBehaviour());
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
