package fr.ubx.poo.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class  WorldFile extends World {


	
	
	/*public  Reader fichier() throws IOException {
		Reader v = null;
		BufferedReader in = null;
	 try  {
		    in = new BufferedReader(new FileReader(cheminfichier()));
	     v=in;
	    } catch (IOException ex) {
	        System.err.println("Error loading configuration");
	    }
	 finally{
		 in.close();
		 }
	 return v;
	 }*/
	
    public WorldFile(WorldEntity[][] raw) {
		super(raw);
		// TODO Auto-generated constructor stub
	}

	public static  WorldEntity[][] worldFromFile(String chemin) {
    	
    	WorldEntity[][] raw1=null;
    	
    	BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(chemin));
			String line;
	    	List<WorldEntity[]> list=new ArrayList<>();
			while ((line = in.readLine()) != null)
			{
				WorldEntity[] we=new WorldEntity[line.length()];
				for(int j=0; j<we.length; j++) {
					we[j]=WorldEntity.fromCode(line.charAt(j));
				}
				list.add(we);
			}
			raw1=(WorldEntity[][]) list.toArray();
			in.close();
				
		} catch ( IOException e) {
			System.out.println("ereur");
		}
    	
		return raw1;
    }
	
	//private  final WorldEntity[][] w = worldFromFile();
	
	


}
