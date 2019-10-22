package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import ui.Main.ManualStages;

public class ManualController extends AbstractController implements Initializable{

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	

    @FXML
    void loadBlacksmithing(MouseEvent event) {
    	this.getParent().showManualStage(ManualStages.blacksmithing);
    }
}
