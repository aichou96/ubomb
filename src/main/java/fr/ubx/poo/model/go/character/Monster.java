package fr.ubx.poo.model.go.character;

import fr.ubx.poo.game.Dimension;
import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Stone;
import fr.ubx.poo.model.decor.Tree;
import fr.ubx.poo.model.go.GameObject;

public class Monster extends GameObject implements Movable {
	Direction direction;
	public long time;

	public Monster(Game game, Position position) {
		super(game, position);
		 this.direction = Direction.E;
		 this.time=0;
		// TODO Auto-generated constructor stub
	}
	
	public boolean canMove(Direction direction) {
	   	Position nextPos = direction.nextPosition(getPosition());
    	
 	   Dimension d = game.getWorld().dimension;
 		if (!nextPos.inside(d))
 			return false;
 		if ((game.getWorld().get(nextPos)) instanceof Stone)
 			return false;
 		
 		if ((game.getWorld().get(nextPos)) instanceof Tree)
 			return false;
 		if ((game.getWorld().get(nextPos)) instanceof Box)
 			return false;
		return true;
	}
	
	public void doMove(Direction direction) {
	     Position nextPos = direction.nextPosition(getPosition());
	     setPosition(nextPos);
	 }
	  public Direction getDirection() {
	        return direction;
	   }
	  public void update(long now) {   
			 if(now-time>=(0.5)*Math.pow(10,9)) {
		      if(canMove(direction)) {
		                doMove(direction);
	             }
		     	direction=Direction.random();
		     	time=now;
			 }
			 }

	

}
