package game.actions;

import edu.monash.fit2099.engine.*;
import game.LaserGun;
import game.Player;
import game.ground.Status;
import game.portableItems.*;

/**
 * Action for Player to purchase items from a vending machine.
 */
public class PurchaseAction extends Action {

    /**
     * Perform the purchase Action.
     * First displays the amount of eco points the Player has, and displays a menu of items for sale.
     * Then based on user input, adds the item purchased into the Player's inventory and deduct its
     * eco points (which = price of item purchased).
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of, if any, what the Player purchased.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Display display = new Display();
        Item itemPurchased = null;

        display.println(actor + " has $" + Player.getEcoPoints());

        display.println("+==============================+");
        display.println("|        Items for sale:       |");
        display.println("|------------------------------|");
        display.println("| 1. Fruit               $  30 |");
        display.println("| 2. Vegetarian meal kit $ 100 |");
        display.println("| 3. Carnivore meal kit  $ 500 |");
        display.println("| 4. Stegosaur egg       $ 200 |");
        display.println("| 5. Brachiosaur egg     $ 500 |");
        display.println("| 6. Allosaur egg        $1000 |");
        display.println("| 7. Pterodactyl egg     $ 200 |");
        display.println("| 8. Laser gun           $ 500 |");
        display.println("| 9. Exit(waste a turn)        |");
        display.println("+==============================+");
        display.println("Enter your option (the number): ");

        boolean flag;
        do {
            flag = false;
            try {
                char playerOption = display.readChar();
                if (playerOption == '1' && Player.getEcoPoints() - 30 >= 0) {
                    Player.addEcoPoints(-30);
                    itemPurchased = new Fruit("fruit", 'f');
                    itemPurchased.addCapability(Status.ON_GROUND);
                }
                else if (playerOption == '2' && Player.getEcoPoints() - 100 >= 0) {
                    Player.addEcoPoints(-100);
                    itemPurchased = new MealKit(MealKitType.VEGETARIAN);
                }
                else if (playerOption == '3' && Player.getEcoPoints() - 500 >= 0) {
                    Player.addEcoPoints(-500);
                    itemPurchased = new MealKit(MealKitType.CARNIVORE);
                }
                else if (playerOption == '4' && Player.getEcoPoints() - 200 >= 0) {
                    Player.addEcoPoints(-200);
                    itemPurchased = new Egg(EggType.STEGOSAUR);
                }
                else if (playerOption == '5' && Player.getEcoPoints() - 500 >= 0) {
                    Player.addEcoPoints(-500);
                    itemPurchased = new Egg(EggType.BRACHIOSAUR);
                }
                else if (playerOption == '6' && Player.getEcoPoints() - 1000 >= 0) {
                    Player.addEcoPoints(-1000);
                    itemPurchased = new Egg(EggType.ALLOSAUR);
                }
                else if (playerOption == '7' && Player.getEcoPoints() - 200 >= 0) {
                    Player.addEcoPoints(-200);
                    itemPurchased = new Egg(EggType.PTERODACTYL);
                }
                else if (playerOption == '8' && Player.getEcoPoints() - 500 >= 0) {
                    Player.addEcoPoints(-500);
                    itemPurchased = new LaserGun();
                }
                else if (playerOption == '9') {
                    return actor + " didn't purchase anything";
                }
                else {
                    throw new Exception();
                }

                actor.addItemToInventory(itemPurchased);
                flag = true;
            }
            catch (Exception e) {
                display.println("Invalid option / not enough hit points to purchase. Try again ");
            }
        }
        while (!flag);

        return actor + " purchased " + itemPurchased;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Purchase items from vending machine";
    }

    @Override
    public String hotkey() {
        return "P";
    }
}
