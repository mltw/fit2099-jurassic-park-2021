package game.actions;

import edu.monash.fit2099.engine.*;
import game.ground.Status;
import game.portableItems.Egg;
import game.portableItems.EggType;
import game.portableItems.MealKitType;

import java.util.Collection;
import java.util.List;

/**
 * Action for dinosaurs to eat.
 */
public class EatAction extends Action {

    /**
     * The food (which is an Item instance) the dinosaur actor is going to eat
     */
    private Item itemToBeEaten;

    /**
     * An indicator whether that food is fed by a Player.
     * (fruits fed by Player will increase different level of hit points)
     */
    private boolean fedByPlayer;

    /**
     * Constructor.
     * If parameter is a List of Item instances, it means the dinosaur is eating the item on
     * its location, not fed by Player.
     *
     * @param items the List of Items on the ground
     */
    public EatAction(List<Item> items){
        // item to be eaten is the one displayed on the ground, which = the last item in List items
        this.itemToBeEaten = items.get(items.size() - 1);
        this.fedByPlayer= false;
    }

    /**
     * Constructor.
     * If parameter is just one Item instance, it means it is fed by the Player.
     *
     * @param item the food item to be fed to the dinosaur actor
     */
    public EatAction(Item item){
        this.itemToBeEaten = item;
        this.fedByPlayer = true;
    }

    /**
     * Perform the eating Action.
     * Items that are not fed by Player will be removed from the map.
     * (Items that are fed by the Player will be removed in the FeedAction class)
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what was eaten and by whom.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String message ="";

        // allosaur or stegosaur corpse
        if (itemToBeEaten.getDisplayChar() == '%' || itemToBeEaten.getDisplayChar() == ')'){
            actor.heal(50);
            message = actor + " ate " + "Allosaur/Stegosaur corpse to restore 50 food level";
        }
        // brachiosaur corpse
        else if (itemToBeEaten.getDisplayChar() == '('){
            actor.heal(100 );
            message = actor + " ate " + "Brachiosaur corpse to restore food level to max";
        }
        // egg
        else if (itemToBeEaten.getDisplayChar() == 'e'){
            actor.heal(10);
            message = actor + " ate an egg to restore 10 food level";
        }
        // fruits
        else if (itemToBeEaten.getDisplayChar() =='f'){
            // check if this fruit is fed by player
            if (this.fedByPlayer){
                actor.heal(20);
                message = actor + " ate a fruit fed by Player to restore 20 food level";
            }
            // check on bush / on ground of a tree
            else if (itemToBeEaten.hasCapability(Status.ON_GROUND)){
                actor.heal(10);
                message = actor + " ate a fruit on bush or a fruit laying on ground under a tree.";
            }
            else if(itemToBeEaten.hasCapability(Status.ON_TREE)){
                actor.heal(5);
                message = actor + " ate fruits on tree.";
            }
        }
        // vegetarian meal kit
        else if (itemToBeEaten.hasCapability(MealKitType.VEGETARIAN)){
            actor.heal(160);
            message = actor + " ate " + "vegetarian meal kit to restore food level to max";
        }
        // carnivore meal kit
        else if (itemToBeEaten.hasCapability(MealKitType.CARNIVORE)){
            actor.heal(100);
            message = actor + " ate " + "carnivore meal kit to restore food level to max";
        }

        // finally, remove the food item from the dinosaur actor's location, it it wasn't fed from
        // the player
        if (!fedByPlayer)
            map.locationOf(actor).removeItem(itemToBeEaten);

        return message;
    }

    /**
     * Returns a descriptive string.
     * This returns null because dinosaur's eating action is controlled by us,
     * therefore no need of having a menu description.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
