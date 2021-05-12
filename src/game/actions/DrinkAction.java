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

        if (actor.hasCapability(game.dinosaurs.Status.STEGOSAUR)|| actor.hasCapability(game.dinosaurs.Status.ALLOSAUR)) {
            drinkVolume = 30;  // water level that can be consumed: 30
            ((Dinosaur) actor).setWaterLevel(drinkVolume+ ((Dinosaur) actor).getWaterLevel());
        }
        else if(actor.hasCapability(game.dinosaurs.Status.BRACHIOSAUR)){
            drinkVolume = 80;  // water level that can be consumed: 80
            ((Dinosaur) actor).setWaterLevel(drinkVolume+ ((Dinosaur) actor).getWaterLevel());
        }

        // display message to console
        message += (actor + " drank water in adjacent" + '\n');

        // testing
        message += (((Dinosaur) actor)+" ,water level after drinking: " + ((Dinosaur) actor).getWaterLevel());


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
