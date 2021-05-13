package game;

import edu.monash.fit2099.engine.*;
import game.ground.Status;

/**
 * A class that finds the nearest lake and moves the actor one step closer to that lake
 * @see MoveActorAction
 */

public class SearchNearestLakeBehaviour implements Behaviour{
    /**
     * Returns a MoveActorAction to the next adjacent location of the actor, towards the nearest lake
     * @param actor the Actor acting
     * @param map   the GameMap containing the Actor
     * @return an Action that actor can perform, or null if actor can't do this.
     * @see Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        int actorLocationX = map.locationOf(actor).x();
        int actorLocationY = map.locationOf(actor).y();
        int minNumberOfSteps = (int) Double.POSITIVE_INFINITY;
        Action moveToLakeAction = null;

        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Location there = map.at(x, y);
                int stepsToThere = Math.abs(actorLocationX - x) + Math.abs(actorLocationY - y);
                if (there.getGround().hasCapability(Status.LAKE)){
                    if (stepsToThere < minNumberOfSteps) {
                        minNumberOfSteps = stepsToThere;
                        Location locationToMove = findNextLocationToMove(actorLocationX, actorLocationY, x, y, map);
                        if ((locationToMove.getGround()).canActorEnter(actor)) {
                            moveToLakeAction = new MoveActorAction(locationToMove, " to nearest lake");
                        }
                    }
                }
            }
        }
        return moveToLakeAction;
    }

    /**
     * Finds the next location, adjacent to the actor, that is one step nearer to the lake
     *
     * @param actorX X coordinate of the actor
     * @param actorY Y coordinate of the actor
     * @param destinationX X coordinate of the nearest lake
     * @param destinationY Y coordinate of the nearest lake
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
