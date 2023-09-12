package edu.tk.examcalc.controller;

import javafx.event.ActionEvent;
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
        content.setCenter(new Text("Punkte berechnen"));
    }
}
