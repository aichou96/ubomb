package fr.ubx.poo.game;

import java.io.InputStream;



public class World1 extends WorldFile{

	public World1() {
		super(worldFromFile(cheminfichier()));
	}
	
	public static  InputStream cheminfichier() {
		
		return World1.class.getResourceAsStream("/sample/level1.txt");
	}

}
