package entities;

import java.awt.Rectangle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import window.WorldRender;

public class Particle extends Entity{
	private double life = 6; //seconds before this particle despawns
	private boolean alive = true;
	private double timeOfCreation;
	private double radius;
	Color color;
	public Particle(double x, double y,double r) {
		super(x, y);
		timeOfCreation = (System.currentTimeMillis())+(Math.random()*1000);
		radius = r;
	}
	public Particle(double x, double y,double r,Color c) {
		this(x,y,r);
		color = c;
	}
	public static Particle[] effect(double x, double y, double r, double v, int quantity, Color... cs){
		Particle[] list = new Particle[quantity];
		for(int i = 0;i< quantity;i++){
			Particle p = new Particle(x,y,r,cs[(int) (Math.random()*cs.length)]);
			p.setVel(new Vector().set(Math.random()*Math.PI*2, Math.random()*v));
			list[i] = p;
		}
		return list;
	}
	@Override
	public void updatePos(){
		super.updatePos();
		if(((System.currentTimeMillis()-timeOfCreation)/1000.0)>life){
			//WorldRender.world.entities.remove(this);
			alive = false;
		}
	}
	@Override
	public void draw(GraphicsContext g){
		g.setFill(color);
		g.fillOval(getPos().getX()-radius, getPos().getY()-radius, radius*2, radius*2);
	}
	
	@Override
	protected void updateHitbox(){
		setHitbox(new Rectangle((int)(getPos().getX()-radius), (int)(getPos().getY()-radius), (int)(radius*2),(int) (radius*2)));
	}
	
	public boolean isAlive(){
		return alive;
	}

}
