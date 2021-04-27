package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.DinosaurInterface;
import game.Behaviour;
import game.WanderBehaviour;

/**
 * A carnivore dinosaur.
 */
public class Allosaur extends Actor implements DinosaurInterface {

    // Will need to change this to a collection if Allosaur gets additional Behaviours.
    private Behaviour behaviour;

    /**
     * Constructor.
     * All Allosaurs are represented by an 'a' and have 100 hit points.
     *
     * @param name the name of the Allosaur
     */
    public Allosaur(String name) {
        super(name, 'a', 100); //to be edited

        behaviour = new WanderBehaviour();
    }



    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    @Override
    public int getFoodLevel() {
        return 0;
    }

    @Override
    public int getUnconsciousCount() {
        return 0;
    }

    @Override
    public int getPregnantCount() {
        return 0;
    }

    @Override
    public boolean isPregnant() {
        return false;
    }

    @Override
    public void eat(Item item) {

    }

    @Override
    public void breed() {

    }
}
