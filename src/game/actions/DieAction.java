package game.actions;

import edu.monash.fit2099.engine.*;
import game.dinosaurs.Status;
import game.portableItems.Corpse;
import game.portableItems.CorpseType;

/**
 * Action to handle dead actors.
 */
public class DieAction extends Action {

    /**
     * Remove the dead actor, drop its inventory items (if any) on the ground it is on,
     * and replace the actor with a corpse.
     *
     * @param actor The actor to be replaced with a corpse.
     * @param map The map the actor is on.
     * @return a description of which actor died and at which location.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String message= "";

        if (actor.hasCapability(Status.ALLOSAUR)) {
            Item corpse = new Corpse("dead " + actor, CorpseType.ALLOSAUR);
            map.locationOf(actor).addItem(corpse);
        }
        else if (actor.hasCapability(Status.BRACHIOSAUR)){
            Item corpse = new Corpse("dead " + actor, CorpseType.BRACHIOSAUR);
            map.locationOf(actor).addItem(corpse);
        }
        else if (actor.hasCapability(Status.STEGOSAUR)){
            Item corpse = new Corpse("dead " + actor, CorpseType.STEGOSAUR);
            map.locationOf(actor).addItem(corpse);
        }
        else if (actor.hasCapability(Status.PTERODACTYL)){
            Item corpse = new Corpse("dead " + actor, CorpseType.PTERODACTYL);
            map.locationOf(actor).addItem(corpse);
        }

        Actions dropActions = new Actions();
        for (Item item : actor.getInventory())
            dropActions.add(item.getDropAction());
        for (Action drop : dropActions)
            drop.execute(actor, map);

        message = (actor + " is dead at (" + map.locationOf(actor).x() + ","
                + map.locationOf(actor).y() + ")");

        map.removeActor(actor);

        return message;
    }

    /**
     * Returns a descriptive string.
     * This returns null because dinosaur's die action is controlled by us (instead of user input),
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
