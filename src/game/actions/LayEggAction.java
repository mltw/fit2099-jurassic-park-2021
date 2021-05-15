package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;
import game.dinosaurs.Status;
import game.portableItems.Egg;
import game.portableItems.EggType;

/**
 * Action for Dinosaurs to lay an egg.
 */
public class LayEggAction extends Action {

    /**
     * Perform the lay egg action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of which dinosaur laid an egg at the location.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        ((Dinosaur) actor ).setPregnant(false);
        ((Dinosaur) actor ).setPregnantCount(0);

        if (actor.hasCapability(Status.ALLOSAUR)){
            map.locationOf(actor).addItem(new Egg(EggType.ALLOSAUR));
        }
        else if (actor.hasCapability(Status.BRACHIOSAUR)){
            map.locationOf(actor).addItem(new Egg(EggType.BRACHIOSAUR));
        }
        else if (actor.hasCapability(Status.STEGOSAUR)){
            map.locationOf(actor).addItem(new Egg(EggType.STEGOSAUR));
        }
        else if (actor.hasCapability(Status.PTERODACTYL)){
            map.locationOf(actor).addItem(new Egg(EggType.PTERODACTYL));
        }

        return actor + " just laid an egg at (" + map.locationOf(actor).x() + "," +map.locationOf(actor).y() +")!";

    }

    /**
     * Returns a descriptive string.
     * This returns null because dinosaur's lay egg action is controlled by us,
     * therefore no need of having a menu description.
     *
     * @param actor The actor performing the action.
     * @return the text we put on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        return null ;
    }
}
