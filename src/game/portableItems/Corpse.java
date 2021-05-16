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
     * the number of times it can be eaten by a Pterodactyl
     */
    private int edibleCount;

    /**
     * Constructor
     * @param name name of corpse.
     * @param corpseType an Enum value, indicating the type of corpse to be formed.
     */
    public Corpse(String name, Enum corpseType) {
        super(name, ')'); //default
        addCapability(ItemType.CORPSE);
        edibleCount = 5;

        // overwrite the display char and edible count
        if (corpseType == CorpseType.BRACHIOSAUR) {
            displayChar = '(';
            edibleCount = 10;
        }
        else if (corpseType == CorpseType.ALLOSAUR) {
            displayChar = '%';
            edibleCount = 5;
        }
        else if (corpseType == CorpseType.PTERODACTYL) {
            displayChar = '/';
            edibleCount = 3;
        }
    }

    /**
     *
     * @param currentLocation The location of the ground on which we lie.
     */
    @Override
    public void tick(Location currentLocation) {
        super.tick(currentLocation);

        this.setCount( this.getCount()+1);

        // if allosaur corpse remain in the game for more than 30 turns, remove it
        if ((this.hasCapability(CorpseType.ALLOSAUR)) && (this.getCount() > 30)){
            currentLocation.removeItem(this);
        }
        // if stegosaur/pterodactyl corpse remain in the game for more than 20 turns, remove it
        else if ((this.hasCapability(CorpseType.STEGOSAUR) || this.hasCapability(CorpseType.PTERODACTYL))
                && (this.getCount() > 20)){
            currentLocation.removeItem(this);
        }
        // if brachiosaur corpse remain in the game for more than 25 turns, remove it
        else if ((this.hasCapability(CorpseType.BRACHIOSAUR)) && (this.getCount() > 25)){
            currentLocation.removeItem(this);
        }
    }

    /**
     * Getter for the corpse's edible count
     * @return the corpse's edible count
     */
    public int getEdibleCount() {
        return edibleCount;
    }

    /**
     * Setter for the corpse's edible count
     * @param edibleCount the corpse's edible count
     */
    public void setEdibleCount(int edibleCount) {
        this.edibleCount = edibleCount;
    }
}
