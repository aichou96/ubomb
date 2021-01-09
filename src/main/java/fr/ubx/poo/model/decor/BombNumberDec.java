package fr.ubx.poo.model.decor;

public class BombNumberDec extends Decor {
	@Override
	public String toString() {
		return "bombnumberdec";
	}

	@Override
	public boolean marcherdessus() {
		
		return true;
	}

	@Override
	public boolean isRemovable() {
		
		return true;
	}
	

}
