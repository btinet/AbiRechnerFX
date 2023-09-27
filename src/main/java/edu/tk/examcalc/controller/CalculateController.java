package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.component.ExamTableView;
import edu.tk.examcalc.component.IconButton;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.repository.ExamRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    public BorderPane centerPane;

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

        newButton.setIcon("win10-create-new");
        editButton.setIcon("win10-pencil");
        exportButton.setIcon("win10-export");
        refreshButton.setIcon("win10-refresh");
        pupilCrudButton.setIcon("win10-gender-neutral-user");

        refreshButton.setOnAction(e -> switchToController(content,this));

        if(this.pupilExams.size() == 5) {
            newButton.setDisable(true);
        }

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(tableView.getSelectionModel().getSelectedItem() != null)
                {
                    Exam item = tableView.getSelectionModel().getSelectedItem().getExam();
                    Session.set("exam",item);
                    editButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                }
            }
        });

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
        centerPane.setCenter(this.tableView.render());
    }
}
