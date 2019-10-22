package ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController extends AbstractController implements Initializable {

	@FXML
	private AnchorPane Root;

	@FXML
	private AnchorPane Main;

	@FXML
	private Button bilgingCustomButton;

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void moveOnMouseDragged(MouseEvent event) {
		this.getSecondaryStage().setX(event.getScreenX() - xOffset);
		this.getSecondaryStage().setY(event.getScreenY() - yOffset);
	}

	@FXML
	void moveOnMousePressed(MouseEvent event) {
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void loadAutomaticScene(MouseEvent event) {
		this.changeScene(Scenes.Automatic);
	}

	@FXML
	void loadManualScene(MouseEvent event) {
		this.changeScene(Scenes.Manual);
	}

	@FXML
	void loadCustomScene(MouseEvent event) {
		this.changeScene(Scenes.Custom);
	}

	@FXML
	void loadAchievementsScene(MouseEvent event) {
		this.changeScene(Scenes.Achievements);
	}

	@FXML
	void loadSettingsScene(MouseEvent event) {
		this.changeScene(Scenes.Settings);
	}

	public void changeScene(Scenes scenes) {
		String fileName = scenes.toString();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/scenes/" + fileName + "Scene.fxml"));
			Parent root = loader.load();
			AbstractController controller = (AbstractController) loader.getController();
			controller.initialize(this.getParent(), this.getPrimaryStage(), this.getSecondaryStage(), this.getScene());
			Main.getChildren().clear();
			Main.getChildren().add(root);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("SYSTEEMFOUT bij file " + fileName + "Scene: " + e.getMessage());
		}
	}

	public enum Scenes {
		Automatic, Manual, Custom, Achievements, Settings
	}

	@FXML
	void minimize(MouseEvent event) {
		this.getPrimaryStage().setIconified(true);
	}

	@FXML
	void close(MouseEvent event) {
		this.getPrimaryStage().close();
	}

}
