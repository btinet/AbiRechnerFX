package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.component.DialogComponent;
import edu.tk.examcalc.component.Icon;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.form.PupilForm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PupilController extends Controller {

    @FXML
    public BorderPane content;
    @FXML
    public Tab listTab;
    @FXML
    public Tab importTab;
    @FXML
    public Tab exportTab;
    public Button newButton;
    public Button editButton;
    public Button showButton;
    public Button calculateButton;

    PupilForm pupilForm = new PupilForm();
    private ObservableList<Pupil> pupils;

    public PupilController() {
        super("pupil-index.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Kollegiat:innen verwalten");

        TableView<Pupil> pupilTableView = new TableView<>();

        DialogComponent dialog = new DialogComponent("Stammdatenverwaltung");
        dialog.addButtonType(new ButtonType("_Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.addButtonType(new ButtonType("_Speichern", ButtonBar.ButtonData.OK_DONE));
        dialog.setContent(pupilForm);



        newButton.setGraphic(new Icon("ci-add-filled"));

        newButton.setOnAction(event -> dialog.showAndWait().ifPresent(response -> {
            if (response.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                System.out.println("Gespeichert");
                System.out.println(pupilForm.getExamDate().getText());
            }
            if (response.getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                System.out.println("Abgebrochen");
            }
        }));

        editButton.setGraphic(new Icon("ci-edit"));


        showButton.setGraphic(new Icon("ci-view-filled"));
        showButton.setOnAction(event -> System.out.println("Show " + Session.copy("pupil")));

        calculateButton.setGraphic(new Icon("ci-exam-mode"));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editRow = new MenuItem("bearbeiten");
        MenuItem calculateGrad = new MenuItem("Note berechnen");
        contextMenu.getItems().addAll(editRow,calculateGrad);

        editRow.setOnAction((ActionEvent event) -> {
            System.out.println("MenuItem: 1");
            Object item = pupilTableView.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + item);
        });

        calculateGrad.setOnAction((ActionEvent event) -> {
            System.out.println("MenuItem: 2");
            Pupil item = pupilTableView.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + item);

            Session.set("pupil",item);
            switchToController(content, new CalculateController());

        });

        pupilTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(pupilTableView.getSelectionModel().getSelectedItem() != null)
                {
                    Pupil item = pupilTableView.getSelectionModel().getSelectedItem();
                    Session.set("pupil",item);
                    editButton.setDisable(false);
                    showButton.setDisable(false);
                    calculateButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                    showButton.setDisable(true);
                    calculateButton.setDisable(true);
                }
            }
        });

        pupilTableView.setContextMenu(contextMenu);

        TableColumn<Pupil,String> firstnameColumn = new TableColumn<>("Vorname");
        firstnameColumn.setMinWidth(200);
        firstnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstnameProperty")
        );

        TableColumn<Pupil,String> lastnameColumn = new TableColumn<>("Nachname");
        lastnameColumn.setMinWidth(200);
        lastnameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastnameProperty")
        );

        TableColumn<Pupil,LocalDate> birthdateColumn = new TableColumn<>("Geburtsdatum");
        birthdateColumn.setMinWidth(200);
        birthdateColumn.setCellValueFactory(
                new PropertyValueFactory<>("birthdateProperty")
        );

        final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Pupil p1 = new Pupil();
        p1.setFirstname("Benjamin");
        p1.setLastname("Wagner");
        p1.setBirthdate(LocalDate.parse("1987-02-16", dateFormat));

        Pupil p2 = new Pupil();
        p2.setFirstname("Peter");
        p2.setLastname("Pan");

        Pupil p3 = new Pupil();
        p3.setFirstname("Lutz");
        p3.setLastname("van der Horst");

        pupils = FXCollections.observableArrayList(p1, p2,p3);

        pupilTableView.setItems(pupils);
        pupilTableView.getColumns().addAll(firstnameColumn,lastnameColumn,birthdateColumn);

        listTab.setContent(pupilTableView);

    }
}
