package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Player;
import unsw.dungeon.entities.Sword;

class SwordTests {

	@Test
	void sword_is_removed_from_dungeon_when_walked_on() throws FileNotFoundException {
		MazeController maze = new MazeController("maze18.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();

		Sword sword = null;
		assertTrue(sword == null, "no sword yet, should be null");
		ArrayList<Entity> entList = dungeon.getEntOnSq(1, 2);
		for (Entity e : entList) {
			if (e instanceof Sword) {
				sword = (Sword)e;
			}
		}
		assertTrue(dungeon.getEntities().contains(sword), "sword still in dungeon");
		player.moveDown();
		assertFalse(dungeon.getEntities().contains(sword), "sword removed from dung, stepped on");
	}
	@Test
	void player_can_carry_only_one_sword() throws FileNotFoundException {
		MazeController maze = new MazeController("maze18.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		Object o = player.getWeapon();
		assertTrue(!(o instanceof List));
	}
	
	@Test
	void player_can_kill_enemy() throws FileNotFoundException {
		MazeController maze = new MazeController("maze18.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.attack();
		List<Entity> entities = dungeon.getEntOnSq(1, 3);
		assertTrue(entities.size()==0);
	}
	
	@Test
	void sword_has_5_hits() throws FileNotFoundException {
		MazeController maze = new MazeController("maze18.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.attack();
		player.attack();
		player.attack();
		player.attack();
		player.attack();
		assertTrue(player.getWeapon()==null);
	}

}
