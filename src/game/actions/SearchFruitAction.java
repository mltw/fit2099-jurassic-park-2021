package game.actions;

import edu.monash.fit2099.engine.*;
import game.Player;
import game.portableItems.ItemType;

/**
 * Action for Player to search for fruit in Bush/Tree in same square.
 */
public class SearchFruitAction extends Action {

    /**
     * The type of plant to be searched; it's either a "bush" or "tree".
     * Mainly for display purposes.
     */
    private String plantType;

    /**
     * Constructor.
     *
     * @param plantType the type of plant to be searched; it's either a "bush" or "tree"
     */
    public SearchFruitAction(String plantType) {
        this.plantType = plantType;
    }

    /**
     * Perform the search fruit Action.
     * Player has 60% fail rate of searching for a fruit.
     *
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return a description of whether the Player could successfully search and pick a fruit.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        double random = Math.random();
        boolean flag = false;

        for (Item item : map.locationOf(actor).getItems()){
            if (item.hasCapability(ItemType.FRUIT) && random<=0.4){
                // successfully picked up
                new PickUpItemAction(item).execute(actor, map);
                flag = true;

                // when a fruit is harvested from a bush or tree, 10 eco points is gained
                Player.addEcoPoints(10);

                break;
            }
        }

        if (flag)
            return actor + " searched and picked up fruit from " + plantType;
        else
            return actor + " searched the " +plantType+" for fruit, but can't find any ripe ones";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " searches for fruit in " + this.plantType ;
    }
}
