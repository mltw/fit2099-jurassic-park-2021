package game;

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
    /**
     * Constructor that creates a map from a sequence of ASCII strings.
     *
     * @param groundFactory Factory to create Ground objects
     * @param lines         List of Strings representing rows of the map
     */
    public DinosaurGameMap(GroundFactory groundFactory, List<String> lines) {
        super(groundFactory, lines);
    }

    /**
     * Called once per turn, so that maps can experience the passage of time.
     */
    @Override
    public void tick() {
        super.tick();
        isRained =false; // reset
        counter++;
        double rand = Math.random();
        if(counter%10==0 && rand <=0.2){ // sky rained
            isRained = true;
        }
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

    public boolean isRained() {
        return isRained;
    }

    public void setRained(boolean rained) {
        isRained = rained;
    }
}
