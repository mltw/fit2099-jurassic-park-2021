package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.DinosaurGameMap;
import game.actions.DieAction;
import game.actions.DrinkAction;
import game.actions.EatAction;
import game.actions.LayEggAction;
import game.ground.Lake;
import game.portableItems.Fish;
import game.portableItems.Fruit;
import game.portableItems.ItemType;

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
        boolean displayedHungry = false;	// reset
        boolean moved = false; 				// reset
        Action actionBreed = null; 		// reset
        boolean eaten = false; 				// reset
        boolean displayThirsty = false; 	// reset

        eachTurnUpdates(30);

        int pterodactylLocationX = map.locationOf(this).x();
        int pterodactylLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {
            Location destination = exit.getDestination();


            // Egg can only be laid on a tree
            if (this.getPregnantCount() >= 10 && here.getGround().hasCapability(game.ground.Status.TREE)) {
                return new LayEggAction();
            }

            else if (this.getWaterLevel() < 40) {
                // display thirsty message
                if (!displayThirsty) {
                    if (this.getWaterLevel() < 40 && this.getWaterLevel() != 0) {
                        display.println(this + " at (" + pterodactylLocationX + "," + pterodactylLocationY + ") is getting thirsty!");
                        display.println("Water level is " + this.getWaterLevel());
                    }
                    // display unconscious message
                    else if (this.getWaterLevel() == 0 && this.getUnconsciousCount() < 15) {
                        if (((DinosaurGameMap) map).isRained()) {
                            this.setWaterLevel(10);
                            this.setUnconsciousCount(0);
                        } else {
                            display.println(this + " at (" + pterodactylLocationX + "," + pterodactylLocationY + ") is unconscious! Get water for it!");
                            display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                        }
                    }
                    displayThirsty = true;
                }
                if (destination.getGround().hasCapability(game.ground.Status.LAKE)) {
                    Lake ground = (Lake) destination.getGround();
                    if (ground.getSips() > 0) {
                        ground.setSips(ground.getSips() - 1); // one turn == one sip(one sip == 30 water level)
                        display.println("After drinking, sip now is: " + ground.getSips()); // testing
                        action = new DrinkAction();
                    }
                    // if empty(sip==0): lose ability to be drunk by stegosaur
                } else if (this.getUnconsciousCount() == 15) {
                    return new DieAction();
                }

            }

            // if not thirsty, then search for food if hungry & NOT thirsty
            else if (this.getHitPoints() < 90 && this.getWaterLevel()>=40) {
                // display hungry message
                if (!displayedHungry){
                    if (this.isConscious()) {
                        display.println(this + " at (" + pterodactylLocationX + "," + pterodactylLocationY + ") is getting hungry!");
                    }
                    // display unconscious message
                    else if (!this.isConscious() && this.getUnconsciousCount() <20){
                        display.println(this + " at (" + pterodactylLocationX + "," + pterodactylLocationY + ") is unconscious! Feed it");
                        display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                    }
                    displayedHungry = true;
                }


                if (destination.getGround().hasCapability(game.ground.Status.LAKE)){
                    if(destination.getItems().stream().filter(c -> c instanceof Fish).count()>=1){
                        for (Item item : destination.getItems()){

                        }

                    }

                }
//                // if fruit on bush/on ground under a tree & stegosaur still able to move
//                if (destination.getItems().stream().filter(c -> c instanceof Fruit).count() >=1) {
//                    for (Item item: destination.getItems()) {
//                        if (item.hasCapability(ItemType.FRUIT) && this.getHitPoints() != 0 && !eaten) {
//                            // check if adjacent square's fruit is on ground first: if yes, means stegosaur can eat, then only move & perform eat action
//                            if (destination.getItems().get(destination.getItems().size() - 1).hasCapability(game.ground.Status.ON_GROUND)) {
//                                map.moveActor(this, destination);            // moveActor to food source
//                                action = new EatAction(item, false);    // eat from 1 adjacent square == 1 turn, so if eaten, then cant eat anymore
//                                eaten = true;
//                            }
//                        }
//                    }
//                }

                // if remain unconscious for 20 turns, pterodactyl is dead & will turn into a corpse
                else if (this.getUnconsciousCount()==20){
                    return new DieAction();
                }
            }
        }

        return action;
    }
}
