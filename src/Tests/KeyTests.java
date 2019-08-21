package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Player;

class KeyTests {

	@Test
	void key_opens_door() throws FileNotFoundException {
		MazeController maze = new MazeController("maze15.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		assertTrue(player.getX()==1 && player.getY()==2 && player.getKey().getId()==0);
	}
	
	@Test
	void can_only_pick_one_key() throws FileNotFoundException {
		MazeController maze = new MazeController("maze15.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		player.moveDown();
		player.moveDown();
		player.moveDown();
		assertTrue(player.getX()==1 && player.getY()==4 && player.getKey().getId()==1);
	}

}
