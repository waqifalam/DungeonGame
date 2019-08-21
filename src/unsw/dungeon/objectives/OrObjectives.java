package unsw.dungeon.objectives;

import java.util.ArrayList;

public class OrObjectives implements ObjectiveCheck {

	@Override
	public boolean checkComplete(ArrayList<Objective> children) {
		// TODO Auto-generated method stub
		for (Objective o : children) {
			if (o.isComplete()) {
				return true;
			}
		}
		
		return false;
	}

}
