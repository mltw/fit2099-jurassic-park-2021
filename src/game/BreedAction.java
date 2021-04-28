package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action for dinosaurs to breed with each other.
 */
public class BreedAction extends Action {
    Actor current;
    Actor target;

    public BreedAction(Actor current, Actor target) {

    }

    /**
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
//    @Override
//    public String execute(Actor actor, GameMap map) {
//        actor.removeItemFromInventory(item);
//        map.locationOf(actor).addItem(item);
//        return menuDescription(actor);
//    }
//
//    /**
//     * A string describing the action suitable for displaying in the UI menu.
//     *
//     * @param actor The actor performing the action.
//     * @return a String, e.g. "Player drops the potato"
//     */
//    @Override
//    public String menuDescription(Actor actor) {
//        return actor + " drops the " + item;
//    }