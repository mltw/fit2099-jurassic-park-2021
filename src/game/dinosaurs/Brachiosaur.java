package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.DinosaurInterface;
import game.Behaviour;
import game.WanderBehaviour;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Actor implements DinosaurInterface {

    // Will need to change this to a collection if Brachiosaur gets additional Behaviours.
    private Behaviour behaviour;
    private int unconsciousCount;
    private int pregnantCount;
    private String gender;

    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'b' and have 100 hit points.
     *
     * @param name the name of the Brachiosaur
     */
    public Brachiosaur(String name) {
        super(name, 'b', 100); //to be edited

        behaviour = new WanderBehaviour();
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        return null;
    }

    @Override
    public int getFoodLevel() {
        return hitPoints;
    }

    @Override
    public int getUnconsciousCount() {
        return unconsciousCount;
    }

    @Override
    public int getPregnantCount() {
        return pregnantCount;
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
