package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;
import game.dinosaurs.Status;
import game.dinosaurs.Stegosaur;
import game.ground.Dirt;
import game.ground.Tree;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree());
//		List<String> map = Arrays.asList(
//				"+##.+.",
//				".....+",
//				".....+");
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		".....#######....................................................................",
		".....#_____#....................................................................",
		".....#_____#....................................................................",
		".....###.###....................................................................",
		"................................................................................",
		"......................................+++.......................................",
		".......................................++++.....................................",
		"...................................+++++........................................",
		".....................................++++++.....................................",
		"......................................+++.......................................",
		".....................................+++........................................",
		"................................................................................",
		"............+++.................................................................",
		".............+++++..............................................................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap gameMap = new DinosaurGameMap(groundFactory, map );
		world.addGameMap(gameMap);
		
		Actor player = new Player("Player", '@', 100);
//		// testing
//		world.addPlayer(player, gameMap.at(0, 1));
//		gameMap.at(2, 1).addActor(new Stegosaur(Status.ADULT));

		world.addPlayer(player, gameMap.at(9, 4));
		// add a vending machine in the map
		gameMap.at(8,4).addItem(new VendingMachine());

		// add 2 male 2 female brachiosaur in the map

		// Place a pair of stegosaurs in the middle of the map

		// 30/4: removed name of stegosaurs, to be auto-generated with a unique count number,
		// and used enum status to differentiate baby/adult dinosaurs
		gameMap.at(30, 12).addActor(new Stegosaur(Status.ADULT));
		gameMap.at(32, 12).addActor(new Stegosaur(Status.ADULT));

			
		world.run();
	}
}
