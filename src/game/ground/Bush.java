package game.ground;

import edu.monash.fit2099.engine.Actor;
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
     * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
     *
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * Ground can also experience the joy of time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        boolean read = false;
        // 29/4
//        if(!read){
//        if(Math.random() == 1.0){
        double rand = Math.random();
        if(rand == 0.10){
            // any turn, 10%:0.1 to produce 1 ripe fruit
            Fruit item = new Fruit("fruit" , 'f');
            foodCount++;
            item.addCapability(Status.ON_GROUND); // check
            location.addItem(item);
        }
    }
}
