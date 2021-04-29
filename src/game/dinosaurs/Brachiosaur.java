package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {

    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'b' and have 100 hit points.
     *
     * @param name the name of the Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'b', 100); //to be edited

        setBehaviour(new WanderBehaviour());
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

//    @Override
//    public int getFoodLevel() {
//        return hitPoints;
//    }

}
