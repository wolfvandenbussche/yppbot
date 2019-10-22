package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import ui.Main.CustomStages;

public class CustomController extends AbstractController implements Initializable {

	@FXML
	private Button bilgingButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void goToBilging(MouseEvent event) {
		this.getParent().showCustomStage(CustomStages.bilging);
	}

}
