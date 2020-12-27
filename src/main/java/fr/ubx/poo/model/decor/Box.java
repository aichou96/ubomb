package fr.ubx.poo.model.decor;

import fr.ubx.poo.game.Game;
import fr.ubx.poo.view.image.ImageFactory;

public class Box  extends Decor{
	
	
	
	@Override
	public String toString() {
		
		return "box";
	}

	@Override
	public boolean marcherdessus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRemovable() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void updateImage() {
		//setImage(ImageFactory.getInstance().getNumBomb(4));
	}
}
