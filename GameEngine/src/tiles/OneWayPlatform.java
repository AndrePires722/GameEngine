package tiles;

import entities.Entity;

public class OneWayPlatform extends Platform {
	
	
	public OneWayPlatform(double x, double y, double w, double h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean intersects(Entity other){
		if(other.getVel().getY()>0) return getHitBox().intersects(other.getHitbox());
		return false;
	}

	@Override
	public void update() {
		
	}
	

}
