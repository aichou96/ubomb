package fr.ubx.poo.model.decor;

public class DoorNextClosed extends Decor {
	@Override
	public String toString() {
		return "doornextclosed";
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
