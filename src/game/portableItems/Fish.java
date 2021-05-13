package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

/** A class that represents fish in the lake.
 * Fish is a portable item.
 */
public class Fish extends PortableItem {
    private int count = 0;
    private int foodpoints = 0;
    Display display = new Display();

    /** Constructor
     * @param name name of fish
     * @param count number of fish in the current lake.
     */
    public Fish(String name,int count) {
        super(name, 'h');
        addCapability(ItemType.FISH);
        this.foodpoints = 5*count;          // each fish providing 5 food points
        this.count = count;
    }

    /** Each lake can hold up to maximum 25 fish
     *  Each turn, there is a probability of 60% for a new fish to be born
     *  (increasing the number of fish of the lake by 1)
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);

        double fishProbability = Math.random();
        if (this.getCount()<25 && fishProbability >=0.6){
            this.count++;
            display.println("Lake has " + count + " fish!");                // testing
        }
        else if (this.getCount()==25){
            display.println("This lake has reached the maximum of 25 fish!"); // testing
        }
    }

    /** Getter
     * Retrieve the number of fish the lake currently has.
     * @return the number of fish the lake currently has.
     */
    @Override
    public int getCount() {
        return count;
    }

    /** Setter
     * Sets the number of fish of the lake.
     * @param count number of fish of the lake to be updated.
     */
    @Override
    public void setCount(int count) {
        this.count = count;
    }
}
