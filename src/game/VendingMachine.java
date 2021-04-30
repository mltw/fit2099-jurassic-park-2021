package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;

/**
 * A class for a vending machine. Only one vending machine in a game.
 */
public class VendingMachine extends Item {

    /**
     * Constructor.
     * A vending machine is displayed by a char '8'
     */
    public VendingMachine() {
        super("vendingMachine",'$',false);
    }
}
