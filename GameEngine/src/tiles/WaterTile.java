package tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class WaterTile{
	int count = 0;
	int width,height;
	public double[][] buff1;
	double[][] buff2;
	double damp;
	public WaterTile(int w, int h, double d){
		width = w;
		height = h;
		damp = d;
		buff1 = new double[w][h];
		buff2 = new double[w][h];
		
		

	}
	public void update(){
		for(int i = 1;i<width-1;i++){
			for(int j = 1;j<height-1;j++){
				buff2[i][j] = ((buff1[i-1][j]+buff1[i+1][j]+buff1[i][j-1]+buff1[i][j+1])/2 - buff2[i][j])*damp;
			}
		}
		double[][] t = buff1;
		buff1 = buff2;
		buff2 = t;
		
		
	}
	
	public void drawAt(GraphicsContext g,double x, double y,double w1, double h1){
		
		double w = w1/width;
		double h = h1/height;
		PixelWriter pw = g.getPixelWriter();
		
		g.setFill(Color.hsb(203, 100/100, 100/100));
		g.fillRect(x, y, w1, h1);
		
		for (int i = 0;i<width;i++){
			for (int j = 0;j<height;j++){
				int color = (int) buff1[i][j];
				if(color<2)continue;
				if(color>255)color=255;
				try{
					g.setFill(Color.hsb(200,(100-(color*100/255/4))/100,100/100));
					g.fillRect(i*w+x, j*h+y, w, h);
					//pw.setColor(i,j,Color.grayRgb(255-color));
					//customFill(pw,i*w,j*h,w,h,Color.grayRgb(255-color));
				}catch(Exception e){
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
}