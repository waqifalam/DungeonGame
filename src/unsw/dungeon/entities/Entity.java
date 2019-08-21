package unsw.dungeon.entities;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * An entity in the dungeon.
 * @authors Robert Clifton-Everest, Owen Silver and Waqif Alam
 *
 */
public class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;

    /**
     * Create an entity positioned in square (x,y)
     * @param x - the x coordinate of the entity
     * @param y - the y coordinate of the entity
     */
    public Entity(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public int getY() {
        return y().get();
    }

    public int getX() {
        return x().get();
    }
    /**
     * If an entity is on the stepped on square, call this method
     * Should be overridden in all classes that do something when walked on.
     * @param p - the player moving on the square
     * @param direction - the direction in which the player is moving.
     */
    public void squareBehav(Player p, String direction) {
    	
    }
    
    /**
     * A second squareBehaviour method for entities such as enemies and boulders.
     * Other entities may have different behaviours when walked on by another 
     * non player entity rather than a player
     * @param e - the entity moving onto the square
     * @param direction - the direction in which the entity is moving.
     */
    public void squareBehav(Entity e, String direction) {
    	
    }
}
