package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import edu.monash.fit2099.interfaces.DinosaurInterface;
import game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A carnivore dinosaur.
 */
public class Allosaur extends Actor implements DinosaurInterface {

    // Will need to change this to a collection if Allosaur gets additional Behaviours.
    private Behaviour behaviour;
    private int unconsciousCount;
    private int pregnantCount;
    private String gender;
    private ArrayList<String> cantAttack;

    /**
     * Constructor.
     * All Allosaurs are represented by an 'a' and have 100 hit points.
     *
     * @param name the name of the Allosaur
     */
    public Allosaur(String name) {
        super(name, 'a', 100);

        behaviour = new WanderBehaviour();
        this.unconsciousCount = 0;

    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        int allosaurLocationX = map.locationOf(this).x();
        int allosaurLocationY = map.locationOf(this).y();

        Location here = map.locationOf(this);

        // NEW VERSION TO FIND ADJACENT AND NEARBY STUFF IDK 29/4/21
        for (Exit exit: here.getExits()){ //for each possible exit for the allosaur to go to
            Location destination = exit.getDestination(); //this represents each adjacent square of the current allosaur
                                                        // regardless of whether that square has an Actor/Tree or wtv
            List<Exit> nearby = destination.getExits(); //all exits of the adjacent square, ie nearby locations
//            exit.name is like North/North-East etc...
            if (destination.getDisplayChar() == 'a'){
                // do stuff
            }

        }


        if ((getHitPoints() > 50) && !isPregnant()){
            ArrayList result = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, 'a'));
            if (result.get(0).equals("Adjacent")){
                return new BreedAction(this, (Actor) result.get(1));
            }
            else if (result.get(0).equals("Nearby")){
                return new FollowBehaviour((Actor) result.get(1)).getAction(this, map);
            }
//            for (int y = allosaurLocationY - 2; y <= (allosaurLocationY + 2) ;y++){
//                for (int x = allosaurLocationX - 2; x <= (allosaurLocationX + 2) ;x++ ){
//                    if (map.at(x,y).getActor().getDisplayChar() == 'a'){
//                        Actor nextAllosaur = map.at(x,y).getActor();
//                        int nextAllosaurLocationX = map.locationOf(nextAllosaur).x();
//                        int nextAllosaurLocationY = map.locationOf(nextAllosaur).y();
//
//                        // ADJACENT SQUARE:
//                        // if the other allosaur is at left/right column of current allosaur,
//                        // check if they're on same/upper/lower row
//                        if ((nextAllosaurLocationX == allosaurLocationX - 1) ||
//                            (nextAllosaurLocationX == allosaurLocationX + 1))
//                            {
//                                if((nextAllosaurLocationY == allosaurLocationY + 1) ||
//                                (nextAllosaurLocationY == allosaurLocationY) ||
//                                (nextAllosaurLocationY == allosaurLocationY - 1)){
//                                    return new BreedAction(this, nextAllosaur);
//                                }
//                            }
//                        // if they're same column, check for two upper and lower rows
//                        else if (nextAllosaurLocationX == allosaurLocationX)
//                            {
//                                if ((nextAllosaurLocationY == allosaurLocationY + 1) ||
//                                    (nextAllosaurLocationY == allosaurLocationY - 1)){
//                                    return new BreedAction(this, nextAllosaur);
//                                }
//                                // NEARBY
//                                else if ((nextAllosaurLocationY == allosaurLocationY + 2) ||
//                                        (nextAllosaurLocationY == allosaurLocationY - 2)){
//                                    return new FollowBehaviour(nextAllosaur).getAction(this, map);
//                                }
//                        }
//
//                        // NEARBY, NOT ADJACENT (EG TWO DINOSAURS HAVE ONE SPACE BETWEEN THEM)
//                        else if ((nextAllosaurLocationX == allosaurLocationX - 2) ||
//                                (nextAllosaurLocationX == allosaurLocationX + 2))
//                        {
//                            if((nextAllosaurLocationY == allosaurLocationY + 2) ||
//                                (nextAllosaurLocationY == allosaurLocationY + 1) ||
//                                (nextAllosaurLocationY == allosaurLocationY) ||
//                                (nextAllosaurLocationY == allosaurLocationY - 1) ||
//                                (nextAllosaurLocationY == allosaurLocationY - 2)){
//                                return new FollowBehaviour(nextAllosaur).getAction(this, map);
//                            }
//                        }
//                    }
//                }
//            }
        }
//        ==============================NOT COMPLETE====================================
        else if (getHitPoints() < 90){
            // display hungry message
            display.println("Allosaur at (" + allosaurLocationX + "," + allosaurLocationY + ") is getting hungry!");

            ArrayList result = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, 'd'));
            Stegosaur toBeAttackedStegosaur = (Stegosaur) result.get(1);

            if (result.get(0).equals("Adjacent") && !cantAttack.contains(((Actor) result.get(1)).toString())){
                //attack
                this.hitPoints += 20;
                if (toBeAttackedStegosaur.getHitPoints() > 0)
                    this.cantAttack.add(toBeAttackedStegosaur.toString());

                return new AttackAction(toBeAttackedStegosaur);
            }
            else{
//                ArrayList result2 = (checkAdjacentAndNearbySquares(allosaurLocationX, allosaurLocationY, map, '%'));

            }
        }



        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;

        return new DoNothingAction();
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



    @Override
    public int getUnconsciousCount() {
        return unconsciousCount;
    }

    @Override
    public int getPregnantCount() {
        return pregnantCount;
    }

    @Override
    public boolean isPregnant() {
        return false;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public String getGender() {
        return gender;
    }

}
