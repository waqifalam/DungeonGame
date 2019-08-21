package Tests;

import java.io.FileNotFoundException;
import java.util.List;

import javafx.scene.image.ImageView;
import unsw.dungeon.Dungeon;
import unsw.dungeon.ApplicationClasses.DungeonControllerLoader;
import unsw.dungeon.ApplicationClasses.DungeonLoader;
import unsw.dungeon.entities.Bomb;
import unsw.dungeon.entities.Boulder;
import unsw.dungeon.entities.Door;
import unsw.dungeon.entities.Enemy;
import unsw.dungeon.entities.Entity;
import unsw.dungeon.entities.Exit;
import unsw.dungeon.entities.Key;
import unsw.dungeon.entities.PPlate;
import unsw.dungeon.entities.Potion;
import unsw.dungeon.entities.Sword;
import unsw.dungeon.entities.Treasure;
import unsw.dungeon.entities.Wall;

public class MazeController extends DungeonLoader {

	public MazeController(String filename) throws FileNotFoundException {
		super(filename);
	}

	@Override
	public void onLoad(Entity player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Wall wall) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Exit exit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Enemy enemy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Treasure treasure) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Bomb bomb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Boulder boulder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(PPlate p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Key key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Door door) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Potion potion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoad(Sword sword) {
		// TODO Auto-generated method stub
		
	}



}
