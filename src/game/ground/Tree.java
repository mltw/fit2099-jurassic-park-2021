package game.ground;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.portableItems.Fruit;

import java.util.Random;

public class Tree extends Ground {
	private int age = 0;
	private int foodCount = 0;

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
			Fruit item = new Fruit("fruit" , 'f',false);
			foodCount++;
			location.addItem(item); // add item onto this square
		}
	}
}
