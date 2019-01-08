package entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import tiles.Platform;
import window.WorldRender;

import java.awt.Rectangle;
import java.util.Map;

public abstract class Entity {
	private double gravity = 1;
	private boolean onFloor = false;
	private Vector pos = new Vector(0, 0);
	private Vector vel = new Vector(0, 0);
	private Vector acc = new Vector(0, gravity);
	private double maxSpeedY = 15;
	private double maxSpeedX = 10;
	private double speedXDamp = 0.98;
	private double speedYDamp = 1;
	Rectangle hitbox = new Rectangle();
	Image image = null;

	public Entity(double x, double y) {
		pos.setX(x);
		pos.setY(y);
	}

	public Entity(double x, double y, Image img) {
		this(x, y);
		setImage(img);
		updateHitbox();
	}

	// only used for Controllable objects
	public boolean check(Map<KeyCode, Boolean> m, KeyCode c) {
		return m.get(c) != null && m.get(c);
	}

	public void updatePos() {
		if (this instanceof Controllable) {
			acc.setX(0);
			Map<KeyCode, Boolean> map = WorldRender.inputs;
			double accel = 0.5;
			if (check(map, KeyCode.W)) {
				// setAcc(getAcc().add(new Vector(0,-accel)));temp = true;
				if (onFloor) {
					vel.add(new Vector(0, -15));
					onFloor = false;
				}

			}
			if (check(map, KeyCode.S)) {
				// setAcc(getAcc().add(new Vector(0,accel)));temp = true;
			}
			if (check(map, KeyCode.A)) {
				acc.add(new Vector(-accel, 0));
			}
			if (check(map, KeyCode.D)) {
				acc.add(new Vector(accel, 0));
			}
			// System.out.println(vel.getMagnitude()<1E-40);
			
		}
		vel.setX(vel.getX() * speedXDamp);
		vel.setY(vel.getY() * speedYDamp);
		vel.add(acc);
		// speed cap
		double thresh = 0.001;
		if (vel.getMagnitude() < thresh)
			vel = new Vector();
		else {
//			if (vel.getX() > maxSpeedX)
//				vel.setX(maxSpeedX);
//			if (vel.getX() < -maxSpeedX)
//				vel.setX(-maxSpeedX);
//			if (vel.getY() > maxSpeedY)
//				vel.setY(maxSpeedY);
//			if (vel.getY() < -maxSpeedY)
//				vel.setY(-maxSpeedY);
			pos.add(vel);
		}
		updateHitbox();

		// collision with terrain
		for (Platform r : WorldRender.world.terrain) {
			if (r.intersects(this)) {
				Rectangle intersection = r.intersection(this);
				//if completely inside wall
				if(intersection.getWidth()==hitbox.getWidth() && intersection.getHeight()==hitbox.getHeight()){
					pos.sub(vel);
					vel.mult(0.5);
				}else{
					if (intersection.getWidth() > intersection.getHeight()) {
						if (intersection.y == hitbox.y) {
							pos.add(new Vector(0, intersection.height));
							vel.setY(0);
							acc.setY(0);
							updateHitbox();
						} else {
							pos.sub(new Vector(0, intersection.height));
							vel.setY(0);
							acc.setY(0);
							updateHitbox();
							onFloor = true;
						}
					} else {
						if (intersection.x > hitbox.x) {
							pos.sub(new Vector(intersection.width, 0));
							vel.setX(0);
							acc.setX(0);
							updateHitbox();
						} else {
							pos.add(new Vector(intersection.width, 0));
							vel.setX(0);
							acc.setX(0);
							updateHitbox();
						}

					}
				}
				
				
			}
		}

		acc.setY(gravity);

	}
	protected void updateHitbox(){
		hitbox = new Rectangle((int) (pos.getX() - (image.getWidth() / 2.0)),
				(int) (pos.getY() - image.getHeight()), (int) image.getWidth(),
				(int) image.getHeight());
	}

	public void draw(GraphicsContext g) {
		if (image != null) {
			g.drawImage(image, pos.getX() - (image.getWidth() / 2.0), pos.getY() - image.getHeight());
		}
	}

	public void drawHitbox(GraphicsContext g) {
		g.strokeRect(hitbox.getX(), hitbox.getY(), hitbox.getWidth(), hitbox.getHeight());
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
	protected void setHitbox(Rectangle other){
		hitbox = other;
	}
	public void setImage(Image img) {
		image = img;
	}

	public Image getImage() {
		return image;
	}

	public void setPos(Vector other) {
		pos = other;
	}

	public Vector getPos() {
		return pos;
	}

	public void setVel(Vector other) {
		vel = other;
	}

	public Vector getVel() {
		return vel;
	}

	public void setAcc(Vector other) {
		acc = other;
	}

	public Vector getAcc() {
		return acc;
	}
}
