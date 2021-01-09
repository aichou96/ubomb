package fr.ubx.poo.model.go.character;

import java.util.*;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.GameObject;

public class Bomb extends GameObject {
	public int numBomb=0;
	public long time;
	private int j=0;
	private long timeinvisible;
	private int porte;
	
	

	public Bomb(Game game, Position position) {

		super(game, position);
		this.time=0;
		this.timeinvisible=0;
		// TODO Auto-generated constructor stub

	}
	public long getTimeinvisible() {
		return timeinvisible;
	}
	public void setTimeinvisible(long time) {
		this.timeinvisible=time;
	}
	
	public void remove() {
		Direction n=Direction.N;
		Direction s=Direction.S;
		Direction w=Direction.W;
		Direction e=Direction.E;

		Position p1=n.nextPosition(getPosition());
		Position p2=s.nextPosition(getPosition());
		Position p3=w.nextPosition(getPosition());
		Position p4=e.nextPosition(getPosition());
		List <Position> L=new ArrayList<Position> ();
		L.add(p1);
		L.add(p2);
		L.add(p3);
		L.add(p4);
		L.add(getPosition());
		for (Position p:L) {
			if(game.getWorld().get(p)!=null) {
				if(game.getWorld().isInside(p)&& (game.getWorld().get(p).isRemovable())) {
					game.getWorld().clear(p);
				}

			}
			if(j!=1) {
				if(p.equals(game.getPlayer().getPosition())) {
				timeinvisible=(long) (4*Math.pow(10, 9));
					game.getPlayer().loseOneLife();
					j++;
				}
				
			}
		   for(int i=0; i<game.getMonster().size();i++) {
		     if( getPosition().equals(game.getMonster().get(i).getPosition())) {
		     game.getMonster().remove(i);
		         }
		     }
		}
	}
		   
	
	public void update(long now) {
		if(time==0)
			time=now;
		else if(now-time>=Math.pow(10,9)) {
			if(numBomb<4) {
				numBomb++;
			}
			
			else if(numBomb==4) {
				game.getWorld().setChanged(true);
			}
			time=now;
			
			
		}
		/*else {
		time=time+ System.nanoTime();}*/

	}


}
