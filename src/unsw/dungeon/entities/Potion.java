package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

public class Potion extends Entity {

	public Potion(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void squareBehav(Player p, String direction) {
		System.out.println("STEPPED ON AN Potion");
		Dungeon dungeon = p.getDungeon();
		p.changeToPotionState();
		dungeon.removeEntity(this);
	}
}
