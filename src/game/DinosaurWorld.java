package game;

import edu.monash.fit2099.engine.*;

/**
 * A class to extend the 'World' class in the 'engine' package.
 */
public class DinosaurWorld extends World {
    /**
     * An indicator whether the player has chosen to exit the game.
     */
    private static boolean playerExits = false;

    /**
     * Constructor.
     *
     * @param display the Display that will display this World.
     */
    public DinosaurWorld(Display display) {
        super(display);
    }

    /**
     * Run the game.
     *
     * On each iteration the gameloop does the following: - displays the player's
     * map - processes the actions of every Actor in the game, regardless of map
     *
     * We could either only process the actors on the current map, which would make
     * time stop on the other maps, or we could process all the actors. We chose to
     * process all the actors.
     *
     * For Player instance, if the player has chosen to exit the game, we break the loop i.e.
     * make the game end. Otherwise, we also check whether the number of moves and eco points has
     * reached the target, and act accordingly to each situation (end the game/continue).
     *
     * @throws IllegalStateException if the player doesn't exist
     */
    @Override
    public void run() {
        if (player == null)
            throw new IllegalStateException();

        // initialize the last action map to nothing actions;
        for (Actor actor : actorLocations) {
            lastActionMap.put(actor, new DoNothingAction());
        }

        // This loop is basically the whole game
        while (stillRunning()) {
            // if player chooses to exit the game
            if (playerExits) {
                display.println("You've decided to exit the game.");
                break;
            }

            // if player has reached the number of moves allowed, check whether he has earned enough eco points
            if (Application.getPlayerNumberOfMoves() >= Application.getNumberOfMoves()){
                if (Player.getEcoPoints() >= Application.getNumberOfEcopoints()){
                    display.println(Player.getEcoPoints()+"");
                    display.println(Application.getNumberOfEcopoints()+"");
                    display.println("Congratulations! You've reached the target eco points and won the game!");
                }
                else{
                    display.println("Oh no, you haven't reached the target eco points. Better luck next time!");
                }
                break;
            }
            // if player hasn't reached max number of moves, check whether its eco points has reached the target
            else if (Player.getEcoPoints() >= Application.getNumberOfEcopoints()){
                display.println(Player.getEcoPoints()+"");
                display.println(Application.getNumberOfEcopoints()+"");
                display.println("Congratulations! You've reached the target eco points and won the game!");
                break;
            }

            GameMap playersMap = actorLocations.locationOf(player).map();
            playersMap.draw(display);

            // Process all the actors.
            for (Actor actor : actorLocations) {
                if (stillRunning())
                    processActorTurn(actor);
            }

            // Tick over all the maps. For the map stuff.
            for (GameMap gameMap : gameMaps) {
                gameMap.tick();
            }

        }
        display.println(endGameMessage());
        display.println("================================================================");
    }

    /**
     * A setter to indicate whether the player has chosen to exit the game.
     * @param playerExits true if player wants to exit; false otherwise.
     */
    public static void setPlayerExits(boolean playerExits) {
        DinosaurWorld.playerExits = playerExits;
    }
}
