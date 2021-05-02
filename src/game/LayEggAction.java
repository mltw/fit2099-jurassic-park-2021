package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.dinosaurs.Dinosaur;
import game.portableItems.Egg;
import game.portableItems.EggType;

public class LayEggAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        ((Dinosaur) actor ).setPregnant(false);
        ((Dinosaur) actor ).setPregnantCount(0);

        if (actor.getDisplayChar() == 'a'){
            map.locationOf(actor).addItem(new Egg(EggType.ALLOSAUR));
        }
        else if (actor.getDisplayChar() == 'b'){
            map.locationOf(actor).addItem(new Egg(EggType.BRACHIOSAUR));
        }
        else if (actor.getDisplayChar() == 'd'){
            map.locationOf(actor).addItem(new Egg(EggType.STEGOSAUR));
        }

        return actor + " just laid an egg at (" + map.locationOf(actor).x() + "," +map.locationOf(actor).y() +")!";

    }

    @Override
    public String menuDescription(Actor actor) {
        return null ;
    }
}
