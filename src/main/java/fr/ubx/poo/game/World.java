/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import fr.ubx.poo.model.decor.Decor;

public class World {
    private final Map<Position, Decor> grid;
    private final WorldEntity[][] raw;
    public final Dimension dimension;
    private boolean changed = false;
    private boolean changeBomb=false;

 

	public World(WorldEntity[][] raw) {
        this.raw = raw;
        dimension = new Dimension(raw.length, raw[0].length);
        grid = WorldBuilder.build(raw, dimension);
    }

    public Position findPlayer() throws PositionNotFoundException {
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                if (raw[y][x] == WorldEntity.Player) {
                    return new Position(x, y);
                }
            }
        }
        throw new PositionNotFoundException("Player");
    }
    
    public List<Position> findMonsters() throws PositionNotFoundException {
    	List<Position> l = new ArrayList<>();
        for (int x = 0; x < dimension.width; x++) {
            for (int y = 0; y < dimension.height; y++) {
                
                if (raw[y][x] == WorldEntity.Monster) {
                	l.add(new Position(x, y));
                }
            }
        }
        
        if(l.size()==0) {
            throw new PositionNotFoundException("Monster");
            }
            return l;
        }



    
    

    public Decor get(Position position) {
        return grid.get(position);
    }
    public Map<Position, Decor> getGrid() {
    	return grid;
    }

    public void set(Position position, Decor decor) {
        grid.put(position, decor);
    }

    public void clear(Position position) {
        grid.remove(position);
    }

    public void forEach(BiConsumer<Position, Decor> fn) {
        grid.forEach(fn);
    }

    public Collection<Decor> values() {
        return grid.values();
    }
    public boolean hasChanged() { 
    	return changed; 
    }
    public void setChanged(boolean changed) { 
    	this.changed=changed; 
    }

    public boolean isInside(Position position) {
    	
    	 return position.x >= 0 && position.x < dimension.width && 
    			 position.y >= 0 && position.y < dimension.height;
    	
       // return true; // to update
    }
   

    public boolean isEmpty(Position position) {
        return grid.get(position) == null;
    }
    public boolean isChangeBomb() {
 		return changeBomb;
 	}

 	public void setChangeBomb(boolean changeBomb) {
 		this.changeBomb = changeBomb;
 	}
}
