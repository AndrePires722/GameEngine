package LevelEditor;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class editorCanvas extends Canvas {
	public static Map<KeyCode, Boolean> inputs = new HashMap<>(100);
	ArrayList<Rectangle> terrain = new ArrayList<>();
	boolean creatingTerrain = false;
	Rectangle currentTerrain = new Rectangle(0, 0, 0, 0);

	int[] offset = { 0, 0 };

	public boolean check(Map<KeyCode, Boolean> m, KeyCode c) {
		return m.get(c) != null && m.get(c);
	}

	public editorCanvas(int i, int j) {
		super(i, j);
		this.setOnMouseClicked(e -> {
			if (creatingTerrain) {
				int x = currentTerrain.x;
				int y = currentTerrain.y;
				currentTerrain.setBounds(x, y, (int) (e.getX() - x + offset[0]), (int) (e.getY() - y + offset[1]));
				creatingTerrain = false;
				terrain.add(currentTerrain);
			} else {
				creatingTerrain = true;
				currentTerrain = new Rectangle((int) e.getX() + offset[0], (int) e.getY() + offset[1], 0, 0);
			}
			
		});
		this.setOnMouseMoved(e -> {
			//System.out.println("dragged");
			if (creatingTerrain) {
				int x = currentTerrain.x;
				int y = currentTerrain.y;
				currentTerrain.setBounds(x, y, (int) (e.getX() - x + offset[0]), (int) (e.getY() - y + offset[1]));
			}

		});
		this.setOnKeyPressed(e -> {
			inputs.put(e.getCode(), true);
		});
		this.setOnKeyReleased(e -> {
			inputs.put(e.getCode(), false);
		});
		try{
			FileReader reader = new FileReader("worldData/testworld.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);

			String line;
			while ((line = bufferedReader.readLine()) != null) {
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
						double t = x;
						x = x + w;
						w = x;
					}
					if (h < 0) {
						double t = y;
						y = y + h;
						h = y;
					}
				}
				terrain.add(new Rectangle((int) x, (int) y, (int) w, (int) h));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}

	public void draw() {
		GraphicsContext g = getGraphicsContext2D();
		if (check(inputs, KeyCode.RIGHT)) {
			g.translate(-10, 0);
			offset[0] += 10;
		}
		if (check(inputs, KeyCode.LEFT)) {
			g.translate(10, 0);
			offset[0] -= 10;
		}
		if (check(inputs, KeyCode.UP)) {
			g.translate(0, 10);
			offset[1] -= 10;
		}
		if (check(inputs, KeyCode.DOWN)) {
			g.translate(0, -10);
			offset[1] += 10;
		}
		if (check(inputs, KeyCode.C)) {
			terrain = new ArrayList<>();
		}
		if (check(inputs, KeyCode.X)) {
			System.out.println("Name the world");
			String name = "worldData/testworld";
			try {
				PrintWriter writer = new PrintWriter(name + ".txt", "UTF-8");
				for (Rectangle x : terrain) {
					writer.println("0");
					writer.println(x.getX());
					writer.println(x.getY());
					writer.println(x.getWidth());
					writer.println(x.getHeight());
				}
				writer.close();
			} catch (FileNotFoundException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		g.clearRect(0 + offset[0], 0 + offset[1], getWidth(), getHeight());
		// fill made rectangles
		g.setFill(Color.BLACK);
		for (Rectangle rec : terrain) {
			g.fillRect(rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight());
		}

		// draw the current rectangle in progress
		if (creatingTerrain) {
			System.out.println(currentTerrain);
			g.strokeRect(currentTerrain.getX(), currentTerrain.getY(), currentTerrain.getWidth(),
					currentTerrain.getHeight());
		}

	}

}
