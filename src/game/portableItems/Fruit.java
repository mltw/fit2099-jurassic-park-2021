package game.portableItems;

import edu.monash.fit2099.engine.*;
import game.PortableItem;
import game.ground.Status;

/**
 * A class for fruits.
 * A fruit is a portable item.
 */
public class Fruit extends PortableItem {

    /** Constructor
     * @param name name of fruit
     * @param displayChar   displayChar for fruit is 'f'
     */
    public Fruit(String name, char displayChar) {
        super(name, displayChar);
        addCapability(ItemType.FRUIT);
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

        //  after 15 turns, dropped fruit (on the ground) will rot, and thus remove it
        if (this.hasCapability(Status.ON_GROUND) && this.getCount() > 15){
            currentLocation.removeItem(this);
        }
        else if (this.hasCapability(Status.ON_GROUND)){
            this.setCount( this.getCount()+1);
        }
    }

}
