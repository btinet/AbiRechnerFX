package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.entity.Pupil;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculateController extends Controller {


    @FXML
    public BorderPane content;

    private Pupil pupil;

    public CalculateController() {
        super("calculate-index.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Note berechnen");

       this.pupil = (Pupil) Session.get("pupil");

        if(pupil != null) {
            content.setCenter(new Text("Punkte für " + pupil + " berechnen."));
        } else {
            content.setCenter(new Text("Punkte berechnen."));
        }

    }
}
