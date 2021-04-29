package game.ground;

import edu.monash.fit2099.engine.*;
import game.portableItems.Fruit;

import java.util.Random;

public class Tree extends Ground {
	private int age = 0;
	private int foodCount = 0;
	private static int counter = 0;
	boolean dropped = false;

	public Tree() {

		super('+');
		addCapability(Status.ALIVE);
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
		if(Math.random() == 0.5){
			// any turn, 50% to produce 1 ripe fruit(still on tree)
			Fruit item = new Fruit("fruit" , 'f');
			foodCount++;
			item.addCapability(Status.ON_TREE);
			location.addItem(item); // add item onto this square
		}

		// 29/4
		for (Item item : location.getItems()) {
			if (Math.random() == 0.05 && item.hasCapability(Status.ON_TREE)){
				// any turn, 5% for ripe fruits to fall
				item.removeCapability(Status.ON_TREE); // remove capability of being on tree
				item.addCapability(Status.ON_GROUND);  // now capability of being on ground
				item.getDropAction(); 				   // an action to drop this item
				dropped = true;
				counter++;
			}
			if (counter == 15){
				// remove from map
				location.removeItem(item);
			}
		}
		if (counter !=0){
			// means fruit dropped before, counter activated
			counter++;
		}
	}
}
