package game.ground;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
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
        double rand = Math.random();
         if(counter%10==0 && rand <=0.2){ // sky rained
             sips = (int) (rainfall*20 + sips);
             display.println("Sky rained! Amount of sips now is: " + sips); // testing
         }

    }

    public int getSips() {
        return sips;
    }

    public void setSips(int sips) {
        this.sips = sips;
    }
}
