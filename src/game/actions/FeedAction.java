package game.actions;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.dinosaurs.Status;
import game.portableItems.*;

/**
 * Action for Player to feed a dinosaur actor.
 */
public class FeedAction extends Action {

    /**
     * The Actor instance to be fed.
     */
    private Actor target;

    /**
     * Constructor.
     *
     * @param target the Actor to be fed.
     */
    public FeedAction(Actor target) {
        this.target = target;
    }

    /**
     * Perform the feeding Action.
     * First displays what the Player has in his/her inventory, and prompts an input to
     * determine which item to be fed. An EatAction will be called for the target to eat the item,
     * and the item would be removed from the Player's inventory.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of, if any, who the Player fed, and what the target ate.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Display display = new Display();

        display.println(actor + " has inventory: " + actor.getInventory());
        display.println("What would "+actor+" like to feed? Enter a number:");
        display.println("(0 for 1st item, 1 for 2nd item etc.. 'E' to exit (Waste a turn))");

        boolean flag;
        String output="";
        do {
            flag = false;
            try {
                char playerOption = display.readChar();
                // player decides to exit
                if (playerOption == 'E'){
                    output = actor + " decided not to feed " + target;
                    break;
                }

                // player decides to feed
                Item itemToFeed = actor.getInventory().get(Character.getNumericValue(playerOption));
                display.println(itemToFeed+"");
                if ((target.hasCapability(Status.ALLOSAUR) || target.hasCapability(Status.PTERODACTYL))
                        && (itemToFeed.hasCapability(MealKitType.CARNIVORE)
                            || itemToFeed.hasCapability(ItemType.EGG))){
                    actor.removeItemFromInventory(itemToFeed);
                    output = System.lineSeparator() + actor + "feeds " + target;
                    output += System.lineSeparator() + new EatAction(itemToFeed, true).execute(target, map);

                    // if player feeds Pterodactyl, 10 eco points is gained
                    if (target.hasCapability(Status.PTERODACTYL))
                        Player.addEcoPoints(10);

                }
                else if ( (target.hasCapability(Status.BRACHIOSAUR) || target.hasCapability(Status.STEGOSAUR) )
                            && ((itemToFeed.hasCapability(MealKitType.VEGETARIAN))
                                || itemToFeed.hasCapability(ItemType.FRUIT))){
                    actor.removeItemFromInventory(itemToFeed);
                    output = System.lineSeparator() + actor + " feeds " + target;
                    output += System.lineSeparator() + new EatAction(itemToFeed, true).execute(target, map);

                    // when a fruit is fed to a dinosaur, 10 eco points is gained
                    if (itemToFeed.hasCapability(ItemType.FRUIT))
                        Player.addEcoPoints(10);
                }
                else {
                    throw new Exception();
                }
                flag = true;
            }
            catch (Exception e) {
                display.println("Invalid option. Try again ");
            }
        }
        while (!flag);
        return output;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + target;
    }
}
