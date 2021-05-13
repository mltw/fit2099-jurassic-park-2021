package game.ground;

import edu.monash.fit2099.engine.*;
import game.Application;
import game.DinosaurGameMap;
import game.dinosaurs.Dinosaur;

public class Lake extends Ground {
    private int counter = 0;
    private int sips;
    Display display = new Display();

    /**
     * Constructor.
     * All lake are represented by a '~' character.
     */
    public Lake(int sips) {
        super('~');
        this.sips = sips;
        addCapability(Status.LAKE);
    }

    /**
     * Land-based creatures(Stegosaur, Brachiosaur, Allosaur) cannot enter lake
     *
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        boolean res;
        super.canActorEnter(actor);
        if (actor.hasCapability(game.dinosaurs.Status.STEGOSAUR)||
                actor.hasCapability(game.dinosaurs.Status.BRACHIOSAUR)||
                actor.hasCapability(game.dinosaurs.Status.ALLOSAUR)){
            res = false;
        }
        else{
            res = true;
        }
        return res;
    }

    /**
     * Ground can also experience the joy of time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {

        super.tick(location);
        counter++;

        // rainfall
        double max = 0.7; // exclusive
        double min = 0.1;
        double rainfall = Math.random() *(max-min) + min;

        // Every 10 turns, probability of 20%, sky might rain which adds water to all lakes
        if (((DinosaurGameMap)location.map()).isRained()&&counter%10==0){
            sips = (int) (rainfall*20 + sips);
            display.println("Sky rained! Amount of possible sips now is: " + sips); // testing
        }
    }

    /** Getter
     * Retrieve the number of sips the lake currently has.
     * @return the number of sips the lake currently has.
     */
    public int getSips() {
        return sips;
    }

    /** Setter
     * Sets the number of sips the lake current has.
     * @param sips number of sips to be updated.
     */
    public void setSips(int sips) {
        this.sips = sips;
    }

}
