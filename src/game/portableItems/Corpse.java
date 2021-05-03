package game.portableItems;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Location;
import game.PortableItem;

/**
 * A class that represents a dinosaur(Stegosaur,Brachiosaur,Allosaur) corpse.
 * Corpse is a portable item.
 */
public class Corpse extends PortableItem {

    /**
     * Constructor
     * @param name name of corpse
     * @param displayChar displayChar for corpse varies(depends on type of dinosaur)
     */
    public Corpse(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);

        this.setCount( this.getCount()+1);
        Display display = new Display();

        // if allosaur corpse remain in the game for more than 30 turns, remove it
        if ((this.getDisplayChar() == '%') && (this.getCount() > 30)){
            currentLocation.removeItem(this);
        }
        // if stegosaur corpse remain in the game for more than 20 turns, remove it
        else if ((this.getDisplayChar() == ')') && (this.getCount() > 20)){
            currentLocation.removeItem(this);
        }
        // if brachiosaur corpse remain in the game for more than 25 turns, remove it
        else if ((this.getDisplayChar() == '(') && (this.getCount() > 25)){
            currentLocation.removeItem(this);
        }


    }
}
