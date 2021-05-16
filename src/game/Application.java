package game;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.engine.*;
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

	/**
	 * the original, old game map
	 */
	private static DinosaurGameMap gameMap;

	/**
	 * the new game map
	 */
	private static DinosaurGameMap gameMap2;

	/**
	 * number of moves the user is allowed to make in a game
	 */
	private static int numberOfMoves;

	/**
	 * amount of eco points the player has to make within 'numberOfMoves' moves
	 */
	private static int numberOfEcopoints;

	/**
	 * the number of moves the player has made
	 */
	private static int playerNumberOfMoves;

	/**
	 * an integer to represent the game mode (1 for Challenge mode, 2 for Sandbox mode)
	 */
	private static int gameMode;

	public static void main(String[] args) {
		boolean flag;
		do {
			flag = false;
			Display display = new Display();

			display.println("Choose your mode: ");
			display.println("1. I'm in for a Challenge!");
			display.println("2. I want to chill, Sandbox");
			display.println("3. A lil too tired.. Exit");
			display.println("Your option: ");
			char playerOption = display.readChar();

			switch (playerOption) {
				case '1': // Challenge mode
					Application.runGame(1);
					break;
				case '2': // Sandbox mode
					Application.runGame(2);
					break;
				case '3': // Exit
					display.println("Thanks for playing. Take care and stay safe.");
					flag = true;
					break;
				default:
					display.println("Invalid option, try again");
					break;
			}
		}

		while (!flag);
	}

	/**
	 * Runs the game based on the game mode.
	 * @param gameMode an integer indicating which game mode it is.
	 *                 <br> 1 for Challenge Mode, 2 for Sandbox mode.
	 */
	public static void runGame(int gameMode){
		Display display = new Display();
		// use a scanner to read in user input of more than 1 character, eg number of moves
		// can easily have two or more digits
		Scanner scanner = new Scanner(System.in);

		Application.gameMode = gameMode;

		if (gameMode == 1){
			Application.playerNumberOfMoves = 0;

			boolean flag;
			do {
				flag = false;
				try {
					// input eco points
					display.println("Enter eco points goal: ");
					int ecoGoal = scanner.nextInt();
					if (ecoGoal<=0){
						throw new Exception("");
					}
					else
						Application.numberOfEcopoints = ecoGoal;

					// input number of moves
					display.println("Enter number of moves allowed: ");
					int moveGoal = scanner.nextInt();
					if (moveGoal<=0){
						throw new Exception("");
					}
					else
						Application.numberOfMoves = moveGoal;

					flag=true;
				}
				catch (Exception e) {
					display.println("Only accepts integer >0. Try again");
				}
			}
			while (!flag);

			display.println("CHALLENGE MODE BEGINS! GOOD LUCK HAVE FUN!");
			display.println("=======================================================================================");
		}
		else if (gameMode == 2){
			Application.playerNumberOfMoves = 0;
			Application.numberOfEcopoints = (int) Double.POSITIVE_INFINITY;
			Application.numberOfMoves = (int) Double.POSITIVE_INFINITY;

			display.println("SANDBOX MODE BEGINS! HAVE FUN!");
			display.println("=======================================================================================");
		}
		DinosaurWorld world = new DinosaurWorld(new Display());

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
		gameMap = new DinosaurGameMap(groundFactory, map, "gameMap1");
		world.addGameMap(gameMap);

		List<String> map2 = Arrays.asList(
				"................................................................................",
				"................................................................................",
				"................................................................................",
				".........+++....................................................................",
				"............++..................................................................",
				"..........+.....................................................................",
				"................................................................................",
				"................................................................................",
				".........................................++.....................................",
				"...................................++..+........................................",
				".....................................+++........................................",
				"......................................+++.......................................",
				".....................................+++........................................",
				"................................................................................",
				"............+++.................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				".........................................................................++.....",
				"........................................................................++.++...",
				".........................................................................++++...",
				"..........................................................................++....",
				"................................................................................");
		gameMap2 = new DinosaurGameMap(groundFactory, map2, "gameMap2");
		world.addGameMap(gameMap2);

		Actor player = new Player("Player", '@', 100);

		// testing
//		List<String> map = Arrays.asList(
//				"#.........+",
//				"...........",
//				"...........");
//		gameMap = new DinosaurGameMap(groundFactory, map, "gameMap1");
//		world.addGameMap(gameMap);


		world.addPlayer(player, gameMap.at(9, 4));

		// testing
//		world.addPlayer(player, gameMap.at(1, 1)); //kx
//		gameMap.at(2, 1).addActor(new Stegosaur(Status.ADULT));
//		for (int i = 0; i < 5; i+=2) {
//			for (int j = 0; j < 1; j++) {
//				Ground ground = gameMap.at(i, j).getGround();
//				if (ground.hasCapability(game.ground.Status.DIRT)) {
//					gameMap.at(i, j).setGround(new Lake(25));
//					for (int k=0;k<5;k++) {
//						Lake lake = (Lake)gameMap.at(i,j).getGround();
//						lake.setFishCount(lake.getFishCount()+1);
//						gameMap.at(i, j).addItem(new Fish("fish", 'h'));
//					}
//				}
//			}
//		}



		// add a vending machine in the map
		gameMap.at(12, 5).addItem(new VendingMachine());

		// placed 2 male 2 female brachiosaurs in the map
		Brachiosaur maleBrachiosaur1 = new Brachiosaur(Status.ADULT);
		maleBrachiosaur1.setGender("M");
		gameMap.at(20, 20).addActor(maleBrachiosaur1);

		Brachiosaur femaleBrachiosaur1 = new Brachiosaur(Status.ADULT);
		femaleBrachiosaur1.setGender("F");
		gameMap.at(22, 20).addActor(femaleBrachiosaur1);

		Brachiosaur maleBrachiosaur2 = new Brachiosaur(Status.ADULT);
		maleBrachiosaur2.setGender("M");
		gameMap.at(40, 20).addActor(maleBrachiosaur2);

		Brachiosaur femaleBrachiosaur2 = new Brachiosaur(Status.ADULT);
		femaleBrachiosaur2.setGender("F");
		gameMap.at(42, 20).addActor(femaleBrachiosaur2);


		// Place a pair of stegosaurs in the middle of the map
		gameMap.at(30, 12).addActor(new Stegosaur(Status.ADULT));
		gameMap.at(32, 12).addActor(new Stegosaur(Status.ADULT));

		// Place some pools of water in the map
		// Fish count will be updated accordingly(initial each lake has only 5 fish)
		for (int i = 10; i < 75; i += 10) {
			for (int j = 2; j < 25; j += 5) {
				Ground ground = gameMap.at(i, j).getGround();
				if (ground.hasCapability(game.ground.Status.DIRT)) {
					gameMap.at(i, j).setGround(new Lake(25));
					for (int k=0;k<5;k++) {
						Lake lake = (Lake)gameMap.at(i,j).getGround();
						gameMap.at(i, j).addItem(new Fish("fish", 'h'));
						lake.setFishCount(lake.getFishCount()+1);
					}
				}
			}
		}
		world.run();
	}

	/**
	 * A getter for the number of moves allowed in the game.
	 * @return number of moves allowed in the game.
	 */
	public static int getNumberOfMoves() {
		return numberOfMoves;
	}

	/**
	 * A getter for the number of eco points the player should earn/make
	 * @return number of eco points the player should earn/make
	 */
	public static int getNumberOfEcopoints() {
		return numberOfEcopoints;
	}

	/**
	 * A getter for the game mode
	 * @return an integer representing the game mode
	 */
	public static int getGameMode() {
		return gameMode;
	}

	/**
	 * A getter for the original, 1st game map
	 * @return the original, 1st game map
	 */
	public static DinosaurGameMap getGameMap() {
		return gameMap;
	}

	/**
	 * A getter for the new game map
	 * @return the new game map
	 */
	public static DinosaurGameMap getGameMap2() {
		return gameMap2;
	}

	/**
	 * A getter for the number of moves the player has made in the game
	 * @return the number of moves the player has made in the game
	 */
	public static int getPlayerNumberOfMoves() {
		return playerNumberOfMoves;
	}

	/**
	 * A setter for the number of moves the player has made in the game
	 * @param playerNumberOfMoves the number of moves the player has made in the game
	 */
	public static void setPlayerNumberOfMoves(int playerNumberOfMoves) {
		Application.playerNumberOfMoves = playerNumberOfMoves;
	}
}
