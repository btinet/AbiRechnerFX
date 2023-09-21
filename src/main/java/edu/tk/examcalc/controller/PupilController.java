package edu.tk.examcalc.controller;

import edu.tk.examcalc.entity.Pupil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.controlsfx.control.MasterDetailPane;

import java.net.URL;
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
        contextMenu.getItems().add(editRow);
        contextMenu.getItems().add(new MenuItem("Note berechnen"));

        editRow.setOnAction((ActionEvent event) -> {
            System.out.println("Menu item 1");
            Object item = pupilTableView.getSelectionModel().getSelectedItem();
            System.out.println("Selected item: " + item);
        });

        pupilTableView.setContextMenu(contextMenu);

        TableColumn<Pupil,String> firstnameColumn = new TableColumn<>("Vorname");
        firstnameColumn.setMinWidth(200);
        firstnameColumn.setCellValueFactory(
                new PropertyValueFactory<Pupil,String>("firstnameProperty")
        );

        TableColumn<Pupil,String> lastnameColumn = new TableColumn<>("Nachname");
        lastnameColumn.setMinWidth(200);
        lastnameColumn.setCellValueFactory(
                new PropertyValueFactory<Pupil,String>("lastnameProperty")
        );

        Pupil p1 = new Pupil();
        p1.setFirstname("Benjamin");
        p1.setLastname("Wagner");

        Pupil p2 = new Pupil();
        p2.setFirstname("Peter");
        p2.setLastname("Pan");

        pupils = FXCollections.observableArrayList(p1, p2);

        pupilTableView.setItems(pupils);
        pupilTableView.getColumns().addAll(firstnameColumn,lastnameColumn);



        masterPane.setMasterNode(pupilTableView);
    }
}
