package game.actions;

import edu.monash.fit2099.engine.*;
import game.Player;
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
     * @throws Exception if option is invalid (out of bound/can't be fed to the actor), will be caught in the code
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
                if (target.getDisplayChar() == 'a'
                        && (itemToFeed.hasCapability(MealKitType.CARNIVORE)
                            || itemToFeed.getDisplayChar() == 'e')){
                    actor.removeItemFromInventory(itemToFeed);
                    output = System.lineSeparator() + actor + "feeds " + target;
                    output += System.lineSeparator() + new EatAction(itemToFeed).execute(target, map);
                }
                else if ( (target.getDisplayChar() == 'b' || target.getDisplayChar() == 'd' )
                            && ((itemToFeed.hasCapability(MealKitType.VEGETARIAN))
                                || itemToFeed.getDisplayChar() == 'f')){
                    actor.removeItemFromInventory(itemToFeed);
                    output = System.lineSeparator() + actor + " feeds " + target;
                    output += System.lineSeparator() + new EatAction(itemToFeed).execute(target, map);

                    // when a fruit is fed to a dinosaur, 10 eco points is gained
                    if (itemToFeed.getDisplayChar() == 'f')
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
