package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;
import game.dinosaurs.*;

import java.awt.*;

/**
 * A class for a dinosaur's Egg
 */
public class Egg extends PortableItem {
    private int eggTickCount;

    /**
     * Constructor.
     *
     * @param eggType which kind/type of dinosaur this egg is of
     */
    public Egg(Enum eggType) {
        super("egg("+eggType+")", 'e');
        addCapability(eggType);
        this.eggTickCount = 0;
    }

    /**
     * Inform an Item on the ground of the passage of time.
     * This method is called once per turn, if the item rests upon the ground.
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        this.eggTickCount ++;

        Display display = new Display();
        Dinosaur newBorn = new Stegosaur(Status.BABY); // let default be a Stegosaur
        boolean hatched = false;

        if (this.eggTickCount == 30 || this.eggTickCount == 40 || this.eggTickCount == 50) {
            try {
                if (this.hasCapability(EggType.ALLOSAUR) && this.eggTickCount == 50) {
                    newBorn = new Allosaur(Status.BABY);
                }
                else if (this.hasCapability(EggType.BRACHIOSAUR) && this.eggTickCount == 30) {
                    newBorn = new Brachiosaur(Status.BABY);
                }

                currentLocation.removeItem(this);
                currentLocation.addActor(newBorn);
                display.println("Yay! " + newBorn + " just hatched at (" + currentLocation.x() + ","
                        + currentLocation.y() + ")");
                hatched = true;

            }
            catch (Exception e) {
                // will have Exception if current location of egg has an Actor, since
                // can't create a new Actor instance in a square which is already occupied by an Actor
                for (Exit exit : currentLocation.getExits()) {
                    if (!exit.getDestination().containsAnActor()) {
                        currentLocation.removeItem(this);
                        exit.getDestination().addActor(newBorn);
                        display.println("Yay! " + newBorn + " just hatched at (" + currentLocation.x() + ","
                                + currentLocation.y() + ")");
                        hatched = true;
                        break;
                    }
                }
                if (!hatched){
                    this.eggTickCount--;
                    display.println(this + "'s surrounding are occupied by actors, can't hatch till " +
                            "an actor moves away!");
                }
            }
        }
    }
}
