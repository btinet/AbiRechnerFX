package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.component.ExamTableView;
import edu.tk.examcalc.component.IconButton;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.repository.ExamRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
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

    private final ArrayList<Exam> pupilExams;
    public Tab resultTab;
    public TabPane tabPane;
    public TextField sumPoints;
    public TextField coursePoints;
    public TextField examPoints;
    public TextField grade;

    public CalculateController() {
        super("calculate-index.fxml");

        this.pupil = (Pupil) Session.copy("pupil");
        assert this.pupil != null;
        this.pupilExams = examRepository.findAllJoinSubject(this.pupil.getId());
        this.tableView = new ExamTableView(pupilExams);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Prüfungen für " + this.pupil);

        int sumExam = 0;
        for (Exam exam : this.pupilExams) {
            sumExam += exam.getPoints()*4;
        }
        this.examPoints.setText(String.valueOf(sumExam));
        sumExam += this.pupil.getCoursePoints();
        this.sumPoints.setText(String.valueOf(sumExam));
        this.coursePoints.setText(pupil.getCoursePoints().toString());

        pupilCrudButton.setOnAction(e -> switchToController(content,new PupilController()));
        this.grade.setText(String.valueOf(Session.get("grade")));

        content.setCenter(this.tableView.render());
    }
}
