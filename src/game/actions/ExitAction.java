package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.DinosaurWorld;

/**
 * Action for Player to exit the current game.
 */
public class ExitAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {
        DinosaurWorld.setPlayerExits(true);
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Exit the game";
    }
}
