package edu.tk.examcalc.controller;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import edu.tk.db.global.Session;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.component.*;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.form.PupilForm;
import edu.tk.examcalc.repository.PupilRepository;
import edu.tk.examcalc.service.PDFExportService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        pupilTableView = new PupilTableView(pupilRepository.findAllOrdered());
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

        // Tab Contents
        listTab.setContent(pupilTableView.render());
    }

}
