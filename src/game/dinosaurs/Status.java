package game.dinosaurs;

/** An Enum class was created to indicate the Capabilities of Dinosaur
 * Since the capabilities are known(not going to change), enum is used.
 * The Status can divided into:
 * BABY: indicating baby dinosaur
 * ADULT: indicating adult dinosaur
 * STEGOSAUR, BRACHIOSAUR, ALLOSAUR: 3 enums indicating its respective class
 * ON_LAND: indicating the dinosaur is on land/ground (used for Pterodactyls)
 * ON_SKY: indicating the dinosaur is flying in the sky (used for Pterodactyls)
 */
public enum Status {
    BABY, ADULT, STEGOSAUR, BRACHIOSAUR, ALLOSAUR, PTERODACTYL, ON_LAND, ON_SKY
}
