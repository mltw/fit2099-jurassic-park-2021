package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.SearchNearestFoodBehaviour;
import game.WanderBehaviour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This is an abstract class for a Dinosaur.
 * Since all different dinosaur will have same attributes but with different values, this
 * abstract class is used to implements methods related.
 * Subclasses(eg: a specific Dinosaur type, Stegosaur) will provides implementations for the methods this class.
 */
public abstract class Dinosaur extends Actor {

    private ArrayList<Behaviour> behaviour = new ArrayList<Behaviour>();
    private int unconsciousCount;
    private int pregnantCount;
    private String gender;
    private boolean isPregnant;
    private int babyCount;

    /**
     * Constructor.
     * Each dinosaur initially would have 0 unconscious count, and wander behaviour, and a random gender (M/F).
     * @param name        the name of the Dinosaur
     * @param displayChar the character that will represent the Dinosaur in the display
     * @param hitPoints   the Dinosaur's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints ) {
        super(name, displayChar, hitPoints);

        //behaviours
        setBehaviour(new WanderBehaviour());
        setBehaviour(new SearchNearestFoodBehaviour());

        // pregnancy status
        setPregnantCount(0);
        setPregnant(false);

        // unconsciousness
        setUnconsciousCount(0);

        // gender: generate a random gender (M for male, F for female)
        String[] gender = new String[]{"M", "F"};
        Random generator = new Random();
        int randomIndex = generator.nextInt(gender.length);
        setGender(gender[randomIndex]);
    }

    /** This is a method similar to tick() in other classes.
     * It handles all necessary updates of the dinosaur & perform checks on the dinosaur
     * on each turn(will be called in each dinosaur's playTurn).
     * Since number of turns needed to turn into an adult is dependent on each dinosaur, it will be the
     * input parameter here.
     * Updates will be done:
     * - Increase unconsciousCount by 1 if remain unconscious
     * - Deduct hit points by 1 if is still conscious
     * - Increase pregnant count by 1 if it's pregnant
     * - Update maturity status accordingly
     * @param babyCount number of counts upon the baby turns into an adult dinosaur
     */
    public void eachTurnUpdates(int babyCount){
        // if dinosaur is unconscious: update unconsciousCount
        if (!this.isConscious()){
            this.setUnconsciousCount( this.getUnconsciousCount() + 1);
        }
        else
            // else dinosaur is conscious: deduct food level by 1 each turn
            this.setHitPoints(this.getHitPoints() - 1);

        // if pregnant: update pregnant count
        if (this.getPregnantCount() > 0)
            this.setPregnantCount(this.getPregnantCount() + 1);

        // update maturity status
        if (this.hasCapability(Status.BABY) && this.getBabyCount() >= babyCount) {
            this.removeCapability(Status.BABY);
            this.setBabyCount(0);
            this.addCapability(Status.ADULT);
        } else if (this.hasCapability(Status.BABY)) {
            this.setBabyCount(this.getBabyCount() + 1);
        }
    }

    /** Getter
     * For the dinosaur's behaviour.
     * @return An array list to store all behaviours of the dinosaur.
     */
    public ArrayList<Behaviour> getBehaviour() {
        return behaviour;
    }

    /** Setter
     * To add dinosaur's behaviour.
     * @param behaviour the dinosaur's behaviour.
     */
    public void setBehaviour(Behaviour behaviour) {
        this.behaviour.add(behaviour);
    }

    /** Getter
     * Retrieve the number of rounds the dinosaur has been unconscious.
     * Different dinosaurs will die after different number of turns(counts) of unconsciousness.
     * @return An integer: the number of rounds of unconsciousness of the dinosaur.
     */
    public int getUnconsciousCount() {
        return unconsciousCount;
    }

    /** Setter
     * For number of turns the dinosaur has been unconscious.
     * @param unconsciousCount number of rounds the dinosaur has been unconscious.
     */
    public void setUnconsciousCount(int unconsciousCount) {
        this.unconsciousCount = unconsciousCount;
    }

    /** Getter
     * Retrieve the number of turns the dinosaur has been pregnant.
     * After a specific number of turns, the dinosaur will lay an egg.
     * @return An integer: the number of turns the dinosaur has been pregnant.
     */
    public int getPregnantCount() {
        return pregnantCount;
    }

    /** Setter
     *  For number of turns the dinosaur has been pregnant.
     * @param pregnantCount number of turns the dinosaur has been pregnant.
     */
    public void setPregnantCount(int pregnantCount) {
        this.pregnantCount = pregnantCount;
    }

    /** Getter
     * For the dinosaur's gender.
     * @return the dinosaur's gender.
     */
    public String getGender() {
        return gender;
    }

    /** Setter
     * For the dinosaur's gender.
     * @param gender the dinosaur's gender. "M" for male, "F" for female
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Checks if the dinosaur is pregnant.
     * @return true if the dinosaur is pregnant; false otherwise.
     */
    public boolean isPregnant() {
        return this.isPregnant;
    }

    /** Setter
     * For the pregnancy status of the dinosaur.
     * @param pregnant true if dinosaur is pregnant; false otherwise.
     */
    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    /** Getter
     * Retrieve the hit points (which is also the food level,health level) of the dinosaur.
     * @return hit points of the dinosaur.
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /** Setter
     * For the dinosaur's hit points.
     * @param hitPoints amount of hit points
     */
    public void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }

    /** Getter
     * Retrieve the number of turns the dinosaur has been a baby.
     * @return the number of turns the dinosaur has been a baby.
     */
    public int getBabyCount() {
        return babyCount;
    }

    /** Setter
     * Sets the number of rounds the dinosaur has been a baby.
     * After a specific number of rounds,the dinosaur will turn into an adult.
     * @param babyCount the number of rounds the dinosaur has been a baby
     */
    public void setBabyCount(int babyCount) {
        this.babyCount = babyCount;
    }
}
