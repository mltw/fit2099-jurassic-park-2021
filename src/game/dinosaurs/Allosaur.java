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
     * All Allosaurs are represented by an 'a' and have 100 hit points.
     * Their name is represented as "Allosaur" followed by a unique number, eg "Allosaur1".
     */
    public Allosaur() {
        super("Allosaur"+allosaurCount, 'a', 100);

        allosaurCount++;

    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        // In each round, check and do the following:
        // update count in cantAttack; after 20 turns of cantAttack, remove the stegosaur from cantAttack
        cantAttack.replaceAll((stegosaurName, cantAttackCount) ->
                (cantAttackCount +1) == 21 ? cantAttack.remove(stegosaurName) : cantAttackCount+1);

        // check if allosaur is still unconscious
        if (this.getHitPoints()>0)
            this.setUnconsciousCount(0);

        // update pregnant count, if it's pregnant
        if (this.getPregnantCount() > 0)
            this.setPregnantCount(this.getPregnantCount() + 1);

        int allosaurLocationX = map.locationOf(this).x();
        int allosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);




        // NEW VERSION TO FIND ADJACENT AND NEARBY STUFF
        for (Exit exit : here.getExits()) { //for each possible exit for the allosaur to go to
            Location destination = exit.getDestination(); //this represents each adjacent square of the current allosaur
            // regardless of whether that square has an Actor/Tree or wtv
            List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
//            exit.name is like North/North-East etc...


            // ===========CHECK FOR PREGNANCY COUNT FIRST IF REACH n THEN LAY EGG================
            if (this.getHitPoints() > 50 && !isPregnant()) {
                // found an adjacent Allosaur
                if (destination.getDisplayChar() == 'a') {
                    Allosaur adjcAllosaur = (Allosaur) destination.getActor();
                    if (!this.isPregnant() && !adjcAllosaur.isPregnant()
                            && !(this.getGender().equals(adjcAllosaur.getGender()))) {
                        return new BreedAction(adjcAllosaur);
                    }
                }
            }

            else if (this.getHitPoints() < 90) {
                // display hungry message
                display.println("Allosaur at (" + allosaurLocationX + "," + allosaurLocationY + ") is getting hungry!");
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
                    return new EatAction(destination.getItems());
                }
                // left with EGG
//                else if (destination.getDisplayChar() == '')
            }

            // update allosaur's unconscious count if it remains unfed
            else if (this.getHitPoints() <= 0 && this.getUnconsciousCount() == 0){
                this.setUnconsciousCount(this.getUnconsciousCount()+1);
            }

            // if allosaur has reached 20 turns of unconsciousness, it will turn into a corpse
            else if (this.getHitPoints() <= 0 && this.getUnconsciousCount() == 20){
                Item corpse = new PortableItem("dead " + this, '%');

                map.locationOf(this).addItem(corpse);

                Actions dropActions = new Actions();
                for (Item item : this.getInventory())
                    dropActions.add(item.getDropAction());
                for (Action drop : dropActions)
                    drop.execute(this, map);

                map.removeActor(this);
            }


        }
        Action wander = getBehaviour().get(0).getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();

    }

//        if ((getHitPoints() > 50) && !isPregnant()){
//            ArrayList result = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, 'a'));
//            if (result.get(0).equals("Adjacent")){
//                return new BreedAction(this, (Actor) result.get(1));
//            }
//            else if (result.get(0).equals("Nearby")){
//                return new FollowBehaviour((Actor) result.get(1)).getAction(this, map);
//            }
////            for (int y = allosaurLocationY - 2; y <= (allosaurLocationY + 2) ;y++){
////                for (int x = allosaurLocationX - 2; x <= (allosaurLocationX + 2) ;x++ ){
////                    if (map.at(x,y).getActor().getDisplayChar() == 'a'){
////                        Actor nextAllosaur = map.at(x,y).getActor();
////                        int nextAllosaurLocationX = map.locationOf(nextAllosaur).x();
////                        int nextAllosaurLocationY = map.locationOf(nextAllosaur).y();
////
////                        // ADJACENT SQUARE:
////                        // if the other allosaur is at left/right column of current allosaur,
////                        // check if they're on same/upper/lower row
////                        if ((nextAllosaurLocationX == allosaurLocationX - 1) ||
////                            (nextAllosaurLocationX == allosaurLocationX + 1))
////                            {
////                                if((nextAllosaurLocationY == allosaurLocationY + 1) ||
////                                (nextAllosaurLocationY == allosaurLocationY) ||
////                                (nextAllosaurLocationY == allosaurLocationY - 1)){
////                                    return new BreedAction(this, nextAllosaur);
////                                }
////                            }
////                        // if they're same column, check for two upper and lower rows
////                        else if (nextAllosaurLocationX == allosaurLocationX)
////                            {
////                                if ((nextAllosaurLocationY == allosaurLocationY + 1) ||
////                                    (nextAllosaurLocationY == allosaurLocationY - 1)){
////                                    return new BreedAction(this, nextAllosaur);
////                                }
////                                // NEARBY
////                                else if ((nextAllosaurLocationY == allosaurLocationY + 2) ||
////                                        (nextAllosaurLocationY == allosaurLocationY - 2)){
////                                    return new FollowBehaviour(nextAllosaur).getAction(this, map);
////                                }
////                        }
////
////                        // NEARBY, NOT ADJACENT (EG TWO DINOSAURS HAVE ONE SPACE BETWEEN THEM)
////                        else if ((nextAllosaurLocationX == allosaurLocationX - 2) ||
////                                (nextAllosaurLocationX == allosaurLocationX + 2))
////                        {
////                            if((nextAllosaurLocationY == allosaurLocationY + 2) ||
////                                (nextAllosaurLocationY == allosaurLocationY + 1) ||
////                                (nextAllosaurLocationY == allosaurLocationY) ||
////                                (nextAllosaurLocationY == allosaurLocationY - 1) ||
////                                (nextAllosaurLocationY == allosaurLocationY - 2)){
////                                return new FollowBehaviour(nextAllosaur).getAction(this, map);
////                            }
////                        }
////                    }
////                }
////            }
//        }
//        ==============================NOT COMPLETE====================================
//        else if (getHitPoints() < 90){
//            // display hungry message
//            display.println("Allosaur at (" + allosaurLocationX + "," + allosaurLocationY + ") is getting hungry!");
//
//            ArrayList result = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, 'd'));
//            Stegosaur toBeAttackedStegosaur = (Stegosaur) result.get(1);
//
//            if (result.get(0).equals("Adjacent") && !cantAttack.contains(((Actor) result.get(1)).toString())){
//                //attack
//                this.hitPoints += 20;
//                if (toBeAttackedStegosaur.getHitPoints() > 0)
//                    this.cantAttack.add(toBeAttackedStegosaur.toString());
//
//                return new AttackAction(toBeAttackedStegosaur);
//            }
//            else{
////                ArrayList result2 = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, '%'));
//
//            }
//        }







    /**
     * Creates and returns an intrinsic weapon.
     * For Allosaur, its intrinsic weapon would be its sharp, pointy teeth. Thus its verb is "bites",
     * and it'll deal 20 damage to the Stegosaur attacked.
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "bites");
    }

    public ArrayList checkAdjacentAndNearbySquares(int allosaurLocationX, int allosaurLocationY, GameMap map,
                                               char chr) {
        ArrayList arrayList = new ArrayList<>();
        String result ="";
        Actor nextActor = null;
        for (int y = allosaurLocationY - 2; y <= (allosaurLocationY + 2) ;y++) {
            for (int x = allosaurLocationX - 2; x <= (allosaurLocationX + 2); x++) {
                if (map.at(x, y).getActor().getDisplayChar() == chr) {
                    nextActor = map.at(x,y).getActor();
                    int nextActorLocationX = map.locationOf(nextActor).x();
                    int nextActorLocationY = map.locationOf(nextActor).y();

                    // ADJACENT SQUARE:
                    // if the other allosaur is at left/right column of current allosaur,
                    // check if they're on same/upper/lower row
                    if ((nextActorLocationX == allosaurLocationX - 1) ||
                            (nextActorLocationX == allosaurLocationX + 1))
                    {
                        if((nextActorLocationY == allosaurLocationY + 1) ||
                                (nextActorLocationY == allosaurLocationY) ||
                                (nextActorLocationY == allosaurLocationY - 1)){
//                            return new BreedAction(this, nextActor);
                            result = "Adjacent";
                            arrayList.add(result);
                        }
                    }
                    // if they're same column, check for two upper and lower rows
                    else if (nextActorLocationX == allosaurLocationX)
                    {
                        if ((nextActorLocationY == allosaurLocationY + 1) ||
                                (nextActorLocationY == allosaurLocationY - 1)){
//                            return new BreedAction(this, nextActor);
                            arrayList.add(result);
                        }
                        // NEARBY
                        else if ((nextActorLocationY == allosaurLocationY + 2) ||
                                (nextActorLocationY == allosaurLocationY - 2)){
//                            return new FollowBehaviour(nextActor).getAction(this, map);
                            arrayList.add(result);
                        }
                    }

                    // NEARBY, NOT ADJACENT (EG TWO DINOSAURS HAVE ONE SPACE BETWEEN THEM)
                    else if ((nextActorLocationX == allosaurLocationX - 2) ||
                            (nextActorLocationX == allosaurLocationX + 2))
                    {
                        if((nextActorLocationY == allosaurLocationY + 2) ||
                                (nextActorLocationY == allosaurLocationY + 1) ||
                                (nextActorLocationY == allosaurLocationY) ||
                                (nextActorLocationY == allosaurLocationY - 1) ||
                                (nextActorLocationY == allosaurLocationY - 2)){
//                            return new FollowBehaviour(nextActor).getAction(this, map);
                            arrayList.add(result);
                        }
                    }
                }
            }
        }
        arrayList.add(nextActor);
        return arrayList;
    }



}
