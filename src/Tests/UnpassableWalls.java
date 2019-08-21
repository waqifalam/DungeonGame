package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Player;

class UnpassableWalls {

	@Test
	void character_cannot_move_to_wall() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		int x = player.getX();
		int y = player.getY();
		player.moveUp();
		assertTrue(x==player.getX() && y==player.getY());
	}
	
	@Test
	void enemy_cannot_move_to_wall() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		assertTrue(dungeon.makeMoveBoulderOrEnemy(0, 0)==false);
	}

}
