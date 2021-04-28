package game.ground;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {

	public Dirt() {

		super('.');
		addCapability(Status.DEAD);
	}
	// at the beginning of game & at the beginning of each turn
	// each square of dirt has a 1% chance to grow a bush

	/**
	 * Ground can also experience the joy of time.
	 *
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		super.tick(location);
		boolean growBushSmallPossibility = new Random().nextInt(200)==0;
		boolean growBushPossibility = new Random().nextInt(10)==0;
		if (growBushSmallPossibility && ((checkNearbySquares(location.x(),location.y(), location.map(), '+')==0)
				&&(checkNearbySquares(location.x(),location.y(), location.map(), 't')==0)
				&& (checkNearbySquares(location.x(),location.y(), location.map(), 'T')==0))){
			// 1%
			location.setGround(new Bush());
		}
		else if(checkNearbySquares(location.x(),location.y(), location.map(), 'v')>=2 && growBushPossibility){
			// 10% to grow bush
			location.setGround(new Bush());
		}
//		else if(checkAdjacentAndNearbySquares(location.x(),location.y(), location.map(), 'v')>=1){
//			// if any square of dirt next to a tree --> 0% to grow bush
//
//		}
//
	}

	public int checkNearbySquares(int bushLocationX, int bushLocationY, GameMap map,
								  char chr) {
		int counter = 0;
		for (int x = bushLocationX - 1; x <= (bushLocationX + 1); x++) {
			for (int y = bushLocationY - 1; y <= (bushLocationY + 1) ;y++) {
//			for (int x = bushLocationX - 1; x <= (bushLocationX + 1); x++) {
				try{
					if (map.at(x, y).getGround().getDisplayChar() == chr) {
						counter++;
					}
				}
				catch (Exception e){
					;
				}
			}
		}
		return counter;
	}
}
