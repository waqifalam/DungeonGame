package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Player;

class ExitTests {

	@Test
	void complete_objectives() throws FileNotFoundException {
		MazeController maze = new MazeController("maze16.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.moveDown();
		assertTrue(dungeon.getComplete());
	}
	
	@Test
	void not_complete_objectives() throws FileNotFoundException {
		MazeController maze = new MazeController("maze17.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.moveDown();
		assertTrue(!dungeon.getComplete());
	}

}
