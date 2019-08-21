package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.entities.Player;

class CharacterMoveTests {

	@Test
	void moveUp() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		int x = player.getX();
		int y = player.getY();
		player.moveUp();
		assertTrue(x==player.getX() && y==player.getY());
	}
	
	@Test
	void moveDown() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		int x = player.getX();
		int y = player.getY();
		player.moveDown();
		assertTrue(x==player.getX() && y+1==player.getY());
	}
	
	@Test
	void moveLeft() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		int x = player.getX();
		int y = player.getY();
		player.moveLeft();
		assertTrue(x==player.getX() && y==player.getY());
	}
	
	@Test
	void moveRight() throws FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		Player player = dungeon.getPlayer();
		int x = player.getX();
		int y = player.getY();
		player.moveLeft();
		assertTrue(x==player.getX() && y==player.getY());
	}

}
