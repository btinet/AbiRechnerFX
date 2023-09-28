package edu.tk.examcalc.form;

import edu.tk.db.global.Session;
import edu.tk.db.model.EntityManager;
import edu.tk.examcalc.component.DialogComponent;
import edu.tk.examcalc.controller.CalculateController;
import edu.tk.examcalc.controller.PupilController;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.entity.SchoolSubject;
import edu.tk.examcalc.repository.ExamRepository;
import edu.tk.examcalc.repository.SchoolSubjectRepository;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

import java.util.ArrayList;
import java.util.Objects;
public class ExamForm extends Form {
    VBox vBox = new VBox();

    ArrayList<SearchableComboBox<SchoolSubject>> examComboBoxes = new ArrayList<>();
    ArrayList<SearchableComboBox<Integer>> examPointComboBoxes = new ArrayList<>();
    ExamRepository examRepository = new ExamRepository();
    ArrayList<Exam> exams;
    private final Pupil currentPupil = ((Pupil) Objects.requireNonNull(Session.copy("pupil")));
    private final DialogComponent dialog;
    private final ArrayList<Boolean> f = new ArrayList<>();
    private final ArrayList<Boolean> p = new ArrayList<>();

    public ExamForm(DialogComponent dialog) {
        exams = examRepository.findAllJoinSubject(this.currentPupil.getId());
        this.dialog = dialog;
        this.dialog.setContent(this);
        dialog.setSubmitButtonDisabled(true);

        Font boldFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12);

        setVgap(15);
        setHgap(15);
        setGridLinesVisible(false);

        SchoolSubjectRepository schoolSubjectRepository = new SchoolSubjectRepository();
        ArrayList<SchoolSubject> schoolSubjects = schoolSubjectRepository.findAll();

        int row = 0;
        Text obliText = new Text("Pflichtangaben");
        obliText.setFont(boldFont);
        add(obliText, 0, row, 2, 1);

        for (int i = 1; i <= 5; i++) {
            SearchableComboBox<SchoolSubject> comboBox = new SearchableComboBox<>();
            comboBox.setPrefWidth(200);
            comboBox.setId(String.valueOf(i));
            comboBox.getItems().addAll(schoolSubjects);
            comboBox.getSelectionModel().selectedItemProperty().addListener((arg0, oldValue, newValue) -> {
                if(oldValue == null && newValue != null) {
                    f.add(true);
                }
                if(oldValue != null && newValue == null) {
                    f.remove(true);
                }
                validateForm();
            });

            for (Exam exam : this.exams) {
                if (i == exam.getExamNumber()) {
                    for (SchoolSubject subject : schoolSubjects) {
                        if (exam.getSchoolSubjectId() == subject.getId()) {
                            comboBox.getSelectionModel().select(subject);
                        }
                    }
                }
            }


            SearchableComboBox<Integer> pointsComboBox = new SearchableComboBox<>();
            pointsComboBox.setPrefWidth(100);
            pointsComboBox.getSelectionModel().selectedItemProperty().addListener((arg0, oldValue, newValue) -> {
                if(oldValue == null && newValue != null) {
                    p.add(true);
                }
                if(oldValue != null && newValue == null) {
                    p.remove(true);
                }
                validateForm();
            });
            for (int k = 1; k <= 15; k++) {
                pointsComboBox.getItems().add(k);
            }

            row++;
            add(new Label("Prüfung " + i + ", Notenpunkte"), 0, row);
            add(comboBox, 1, row);
            add(pointsComboBox, 2, row);

            this.examComboBoxes.add(comboBox);
            this.examPointComboBoxes.add(pointsComboBox);
        }

    }

    private void validateForm() {
        if(f.size() == 5 && p.size() == 5) {
            for (Boolean isSet: this.f
                 ) {
                if(!isSet) {
                    dialog.setSubmitButtonDisabled(true);
                    break;
                } else {
                    dialog.setSubmitButtonDisabled(false);
                }
            }
            for (Boolean isSet: this.p
            ) {
                if(!isSet) {
                    dialog.setSubmitButtonDisabled(true);
                    break;
                } else {
                    dialog.setSubmitButtonDisabled(false);
                }
            }
        }
    }

    public VBox render() {
        vBox.getChildren().addAll(this);
        return vBox;
    }

    public void showAndWait(CalculateController controller) {
        dialog.setHeaderText("Datensatz hinzufügen");
        dialog.showAndWait().ifPresent(response -> {
            if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                System.out.println("Gespeichert");
                submit();
                controller.switchToController(controller.content, controller);
            }
            if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                System.out.println("Abgebrochen");
                cancel();
            }
        });
    }

    public void viewAndWait(PupilController controller) {
        dialog.setHeaderText("Datensatz anzeigen");
        Pupil currentPupil = (Pupil) Session.get("pupil");

        if (currentPupil != null) {
            dialog.showAndWait().ifPresent(response -> {
                if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                    System.out.println("Gespeichert");
                }
                if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                    System.out.println("Abgebrochen");
                }
            });
        }
        cancel();
        dialog.close();
    }

    @Override
    public void submit() {
        EntityManager<Exam> entityManager = new EntityManager<>();
        for (int i = 0; i <5; i++) {
            Exam exam = new Exam();
            exam.setExamNumber(i+1);
            exam.setSchoolSubjectId(examComboBoxes.get(i).getSelectionModel().getSelectedItem().getId());
            exam.setPupilId(this.currentPupil.getId());
            exam.setPoints(examPointComboBoxes.get(i).getSelectionModel().getSelectedItem());
            entityManager.persist(exam);
        }
    }

    public void update() {
    }

    @Override
    public void cancel() {
    }

}
