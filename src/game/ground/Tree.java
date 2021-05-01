package game.ground;

import edu.monash.fit2099.demo.conwayslife.ConwayLocation;
import edu.monash.fit2099.engine.*;
import game.portableItems.Fruit;

import java.util.Random;

public class Tree extends Ground {
	private int age = 0;
	private int foodCount = 0;
	private int counter = 0;
	boolean dropped = false;
	private NextTurn action = NextTurn.SAME;
	private boolean read = false;

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
		if(Math.random() == 0.5){
			// any turn, 50% to produce 1 ripe fruit(still on tree)
			Fruit item = new Fruit("fruit" , 'f');
			foodCount++;
			item.addCapability(Status.ON_TREE);
			location.addItem(item); // add item onto this square
		}

		// 29/4
		for (Item item : location.getItems()) {
			if (item.hasCapability(Status.ON_TREE)){
//			if (Math.random() == 0.05 && item.hasCapability(Status.ON_TREE)){
				// any turn, 5% for ripe fruits to fall
				item.removeCapability(Status.ON_TREE); // remove capability of being on tree
				item.addCapability(Status.ON_GROUND);  // now capability of being on ground
				item.getDropAction(); 				   // an action to drop this item
				dropped = true;
				counter++;
			}
			else if (item.hasCapability(Status.ON_GROUND) && counter == 1){
				read = !read;
				if (read){
					counter++;
				} else {
					if (counter == 2)
						// remove from map
						location.removeItem(item);
				}
			}
		}
	}
	private enum NextTurn {
		GROW, DIE, SAME
	}
}
