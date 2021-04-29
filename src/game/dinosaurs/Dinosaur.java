package game.dinosaurs;

import edu.monash.fit2099.engine.*;
import game.Behaviour;
import game.WanderBehaviour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * An abstract class for a Dinosaur.
 */
public abstract class Dinosaur extends Actor {

    private ArrayList<Behaviour> behaviour;
    private int unconsciousCount;
    private int pregnantCount;
    private String gender;
    private boolean isPregnant;

    /**
     * Constructor.
     * Each dinosaur initially would have 0 unconscious count, and wander behaviour, and a random gender (M/F).
     * @param name        the name of the Dinosaur
     * @param displayChar the character that will represent the Dinosaur in the display
     * @param hitPoints   the Dinosaur's starting hit points
     */
    public Dinosaur(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        setBehaviour(new WanderBehaviour());
        setUnconsciousCount(0);
        setPregnantCount(0);
        setPregnant(false);

        //generate a random gender (M for male, F for female)
        String[] gender = new String[]{"M", "F"};
        Random generator = new Random();
        int randomIndex = generator.nextInt(gender.length);
        setGender(gender[randomIndex]);
    }

    /**
     * A getter for the dinosaur's behaviour.
     * @return An array list to store all behaviours of the dinosaur.
     */
    public ArrayList<Behaviour> getBehaviour() {
        return behaviour;
    }

    /**
     * A setter to add dinosaur's behaviour.
     * @param behaviour the dinosaur's behaviour.
     */
    public void setBehaviour(Behaviour behaviour) {
        this.behaviour.add(behaviour);
    }

    /**
     * Gets the number of rounds the dinosaur has been unconscious. Different dinosaurs will die
     * after different number of rounds(counts) of unconsciousness.
     * @return an integer, representing the number of rounds of unconsciousness of the dinosaur.
     */
    public int getUnconsciousCount() {
        return unconsciousCount;
    }

    /**
     * A setter for number of rounds the dinosaur has been unconscious.
     * @param unconsciousCount number of rounds the dinosaur has been unconscious.
     */
    public void setUnconsciousCount(int unconsciousCount) {
        this.unconsciousCount = unconsciousCount;
    }

    /**
     * Gets the number of rounds the dinosaur has been pregnant. After a specific number of rounds,
     * the dinosaur will lay an egg.
     * @return an integer, representing the the number of rounds the dinosaur has been pregnant.
     */
    public int getPregnantCount() {
        return pregnantCount;
    }

    /**
     *  A setter for number of rounds the dinosaur has been pregnant.
     * @param pregnantCount number of rounds the dinosaur has been pregnant.
     */
    public void setPregnantCount(int pregnantCount) {
        this.pregnantCount = pregnantCount;
    }

    /**
     * A getter for the dinosaur's gender.
     * @return the dinosaur's gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * A setter for the dinosaur's gender.
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

    /**
     * A setter for the pregnancy status of the dinosaur.
     * @param pregnant true if dinosaur is pregnant; false otherwise.
     */
    public void setPregnant(boolean pregnant) {
        isPregnant = pregnant;
    }

    /**
     * Gets the hit points (= food level = health level) of the dinosaur.
     * @return hit points of the dinosaur.
     */
    public int getHitPoints(){
        return this.hitPoints;
    }

    /**
     * A setter for the dinosaur's hit points.
     * @param hitPoints amount of hit points
     */
    public void setHitPoints(int hitPoints){
        this.hitPoints = hitPoints;
    }

}
