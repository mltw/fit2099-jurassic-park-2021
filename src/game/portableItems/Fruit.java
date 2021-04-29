package game.portableItems;

import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

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
}
