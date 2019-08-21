package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

class TreasureTests {
	//Add check to see if removed from dungeon, check to see if cant be stepped on twice
	@Test
	void treasure_collected_when_walked_on() throws FileNotFoundException {
		MazeController maze = new MazeController("treasureTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		assertEquals(player.getTreasures().size(), 0, "Size should be 0, no treasures yet");
		player.moveDown();
		assertEquals(player.getTreasures().size(), 1, "Size should be 1, picked up treasure");
	}
	
	@Test
	void multiple_treasures_collected() throws FileNotFoundException{
		MazeController maze = new MazeController("treasureTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		assertEquals(player.getTreasures().size(), 0, "Size should be 0, no treasures yet");
		player.moveDown();
		assertEquals(player.getTreasures().size(), 1, "Size should be 1, picked up treasure");
		player.moveDown();
		assertEquals(player.getTreasures().size(), 2, "Size should be 2, picked up another");
	}
	@Test
	void all_treasure_collected_is_objective() throws FileNotFoundException{
		MazeController maze = new MazeController("treasureTests.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		assertEquals(dungeon.getObjective().isComplete(), false, "should be false, no treasure collected");
		player.moveDown();
		assertEquals(dungeon.getObjective().isComplete(), false, "false, only 1/2 treasure collected");
		player.moveDown();
		assertEquals(dungeon.getObjective().isComplete(), true, "true, 2/2 treasure colld");
	}
}