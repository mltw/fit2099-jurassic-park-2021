package game.dinosaurs;


import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.DinosaurInterface;
import game.AttackAction;
import game.Behaviour;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Actor implements DinosaurInterface {
	// Will need to change this to a collection if Stegosaur gets additional Behaviours.
	private Behaviour behaviour;

	/** 
	 * Constructor.
	 * All Stegosaurs are represented by a 'd' and have 100 hit points.
	 * 
	 * @param name the name of this Stegosaur
	 */
	public Stegosaur(String name) {
		super(name, 'd', 100);
		
		behaviour = new WanderBehaviour();
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
		Action wander = behaviour.getAction(this, map);
		if (wander != null)
			return wander;
		
		return new DoNothingAction();
	}

	/**
	 * Gets the number of rounds the dinosaur has been unconscious. Different dinosaurs will die
	 * after different number of rounds(counts) of unconsciousness.
	 *
	 * @return an integer, representing the number of rounds of unconsciousness of the dinosaur.
	 */
	@Override
	public int getUnconsciousCount() {
		return 0;
	}

	/**
	 * Gets the number of rounds the dinosaur has been pregnant. After a specific number of rounds,
	 * the dinosaur will lay an egg.
	 *
	 * @return an integer, representing the the number of rounds the dinosaur has been pregnant.
	 */
	@Override
	public int getPregnantCount() {
		return 0;
	}

	/**
	 * Checks if the dinosaur is pregnant.
	 *
	 * @return true if the dinosaur is pregnant; false otherwise.
	 */
	@Override
	public boolean isPregnant() {
		return false;
	}
}
