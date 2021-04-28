package game.ground;

import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {

		super('.');
		addCapability(Status.DEAD);
	}
	// at the beginning of game & at the beginning of each turn
	// each square of dirt has a 1% chance to grow a bush

	// on any turn, any square of dirt that is next to at least 2 square of bushes
	// has a 10% chance to grow a bush (similar concept as aliveNeighbours? in conwayslife)

	// if any square of dirt next to a tree --> 0% to grow bush
}
