package game.ground;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	/**
	 * A boolean used for internal code.
	 */
	private boolean read = false;

	/**
	 * Constructor
	 * All dirt are represented by a '.' character.
	 * Since dirt are not able to grow fruits, there it's Capability is set to DEAD.
	 */
	public Dirt() {
		super('.');
		addCapability(Status.DEAD);
		addCapability(Status.DIRT);
	}

	/**
	 * Ground can also experience the joy of time.
	 * Here, we have 3 possibilities which will happen in any turn, on any square of dirt:
	 * - each square of dirt has 0.5% chances to grow a bush
	 * - any square of dirt that is next to at least 2 square of bushes has a 10% to grow a bush.
	 * - any square of dirt that is next to a tree:
	 * Ground will be converted from Dirt to Bush if it met the conditions above.
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		read = !read;
		if (read) {
			int aliveBushNeighbours = aliveBushCount(location);
			int aliveTreeNeighbours = aliveTreeCount(location);

			// each square of dirt has a 0.5% chance to grow a bush
			boolean growBushSmallPossibility = new Random().nextInt(200) == 0;
			// any square of dirt next to >=2 bushes have 10% chance to grow a bush
			boolean growBushPossibility = new Random().nextInt(10) == 0;

			// handle cases: where there is 10% chance to grow a bush
			if ((aliveBushNeighbours >= 2 && aliveTreeNeighbours < 1) && growBushPossibility &&!location.getGround().hasCapability(Status.LAKE)) {
				location.setGround(new Bush());
			}
			// handles cases: where bush are unable to grow due to existence of tree & 0.5% chance to grow a bush
			else if (aliveTreeNeighbours < 1 && growBushSmallPossibility && !location.getGround().hasCapability(Status.LAKE)) {
				location.setGround(new Bush());
			}
		}
	}

	/** This method is used to calculate the number of alive Bush in the adjacent squares of current location.
	 *
	 * @param location The location of the Ground
	 * @return An integer represents the total number of alive bush in the adjacent square of the current location.
	 */
	private int aliveBushCount(Location location) {
		return (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasCapability(Status.ALIVE) && ground.getDisplayChar()=='v').count();
	}

	/** This method is used to calculate the number of alive Tree in the adjacent squares of current location.
	 *
	 * @param location The location of the Ground
	 * @return An integer represents the total number of alive tree in the adjacent square of the current location.
	 */
	private int aliveTreeCount(Location location) {
		return (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasCapability(Status.ALIVE) && ((ground.getDisplayChar() == '+') ||
						(ground.getDisplayChar() == 't') ||
						(ground.getDisplayChar() == 'T'))).count();
	}
}
