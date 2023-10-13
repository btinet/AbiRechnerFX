package edu.tk.examcalc.form;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import edu.tk.db.global.Session;
import edu.tk.db.model.ResultSorter;
import edu.tk.examcalc.component.DialogComponent;
import edu.tk.examcalc.controller.CalculateController;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.entity.SchoolSubject;
import edu.tk.examcalc.repository.SchoolSubjectRepository;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import com.dlsc.formsfx.model.structure.Form;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;

public class SingleExamForm extends edu.tk.examcalc.form.Form {

    private final DialogComponent dialog;
    VBox vBox = new VBox();
    SearchableComboBox<SchoolSubject> subjectsComboBox = new SearchableComboBox<>();
    SearchableComboBox<Integer> gradesComboBox = new SearchableComboBox<>();

    public SingleExamForm(DialogComponent dialog) {
        super();
        this.dialog = dialog;
        this.dialog.setContent(this);
        dialog.setSubmitButtonDisabled(true);

        SchoolSubjectRepository schoolSubjectRepository = new SchoolSubjectRepository();
        ArrayList<Integer> grades = new ArrayList<>();
        for(int i = 0; i <= 15; i++) {
            grades.add(i);
        }

        Font boldFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12);

        subjectsComboBox.setPrefWidth(200);
        subjectsComboBox.getItems().addAll(schoolSubjectRepository.findAll(new ResultSorter("label","ASC").getMap()));

        gradesComboBox.setPrefWidth(200);
        gradesComboBox.getItems().addAll(grades);

        setVgap(15);
        setHgap(15);
        setGridLinesVisible(false);

        int row = 0;
        Text obliText = new Text("Pflichtangaben");
        obliText.setFont(boldFont);
        add(obliText, 0, row, 3, 1);

        row++;
        add(new Label("Prüfung, Notenpunkte"), 0, row);
        add(subjectsComboBox, 1, row);
        add(gradesComboBox, 2, row);

    }

    @Override
    public VBox render() {
        vBox.getChildren().addAll(this);
        return vBox;
    }

    @Override
    public void submit() {

    }

    @Override
    public void cancel() {

    }

    public void showAndWait(CalculateController controller) {

        Exam exam = (Exam) Session.copy("exam");
        assert exam != null;
        SchoolSubject subject = exam.getSchoolSubject();
        subjectsComboBox.getSelectionModel().select(subject);
        gradesComboBox.getSelectionModel().select(exam.getPoints());

        dialog.setHeaderText(exam.getExamNumber() + ". Prüfung bearbeiten (aktuell: " + subject + ")");
        dialog.showAndWait().ifPresent(response -> {
            if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                submit();
                controller.switchToController(controller.content, controller);
            }
            if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                cancel();
            }
        });

    }
}
