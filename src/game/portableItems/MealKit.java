package game.portableItems;

import game.PortableItem;

/**
 * A class for meal kits (vegetarian or carnivore).
 * A meal kit is portable item.
 */
public class MealKit extends PortableItem {

    /**
     * Constructor
     * @param mealKitType the type of meal kit, either vegetarian or carnivore
     */
    public MealKit(Enum mealKitType) {
        super("mealKit("+mealKitType+")", 'm');
        this.capabilities.addCapability(mealKitType);
    }
}
