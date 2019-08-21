package unsw.dungeon.entities;

/**
 * This entity represents a door which can be opened with a key of corresponding id.
 * Once a door is opened, a key is consumed and it will not be closed. 
 * @author Waqif Alam and Owen Silver
 *
 */
public class Door extends Entity{
	private boolean opened = false; //Whether the door is open or not, closed by default.
	private int id; //the id of the door
	
	/**
	 * Constructor for instance of door.
	 * @param x - x coordinate that the door is located
	 * @param y - y coordinate that the door is located
	 * @param id - id of the door, corresponds to a key with the same id.
	 */
	public Door(int x, int y, int id) {
		super(x, y);
		this.id = id;
	}
	
	/**
	 * Checks if player has corresponding key, if so, opens door
	 * removes key from dungeon and sets key of player to null.
	 * Called when a player attempts to walk on the door.
	 * 
	 * @param p - Player that is attempting to open the door.
	 */
	public void openDoor(Player p) {
		if (p.getKeyId() == id) {
			opened = true; //could make a use key function for these
			p.getKey().setPickedUp(false);
			p.getDungeon().removeEntity(p.getKey());
			p.setKey(null);
			
			System.out.println("Door is opened!");
		}
	}
	
	/**
	 * If door is attempted to be walked on when closed, openDoor method will be called.
	 * If the door is still not opened after openDoor method, the player cannot move.
	 * @param p - the player walking on the square
	 * @param direction - the direction the player is moving.
	 */
	@Override
	public void squareBehav(Player p, String direction) {
		if (!opened) {
			openDoor(p);
		}
		if (!opened) {
			p.setCanMove(false);
		}
	}
}
