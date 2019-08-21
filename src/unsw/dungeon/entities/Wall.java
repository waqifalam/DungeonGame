package unsw.dungeon.entities;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }

	@Override
	public void squareBehav(Player p, String direction) {
		p.setCanMove(false);
	}
    
}
