package unsw.dungeon;

import unsw.dungeon.entities.Player;

public class PotionStateThread extends Thread{
	private Player player;
	
	public PotionStateThread(Player p) {
		this.player = p;
	}
	
	public void run() {
		try { Thread.sleep(10000); } catch (Exception e) {}
		player.changeToNoPotionState();
	}
}
