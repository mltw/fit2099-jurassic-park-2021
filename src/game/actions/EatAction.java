package game.actions;

import edu.monash.fit2099.engine.*;
import game.ground.Status;
import game.portableItems.Egg;
import game.portableItems.EggType;
import game.portableItems.MealKitType;

import java.util.Collection;
import java.util.List;

public class EatAction extends Action {
    Item itemToBeEaten;
    boolean fedByPlayer; // fruits fed by Player will increase different level of hit points
    Actor actor;
    Location location;

    public EatAction(List<Item> items){
        // item to be eaten is the one displayed on the ground, which = the last item in List items
        this.itemToBeEaten = items.get(items.size() - 1);
        this.fedByPlayer= false;
    }

    public EatAction(Item item){
        this.itemToBeEaten = item;
        this.fedByPlayer = true;
    }

    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String message ="";
        map.locationOf(actor).removeItem(itemToBeEaten);
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
                message = actor + " ate a fruit fed by Player";
            }
            // check on bush / on ground of a tree
            else if (itemToBeEaten.hasCapability(Status.ON_GROUND)){
                actor.heal(10);
                map.locationOf(actor).removeItem(itemToBeEaten); // remove from map
                message = actor + " ate a fruit on bush or a fruit laying on ground under a tree.";
            }
            else if(itemToBeEaten.hasCapability(Status.ON_TREE)){
                actor.heal(5);
                map.locationOf(actor).removeItem(itemToBeEaten); // remove from map
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

        return message;
    }

    /**
     * Returns a descriptive string
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}