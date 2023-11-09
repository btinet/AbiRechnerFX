package edu.tk.examcalc.controller;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import edu.tk.db.global.Session;
import edu.tk.db.model.EntityManager;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.component.*;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.form.PupilForm;
import edu.tk.examcalc.repository.PupilRepository;
import edu.tk.examcalc.service.PDFExportService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;

public class PupilController extends Controller {

    @FXML
    public BorderPane content;
    @FXML
    public Tab listTab;
    @FXML
    public Tab importTab;
    @FXML
    public Tab exportTab;
    public IconButton newButton;
    public IconButton editButton;
    public IconButton calculateButton;
    public IconButton refreshButton;
    public IconButton exportButton;
    private PupilForm pupilForm;
    private final PupilTableView pupilTableView;

    public PupilController() {
        super("pupil-index.fxml");
        PupilRepository pupilRepository = new PupilRepository();
        pupilTableView = new PupilTableView(pupilRepository.findAllGroupedByPupil());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPageTitle("Kollegiat:innen verwalten");

        EventHandler<ActionEvent> eventHandler = event -> {
            Pupil item = pupilTableView.getSelectionModel().getSelectedItem().getPupil();
            Session.set("pupil",item);
            switchToController(content, new CalculateController());
        };

        // List Tab
        DialogComponent dialog = new DialogComponent("Stammdatenverwaltung");
        pupilForm = new PupilForm(dialog);

        // Set Button Icons
        newButton.setIcon("win10-create-new");
        refreshButton.setIcon("win10-refresh");
        editButton.setIcon("win10-pencil");
        calculateButton.setIcon("win10-share");
        exportButton.setIcon("win10-export");

        // Set Actions
        newButton.setOnAction(event -> pupilForm.showAndWait(this));
        editButton.setOnAction(event -> pupilForm.editAndWait(this));
        calculateButton.setOnAction(eventHandler);
        refreshButton.setOnAction(e -> switchToController(content,this));
        exportButton.setOnAction(PDFExportService::new);

        pupilTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    if(row.getItem() != null) {
                        switchToController(content,new CalculateController());
                    }
                }
            }
        });



        // Create ContextMenu
        ContextMenuComponent contextMenu = new ContextMenuComponent();
        contextMenu.createAndAddMenuItem("Bearbeiten");
        contextMenu.createAndAddMenuItem("Note berechnen", eventHandler);
        pupilTableView.setContextMenu(contextMenu);

        // TableView Select Listener
        pupilTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(pupilTableView.getSelectionModel().getSelectedItem() != null)
                {
                    Pupil item = pupilTableView.getSelectionModel().getSelectedItem().getPupil();
                    Session.set("pupil",item);
                    editButton.setDisable(false);
                    calculateButton.setDisable(false);
                    exportButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                    calculateButton.setDisable(true);
                    exportButton.setDisable(true);
                }
            }
        });
        Button importButton = new Button("importieren");

        List<List<String>> records = new ArrayList<>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("CSV-Datei zum Import auswählen");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Dateien", "*.csv")
        );
        importButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(this.content.getScene().getWindow());

            EntityManager<Pupil> entityManager = new EntityManager<>();



            try  {
                CSVParser parser = new CSVParserBuilder()
                        .withSeparator(';')
                        .withIgnoreQuotations(true)
                        .build();

                CSVReader csvReader = new CSVReaderBuilder(new FileReader(selectedFile))
                        .withSkipLines(1)
                        .withCSVParser(parser)
                        .build();

                String[] values = null;
                while ((values = csvReader.readNext()) != null) {
                        Pupil pupil = new Pupil();
                        records.add(Arrays.asList(values));
                        pupil.setFirstname(values[0]);
                        pupil.setLastname(values[1]);
                        pupil.setBirthDate(values[2]);
                        pupil.setExamDate(values[3]);
                        pupil.setCoursePoints(Integer.parseInt(values[4]));
                        entityManager.persist(pupil);
                }
            } catch (IOException | CsvValidationException exception) {
                System.out.println(exception.getMessage());
            } catch (NullPointerException ignored) {

            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Die CSV-Datei enthält zu wenig Spalten!");
            } catch (NumberFormatException exception) {
                System.out.println("Eine Spalte enthält falsche Werte!");
            }

            System.out.println("Alles hat geklappt!");
            switchToController(content,this);
        });





        PupilTableView tableRender = pupilTableView.render();
        VBox box = new VBox(tableRender);
        box.setPadding(new Insets(5));
        VBox.setVgrow(tableRender, Priority.ALWAYS);
        // Tab Contents
        listTab.setContent(box);
        importTab.setContent(importButton);
    }

}
