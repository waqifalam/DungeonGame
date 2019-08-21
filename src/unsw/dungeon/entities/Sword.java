package unsw.dungeon.entities;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Weapon;

/**
 * The Sword class is a type of weapon that can be picked up by players.
 * It can be used 5 times before breaking and being unable to be used.
 * @author Owen Silver and Waqif Alam
 *
 */
public class Sword extends Entity implements Weapon{
	private int attacks = 5; //The number of attacks the weapon has
	private Player p; //The player using the sword.
	
	/**
	 * 
	 * @param x - the x coordinate on which the sword lies
	 * @param y - the y coordinate on which the sword lies
	 */
	public Sword(int x, int y) {
		super(x, y);
	}

	/**
	 * Method override of Entity squareBehaviour, called when a player walks on this
	 * object. The player picks up this weapon and it is removed from the floor of the dungeon.
	 */
	@Override
	public void squareBehav(Player p, String direction) {
		System.out.println("STEPPED ON AN Sword");
		Dungeon dungeon = p.getDungeon();
		dungeon.removeEntity(this);
		p.setWeapon(this);
	}

	/**
	 * Overrode method from Weapon interface. The specifics of swords attacks.
	 * Breaks sword after 5 hits, setting players weapon to null.
	 * Sword attacks in the squares immediately above, below, left and right
	 * as if spinning in a 2d block circle.
	 */
	@Override
	public void attack(Player p) {
		// TODO Auto-generated method stub

				System.out.println("USING SWORD");
				int x = p.getX();
				int y = p.getY();
				Dungeon dungeon = p.getDungeon();
				if (x> 0) {
					ArrayList<Entity> entOnSq = dungeon.getEntOnSq(x-1, y);
					for (Entity e : entOnSq) {
						if (e instanceof Enemy) {
							dungeon.removeEntity(e);
							((Enemy) e).killEnemy();
							System.out.println("Killed enemy");
						}
					}
				}
				if (y > 0) {
					ArrayList<Entity> entOnSq = dungeon.getEntOnSq(x, y-1);
					for (Entity e : entOnSq) {
						if (e instanceof Enemy) {
							dungeon.removeEntity(e);
							((Enemy) e).killEnemy();
							System.out.println("Killed enemy");
						}
					}
				}
				if (x < dungeon.getWidth() - 1) {
					ArrayList<Entity> entOnSq = dungeon.getEntOnSq(x+1, y);
					for (Entity e : entOnSq) {
						if (e instanceof Enemy) {
							dungeon.removeEntity(e);
							((Enemy) e).killEnemy();
							System.out.println("Killed enemy");
						}
					}
				}
				if (y < dungeon.getHeight() - 1) {
					ArrayList<Entity> entOnSq = dungeon.getEntOnSq(x, y+1);
					for (Entity e : entOnSq) {
						if (e instanceof Enemy) {
							((Enemy) e).killEnemy();
							dungeon.removeEntity(e);
							System.out.println("Killed enemy");
						}
					}
				}
				attacks --;
				if (attacks < 1) {
					p.setWeapon(null);
					return;
				}
			

	}
}