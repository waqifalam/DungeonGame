package unsw.dungeon.objectives;

import java.util.ArrayList;

public class EnemyObjective implements Objective {
	public boolean complete = false;
	
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return complete;
	}

	@Override
	public void addChild(Objective o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeChild(Objective o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void complete(Objective o) {
		// TODO Auto-generated method stub
		this.complete = true;
	}

	@Override
	public void incomplete(Objective o) {
		// TODO Auto-generated method stub
		this.complete = false;
	}

	@Override
	public ArrayList<Objective> getObjectives() {
		// TODO Auto-generated method stub
			return null;
		}

}
