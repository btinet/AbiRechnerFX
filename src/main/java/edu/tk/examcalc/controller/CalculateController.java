package edu.tk.examcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculateController extends Controller {


    @FXML
    public BorderPane content;

    public CalculateController() {
        super("calculate-index.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Note berechnen");
        content.setCenter(new Text("Punkte berechnen"));
    }
}
