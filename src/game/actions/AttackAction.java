package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;
import game.portableItems.Corpse;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	Action dead;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	/**
	 * Perform the Action.
	 * Get the weapon of the Player and deal damage to the target.
	 * (The actor could possibly miss attacking the target. )
	 * Damage dealt depends on the weapon; the target gets hurt that amount of damage,
	 * meanwhile the actor gets healed that amount.
	 * If target is unconscious after being attacked, a Corpse is formed.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		actor.heal(damage);

		if (!target.isConscious()) {
			Corpse corpse;

			if (target.getDisplayChar() == 'a'){ //allosaur corpse
				dead = new DieAction();
//				corpse = new Corpse("dead "+ target, '%');
			}
			else if (target.getDisplayChar() == 'b') { //brachiosaur corpse
				dead = new DieAction();
//				corpse = new Corpse("dead " + target, '(');
			}
			else { //stegosaur corpse
				dead = new DieAction();
//				corpse = new Corpse("dead " + target, ')');
			}
//			map.locationOf(target).addItem(corpse);

			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)
				drop.execute(target, map);
			map.removeActor(target);
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
