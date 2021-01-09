package fr.ubx.poo.game;

import java.io.InputStream;

public class World2 extends WorldFile{
	
	public World2() {
		super(worldFromFile(cheminfichier2()));
	}
	
	public static  InputStream cheminfichier2() {
		
		return World2.class.getResourceAsStream("/sample/level2.txt");
	}

}
