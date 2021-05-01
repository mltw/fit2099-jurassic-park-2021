package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.*;

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
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

        Action action = new DoNothingAction();

        eachTurnUpdates(50);

        int brachiosaurLocationX = map.locationOf(this).x();
        int brachiosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) { //for each possible exit for the brachiosaur to go to
            Location destination = exit.getDestination(); //each adjacent square of the current brachiosaur

            // check nearby if got brachiosaur, if yes, move towards it & breed
            List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
            for (Exit there : nearby){
                if (there.getDestination().getDisplayChar()=='d' && !moved && there.getDestination().getActor()!=this){
                    // follow behaviour
                    Action actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
                    if (actionBreed != null){
                        actionBreed.execute(this,map);
                        moved = true;
                    }
                }
            }

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
                        return new BreedAction(adjcBrachiosaur);
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
                    // moveActor to food source
                    map.moveActor(this,destination);
                    action = new EatAction(destination.getItems());
//                    Item itemToBeEaten = destination.getItems().get(destination.getItems().size() - 1);
//                    destination.removeItem(itemToBeEaten);
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
        if (this.getUnconsciousCount()== 15){
            Item corpse = new PortableItem("dead " + this, '%');
            map.locationOf(this).addItem(corpse);
            map.removeActor(this);}

        displayed = false; // reset
        return action;
    }
}


