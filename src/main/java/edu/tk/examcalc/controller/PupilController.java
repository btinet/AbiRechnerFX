package edu.tk.examcalc.controller;

import com.itextpdf.layout.Document;
import edu.tk.db.global.Session;
import edu.tk.examcalc.component.DialogComponent;
import edu.tk.examcalc.component.Icon;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.form.PupilForm;
import edu.tk.examcalc.repository.PupilRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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

    PupilRepository pupilRepository = new PupilRepository(false);

    PupilForm pupilForm = new PupilForm();
    private ObservableList<Pupil> pupils;

    private String dest;

    public PupilController() {
        super("pupil-index.fxml");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Kollegiat:innen verwalten");

        TableView<Pupil> pupilTableView = new TableView<>();
        Button exportButton = new Button("Ergebnis exportieren");
        exportButton.setOnAction(this::savePdf);
        exportTab.setContent(exportButton);

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
                new PropertyValueFactory<>("birthDateProperty")
        );


        ArrayList<Pupil> pupilList = (ArrayList<Pupil>) this.pupilRepository.findAll();
        pupils = FXCollections.observableArrayList(pupilList);

        pupilTableView.setItems(pupils);
        pupilTableView.getColumns().addAll(firstnameColumn,lastnameColumn,birthdateColumn);

        listTab.setContent(pupilTableView);

    }

    public void savePdf(ActionEvent actionEvent)  {
        Node source = (Node) actionEvent.getSource();
        Window stage = source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ergebnis speichern");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Dokument (*.pdf)", "*.pdf"));
        fileChooser.setInitialFileName(Session.copy("pupil")+".pdf");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
            this.dest = file.getAbsolutePath();
        }

        Document document = null;
        PdfFont normal = null;
        try {
            normal = PdfFontFactory.createFont(StandardFonts.COURIER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PdfDocument pdf = new PdfDocument(new PdfWriter(dest))) {
            document = new Document(pdf).setTextAlignment(TextAlignment.JUSTIFIED);
            Pupil currentPupil = (Pupil) Session.copy("pupil");
            if(currentPupil != null) {

                document.add(new Paragraph(currentPupil.getFirstname()).setFont(normal));
                document.add(new Paragraph(currentPupil.getLastname()).setFont(normal));
            }



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (document != null) document.close();
        }

    }

}
