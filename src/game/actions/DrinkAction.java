package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.dinosaurs.Dinosaur;
import game.ground.Lake;
import game.ground.Status;

public class DrinkAction extends Action {
    private int drinkVolume;
    /**
     * Perform the Action.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of what happened that can be displayed to the user.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String message = "";

        // access lake's water
        if (map.locationOf(actor).getGround().hasCapability(Status.LAKE)){
            Lake ground = (Lake) map.locationOf(actor).getGround();
//            if (ground.getSips()>=30){
//                drinkVolume = 30;
//            }
//            else{
//                drinkVolume = ground.getSips();
//            }
            // water level that can be consumed
            drinkVolume = Math.min(ground.getSips(),30);

            // update water level of lake
            ground.setSips(ground.getSips()-drinkVolume);

            // update water level of dinosaur after drank water
            ((Dinosaur) actor).setWaterLevel(drinkVolume);

            // display message to console
            message = (actor + " drank water at (" + map.locationOf(actor).x() + ","
                    + map.locationOf(actor).y() + ")");

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
