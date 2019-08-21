package Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import org.json.JSONException;


class CharacterTests {

	@Test
	void character_present() throws JSONException, FileNotFoundException {
		MazeController maze = new MazeController("maze.json");
		Dungeon dungeon = maze.load();
		assertTrue(dungeon!=null);
	}
	
	@Test
	void no_entities_on_character() throws JSONException, FileNotFoundException {
		MazeController maze = new MazeController("maze2.json");
		Dungeon dungeon = maze.load();
		assertTrue(dungeon==null);
	}
}
