package unsw.dungeon.entities;

import java.util.ArrayList;

import unsw.dungeon.Dungeon;
import unsw.dungeon.NoPotionState;
import unsw.dungeon.Observer;
import unsw.dungeon.PotionStatePlayer;
import unsw.dungeon.Subject;

/**
 * The enemy entity class chases the player, moving every 3 moves the player makes.
 * It can only move up, down, left or right. If a player touches an enemy the player dies.
 * These effects are reversed if a player is under the influence of a potion.
 * @author Owen Silver and Waqif Alam
 *
 */
public class Enemy extends Entity implements Observer {
	private Dungeon dungeon; //Dungeon the Enemy is contained in
	private int moveCounter; //Counts every 3 moves of the player
	private boolean alive; //Whether the enemy should be displayed and methods work.
	
	/**
	 * Instantiates an enemy.
	 * @param dungeon - the dungeon the enemy is located within
	 * @param x - the x coordinate the enemy currently lies
	 * @param y - the y coordinate the enemy currently lies
	 */
	public Enemy(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		this.moveCounter = 0;
		this.alive = true;
	}
	/**
	 * Returns whether the enemy can be considered dead (false) or alive (true)
	 * @return - boolean of whether enemy is alive.
	 */
	public boolean getAlive() {
		return this.alive;
	}

	/**
	 * If a player steps on an enemy, they are killed, unless they are
	 * under the influence of a potion, in which the enemy is killed.
	 */
	@Override
	public void squareBehav(Player p, String direction) {
		if (alive) {
			if (p.getPotionState() instanceof NoPotionState) {
				System.out.println("STEPPED ON AN ENEMY");
				System.exit(1);
			} else {
				System.out.println("You are invincible!!!!");
				this.killEnemy();
			}
		}
	}

	/**
	 * Update method called every time a player moves.
	 * Increases a counter, after 3 increases the enemy will move towards the player.
	 */
	@Override
	public void update(Subject o) {
		if (this.alive) {
			System.out.println("Enemy updating");
			this.moveCounter ++;
			if (this.moveCounter == 3) {
				System.out.println("moving enemy");
				moveTowardsPlayer();
				this.moveCounter = 0;
			}
		}
	}
	/**
	 * This method calculates an x and y direction that an enemy should
	 * move in order to catch the player. If the player is under the effects
	 * of the potion, the direction will be reversed by multiplying changes by -1.
	 * The priority of move direction is decided by which distance is larger (x or y)
	 * between enemy and player. If the first priority direction cannot be moved to
	 * the second priority will be tried. If neither of these are possible, the enemy
	 * will not move.
	 */
	public void moveTowardsPlayer() {
		int calcdX = 0;
		int calcdY = 0;
		int xDir = 1; //default change in x direction
		int yDir = 1; //default change in y direction
		boolean blockX = false;
		boolean blockY = false;
		String priority = "x"; //priority direction
		//if xdiff is positive player is on right of enemy
		int xDiff = dungeon.getPlayer().getX() - this.getX();
		if (xDiff == 0) {
			blockX = true;
			System.out.println("Blocking x");
		}


		//if ydiff is positive player is below enemy
		int yDiff = dungeon.getPlayer().getY() - this.getY();
		if (yDiff == 0) {
			blockY = true;
			System.out.println("blockingY");
		}
		
		//if abs(xDiff) > abs(yDiff) decrease xDiff by moving toward playerX.
		if (Math.abs(xDiff) >= Math.abs(yDiff)) {
			priority = "x";
		}
		else if (Math.abs(xDiff) < Math.abs(yDiff)){
			priority = "y";
		}

		if (xDiff < 0){ //change direction if player is to left
			xDir = -1;
		}
		if (yDiff <  0) { //change direction if player is above
			yDir = -1;
		}
		if (this.dungeon.getPlayer().getPotionState() instanceof PotionStatePlayer) {
			System.out.println("Potion state reversion"); //reverse if under potion state.
			xDir = xDir * -1;
			yDir = yDir * -1;
			//could change direction here too.
		}
		if (priority == "x") {
			if (!blockX) {
				System.out.println(priority + xDir + yDir);
				calcdX = this.getX() + xDir;
				calcdY = this.getY();
				if (dungeon.makeMoveBoulderOrEnemy(calcdX, calcdY)) { //try move in priority direction
					x().set(calcdX);
					y().set(calcdY);
				}
				else { //otherwise try second priority
					calcdX = this.getX();
					calcdY = this.getY() + yDir;
					if (dungeon.makeMoveBoulderOrEnemy(calcdX, calcdY)) {
						x().set(calcdX);
						y().set(calcdY);
					} 
				}
			}
		}
		else if (priority == "y"){ //try move priority y
			if (!blockY) {
				calcdX = this.getX();
				calcdY = this.getY() + yDir;
				if (dungeon.makeMoveBoulderOrEnemy(calcdX, calcdY)) {
					x().set(calcdX);
					y().set(calcdY);
				}
				else { //otherwise try second priority
					calcdX = this.getX() + xDir;
					calcdY = this.getY();
					if (dungeon.makeMoveBoulderOrEnemy(calcdX, calcdY)) {
						x().set(calcdX);
						y().set(calcdY);
					} 
				}
			}
		}
	}
	/**
	 * Kills the enemy, notifies players and completes the enemy objective for a dungeon if
	 * there are no more enemies.
	 */
	public void killEnemy() {
		this.alive = false;
		System.out.println("Killed the enemy");
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Enemy) {
				return;
			}
		}
		
		dungeon.completeEnemyObjective(dungeon.getObjective());
	}
	

}