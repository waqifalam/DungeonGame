package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.NoPotionState;
import unsw.dungeon.PotionStatePlayer;
import unsw.dungeon.entities.*;

class PotionTests {

	@Test
	void potion_removed_from_dungeon_when_stepped_on() throws FileNotFoundException {
		MazeController maze = new MazeController("potionTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		ArrayList<Entity> PotionList = dungeon.getEntOnSq(1, 2);
		Entity myPotion = null;
		assertFalse(dungeon.getEntities().contains(myPotion), "should be false, myPotion is null");
		for (Entity e: PotionList) {
			if (e instanceof Potion) {
				myPotion = e;
			}
		}
		assertTrue(dungeon.getEntities().contains(myPotion), "Should be true, potion is in dungeon");
		player.moveDown();
		assertFalse(dungeon.getEntities().contains(myPotion), "Should be false, potion is not in dungeon");
		
	}
	@Test
	void potion_changes_player_state() throws FileNotFoundException {
		MazeController maze = new MazeController("potionTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		assertTrue(player.getPotionState() instanceof NoPotionState, "true, potion not drank yet");
		assertFalse(player.getPotionState() instanceof PotionStatePlayer, "false potion not drank yet");
		player.moveDown();
		assertFalse(player.getPotionState() instanceof NoPotionState, "false, in potion state");
		assertTrue(player.getPotionState() instanceof PotionStatePlayer, "true, in potion state");
	}
	@Test
	void player_kills_enemy_with_potion() throws FileNotFoundException {
		MazeController maze = new MazeController("potionTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.addObserver();
		Enemy myEnemy = (Enemy) player.getEnemies().get(0);
		assertEquals(myEnemy.getAlive(), true, "true, enemy is alive");
		player.moveDown();
		player.moveDown();
		assertEquals(myEnemy.getAlive(), false, "false, enemy has been walked on with potion");
	}
	@Test
	void enemy_runs_from_player_with_potion() throws FileNotFoundException {
		MazeController maze = new MazeController("potionTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.addObserver();
		Enemy myEnemy = (Enemy) player.getEnemies().get(0);
		int xDiff1 = player.getX() - myEnemy.getX();
		int yDiff1 = player.getY() - myEnemy.getY();
		player.moveDown(); //Player is in potion state
		player.moveUp(); 
		player.moveDown();//return to original position, enemy should move on 3rd walk (this)
		int xDiff2 = Math.abs(player.getX() - myEnemy.getX());
		int yDiff2 = Math.abs(player.getY() - myEnemy.getY());
		assertTrue(((xDiff1 < xDiff2) || (yDiff1 < yDiff2)), "enemy should have moved away");
		
	}
	
	@Test
	void potion_has_timer() throws FileNotFoundException, InterruptedException {
		MazeController maze = new MazeController("potionTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();//pick up potion
		assertTrue(player.getPotionState() instanceof PotionStatePlayer, "player picked up potion");
		TimeUnit.SECONDS.sleep(15);
		assertFalse(player.getPotionState() instanceof PotionStatePlayer, "potion timer run out");

	}
}
	