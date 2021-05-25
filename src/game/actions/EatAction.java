package game.actions;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Pterodactyl;
import game.ground.Status;
import game.portableItems.*;

/**
 * Action for dinosaurs to eat.
 */
public class EatAction extends Action {

    /**
     * The food (which is an Item instance) the dinosaur actor is going to eat
     */
    private Item itemToBeEaten;

    /**
     * The food (which is an Actor instance) the dinosaur actor is going to eat.
     */
    private Actor dinosaurToBeEaten = null;

    /**
     * An indicator whether that food is fed by a Player.
     * (fruits fed by Player will increase different level of hit points)
     */
    private boolean fedByPlayer = false;

    /**
     * Constructor.
     *
     * @param item the food item to be fed to the dinosaur actor
     * @param fedByPlayer an indicator whether that food is fed by a Player.
     */
    public EatAction(Item item, boolean fedByPlayer){
        this.itemToBeEaten = item;
        this.fedByPlayer = fedByPlayer;
    }

    /**
     * Constructor.
     * This is only used when an Allosaur eats an alive Pterodactyl
     *
     * @param dinosaurToBeEaten the actor to be eaten by the dinosaur actor
     * @param fedByPlayer an indicator whether that food is fed by a Player.
     */
    public EatAction(Actor dinosaurToBeEaten, boolean fedByPlayer){
        this.dinosaurToBeEaten = dinosaurToBeEaten;
        this.fedByPlayer = fedByPlayer;
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

        // handle Pterodactyl eating Corpse
        if (actor.hasCapability(game.dinosaurs.Status.PTERODACTYL)
                && itemToBeEaten.hasCapability(ItemType.CORPSE)){
            actor.heal(10);
            message = actor + " ate " + "a corpse to restore 10 food level";

            // deduct the corpse's edible count
            ((Corpse) itemToBeEaten).setEdibleCount( ((Corpse) itemToBeEaten).getEdibleCount() - 1);

            // remove corpse if it's completely eaten by the pterodactyl, and it's not fed by player
            // ie Corpse is on the ground
            if (((Corpse) itemToBeEaten).getEdibleCount() <=0 && !fedByPlayer)
                map.locationOf(actor).removeItem(itemToBeEaten);
        }

        // handle Allosaur eating live Pterodactyl that is walking on the ground
        else if (this.dinosaurToBeEaten != null
                && this.dinosaurToBeEaten.hasCapability(game.dinosaurs.Status.ON_LAND)
                && !map.locationOf(this.dinosaurToBeEaten).getGround().hasCapability(Status.TREE)){
            actor.heal(100);
            message = actor + " ate " + "a live Pterodactyl to restore food level to max";
            map.removeActor(dinosaurToBeEaten);
        }

        else if (this.itemToBeEaten != null){
            // allosaur or stegosaur corpse
            if (itemToBeEaten.hasCapability(CorpseType.ALLOSAUR)
                    || itemToBeEaten.hasCapability(CorpseType.STEGOSAUR)) {
                int healingPoints = ((Corpse) itemToBeEaten).getEdibleCount()*10;
                actor.heal(healingPoints);
                message = actor + " ate " + "Allosaur/Stegosaur corpse to restore " + healingPoints +" food level";
            }
            // brachiosaur corpse
            else if (itemToBeEaten.hasCapability(CorpseType.BRACHIOSAUR)) {
                int healingPoints = ((Corpse) itemToBeEaten).getEdibleCount()*10;
                actor.heal(healingPoints);
                message = actor + " ate " + "Brachiosaur corpse to restore " + healingPoints + "food level";
            }
            // pterodactyl corpse
            else if (itemToBeEaten.hasCapability(CorpseType.PTERODACTYL)) {
                int healingPoints = ((Corpse) itemToBeEaten).getEdibleCount()*10;
                actor.heal(healingPoints);
                message = actor + " ate " + "Pterodactyl corpse to restore " + healingPoints +" food level";
            }
            // fish
            else if (itemToBeEaten.hasCapability(ItemType.FISH)) {
                actor.heal(5);
                message = actor + " ate " + "a fish to restore 5 food level";
            }
            // egg
            else if (itemToBeEaten.hasCapability(ItemType.EGG)) {
                actor.heal(10);
                message = actor + " ate an egg to restore 10 food level";
            }
            // fruits
            else if (itemToBeEaten.hasCapability(ItemType.FRUIT)) {
                // check if this fruit is fed by player
                if (this.fedByPlayer) {
                    actor.heal(20);
                    message = actor + " ate a fruit fed by Player to restore 20 food level";
                }
                // check on bush / on ground of a tree
                else if (itemToBeEaten.hasCapability(Status.ON_GROUND)) {
                    actor.heal(10);
                    message = actor + " ate a fruit on bush or a fruit laying on ground under a tree.";
                } else if (itemToBeEaten.hasCapability(Status.ON_TREE)) {
                    actor.heal(5);
                    message = actor + " ate fruits on tree.";
                }
            }
            // vegetarian meal kit
            else if (itemToBeEaten.hasCapability(MealKitType.VEGETARIAN)) {
                actor.heal(160);
                message = actor + " ate " + "vegetarian meal kit to restore food level to max";
            }
            // carnivore meal kit
            else if (itemToBeEaten.hasCapability(MealKitType.CARNIVORE)) {
                actor.heal(100);
                message = actor + " ate " + "carnivore meal kit to restore food level to max";
            }

            // finally, remove the food item from the dinosaur actor's location, it it wasn't fed from
            // the player
            if (!fedByPlayer)
                map.locationOf(actor).removeItem(itemToBeEaten);
        }

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
