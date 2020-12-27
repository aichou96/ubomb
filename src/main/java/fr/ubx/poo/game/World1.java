package fr.ubx.poo.game;

public class World1 extends WorldFile{

	public World1() {
		super(worldFromFile(cheminfichier()));
	}
	
	public static  String cheminfichier() {
		
		return "/sample/level1.txt";
	}

}
