package window;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import entities.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import tiles.BasicPlatform;
import tiles.MovingPlatform;
import tiles.OneWayPlatform;
import tiles.Platform;
import tiles.WaterTile;

public class WorldRender extends Canvas{
	private boolean doFPS = true;
	private boolean showHitbox = false;
	
	public static Map<KeyCode, Boolean> inputs = new HashMap<>(100);
	public static WorldLoader world;
	public static Vector offset = new Vector(0,0);
	
	private Entity test;
	
	private int frames = 0;
	private long time = System.currentTimeMillis();
	private double[] fpsBuffer = new double[100];
	
	//private WaterTile tile;
	
	public WorldRender(int w, int h){
		super(w,h);
		//load world
		world = new WorldLoader();
		world.loadWorld();
		//tile = new WaterTile(512,512,0.95);
		//make sprite at a position
		test = new RemTest(getWidth()/2,getHeight()/5);
		
		
		//manage input map - need to be cautious of null within the map
		this.setOnKeyPressed(e -> {
			inputs.put(e.getCode(), true);
		});
		this.setOnKeyReleased(e -> {
			inputs.put(e.getCode(), false);
		});
	}
	
	//renders the current world, along with all entities on it.
	public void render(){
		//System.out.println(inputs);
		GraphicsContext g = getGraphicsContext2D();
		
		
		//background
		g.setFill(Color.WHITE);
		g.fillRect(world.worldBounds.getX(),world.worldBounds.getY(),world.worldBounds.getWidth(),world.worldBounds.getHeight());
//		double xPos = (test.getPos().getX() * tile.getWidth()/world.worldBounds.getWidth());
//		double yPos = (test.getPos().getY() * tile.getHeight()/world.worldBounds.getHeight());
//		if((int)xPos >= 0 && xPos < tile.getWidth() && yPos >=0 && yPos < tile.getHeight()){
//			tile.buff1[(int) xPos][(int) yPos]=255;
//		}
//		System.out.println(xPos+","+yPos);
//		tile.update();
//		tile.drawAt(g, 0, 0, getWidth(), getHeight());
		
		
		//translation
		
		if(test.getPos().getX()>world.worldBounds.getX()+getWidth()/2){
			if(test.getPos().getX()<world.worldBounds.getX()+world.worldBounds.getWidth()-getWidth()/2){
				offset.setX(-test.getPos().getX()+getWidth()/2);
			}
		}
		if(test.getPos().getY()>world.worldBounds.getY()+getHeight()/2){
			if(test.getPos().getY()<world.worldBounds.getY()+world.worldBounds.getHeight()-getHeight()/2){
				offset.setY(-test.getPos().getY()+getHeight()/2);
			}
			
		}
		g.translate(offset.getX(), offset.getY());
		//draw/update entities
		if(frames%100==00){
			for(Particle p : Particle.effect(getWidth()/2,getHeight()/2,2.5,40.0,300,Color.RED,Color.RED,Color.RED,Color.ORANGE,Color.ORANGE,Color.LIGHTGRAY)){
				world.entities.add(p);
			}
		}
		for(Entity e : world.entities){
			e.updatePos();
			e.draw(g);
		}
		//remove dead particles
		for(int i = 0;i<world.entities.size();i++){
			if(world.entities.get(i) instanceof Particle && !((Particle) world.entities.get(i)).isAlive()){
				world.entities.remove(i);
				i--;
			}
		}
		//terrain (TODO: REPLACE WITH IMAGE)
		g.setFill(Color.BLACK);
		for(Platform x : world.terrain){
			x.update();
			if(x instanceof BasicPlatform){
				g.setFill(Color.BLACK);
			}else if (x instanceof MovingPlatform){
				g.setFill(Color.GREEN);
			}else if (x instanceof OneWayPlatform){
				g.setFill(Color.BLUE);
			}
			g.fillRect(x.getX(), x.getY(), x.getWidth(), x.getHeight());
		}
		
		
		
		//draw/update player
		test.updatePos();
		test.draw(g);
		if(showHitbox){
			test.drawHitbox(g);
		}
		g.translate(-offset.getX(), -offset.getY());
		
		
		//conditional info
		
		if(doFPS){
			g.setFill(Color.GREEN);
			g.fillText("FPS: "+getFPS(), 10, 20);
			updateFPS();
			g.fillText("Pos:"+(int)test.getPos().getX()+","+(int)test.getPos().getY(),10,40);
			g.fillText("Vel:"+(int)test.getVel().getX()+","+(int)test.getVel().getY(),10,60);
			g.fillText("Acc:"+(int)test.getAcc().getX()+","+(int)test.getAcc().getY(),10,80);
			g.fillText("Entities:"+world.entities.size(),10,100);
		}
		//doFPS = false;showHitbox=false;
		doFPS = true;showHitbox=true;
		frames++;
		
		
	}
	
	//FPS management and access methods
	
	public int getFPS(){
		double avg=0;
		for(double x : fpsBuffer){
			avg+=x;
		}
		return (int)(avg/fpsBuffer.length);
	}
	private void updateFPS(){
		for(int i = 0;i<fpsBuffer.length-1;i++){
			fpsBuffer[i] = fpsBuffer[i+1];
		}
		double denom = ( System.currentTimeMillis() - time);
		if(denom>0)
			fpsBuffer[fpsBuffer.length-1] = 1000/denom;
		else fpsBuffer[fpsBuffer.length-1] = fpsBuffer[fpsBuffer.length-2];
		
		time = System.currentTimeMillis();
	}

	
}
