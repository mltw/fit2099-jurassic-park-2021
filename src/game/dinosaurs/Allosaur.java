package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A carnivore dinosaur.
 */
public class Allosaur extends Dinosaur {

    //    private ArrayList<String> cantAttack;
    private HashMap<String, Integer> cantAttack;
    // used to give a unique name for each Allosaur
    private static int allosaurCount = 1;

    /**
     * Constructor.
     * All Allosaurs are represented by an 'a' and have 50 hit points.
     * Their name is represented as "Allosaur" followed by a unique number, eg "Allosaur1".
     */
    public Allosaur(Enum status) {
        // Allosaurs always start as baby (hatch from egg)
        super("Allosaur"+allosaurCount, 'a', 50);
        addCapability(status);
        this.setBabyCount(1);
        this.maxHitPoints = 100;

        allosaurCount++;
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedAction(this)); // an Allosaur can be fed by Player
        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        boolean displayed = false;

        eachTurnUpdates(50);

        // update count in cantAttack; after 20 turns of cantAttack, remove the stegosaur from cantAttack
        cantAttack.replaceAll((stegosaurName, cantAttackCount) ->
                (cantAttackCount +1) == 21 ? cantAttack.remove(stegosaurName) : cantAttackCount+1);

        int allosaurLocationX = map.locationOf(this).x();
        int allosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) { //for each possible exit for the allosaur to go to
            Location destination = exit.getDestination(); //this represents each adjacent square of the current allosaur
            // regardless of whether that square has an Actor/Tree or wtv
            List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations

            if (this.getPregnantCount()>=20){
                return new LayEggAction();
            }

            // if allsoaur has possibility to breed, search for mating partner
            else if (this.getHitPoints() > 50 && !this.isPregnant() && this.hasCapability(Status.ADULT)) {
                // found an adjacent Allosaur
                if (destination.getDisplayChar() == 'a') {
                    Allosaur adjcAllosaur = (Allosaur) destination.getActor();
                    if (!adjcAllosaur.isPregnant()
                            && adjcAllosaur.hasCapability(Status.ADULT)
                            && !(this.getGender().equals(adjcAllosaur.getGender()))) {
                        return new BreedAction(adjcAllosaur);
                    }
                }
            }

            // if can't breed, then search for food if hungry
            else if (this.getHitPoints() < 90) {
                // display hungry message
                if (!displayed){
                    if (this.isConscious()){
                        display.println(this + " at (" + allosaurLocationX + "," + allosaurLocationY + ") is getting hungry!");
                        display.println("Hit point is " + this.getHitPoints()); // for checking purpose only, will delete
                    }
                    else if (!this.isConscious() && this.getUnconsciousCount() <20){
                        display.println(this + " at (" + allosaurLocationX + "," + allosaurLocationY + ") is unconscious! Feed it");
                        display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                    }
                    displayed = true;
                }

                 // if found a Stegosaur
                if (destination.getDisplayChar() == 'd') {
                    Stegosaur adjcStegosaur = (Stegosaur) destination.getActor();

                    // if the Stegosaur is not attacked by this Allosaur within the previous 20 turns
                    if (!this.cantAttack.containsKey(adjcStegosaur.toString())) {
                        this.cantAttack.put(adjcStegosaur.toString(), 1); //add the Stegosaur into 'cantAttack'
                        return new AttackAction(adjcStegosaur);
                    }
                }

                // if found a Corpse
                else if(destination.getDisplayChar() == '%' || destination.getDisplayChar() == '('
                        || destination.getDisplayChar() == ')'){
                    // moveActor to food source
                    map.moveActor(this,destination);
                    return new EatAction(destination.getItems());
                }

                // if found an Egg
                else if (destination.getDisplayChar() == 'e'){
                    // moveActor to food source
                    map.moveActor(this,destination);
                    return new EatAction(destination.getItems());}

                // if allosaur has reached 20 turns of unconsciousness, it will turn into a corpse
                else if (this.getHitPoints() <= 0 && this.getUnconsciousCount() >= 20){
                    Item corpse = new PortableItem("dead " + this, '%');

                    map.locationOf(this).addItem(corpse);

                    Actions dropActions = new Actions();
                    for (Item item : this.getInventory())
                        dropActions.add(item.getDropAction());
                    for (Action drop : dropActions)
                        drop.execute(this, map);

                    display.println(this + "is dead at (" + map.locationOf(this).x() + ","
                                    + map.locationOf(this).y() +")");

                    map.removeActor(this);
                    return new DoNothingAction();
                }

                // if allosaur is still unfed and unconsicous, do nothing
                else if (this.getHitPoints() <= 0 && this.getUnconsciousCount() >= 0){
                    return new DoNothingAction();
                }
                else{
                    Action wander = getBehaviour().get(0).getAction(this, map);
                    if (wander != null)
                        return wander;
                }
            }
            Action wander = getBehaviour().get(0).getAction(this, map);
            if (wander != null)
                return wander;
        }
        return new DoNothingAction();
    }

    /**
     * Creates and returns an intrinsic weapon.
     * For Allosaur, its intrinsic weapon would be its sharp, pointy teeth. Thus its verb is "bites".
     * Baby Allosaurs deal 10 damage to the Stegosaur attacked, adult Allosaurs deal 20 damage.
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        if (this.hasCapability(Status.BABY))
            return new IntrinsicWeapon(10, "baby bites");

        return new IntrinsicWeapon(20, "bites");
    }



}
