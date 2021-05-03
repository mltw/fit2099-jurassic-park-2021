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
	private boolean read = false;

	public Dirt() {

		super('.');
		addCapability(Status.DEAD);
	}

	/**
	 * Ground can also experience the joy of time.
	 *
	 * @param location The location of the Ground
	 */
	@Override
	// update: 30/4
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

			if ((aliveBushNeighbours >= 2 && aliveTreeNeighbours < 1) && growBushPossibility) {
				location.setGround(new Bush());
			}
			else if (aliveTreeNeighbours < 1 && growBushSmallPossibility) {
				location.setGround(new Bush());
			}
		}
	}
	private int aliveBushCount(Location location) {
		return (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasCapability(Status.ALIVE) && ground.getDisplayChar()=='v').count();
	}

	private int aliveTreeCount(Location location) {
		return (int) location.getExits().stream().map(exit -> exit.getDestination().getGround())
				.filter(ground -> ground.hasCapability(Status.ALIVE) && ((ground.getDisplayChar() == '+') ||
						(ground.getDisplayChar() == 't') ||
						(ground.getDisplayChar() == 'T'))).count();
	}
}
