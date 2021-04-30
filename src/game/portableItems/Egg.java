package game.portableItems;

import edu.monash.fit2099.engine.Location;
import game.PortableItem;
import game.dinosaurs.Allosaur;
import game.dinosaurs.Brachiosaur;
import game.dinosaurs.Status;
import game.dinosaurs.Stegosaur;

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

        //can do capabilities and enum to separate which egg is which type
        if (this.hasCapability(EggType.STEGOSAUR) && this.eggTickCount == 40){
            currentLocation.removeItem(this);
            currentLocation.addActor(new Stegosaur(Status.BABY));
        }
        else if (this.hasCapability(EggType.BRACHIOSAUR) && this.eggTickCount == 30){
            currentLocation.removeItem(this);
            currentLocation.addActor(new Brachiosaur(Status.BABY));
        }
        else if (this.hasCapability(EggType.ALLOSAUR) && this.eggTickCount == 50){
            currentLocation.removeItem(this);
            currentLocation.addActor(new Allosaur(Status.BABY));
        }
    }
}
