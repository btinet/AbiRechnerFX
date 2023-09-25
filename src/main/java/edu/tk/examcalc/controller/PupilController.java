package edu.tk.examcalc.controller;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.canvas.parser.listener.TextChunk;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Text;
import edu.tk.db.global.Session;
import edu.tk.db.model.ResultSorter;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.component.*;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import edu.tk.examcalc.form.PupilForm;
import edu.tk.examcalc.repository.PupilRepository;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import org.controlsfx.control.tableview2.FilteredTableView;

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
    public IconButton showButton;
    public IconButton calculateButton;
    public IconButton refreshButton;
    public IconButton exportButton;

    PupilRepository pupilRepository = new PupilRepository();
    PupilForm pupilForm;
    private String dest;
    private final PupilTableView pupilTableView;

    private ArrayList<Exam> currentExams;

    public PupilController() {
        super("pupil-index.fxml");
        pupilTableView = new PupilTableView(pupilRepository.findAllJoin());

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
        showButton.setIcon("win10-gender-neutral-user");
        calculateButton.setIcon("win10-share");
        exportButton.setIcon("win10-export");

        // Set Actions
        newButton.setOnAction(event -> pupilForm.showAndWait(this));
        showButton.setOnAction(event -> pupilForm.viewAndWait(this));
        editButton.setOnAction(event -> pupilForm.editAndWait(this));
        calculateButton.setOnAction(eventHandler);
        refreshButton.setOnAction(e -> switchToController(content,this));
        exportButton.setOnAction(this::generatePdf);



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
                    showButton.setDisable(false);
                    calculateButton.setDisable(false);
                    exportButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                    showButton.setDisable(true);
                    calculateButton.setDisable(true);
                    exportButton.setDisable(true);
                }
            }
        });

        // Tab Contents
        listTab.setContent(pupilTableView.render());
    }

    public void generatePdf(ActionEvent actionEvent) {
        Pupil currentPupil = (Pupil) Session.copy("pupil");
        if(currentPupil != null) {
            this.currentExams = currentPupil.getExams();

            while (true) {
                if(this.currentExams != null) {
                    break;
                }
            }
            savePdf(actionEvent);
        }

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
        PdfFont headline = null;
        PdfFont normal = null;
        PdfFont italic = null;
        try {
            headline = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
            normal = PdfFontFactory.createFont(StandardFonts.COURIER);
            italic = PdfFontFactory.createFont(StandardFonts.COURIER_OBLIQUE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PdfDocument pdf = new PdfDocument(new PdfWriter(dest))) {
            document = new Document(pdf).setTextAlignment(TextAlignment.JUSTIFIED);
            document.add(new Paragraph());
            Pupil currentPupil = (Pupil) Session.copy("pupil");
            if(currentPupil != null) {
                Paragraph title = new Paragraph("Punkteübersicht und Zusatzprüfungen");
                title.setFont(headline);
                title.setFontSize(16);

                Table table = new Table(new float[8]).useAllAvailableWidth();
                table.setMarginTop(25);
                table.setMarginBottom(25);
                table.setFontSize(8);
                table.setFont(normal);

                Cell cell = new Cell(1, 8).add(new Paragraph("Prüfungsergebnisse"));
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(new DeviceRgb(220, 220, 220));
                table.addCell(cell);
                table.addCell(new Cell(1,3));
                table.addCell("Gesamtpunktzahl");
                table.addCell(new Cell(1,4));
                table.startNewRow();

                table.addCell(new Cell(1,3).add(new Paragraph("Kursblock")));
                table.addCell(currentPupil.getCoursePoints().toString());

                Integer sumPoints = currentPupil.getCoursePoints();

                table.addCell(new Cell(1,4));
                table.addCell(new Cell(1,8));

                table.addCell(new Cell(1,4).add(new Paragraph("Prüfungsblock")));
                table.addCell(new Cell(1,2).add(new Paragraph("Zusatzprüfung")));
                table.addCell(new Cell(1,2).add(new Paragraph("Endergebnis")));

                table.addCell("Nr.");
                table.addCell("Fach");
                table.addCell("Punkte");
                table.addCell("Zwischensumme");
                table.addCell("notwendige Punkte");
                table.addCell("Zwischensumme");
                table.addCell("Gesamtpunktzahl");
                table.addCell("Endnote");

                for (Exam exam : this.currentExams) {
                    sumPoints += (exam.getPoints()*4);
                }

                Double grade = 0.0;
                boolean lowerEnd = false;
                int currentKey = 0;
                int nextKey = 0;

                ArrayList<Integer> grades = new ArrayList<>(Grades.GRADE.keySet());
                Collections.sort(grades);
                for (Integer entry : grades) {
                    if(sumPoints >= entry) {
                        currentKey = entry;
                        System.out.println("Punkte: " + currentKey);
                        lowerEnd = true;
                    }
                    if(lowerEnd && sumPoints < entry) {
                        grade = Grades.GRADE.get(currentKey);
                        nextKey = entry;
                        break;
                    }
                }

                for (Exam exam : this.currentExams) {
                    table.addCell(String.valueOf(exam.getExamNumber()));
                    table.addCell(exam.getSchoolSubject().getLabel());
                    table.addCell(exam.getPoints().toString());
                    table.addCell(String.valueOf(exam.getPoints()*4));
                    if(exam.getExamNumber() != null && exam.getExamNumber() <= 3) {
                        int zwischenSumme = exam.getPoints()*4 + nextKey-sumPoints;
                        int x = zwischenSumme/4+(exam.getPoints()/3*2);

                        if(x < 15) {
                            table.addCell(String.valueOf(x));
                            table.addCell(String.valueOf(exam.getPoints()*4 + nextKey-sumPoints));
                            table.addCell(String.valueOf(nextKey));
                            table.addCell(String.valueOf(grade - .1));
                        } else {
                            table.addCell(new Cell(1,4));
                        }
                    } else {
                        table.addCell(new Cell(1,4));
                    }
                }

                table.addCell(new Cell(1,3).add(new Paragraph("Gesamtpunktzahl")));
                table.addCell(sumPoints.toString());
                table.addCell(new Cell(1,4));



                table.addCell(new Cell(1,3).add(new Paragraph("Endnote")));
                assert grade != null;
                table.addCell(grade.toString());
                table.addCell(new Cell(1,4).add(new Paragraph("fehlende Punkte bis zur besseren Note: " + (nextKey-sumPoints)).setFont(italic)));


                document.add(title);
                document.add(new Paragraph(currentPupil + ", geboren am "+ currentPupil.getBirthDate()).setFont(normal).setFontSize(11));
                if(currentPupil.getTutor() != null) {
                    document.add(new Paragraph("Tutorium: " + currentPupil.getTutor().toString()).setFont(normal).setFontSize(11));
                }
                document.add(table);
                //document.add(new Paragraph(currentPupil + " wird im Abschlussjahr " + currentPupil.getExamDate() + " das Abitur ablegen und dabei eine Endnote von " + grade + " erhalten. Diese kann durch ablegen einer der oben vorgeschlagenen Zusatzprüfungen auf " + (grade - .1) + " verbessert werden. Bis zur nächst besseren Note fehlen insgesamt " + (nextKey-sumPoints) + " Punkte.").setFont(normal).setFontSize(11));

            }

            Alert pdfSavedAltert = new Alert(Alert.AlertType.INFORMATION);
            Stage alertStage = (Stage) pdfSavedAltert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(MainApplication.appImage);
            pdfSavedAltert.setTitle("Akte exportieren");
            pdfSavedAltert.setHeaderText("Punkteübersicht exportieren");
            pdfSavedAltert.setContentText("Die Punkteübersicht wurde erfolgreich als PDF-Dokument gespeichert.");
            pdfSavedAltert.show();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NullPointerException ignored) {
        } finally {
            if (document != null) document.close();
        }

    }

}
