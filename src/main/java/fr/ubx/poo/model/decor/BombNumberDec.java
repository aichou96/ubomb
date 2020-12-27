package fr.ubx.poo.model.decor;

public class BombNumberDec extends Decor {
	@Override
	public String toString() {
		return "Bomb number";
	}

	@Override
	public boolean marcherdessus() {
		
		return false;
	}

	@Override
	public boolean isRemovable() {
		
		return false;
	}
	

}
