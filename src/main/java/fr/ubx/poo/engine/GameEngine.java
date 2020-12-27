/*
 * Copyright (c) 2020. Laurent Réveillère
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


public final class GameEngine {

	private static AnimationTimer gameLoop;
	private final String windowTitle;
	private final Game game;
	private final Player player;
	private final List<Sprite> sprites = new ArrayList<>();
	private List<Bomb> bomb1=new ArrayList<>() ;
	private List<Sprite> spriteBomb1=new ArrayList<>();
	private List<Sprite> spriteMonsters = new ArrayList<>();
	  private List< Monster> monsters= new ArrayList<>();
	private StatusBar statusBar;
	private Pane layer;
	private Input input;
	private Stage stage;
	private Sprite spritePlayer;
	private Sprite spriteExplos;
	private  Bomb bomb;
	boolean vf=false;
	private boolean loseHert=false;
	private int numberBomb=3;



	public GameEngine(final String windowTitle, Game game, final Stage stage) {
		this.windowTitle = windowTitle;
		this.game = game;
		this.player = game.getPlayer();
		this.monsters=game.getMonster();
		initialize(stage, game);
		buildAndSetGameLoop();
	}

	public void controlExplosion(Bomb b) {
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
	spriteBomb1.add(SpriteFactory.createBomb(layer, up));

	Bomb rigth =new Bomb(game,p2);
	rigth.numBomb=4;
	spriteBomb1.add(SpriteFactory.createBomb(layer, rigth));


	Bomb left=new Bomb(game,p3);
	left.numBomb=4;
	spriteBomb1.add(SpriteFactory.createBomb(layer, left));

	Bomb down=new Bomb(game,p4);
	down.numBomb=4;
	spriteBomb1.add(SpriteFactory.createBomb(layer, down));
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
		game.getWorld().forEach( (pos,d) -> sprites.add(SpriteFactory.createDecor(layer, pos, d)));
		spritePlayer = SpriteFactory.createPlayer(layer, player);  
		for(int i=0;i<bomb1.size();i++) {
			spriteBomb1.add(SpriteFactory.createBomb(layer, bomb1.get(i)));
		}
		for(int i=0;i<monsters.size();i++) {
			spriteMonsters.add(SpriteFactory.createMonster(layer, monsters.get(i)));
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
		if(vf && numberBomb!=0) {
	        Bomb b=new Bomb(game, player.getPosition());
	        bomb1.add(b);
	        spriteBomb1.add(SpriteFactory.createBomb(layer,b));
	        numberBomb--;
	        vf=false;
	        }
		
	    if(game.getWorld().hasChanged()){
	    sprites.forEach(Sprite::remove);
	    sprites.clear();
	    spriteBomb1.forEach(Sprite::remove);
	    spriteBomb1.clear();
	    Iterator<Bomb> it = bomb1.iterator();
	    while(it.hasNext()) {
	    Bomb b = it.next();
	    if(b.numBomb==4) {
	    numberBomb++;
	    it.remove();
	    }
	   
	    }
	    initialize(stage,game);
	    game.getWorld().setChanged(false);
	    }
	        boolean in=false ;
	        for(int i=0; i<bomb1.size();i++) {
	        bomb1.get(i).update(now);
	        if(bomb1.get(i).numBomb==4) {
	        	in=true;
	        	bomb1.get(i).remove();
	        	int j=0;
	        	sprites.forEach(Sprite::remove);
				sprites.clear();
				initialize(stage,game);
	        controlExplosion(bomb1.get(i));
	        }
	        }
	       
	        /*if(in) {
	        	long debut=	System.currentTimeMillis();
	        	double i=1;
	        	boolean v=false;
	        	while(System.currentTimeMillis()-debut<4*Math.pow(10, 3)) {
	        		if(v) {
	        			if(i%2!=0) {
	    	        		spritePlayer.remove();
	    	        		}
	    	        		else {
	    	        			//spritePlayer = SpriteFactory.createPlayer(layer, player); 
	    	        		}
	        			
	        			//initialize(stage,game);
	        			v=false;
	        		}
	        		
	        		if((System.currentTimeMillis()-debut)%(0.5*Math.pow(10, 3))==0) {
	        		v=true;
	        		i++;
	        		
	        		}
	        		
	        	}
	        	in=false;
	        	
	        }*/
	        
	        player.update(now);
	        for(int i=0; i<monsters.size();i++) {
	        monsters.get(i).update(now);
	        }

	        if (player.isAlive() == false) {
	            gameLoop.stop();
	            showMessage("Perdu!", Color.RED);
	        }
	        if (player.isWinner()) {
	            gameLoop.stop();
	             showMessage("Gagn�", Color.BLUE);
	        }

	       
	    }


    private void render() {
   
    spriteBomb1.forEach(Sprite::render);
        sprites.forEach(Sprite::render);
        // last rendering to have player in the foreground
        spriteMonsters.forEach(Sprite::render);
        spritePlayer.render();
}

	public void start() {
		gameLoop.start();
	}

}
