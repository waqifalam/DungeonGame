package unsw.dungeon.objectives;

import java.util.ArrayList;

/**
 * Interface for Objectives, requiring methods to check, complete/incomplete subObjectives.
 * @authors Owen Silver and Waqif Alam
 *
 */
public interface Objective {
	/**
	 * Checks if the objective is complete, logic differs between AND and OR objs
	 * @return boolean representing completion
	 */
	public boolean isComplete();
	
	/**
	 * Add a child objective
	 * @param o - child objective to be added
	 */
	public void addChild(Objective o);
	
	/**
	 * Remove a child objective
	 * @param o - child objective to be removed.
	 */
	public void removeChild(Objective o);
	
	/**
	 * If the objective is a leaf objective, it will change the objective to true
	 * @param o - the objective to complete if called recursively.
	 */
	public void complete(Objective o);
	
	/**
	 * If objective is a leaf, will change complete attribute to false, otherwise recursively
	 * calls to incomplete a given attribute.
	 * @param o
	 */
	public void incomplete(Objective o);
	
	/**
	 * Gets a list of all child objectives
	 * @return ArrayList<Objective> of all child objectives.
	 */
	public ArrayList<Objective> getObjectives();
}