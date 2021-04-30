package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;

/**
 * Action for dinosaurs to breed with each other.
 */
public class BreedAction extends Action {
    Dinosaur target;

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
            return menuDescription(actor);
        }
        else{
            target.setPregnant(true);
            target.setPregnantCount(1);
            return menuDescription(target);
        }
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor that becomes pregnant after successfully mating.
     * @return a String, e.g. "Allosaur1 is pregnant now".
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + "is pregnant now";
    }
}
