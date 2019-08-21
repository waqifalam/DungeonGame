package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Player;

class SwitchTests {

	@Test
	void switches_must_be_pressed() throws FileNotFoundException {
		MazeController maze = new MazeController("maze14.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		assertTrue(dungeon.getComplete()==true);
	}
	
	@Test
	void boulder_moved_away_from_switch() throws FileNotFoundException {
		System.out.println("failed test--------------");
		MazeController maze = new MazeController("maze14.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		System.out.println("first movedown test--------------");
		player.moveDown();
		System.out.println("second movedown test--------------");
		player.moveDown();
		System.out.println("third movedown test--------------");
		assertTrue(dungeon.getComplete()==false, "Dungeon should be incomplete");
	}

}
