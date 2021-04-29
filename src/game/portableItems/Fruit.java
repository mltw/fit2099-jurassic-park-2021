package game.portableItems;

import edu.monash.fit2099.engine.Item;
import game.PortableItem;

/**
 * A class for fruits
 */
public class Fruit extends Item {

    // 29/4 : change from PortableItem to Item
    public Fruit(String name, char displayChar, boolean portable) {
        super(name, displayChar, portable);
    }
}
