package fr.ubx.poo.model.decor;

public class Monster extends Decor {
	@Override
	public String toString()
	{
		return "monstre";
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

}
