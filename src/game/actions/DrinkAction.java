package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.dinosaurs.Dinosaur;
import game.ground.Lake;
import game.ground.Status;

/**
 * Action for dinosaurs to drink water from lake.
 */
public class DrinkAction extends Action {
    private int drinkVolume;

    /**
     * Perform the drinking Action.
     * When this method is called: it means dinosaur found an adjacent lake & it is thirsty
     * Different dinosaur has different water level that it can consume once
     * Eg: Stegosaur & Allosaur: each sip will increase water level by 30
     *     Brachiosaur         : each sip will increase water level by 80
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
        message += (actor + " at ("+map.locationOf(actor).x() + "," +
                    map.locationOf(actor).y()+")"+ " drank water in adjacent square" + '\n');

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
