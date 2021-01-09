package fr.ubx.poo.model.decor;

public class DoorPrevOpened  extends Decor{

	
	@Override
	public String toString() {
	return "doorprevopened";
}
	@Override
	public boolean marcherdessus() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRemovable() {
		// TODO Auto-generated method stub
		return false;
	}

}
