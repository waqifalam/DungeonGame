/**
 *
 */
package unsw.dungeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Key;
import unsw.dungeon.entities.PPlate;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Wall;
import unsw.dungeon.objectives.BoulderObjective;
import unsw.dungeon.objectives.EnemyObjective;
import unsw.dungeon.objectives.ExitObjective;
import unsw.dungeon.objectives.Objective;
import unsw.dungeon.objectives.TreasureObjective;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square. A dungeon also contains a list of objectives required to 
 * be completed before a player can complete the level.
 *
 * @author Robert Clifton-Everest, Owen Silver and Waqif Alam.
 *
 */
public class Dungeon implements Observer{

    private int width, height;
    private List<Entity> entities;
    private Player player;
    private List<PPlate> plates = new ArrayList<PPlate>();
    private Objective objective;
    private boolean complete = false;
    //private ArrayList<Objective> objectives = new ArrayList<Objective>();
    //to make things quicker, it may be worth having a list of switches, list of treasure... etc, so we can check objectives quicker.s

    /**
     * Dungeon constructor
     * @param width - width of dungeon in square blocks
     * @param height - height of dungeon in square blocks- 
     */
    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.player = null;
    }
    /**
     * Used to set final objective of dungeon, which can be composed of 1 or more objectives.
     * @param o - the final objective to be set.
     */
    public void setFinalObjective (Objective o) {
    	this.objective = o;
    }
    /**
     * Returns the width of the dungeon in square blocks.
     * @return - integer width of the dungeon
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the dungeon in square blocks
     * @return - integer height of the dungeon
     */
    public int getHeight() {
        return height;
    }
    /**
     * Get the player currently stored in the dungeon.
     * The Player represents the player of the game in real life.
     * @return Player instance stored by dungeon.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Set Player instance stored in dungeon.
     * The Player represents the player of the game in real life.
     * @param player - the player that is desired to be set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
	/**
	 * Adds an Entity instance into the list of entities the dungeon contains.
	 * @param entity - an Entity instance wished to be added.
	 */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }
    /**
     * Given an x coord, y coord and a direction that the entity wishes to travel, this method
     * will call getEntOnSq to get a list of all entities on the square being moved to.
     * Then for each entity on the square, the corresponding "squarebehaviour" is called.
     * @param x - the x coordinate wishing to be moved to.
     * @param y - the y coordinate wishing to be moved to.
     * @param direction - the direction of movement ("left", "right", "up", "down").
     */
    public void makeMovePlayer (int x, int y, String direction) {
    	ArrayList<Entity> entOnSq = getEntOnSq( x, y);
    	for (Entity e: entOnSq) {
    		e.squareBehav(this.player, direction);
    	}
    }
    
    //Do we pass in the thing we are trying to move and then (i.e we call make move with *this* as the player or *this* as the boulder
    //makeMove would still either need multiple functions or to check instance of here.
    /**
     * This move method is more specifically for entities that cannot pick up items 
     * but are still blocked by walls and boulders. If an enemy moves onto a player
     * the game will end.
     * @param x - the x coord wishing to be moved to.
     * @param y - the y coord wishing to be moved to.
     * @return - a boolean representing whether a move can be made.
     */
    public boolean makeMoveBoulderOrEnemy (int x, int y) {
    	ArrayList<Entity> entOnSq = getEntOnSq( x, y);
    	for (Entity e: entOnSq) {
    		if (e == null) continue;
    		if (e instanceof Wall) {
    			return false;
    			
    		}
    		if (e instanceof Boulder) {
    			return false;
    		}
    		if ( e instanceof Player) { //we should try make another interface
    			System.out.println("Enemy caught a player");
    			System.exit(1);
    		}
    	}
    	return true;
    }
    /**
     * Generates an arraylist of entities on a given square by going through all entities
     * in a dungeon and checking if the x and y coordinates of that entity match
     * the x and y coordinates passed in.
     *   
     * @param x - the x coordinate of the square that the list will be generated for.
     * @param y - the y coordinate of the square that the list will be generated for.
     * @return - an ArrayList<Entity> with all entities on the square of (x,y).
     */
    public ArrayList<Entity> getEntOnSq (int x, int y) {
    	ArrayList<Entity> entOnSq = new ArrayList<Entity>();
    	for (Entity e: this.entities) {
    		if (e == null) {
    			continue;
    		}
    		//TEMPORARY REMOVE/move THIS
    		if (e instanceof Key) {
    			((Key) e).setJustDropped(false);
    		}
    		if ((e.getX() == x) && (e.getY() == y)) {
    			entOnSq.add(e);
    		}
    	}
		return entOnSq;
    }
    
    /**
     * Returns the list of entities stored within the dungeon, representing all
     * the entities in the dungeon.
     * @return - List<Entity> a list of the entities within the dungeon.
     */
    public List<Entity> getEntities(){
		return this.entities;
    	
    }

    /**
     * Returns a list of pressure plates within a dungeon.
     * @return - List<PPlate> list of pressure plates within a dungeon.
     */
	public List<PPlate> getPlates() {
		return plates;
	}

	/**
	 * Goes through all entities in a dungeon and adds only Pressure plates to 
	 * a new list for faster access.
	 */
	public void setPlates() {
		for (Entity e : entities) {
			if (e instanceof PPlate) {
				if (!plates.contains(e)) {
					plates.add((PPlate) e);
				}
			}
		}
	}
	/**
	 * Dungeon implementation of update method as an observer. Updated when a boulder is 
	 * moved this method will count if there is a boulder on every switch in the dungeon
	 * completing the dungeon boulder objective if this is the case.
	 * @param Subject that the dungeon observes (Boulder instance).
	 */
	@Override
	public void update(Subject o) {
		int count = 0;
		setPlates();
		// TODO Auto-generated method stub
		for (PPlate p : getPlates()) {
			ArrayList<Entity> entOnSq = getEntOnSq( p.getX(), p.getY());
	    	for (Entity e: entOnSq) {
	    		if (e instanceof Boulder) {
	    			count = count +1;
	    			//System.out.println(count + " plates are pressed");
	    		}
	    	}
		}
		if (count == getPlates().size()) {
			completeBoulderObjective(this.getObjective());
		}else {
			resetBoulderObjective(this.getObjective());
		}
	}
	/**
	 * Removes an entity from the dungeon entity list
	 * @param e1 - the Entity instance wishing to be removed.
	 */
	public void removeEntity(Entity e1) {
		this.entities.remove(e1);
	}
	
	/**
	 * Recursive function that takes in an Objective (composite), starting with
	 * the final objective of the dungeon, breaks the objective into smaller pieces through
	 * recursion and checks if any of these are Exit Objectives. In this case, the objective
	 * is completed, we then check if all dungeon objectives are complete which will end the
	 * level if true.
	 * @param obj - the instance implementing Objective interface we are looking within.
	 */
	public void completeExitObjective(Objective obj) {
		if (obj == null) return;
		if (obj.getObjectives() == null) {
			if (obj instanceof ExitObjective) {
				System.out.println("Instance of Exit objective");
				obj.complete(obj);
			}
			return;
		}
		for (Objective o : obj.getObjectives()) {
			System.out.println(o);
			completeExitObjective(o);
		}
		checkObjectives();
	
	}
	/**
	 * Calls a method in the checkObjectives class to see if all objectives are complete
	 * if true, the game will be finished, represented by a system.exit currently.
	 */
	private void checkObjectives() {
		// TODO Auto-generated method stub

		System.out.println("Checking objectives");
		this.complete = objective.isComplete();
		if (complete) {
			System.out.println("Objectives Complete");
		}

	}
	
	public boolean getComplete() {
		return this.complete;
	}
	
	/**
	 * Recursive function that takes in an Objective (composite), starting with
	 * the final objective of the dungeon, breaks the objective into smaller pieces through
	 * recursion and checks if any of these are Boulder Objectives. In this case, the objective
	 * is completed, we then check if all dungeon objectives are complete which will end the
	 * level if true.
	 * @param obj - the instance implementing Objective interface we are looking within.
	 */
	public void completeBoulderObjective(Objective obj) {
		if (obj == null) return;
		if (obj.getObjectives() == null) {
			if (obj instanceof BoulderObjective) {
				System.out.println("Instance of Boulder obj");
				obj.complete(obj);
			}
			return;
		}
		for (Objective o : obj.getObjectives()) {
			System.out.println(o);
			completeBoulderObjective(o);
		}
		checkObjectives();
	}
	
	/**
	 * Recursive function that takes in an Objective (composite), starting with
	 * the final objective of the dungeon, breaks the objective into smaller pieces through
	 * recursion and checks if any of these are Treasure Objectives. In this case, the objective
	 * is completed, we then check if all dungeon objectives are complete which will end the
	 * level if true.
	 * @param obj - the instance implementing Objective interface we are looking within.
	 */
	public void completeTreasureObjective(Objective obj) {
		if (obj == null) return;
		if (obj.getObjectives() == null) {
			if (obj instanceof TreasureObjective) {
				System.out.println("Instance of treasure");
				obj.complete(obj);
			}
			return;
		}
		for (Objective o : obj.getObjectives()) {
			System.out.println(o);
			completeTreasureObjective(o);
		}
		checkObjectives();
	}
	/**	
	 * Recursive function that takes in an Objective (composite), starting with
	 * the final objective of the dungeon, breaks the objective into smaller pieces through
	 * recursion and checks if any of these are Enemy Objectives. In this case, the objective
	 * is completed, we then check if all dungeon objectives are complete which will end the
	 * level if true.
	 * @param obj - the instance implementing Objective interface we are looking within.
	 */
	public void completeEnemyObjective(Objective obj) {
		if (obj == null) return;
		if (obj.getObjectives() == null) {
			if (obj instanceof EnemyObjective) {
				System.out.println("Instance of enemyObj");
				obj.complete(obj);
			}
			return;
		}
		for (Objective o : obj.getObjectives()) {
			System.out.println(o);
			completeEnemyObjective(o);
		}
		checkObjectives();

	}
	/**
	 * Returns the final objective stored within the dungeon, this can be comprised of multiple
	 * sub objectives
	 * @return - an objective representing all objectives required of a level.
	 */
	public Objective getObjective() {
		return objective;
	}
	
	/**
	 * Removes a boulder objective in case a boulder is moved away from the switch again
	 * @param currObj - the objective currently passed in to be broken down and recursed.
	 */
	public void resetBoulderObjective(Objective currObj) {
		if (currObj == null ) return;
		if (currObj.getObjectives() == null) {
			if (currObj instanceof BoulderObjective) {
				currObj.incomplete(currObj);
				System.out.println("Reset boulder objectives");
				checkObjectives();
				return;
			}
		}
		for (Objective o : currObj.getObjectives()) {
			resetBoulderObjective(o);
		}

	}
}
