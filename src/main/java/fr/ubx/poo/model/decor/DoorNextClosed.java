package fr.ubx.poo.model.decor;

public class DoorNextClosed extends Decor {
	@Override
	public String toString() {
		return "door closed";
	}

	@Override
	public boolean marcherdessus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRemovable() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
