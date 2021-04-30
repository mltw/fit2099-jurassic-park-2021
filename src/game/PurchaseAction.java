package game;

import edu.monash.fit2099.engine.*;
import game.portableItems.*;

public class PurchaseAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        Display display = new Display();
        Item itemPurchased = null;

        display.println(actor + " has $" + ((Player) actor).getHitPoints());

        display.println("+==============================+");
        display.println("|        Items for sale:       |");
        display.println("|------------------------------|");
        display.println("| 1. Fruit               $  30 |");
        display.println("| 2. Vegetarian meal kit $ 100 |");
        display.println("| 3. Carnivore meal kit  $ 500 |");
        display.println("| 4. Stegosaur egg       $ 200 |");
        display.println("| 5. Brachiosaur egg     $ 500 |");
        display.println("| 6. Allosaur egg        $1000 |");
        display.println("| 7. Laser gun           $ 500 |");
        display.println("| 8. Exit(waste a turn)        |");
        display.println("+==============================+");
        display.println("Enter your option (the number): ");

        boolean flag;
        do {
            flag = false;
            try {
                char playerOption = display.readChar();
                if (playerOption == '1' && ((Player) actor).getHitPoints() - 30 >= 0) {
                    actor.hurt(30);
                    itemPurchased = new Fruit("fruit", 'f');
                } else if (playerOption == '2' && ((Player) actor).getHitPoints() - 100 >= 0) {
                    actor.hurt(100);
                    itemPurchased = new MealKit(MealKitType.VEGETARIAN);
                } else if (playerOption == '3' && ((Player) actor).getHitPoints() - 500 >= 0) {
                    actor.hurt(500);
                    itemPurchased = new MealKit(MealKitType.CARNIVORE);
                } else if (playerOption == '4' && ((Player) actor).getHitPoints() - 200 >= 0) {
                    actor.hurt(200);
                    itemPurchased = new Egg(EggType.STEGOSAUR);
                } else if (playerOption == '5' && ((Player) actor).getHitPoints() - 500 >= 0) {
                    actor.hurt(500);
                    itemPurchased = new Egg(EggType.BRACHIOSAUR);
                } else if (playerOption == '6' && ((Player) actor).getHitPoints() - 1000 >= 0) {
                    actor.hurt(1000);
                    itemPurchased = new Egg(EggType.ALLOSAUR);
                } else if (playerOption == '7' && ((Player) actor).getHitPoints() - 500 >= 0) {
                    actor.hurt(500);
                    itemPurchased = new LaserGun("laserGun", '~', 70, "zaps");
                } else if (playerOption == '8') {
                    return actor + " didn't purchase anything";
                } else {
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
