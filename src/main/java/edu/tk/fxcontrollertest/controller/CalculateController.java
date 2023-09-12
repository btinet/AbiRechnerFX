package edu.tk.fxcontrollertest.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculateController extends Controller {


    @FXML
    public BorderPane branch;

    public CalculateController() {
        super("calculate-index.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        branch.setCenter(new Text("Punkte berechnen"));
    }
}
