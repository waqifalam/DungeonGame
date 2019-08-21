package unsw.dungeon.entities;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Observer;

/**
 * The bomb class is a dungeon entity that can be picked up and used by a 
 * player. When used (lit), the bomb starts a timer which will countdown until
 * explosion. Upon explosion all boulders and enemies in the squares immediately
 * above, below, left and right will be destroyed.
 * @author Waqif Alam and Owen Silver
 *
 */
public class Bomb extends Entity implements Runnable{
	private int fuseLength; //Represents the time till detonation for the icons
	private Player player; //Represents the player holding the bomb
	private Dungeon dungeon; //Represents the dungeon the bomb is stored in.
	
	/**
	 * Instantiates bomb
	 * @param x - x coordinate that the bomb is located at
	 * @param y - y coordinate that the bomb is located at
	 * @param dungeon - dungeon the bomb is located within
	 */
	public Bomb(int x, int y, Dungeon dungeon) {
		super(x, y);
		fuseLength = 3;
		this.dungeon = dungeon;
	}
	
	/**
	 * Called when stepped on by a player, this method will pick the bomb
	 * up into the players bomb list and remove the bomb from the 
	 * floor. Override from entity.
	 */
	@Override
	public void squareBehav(Player p, String direction) {
		System.out.println("STEPPED ON A Bomb");
		Dungeon dungeon = p.getDungeon();
		p.addBomb(this);
		this.player = p;
		dungeon.removeEntity(this);
	}
	
	/**
	 * Lights the bomb which creates a thread with a timer on it.
	 * Sets coordinates based on the square where player used it.
	 */
	public void lightBomb() {
		setX(player.getX());
		setY(player.getY());
		Thread t1 = new Thread(this);
		t1.start();
		
		
	}
	/**
	 * Sets the x coordinate of the bomb
	 * @param x - the x coordinate of the bomb
	 */
	public void setX(int x) {
		x().set(x);
	}
	
	/**
	 * Sets the y coordinate of the bomb
	 * @param y - the y coordinate of the bomb
	 */
	public void setY(int y) {
		// TODO Auto-generated method stub
		y().set(y);
	}
	
	/**
	 * Called after bomb timer has elapsed, this calls a function to check the 
	 * immediate left,right,up,down for any bomb targets to remove from the dungeon.
	 */
	public void detonateBomb() {
		System.out.println("DETONATING BOMB");
		int x = this.getX();
		int y = this.getY();
		if (x> 0) {
			checkBombRadius(x-1, y);
		}
		if (y > 0) {
			checkBombRadius(x, y-1);
		}
		if (x < dungeon.getWidth() - 1) {
			checkBombRadius(x+1, y);
		}
		if (y < dungeon.getHeight() - 1) {
			checkBombRadius(x, y+1 );
		}
	}
	
	/**
	 * Checks a specific location for any entities that are effected by a bomb.
	 * If it is there it will kill them enemy or remove the boulder from the dungeon.
	 * @param x
	 * @param y
	 */
	private void checkBombRadius(int x, int y) {
		ArrayList<Entity> entOnSq = dungeon.getEntOnSq(x, y);
		for (Entity e : entOnSq) {
			if (e instanceof Enemy) {
				((Enemy) e).killEnemy();
				dungeon.removeEntity(e);
				Player player = dungeon.getPlayer();
				player.removeObserver((Observer) e);
			} else if (e instanceof Boulder) {
				((Boulder) e).killBoulder();
				dungeon.removeEntity(e);
			}
			else if (e instanceof Player) {
				((Player) e).killPlayer();
				dungeon.removeEntity(e	);
			}

		}
	}
	
	/**
	 * The thread logic for the bomb. This method runs as a thread, sleeping for
	 * 3 seconds, decrementing the fuseLength each second. After 3 seconds
	 * detonate will be called. Prints stack trace if exception occurs.
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			fuseLength--; //this should be observed by frontend i think.
			Thread.sleep(1000);
			fuseLength--;
			Thread.sleep(1000);
			fuseLength--;
			this.detonateBomb(); //Can add another second here so we can see the explosion.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
