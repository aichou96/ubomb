package fr.ubx.poo.view.sprite;

import fr.ubx.poo.model.go.GameObject;
import fr.ubx.poo.view.image.ImageFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteExplosion extends  SpriteGameObject {

	public SpriteExplosion(Pane layer, GameObject go) {
		super(layer, null, go);
		updateImage();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updateImage() {
		 setImage(ImageFactory.getInstance().getNumBomb(4));
		// TODO Auto-generated method stub
		
	}

}
