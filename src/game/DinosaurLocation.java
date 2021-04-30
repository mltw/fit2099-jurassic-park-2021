package game;

import edu.monash.fit2099.demo.conwayslife.ConwayLocation;
import edu.monash.fit2099.demo.conwayslife.Status;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.ground.Bush;

import java.util.Random;

public class DinosaurLocation extends Location {
    private boolean read = false;
    private DinosaurLocation.NextTurn action = DinosaurLocation.NextTurn.SAME;

    public DinosaurLocation(GameMap map, int x, int y) {
        super(map, x, y);
    }

    /**
     * Called once per turn, so that Locations can experience the passage time. If that's
     * important to them.
     */
    @Override
    public void tick() {
        read = !read;
        if (read) {
            int aliveBushNeighbours = aliveBushCount();
            int aliveTreeNeighbours = aliveTreeCount();
            boolean aliveHere = getGround().hasCapability(Status.ALIVE);
            // each square of dirt has a 0.5% chance to grow a bush
            boolean growBushSmallPossibility = new Random().nextInt(200) == 0;
            // any square of dirt next to >=2 bushes have 10% chance to grow a bush
            boolean growBushPossibility = new Random().nextInt(10) == 0;
            if ((aliveBushNeighbours>=2 && aliveTreeNeighbours<1) && growBushPossibility){
                setGround(new Bush());
            }
            else if(aliveTreeNeighbours <1 && growBushSmallPossibility){
                setGround(new Bush());
            }
            super.tick();
        }
    }
    private int aliveBushCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasCapability(Status.ALIVE) && ground.getDisplayChar()=='v').count();
    }

    private int aliveTreeCount() {
        return (int) getExits().stream().map(exit -> exit.getDestination().getGround())
                .filter(ground -> ground.hasCapability(Status.ALIVE) && ((ground.getDisplayChar() == '+') ||
					(ground.getDisplayChar() == 't') ||
					(ground.getDisplayChar() == 'T'))).count();
    }

    private enum NextTurn {
        GROW, DIE, SAME
    }
}
