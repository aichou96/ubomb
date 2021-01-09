package fr.ubx.poo.game;

import java.io.InputStream;

public class World3 extends WorldFile {
	public World3() {
		super(worldFromFile(cheminfichier()));
	}
	
	public static  InputStream cheminfichier() {
		
		return World3.class.getResourceAsStream("/sample/level3.txt");
	}
	
	
}
