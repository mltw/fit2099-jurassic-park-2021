package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {

    private static int brachiosaurCount = 1;

    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'b' and have 100 hit points.
     */
    public Brachiosaur(Enum status) {
        super("Brachiosaur" + brachiosaurCount, 'b', 100);
        addCapability(status);

        if(hasCapability(Status.BABY)) {
            this.setBabyCount(1);
            this.setHitPoints(10); //if is baby, overwrite its hit points
        }

        brachiosaurCount++;
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
