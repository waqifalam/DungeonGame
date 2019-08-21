package unsw.dungeon.objectives;

import java.util.ArrayList;

public class StrategyObjective implements Objective {
	private ArrayList<Objective> children = new ArrayList<Objective>();
	private ObjectiveCheck strategy = new AndObjectives(); //change this accordingly
	
	public StrategyObjective(ArrayList<Objective> children, ObjectiveCheck ANDorOR) {
		this.children = children;
		this.strategy = ANDorOR;
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return strategy.checkComplete(children);
	}

	@Override
	public void addChild(Objective o) {
		// TODO Auto-generated method stub
		children.add(o);
	}

	@Override
	public void removeChild(Objective o) {
		// TODO Auto-generated method stub
		children.remove(o);
	}

	@Override
	public void complete(Objective o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void incomplete(Objective o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Objective> getObjectives() {
		// TODO Auto-generated method stub
		return this.children;
	}


	
	
}
