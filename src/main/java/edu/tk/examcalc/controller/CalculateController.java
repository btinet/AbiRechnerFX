package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.component.IconButton;
import edu.tk.examcalc.entity.Pupil;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculateController extends Controller {


    @FXML
    public BorderPane content;
    public IconButton newButton;
    public IconButton editButton;
    public IconButton exportButton;
    public IconButton refreshButton;
    public IconButton pupilCrudButton;

    private final Pupil pupil;


    public CalculateController() {
        super("calculate-index.fxml");
        this.pupil = (Pupil) Session.copy("pupil");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(pupil != null) {
            setPageTitle("Prüfungen für " + this.pupil);
        } else {
            setPageTitle("Prüfungen");
        }

        pupilCrudButton.setOnAction(e -> switchToController(content,new PupilController()));

        if(pupil != null) {
            content.setCenter(new Text("Punkte für " + pupil + " berechnen."));
        } else {
            content.setCenter(new Text("Punkte berechnen."));
        }

    }
}
