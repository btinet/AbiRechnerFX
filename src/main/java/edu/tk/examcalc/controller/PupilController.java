package edu.tk.examcalc.controller;

import edu.tk.db.global.Session;
import edu.tk.examcalc.entity.Pupil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;

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
    @FXML
    public MasterDetailPane masterPane;

    private ObservableList<Pupil> pupils;

    public PupilController() {
        super("pupil-index.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Kollegiat:innen verwalten");

        TableView<Pupil> pupilTableView = new TableView<>();

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



        masterPane.setMasterNode(pupilTableView);
    }
}
