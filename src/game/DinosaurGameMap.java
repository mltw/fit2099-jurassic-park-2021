package game;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.GroundFactory;
import edu.monash.fit2099.engine.Location;

import java.util.List;

/**
 * Create a map from dinosaurs.
 */
public class DinosaurGameMap extends GameMap {
    private boolean isRained = false;
    private int counter = 0;
    Display display = new Display();
    private String name = null;

    /**
     * Constructor that creates a map from a sequence of ASCII strings.
     *
     * @param groundFactory Factory to create Ground objects
     * @param lines         List of Strings representing rows of the map
     * @param name          Name of the map
     */
    public DinosaurGameMap(GroundFactory groundFactory, List<String> lines, String name) {
        super(groundFactory, lines);
        this.name = name;
    }

    /** Every 10 turns, with 20% probability, the sky will rain
     * Called once per turn, so that maps can experience the passage of time.
     */
    @Override
    public void tick() {

        counter++;
        isRained = false;
        double rand = Math.random();
        if(counter%10==0 && rand <=0.2){ // sky rained
            isRained = true;
        }
        super.tick();
    }

    /**
     * Creates a new Location.
     *
     * Override this method if you want a map based around different Location types.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @return a new Location.
     */
    @Override
    protected Location makeNewLocation(int x, int y) {
        return new DinosaurLocation(this,x,y);
    }

    /** Getter
     * Return if the map rained.
     * @return true if map rained, otherwise false.
     */
    public boolean isRained() {
        return isRained;
    }

    /** Setter
     * Update if the map rained.
     * @param rained true if probability of raining met, otherwise false.
     */
    public void setRained(boolean rained) {
        isRained = rained;
    }

    /**
     * Getter
     * Returns the name of the map (used to differentiate between gameMap1 and gameMap2)
     * @return the name of the map
     */
    public String getName() {
        return name;
    }
}
