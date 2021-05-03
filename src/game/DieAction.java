package game;

import edu.monash.fit2099.engine.*;
import game.portableItems.Corpse;

public class DieAction extends Action {

    @Override
    public String execute(Actor actor, GameMap map) {

        if (actor.getDisplayChar() == 'a') {
            Item corpse = new Corpse("dead " + actor, '%');
            map.locationOf(actor).addItem(corpse);
        }
        else if (actor.getDisplayChar() == 'b'){
            Item corpse = new Corpse("dead " + actor, '(');
            map.locationOf(actor).addItem(corpse);
        }
        else if (actor.getDisplayChar() == 'd'){
            Item corpse = new Corpse("dead " + actor, ')');
            map.locationOf(actor).addItem(corpse);
        }

        Actions dropActions = new Actions();
        for (Item item : actor.getInventory())
            dropActions.add(item.getDropAction());
        for (Action drop : dropActions)
            drop.execute(actor, map);

        map.removeActor(actor);

        return (actor + "is dead at (" + map.locationOf(actor).x() + ","
                + map.locationOf(actor).y() + ")");
    }

    @Override
    public String menuDescription(Actor actor) {
        return "smlj";
    }
}
