package game.actions;

import edu.monash.fit2099.engine.*;
import game.Player;

import java.util.Random;

public class SearchFruitAction extends Action {
    String plantType;

    public SearchFruitAction(String plantType) {
        this.plantType = plantType;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        double random = Math.random();
        boolean flag = false;

        for (Item item : map.locationOf(actor).getItems()){
            if (item.getDisplayChar()=='f' && random<=0.4){
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
