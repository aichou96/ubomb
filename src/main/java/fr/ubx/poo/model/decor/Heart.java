package fr.ubx.poo.model.decor;

public class Heart extends Decor{
	public String toString() {
		return "coeur";
	}

	@Override
	public boolean marcherdessus() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isRemovable() {
		// TODO Auto-generated method stub
		return true;
	}

}
