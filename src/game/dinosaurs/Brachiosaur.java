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
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {

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
                if (there.getDestination().getDisplayChar()=='d' && !moved && there.getDestination().getActor()!=this){
                    // follow behaviour
                    Action actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
                    if (actionBreed != null){
                        moved = true;
                        return actionBreed;
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
                else if (destination.getDisplayChar() == 'F' && this.getHitPoints() != 0) {
                    int listFruits = destination.getItems().size();
                    for (int i=0;i < listFruits;i++){
                        if (destination.getItems().get(destination.getItems().size()-1).hasCapability(game.ground.Status.ON_TREE)){
                            map.moveActor(this,destination);
                            action = new EatAction(destination.getItems());
                        }
                    }
//                    Item itemToBeEaten = destination.getItems().get(destination.getItems().size() - 1);
//                    destination.removeItem(itemToBeEaten);
                }
                // adjacent square has player & has fruit
//                else if(destination.getDisplayChar() == '@') {
//                    Player player = (Player) destination.getActor();
//                    for (Item item: player.getInventory()){
//                        if (item.getDisplayChar() =='f'){
//                            action = new EatAction(item);
//                        }
//
//                    }
//                }
            }
            else{
                Action wander = getBehaviour().get(0).getAction(this, map);
                if (wander != null)
                    return wander;
            }
        }
        if (this.getUnconsciousCount()== 15) {
            return new DieAction();
        }

        displayed = false; // reset

        if (action!= null)
            return action;
        return new DoNothingAction();
    }
}


