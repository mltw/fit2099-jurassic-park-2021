package game.ground;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.portableItems.Fruit;


/**
 * A class that represents bush.
 */
public class Bush extends Ground {
    /**
     * Constructor.
     * All bushes are represented by a 'v' character.
     * Bush has a Capability of being ALIVE, since it can grow fruits on it.
     */
    public Bush() {
        super('v');
        addCapability(Status.ALIVE);
    }

    /**
     * Override this to implement impassable terrain, or terrain that is only passable if conditions are met.
     *
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * Ground can also experience the joy of time.
     * In any turn, each square has 10% to produce 1 ripe fruit.
     * Fruits produced from bush will be on ground, therefore it has a Capability of
     * being ON_GROUND.
     * This produced fruit will be added to the current location.
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        double rand = Math.random();
        if(rand <= 0.1){
            Fruit item = new Fruit("fruit" , 'f');
            item.addCapability(Status.ON_GROUND); // check
            location.addItem(item);
        }
    }
}
