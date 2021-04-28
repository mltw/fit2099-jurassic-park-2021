package game.portableItems;

import game.PortableItem;

/**
 * A class for meal kits (vegetarian or carnivore)
 */
public class MealKit extends PortableItem {

    /**
     * Constructor
     * @param name
     * @param displayChar
     */
    public MealKit(String name, char displayChar) {
        super(name, displayChar);
    }
}
