package edu.tk.examcalc.form;

import edu.tk.examcalc.entity.Tutor;
import edu.tk.examcalc.entity.User;
import edu.tk.examcalc.repository.TutorRepository;
import edu.tk.examcalc.repository.UserRepository;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.converter.DateTimeStringConverter;
import org.controlsfx.control.SearchableComboBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class PupilForm extends Form {

    private final Button submitButton = new Button("Anmelden");
    private final Button resetButton = new Button("Formular leeren");
    VBox vBox = new VBox();
    HBox buttonGroup = new HBox(5);
    private final TextField firstname = new TextField();
    private final TextField lastname = new TextField();
    private final TextField birthdate = new TextField();
    private final TextField examDate = new TextField();
    private final SearchableComboBox<Tutor> tutor = new SearchableComboBox<>();
    private final Label errorLabel = new Label();

    public PupilForm() {

        Font boldFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12);

        setVgap(15);
        setHgap(15);
        setGridLinesVisible(false);

        errorLabel.setTextFill(Color.RED);

        buttonGroup.getChildren().addAll(submitButton,resetButton);
        buttonGroup.setPadding(new Insets(25,0,25,0));
        submitButton.setDefaultButton(true);
        resetButton.setCancelButton(true);

        firstname.setPrefWidth(200);
        lastname.setPrefWidth(200);

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
        add(new Label("Tutorium"),0,row);
        add(tutor,1,row);

        row++;
        add(new Label("Geburtsdatum"),0,row);
        add(new DatePicker(),1,row);

        TutorRepository tutorRepository = new TutorRepository();
        ArrayList<Tutor> tutors = (ArrayList<Tutor>) tutorRepository.findAll();
        System.out.println(tutors);
        if(tutors != null) {
            tutor.getItems().addAll(tutors);
        }

    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

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

    public void setFirstname(String firstname) {
        this.firstname.setText(firstname);
    }

    public void setLastname(String lastname) {
        this.lastname.setText(lastname);
    }

    public void setBirthdate(LocalDate birthdate) {
        if(birthdate != null) {
            this.birthdate.setText(birthdate.toString());
        } else {
            this.birthdate.clear();
        }
    }

    public TextField getFirstname() {
        return firstname;
    }

    public TextField getLastname() {
        return lastname;
    }

    public TextField getBirthdate() {
        return birthdate;
    }

    public TextField getExamDate() {
        return examDate;
    }

    public SearchableComboBox<Tutor> getTutor() {
        return tutor;
    }

}
