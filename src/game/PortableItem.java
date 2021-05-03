package game;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	int count;

	/**
	 * Constructor
	 * @param name name of portable item
	 * @param displayChar displayChar of portable item varies (depends on the portable item)
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
		this.count=1;
	}

	/**
	 * Create and return an action to pick this Item up.
	 * If this Item is not portable, returns null.
	 *
	 * @return a new PickUpItemAction if this Item is portable, null otherwise.
	 */
	@Override
	public PickUpItemAction getPickUpAction() {
		return super.getPickUpAction();
	}

	/** TODO: update javadoc
	 * Getter to retrieve count
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/** TODO: update javadoc
	 * Setter for count
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}
}
