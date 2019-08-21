package unsw.dungeon.entities;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Observer;
import unsw.dungeon.Subject;

public class Boulder extends Entity implements Subject{
	private boolean canMove;
	private Dungeon dungeon;
	private boolean alive;
	private ArrayList observers = new ArrayList();
	
	public Boulder(Dungeon dungeon, int x, int y) {
		super(x, y);
		this.canMove = true;
		this.dungeon = dungeon;
		this.alive = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void squareBehav(Player p, String direction) {
		switch(direction){
		case "right":
			canMove = dungeon.makeMoveBoulderOrEnemy(getX()+ 1, getY()); //doesnt check if edge of map
			if (canMove) {
	            x().set(getX() + 1);
	            addObservers();
	            notifyObservers();
			}
			else {
				p.setCanMove(false);
			}
			break;
		case "left":
			canMove = dungeon.makeMoveBoulderOrEnemy(getX()- 1, getY());
			if (canMove) {
	            x().set(getX() - 1);
	            addObservers();
	            notifyObservers();
			}
			else {
				p.setCanMove(false);
			}
			break;
		case "up":
			canMove = dungeon.makeMoveBoulderOrEnemy(getX(), getY()-1);
			if (canMove) {
	            y().set(getY() - 1);
	            addObservers();
	            notifyObservers();
			}
			else {
				p.setCanMove(false);
			}
			break;
		case "down":
			canMove = dungeon.makeMoveBoulderOrEnemy(getX(), getY()+1);
			if (canMove) {
	            y().set(getY() + 1);
	            addObservers();
	            notifyObservers();
			}
			else {
				p.setCanMove(false);
			}
			break;
		
		}
	}

	public void addObservers() {
		observers.add(dungeon);
	}
	
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		int i = observers.indexOf(o);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer)observers.get(i);
			observer.update(this);
		}
	}
	public void killBoulder() {
		this.alive = false;
	}

}
