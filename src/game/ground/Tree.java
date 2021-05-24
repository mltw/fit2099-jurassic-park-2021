package game.ground;

import edu.monash.fit2099.demo.conwayslife.ConwayLocation;
import edu.monash.fit2099.engine.*;
import game.Player;
import game.portableItems.Fruit;

import java.util.Random;

/** A class that represents tree.
 *
 */
public class Tree extends Ground {

	/**
	 * Age of the tree
	 */
	private int age = 0;

	/**
	 * Whether the tree has dropped fruit
	 */
	private boolean dropped = false;

	/** Constructor
	 * All trees are represented by a '+' character OR 't' character OR 'T' character.
	 * Trees has a Capability of being ALIVE, since it can grow fruits on it.
	 */
	public Tree() {
		super('+');
		addCapability(Status.ALIVE);
		addCapability(Status.TREE);
	}

	/** Actors are able to enter trees.
	 * @param actor the Actor to check
	 * @return true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return true;
	}

	/** Ground can also experience the joy of time.
	 * age act as a counter here:
	 * - if age reaches 10, means tree can grow from '+' small trees to 't' medium trees.
	 * - if age reaches 20, means tree can grow from 't' medium trees to 'T' big trees.
	 * There are 2 possibilities here, in any turn:
	 * - any tree has 50% to produce 1 ripe fruit(still on tree)
	 * - any ripe fruit in a tree has 5% chance to fall (dropped on ground)
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		double rand = Math.random();
		if(rand >= 0.5){
			// any turn, 50% to produce 1 ripe fruit(still on tree)
			Fruit item = new Fruit("fruit" , 'f');
			item.addCapability(Status.ON_TREE);
			location.addItem(item);
			// when a fruit is produced by a tree, 1 eco point is gained
			Player.addEcoPoints(1);
		}

		for (Item item : location.getItems()) {
			double random= Math.random();
			if (random <= 0.05 && item.hasCapability(Status.ON_TREE)){
				// any turn, 5%: for ripe fruits to fall
				item.removeCapability(Status.ON_TREE); // remove capability of being on tree
				item.addCapability(Status.ON_GROUND);  // now capability of being on ground
				displayChar = 'f';
				item.getDropAction(); 				   // an action to drop this item
				dropped = true;
			}
		}
	}
}
