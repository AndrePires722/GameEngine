package tiles;

import entities.Vector;
import window.WorldRender;

public class MovingPlatform extends Platform{

	Vector velocity = new Vector(5,0);
	public MovingPlatform(double x, double y, double w, double h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		if(getX()+getWidth()>WorldRender.world.worldBounds.getWidth() || getX()<WorldRender.world.worldBounds.getX()){
			velocity.setX(-velocity.getX());
		}
		setX(getX()+velocity.getX());
		updateHitBox();
	}
	
	

}
