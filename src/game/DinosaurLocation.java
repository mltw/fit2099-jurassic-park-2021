package game;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class DinosaurLocation extends Location {

    public DinosaurLocation(GameMap map, int x, int y) {
        super(map, x, y);
    }

    /**
     * Called once per turn, so that Locations can experience the passage time. If that's
     * important to them.
     */
    @Override
    public void tick() {
        super.tick();

    }
}
