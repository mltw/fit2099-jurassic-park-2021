package game.portableItems;

import edu.monash.fit2099.engine.*;
import game.PortableItem;
import game.ground.Status;

/**
 * A class for fruits
 */
public class Fruit extends PortableItem {

    public Fruit(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Create and return an action to drop this Item.
     * If this Item is not portable, returns null.
     *
     * @return a new DropItemAction if this Item is portable, null otherwise.
     */
    @Override
    public DropItemAction getDropAction() {
        return super.getDropAction();
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
        this.setCount( this.getCount()+1);
        Display display = new Display();

        // after 15 turns, remove the fruit
        if (this.getCount() > 15){
            currentLocation.removeItem(this);
        }
    }

}
