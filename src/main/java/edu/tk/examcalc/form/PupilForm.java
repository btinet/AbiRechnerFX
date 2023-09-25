package edu.tk.examcalc.form;

import edu.tk.db.global.Session;
import edu.tk.db.model.EntityManager;
import edu.tk.examcalc.component.DialogComponent;
import edu.tk.examcalc.controller.Controller;
import edu.tk.examcalc.controller.PupilController;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.entity.Tutor;
import edu.tk.examcalc.repository.TutorRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.textfield.CustomTextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class PupilForm extends Form {
    VBox vBox = new VBox();
    private final TextField firstname = new CustomTextField();
    private boolean hasFirstname = false;
    private final TextField lastname = new TextField();
    private boolean hasLastname = false;
    private final DatePicker birthdate = new  DatePicker();
    private final TextField coursePoints = new TextField();
    private final TextField examDate = new TextField();
    private boolean hasExamDate = false;
    private final SearchableComboBox<Tutor> tutor = new SearchableComboBox<>();
    private final Label errorLabel = new Label();
    private DialogComponent dialog;

    public PupilForm(DialogComponent dialog) {

        this.dialog = dialog;
        this.dialog.setContent(this);
        this.dialog.setSubmitButtonDisabled(true);

        Font boldFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12);

        setVgap(15);
        setHgap(15);
        setGridLinesVisible(false);

        errorLabel.setTextFill(Color.RED);

        firstname.textProperty().addListener((arg0, oldValue, newValue) -> {
            hasFirstname = !firstname.getText().isEmpty();
            validateForm();
        });

        lastname.textProperty().addListener((arg0, oldValue, newValue) -> {
            hasLastname = !lastname.getText().isEmpty();
            validateForm();
        });

        examDate.textProperty().addListener((arg0, oldValue, newValue) -> {
            hasExamDate = !examDate.getText().isEmpty();
            validateForm();
        });

        firstname.setPrefWidth(200);
        lastname.setPrefWidth(200);
        coursePoints.setPrefWidth(200);

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        try {
            examDate.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(yearFormat), yearFormat.parse("2023")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int row = 0;

        Text obliText = new Text("Pflichtangaben");
        Text optioText = new Text("Optionale Angaben");
        obliText.setFont(boldFont);
        optioText.setFont(boldFont);

        add(obliText,0,row,4,1);

        row++;
        add(new Label("Vorname"),0,row);
        add(firstname,1,row);


        add(new Label("Nachname"),2,row);
        add(lastname,3,row);

        row++;
        add(new Label("Abschlussjahr"),0,row);
        add(examDate,1,row);

        row++;
        add(new VBox(10),0,row,4,1);

        row++;
        add(optioText,0,row,4,1);

        row++;
        add(new Label("Kursblock, Punkte"),0,row);
        add(coursePoints,1,row);

        row++;
        add(new Label("Tutorium"),0,row);
        add(tutor,1,row);

        row++;
        add(new Label("Geburtsdatum"),0,row);
        add(birthdate,1,row);

        TutorRepository tutorRepository = new TutorRepository();
        ArrayList<Tutor> tutors = tutorRepository.findAll();
        System.out.println(tutors);
        if(tutors != null) {
            tutor.getItems().addAll(tutors);
        }

    }

    public VBox render() {
        vBox.getChildren().addAll(this);
        return vBox;
    }

    private void validateForm() {
        dialog.setSubmitButtonDisabled(!hasFirstname || !hasLastname || !hasExamDate);
    }

    public void showAndWait(PupilController controller) {
        dialog.setHeaderText("Datensatz hinzufügen");
        dialog.showAndWait().ifPresent(response -> {
            if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                System.out.println("Gespeichert");
                submit();
                controller.switchToController(controller.content,controller);
            }
            if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                System.out.println("Abgebrochen");
                cancel();
            }
        });
    }

    public void editAndWait(PupilController controller) {
        dialog.setHeaderText("Datensatz aktualisieren");
        Pupil currentPupil = (Pupil) Session.get("pupil");

        if (currentPupil != null) {
            firstname.setText(currentPupil.getFirstname());
            lastname.setText(currentPupil.getLastname());
            examDate.setText(currentPupil.getExamDate());
            birthdate.getEditor().setText(currentPupil.getBirthDate());
            tutor.setValue(currentPupil.getTutor());
            coursePoints.setText(currentPupil.getCoursePoints().toString());


            dialog.showAndWait().ifPresent(response -> {
                if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                    System.out.println("Gespeichert");
                    update(currentPupil);
                    controller.switchToController(controller.content, controller);
                }
                if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                    System.out.println("Abgebrochen");
                    cancel();
                }
            });

        } else {
            cancel();
            dialog.close();
        }

    }

    public void viewAndWait(PupilController controller) {
        dialog.setHeaderText("Datensatz anzeigen");
        Pupil currentPupil = (Pupil) Session.get("pupil");

        if (currentPupil != null) {
            firstname.setText(currentPupil.getFirstname());
            firstname.setEditable(false);
            lastname.setText(currentPupil.getLastname());
            lastname.setEditable(false);
            examDate.setText(currentPupil.getExamDate());
            examDate.setEditable(false);
            birthdate.getEditor().setText(currentPupil.getBirthDate());
            birthdate.setEditable(false);
            tutor.setValue(currentPupil.getTutor());
            tutor.setEditable(false);
            coursePoints.setText(currentPupil.getCoursePoints().toString());
            coursePoints.setEditable(false);


            dialog.showAndWait().ifPresent(response -> {
                if (response.getButtonData() == dialog.getSubmitButtonType().getButtonData()) {
                    System.out.println("Gespeichert");
                    cancel();
                }
                if (response.getButtonData() == dialog.getCancelButtonType().getButtonData()) {
                    System.out.println("Abgebrochen");
                    cancel();
                }
            });

        } else {
            cancel();
            dialog.close();
        }

    }

    @Override
    public void submit() {
        EntityManager entityManager = new EntityManager(false);
        Pupil pupil = new Pupil();

        pupil.setFirstname(this.firstname.getText());
        pupil.setLastname(this.lastname.getText());
        pupil.setExamDate(this.examDate.getText());
        pupil.setBirthDate(this.birthdate.getEditor().getText());
        pupil.setCoursePoints(Integer.parseInt(this.coursePoints.getText()));

        if(this.tutor.getSelectionModel().getSelectedItem() != null) {
            pupil.setTutorId(this.tutor.getSelectionModel().getSelectedItem().getId());
        } else {
            pupil.setTutorId(null);
        }

        System.out.println(pupil);
        entityManager.persist(pupil);
        cancel();
    }

    public void update(Pupil pupil) {
        EntityManager entityManager = new EntityManager(false);

        pupil.setFirstname(this.firstname.getText());
        pupil.setLastname(this.lastname.getText());
        pupil.setExamDate(this.examDate.getText());
        pupil.setBirthDate(this.birthdate.getEditor().getText());
        pupil.setCoursePoints(Integer.parseInt(this.coursePoints.getText()));

        if(this.tutor.getSelectionModel().getSelectedItem() != null) {
            pupil.setTutorId(this.tutor.getSelectionModel().getSelectedItem().getId());
        } else {
            pupil.setTutorId(null);
        }
        entityManager.persist(pupil,pupil.getId());
        cancel();
    }

    @Override
    public void cancel() {
        getFirstname().clear();
        getFirstname().setEditable(true);
        getLastname().clear();
        getLastname().setEditable(true);
        getExamDate().clear();
        getExamDate().setEditable(true);
        getBirthdate().getEditor().clear();
        getBirthdate().setEditable(true);
        getCoursePoints().clear();
        getCoursePoints().setEditable(true);
        getTutor().getSelectionModel().clearSelection();
        getTutor().setEditable(true);
    }

    public TextField getFirstname() {
        return firstname;
    }

    public TextField getLastname() {
        return lastname;
    }

    public DatePicker getBirthdate() {
        return birthdate;
    }

    public TextField getExamDate() {
        return examDate;
    }

    public TextField getCoursePoints() {
        return coursePoints;
    }

    public SearchableComboBox<Tutor> getTutor() {
        return tutor;
    }

}
