package game;

import edu.monash.fit2099.engine.*;
import game.portableItems.*;

public class FeedAction extends Action {

    private Actor target;

    public FeedAction(Actor target) {
        this.target = target;
    }

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
                    output = new EatAction(itemToFeed).execute(target, map);
                }
                else if ( (target.getDisplayChar() == 'b' || target.getDisplayChar() == 'd' )
                            && ((itemToFeed.hasCapability(MealKitType.VEGETARIAN))
                                || itemToFeed.getDisplayChar() == 'f')){
                    actor.removeItemFromInventory(itemToFeed);
                    output = new EatAction(itemToFeed).execute(target, map);
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
