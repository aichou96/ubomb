package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.model.go.character.Bomb;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteBomb extends SpriteGameObject {
	 private final ColorAdjust effect = new ColorAdjust();

	public SpriteBomb(Pane layer, Bomb bomb) {
		super(layer,null,bomb);
		  updateImage();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateImage() {
		Bomb bomb=(Bomb) go;
		 setImage(ImageFactory.getInstance().getNumBomb(bomb.numBomb));
		// TODO Auto-generated method stub
		
	}


}
