package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

/** A class that represents fish in the lake.
 * Fish is a portable item.
 */
public class Fish extends PortableItem {

    /**
     * Food points provided to Actor that eats a Fish.
     */
    private int foodpoints;

    /** Constructor
     * @param name name of fish
     * @param displayChar display character of a Fish
     */
    public Fish(String name,char displayChar) {
        super(name, 'h');
        addCapability(ItemType.FISH);
        this.foodpoints = 5;          // each fish providing 5 food points

    }

    /**
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);
    }

}
