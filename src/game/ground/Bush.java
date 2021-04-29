package game.ground;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.portableItems.Fruit;

import java.util.ArrayList;

/**
 * A class that represents bush.
 */
public class Bush extends Ground {
    private int foodCount = 0;

    /**
     * Constructor.
     * All bushes are represented by a 'v' character.
     */
    public Bush() {
        super('v');
        addCapability(Status.ALIVE); // since it can grow something: 29/4
    }

    /**
     * Ground can also experience the joy of time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);

        // 29/4
        if(Math.random() == 0.1){
            // any turn, 10% to produce 1 ripe fruit
            Fruit item = new Fruit("fruit" , 'f');
            foodCount++;
            item.addCapability(Status.ON_GROUND); // check
            location.addItem(item);

        }
    }
}
