package unsw.dungeon.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import unsw.dungeon.Dungeon;
import unsw.dungeon.NoPotionState;
import unsw.dungeon.Observer;
import unsw.dungeon.PotionState;
import unsw.dungeon.PotionStateThread;
import unsw.dungeon.Subject;
import unsw.dungeon.Weapon;

/**
 * The player entity representing the player of the game. Players are used to
 * as a means move through the game and complete level objectives.
 * @author Robert Clifton-Everest, Waqif Alam and Owen Silver
 *
 */
public class Player extends Entity implements Subject {

    private Dungeon dungeon; //Dungeon player is within
    private boolean canMove; //Boolean representing if a player can move or not
    private List<Observer> enemies = new ArrayList<Observer>(); //List of enemies within dungeon.
  //State representing whether a a player is under the effects of a potion.
    private PotionState potionState = new NoPotionState(); 
    private Weapon weapon = null; //the weapon the player is currently holding
    private List<Bomb> bombs = new ArrayList<Bomb>(); //list of bombs the player is holding
    private List<Treasure> treasures = new ArrayList<Treasure>();  //list of treasures colected
    private Key key; //current key being held.
    boolean alive = true;

	/**
     * Create a player positioned in square (x,y)
     * @param x - the x coordinate of the square the player will be created on
     * @param y - the y coordinate of the square the player will be created on
     * @param dungeon - the dungeon that the player is contained within.
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
        this.canMove = true;
    }

    /**
     * After checking if it is a valid move in the dungeon, this will move the player up.
     * The method also notifies any observers of the player that the player has moved.
     */
    public void moveUp() {
    	dungeon.makeMovePlayer(getX(), getY()-1, "up");
        if ((getY() > 0) && (canMove)) {
            y().set(getY() - 1);
            addObserver();
            notifyObservers();
        }
        canMove = true; //BAD DESIGN?
    }
    
    /**
     * After checking if it is a valid move in the dungeon, this will move the player down.
     * The method also notifies any observers of the player that the player has moved.
     */
    public void moveDown() {
    	dungeon.makeMovePlayer(getX(), getY()+1, "down");
        if ((getY() < dungeon.getHeight() - 1)&& (canMove)) {
            y().set(getY() + 1);
            addObserver();
            notifyObservers();
        }
        canMove = true; //BAD DESIGN?
    }

    /**
     * After checking if it is a valid move in the dungeon, this will move the player left.
     * The method also notifies any observers of the player that the player has moved.
     */
    public void moveLeft() {
    	dungeon.makeMovePlayer(getX()-1, getY(), "left");
        if ((getX() > 0)&& (canMove)) {
            x().set(getX() - 1);
            addObserver();
        	notifyObservers();
        }
        canMove = true; //BAD DESIGN?
    }

    /**
     * After checking if it is a valid move in the dungeon, this will move the player right.
     * The method also notifies any observers of the player that the player has moved.
     */
    public void moveRight() {
    	dungeon.makeMovePlayer(getX()+1, getY(), "right");
        if ((getX() < dungeon.getWidth() - 1)&& (canMove)){ //should separate first if for before makeMovePlayer call
            x().set(getX() + 1);
            addObserver();
            notifyObservers();
        }
        canMove = true; //BAD DESIGN?
    }

    /**
     * Returns the canMove boolean which represents whether a player can move or not.
     * @return boolean - canMove, whether the player is able to move on a particular turn.
     */
	public boolean isCanMove() {
		return canMove;
	}
	/**
	 * Sets the canMove attribute that represents whether a player can move or not.
	 * @param canMove - boolean representing whether a player can move.
	 */
	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	/**
	 * Registers all enemies as observers.
	 */
	@Override
	public void registerObserver(Observer o) {
		if (!enemies.contains(o)) {
			enemies.add(o);
		}	
	}

	/**
	 * Calls registerObserver method on all enemies to add them as observers
	 * to be notified when a player moves.
	 */
	public void addObserver() {
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Enemy) {
				registerObserver((Observer)e);
			}
		}
	}
	/**
	 * Removes an observer (enemy) from the notify list.
	 */
	@Override
	public void removeObserver(Observer o) {
		enemies.remove(o);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (Observer o : enemies) {
			o.update(this);
		}
	}
	
	public List<Observer> getEnemies(){
		return this.enemies;
	}

	/**
	 * Return the dungeon copy that the player stores, representing the dungeon
	 * that the player is playing in.
	 * @return Dungeon instance player is playing in.
	 */
	public Dungeon getDungeon() {
		return dungeon;
	}
	
	/**
	 * Changes a player to the potion state, starting a timer that will run for X seconds
	 * after which the player will be returned to normal state.
	 */
	public void changeToPotionState() {
		potionState = potionState.changeToPotionState();
		PotionStateThread potionThread = new PotionStateThread(this);
		potionThread.start();
	}
	
	/**
	 * Returns player to original state that is not under effects of potion.
	 */
	public void changeToNoPotionState() {
		potionState = potionState.changeToNoPotionState();
	}

	/**
	 * Returns the potion state of the player.
	 * @return - PotionState representing the potion state of the player.
	 */
	public PotionState getPotionState() {
		return potionState;
	}
	
	/**
	 * Sets the weapon the player has access to.
	 * @param w - the weapon wishing to be set for the player.
	 */
	public void setWeapon(Weapon w) {
		this.weapon = w;
	}
	
	/**
	 * If a player has a weapon, this will call the weapons corresponding
	 * attack method.
	 */
	public void attack() {
		if (this.weapon != null) {
		 	weapon.attack(this);
		} 
	}
	
	/**
	 * Adds a bomb to the players inventory of bombs.
	 * @param b - the Bomb being added.
	 */
	public void addBomb(Bomb b) {
		bombs.add(b);
	}
	
	/**
	 * Returns the list of bombs the player currently holds.
	 * @return - List<Bomb> of bombs the player currently holds.
	 */
	public List<Bomb> getBombs() {
		return this.bombs;
	}
	
	/**
	 * If a player has bombs, uses the most recent bomb that the player has picked up.
	 */
	public void useBomb() {
		if (bombs.size() > 0) {
			System.out.println("Attacking");
			bombs.get(bombs.size()-1).lightBomb();
			bombs.remove(bombs.size() - 1);
		}
	}


	/**
	 * Returns a list of all treasures a player has collected.
	 * @return - List of all treasure a player has collected.
	 */
	public List<Treasure> getTreasures() {
		return treasures;
	}
	
	/**
	 * Adds a treasure to the list that a player has collected.
	 * @param treasure - the treasure wishing to be added.
	 */
	public void addTreasure(Treasure treasure) {
		this.treasures.add(treasure);
	}
	
	/**
	 * Called when a player steps on a key, sets the key of the player to the key passed in.
	 * If a player already has a key, the old key will be dropped on the square the new key
	 * was picked up. Players must attempt to move once before they can pick back the old
	 * key.
	 * @param k - the key wishing to be set as the players current key.
	 */
	public void setKey(Key k) {
		if (k == null) { //we could make a property/state called nokey if we wanted to.
			this.key = null;
		}
		if (key != null) { //puts key down where other key was
			System.out.println("Player stepped on key, with id " + k.getId());
			key.setX(k.getX());
			key.setY(k.getY());
			key.setPickedUp(false);
			key.setJustDropped(true); //set just dropped attribute so key isnt immediately
								      //picked back up
		}
		key = k;
	}
	
	/**
	 * Returns the current key the player is holding.
	 * @return Key that the player is holding.
	 */
	public Key getKey() {
		return this.key;
	}
	/**
	 * Returns the id of the key that the player is holding. If 
	 * the player is not holding a key, will return -1.
	 * @return
	 */
	public int getKeyId() {
		if (key == null) return -1;
		return key.getId();
	}

	/**
	 * Returns the instance of the weapon the player is currently wielding.
	 * @return - the instance of the weapon the player is using.
	 */
	public Weapon getWeapon() {
		return this.weapon;
	}

	/**
	 * sets player alive to false indicating player is dead.
	 */
	public void killPlayer() {
		System.out.println("killing player");
		this.alive = false;
		
	}
	/**
	 * indicates whether the player is alive
	 * @return - boolean, true for alive, false for dead.
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
}
