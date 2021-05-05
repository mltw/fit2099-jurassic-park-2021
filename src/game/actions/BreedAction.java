package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;

/**
 * Action for dinosaurs to breed with each other.
 */
public class BreedAction extends Action {

    /**
     * The Dinosaur actor that is to be mated with
     */
    private Dinosaur target;

    /**
     * Constructor.
     *
     * @param target the Dinosaur to mate with
     */
    public BreedAction(Dinosaur target) {
        this.target = target;
    }

    /**
     * Two dinosaurs breed.
     *
     * @param actor The dinosaur actor performing the action.
     * @param map The map the dinosaur actor is on.
     * @return a message saying which dinosaur is pregnant after successfully mating.
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        if (((Dinosaur) actor).getGender().equals("F")){
            ((Dinosaur) actor).setPregnant(true);
            ((Dinosaur) actor).setPregnantCount(1);
            return actor + "is pregnant now";
        }
        else{
            target.setPregnant(true);
            target.setPregnantCount(1);
            return target + "is pregnant now";
        }
    }

    /**
     * Returns a descriptive string.
     * This returns null because dinosaur's breeding action is controlled by us, (instead of user input),
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
