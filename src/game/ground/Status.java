package game.ground;

/**
 * An Enum class was created to indicate the Capabilities of Ground
 * Since the capabilities are known(not going to change), enum is used.
 * The Status can divided into:
 * - DEAD: unable to grow fruits
 * - ALIVE: able to grow fruits
 * - ON_TREE: fruits are on tree
 * - ON_GROUND: fruits dropped on the ground
 * - BUSH: type bush
 * - TREE: type tree
 * - LAKE: type lake
 * - DIRT: type dirt
 */
public enum Status {
    DEAD,ALIVE,ON_TREE,ON_GROUND, BUSH, TREE,LAKE,DIRT
}
