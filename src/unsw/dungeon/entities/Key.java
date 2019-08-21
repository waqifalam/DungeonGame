package unsw.dungeon.entities;

import unsw.dungeon.Dungeon;

public class Key extends Entity {
	private int id;
	private Dungeon dungeon;
	private boolean pickedUp; //so that we can display or not display key without having to remove it from dungeon.
	private boolean justDropped; //so that we dont repickup the key when we drop it down
	
	public Key(Dungeon dungeon, int x, int y, int id) {
		super(x, y);
		this.id = id;
		this.dungeon = dungeon;
		this.pickedUp = false;
		this.justDropped = false;
		// TODO Auto-generated constructor stub
	}

	public void setX(int x1) {
		// TODO Auto-generated method stub
		x().set(x1);
	}
	
	public void setY(int x1) {
		// TODO Auto-generated method stub
		y().set(x1);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override //previously we set justdropped to false after using it.
	public void squareBehav(Player p, String direction) {
		if (pickedUp) return; //do nothing because we already have the key
		if (justDropped) return;
		p.setKey(this);
		this.setPickedUp(true);

		
	}

	public void setPickedUp(boolean b) {
		this.pickedUp = b;
		if (b) {
			System.out.println("Picked up key" + this.getId());
		}
		else {
			System.out.println("Put down key" + this.dungeon.getPlayer().getKeyId()); //change this long chain haha
		}
	}

	public void setJustDropped(boolean b) {
		//System.out.println("setting just dropped to" + b);
		this.justDropped = b;
	}
}
