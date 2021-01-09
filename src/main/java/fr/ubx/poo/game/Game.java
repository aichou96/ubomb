/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import fr.ubx.poo.model.decor.Decor;
import fr.ubx.poo.model.go.character.Bomb;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;

public class Game {
	
    private final Player player;
    private final String worldPath;
    private final long now;
    public int initPlayerLives;
    private Map <Integer, List<Monster> >  monsters=new HashMap<>();
    private List<World> world = new ArrayList<>();
    private int niveau=0;
    private Map<String, World> mapWorldStatus = new HashMap<>();
    //mettre les variables detat ici

    
    public List<World> getListWorld(){
    	return world;
    } 
    public Map<String, World> getMapWorldStatus() {
		return mapWorldStatus;
	}
	public Game(String worldPath) {
    	 World1 world1=new World1();
    	 World2 world2=new World2();
    	 World3 world3=new World3();
    	 world.add(world1);
    	 world.add(world2);
    	 world.add(world3);
       
        this.worldPath = worldPath;
        loadConfig(worldPath);
        Position positionPlayer = null;
        List<Position> positionMonsters=new ArrayList<>();
        now=(long) 0;
        try {
        	
            positionPlayer = world.get(niveau).findPlayer();
            player = new Player(this, positionPlayer);
            
            for (int i = 0; i < world.size(); i++) {
            	positionMonsters = world.get(i).findMonsters();
            	for(Position p:positionMonsters ) {
            		if(monsters.get(i)==null) {
            			monsters.put(i, new ArrayList<>());
            		}
                    monsters.get(i).add( new Monster(this, p));
                    
                    }
			}
            
           
           } catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        
    }
    public void newPositon() {
    	try {
    		 Position positionDoorPrevOpened = null;
    		 positionDoorPrevOpened = world.get(niveau).findDoorPrevOpened();
         	 player.setPosition(positionDoorPrevOpened);
         	 player.setPosition(player.getDirection().nextPosition(positionDoorPrevOpened));
      
    	}catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
    }
    }
    
    public void newPositon1() {
    
    	try {
    		 Position positionDoorPrevOpened = null;
    		 positionDoorPrevOpened = world.get(niveau).findDoorNextClosed();
         	 player.setPosition(positionDoorPrevOpened);
         	 player.setPosition(player.getDirection().nextPosition(positionDoorPrevOpened));
         
         
    	}catch (PositionNotFoundException e) {
            System.err.println("Position not found : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
    }
    }
    

    public int getInitPlayerLives() {
        return initPlayerLives;
    }

    private void loadConfig(String path) {
        try (InputStream input = new FileInputStream(new File(path, "config.properties"))) {
            Properties prop = new Properties();
            // load the configuration file
            prop.load(input);
            initPlayerLives = Integer.parseInt(prop.getProperty("lives", "3"));
        } catch (IOException ex) {
            System.err.println("Error loading configuration");
        }
    }

    public World getWorld() {
        return world.get(niveau);
    }
    public void setWorld(World world) {
		this.world.set(niveau, world);
	}
	public int getNiveau() {
    	return niveau;
    }
    public void setNiveau(int i) {
    	this.niveau=i;
    }

    public Player getPlayer() {
        return this.player;
    }
    public List< Monster> getMonster() {
        return this.monsters.get(niveau);
    }
    /*
     *  private boolean nextchangeWorld=false;
    private boolean prevchangeWorld=false;
 public void increaseLevel() {
    	level=level+1;
    }
    public void decreaseLevel() {
    	level=level-1;
    }
*/
 
    //m�thode monde suivant
    

}
