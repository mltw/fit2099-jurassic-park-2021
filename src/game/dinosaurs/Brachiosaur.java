package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.*;

/**
 * A herbivorous dinosaur.
 */
public class Brachiosaur extends Dinosaur {

    private static int brachiosaurCount = 1;
    private boolean displayed = false;

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
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        Action action = new DoNothingAction();

        // update food level by 1 each turn
        this.setHitPoints(Math.max(this.getHitPoints() - 1, 0));

        // if pregnant: update pregnant count
        if (this.getPregnantCount() > 0)
            this.setPregnantCount(this.getPregnantCount() + 1);

        // update maturity status
        if (this.hasCapability(Status.BABY) && this.getBabyCount() >= 50) {
            this.removeCapability(Status.BABY);
            this.setBabyCount(0);
            this.addCapability(Status.ADULT);
        } else if (this.hasCapability(Status.BABY)) {
            this.setBabyCount(this.getBabyCount() + 1);
        }

        int brachiosaurLocationX = map.locationOf(this).x();
        int brachiosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) { //for each possible exit for the brachiosaur to go to
            Location destination = exit.getDestination(); //each adjacent square of the current brachiosaur
            if (this.getPregnantCount() >= 30) {
                action = new LayEggAction();
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
                        action = new BreedAction(adjcBrachiosaur);
                    }
                }
            }
            // if can't breed, then search for food if hungry
            else if (this.getHitPoints() < 140) {
                // display hungry message
                if (!displayed) {
                    display.println("Brachiosaur at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is getting hungry!");
//                    display.println("Hit point is "+ this.getHitPoints()); // for checking purpose only, will delete
                    displayed = true;
                }
                // if fruit on bush/on ground under a tree & still can move
                if (destination.getDisplayChar() == 'f' && this.getHitPoints() != 0) {
                    // check
                    action = new EatAction(destination.getItems());
                    Item itemToBeEaten = destination.getItems().get(destination.getItems().size() - 1);
                    destination.removeItem(itemToBeEaten);
                }
                // adjacent square has player & has fruit
                else if(destination.getDisplayChar() == '@') {
                    Player player = (Player) destination.getActor();
                    // havent complete
                }


            }
            else{
                Action wander = getBehaviour().get(0).getAction(this, map);
                if (wander != null)
                    return wander;
            }
        }
        // check if brachiosaur is unconscious, first turn;
        if (this.getHitPoints() == 0 && this.getUnconsciousCount()==0) { // check
            this.setUnconsciousCount(this.getUnconsciousCount() + 1);
        }
        // if previous turn is unconscious dy
        else if(this.getHitPoints() == 0 && this.getUnconsciousCount()>0 ) {
            this.setUnconsciousCount(this.getUnconsciousCount() + 1);
            if (this.getUnconsciousCount()== 15){
                Item corpse = new PortableItem("dead " + this, '%');
                map.locationOf(this).addItem(corpse);
                map.removeActor(this);
            }else{
                action = new DoNothingAction();}
        }
        displayed = false; // reset
        return action;
    }
}


