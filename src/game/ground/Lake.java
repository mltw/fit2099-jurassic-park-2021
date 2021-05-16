package game.ground;

import edu.monash.fit2099.engine.*;
import game.Application;
import game.DinosaurGameMap;
import game.dinosaurs.Dinosaur;
import game.portableItems.Fish;

/** A class that represents lake.
 *
 */
public class Lake extends Ground {
    private int counter = 0;
    private int sips;
    Display display = new Display();
    private int fishCount = 0;

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

    /** Every 10 turns, probability of 20%, sky might rain which adds water to all lakes
     *
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        // access fish count
//        Fish item = (Fish) location.getItems().get(location.getItems().size()-1);
        super.tick(location);
        counter++;

        // rainfall
        double max = 0.7; // exclusive
        double min = 0.1;
        double rainfall = Math.random() *(max-min) + min;

        if (((DinosaurGameMap)location.map()).isRained()&&counter%10==0){
            sips = (int) (rainfall*20 + sips);
            display.println("Sky rained! Amount of possible sips now is: " + sips); // testing
        }

        double fishProbability = Math.random();
        if (fishCount<25 && fishProbability >=0.6){
            Fish newFish = new Fish("fish",'h');
            location.addItem(newFish);
            fishCount++;
            display.println("Lake has " + fishCount + " fish!");                // testing
        }
        else if (fishCount==25){
            display.println("This lake has reached the maximum of 25 fish!"); // testing
        }

    }

    public int getFishCount() {
        return fishCount;
    }

    public void setFishCount(int fishCount) {
        this.fishCount = fishCount;
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
