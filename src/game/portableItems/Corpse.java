package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

/**
 * A class that represents a dinosaur corpse.
 */
public class Corpse extends PortableItem {

    /**
     * Constructor
     * @param name
     * @param displayChar
     */
    public Corpse(String name, char displayChar) {
        super(name, displayChar);
    }

    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);

        this.setCount( this.getCount()+1);
        Display display = new Display();

        // allosaur corpse
        if ((this.getDisplayChar() == '%') && (this.getCount() > 30)){
            currentLocation.removeItem(this);
        }
        // stegosaur corpse
        else if ((this.getDisplayChar() == ')') && (this.getCount() > 20)){
            currentLocation.removeItem(this);
        }
        // brachiosaur corpse
        else if ((this.getDisplayChar() == '(') && (this.getCount() > 25)){
            currentLocation.removeItem(this);
        }


    }
}
