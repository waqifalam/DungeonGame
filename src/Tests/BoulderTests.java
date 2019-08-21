package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

class BoulderTests {

	@Test
	void moving_boulder() throws FileNotFoundException {
		MazeController maze = new MazeController("maze10.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		List<Entity> entities = dungeon.getEntOnSq(1,3);
		boolean moved = false;
		for (Entity e: entities) {
			if (e instanceof Boulder) {
				moved = true;
			}
		}
		
		assertTrue(player.getY()==2 && moved);
	}
	
	@Test
	void moving_boulder_into_another_boulder() throws FileNotFoundException {
		MazeController maze = new MazeController("maze11.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		List<Entity> entities = dungeon.getEntOnSq(1,4);
		boolean moved = false;
		for (Entity e: entities) {
			if (e instanceof Boulder) {
				moved = true;
			}
		}
		assertTrue(player.getY()==1 && !moved);
	}
	
	@Test
	void moving_boulder_into_wall() throws FileNotFoundException {
		MazeController maze = new MazeController("maze12.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		List<Entity> entities = dungeon.getEntOnSq(1,4);
		boolean moved = false;
		for (Entity e: entities) {
			if (e instanceof Boulder) {
				moved = true;
			}
		}
		assertTrue(player.getY()==1 && !moved);
	}
	
	@Test
	void moving_boulder_into_switch() throws FileNotFoundException {
		MazeController maze = new MazeController("maze13.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		List<Entity> entities = dungeon.getEntOnSq(1,3);
		boolean moved = false;
		for (Entity e: entities) {
			if (e instanceof Boulder) {
				moved = true;
			}
		}
		assertTrue(player.getY()==2 && moved);
	}

}
