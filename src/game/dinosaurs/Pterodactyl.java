package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.actions.LayEggAction;

public class Pterodactyl extends Dinosaur{
    private static int pterodactylCount = 1;       // used to give a unique name for each Pterodactyl

    /**
     * Constructor.
     * All Pterodactyls are represented by a 'p' and have 50 hit points initially.
     * Their name is represented as "Pterodactyl" followed by a unique number, eg "Pterodactyl1".
     *
     * @param status an Enum value representing its maturity status (BABY/ADULT)
     * @see Status
     */
    public Pterodactyl(Enum status) {
        super("Pterodactyl"+pterodactylCount, 'p', 50);
        addCapability(status);
        addCapability(Status.PTERODACTYL);
        maxHitPoints = 100;

        if(hasCapability(Status.BABY)) {
            this.setBabyCount(1);
            this.setHitPoints(10); 		//if is baby, starting hit points is 10
        }

        pterodactylCount++;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action action = null;
        eachTurnUpdates(30);

        int pterodactylLocationX = map.locationOf(this).x();
        int pterodactylLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();
        }

        // Egg can only be laid on a tree
        if (this.getPregnantCount() >= 10 && here.getGround().hasCapability(game.ground.Status.TREE)) {
            return new LayEggAction();
        }

        return action;
    }
}
