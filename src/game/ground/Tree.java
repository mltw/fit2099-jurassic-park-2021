package game.ground;

import edu.monash.fit2099.demo.conwayslife.ConwayLocation;
import edu.monash.fit2099.engine.*;
import game.Player;
import game.portableItems.Fruit;

import java.util.Random;

public class Tree extends Ground {
	private int age = 0;
	private int foodCount = 0;
	boolean dropped = false;

	public Tree() {

		super('+');
		addCapability(Status.ALIVE);
	}

	/**
	 * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
	 *
	 * @param actor the Actor to check
	 * @return true
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return true;
	}

	@Override
	public void tick(Location location) {
		super.tick(location);

		age++;
		if (age == 10)
			displayChar = 't';
		if (age == 20)
			displayChar = 'T';

		// 29/4
		boolean read = false;
//		if(!read){
		double rand = Math.random();
		if(rand >= 0.5){
			// any turn, 50% to produce 1 ripe fruit(still on tree)
			Fruit item = new Fruit("fruit" , 'f');
			foodCount++;
			item.addCapability(Status.ON_TREE);
			location.addItem(item); // add item onto this square

			// when a fruit is produced by a tree, 1 eco point is gained
			Player.addEcoPoints(1);
		}

		// 29/4
		for (Item item : location.getItems()) {
			double random= Math.random();
			if (random <= 0.05 && item.hasCapability(Status.ON_TREE)){
				// any turn, 5%:0.05 for ripe fruits to fall
				item.removeCapability(Status.ON_TREE); // remove capability of being on tree
				item.addCapability(Status.ON_GROUND);  // now capability of being on ground
				displayChar = 'f';
				item.getDropAction(); 				   // an action to drop this item
				dropped = true;
			}
		}
	}
}
