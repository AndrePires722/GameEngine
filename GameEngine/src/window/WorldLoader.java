package window;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entities.Entity;
import entities.Particle;
import entities.Vector;
import javafx.scene.paint.Color;
import tiles.BasicPlatform;
import tiles.MovingPlatform;
import tiles.OneWayPlatform;
import tiles.Platform;
import tiles.WaterTile;

public class WorldLoader {
	// stores terrain
	public ArrayList<Platform> terrain = new ArrayList<>();
	public Rectangle worldBounds = new Rectangle();
	
	//store entity data
	public ArrayList<Entity> entities = new ArrayList<>();
	// read world files
	FileReader reader;

	public void loadWorld() {
		
		
		try {
			reader = new FileReader("worldData/testworld.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;
			double minx = 0, miny = 0, maxx = 1920, maxy = 1080;
			while ((line = bufferedReader.readLine()) != null) {
				int type = Integer.parseInt(line);
				switch (type) {
				case 0:
					line = bufferedReader.readLine();
					double x = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double y = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double w = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double h = Double.parseDouble(line);
					if (w != 0 && h != 0) {
						if (w < 0) {
							x = x + w;
							w = -w;
						}
						if (h < 0) {
							y = y + h;
							h = -h;
						}
						terrain.add(new BasicPlatform((int) x, (int) y, (int) w, (int) h));
					}
					minx = Math.min(minx, x);
					miny = Math.min(miny, y);
					maxx = Math.max(maxx, x + w);
					maxy = Math.max(maxy, y + h);
					worldBounds = new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
					System.out.println(worldBounds);
					break;
				case 1:
					line = bufferedReader.readLine();
					double x1 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double y1 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double w1 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double h1 = Double.parseDouble(line);
					if (w1 != 0 && h1 != 0) {
						if (w1 < 0) {
							x1 = x1 + w1;
							w1 = -w1;
						}
						if (h1 < 0) {
							y1 = y1 + h1;
							h1 = -h1;
						}
						terrain.add(new OneWayPlatform((int) x1, (int) y1, (int) w1, (int) h1));
					}
					minx = Math.min(minx, x1);
					miny = Math.min(miny, y1);
					maxx = Math.max(maxx, x1 + w1);
					maxy = Math.max(maxy, y1 + h1);
					worldBounds = new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
					System.out.println(worldBounds);
					break;
				case 2:
					line = bufferedReader.readLine();
					double x2 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double y2 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double w2 = Double.parseDouble(line);
					line = bufferedReader.readLine();
					double h2 = Double.parseDouble(line);
					if (w2 != 0 && h2 != 0) {
						if (w2 < 0) {
							x2 = x2 + w2;
							w2 = -w2;
						}
						if (h2 < 0) {
							y2 = y2 + h2;
							h2 = -h2;
						}
						terrain.add(new MovingPlatform((int) x2, (int) y2, (int) w2, (int) h2));
					}
					minx = Math.min(minx, x2);
					miny = Math.min(miny, y2);
					maxx = Math.max(maxx, x2 + w2);
					maxy = Math.max(maxy, y2 + h2);
					worldBounds = new Rectangle((int) minx, (int) miny, (int) (maxx - minx), (int) (maxy - miny));
					System.out.println(worldBounds);
					break;
				default:
					System.out.println("Invalid type, world failed to load.");
					System.exit(0);

				}
				
				

				
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error loading World!");
			e.printStackTrace();
		}
		
	}
}
