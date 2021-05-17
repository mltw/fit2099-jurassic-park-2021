package game;

import edu.monash.fit2099.engine.*;
import game.ground.Status;
import game.portableItems.ItemType;

/**
 * A class that finds the nearest food source and moves the actor one step
 * closer to that food source
 *
 * @see MoveActorAction
 */
public class SearchNearestFoodBehaviour implements Behaviour{


    /**
     * Returns a MoveActorAction to the next adjacent location of the actor, towards the nearest food source.
     * Will check if the food source is suitable for the actor to eat.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return an Action, or null if no food source is available
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        int actorLocationX = map.locationOf(actor).x();
        int actorLocationY = map.locationOf(actor).y();
        int minNumberOfSteps = (int) Double.POSITIVE_INFINITY;
        Action moveToFoodAction = null;

        for (int x : map.getXRange()){
            for (int y : map.getYRange()){
                Location there = map.at(x,y);
                int stepsToThere = Math.abs(actorLocationX - x) + Math.abs(actorLocationY - y);
                for (Item item : there.getItems()){

                    // case Stegosaur
                    if (actor.hasCapability(game.dinosaurs.Status.STEGOSAUR)) {
                        if (item.hasCapability(Status.ON_GROUND)
                                && item.hasCapability(ItemType.FRUIT)) {

                            // if found another nearer food source
                            if (stepsToThere < minNumberOfSteps) {
                                minNumberOfSteps = stepsToThere;

                                moveToFoodAction = new MoveActorAction(
                                        findNextLocationToMove(actorLocationX, actorLocationY, x, y, map),
                                        "to nearest food source");
                            }
                        }
                    }
                    // case Brachiosaur
                    else if (actor.hasCapability(game.dinosaurs.Status.BRACHIOSAUR)) {
                        if (item.hasCapability(Status.ON_TREE)
                                && item.hasCapability(ItemType.FRUIT)) {

                            // if found another nearer food source
                            if (stepsToThere < minNumberOfSteps) {
                                minNumberOfSteps = stepsToThere;

                                moveToFoodAction = new MoveActorAction(
                                        findNextLocationToMove(actorLocationX, actorLocationY, x, y, map),
                                        " to nearest food source");
                            }
                        }
                    }
                    // case Allosaur
                    else if (actor.hasCapability(game.dinosaurs.Status.ALLOSAUR)) {
                        if ((item.hasCapability(ItemType.EGG) || item.hasCapability(ItemType.CORPSE))
                            || (there.containsAnActor()
                                    && there.getActor().hasCapability(game.dinosaurs.Status.PTERODACTYL))
                                    && there.getActor().hasCapability(game.dinosaurs.Status.ON_LAND)){

                            // if found another nearer food source
                            if (stepsToThere < minNumberOfSteps) {
                                minNumberOfSteps = stepsToThere;
                                Location locationToMove =findNextLocationToMove(actorLocationX, actorLocationY, x, y, map);
                                if ((locationToMove.getGround()).canActorEnter(actor)) {
                                    moveToFoodAction = new MoveActorAction(locationToMove, " to nearest food source");
                                }
                            }
                        }
                    }
                    // case Pterodactyl
                    else if (actor.hasCapability(game.dinosaurs.Status.PTERODACTYL)) {
                        if (item.hasCapability(ItemType.EGG) || item.hasCapability(ItemType.CORPSE)
                                || item.hasCapability(ItemType.FISH)) {

                            // if found another nearer food source
                            if (stepsToThere < minNumberOfSteps) {
                                minNumberOfSteps = stepsToThere;
                                Location locationToMove =findNextLocationToMove(actorLocationX, actorLocationY, x, y, map);
                                if ((locationToMove.getGround()).canActorEnter(actor)) {
                                    moveToFoodAction = new MoveActorAction(locationToMove, " to nearest food source");
                                }
                            }
                        }
                    }
                }
            }
        }
        return moveToFoodAction;
    }

    /**
     * Finds the next location, adjacent to the actor, that is one step nearer to the food source
     *
     * @param actorX X coordinate of the actor
     * @param actorY Y coordinate of the actor
     * @param destinationX X coordinate of the nearest food source
     * @param destinationY Y coordinate of the nearest food source
     * @param map the game map
     * @return the next location for the actor to be at (its an adjacent square since actors can only move
     *          one square at a time)
     */
    public Location findNextLocationToMove(int actorX, int actorY, int destinationX, int destinationY, GameMap map){
        int nextX;
        int nextY;

        // find next X location to move
        if (destinationX > actorX)
            nextX = actorX + 1;
        else if (destinationX == actorX)
            nextX = actorX;
        else
            nextX = actorX - 1;

        // find next Y location to move
        if (destinationY > actorY)
            nextY = actorY + 1;
        else if (destinationY == actorY)
            nextY = actorY;
        else
            nextY = actorY - 1;

        return map.at(nextX,nextY);
    }
}
