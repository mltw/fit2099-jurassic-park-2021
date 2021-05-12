package game.portableItems;

import edu.monash.fit2099.engine.Location;
import game.PortableItem;

public class Fish extends PortableItem {
    // Each lake can also hold up to maximum 25 fish,
    // each fish providing 5 food points.
    // Lakes start the game with only 5 fish.
    // Each turn, there is a probability of 60% for a new fish to be born
    // (increasing the number of fish of the lake by 1).

    public Fish(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
        double rand = Math.random();
        if(rand >= 0.6){

        }
    }
}
