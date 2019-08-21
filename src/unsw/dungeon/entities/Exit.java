package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

public class Exit extends Entity {
	private Dungeon dungeon;
	public Exit(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.dungeon = dungeon;
		// TODO Auto-generated constructor stub
	}
	//the exit could have state.
	@Override
	public void squareBehav(Player p, String direction) {
		System.out.println("Found an exit");
 //if dungeon is complete
			//end game?
			dungeon.completeExitObjective(dungeon.getObjective());
	}

}
