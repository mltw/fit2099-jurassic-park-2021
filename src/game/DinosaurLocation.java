package game;

import edu.monash.fit2099.demo.conwayslife.ConwayLocation;
import edu.monash.fit2099.demo.conwayslife.Status;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.ground.Bush;

import java.util.Random;

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
