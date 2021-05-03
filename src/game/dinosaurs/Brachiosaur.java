package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.*;
import game.ground.Dirt;
import game.portableItems.Corpse;
import game.portableItems.MealKitType;

import java.util.List;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {

    private static int brachiosaurCount = 1;
    private boolean displayed = false;
    private boolean moved = false;


    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'b' and have 100 hit points.
     */
    public Brachiosaur(Enum status) {
        super("Brachiosaur" + brachiosaurCount, 'b', 100);
        addCapability(status);
        maxHitPoints = 160;
        if (hasCapability(Status.BABY)) {
            this.setBabyCount(1);
            this.setHitPoints(10); //if is baby, overwrite its hit points
        }

        brachiosaurCount++;
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedAction(this)); // a Brachiosaur can be fed by Player
        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Action actionBreed = null;
        Action action = getBehaviour().get(0).getAction(this, map);
//        Action action = new DoNothingAction();

        eachTurnUpdates(50);

        int brachiosaurLocationX = map.locationOf(this).x();
        int brachiosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) { //for each possible exit for the brachiosaur to go to
            Location destination = exit.getDestination(); //each adjacent square of the current brachiosaur

            // check nearby if got brachiosaur, if yes, move towards it & breed
            List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
            for (Exit there : nearby){
                if (there.getDestination().getDisplayChar()=='b' && !moved && there.getDestination().getActor()!=this){
                    // follow behaviour
                    actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
                    if (actionBreed != null){
                        moved = true;
//                        return actionBreed;
                    }
                }
            }

            if (this.getPregnantCount() >= 30) {
                return new LayEggAction();
            }

            // if brachiosaur has possibility to breed, search for mating partner
            else if (this.getHitPoints() > 70 && !isPregnant() && this.hasCapability(Status.ADULT)) {
                // found an adjacent brachiosaur
                if (destination.getDisplayChar() == 'b') {
                    Brachiosaur adjcBrachiosaur = (Brachiosaur) destination.getActor();
                    if (!this.isPregnant()
                            && !adjcBrachiosaur.isPregnant()
                            && adjcBrachiosaur.hasCapability(Status.ADULT)
                            && !(this.getGender().equals(adjcBrachiosaur.getGender()))) {
                        return new BreedAction(adjcBrachiosaur);
                    }
                }
            }
            // if can't breed, then search for food if hungry
            else if (this.getHitPoints() < 140) {
                // display hungry message
                if (!displayed) {
                    if (this.isConscious()) {
                        display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is getting hungry!");
                        display.println("Hit point is " + this.getHitPoints()); // for checking purpose only, will delete
                    }
                    else if (!this.isConscious() && this.getUnconsciousCount() <20){
                        display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is unconscious! Feed it");
                        display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                    }
                    displayed = true;
                }
                // if step on bush, 50% to kill
                if(destination.getGround().getDisplayChar()=='v' && Math.random()>=0.5){
                    destination.setGround(new Dirt());
                }
                // if fruit on bush/on ground under a tree & still can move
                // can eat multiple fruits in a tree
                else if (destination.getDisplayChar() == 'f' && this.getHitPoints() != 0) {
                    int listFruits = destination.getItems().size();
                    int count = 0;
                    for (int i=0;i < listFruits;i++){
                        if (destination.getItems().get(destination.getItems().size()-1).hasCapability(game.ground.Status.ON_TREE)){
                                count++;  // count number of fruits found by Brachiosaur
//                            map.moveActor(this,destination);
//                            new EatAction(destination.getItems()).execute(this,map);
                        }
                    }
                    int temp = count;
                    // count > 0 means found at least 1 fruit, now to consume:
                    if (count > 0){
                        // move Brachiosaur to that location first
                        map.moveActor(this,destination);
                        // do EatAction for each fruit found, leaving one last fruit to be used to return an EatAction
                        while (count > 1){
                            new EatAction(destination.getItems()).execute(this,map);
                            count--;
                        }
                        display.println(this + " eats " + temp + " fruits");
                        return new EatAction(destination.getItems());
                    }

                }
            }
//            else{
//                Action wander = getBehaviour().get(0).getAction(this, map);
//                if (wander != null)
//                    return wander;
//            }
        }
        if (this.getUnconsciousCount()== 15) {
            return new DieAction();
        }

        displayed = false; // reset

        // finally choose which action to return if previously never returned any actions.
        // here prioritise following another dinosaur to prepare to breed, then wandering around,
        // then lastly do nothing
        if (actionBreed != null)
            return actionBreed;
        else{
            Action wander = getBehaviour().get(0).getAction(this, map);
            if (wander != null)
                return wander;
            return new DoNothingAction();
        }
    }
}


