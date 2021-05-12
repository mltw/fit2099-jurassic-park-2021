package game.ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Lake extends Ground {
    private int counter = 0;
    private int sips;
    /**
     * Constructor.
     * All lake are represented by a '~' character.
     */
    public Lake(int sips) {
        super('~');
        this.sips = sips;

    }

    /**
     * Ground can also experience the joy of time.
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {

        // The amount of water that is added to the lake is calculated
        // by multiplying the rainfall by 20 sips where the rainfall
        // is a random value between 0.1 and 0.6 inclusive.

        super.tick(location);
        counter++;

        // rainfall
        double max = 0.7; // exclusive
        double min = 0.1;
        double rainfall = Math.random() *(max-min) + min;

        // Every 10 turns, probability of 20%, sky might rain which adds water to all lakes
        double rand = Math.random();
         if(counter%10==0 && rand <=0.2){

         }

    }
}
