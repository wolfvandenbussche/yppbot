package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.controllers.AbstractController;
import ui.controllers.AbstractGameController;
import ui.controllers.MainController;
import ui.controllers.MainController.Scenes;
import utility.PuzzlePirates;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		showMainStage();
		// showManualStage(ManualStages.blacksmithing);
	}

	public void showMainStage() {
		try {
			Stage primaryStage = new Stage();
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.setOpacity(0);
			primaryStage.setHeight(0);
			primaryStage.setWidth(0);
			primaryStage.show();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/rsc/ui/logo.png")));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Puzzle Pirates");

			Stage mainStage = new Stage();
			mainStage.initOwner(primaryStage);
			mainStage.initStyle(StageStyle.TRANSPARENT);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/scenes/MainScene.fxml"));
			Parent root = loader.load();
			MainController controller = (MainController) loader.getController();
			Scene scene = new Scene(root);
			controller.initialize(this, primaryStage, mainStage, scene);
			mainStage.setScene(scene);
			controller.changeScene(Scenes.Automatic);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showManualStage(ManualStages manualStage) {
		try {
			if (PuzzlePirates.isRunning()) {
				Stage primaryStage = new Stage();
				primaryStage.setAlwaysOnTop(true);
				primaryStage.initStyle(StageStyle.UTILITY);
				primaryStage.setOpacity(0);
				primaryStage.setHeight(0);
				primaryStage.setWidth(0);
				primaryStage.show();

				primaryStage.getIcons()
						.add(new Image(getClass().getResourceAsStream("/rsc/ui/" + manualStage.toString() + ".png")));
				primaryStage.setResizable(false);
				primaryStage.setTitle(manualStage.toString());

				Stage mainStage = new Stage();
				mainStage.setAlwaysOnTop(true);
				mainStage.initOwner(primaryStage);
				mainStage.initStyle(StageStyle.TRANSPARENT);
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/ui/scenes/manual/" + manualStage.toString() + "Scene.fxml"));
				Parent root = loader.load();
				AbstractGameController controller = (AbstractGameController) loader.getController();
				Scene scene = new Scene(root);
				controller.initialize(this, primaryStage, mainStage, scene);

				controller.initializeGame();
				scene.setFill(Color.TRANSPARENT);
				mainStage.setScene(scene);
				mainStage.show();
			} else {
				showAlert();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void showAutomaticStage(AutomaticStages automaticStage) {
		try {
			if (PuzzlePirates.isRunning()) {
				Stage primaryStage = new Stage();
				primaryStage.setAlwaysOnTop(true);
				primaryStage.initStyle(StageStyle.UTILITY);
				primaryStage.setOpacity(0);
				primaryStage.setHeight(0);
				primaryStage.setWidth(0);
				primaryStage.show();

				primaryStage.getIcons()
						.add(new Image(getClass().getResourceAsStream("/rsc/ui/" + automaticStage.toString() + ".png")));
				primaryStage.setResizable(false);
				primaryStage.setTitle(automaticStage.toString());

				Stage mainStage = new Stage();
				mainStage.setAlwaysOnTop(true);
				mainStage.initOwner(primaryStage);
				mainStage.initStyle(StageStyle.TRANSPARENT);
				/*FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/ui/scenes/automatic/"+automaticStage.toString()+"Scene.fxml"));*/
				FXMLLoader loader = new FXMLLoader(
						getClass().getResource("/ui/scenes/automatic/BlacksmithingScene.fxml"));
				Parent root = loader.load();
				AbstractGameController controller = (AbstractGameController) loader.getController();
				Scene scene = new Scene(root);
				controller.initialize(this, primaryStage, mainStage, scene);

				controller.initializeGame();
				scene.setFill(Color.TRANSPARENT);
				mainStage.setScene(scene);
				mainStage.show();
			} else {
				showAlert();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showCustomStage(CustomStages customStage) {
		try {
			Stage primaryStage = new Stage();
			primaryStage.setTitle(customStage.toString());
			primaryStage.setResizable(false);
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("rsc/" + customStage.toString() + ".png")));
			primaryStage.initStyle(StageStyle.DECORATED);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/scenes/custom/BilgingScene.fxml"));
			Parent root = loader.load();
			AbstractController controller = (AbstractController) loader.getController();
			Scene scene = new Scene(root);
			controller.initialize(this, primaryStage, null, scene);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION, "Puzzle Pirates isn't running.", ButtonType.OK);
		alert.showAndWait();
	}
	
	public enum CustomStages {
		bilging
	}

	public enum ManualStages {
		bilging, blacksmithing
	}

	public enum AutomaticStages {
		bilging, blacksmithing
	}

	public static void main(String[] args) {
		launch(args);

	}
}
