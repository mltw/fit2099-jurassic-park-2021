package game;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * A class for a laser gun, used by Player to kill Stegosaurs.
 */
public class LaserGun extends WeaponItem {
    /**
     * Constructor.
     * A laser gun is displayed by a '~', deals 70 damage.
     */
    public LaserGun() {
        super("laserGun", '~', 70, "zaps");
        this.portable = true;
    }
}
