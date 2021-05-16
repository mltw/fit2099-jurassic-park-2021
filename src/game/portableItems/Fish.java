package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

/** A class that represents fish in the lake.
 * Fish is a portable item.
 */
public class Fish extends PortableItem {
    private int foodpoints = 0;
    Display display = new Display();

    /** Constructor
     * @param name name of fish
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

//        double fishProbability = Math.random();
//        if (this.getCount()<25 && fishProbability >=0.6){
//            Fish item = new Fish("fish",'h');
//            currentLocation.addItem(item);
////            this.count++;
//            display.println("Lake has " + count + " fish!");                // testing
//        }
//        else if (this.getCount()==25){
//            display.println("This lake has reached the maximum of 25 fish!"); // testing
//        }
    }

}
