package entities;

import javafx.scene.image.Image;
import window.WorldRender;

public class RemTest extends Entity implements Controllable{
	private static double mult = 0.15;
	private static Image img = new Image("/left.gif", 248*mult, 500*mult, false, false);
	public RemTest(double x, double y){
		super(x,y,img);
	}
	
}
