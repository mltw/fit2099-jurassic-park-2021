package edu.monash.fit2099.interfaces;

import edu.monash.fit2099.engine.Item;

/**
 * This interface provides the ability to add methods to Dinosaur, without modifying code in the engine,
 * or downcasting references in the game.
 *
 * @see Item
 * @see game.dinosaurs.Stegosaur
 * @see game.dinosaurs.Brachiosaur
 * @see game.dinosaurs.Allosaur
 */
public interface DinosaurInterface {
    /**
     * Gets the food level of the dinosaur.
     * @return food level of the dinosaur.
     */
    int getFoodLevel();

    /**
     * Gets the number of rounds the dinosaur has been unconscious. Different dinosaurs will die
     * after different number of rounds(counts) of unconsciousness.
     * @return an integer, representing the number of rounds of unconsciousness of the dinosaur.
     */
    int getUnconsciousCount();

    /**
     * Gets the number of rounds the dinosaur has been pregnant. After a specific number of rounds,
     * the dinosaur will lay an egg.
     * @return an integer, representing the the number of rounds the dinosaur has been pregnant.
     */
    int getPregnantCount();

    /**
     * Checks if the dinosaur is pregnant.
     * @return true if the dinosaur is pregnant; false otherwise.
     */
    boolean isPregnant();

    /**
     * A method for the dinosaur to eat an Item instance.
     * @param item an dinosaur's food, which is an Item instance.
     */
    void eat(Item item);

    /**
     * A method to let the dinosaur breed with another same specie, opposite sex dinosaur.
     */
    void breed();

}
