package window;

import LevelEditor.editorCanvas;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	boolean edit = false;

	public static void main(String[] args) {
		try {
			launch(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group root = new Group();

		if (edit) {
			editorCanvas editor = new editorCanvas(1920, 1080);
			editor.setFocusTraversable(true);
			new AnimationTimer() {
				@Override
				public void handle(long now) {
					try {
						editor.draw();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();
			root.getChildren().add(editor);

		} else {
			WorldRender world = new WorldRender(1920, 1080);
			world.setFocusTraversable(true);
			new AnimationTimer() {
				@Override
				public void handle(long now) {
					try {
						world.render();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();
			root.getChildren().add(world);

		}

		primaryStage.setScene(new Scene(root));
		primaryStage.setFullScreen(true);
		primaryStage.show();

	}
}
