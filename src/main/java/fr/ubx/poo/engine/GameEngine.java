/*
 * Copyright (c) 2020. Laurent RÃ©veillÃ¨re
 */

package fr.ubx.poo.engine;

import fr.ubx.poo.game.Direction;
import fr.ubx.poo.view.image.ImageFactory;
import fr.ubx.poo.view.sprite.Sprite;
import fr.ubx.poo.view.sprite.SpriteFactory;
import fr.ubx.poo.view.sprite.SpriteMonster;
import fr.ubx.poo.game.Game;
import fr.ubx.poo.game.Position;
import fr.ubx.poo.model.go.character.Bomb;
import fr.ubx.poo.model.go.character.Monster;
import fr.ubx.poo.model.go.character.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.*;
import java.util.Map.Entry;


public final class GameEngine {

	private static AnimationTimer gameLoop;
	private final String windowTitle;
	private final Game game;
	private final Player player;
	// TODO essayer de mettre ces bombes ligne dans une autre classe
	
	//créer des map pour ces sprites
	private Map<Integer ,List<Sprite>> sprites = new HashMap<>();
	private Map<Integer ,List<Sprite>> spriteBomb = new HashMap<>();
	private List<Sprite> spriteMonsters = new ArrayList<>();
	
	// TODO essayer de déplacer ces monstres  dans une autre classe
	private List< Monster> monsters= new ArrayList<>();
	
	private StatusBar statusBar;
	private Pane layer;
	private Input input;
	private Stage stage;
	private Sprite spritePlayer;
	boolean vf=false;




	public GameEngine(final String windowTitle, Game game, final Stage stage) {
		this.windowTitle = windowTitle;
		this.game = game;
		this.player = game.getPlayer();
		initialize(stage, game);
		buildAndSetGameLoop();
	}

	public void controlExplosion(Bomb b) {
		
		for (int i = 0; i < game.getListWorld().size(); i++) {
			System.out.println("oui10");
			if(game.getListWorld().get(i).getBombput().size()!=0) {
				Position p= b.getPosition();
				Direction n=Direction.N;
				Direction s=Direction.S;
				Direction w=Direction.W;
				Direction e=Direction.E;
				Position p1=n.nextPosition(p);
				Position p2=s.nextPosition(p);
				Position p3=w.nextPosition(p);
				Position p4=e.nextPosition(p);
				

				Bomb up=new Bomb(game,p1);
				up.numBomb=4;
				
				spriteBomb.get(i).add(SpriteFactory.createBomb(layer, up));

				Bomb rigth =new Bomb(game,p2);
				rigth.numBomb=4;
				spriteBomb.get(i).add(SpriteFactory.createBomb(layer, rigth));


				Bomb left=new Bomb(game,p3);
				left.numBomb=4;
				spriteBomb.get(i).add(SpriteFactory.createBomb(layer, left));

				Bomb down=new Bomb(game,p4);
				down.numBomb=4;
				spriteBomb.get(i).add(SpriteFactory.createBomb(layer, down));

				
				
			}
		}
		
		    }
	

	private void initialize(Stage stage, Game game) {
		this.stage = stage;
		Group root = new Group();
		layer = new Pane();

		int height = game.getWorld().dimension.height;
		int width = game.getWorld().dimension.width;
		int sceneWidth = width * Sprite.size;
		int sceneHeight = height * Sprite.size;
		Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
		scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

		stage.setTitle(windowTitle);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

		input = new Input(scene);
		root.getChildren().add(layer); 
		statusBar = new StatusBar(root, sceneWidth, sceneHeight, game);
		// Create decor sprites
		// pour chaque monde on cree les sprites decor correspondant
		
		for (int i = 0; i < game.getListWorld().size(); i++) {
			final List<Sprite> s=new ArrayList<>();
			game.getListWorld().get(i).forEach( (pos,d) -> s.add(SpriteFactory.createDecor(layer, pos, d)));
			sprites.put(i, s);
			System.out.println("oui9");
		}
		
		
		spritePlayer = SpriteFactory.createPlayer(layer, player);  
		
		
		
		for (int i = 0; i < game.getListWorld().size(); i++) {
			 List<Sprite> s=new ArrayList<>();
			 System.out.println("oui8");
			 
			for (int j = 0; j < game.getListWorld().get(i).getBombput().size(); j++) {
				s.add(SpriteFactory.createBomb(layer, game.getListWorld().get(i).getBombput().get(j)));
				System.out.println("oui7");
			}
			
			spriteBomb.put(i, s);
		}
		
		monsters=game.getMonster();
		for(int i=0;i<monsters.size();i++) {
	     spriteMonsters.add(SpriteFactory.createMonster(layer, monsters.get(i)));
           System.out.println("oui6");
		}
		
		

	}

	protected final void buildAndSetGameLoop() {
		gameLoop = new AnimationTimer() {
			public void handle(long now) {
				// Check keyboard actions
				processInput(now);

				// Do actions
				update(now);

				// Graphic update
				render();
				statusBar.update(game);
			}
		};
	}

	private void processInput(long now) { 
		if (input.isExit()) {
			gameLoop.stop();
			Platform.exit();
			System.exit(0);
		}
		if (input.isMoveDown()) {
			player.requestMove(Direction.S);
		}
		if (input.isMoveLeft()) {
			player.requestMove(Direction.W);
		}
		if (input.isMoveRight()) {
			player.requestMove(Direction.E);
		}
		if (input.isMoveUp()) {
			player.requestMove(Direction.N);
		}
		if(input.isBomb()) {
			vf=true;
		}
		input.clear();
	}

	
	
	private void showMessage(String msg, Color color) {
		Text waitingForKey = new Text(msg);
		waitingForKey.setTextAlignment(TextAlignment.CENTER);
		waitingForKey.setFont(new Font(60));
		waitingForKey.setFill(color);
		StackPane root = new StackPane();
		root.getChildren().add(waitingForKey);
		Scene scene = new Scene(root, 400, 200, Color.WHITE);
		stage.setTitle(windowTitle);
		stage.setScene(scene);
		input = new Input(scene);
		stage.show();
		new AnimationTimer() {
			public void handle(long now) {
				processInput(now);
			}
		}.start();
	}

	private void update(long now) {
		if(vf && game.getPlayer().getNbBomb()!=0) {
	        Bomb b=new Bomb(game, player.getPosition());
	        game.getWorld().getBombput().add(b);
	        spriteBomb.get(game.getNiveau()).add(SpriteFactory.createBomb(layer,b));
	        game.getPlayer().setNbBomb(game.getPlayer().getNbBomb()-1);
	        vf=false;
	        }
		for (int i = 0; i < game.getListWorld().size(); i++) {
			System.out.println("oui5");
			if(game.getListWorld().get(i).hasChanged() ){
		    		sprites.get(i).forEach(Sprite::remove);
		    		sprites.get(i).clear();
				
		    		spriteBomb.get(i).forEach(Sprite::remove);
		    		spriteBomb.get(i).clear();
				
		    		
		    Iterator<Bomb> it = game.getListWorld().get(i).getBombput().iterator();
		    while(it.hasNext()) {
		    Bomb b = it.next();
		    if(b.numBomb==4) {
		    	game.getPlayer().setNbBomb(game.getPlayer().getNbBomb()+1);
		    it.remove();
		    System.out.println("oui4");
		    }
		   
		    }
		    initialize(stage,game);
		    game.getListWorld().get(i).setChanged(false);
		    }
		}
	    
	    
		if(game.getWorld().isNextWorld()) {
	    	game.getWorld().setNextWorld(false);
	   	game.newPositon();
	    	game.getPlayer().setNbKey(0);
	    	stage.close();
	    	initialize(stage,game);
	
	    }
	    if(game.getWorld().isPreviousWorld()) {
	    	game.getWorld().setPreviousWorld(false);
	    	game.newPositon1();
	    	game.getPlayer().setNbKey(1);
	    	stage.close();
	    	initialize(stage,game);
	    }
	    /*if(game.getNextChangeWorld()) {
    	   game.newPositon();
	    	game.getPlayer().setNbKey(0);
	    	stage.close();
	    	initialize(stage,game);
	    	game.setNextChangeWorld(false);
       }
       if(game.getPrevChangeWorld()) {
    	   game.newPositon1();
	    	game.getPlayer().setNbKey(1);
	    	stage.close();
	    	initialize(stage,game);
	    	game.setPrevChangeWorld(false);
       }
*/
	    
	    /*c'est ici qui bloque le monde*/
	    for (int j = 0; j < game.getListWorld().size(); j++) {
	    //	render();
	    	 for(int i=0; i<game.getListWorld().get(j).getBombput().size();i++) {
	    		 System.out.println("oui2");
	    		 game.getListWorld().get(j).getBombput().get(i).update(now);
	    		 
	 	        if(game.getListWorld().get(j).getBombput().get(i).numBomb==4) {
	 	        	game.getListWorld().get(j).getBombput().get(i).remove();
	 		    		sprites.get(j).forEach(Sprite::remove);
	 		    		sprites.get(j).clear();
	 		    		//initialize(stage,game);
	 	        controlExplosion(game.getListWorld().get(j).getBombput().get(i));
	 	       
	 	        }
	 	        
	 	        }
		}
	       
	       
	        
	        player.update(now);
	        for(int i=0; i<monsters.size();i++) {
	        	System.out.println("oui3");
	        monsters.get(i).update(now);
	        }
	        

	        if (player.isAlive() == false) {
	            gameLoop.stop();
	            showMessage("Perdu!", Color.RED);
	        }
	        if (player.isWinner()) {
	            gameLoop.stop();
	             showMessage("Gagné", Color.BLUE);
	        }
	    }


    private void render() {
   
    	
    spriteBomb.get(game.getNiveau()).forEach(Sprite::render);
        sprites.get(game.getNiveau()).forEach(Sprite::render);
        // last rendering to have player in the foreground
        spriteMonsters.forEach(Sprite::render);
        spritePlayer.render();
}

	public void start() {
		gameLoop.start();
	}

}
