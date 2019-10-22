package ui.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.Main;

public abstract class AbstractController {

	private Main parent;
	private Stage primaryStage;
	private Stage secondaryStage;
	private Scene scene;

	public void initialize(Main parent,Stage primaryStage, Stage secondaryStage, Scene scene) {
		this.parent = parent;
		this.primaryStage = primaryStage;
		this.secondaryStage = secondaryStage;
		this.scene = scene;
	}

	public Main getParent() {
		return parent;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public Stage getSecondaryStage() {
		return secondaryStage;
	}
	
	public Scene getScene() {
		return scene;
	}
	
}
