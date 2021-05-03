package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

public class Wall extends Ground {

	/**
	 * Constructor
	 * A wall is represented by '#' in the game map.
	 */
	public Wall() {
		super('#');
	}

	/**
	 * Player is not able to enter a wall.
	 * @param actor the Actor to check
	 * @return false
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}

	/**
	 * A wall can used to block thrown objects.
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}
