package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.SearchNearestFoodBehaviour;
import game.SearchNearestLakeBehaviour;
import game.WanderBehaviour;

import java.util.ArrayList;
import java.util.Random;

/**
 * This is an abstract class for a Dinosaur.
 * Since all different dinosaur will have same attributes but with different values, this
 * abstract class is used to implements those related attributes and methods.
 * Subclasses(eg: a specific Dinosaur type, Stegosaur) will provide implementations for the methods
 * and attribute in this class.
 */
public abstract class Dinosaur extends Actor {

    /**
     * An ArrayList to store the dinosaur's behaviour.
     */
    private ArrayList<Behaviour> behaviour = new ArrayList<>();

    /**
     * The dinosaur's gender.
     */
    private String gender;

    /**
     * Number of turns the dinosaur has been unconscious.
     */
    private int unconsciousCount;

    /**
     * Number of turns the dinosaur has been pregnant.
     */
    private int pregnantCount;

    /**
     * An indicator whether the dinoaur is pregnant. True if pregnant, false otherwise
     */
    private boolean isPregnant;

    /**
     * Number of turns the dinosaur has been a baby.
     */
    private int babyCount;

    /**
     * Water level of the dinosaur.
     */
    private int waterLevel;

    /**
     * Maximum water level (water capacity) of the dinosaur.
     */
    private int maxWaterLevel;

    /**
     * Constructor.
     * Each dinosaur initially would have 0 unconscious count,
     * wander behaviour, search for nearest food and lake behaviour,
     * and a random gender (M/F).
     * @param name        the name of the Dinosaur
     * @param displayChar the character that will represent the Dinosaur in the display
     * @param hitPoints   the Dinosaur's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);

        //behaviours
        setBehaviour(new WanderBehaviour());
        setBehaviour(new SearchNearestFoodBehaviour());
        setBehaviour(new SearchNearestLakeBehaviour());

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

        this.setWaterLevel(60);     // initial water level:60
        this.setMaxWaterLevel(100);
    }

    /** This is a method similar to tick() in other classes.
     * It handles all necessary updates of the dinosaur and perform checks on the dinosaur
     * on each turn(will be called in each dinosaur's playTurn).
     * Since number of turns needed to turn into an adult is dependent on each dinosaur, it will be the
     * input parameter here.
     * Updates will be done:
     * - Increase unconsciousCount by 1 if remain unconscious
     * - Deduct hit points by 1 if is still conscious
     * - Deduct water level by 1 if is still conscious
     * - Increase pregnant count by 1 if it's pregnant
     * - Update maturity status accordingly
     * @param babyCount number of counts upon the baby turns into an adult dinosaur
     */
    public void eachTurnUpdates(int babyCount){
        // if dinosaur is unconscious (by hunger or thirst): update unconsciousCount
        if (!this.isConscious() || this.getWaterLevel() ==0){
            this.setUnconsciousCount( this.getUnconsciousCount() + 1);
        }
        else {
            // else dinosaur is conscious: deduct food and water level by 1 each turn
            this.setHitPoints(this.getHitPoints() - 1);
            this.setWaterLevel(Math.max(this.getWaterLevel()-1,0));
        }


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

    /** Getter
     * Retrieve the water level of current dinosaur has.
     * @return the water level of current dinosaur has.
     */
    public int getWaterLevel() {
        return waterLevel;
    }

    /** Setter
     * Sets the water level of current dinosaur.
     * @param waterLevel water level to be updated.
     */
    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    /** Getter
     * Retrieve the maximum water level of current dinosaur.
     * @return the maximum water level of current dinosaur.
     */
    public int getMaxWaterLevel() {
        return maxWaterLevel;
    }

    /** Setter
     * Sets the maximum water level the current dinosaur can have.
     * @param maxWaterLevel maximum water level to be set.
     */
    public void setMaxWaterLevel(int maxWaterLevel) {
        this.maxWaterLevel = maxWaterLevel;
    }
}
