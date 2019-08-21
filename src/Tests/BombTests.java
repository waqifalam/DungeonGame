package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.Observer;
import unsw.dungeon.entities.Bomb;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Player;

class BombTests {

	@Test
	void player_picks_bomb() throws FileNotFoundException {
		MazeController maze = new MazeController("maze8.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		assertTrue(player.getBombs().size()==1);
	}
	@Test
	void bomb_removed_from_dungeon_when_stepped_on() throws FileNotFoundException {
		MazeController maze = new MazeController("maze8.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		Bomb myBomb = null;
		assertFalse(dungeon.getEntities().contains(myBomb), "Should not contain null");
		ArrayList<Entity> entList = dungeon.getEntOnSq(1, 2);
		for (Entity e: entList) {
			if (e instanceof Bomb) {
				myBomb = (Bomb) e;
			}
		}
		assertTrue(dungeon.getEntities().contains(myBomb), "Should contain bomb before stepped on");
		player.moveDown();
		assertFalse(dungeon.getEntities().contains(myBomb), "Shouldnt contain bomb, stepped on");
	}
	
	@Test
	void player_picks_multiple_bomb() throws FileNotFoundException {
		MazeController maze = new MazeController("maze8.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.moveDown();
		assertTrue(player.getBombs().size()==2);
	}
	
	@Test
	void player_kill_enemy_after_3_secs() throws FileNotFoundException, InterruptedException {
		MazeController maze = new MazeController("advanced2.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.useBomb();
		TimeUnit.SECONDS.sleep(1);
		List<Entity> entities = dungeon.getEntities();
		int x = 0;
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				x=x+1;
			}
		}
		assertTrue(x==1, "Enemy should be in dungeon because bomb hasnt detonated");
		TimeUnit.SECONDS.sleep(3);
		entities = dungeon.getEntities();
		int x2 = 0;
		for (Entity e : entities) {
			if (e instanceof Enemy) {
				x2=x2+1;
			}
		}
		assertTrue(x2==0, "Enemy should be removed from dungeon since dead");
	}
	@Test
	void bomb_kills_boulders() throws FileNotFoundException, InterruptedException{
		MazeController maze = new MazeController("bombTestsAllBoulders.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		Bomb b = new Bomb(1,1, dungeon);
		System.out.println(player.getX());
		b.squareBehav(player, "left"); //pick up bomb
		int numBoulders = 0;
		ArrayList<Entity> checklist;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Boulder) {
				numBoulders++;
			}
		}
		assertEquals(numBoulders, 45, "there are initially 45 boulders");
		checklist = dungeon.getEntOnSq(player.getX()+1, player.getY());
		assertEquals(checklist.size(), 1, "one bould to right");
		checklist = dungeon.getEntOnSq(player.getX()-1, player.getY());
		assertEquals(checklist.size(), 1, "one bould to left");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()-1);
		assertEquals(checklist.size(), 1, "one bould to up");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()+1);
		assertEquals(checklist.size(), 1, "one bould to down");
		player.useBomb();
		TimeUnit.SECONDS.sleep(4); //bomb will have detonated
		int numBoulders2 = 0;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Boulder) {
				numBoulders2++;
			}
		}
		assertEquals(numBoulders2, numBoulders-4 , "4 boulders have been removed");
		checklist = dungeon.getEntOnSq(player.getX()+1, player.getY());
		assertEquals(checklist.size(), 0, "no entities to right");
		checklist = dungeon.getEntOnSq(player.getX()-1, player.getY());
		assertEquals(checklist.size(), 0, "no entities to left");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()-1);
		assertEquals(checklist.size(), 0, "no entities to up");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()+1);
		assertEquals(checklist.size(), 0, "no entities to down");
		
	}
	@Test
	void bomb_attacks_only_left_right_up_down() throws FileNotFoundException, InterruptedException{
		MazeController maze = new MazeController("bombTestsAllEnemies.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		Bomb b = new Bomb(1,1, dungeon);
		System.out.println(player.getX());
		b.squareBehav(player, "left");
		int numEnemies = 0;
		ArrayList<Entity> checklist;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Enemy) {
				numEnemies++;
			}
		}
		assertEquals(numEnemies, 45, "there are initially 45 enemies");
		checklist = dungeon.getEntOnSq(player.getX()+1, player.getY());
		assertEquals(checklist.size(), 1, "one enemy to right");
		checklist = dungeon.getEntOnSq(player.getX()-1, player.getY());
		assertEquals(checklist.size(), 1, "one enemy to left");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()-1);
		assertEquals(checklist.size(), 1, "one enemy to up");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()+1);
		assertEquals(checklist.size(), 1, "one enemy to down");
		player.useBomb();
		TimeUnit.SECONDS.sleep(4); //bomb will have detonated
		int numEnemies2 = 0;
		for (Entity e : dungeon.getEntities()) {
			if (e instanceof Enemy) {
				numEnemies2++;
			}
		}
		assertEquals(numEnemies2, numEnemies-4 , "4 enemies have been killed");
		checklist = dungeon.getEntOnSq(player.getX()+1, player.getY());
		assertEquals(checklist.size(), 0, "no entities to right");
		checklist = dungeon.getEntOnSq(player.getX()-1, player.getY());
		assertEquals(checklist.size(), 0, "no entities to left");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()-1);
		assertEquals(checklist.size(), 0, "no entities to up");
		checklist = dungeon.getEntOnSq(player.getX(), player.getY()+1);
		assertEquals(checklist.size(), 0, "no entities to down");
		
	}
	@Test
	void bomb_can_kill_player() throws FileNotFoundException, InterruptedException{
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		Bomb b = new Bomb(1,2, dungeon);
		b.squareBehav(player, "down");
		player.moveDown();
		assertTrue(player.isAlive(), "player should be alive, no bomb used yet");
		player.useBomb();
		player.moveUp();
		TimeUnit.SECONDS.sleep(4);
		assertFalse(player.isAlive(), "player should be dead, in bomb radius");
		
		
		
	}
}
