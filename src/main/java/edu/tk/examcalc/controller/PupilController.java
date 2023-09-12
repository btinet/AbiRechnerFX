package edu.tk.examcalc.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class PupilController extends Controller {


    @FXML
    public BorderPane branch;

    public PupilController() {
        super("pupil-index.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        branch.setCenter(new Text("Kollegiat:innen anlegen und bearbeiten."));
    }
}