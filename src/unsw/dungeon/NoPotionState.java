package unsw.dungeon;

/**
 * A class representing the potion state of the player.
 * This is the default player state, meaning the player is
 * not under the effects of a potion. In this state, enemies
 * chase players and will kill them if stepped on.
 * @authors Waqif Alam and Owen Silver
 *
 */
public class NoPotionState implements PotionState {

	/**
	 * Changes from no potion state to potion state.
	 */
	@Override
	public PotionState changeToPotionState() {
		// TODO Auto-generated method stub
		return new PotionStatePlayer();
	}

	/**
	 * Makes no change because potion state is already no potion state.
	 */
	@Override
	public PotionState changeToNoPotionState() {
		// TODO Auto-generated method stub
		return this;
	}

}
