package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.DinosaurGameMap;
import game.actions.*;
import game.ground.Lake;
import game.portableItems.Fish;
import game.portableItems.Fruit;
import game.portableItems.ItemType;

import java.util.ArrayList;
import java.util.Random;

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

    /**
     * A pterodactyl can be fed by a player.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return all actions that can be performed on this pterodactyl.
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedAction(this));
        return actions;
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

            // if thirsty
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
                }

                // dies after 15 turns of unconsciousness without water
                else if (this.getUnconsciousCount() == 15) {
                    return new DieAction();
                }

            }

            // if not thirsty, then search for food if hungry
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

                // if its on a lake and the lake has at least one fish
                if (destination.getGround().hasCapability(game.ground.Status.LAKE) &&
                    (destination.getItems().stream().filter(c -> c instanceof Fish).count()>=1)){

                    ArrayList<Integer> indexOfFish = new ArrayList<>();
                    for (int i=0; i<destination.getItems().size(); i++){
                        if(destination.getItems().get(i).hasCapability(ItemType.FISH) && this.isConscious()){
                            indexOfFish.add(i);
                        }
                    }
                    // gender: generate a random number in between 0-2
                    int[] canCatch = new int[]{0,1,2};
                    Random generator = new Random();
                    int randomIndex = generator.nextInt(canCatch.length);
                    int numOfFishCaught = canCatch[randomIndex];

                    if (numOfFishCaught==0){
                        display.println(this + " couldn't catch any fish from the lake");

                    }
                    else{
                        map.moveActor(this,destination);
                        int i =1;
                        int fishIndex = 0;
                        while (i<=numOfFishCaught && indexOfFish.size()!=0){
                            new EatAction(destination.getItems().get(indexOfFish.get(fishIndex)),
                                    false).execute(this,map);

                            i++;
                            indexOfFish.remove(fishIndex);
                            fishIndex++;
                        }
                        display.println(this + " ate "+ (i-1) + " fish from lake");
                    }
                    new DrinkAction().execute(this, map);

                    return new DoNothingAction();
                }

                // if its on a ground, and there's a dead corpse
                else if((destination.getItems().stream().filter(
                        c -> c.hasCapability(ItemType.CORPSE)).count()>=1)){

                    // check if there are any dinosaurs around the corpse
                    boolean noDinosaursAround = true;
                    for (Exit exit1: destination.getExits() ){
                        if ((exit1.getDestination().getActor().hasCapability(Status.ALLOSAUR) )||
                            (exit1.getDestination().getActor().hasCapability(Status.BRACHIOSAUR) )||
                            (exit1.getDestination().getActor().hasCapability(Status.PTERODACTYL) )||
                            (exit1.getDestination().getActor().hasCapability(Status.STEGOSAUR)) &&
                            (exit1.getDestination().getActor()!=this)){
                            noDinosaursAround = false;
                        }
                    }
                    Item thatCorpse = null;

                    // if no dinosaurs, eat that corpse
                    if (noDinosaursAround){
                        for (Item item: destination.getItems()){
                            if (item.hasCapability(ItemType.CORPSE)) {
                                thatCorpse = item;
                                break;
                            }
                        }
                        action = new EatAction(thatCorpse,false);
                    }
                }


                // if remain unconscious for 20 turns, pterodactyl is dead & will turn into a corpse
                else if (this.getUnconsciousCount()==20){
                    return new DieAction();
                }
            }
        }
        if (action != null)
            return action;
        // wandering around
        else if (getBehaviour().get(0).getAction(this, map)!=null)
            return getBehaviour().get(0).getAction(this, map);
        // do nothing
        else
            return new DoNothingAction();
    }
}
