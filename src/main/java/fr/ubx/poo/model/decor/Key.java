package fr.ubx.poo.model.decor;

public class Key extends Decor{

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

	@Override
    public String toString() {
        return "Key";
    }
}
