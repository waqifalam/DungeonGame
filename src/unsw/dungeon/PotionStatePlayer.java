package unsw.dungeon;

public class PotionStatePlayer implements PotionState {

	@Override
	public PotionState changeToPotionState() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public PotionState changeToNoPotionState() {
		// TODO Auto-generated method stub
		return new NoPotionState();
	}

}
