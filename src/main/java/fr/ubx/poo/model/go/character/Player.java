/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.model.go.character;

import java.util.List;

import fr.ubx.poo.engine.GameEngine;
import fr.ubx.poo.game.Dimension;
import fr.ubx.poo.game.Direction;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.Movable;
import fr.ubx.poo.model.decor.Box;
import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.decor.Heart;
import fr.ubx.poo.model.decor.Monster;
import fr.ubx.poo.model.decor.Princess;
import fr.ubx.poo.model.decor.Stone;
import fr.ubx.poo.model.decor.Tree;
import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.game.Game;

public class Player extends GameObject implements Movable {

    private  boolean alive = true;
    Direction direction;
    private boolean moveRequested = false;
    private  int lives = 3;
    private boolean winner;
    private boolean loseHert=false;
    boolean putbom;
    private long time;
 

    

	public boolean isLoseHert() {
		return loseHert;
	}

	public void setLoseHert(boolean loseHert) {
		this.loseHert = loseHert;
	}

	public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.S;
        this.lives = game.getInitPlayerLives();
        this.time=0;
    }

    public int getLives() {
        return lives;
    }
    public void loseOneLife() {
        lives=lives-1;
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
        }
        moveRequested = true;
    }

    @Override
    public boolean canMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        Position nextP2= direction.nextPosition(nextPos);
        Dimension d = game.getWorld().dimension;
      if (!nextPos.inside(d))
      return false;
      if(game.getWorld().get(nextPos)!=null) {
      if (game.getWorld().get(nextPos).toString().equalsIgnoreCase("box")) {
      if(nextP2.inside(d) && game.getWorld().isEmpty(nextP2) ) {
      game.getWorld().setChanged(true);
      return true;
      }else {
      return false;
      }
      }
      }
      if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("heart") ) {
      lives ++;
      game.getWorld().setChanged(true);
      return true;
      }

    return (game.getWorld().get(nextPos)==null || game.getWorld().get(nextPos).marcherdessus());
     
       }
 
    
    public void doMove(Direction direction) {
        Position nextPos = direction.nextPosition(getPosition());
        Position nextP2= direction.nextPosition(nextPos);
             Decor decor=game.getWorld().get(nextPos);
             if(decor !=null && decor.toString().equalsIgnoreCase("box")) {
             
              game.getWorld().clear(nextPos);
              game.getWorld().set(nextP2, decor);
             
             }
             if( decor !=null && decor.toString().equalsIgnoreCase("heart")) {
            game.getWorld().clear(nextPos);
            //game.getWorld().set(nextP2, null);
             }
             setPosition(nextPos);

        }


    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
      for(int i=0; i<game.getMonster().size();i++) {
     if(now-time>=Math.pow(10,10) && getPosition().equals(game.getMonster().get(i).getPosition())) {
          lives=lives-1;
          time=now;
          }
      }
       
       
        moveRequested = false;
    }
    public boolean getBomb() {
    	
    	return putbom;
    }
  public void setBomb(boolean v) {
    	
    	putbom=v;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
        return alive;
    }

	

}
