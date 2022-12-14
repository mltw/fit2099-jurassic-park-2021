package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.*;
import game.actions.*;
import game.ground.Dirt;
import game.ground.Lake;
import game.portableItems.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Brachiosaur is a long neck herbivorous dinosaur which only eat fruits from trees.
 * It's main actions will be handled in the playTurn method.
 */
public class Brachiosaur extends Dinosaur {

    /**
     * A count for number of Brachiosaurs instantiated, used to give a unique name for each Brachiosaur
     */
    private static int brachiosaurCount = 1;

    /**
     * An Action, to store a breeding action if possible to breed. Else, it'll be null.
     */
    private Action actionBreed;

    /**
     * Constructor.
     * All Brachiosaurs are represented by a 'b' and have 100 hit points initially.
     *
     * @param status an Enum value representing its maturity status (BABY/ADULT)
     * @see Status
     */
    public Brachiosaur(Enum status) {
        super("Brachiosaur" + brachiosaurCount, 'b', 100);
        addCapability(status);
        addCapability(Status.BRACHIOSAUR);
        maxHitPoints = 160;
        this.setMaxWaterLevel(200); //Brachiosaur has max 200 capacity, so overwrite the one set in Dinosaur class

        if (hasCapability(Status.BABY)) {
            this.setBabyCount(1);
            this.setHitPoints(10);      //if is baby, starting hit points is 10
        }
        brachiosaurCount++;
    }

    /**
     * A brachiosaur can be fed by a player.
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return all actions that can be performed on this brachiosaur
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new FeedAction(this));
        return actions;
    }

    /**
     * This method will determine what action a brachiosaur can perform, considering it's hitpoints,
     * and its surrounding(adjacent square)
     *  @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        boolean displayedHungry = false;
        boolean moved = false;
        boolean displayThirsty = false;
        actionBreed = null;             // reset

        eachTurnUpdates(50);  // to handle necessary updates for each turn

        int brachiosaurLocationX = map.locationOf(this).x();
        int brachiosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        for (Exit exit : here.getExits()) {                     //for each possible exit for the brachiosaur to go to
            Location destination = exit.getDestination();       //each adjacent square of the current brachiosaur

            // check if nearby has a brachiosaur, if yes, move towards it & breed
            List<Exit> nearby = destination.getExits();         //all exits of the adjacent square, ie nearby locations
            for (Exit there : nearby){
                if (there.getDestination().containsAnActor()
                        && there.getDestination().getActor().hasCapability(Status.BRACHIOSAUR)
                        && !moved && there.getDestination().getActor()!=this){
                    actionBreed = new FollowBehaviour(there.getDestination().getActor()).getAction(this,map);
                    if (actionBreed != null){
                        moved = true;
                    }
                }
            }

            // Egg hatched & should turn into a baby brachiosaur
            if (this.getPregnantCount() >= 30) {
                return new LayEggAction();
            }

            // if brachiosaur has possibility to breed, search for mating partner
            else if (this.getHitPoints() > 70 && !isPregnant() && this.hasCapability(Status.ADULT)) {
                // found an adjacent brachiosaur
                if (destination.containsAnActor()
                        && destination.getActor().hasCapability(Status.BRACHIOSAUR)) {
                    Brachiosaur adjcBrachiosaur = (Brachiosaur) destination.getActor();
                    if (!this.isPregnant()
                            && !adjcBrachiosaur.isPregnant()
                            && adjcBrachiosaur.hasCapability(Status.ADULT)
                            && !(this.getGender().equals(adjcBrachiosaur.getGender()))) {
                        return new BreedAction(adjcBrachiosaur);
                    }
                }
            }

            // 12/5
            else if(this.getWaterLevel() <40){
                // display thirsty message
                if (!displayThirsty){
                    if (this.getWaterLevel() <40 && this.getWaterLevel() !=0) {
                        display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is getting thirsty!");
                        display.println("Water level is " + this.getWaterLevel());
                    }
                    // display unconscious message
                    else if (this.getWaterLevel()==0 && this.getUnconsciousCount() <15){
                        if (((DinosaurGameMap)map).isRained()){
                            this.setWaterLevel(10);
                            this.setUnconsciousCount(0);
                        }
                        else{
                            display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is unconscious! Get water for it!");
                            display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                        }
                    }
                    displayThirsty = true;
                }
                if (destination.getGround().hasCapability(game.ground.Status.LAKE)){
                    Lake ground = (Lake) destination.getGround();
                    if (ground.getSips()>0) {
                        ground.setSips(ground.getSips() - 1); // one turn == one sip(one sip == 80 water level)
                        new DrinkAction();
                    }
                    // if empty(sip==0): lose ability to be drunk by stegosaur
                }

                else if (this.getUnconsciousCount()==15){
                    return new DieAction();
                }

            }

            // if not thirsty, then search for food if hungry
            else if (this.getHitPoints() < 140) {
                // display hungry message
                if (!displayedHungry) {
                    if (this.isConscious()) {
                        display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is getting hungry!");
                    }
                    // display unconscious message
                    else if (!this.isConscious() && this.getUnconsciousCount() <20){
                        display.println(this + " at (" + brachiosaurLocationX + "," + brachiosaurLocationY + ") is unconscious! Feed it");
                        display.println("Unconscious count: " + (this.getUnconsciousCount() + 1));
                    }
                    displayedHungry = true;
                }

                // if step on bush, 50% to kill
                if(destination.getGround().hasCapability(game.ground.Status.BUSH) && Math.random()>=0.5){
                    destination.setGround(new Dirt());
                }

                // if fruit on bush/on ground under a tree & still can move: can eat multiple fruits in a tree
                else if (destination.getItems().stream().filter(c -> c instanceof Fruit).count() >=1
                            && this.getHitPoints() != 0)    {
//                else if (destination.getDisplayChar() == 'f' && this.getHitPoints() != 0) {
//                    int listFruits = destination.getItems().size();
                    ArrayList<Integer> indexOfFruits = new ArrayList<>();

                    int count = 0;
                    for (int i=0;i < destination.getItems().size();i++){
                        if (destination.getItems().get(i).hasCapability(game.ground.Status.ON_TREE)){
                            count++;                          // count number of fruits found by Brachiosaur
                            indexOfFruits.add(i);
                        }
                    }
//                    int temp = count;
                    if (count > 0){                               // count > 0 means found at least 1 fruit, now to consume:
                        map.moveActor(this,destination);    // move Brachiosaur to that location first
                        // do EatAction for each fruit found, finally return a DoNothing Action
                        for (int i = indexOfFruits.size()-1; i >=0; i--) {
                            new EatAction(destination.getItems().get(i), false).execute(this, map);
                        }

//                        while (count > 1){
//                            new EatAction(destination.getItems()).execute(this,map);
//                            count--;
//                        }
                        display.println(this + " eats " + count + " fruits from a tree");
//                        return new EatAction(destination.getItems().get(indexOfFruits.get(indexOfFruits.size()-1)),false);
                        return new DoNothingAction();
                    }
                }

                // if remain unconscious for 20 turns, stegosaur is dead & will turn into a corpse
                else if (this.getUnconsciousCount()== 15) {
                    return new DieAction();
                }
            }
        }

        // Finally choose which action to return if previously never return any action.
        // Priority from most important to least:
        // following another dinosaur to check if can breed
        if (actionBreed != null)
            return actionBreed;
        // searching for nearest lake
        else if(displayThirsty && getBehaviour().get(2).getAction(this,map)!=null){
            return getBehaviour().get(2).getAction(this,map);
        }
        // searching for nearest food source
        else if (getBehaviour().get(1).getAction(this,map)!=null)
            return getBehaviour().get(1).getAction(this,map);
        // wandering around
        else if (getBehaviour().get(0).getAction(this, map)!=null)
            return getBehaviour().get(0).getAction(this, map);
        // do nothing
        else
            return new DoNothingAction();
    }

    /**
     * Reset the brachiosaur count. Used when a new round of game is called.
     */
    public static void resetBrachiosaurCount(){
        brachiosaurCount = 1;
    }
}


