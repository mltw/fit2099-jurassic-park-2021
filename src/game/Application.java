package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;
import game.dinosaurs.Allosaur;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Status;
import game.dinosaurs.Stegosaur;
import game.ground.Dirt;
import game.ground.Lake;
import game.ground.Tree;
import game.portableItems.Fish;

/**
 * The main class for the Jurassic World game.
 *
 */
public class Application {

	public static void main(String[] args) {
		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Tree());
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

		world.addPlayer(player, gameMap.at(9, 4));
		// add a vending machine in the map
		gameMap.at(12,5).addItem(new VendingMachine());


		// placed 2 male 2 female brachiosaurs in the map
		Brachiosaur maleBrachiosaur1 = new Brachiosaur(Status.ADULT);
		maleBrachiosaur1.setGender("M");
		gameMap.at(20,20).addActor(maleBrachiosaur1);

		Brachiosaur femaleBrachiosaur1 = new Brachiosaur(Status.ADULT);
		femaleBrachiosaur1.setGender("F");
		gameMap.at(22,20).addActor(femaleBrachiosaur1);

		Brachiosaur maleBrachiosaur2 = new Brachiosaur(Status.ADULT);
		maleBrachiosaur2.setGender("M");
		gameMap.at(40,20).addActor(maleBrachiosaur2);

		Brachiosaur femaleBrachiosaur2 = new Brachiosaur(Status.ADULT);
		femaleBrachiosaur2.setGender("F");
		gameMap.at(42,20).addActor(femaleBrachiosaur2);


		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur(Status.ADULT));
		gameMap.at(32, 12).addActor(new Stegosaur(Status.ADULT));

		// Place some pools of water in the map
		gameMap.at(35,12).setGround(new Lake(25));
		gameMap.at(35,12).addItem(new Fish("fish",5));

		gameMap.at(39,20).setGround(new Lake(25));
		gameMap.at(39,20).addItem(new Fish("fish",5));

		gameMap.at(19,12).setGround(new Lake(25));
		gameMap.at(19,12).addItem(new Fish("fish",5));

		gameMap.at(50,19).setGround(new Lake(25));
		gameMap.at(50,19).addItem(new Fish("fish",5));

		world.run();
	}
}
