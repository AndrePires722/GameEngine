package tiles;

import java.awt.Rectangle;

import entities.Entity;

public abstract class Platform {
	private double width, height, xPos, yPos;
	private Rectangle hitBox;
	public Platform(double x, double y, double w, double h){
		xPos = x;
		yPos = y;
		width = w;
		height = h;
		updateHitBox();
	}
	
	public void updateHitBox(){
		hitBox = new Rectangle((int)xPos,(int)yPos,(int)width,(int)height);
	}
	
	public boolean intersects(Entity other){
		return hitBox.intersects(other.getHitbox());
	}
	public Rectangle intersection(Entity other){
		return hitBox.intersection(other.getHitbox());
	}
	public void setX(double x){
		xPos = x;
	}
	public double getX(){
		return xPos;
	}
	public void setY(double y){
		yPos = y;
	}
	public double getY(){
		return yPos;
	}
	public void setWidth(double x){
		width = x;
	}
	public double getWidth(){
		return width;
	}
	public void setHeight(double x){
		height = x;
	}
	public double getHeight(){
		return height;
	}
	
	public Rectangle getHitBox(){
		return hitBox;
	}
	
	public abstract void update();

}
