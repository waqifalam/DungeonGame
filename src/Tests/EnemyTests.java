package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.*;

class EnemyTests {

	@Test
	void enemy_move_up_down_left_right() throws FileNotFoundException {
		MazeController maze = new MazeController("maze19.json");
		Dungeon dungeon = maze.load();
		Enemy enemy = new Enemy(dungeon, 3, 3);
		
		//move right
		enemy.x().set(enemy.getX() + 1);
		enemy.y().set(enemy.getY());
		assertTrue(enemy.getX() == 4, "x coord should be 4");
		assertTrue(enemy.getY() == 3, "y coord should be 3");
	}
}
	