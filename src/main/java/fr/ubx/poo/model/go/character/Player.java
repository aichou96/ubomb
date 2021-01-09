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
    private  int lives = 1;
    private boolean winner;
    private boolean loseHert=false;
    boolean putbom;
    private long time;
    private int nbKey=0;
	private int nbBomb=3;

	
    public int getNbBomb() {
		return nbBomb;
	}
	public void setNbBomb(int nbBomb) {
		this.nbBomb = nbBomb;
	}


 

    
    public void setNbKey(int nbKey) {
		this.nbKey = nbKey;
	}
	

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
    public int getKey() {
        return nbKey;
    }
    public int getBomb() {
        return nbBomb;
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
   if(lives<3) {
  lives ++;
  game.getWorld().setChanged(true);}
  return true;
  }
  if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("princess") ) {
      winner=true;
	  return true;
	  }
  if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("key") ) {
  nbKey ++;
  game.getWorld().setChanged(true);
  return true;
  }
   if(game.getWorld().get(nextPos)!=null && 
		  game.getWorld().get(nextPos).toString().equalsIgnoreCase("doornextclosed") && nbKey!=0) {
	   game.getMapWorldStatus().put("key"+game.getNiveau()+1, game.getWorld());
	   game.setNiveau(game.getNiveau()+1);
  	 game.getWorld().setNextWorld(true);
  	 return true;
  	}
	 if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("doorprevopened")) {
 	  	
 	  	game.setNiveau(game.getNiveau()-1);
 	//  game.setWorld(game.getMapWorldStatus().get("key"+(game.getNiveau()-1)));
 	  	 game.getWorld().setPreviousWorld(true);	 
 	
 	 return true;
	 }
	 return (game.getWorld().get(nextPos)==null || game.getWorld().get(nextPos).marcherdessus());
 
   }

    /*  public boolean canMove(Direction direction) {
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
 		if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("key") ) {
 			nbKey ++;
 			game.getWorld().setChanged(true);
 			return true;
 		}
 		if(game.getWorld().get(nextPos)!=null && 
				  game.getWorld().get(nextPos).toString().equalsIgnoreCase("doornextclosed") && nbKey!=0) {
			   //game.setNiveau(level);
		  	  //level++;
		  	 //game.getWorld().setNextWorld(true);
			game.increaseLevel();
			game.setNextChangeWorld(true);
			
		  	 return true;
		  	}
			 if(game.getWorld().get(nextPos)!=null && game.getWorld().get(nextPos).toString().equalsIgnoreCase("doorprevopened")) {
		 	  	// level--;
		 	  	//game.setNiveau(level-1);
		 	  	// game.getWorld().setPreviousWorld(true);	
				 game.decreaseLevel();
				game.setPrevChangeWorld(true);
				
		 	
		 	 return true;
			 }


		return (game.getWorld().get(nextPos)==null || game.getWorld().get(nextPos).canWalk());
		
 }*/


    public void doMove(Direction direction) {
    Position nextPos = direction.nextPosition(getPosition());
    Position nextP2= direction.nextPosition(nextPos);
         Decor decor=game.getWorld().get(nextPos);
         if(decor !=null && decor.toString().equalsIgnoreCase("box")) {
         
          game.getWorld().clear(nextPos);
          game.getWorld().set(nextP2, decor);
         
         }
         if(decor !=null && decor.toString().equalsIgnoreCase("bombnumberdec")) {
        	 nbBomb--;
             game.getWorld().clear(nextPos);
             game.getWorld().setChanged(true);
         }
         if(decor !=null && decor.toString().equalsIgnoreCase("bombnumberinc")) {
        	 nbBomb++;
             game.getWorld().clear(nextPos);
             game.getWorld().setChanged(true);
         }
         if( decor !=null && decor.toString().equalsIgnoreCase("heart")) {
        game.getWorld().clear(nextPos);
         }
         if( decor !=null && decor.toString().equalsIgnoreCase("key")) {
         game.getWorld().clear(nextPos);
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
  
  public void setBomb(boolean v) {
    	
    	putbom=v;
    }

    public boolean isWinner() {
        return winner;
    }

    public boolean isAlive() {
    	if(lives==0) {
    		alive=false;
    	}
        return alive;
    }

	

}
