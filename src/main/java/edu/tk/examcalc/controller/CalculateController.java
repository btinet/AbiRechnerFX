package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.component.ExamTableView;
import edu.tk.examcalc.component.IconButton;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.repository.ExamRepository;
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
    private final ExamTableView tableView;

    private final ExamRepository examRepository = new ExamRepository();


    public CalculateController() {
        super("calculate-index.fxml");
        this.pupil = (Pupil) Session.copy("pupil");
        this.tableView = new ExamTableView(examRepository.findAllJoinSubject());
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
            content.setCenter(this.tableView);
        } else {
            content.setCenter(this.tableView);
        }

    }
}
