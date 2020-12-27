package fr.ubx.poo.model.decor;

public class BombNumberInc extends Decor{

	@Override
	public String toString() {
		return "bombe";
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
